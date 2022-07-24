package com.unisoft.retail.card.dataEntry.distribution.accountOtherInfo;

import com.unisoft.retail.card.dataEntry.distribution.accountBasicInfo.CardAccountBasicInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardAccountOtherRepository extends JpaRepository<CardAccountOtherInfo, Long> {
    CardAccountOtherInfo findFirstByCardAccountBasicInfo(CardAccountBasicInfo cardAccountBasicInfo);
}
