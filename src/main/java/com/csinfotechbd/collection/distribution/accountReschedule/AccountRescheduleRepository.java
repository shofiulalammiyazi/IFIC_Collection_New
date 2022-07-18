package com.csinfotechbd.collection.distribution.accountReschedule;
/*
Created by Monirul Islam at 8/26/2019
*/

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository

public interface AccountRescheduleRepository extends JpaRepository<AccountReschedule, Long> {
    List<AccountReschedule> findByCreatedDateIsBetween(Date startDate, Date endDate);

    AccountReschedule findFirstByAccountNoOrderByCreatedDateDesc(String accountNo);
}
