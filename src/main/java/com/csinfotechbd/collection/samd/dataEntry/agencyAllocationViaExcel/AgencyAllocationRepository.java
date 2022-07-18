package com.csinfotechbd.collection.samd.dataEntry.agencyAllocationViaExcel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgencyAllocationRepository extends JpaRepository<AgencyAllocation, Long> {


    @Query(value = "SELECT * FROM SAM_AGENCY_ALLOCATION WHERE ACCOUNT_NO IN ?1 AND LATEST=?2 ", nativeQuery = true)
    List<AgencyAllocation> findAgencyAllocationList(List<String> accountNoList, boolean latest);


    @Query(value = "SELECT * FROM SAM_AGENCY_ALLOCATION WHERE LATEST = 1 ", nativeQuery = true)
    List<AgencyAllocation> findAgencyAllocationsByLatest();
}
