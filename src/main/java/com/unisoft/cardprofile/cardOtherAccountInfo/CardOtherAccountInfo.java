package com.unisoft.cardprofile.cardOtherAccountInfo;

import com.unisoft.base.BaseInfo;
import com.unisoft.customerbasicinfo.CustomerBasicInfoEntity;
import lombok.Data;

import javax.persistence.*;


//Created by: Nabil, Mijan on August 18, 2019

@Entity
@Data
public class CardOtherAccountInfo extends BaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String otherAccountNo;

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    private CustomerBasicInfoEntity customerBasicInfoEntity;

    private String otherRemark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOtherAccountNo() {
        return otherAccountNo;
    }

    public void setOtherAccountNo(String otherAccountNo) {
        this.otherAccountNo = otherAccountNo;
    }

    public CustomerBasicInfoEntity getCustomerBasicInfoEntity() {
        return customerBasicInfoEntity;
    }

    public void setCustomerBasicInfoEntity(CustomerBasicInfoEntity customerBasicInfoEntity) {
        this.customerBasicInfoEntity = customerBasicInfoEntity;
    }

    public String getOtherRemark() {
        return otherRemark;
    }

    public void setOtherRemark(String otherRemark) {
        this.otherRemark = otherRemark;
    }
}
