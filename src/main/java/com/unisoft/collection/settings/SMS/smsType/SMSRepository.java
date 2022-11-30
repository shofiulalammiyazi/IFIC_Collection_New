package com.unisoft.collection.settings.SMS.smsType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SMSRepository extends JpaRepository<SMSEntity, Long> {

    @Query(value = "SELECT SE.* FROM SMSENTITY SE WHERE SE.ID=?1", nativeQuery = true)
    SMSEntity findSmsById(Long id);
}