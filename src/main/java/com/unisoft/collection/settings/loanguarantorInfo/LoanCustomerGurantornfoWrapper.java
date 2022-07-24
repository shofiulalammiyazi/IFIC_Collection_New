package com.unisoft.collection.settings.loanguarantorInfo;

import com.unisoft.customerloanprofile.guarantorinfo.GuarantorInfoEntity;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class LoanCustomerGurantornfoWrapper {
    List<GuarantorInfoEntity>guarantorInfoEntityList=new ArrayList<>();
}
