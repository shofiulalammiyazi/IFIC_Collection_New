package com.unisoft.collection.settings.deferredAccount;

import com.google.gson.annotations.Expose;
import com.unisoft.base.BaseInfo;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "deferred_account")
public class DeferredAccount extends BaseInfo {

    @Expose
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String accNo;
    private String status;
}
