package com.unisoft.collection.settings.customerinfouploadcard.professionsegment;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessionSegmentRepository extends JpaRepository<ProfessionSegment,Long> {

    ProfessionSegment findByContractId(String contractId);

}
