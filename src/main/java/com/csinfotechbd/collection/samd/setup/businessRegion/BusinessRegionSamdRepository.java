package com.csinfotechbd.collection.samd.setup.businessRegion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusinessRegionSamdRepository extends JpaRepository<BusinessRegionSamd, Long> {

    BusinessRegionSamd findBusinessRegionById(Long id);
}
