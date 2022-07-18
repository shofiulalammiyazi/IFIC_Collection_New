package com.csinfotechbd.collection.settings.unit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UnitRepository extends JpaRepository<UnitEntity, Long> {

    List<UnitEntity> findByEnabled(boolean enabled);

    boolean existsByCode(String code);

    Optional<UnitEntity> findByName(String name);

    @Query("SELECT u FROM UnitEntity u WHERE u.enabled = true AND LOWER(u.name) IN(:names)")
    List<UnitEntity> findByLowerNamesIn(@Param("names") List<String> name);
}
