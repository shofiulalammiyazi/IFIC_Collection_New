package com.unisoft.collection.samd.dataEntry.monitoringFollowUp;

import com.unisoft.customerbasicinfo.CustomerBasicInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.List;


@Repository
public interface MonitoringFollowUpRepository extends JpaRepository<MonitoringAndFollowUp, Long> {


    List<MonitoringAndFollowUp> findMonitoringFollowUpsByCustomerBasicInfo(CustomerBasicInfoEntity customerBasicInfoEntity);

    @Query(value = "SELECT MAFU.ID                  as monitoringId, " +
            "       MAFU.BORROWER_NATURE     as borrowerNature, " +
            "       MAFU.NO_TE_TDRCRS        as noTeTdRCRs, " +
            "       MAFU.PRO_RE_CURRENT_YEAR as proReCurrentYear, " +
            "       MFUF.DMS_FILE_ID         as dmsFileId, " +
            "       MFUF.DMS_FILE_TYPE       as dmsFileType, " +
            "       MFUF.FILE_NAME           as fileName " +
            "FROM MONITORING_AND_FOLLOW_UP MAFU " +
            "            LEFT JOIN MONITORING_FOLLOW_UP_FILE MFUF on MAFU.ID = MFUF.MONITORING_FOLLOW_UP_ID " +
            "WHERE MAFU.CUSTOMER_ID = ? ", nativeQuery = true)
    List<Tuple> findMonitoringAndFollowUpByCustomerId(String customerId);


    MonitoringAndFollowUp findMonitoringAndFollowUpById(Long id);
}
