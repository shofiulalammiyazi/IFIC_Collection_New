package com.unisoft.retail.card.dataEntry.distribution.accountBasicInfo;
/*
Created by   Islam at 7/21/2019
*/

import com.unisoft.base.BaseInfo;
import com.unisoft.customerbasicinfo.CustomerBasicInfoEntity;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "CARD_ACCOUNT_BASIC_INFO",
        uniqueConstraints = @UniqueConstraint(columnNames = {"CUSTOMER_ID"})
)
public class CardAccountBasicInfo extends BaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String clientId;

    private String cardNo;

    private String contractId; //cardNo

    private String cardName;

    private String currency;

    private String location;

    private String customerType;

    private String recommenderNumber;

    private String recommenderName;

    private String recommenderPhone;

    private String cardType;

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    private CustomerBasicInfoEntity customer;

    public CardAccountBasicInfo() {
    }

    public CardAccountBasicInfo(String clientId, String contractId, String location) {
        this.clientId = clientId;
        this.contractId = contractId;
        this.location = location;
    }


}
