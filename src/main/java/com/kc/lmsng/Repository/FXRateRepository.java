package com.kc.lmsng.Repository;

import com.kc.lmsng.Entity.FXRate;
import com.kc.lmsng.Entity.FXRateKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FXRateRepository extends JpaRepository<FXRate, FXRateKey> {
}
