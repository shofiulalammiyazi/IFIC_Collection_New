package com.unisoft.collection.settings.SMS.generate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeneratedSMSRepository extends JpaRepository<GeneratedSMS, Long> {
}