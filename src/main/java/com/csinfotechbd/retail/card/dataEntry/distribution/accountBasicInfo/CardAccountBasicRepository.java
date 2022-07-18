package com.csinfotechbd.retail.card.dataEntry.distribution.accountBasicInfo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.List;

@Repository
public interface CardAccountBasicRepository extends JpaRepository<CardAccountBasicInfo, Long> {
    CardAccountBasicInfo findByCardNo(String accountNo);

    @Query(value = "SELECT CABI.CARD_NAME AS customerAccName, CABI.CARD_NO AS cardNumber, CBIE.BRANCH_NAME AS branchName " +
            "FROM CARD_ACCOUNT_BASIC_INFO CABI " +
            "            LEFT JOIN CUSTOMER_BASIC_INFO_ENTITY CBIE ON CBIE.ID = CABI.CUSTOMER_ID " +
            "WHERE CABI.CLIENT_ID = ? ", nativeQuery = true)
    Tuple getCardDataForLegal(String clientId);

    CardAccountBasicInfo findFirstByClientId(String clientId);
    CardAccountBasicInfo findFirstByContractIdAndCardType(String clientId, String cardType);
    List<CardAccountBasicInfo> findAllByClientId(String clientId);

    List<CardAccountBasicInfo> findAllByContractId(String accountNo);

    CardAccountBasicInfo findByContractIdAndClientId(String contractNo, String clientId);


    @Query(value = "SELECT CONTRACT_ID AS contractId FROM CARD_ACCOUNT_BASIC_INFO ", nativeQuery = true)
    List<String> findAllContractId();
}
