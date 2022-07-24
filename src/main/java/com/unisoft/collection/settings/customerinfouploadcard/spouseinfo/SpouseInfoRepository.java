package com.unisoft.collection.settings.customerinfouploadcard.spouseinfo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpouseInfoRepository extends JpaRepository<SpouseInfo,Long> {

    SpouseInfo findByContractId(String contractId);
}
