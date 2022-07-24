package com.unisoft.collection.samd.dataEntry.monitoringFollowUp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MonitoringFollowUpFileRepository extends JpaRepository<MonitoringFollowUpFile, Long> {
}
