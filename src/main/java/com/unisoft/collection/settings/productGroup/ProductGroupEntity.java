package com.unisoft.collection.settings.productGroup;

import com.unisoft.base.BaseInfo;
import com.google.gson.annotations.Expose;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class ProductGroupEntity extends BaseInfo {

    @Expose
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Expose
    @Column(unique = true)
    private String name;

    private String shortName;

    @Expose
    @Column(unique = true)
    private String code;

    private String cardAccount;

    public ProductGroupEntity() {
    }

    public ProductGroupEntity(Long id) {
        this.id = id;
    }

    public String getCardAccount() {
        return cardAccount;
    }

    public void setCardAccount(String cardAccount) {
        this.cardAccount = cardAccount;
    }
}
