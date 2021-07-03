package com.kc.lmsng.Controller;

import com.kc.lmsng.Entity.Account;
import com.kc.lmsng.Service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api")
@Slf4j
@Transactional
public class lmsngController {

    private final AccountService acctService;
    private final StructureService structureService;
    private final BalInqService balInqService;
    private final FXService fxService;
    private final CacheManager cacheManager;

//    @Autowired
//    private JmsTemplate jmsTemplate;

    public lmsngController(AccountService acctService, StructureService structureService, BalInqService balInqService, FXService fxService, CacheManager cacheManager) {
        this.acctService = acctService;
        this.structureService = structureService;
        this.balInqService = balInqService;
        this.fxService = fxService;
        this.cacheManager = cacheManager;
    }

//    @GetMapping("/send")
//    public String send(){
//        try{
//            jmsTemplate.convertAndSend("DEV.QUEUE.1", "Hello World!");
//            return "OK";
//        }catch(JmsException ex){
//            ex.printStackTrace();
//            return "FAIL";
//        }
//    }

    @GetMapping("/balInq/{accountId}")
    public ResponseEntity<Double> balanceInquiry(@PathVariable String accountId) throws ExecutionException, InterruptedException {
        return new ResponseEntity<>(balInqService.balInqSvc(accountId), HttpStatus.OK);
        //return new ResponseEntity<>(0.00, HttpStatus.OK);
    }

    @PutMapping("/account/{accountId}")
    public ResponseEntity<Account> accountUpdate(@RequestBody Account account) throws InterruptedException {
        log.info("accountUpdate starts===>");
        System.out.println("inside PUT ===============>");
        Account account1 = acctService.updateAccount(account);
        log.info("<====accountUpdate ends");
        return new ResponseEntity<>(account1, HttpStatus.OK);
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<Double> getAccount(@PathVariable String accountId) throws InterruptedException {
        log.info("getAccount starts===>");
        System.out.println("inside GET ===============>");
        Account account = acctService.getAccount(accountId);
        log.info("<====getAccount ends");
        return new ResponseEntity<>(account.getBalance(), HttpStatus.OK);
    }

    @GetMapping("/build")
    public ResponseEntity<String> buildAcctList(){
        log.info("inside build");
        balInqService.refreshAndBuild();
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @GetMapping("/fxRefresh")
    public ResponseEntity<String> fxRefresh(){
        log.info("inside fxRefresh");
        balInqService.fxRateBuild();
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

//    @GetMapping("/put")
//    public ResponseEntity<String> put(){
//        log.info("inside put");
//        testservice.setAcct("1");
//        return new ResponseEntity<>("success", HttpStatus.OK);
//    }
//
//    @GetMapping("/get")
//    public ResponseEntity<String> get(){
//        log.info("inside get");
//        testservice.getAcct("1");
//        return new ResponseEntity<>("success", HttpStatus.OK);
//    }

}
