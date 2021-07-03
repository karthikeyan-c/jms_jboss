package com.kc.lmsng.Service;

import com.kc.lmsng.Entity.Account;
import com.kc.lmsng.Entity.Limits;
import com.kc.lmsng.Entity.TargetAcct;
import com.kc.lmsng.Repository.AccountRepository;
import com.kc.lmsng.Repository.LimitRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.OptionalDouble;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@Slf4j
public class LimitService {

    private final LimitRepository limitRepository;
    private final AccountRepository accountRepository;
    private final StructureService structureService;

    @Autowired
    public LimitService(LimitRepository limitRepository, AccountRepository accountRepository, StructureService structureService) {
        this.limitRepository = limitRepository;
        this.accountRepository = accountRepository;
        this.structureService = structureService;
    }

    @Async("limitPool")
    public CompletableFuture<Double> evaluateLimits(){
        //Async makes it to run on LimitsPool (default custom config)
        List<Limits> limitAll = limitRepository.findAll();

        // parallel streams will lead to run on commonPool. So don't use.
        // When required use with ForkJoinPool/RecursiveAction/RecursiveTask.
        OptionalDouble minLimit = limitAll.stream()
                .mapToDouble(this::computeLimit)
                .min();

        log.info(" minLimit is " + minLimit);
        return CompletableFuture.completedFuture(minLimit.orElse(99999999999.99));
    }

    public Double computeLimit(Limits limit) {
        //To-do ==> Limit logic.
        log.info(" limit.getLimitAmt() is " + limit.getLimitAmt());
        return limit.getLimitAmt();
    }
}
