package com.unisoft.collection.settings.SMS.smslog;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SMSLogRepository extends JpaRepository<SmsLog, Long> {
}
