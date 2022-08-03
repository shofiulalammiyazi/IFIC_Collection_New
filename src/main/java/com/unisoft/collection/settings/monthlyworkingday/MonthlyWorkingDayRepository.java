package com.unisoft.collection.settings.monthlyworkingday;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonthlyWorkingDayRepository extends JpaRepository<MonthlyWorkingDay, Long> {
}
