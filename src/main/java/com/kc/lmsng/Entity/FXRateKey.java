package com.kc.lmsng.Entity;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class FXRateKey implements Serializable {
    private String fromCrncy;
    private String toCrncy;
}
