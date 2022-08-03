package com.unisoft.collection.settings.assetClassificationLoan;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssetClassificationLoanRepository extends JpaRepository<AssetClassificationLoanEntity, Long> {
    //AssetClassificationLoanEntity findByCode(String code);
    //AssetClassificationLoanEntity findByName(String name);
    AssetClassificationLoanEntity findByType(String type);
}
