package com.unisoft.customerloanprofile.dailynote;

import com.unisoft.customerbasicinfo.CustomerBasicInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface DailyNoteRepository extends JpaRepository<DailyNoteEntity, Long> {

    List<DailyNoteEntity> findDailyNoteEntitiesByAccountNo(String accountNo);

    List<DailyNoteEntity> findByCustomerBasicInfo(CustomerBasicInfoEntity customerBasicInfoEntity);

}
