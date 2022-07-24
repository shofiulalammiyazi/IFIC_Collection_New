package com.unisoft.collection.chequeManagement;

import com.unisoft.common.CommonEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "lms_loan_cheque_management")
public class ChequeManagement extends CommonEntity {

    private String customerId;

    private String chequeNo;

    private String accountNo;

    private String accountName;

    private String bankName;

    private String branchName;

    private String routingNo;

    private Double amount;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date chequeDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date receiveDate;

    private String receivedBy;

    private String presentStatus;
}
