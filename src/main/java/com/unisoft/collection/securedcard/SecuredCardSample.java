package com.unisoft.collection.securedcard;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class SecuredCardSample {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String securedCard;
    private String accountNo;
    private String secureCheck;

    public SecuredCardSample() {
    }

    public SecuredCardSample(String securedCard, String accountNo, String secureCheck) {
        this.securedCard = securedCard;
        this.accountNo = accountNo;
        this.secureCheck = secureCheck;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSecuredCard() {
        return securedCard;
    }

    public void setSecuredCard(String securedCard) {
        this.securedCard = securedCard;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getSecureCheck() {
        return secureCheck;
    }

    public void setSecureCheck(String secureCheck) {
        this.secureCheck = secureCheck;
    }
}
