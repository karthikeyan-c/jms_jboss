package com.kc.lmsng.Entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "sub_group_acct")
@IdClass(SubGroupKey.class)
public class SubGroup {
    @Id
    String acctId;

    @Id
    Integer subGroupId;
}
