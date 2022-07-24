package com.unisoft.collection.samd.dataEntry.basicInformation;

import com.unisoft.common.CommonEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SAMDBasicInformation extends CommonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String hobName;
    private String hobDesignation;
    private String keyPerson;
    private String subject;
    private String branchCode;
    private String branchName;
    private String customerId;
    private String accountNo;
    private String accountName;
    private boolean status;

}
