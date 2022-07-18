package com.csinfotechbd.collection.settings.earlyNotifyEngine;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EarlyNotifyEngineRepository extends JpaRepository<EarlyNotifyEngine,Long> {

    List<EarlyNotifyEngine> findByEnabled(boolean enabled);
}
