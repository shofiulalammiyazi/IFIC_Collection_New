package com.csinfotechbd.retail.loan.dataEntry.manualAccountWriteOff;

import com.csinfotechbd.common.CommonEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
@Data
public class ManualAccountWriteOff extends CommonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String accountNumber;
    private String accountName;
    private String accountStatus;
    private String location;
    private String productCode;
    private String branchCode;
}
