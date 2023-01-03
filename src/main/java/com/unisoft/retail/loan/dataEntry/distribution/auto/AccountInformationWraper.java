package com.unisoft.retail.loan.dataEntry.distribution.auto;

import com.unisoft.retail.loan.dataEntry.CustomerUpdate.accountInformation.AccountInformationEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Setter
@Getter
public class AccountInformationWraper {

    List<AccountInformationEntity> accountInformationEntityList;

    public AccountInformationWraper() {
    }

    public AccountInformationWraper(List<AccountInformationEntity> accountInformationEntityList) {
        this.accountInformationEntityList = accountInformationEntityList;
    }
}
