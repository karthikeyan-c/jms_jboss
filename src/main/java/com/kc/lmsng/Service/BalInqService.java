package com.kc.lmsng.Service;

import com.kc.lmsng.Entity.Account;
import com.kc.lmsng.Entity.TargetAcct;
import com.kc.lmsng.Repository.SubGroupRepository;
import com.kc.lmsng.Repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.OptionalDouble;
import java.util.Set;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class BalInqService {

    private final AccountRepository accountRepository;
    private final TargetAcct targetAcct;
    private final StructureService structureService;
    private final LimitService limitService;
    private final FXService fxService;

    @Autowired
    public BalInqService(AccountRepository accountRepository, TargetAcct targetAcct, SubGroupRepository subGroupRepository, StructureService structureService, LimitService limitService, FXService fxService) throws InterruptedException {
        this.accountRepository = accountRepository;
        this.targetAcct = targetAcct;
        this.structureService = structureService;
        this.limitService = limitService;
        this.fxService = fxService;
    }

    public Double balInqSvc(String accountId) throws ExecutionException, InterruptedException {
        //ForkJoinPool service = new ForkJoinPool(4);
        //List<Future<Double>> futureLimit = service.invokeAll(limitService.evaluateLimits());

        targetAcct.setToCrncy(accountRepository.getOne(accountId).getAcctCrncy());

        // TO-do ==> Hystrix

        CompletableFuture<Double> minEab = getEAB(accountId);
        CompletableFuture<Double> minLimit = limitService.evaluateLimits();

        OptionalDouble eab = Stream.of(minEab, minLimit)
                .mapToDouble(CompletableFuture::join)
                .min();

        log.info("eab is " + eab);
        // To-do ==> return available balance in ELSE.
        return eab.orElse(9999999999.99);
    }

    @Async("limitPool")
    private CompletableFuture<Double> getEAB(String accountId) {
        Double EAB = structureService.getAcctMap(accountId)
                .stream()
                .map(accountRepository::getOne)
                .collect(Collectors.groupingBy(Account::getAcctCrncy,
                        Collectors.summingDouble(Account::getBalance)))
                .entrySet().stream()
                .mapToDouble(targetAcct::applyFX).sum();
        log.info("getEAB " + EAB);
        return CompletableFuture.completedFuture(EAB);
    }

    @Scheduled(fixedRate = 360000)
    public void refreshAndBuild() {
        structureService.buildSubGroupToAcctMap();
        buildAccountMap();
    }

    public void buildAccountMap() {
        Set<String> masterAcctList = structureService.getSubGroupAcctList2().keySet();
        /*
        ForkJoinPool customPool = new ForkJoinPool(4);

        customPool.submit(() -> {
            masterAcctList.parallelStream()
                    .forEach((acctId) -> {
                        List<Integer> passSubGroupList = new ArrayList<>();
                        List<String> acctList = new ArrayList<>();
                        List<String> derivedAcctList = new ArrayList<>();

                        acctList.add(acctId);
                        derivedAcctList.add(acctId);
                        //Derive accounts
                        derivedAcctList.addAll(deriveAccts(acctList, passSubGroupList));
                        acctListMap.put(acctId, derivedAcctList);
                    });
        }).join();*/
        masterAcctList.forEach(structureService::buildOneAcct);
    }

    @Scheduled(fixedRate = 360000)
    @Transactional
    public void fxRateBuild(){
        fxService.getFxRateAll()
                .forEach(fxService::putFXRate);
    }
}
