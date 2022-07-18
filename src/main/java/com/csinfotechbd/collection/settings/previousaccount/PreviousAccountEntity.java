package com.csinfotechbd.collection.settings.previousaccount;

import com.csinfotechbd.base.BaseInfo;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class PreviousAccountEntity extends BaseInfo {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;
    private String loanAccountNo;
    private String previousAccountNo;

}
