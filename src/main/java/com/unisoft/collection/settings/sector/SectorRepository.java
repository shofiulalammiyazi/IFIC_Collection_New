package com.unisoft.collection.settings.sector;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SectorRepository extends JpaRepository<SectorEntity, Long> {
    SectorEntity findFirstByName(String name);
}
