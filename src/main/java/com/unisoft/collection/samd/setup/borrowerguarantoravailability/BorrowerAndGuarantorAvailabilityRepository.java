package com.unisoft.collection.samd.setup.borrowerguarantoravailability;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface BorrowerAndGuarantorAvailabilityRepository extends JpaRepository<BorrowerAndGuarantorAvailability,Long> {


}
