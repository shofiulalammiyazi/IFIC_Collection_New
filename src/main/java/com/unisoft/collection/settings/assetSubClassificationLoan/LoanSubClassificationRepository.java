package com.unisoft.collection.settings.assetSubClassificationLoan;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanSubClassificationRepository extends JpaRepository<LoanSubClassification, Long> {

    List<LoanSubClassification> findByEnabled(boolean enabled);

    boolean existsByCode(String code);
}
