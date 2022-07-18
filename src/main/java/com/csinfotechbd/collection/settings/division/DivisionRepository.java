package com.csinfotechbd.collection.settings.division;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DivisionRepository extends JpaRepository<DivisionEntity, Long> {
    DivisionEntity findByDivName(String name);

    List<DivisionEntity> findByEnabled(boolean enabled);

    boolean existsByDivCode(String divCode);
}
