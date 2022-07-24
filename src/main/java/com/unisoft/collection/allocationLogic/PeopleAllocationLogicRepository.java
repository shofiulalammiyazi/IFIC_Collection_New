package com.unisoft.collection.allocationLogic;
/*
Created by   Islam at 8/28/2019
*/

import com.unisoft.collection.settings.agency.AgencyEntity;
import com.unisoft.collection.settings.employee.EmployeeInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.List;

@Repository
public interface PeopleAllocationLogicRepository extends JpaRepository<PeopleAllocationLogicInfo, Long> {

    PeopleAllocationLogicInfo findByDealerAndUnit(EmployeeInfoEntity employeeInfoEntity, String unit);

    PeopleAllocationLogicInfo findByDealerAndUnitAndDistributionType(EmployeeInfoEntity employeeInfoEntity, String unit, String distributionType);

    List<PeopleAllocationLogicInfo> findByUnitAndDistributionType(String unit, String distributionType);

    PeopleAllocationLogicInfo findFirstByDealerAndUnitOrderByCreatedDateDesc(EmployeeInfoEntity employeeInfoEntity, String unit);

    PeopleAllocationLogicInfo findFirstByManager(EmployeeInfoEntity employeeInfoEntity);

    PeopleAllocationLogicInfo findFirstByTeamlead(EmployeeInfoEntity employeeInfoEntity);

    @Query("select new com.unisoft.collection.allocationLogic.DealerViewModel(pl.dealer.pin, pl.dealer.user.firstName || ' ' || pl.dealer.user.lastName) " +
            "from people_allocation_logic pl where pl.teamlead.pin in (:list)")
    List<DealerViewModel> findByMyTeamlead(@Param(value = "list") List<String> teamLeadList);

    @Query(value = "SELECT U_TL.USERNAME PIN, MAX(U_TL.FIRST_NAME || ' ' || U_TL.LAST_NAME) NAME " +
            "FROM PEOPLE_ALLOCATION_LOGIC PAL " +
            "       JOIN EMPLOYEE_INFO_ENTITY EIE_SU ON PAL.SUPERVISOR_ID = EIE_SU.ID AND EIE_SU.PIN IN (:pinList) " +
            "       JOIN LOS_TB_M_USERS U_SU ON EIE_SU.PIN = U_SU.USERNAME " +
            "       JOIN EMPLOYEE_INFO_ENTITY EIE_TL ON PAL.TEAM_LEAD_ID = EIE_TL.ID " +
            "       JOIN LOS_TB_M_USERS U_TL ON EIE_TL.PIN = U_TL.USERNAME " +
            "GROUP BY U_TL.USERNAME", nativeQuery = true)
    List<Tuple> findTeamLeadBySupervisors(@Param(value = "pinList") List<String> pinList);

    @Query(value = "SELECT distinct SUPERVISOR_ID FROM EMPLOYEE_INFO_ENTITY " +
            "LEFT JOIN PEOPLE_ALLOCATION_LOGIC L on L.SUPERVISOR_ID = EMPLOYEE_INFO_ENTITY.ID " +
            "    WHERE TEAM_LEAD_ID = ? ", nativeQuery = true)
    Long getSupperVisorIdByTeamleader(Long id);


    @Query(value = "SELECT distinct MANAGER_ID FROM EMPLOYEE_INFO_ENTITY " +
            "LEFT JOIN PEOPLE_ALLOCATION_LOGIC L on L.MANAGER_ID = EMPLOYEE_INFO_ENTITY.ID " +
            "WHERE SUPERVISOR_ID = ? ", nativeQuery = true)
    Long getManagerIdBySuperVisorId(Long id);

    @Query(value = "SELECT * FROM PEOPLE_ALLOCATION_LOGIC WHERE TEAM_LEAD_ID=? ", nativeQuery = true)
    List<PeopleAllocationLogicInfo> findListByTeamleaderId(Long id);

