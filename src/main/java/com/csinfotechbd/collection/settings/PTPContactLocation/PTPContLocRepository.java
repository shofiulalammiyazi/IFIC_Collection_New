package com.csinfotechbd.collection.settings.PTPContactLocation;
/*
Created by Monirul Islam at 6/30/2019
*/

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PTPContLocRepository extends JpaRepository<PTPContactLocationEntity, Long> {
    List<PTPContactLocationEntity> findByEnabled(boolean enabled);
}
