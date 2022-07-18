package com.csinfotechbd.collection.settings.monthlyworkingday;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MonthlyWorkingDayRepository extends JpaRepository<MonthlyWorkingDay, Long> {
}
