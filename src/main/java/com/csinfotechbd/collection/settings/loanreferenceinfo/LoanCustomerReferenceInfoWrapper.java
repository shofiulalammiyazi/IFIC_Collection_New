package com.csinfotechbd.collection.settings.loanreferenceinfo;

import com.csinfotechbd.customerloanprofile.referenceinfo.ReferenceInfoEntity;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class LoanCustomerReferenceInfoWrapper {
    List<ReferenceInfoEntity>referenceInfoEntityList=new ArrayList<>();
}
