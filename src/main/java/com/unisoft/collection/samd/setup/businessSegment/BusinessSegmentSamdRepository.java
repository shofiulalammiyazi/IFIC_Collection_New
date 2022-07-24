package com.unisoft.collection.samd.setup.businessSegment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusinessSegmentSamdRepository extends JpaRepository<BusinessSegmentSamd, Long> {


    BusinessSegmentSamd findBusinessSegmentById (Long id);
}
