package com.csinfotechbd.legal.setup.litigationZone;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.List;

@Repository
public interface LitigationZoneRepository extends JpaRepository<LitigationZoneEntity, Long> {

    @Query(value = "" +
            "SELECT L.ID, L.NAME, LISTAGG(B.BRANCH_NAME,',') WITHIN GROUP(ORDER BY B.BRANCH_ID) AS BRANCHES " +
            "FROM LITIGATION_ZONE_ENTITY_BRANCHES LZEB " +
            "JOIN LITIGATION_ZONE_ENTITY L on LZEB.LITIGATION_ZONE_ENTITY_ID = L.ID AND L.ENABLED = 1 " +
            "JOIN LOS_TB_S_BRANCH B on LZEB.BRANCHES_BRANCH_ID = B.BRANCH_ID AND B.ENABLED = 1 " +
            "GROUP BY L.ID, L.NAME", nativeQuery = true)
    List<Tuple> findDtosWithBranchNames();

    List<LitigationZoneEntity> findByEnabled(boolean enabled);

    @Query(value = "" +
            "SELECT L.ID, L.NAME " +
            "FROM LITIGATION_ZONE_ENTITY L " +
            "WHERE L.ENABLED = 1 " +
            "  AND REGEXP_REPLACE(LOWER(L.NAME), '\\W+', '') LIKE '%' || REGEXP_REPLACE(LOWER(:zoneName), '\\W+', '') || '%'", nativeQuery = true)
    List<Tuple> findFirstByNameMatches(@Param("zoneName") String zoneName);


    @Query(value = "" +
            "SELECT DISTINCT LZE.ID, LZE.NAME, D.DISTRICTS, B.BRANCHES " +
            "FROM LITIGATION_ZONE_ENTITY LZE " +
            "       JOIN LITIGATION_CASE_INFO LCI ON LZE.NAME = LCI.ZONE AND LCI.ZONE IS NOT NULL " +
            "       LEFT JOIN (SELECT LZE.ID, LISTAGG(DE.NAME, ',') WITHIN GROUP (ORDER BY DE.ID) AS DISTRICTS " +
            "                  FROM LITIGATION_ZONE_ENTITY LZE " +
            "                         LEFT JOIN LITIGATION_ZONE_ENTITY_DISTRICTS LZED ON LZE.ID = LZED.LITIGATION_ZONE_ENTITY_ID " +
            "                         LEFT JOIN DISTRICT_ENTITY DE ON LZED.DISTRICTS_ID = DE.ID AND DE.ENABLED = 1 " +
            "                 GROUP BY LZE.ID) D ON LZE.ID = D.ID " +
            "       LEFT JOIN (SELECT LZE.ID, LISTAGG(B.BRANCH_NAME, ',') WITHIN GROUP (ORDER BY B.BRANCH_ID) AS BRANCHES " +
            "                  FROM LITIGATION_ZONE_ENTITY LZE " +
            "                         LEFT JOIN LITIGATION_ZONE_ENTITY_BRANCHES LZEB ON LZEB.LITIGATION_ZONE_ENTITY_ID = LZE.ID " +
            "                         LEFT JOIN LOS_TB_S_BRANCH B ON LZEB.BRANCHES_BRANCH_ID = B.BRANCH_ID AND B.ENABLED = 1 " +
            "                 GROUP BY LZE.ID) B ON LZE.ID = B.ID " +
            "WHERE LZE.ENABLED = 1", nativeQuery = true)
    List<Tuple> getAssignedZonesWithDistrictNames();



//    @Query(value = "" +
//            "SELECT DISTINCT LZE.ID, " +
//            "                LZE.NAME, " +
//            "                LISTAGG(DE.NAME, ',') WITHIN GROUP (ORDER BY DE.ID)            AS DISTRICTS, " +
//            "                REPLACE('[' || " +
//            "                        RTRIM( " +
//            "                          XMLAGG( " +
//            "                            XMLELEMENT(e, B.BRANCH_NAME, ',') " +
//            "                                .extract('//text()') ORDER BY B.BRANCH_ID) " +
//            "                              .getclobval(), ', ') " +
//            "                          || ']', '&quot;', '\\\"')                               AS BRANCHES " +
//            "FROM LITIGATION_ZONE_ENTITY LZE " +
//            "       JOIN LITIGATION_CASE_INFO LCI ON LZE.NAME = LCI.ZONE AND LCI.ZONE IS NOT NULL " +
//            "       LEFT JOIN LITIGATION_ZONE_ENTITY_DISTRICTS LZED ON LZE.ID = LZED.LITIGATION_ZONE_ENTITY_ID " +
//            "       LEFT JOIN DISTRICT_ENTITY DE ON LZED.DISTRICTS_ID = DE.ZONE_ID AND DE.ENABLED = 1 " +
//            "       LEFT JOIN LITIGATION_ZONE_ENTITY_BRANCHES LZEB ON LZEB.LITIGATION_ZONE_ENTITY_ID = LZE.ID " +
//            "       LEFT JOIN LOS_TB_S_BRANCH B ON LZEB.BRANCHES_BRANCH_ID = B.BRANCH_ID AND B.ENABLED = 1 " +
//            "WHERE LZE.ENABLED = 1 " +
//            "GROUP BY LZE.ID, LZE.NAME", nativeQuery = true)
//    List<Tuple> getAssignedZonesWithDistrictNames();




}
