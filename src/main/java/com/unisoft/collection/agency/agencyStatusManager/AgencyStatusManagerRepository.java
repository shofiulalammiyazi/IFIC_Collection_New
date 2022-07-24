package com.unisoft.collection.agency.agencyStatusManager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AgencyStatusManagerRepository extends JpaRepository<AgencyStatusManagerEntity, Long> {
    AgencyStatusManagerEntity findFirstByUserIdOrderByIdDesc(Long userId);

    AgencyStatusManagerEntity findAgencyStatusManagerEntityById(Long id);
}
