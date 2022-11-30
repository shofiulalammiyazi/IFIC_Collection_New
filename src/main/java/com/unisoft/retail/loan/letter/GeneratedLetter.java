package com.unisoft.retail.loan.letter;


import com.unisoft.common.CommonEntity;
import lombok.Data;

import javax.persistence.Entity;

@Entity
@Data
public class GeneratedLetter extends CommonEntity {


    private String accountNo;
    private String referenceId;
    private String dateed;
    private String latterType;

}
