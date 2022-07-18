package com.csinfotechbd.collection.samd.dataEntry.borrowerPresentProposition;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BorrowerPresentPropositionRepository extends JpaRepository<BorrowerPresentProposition, Long> {
    List<BorrowerPresentProposition> findAllByAccountNumber(String accountNumber);
}
