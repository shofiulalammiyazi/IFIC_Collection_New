package com.csinfotechbd.collection.samd.dataEntry.basicInformation;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SAMDBasicInformationRepository extends JpaRepository<SAMDBasicInformation, Long> {

    @Query(value = "SELECT * FROM SAMDBASIC_INFORMATION where STATUS = 1 ORDER BY CREATED_DATE DESC ", nativeQuery = true)
    SAMDBasicInformation findSAMDBasicInformationByStatusAndOrderByCreatedDateDesc();

}
