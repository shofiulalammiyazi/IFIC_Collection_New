package com.unisoft.schedulerinformation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SchedulerInformationRepository extends JpaRepository<SchedulerInformationEntity,Long> {

    SchedulerInformationEntity getById(Long id);

    @Query(value = "SELECT * FROM SCHEDULER_INFORMATION_ENTITY WHERE SCHEDULER_NAME = ?1 " +
            "AND ENABLED = ?2", nativeQuery = true)
    SchedulerInformationEntity findBySchedulerNameAndStatus(String name, Number Status);

    SchedulerInformationEntity findBySchedulerName(String name);
}
