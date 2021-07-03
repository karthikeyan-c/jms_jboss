package com.kc.lmsng.Entity;


import lombok.*;

import java.io.Serializable;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class SubGroupKey implements Serializable {
    private String acctId;
    private Integer subGroupId;
}
