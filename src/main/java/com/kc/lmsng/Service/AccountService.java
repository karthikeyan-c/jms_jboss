package com.kc.lmsng.Service;

import com.kc.lmsng.Entity.Account;
import com.kc.lmsng.Repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@Slf4j
//@Transactional
public class AccountService {

    private AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    //@Transactional(isolation = Isolation.REPEATABLE_READ)
    //@Transactional(propagation = Propagation.REQUIRES_NEW)
//    @Transactional
//    public Account updateAccount(Account account) throws InterruptedException {
//        Account one = accountRepository.getOne(account.getAcctId());
//        log.info("sleeping updateAccount service <==" + account);
//        Thread.sleep(10000);
//        log.info("after sleeping updateAccount service <==" + one);
//        account.setBalance(one.getBalance()+1);
//        log.info("Before save updateAccount service <==" + account);
//        accountRepository.save(account);
//        log.info("After save sleeping updateAccount service <==" + account);
//        Thread.sleep(30000);
//        log.info("end updateAccount service <==");
//        return account;
//    }

    @Transactional
    public Account updateAccount(Account account) throws InterruptedException {
        Integer sleepMillis = 30000;

//        accountRepository.findById(account.getAcctId()).ifPresent(t -> {
////            log.info("At start" + t.toString());
////            t.setAcctCrncy("Pessimistic Lock: " + t.getAcctId() + " Sleep millis: " + sleepMillis);
////            t.setBalance(123.0);
////            try {
////                log.info("sleeping...");
////                Thread.sleep(sleepMillis);
////            } catch (InterruptedException e) {
////                e.printStackTrace();
////            }
//            accountRepository.save(account);
//        });
        accountRepository.save(account);
        return account;
    }

//        Account one = accountRepository.getOne(account.getAcctId());
//        log.info("sleeping updateAccount service <==" + account);
//        Thread.sleep(10000);
//        log.info("after sleeping updateAccount service <==" + one);
//        account.setBalance(one.getBalance()+1);
//        log.info("Before save updateAccount service <==" + account);
//        accountRepository.save(account);
//        log.info("After save sleeping updateAccount service <==" + account);
//        Thread.sleep(30000);
//        log.info("end updateAccount service <==");
//        return account;
//    }

    //@Transactional(isolation = Isolation.REPEATABLE_READ)
    public Account getAccount(String accountId) throws InterruptedException {
        log.info("start getaccount service <==" + accountId);
        log.info("After getaccount service <==" + accountId);
        return accountRepository.getOne(accountId);
    }
}
