package com.unisoft.retail.loan.dataEntry.CustomerUpdate.accountInformationRepository;

import com.unisoft.detailsOfCollection.cardviewmodels.AccountInformation;
import com.unisoft.retail.loan.dataEntry.CustomerUpdate.accountInformation.AccountInformationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AccountInformationRepository extends JpaRepository<AccountInformationEntity, Long> {
}
