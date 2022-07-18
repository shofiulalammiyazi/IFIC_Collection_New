package com.csinfotechbd.collection.samd.setup.borrowerPresentStatus;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BorrowerPresentStatusRepository extends JpaRepository<BorrowerPresentStatus, Long> {

    BorrowerPresentStatus findBorrowerPresentStatusById(Long id);
}
