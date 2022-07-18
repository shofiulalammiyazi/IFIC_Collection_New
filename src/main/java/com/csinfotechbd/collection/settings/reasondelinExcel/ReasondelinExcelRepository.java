package com.csinfotechbd.collection.settings.reasondelinExcel;

import com.csinfotechbd.collection.reasonDelinquency.ReasonDelinquency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface ReasondelinExcelRepository extends JpaRepository<ReasonDelinquency,Long> {

    ReasonDelinquency findReasonDelinquencyByAccountNo(String accountNo);
}
