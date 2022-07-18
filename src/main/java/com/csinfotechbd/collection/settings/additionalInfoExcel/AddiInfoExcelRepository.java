package com.csinfotechbd.collection.settings.additionalInfoExcel;

import com.csinfotechbd.customerloanprofile.AdditionalInfo.AdditionalInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface AddiInfoExcelRepository extends JpaRepository<AdditionalInfo, Long> {

    AdditionalInfo findAdditionalInfoByAccountNo(String accountNo);
}
