package com.csinfotechbd.collection.settings.employee;
/*
Created by Monirul Islam at 10/6/2019 
*/

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.Date;
import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeInfoEntity, Long> {
    EmployeeInfoEntity findByPin(String pin);

    List<EmployeeInfoEntity> findByPinNotIn(List<String> pin);

    @Query(value = "select * from EMPLOYEE_INFO_ENTITY where PIN in (select DEALER_PIN from CARD_ACCOUNT_DISTRIBUTION_INFO WHERE CREATED_DATE >=?1 and CREATED_DATE <=?2)", nativeQuery = true)
    List<EmployeeInfoEntity> findByCardDistribution(Date startDate, Date endDate);

    List<EmployeeInfoEntity> findByDesignationNameOrderByUserFirstNameAsc(String designation);

    List<EmployeeInfoEntity> findByEnabled(boolean enabled);

    @Query("SELECT id FROM EmployeeInfoEntity")
    List<Long> findIdList();

    @Query(value = "SELECT EIE.ID, EIE.PIN, LTMU.FIRST_NAME, LTMU.LAST_NAME, DE.NAME AS DESIGNATION, EIE.UNIT " +
            "FROM EMPLOYEE_INFO_ENTITY EIE " +
            "       JOIN DESIGNATION_ENTITY DE on EIE.DESIGNATION_ID = DE.ID AND DE.NAME IN(:designations) " +
            "       JOIN LOS_TB_M_USERS LTMU on EIE.USER_ID = LTMU.USER_ID " +
            "WHERE EIE.ENABLED = 1 " +
            "  AND LOWER(EIE.UNIT) LIKE '%' || LOWER(:unit) || '%' " +
            "  AND EIE.UNIT NOT IN(:exceptionUnits)", nativeQuery = true)
    List<Tuple> findEmployeePinsByUnitAndDesignation(@Param("designations") List<String> designations, @Param("unit") String unit, @Param("exceptionUnits") List<String> exceptionUnits);

    boolean existsByPin(String pin);

    EmployeeInfoEntity findEmployeeInfoEntityByEmail(String email);


    @Query(value = "SELECT * FROM EMPLOYEE_INFO_ENTITY WHERE USER_ID=? ", nativeQuery = true)
    EmployeeInfoEntity findByUserId(Long userId);


    List<EmployeeInfoEntity> findByDesignationNameAndUnitOrderByUserFirstNameAsc(String designation, String unit);

    @Query(value = "SELECT * " +
            "         FROM EMPLOYEE_INFO_ENTITY " +
            "        WHERE DESIGNATION_ID = ?1 " +
            "          AND (UNIT LIKE '%Loan%' OR UNIT LIKE '%Card%')", nativeQuery = true)
    List<EmployeeInfoEntity> findByDesignationIdAndUnit(Long id);
}
