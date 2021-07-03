package com.kc.lmsng.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Data
@Entity
@Table
@IdClass(FXRateKey.class)
@AllArgsConstructor
@NoArgsConstructor
public class FXRate {
    @Id
    String fromCrncy;
    @Id
    String toCrncy;

    Double rate;
}
