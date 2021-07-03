package com.kc.lmsng.Entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Data
@Table
public class Account {
    @Id
    private String acctId;

    private String acctCrncy;
    private Double balance;
    private Double unUtilizedAmt;
//    @Version
//    private long version;
    //private int subGroup;
}