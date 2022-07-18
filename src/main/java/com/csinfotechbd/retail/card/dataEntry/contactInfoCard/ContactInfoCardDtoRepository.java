package com.csinfotechbd.retail.card.dataEntry.contactInfoCard;

import com.csinfotechbd.retail.card.dataEntry.distribution.accountDistributionInfo.CardAccountDistributionInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.Date;
import java.util.List;

@Repository
public interface ContactInfoCardDtoRepository extends JpaRepository<CardAccountDistributionInfo,Long> {

    @Query(value = " SELECT L.ACCOUNT_NO as accountNo,  " +
            "       L.CUSTOMER_ID as customerId,  " +
            "       COUNT(con.ATTEMPT) AS attempted  " +
            "       FROM CARD_ACCOUNT_DISTRIBUTION_INFO ladi  " +
            "       LEFT JOIN CARD_ACCOUNT_BASIC_INFO L on ladi.LOAN_ACCOUNT_BASIC_INFO_ID = L.ID  " +
            "       left join CONTACT_INFO_CARD con on con.CUSTOMER_ID = L.CUSTOMER_ID  " +
            "       WHERE LADI.CREATED_DATE BETWEEN cast(?1 as date) AND cast(?2 as date) " +
            "  and CON.ATTEMPT IS NOT NULL " +
            "  AND ladi.DEALER_PIN = ?3 " +
            "GROUP BY L.ACCOUNT_NO, l.CUSTOMER_ID ", nativeQuery = true)

    List<Tuple> findCurrentMonthContactInfoCardByDealerPin(Date startDate, Date endDate, String pin);
}
