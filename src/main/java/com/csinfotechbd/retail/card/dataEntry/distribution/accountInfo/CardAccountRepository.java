package com.csinfotechbd.retail.card.dataEntry.distribution.accountInfo;

import com.csinfotechbd.retail.card.dataEntry.distribution.accountBasicInfo.CardAccountBasicInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardAccountRepository extends JpaRepository<CardAccountInfo, Long> {
    CardAccountInfo findFirstByCardAccountBasicInfo(CardAccountBasicInfo cardAccountBasicInfo);

    CardAccountInfo findFirstByClientId(String clientId);
    CardAccountInfo findFirstByContractNoAndCardType(String clientId, String cardType);
}
