package com.kc.lmsng.Entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table
public class Linkage {
    @Id
    String childAcct;

    String parentAcct;
    String structId;
}
