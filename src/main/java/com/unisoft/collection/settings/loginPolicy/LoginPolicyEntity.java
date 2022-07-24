package com.unisoft.collection.settings.loginPolicy;
/*
Created by   Islam at 7/3/2019
*/

import com.unisoft.base.BaseInfo;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class LoginPolicyEntity extends BaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String userNameCheck;// First char for Alphabet & 2nd char for numeric
    private Boolean specialCharacterRequired; // For password
    private int paswLength;
    private int maxUnsucAtmpt;
    private int paswChngPeriod;
    private int pasHisChk;
    private float sessionIdle;

}
