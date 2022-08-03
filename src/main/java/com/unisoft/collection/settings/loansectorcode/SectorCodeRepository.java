package com.unisoft.collection.settings.loansectorcode;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface SectorCodeRepository extends JpaRepository<SectorCodeEntity,Long> {


    @Query(value = "select * from SECTOR_CODE_ENTITY WHERE ACCOUNT_NO = ? ", nativeQuery = true)
    SectorCodeEntity findSectorCodeEntityByAccountNo(String accountNo);
}
