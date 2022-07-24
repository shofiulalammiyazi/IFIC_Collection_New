package com.unisoft.collection.agency.agencyStatus;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgencyStatusRepository extends JpaRepository<AgencyStatusEntity, Long> {
    AgencyStatusEntity findByName(String Name);
}
