package com.csinfotechbd.collection.settings.ageCode;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AgeCodeRepository extends JpaRepository<AgeCodeEntity, Long> {

    @Query(value = "select * from AGE_CODE where NAME in (select distinct(AGE_CODE) from " +
            "CARD_ACCOUNT_DISTRIBUTION_INFO where DEALER_PIN=?1 and CREATED_DATE >=?2 and CREATED_DATE <=?3)", nativeQuery = true)
    List<AgeCodeEntity> findByDealerPin(String pin, Date startDate, Date endDate);

    AgeCodeEntity findFirstByName(String name);
}
