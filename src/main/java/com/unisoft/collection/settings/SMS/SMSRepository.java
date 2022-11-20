package com.unisoft.collection.settings.SMS;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SMSRepository extends JpaRepository<SMSEntity, Long> {
    SMSEntity findSmsById(Long id);
}
