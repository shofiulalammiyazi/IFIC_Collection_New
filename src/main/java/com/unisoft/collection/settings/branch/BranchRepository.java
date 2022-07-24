package com.unisoft.collection.settings.branch;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.List;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Long> {

    Branch findFirstByBranchCode(String branchCode);

    @Query("SELECT b FROM Branch b WHERE FUNCTION('TO_NUMBER', b.branchCode) = :branchCode")
    Branch findByNumericBranchCode(@Param("branchCode") int branchCode);

    @Query("SELECT b FROM Branch b WHERE LOWER(b.branchName) = LOWER(:name) ")
    Branch findFirstByBranchNameIgnoreCase(@Param("name") String name);

    List<Branch> findByBranchCodeIn(List<String> branchCodes);


    @Query(value = "" +
            "SELECT DISTINCT B.BRANCH_ID   AS ID, " +
            "                B.BRANCH_CODE AS CODE, " +
            "                B.BRANCH_NAME AS NAME, " +
            "                DE.ID         AS DISTRICT_ID, " +
            "                DE.NAME       AS DISTRICT_NAME, " +
            "                DIV.DIV_ID    AS DIVISION_ID, " +
            "                DIV.DIV_NAME  AS DIVISION_NAME " +
            "FROM LOS_TB_S_BRANCH B " +
            "       JOIN LITIGATION_CASE_INFO LCI ON B.BRANCH_CODE = LCI.BRANCH_CODE " +
            "       LEFT JOIN DISTRICT_ENTITY DE ON B.DISTRICT_ID = DE.ID AND DE.ENABLED = 1 " +
            "       LEFT JOIN DIVISION_ENTITY DIV on DE.DIVISION_ID = DIV.DIV_ID AND DIV.ENABLED = 1 " +
            "WHERE B.ENABLED = 1", nativeQuery = true)
    List<Tuple> getBranchesContainingCases();

}
