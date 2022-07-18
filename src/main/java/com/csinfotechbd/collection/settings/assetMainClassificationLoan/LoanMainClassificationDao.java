package com.csinfotechbd.collection.settings.assetMainClassificationLoan;
/*
Created by Monirul Islam at 7/16/2019 
*/

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanMainClassificationDao extends JpaRepository<LoanMainClassification, Long> {
    boolean existsByCode(String code);
    List<LoanMainClassification> findByEnabled(boolean enabled);
}
