package com.kc.lmsng.Entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table
public class Structure {
    @Id
    String structId;
    String structCrncy;
    Character structType;
}
