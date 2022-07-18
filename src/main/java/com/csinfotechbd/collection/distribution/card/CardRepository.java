package com.csinfotechbd.collection.distribution.card;
/*
Created by Monirul Islam at 7/23/2019
*/

import com.csinfotechbd.retail.card.dataEntry.distribution.accountBasicInfo.CardAccountBasicInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<CardAccountBasicInfo, Long> {

    List<CardAccountBasicInfo> findByCreatedDateIsBetween(Date startDate, Date endDate);

    @Query(value = "SELECT * from CARD_ACCOUNT_BASIC_INFO WHERE CARD_NO LIKE ?1%", nativeQuery = true)
    List<CardAccountBasicInfo> findByAccountCardNumberLike(String accountNumber);
}
