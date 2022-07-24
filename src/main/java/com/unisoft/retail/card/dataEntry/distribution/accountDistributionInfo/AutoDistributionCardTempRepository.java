package com.unisoft.retail.card.dataEntry.distribution.accountDistributionInfo;
/*
Created by   Islam at 9/8/2019
*/

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AutoDistributionCardTempRepository extends JpaRepository<AutoDistributionCardTemp, Long> {
    List<AutoDistributionCardTemp> findAllByOrderByIdDesc();
}
