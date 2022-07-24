package com.unisoft.collection.samd.setup.samNplRelease.samNplReleaseLoan;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SamNplReleaseLoanRepository extends JpaRepository<SamNplReleaseLoan, Long> {
    SamNplReleaseLoan findSamNplReleaseLoanById(Long id);
}
