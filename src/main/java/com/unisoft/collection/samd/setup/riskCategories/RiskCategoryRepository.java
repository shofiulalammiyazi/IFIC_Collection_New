package com.unisoft.collection.samd.setup.riskCategories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RiskCategoryRepository extends JpaRepository<RiskCategory, Long> {
    RiskCategory findRiskCategoryById(Long id);

    List<RiskCategory> findByUnitName(String name);
}
