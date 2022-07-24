package com.unisoft.collection.samd.dataEntry.reasonForClassification;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReasonForClassificationRepository extends JpaRepository<ReasonForClassification, Long> {
    List<ReasonForClassification> findAllByAccountNumber(String accountNumber);
}
