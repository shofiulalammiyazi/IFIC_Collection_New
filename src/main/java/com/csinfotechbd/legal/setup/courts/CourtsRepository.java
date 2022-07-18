package com.csinfotechbd.legal.setup.courts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourtsRepository extends JpaRepository<Courts, Long> {

    List<Courts> findByEnabledOrderBySequence(boolean b);

    @Query("SELECT DISTINCT c FROM Courts c JOIN c.caseTypes ct WHERE ct.id = :caseTypeId ORDER BY c.sequence")
    List<Courts> findByCaseTypeId(@Param("caseTypeId") Long caseTypeId);

    List<Courts> findByNameInOrderByCreatedDateAsc(List<String> courtNames);

//    @Query("SELECT DISTINCT c.name FROM Courts c JOIN c.caseTypes ct WHERE ct.id = :caseTypeId AND c.enabled = true")
//    List<String> findCourtNamesByCaseTypeId(@Param("caseTypeId") Long caseTypeId);

    @Query(value = "SELECT c FROM Courts c JOIN c.caseTypes ct WHERE ct.id = :caseTypeId AND c.name = :name")
    List<Courts> findByCaseTypeAndName(@Param("caseTypeId") Long caseTypeId, @Param("name") String name);

    boolean existsByName(String name);
}
