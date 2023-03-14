package com.unisoft.collection.settings.designation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DesignationRepository extends JpaRepository<DesignationEntity,Long> {

    @Query(value = "SELECT COUNT(1) FROM DESIGNATION_ENTITY", nativeQuery = true)
    String countAll();
}
