package com.unisoft.reports.retail.loan.vipListReport;

import com.unisoft.collection.settings.vipStatus.VipStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VipListReportRepository extends JpaRepository<VipStatus, Long> {

    @Query("SELECT new com.unisoft.reports.retail.loan.vipListReport.VipListReportDto(" +
            "hn.customerBasicInfo.accountNo, " +
            "hn.customerBasicInfo.customerName, " +
            "hn.customerBasicInfo.companyName, " +
            "hn.hotNote) " +
            "FROM HotNoteEntity hn " +
            "WHERE LOWER(hn.vipStatus) = LOWER(:status) AND hn.statusFlag = true")
    List<VipListReportDto> getVipListByVipStatus(@Param("status") String vipStatus);

}
