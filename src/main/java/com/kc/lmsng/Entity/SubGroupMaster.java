package com.kc.lmsng.Entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table
public class SubGroupMaster {
    @Id
    private Integer subGroupId;

    private Character groupType;
}