    List<PeopleAllocationLogicInfo> findTeamLeadBySupervisorId(Long id);

    @Query(value = "SELECT * FROM PEOPLE_ALLOCATION_LOGIC WHERE MANAGER_ID = ? AND UNIT='SAM Loan' ", nativeQuery = true)
    List<PeopleAllocationLogicInfo> getSamPeopleAllocationLogicInfoListByManagerId(Long id);

    @Query(value = "SELECT DISTINCT TEAM_LEAD_ID AS teamlederId FROM PEOPLE_ALLOCATION_LOGIC WHERE SUPERVISOR_ID = ? AND UNIT = ? ", nativeQuery = true)
    List<Tuple> findSupervisorWiseTeamleader(Long id, String unit);

    @Query(value = "SELECT DEALER_ID AS dealerId FROM PEOPLE_ALLOCATION_LOGIC WHERE TEAM_LEAD_ID = ? AND UNIT = ? ", nativeQuery = true)
    List<Tuple> findTeamleaderWiseDealer(Long id, String unit);


    PeopleAllocationLogicInfo findPeopleAllocationLogicInfoByAgency(AgencyEntity agencyEntity);

    @Query(value = "SELECT * FROM PEOPLE_ALLOCATION_LOGIC WHERE MANAGER_ID = ? AND UNIT = ? ", nativeQuery = true)
    List<PeopleAllocationLogicInfo> findByManagerIdAndUnit(Long id, String unit);

    @Query(value = "WITH dealerIdList AS (SELECT PAL.DEALER_ID " +
            "FROM EMPLOYEE_INFO_ENTITY EIE " +
            "       LEFT JOIN PEOPLE_ALLOCATION_LOGIC PAL ON PAL.DEALER_ID = EIE.ID " +
            "           OR PAL.TEAM_LEAD_ID = EIE.ID " +
            "           OR PAL.SUPERVISOR_ID = EIE.ID " +
            "           OR PAL.MANAGER_ID = EIE.ID " +
            "WHERE EIE.PIN = :pin) " +
            "SELECT EIE2.PIN FROM EMPLOYEE_INFO_ENTITY EIE2 WHERE EIE2.ID IN (SELECT * FROM dealerIdList)", nativeQuery = true)
    List<String> getAllDealerPinByAnyPin(@Param("pin") String pin);


    @Query(value = "SELECT U_TL.USERNAME PIN, MAX(U_TL.FIRST_NAME || ' ' || U_TL.LAST_NAME) NAME " +
            "FROM PEOPLE_ALLOCATION_LOGIC PAL " +
            "       JOIN EMPLOYEE_INFO_ENTITY EIE_SU ON PAL.MANAGER_ID = EIE_SU.ID AND EIE_SU.PIN IN (:pinList) " +
            "       JOIN LOS_TB_M_USERS U_SU ON EIE_SU.PIN = U_SU.USERNAME " +
            "       JOIN EMPLOYEE_INFO_ENTITY EIE_TL ON PAL.TEAM_LEAD_ID = EIE_TL.ID " +
            "       JOIN LOS_TB_M_USERS U_TL ON EIE_TL.PIN = U_TL.USERNAME " +
            "GROUP BY U_TL.USERNAME", nativeQuery = true)
    List<Tuple> findSupervisorsByManager(@Param(value = "pinList") List<String> pinList);


    @Query(value = "SELECT U_TL.USERNAME AS PIN, MAX(U_TL.FIRST_NAME || ' ' || U_TL.LAST_NAME) NAME " +
            "         FROM EMPLOYEE_INFO_ENTITY EIE " +
            "         JOIN LOS_TB_M_USERS U_TL ON EIE.PIN = U_TL.USERNAME " +
            "        WHERE EIE.UNIT IN ('Card','Loan') " +
            "        GROUP BY U_TL.USERNAME", nativeQuery = true)
    List<Tuple> getAllSupervisorDoc();
}
