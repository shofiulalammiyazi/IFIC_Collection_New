package com.unisoft.cardprofile;

import com.unisoft.retail.card.dataEntry.distribution.accountDistributionInfo.CardAccountDistributionInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;

@Repository
public interface AllocationDetailsRepository extends JpaRepository<CardAccountDistributionInfo, Long> {
    @Query(value = "" +
            " select cadi.* from card_account_basic_info cabi " +
            " LEFT JOIN card_account_distribution_info cadi on cabi.id = cadi.card_account_basic_info_id " +
            " where cabi.contract_id = ?1 and cabi.CLIENT_ID = ?4 and cadi.created_date BETWEEN ?2 and ?3 ", nativeQuery = true)
    Tuple findCardAccountInfoByContractNoAndClientId(String contactNo,String startDate, String endDate, String clientId);
}
