package com.csinfotechbd.customerloanprofile.hotnote;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface HotNoteRepository extends JpaRepository<HotNoteEntity, Long> {

    @Query(value = "SELECT * FROM HOT_NOTE_ENTITY WHERE CUSTOMER_ID = ?1 AND CREATED_DATE BETWEEN  ?2 AND  ?3 ", nativeQuery = true)
    List<HotNoteEntity> findHotNoteEntitiesByCustomerIdAndMonth(Long customerId, Date startDate, Date endDate);


    @Query(value = "SELECT * FROM HOT_NOTE_ENTITY WHERE CUSTOMER_ID = ? AND STATUS_FLAG = 1 ", nativeQuery = true)
    List<HotNoteEntity> findHotNoteEntitiesByCustomerId(Long customerId);
}
