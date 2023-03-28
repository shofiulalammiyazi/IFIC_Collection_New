package com.unisoft.collection.settings.SMS.smslog;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SMSLogRepository extends JpaRepository<SmsLog, Long> {

    @Query(value = "SELECT * FROM SMS_LOG SL WHERE SL.CREATED_DATE >= SYSDATE-30 and SL.ACC_NO = ?1 AND SL.DEAL_REFERENCE = ?2", nativeQuery = true)
    List<SmsLog> getSmslogofLastThirtyDaysByAccNoAndDealReference(String accNo, String dealReference);
}
