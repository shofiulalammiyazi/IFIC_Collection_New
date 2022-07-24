package com.unisoft.retail.loan.dataEntry.manualAccountWriteOff;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ManualAccountWriteOffRepository extends JpaRepository<ManualAccountWriteOff, Long> {

    @Query(value = "SELECT * FROM MANUAL_ACCOUNT_WRITE_OFF MAWO " +
            "WHERE MAWO.CREATED_DATE BETWEEN cast(?1 as date) AND cast(?2 as date) ", nativeQuery = true)
    List<ManualAccountWriteOff> findCurrentMonthWriteOffAccount(Date startDate, Date endDate);
}
