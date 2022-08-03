package com.unisoft.collection.distribution.samAccountHandover;
/*
Created by   Islam at 9/14/2019
*/

import com.unisoft.retail.card.dataEntry.distribution.accountBasicInfo.CardAccountBasicInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AutoDistributionCardSamTempRepository extends JpaRepository<AutoDistributionCardSamTemp, Long> {

    List<AutoDistributionCardSamTemp> findAllByOrderByIdDesc();

    List<AutoDistributionCardSamTemp> findByCardAccountBasicInfo(CardAccountBasicInfo cardAccountBasicInfo);
}
