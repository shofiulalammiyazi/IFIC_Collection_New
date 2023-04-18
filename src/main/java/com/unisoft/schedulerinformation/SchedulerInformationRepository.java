package com.unisoft.schedulerinformation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchedulerInformationRepository extends JpaRepository<SchedulerInformationEntity,Long> {

    SchedulerInformationEntity getById(Long id);
}
