package com.unisoft.retail.card.dataEntry.distribution.agency;
/*
Created by   Islam at 7/23/2019
*/

import com.unisoft.retail.card.dataEntry.distribution.accountBasicInfo.CardAccountBasicInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CardAgencyDistributionRepository extends JpaRepository<CardAgencyDistributionInfo, Long> {
    List<CardAgencyDistributionInfo> findByCreatedDateIsBetween(Date startDate, Date endDate);
    CardAgencyDistributionInfo findFirstByCardAccountBasicInfoOrderByCreatedDateDesc(CardAccountBasicInfo cardAccountBasicInfo);


    List<CardAgencyDistributionInfo> findByCreatedDateGreaterThanEqualAndCreatedDateLessThanEqualAndCardAccountBasicInfo(Date startDate, Date endDate, CardAccountBasicInfo cardAccountBasicInfo);

    List<CardAgencyDistributionInfo> findByCreatedDateGreaterThanEqualAndCreatedDateLessThanEqualAndLatest(Date startDate, Date endDate, String s);

    CardAgencyDistributionInfo findFirstByCreatedDateGreaterThanEqualAndCreatedDateLessThanEqualAndCardAccountBasicInfoOrderByCreatedDateDesc(Date startDate, Date endDate, CardAccountBasicInfo cardAccountBasicInfo);

    CardAgencyDistributionInfo findFirstByCreatedDateGreaterThanEqualAndCreatedDateLessThanEqualAndCardAccountBasicInfoAndLatestOrderByCreatedDateDesc(Date startDate, Date endDate, CardAccountBasicInfo cardAccountBasicInfo, String s);

    CardAgencyDistributionInfo findFirstByCreatedDateLessThanAndCardAccountBasicInfoAndLatestOrderByCreatedDateDesc(Date startDate, CardAccountBasicInfo cardAccountBasicInfo, String s);
}
