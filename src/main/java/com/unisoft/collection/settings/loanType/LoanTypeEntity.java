package com.unisoft.collection.settings.loanType;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Data
@Entity
public class LoanTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private String type;

    private Long noOfDaysBeforeEMIDate;

    private  Long unpaidInstallmentNumber;

    private String loanType;

    private String loanStatus;

}
