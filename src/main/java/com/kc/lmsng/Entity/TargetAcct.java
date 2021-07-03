package com.kc.lmsng.Entity;

import com.kc.lmsng.Service.FXService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Map;
import java.util.Optional;

@Data
@Component
@Slf4j
public class TargetAcct {
    private String toCrncy;

    @Autowired
    private FXService fxService;

    public Double applyFX(Map.Entry<String,Double> crncyBucket){
        double convAmount = crncyBucket.getValue();

        if (!crncyBucket.getKey().equals(this.toCrncy)) {
            convAmount = crncyBucket.getValue() * fxService.getFXRate(crncyBucket.getKey(),this.toCrncy);
            //To-do ==> haircut
        }
        //log.info("====================================");
        //log.info("from value: " + crncyBucket.getValue());
        //log.info("from currency: " + crncyBucket.getKey());
        //log.info("to value: " + convAmount);
        //log.info("to currency: " + toCrncy);
        //log.info("====================================");
        return convAmount;
    }
}
