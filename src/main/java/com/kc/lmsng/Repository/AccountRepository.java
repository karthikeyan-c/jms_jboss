package com.kc.lmsng.Repository;

import com.kc.lmsng.Entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockModeType;
import java.util.Optional;

@Repository
//@Transactional
public interface AccountRepository extends JpaRepository<Account, String> {
    @Override
    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    Optional<Account> findById(String acctId);
}
