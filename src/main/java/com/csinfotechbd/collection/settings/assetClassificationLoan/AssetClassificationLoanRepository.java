package com.csinfotechbd.collection.settings.assetClassificationLoan;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssetClassificationLoanRepository extends JpaRepository<AssetClassificationLoanEntity, Long> {
    //AssetClassificationLoanEntity findByCode(String code);
    //AssetClassificationLoanEntity findByName(String name);
    AssetClassificationLoanEntity findByType(String type);
}
