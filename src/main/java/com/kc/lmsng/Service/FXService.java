package com.kc.lmsng.Service;

import com.kc.lmsng.Entity.FXRate;
import com.kc.lmsng.Repository.FXRateRepository;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;

@Service
@Slf4j
public class FXService {
    private final FXRateRepository FXRateRepository;

    @Autowired
    public FXService(com.kc.lmsng.Repository.FXRateRepository fxRateRepository) {
        FXRateRepository = fxRateRepository;
    }

    @Synchronized
    public List<FXRate> getFxRateAll() {
        return FXRateRepository.findAll();
    }

    @CachePut(value = "fxmap", key = "#fxRate.fromCrncy + #fxRate.toCrncy")
    public Double putFXRate(FXRate fxRate){
        log.info("inside putFXRate" + fxRate.getFromCrncy() + fxRate.getToCrncy());
        return fxRate.getRate();
    }

    @Cacheable(value = "fxmap", key = "#fromCrncy + #toCrncy")
    public Double getFXRate(String fromCrncy, String toCrncy){
        log.info("inside getFXRate" + fromCrncy + toCrncy);
        return putFXRate(new FXRate(fromCrncy,toCrncy,0.00));
    }
}
