package com.csinfotechbd.collection.settings.loanguarantorInfo;

import com.csinfotechbd.customerloanprofile.guarantorinfo.GuarantorInfoEntity;
import com.csinfotechbd.customerloanprofile.referenceinfo.ReferenceInfoEntity;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class LoanCustomerGurantornfoWrapper {
    List<GuarantorInfoEntity>guarantorInfoEntityList=new ArrayList<>();
}
