package com.csinfotechbd.collection.settings.monthlyworkingday;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonthlyWorkingDayDetailsRepository extends JpaRepository<MonthlyWorkingDayDetails, Long> {
}
