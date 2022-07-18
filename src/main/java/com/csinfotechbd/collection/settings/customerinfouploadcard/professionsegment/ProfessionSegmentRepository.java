package com.csinfotechbd.collection.settings.customerinfouploadcard.professionsegment;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfessionSegmentRepository extends JpaRepository<ProfessionSegment,Long> {

    ProfessionSegment findByContractId(String contractId);

}
