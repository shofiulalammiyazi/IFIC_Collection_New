package com.unisoft.retail.card.dataEntry.dairynotes;

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
public interface DairyNotesRepository extends JpaRepository<DairyNotes,Long> {

    List<DairyNotes> findByEnabled(boolean a);
    List<DairyNotes> findByEnabledIs(boolean a);
    List<DairyNotes> findByCustomerBasicInfoId(Long customerBasicInfoId);
    List<DairyNotes> findByCustomerBasicInfo(CustomerBasicInfoEntity customerBasicInfoEntity);

    @Query("select new com.unisoft.collection.dashboard.RfdMenuModel(" +
            "lower(d.card_menu) , count(distinct d.customerBasicInfo.id)" +
            ") from DairyNotes d " +
            "where TO_DATE(d.card_date, 'DD/MM/YYYY') >= :startDate and d.customerBasicInfo.id IN(:custIdList) " +
            "group by lower(d.card_menu)")
    List<RfdMenuModel> findRfdMenuGroup(@Param("custIdList") List<Long> customerIdList, @Param("startDate") Date startDate);

    @Query("select new com.unisoft.collection.dashboard.RfdMenuModel(" +
            "lower(d.card_submenu_one) , count(distinct d.customerBasicInfo.id)" +
            ") from DairyNotes d " +
            "where TO_DATE(d.card_date, 'DD/MM/YYYY') >= :startDate and d.customerBasicInfo.id IN(:custIdList) " +
            "group by lower(d.card_submenu_one)")
    List<RfdMenuModel> findRfdSuBMenu1Group(@Param("custIdList") List<Long> customerIdList, @Param("startDate") Date startDate);

    @Query("select new com.unisoft.collection.dashboard.RfdMenuModel(" +
            "lower(d.card_submenu_two) , count(distinct d.customerBasicInfo.id)" +
            ") from DairyNotes d " +
            "where TO_DATE(d.card_date, 'DD/MM/YYYY') >= :startDate and d.customerBasicInfo.id IN(:custIdList) " +
            "group by lower(d.card_submenu_two)")
    List<RfdMenuModel> findRfdSuBMenu2Group(@Param("custIdList") List<Long> customerIdList, @Param("startDate") Date startDate);

    @Query("select new com.unisoft.collection.dashboard.RfdMenuModel(" +
            "lower(d.card_submenu_three) , count(distinct d.customerBasicInfo.id)" +
            ") from DairyNotes d " +
            "where TO_DATE(d.card_date, 'DD/MM/YYYY') >= :startDate and d.customerBasicInfo.id IN(:custIdList) " +
            "group by lower(d.card_submenu_three)")
    List<RfdMenuModel> findRfdSuBMenu3Group(@Param("custIdList") List<Long> customerIdList, @Param("startDate") Date startDate);


}
