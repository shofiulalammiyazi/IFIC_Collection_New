package com.unisoft.customerloanprofile.diarynote;

import com.unisoft.collection.dashboard.RfdMenuModel;
import com.unisoft.customerbasicinfo.CustomerBasicInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
@Transactional
public interface DairyNotesLoanRepository extends JpaRepository<DairyNotesLoan,Long> {

    List<DairyNotesLoan> findByEnabled(boolean a);
    List<DairyNotesLoan> findByEnabledIs(boolean a);
    List<DairyNotesLoan> findByCustomerBasicInfo(CustomerBasicInfoEntity customerBasicInfoEntity);
    List<DairyNotesLoan> findByCustomerBasicInfoId(Long customerBasicInfoId);

    //Todo: Check date format of loan_date field 'DD-MM-YYYY' or 'DD/MM/YYYY'

    @Query("select new com.unisoft.collection.dashboard.RfdMenuModel(" +
            "lower(d.loan_menu) , count(distinct d.customerBasicInfo.id)" +
            ") from DairyNotesLoan d " +
            "where TO_DATE(d.loan_date, 'DD/MM/YYYY') >= :startDate and d.customerBasicInfo.id IN(:custIdList) " +
            "group by lower(d.loan_menu)")
    List<RfdMenuModel> findRfdMenuGroup(@Param("custIdList") List<Long> customerIdList, @Param("startDate") Date startDate);

    @Query("select new com.unisoft.collection.dashboard.RfdMenuModel(" +
            "lower(d.loan_submenu_one) , count(distinct d.customerBasicInfo.id)" +
            ") from DairyNotesLoan d " +
            "where TO_DATE(d.loan_date, 'DD/MM/YYYY') >= :startDate and d.customerBasicInfo.id IN(:custIdList) " +
            "group by lower(d.loan_submenu_one)")
    List<RfdMenuModel> findRfdSuBMenu1Group(@Param("custIdList") List<Long> customerIdList, @Param("startDate") Date startDate);

    @Query("select new com.unisoft.collection.dashboard.RfdMenuModel(" +
            "lower(d.loan_submenu_two) , count(distinct d.customerBasicInfo.id)" +
            ") from DairyNotesLoan d " +
            "where TO_DATE(d.loan_date, 'DD/MM/YYYY') >= :startDate and d.customerBasicInfo.id IN(:custIdList) " +
            "group by lower(d.loan_submenu_two)")
    List<RfdMenuModel> findRfdSuBMenu2Group(@Param("custIdList") List<Long> customerIdList, @Param("startDate") Date startDate);

    @Query("select new com.unisoft.collection.dashboard.RfdMenuModel(" +
            "lower(d.loan_submenu_three) , count(distinct d.customerBasicInfo.id)" +
            ") from DairyNotesLoan d " +
            "where TO_DATE(d.loan_date, 'DD/MM/YYYY') >= :startDate and d.customerBasicInfo.id IN(:custIdList) " +
            "group by lower(d.loan_submenu_three)")
    List<RfdMenuModel> findRfdSuBMenu3Group(@Param("custIdList") List<Long> customerIdList, @Param("startDate") Date startDate);

}
