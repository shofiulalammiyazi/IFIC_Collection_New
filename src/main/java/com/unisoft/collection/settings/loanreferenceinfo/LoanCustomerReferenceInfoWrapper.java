package com.unisoft.collection.settings.loanreferenceinfo;

import com.unisoft.customerloanprofile.referenceinfo.ReferenceInfoEntity;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class LoanCustomerReferenceInfoWrapper {
    List<ReferenceInfoEntity>referenceInfoEntityList=new ArrayList<>();
}
