package com.kc.lmsng.Entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table
public class Limits {
    @Id
    int limitId;

    String limitType;
    String limitCrncy;
    String listOfAccts;
    Double limitAmt;
    Double unUtilizedAmt;
    Double unUtilizedLimit;
}
