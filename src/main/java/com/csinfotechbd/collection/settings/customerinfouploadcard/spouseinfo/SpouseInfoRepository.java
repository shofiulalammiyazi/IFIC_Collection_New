package com.csinfotechbd.collection.settings.customerinfouploadcard.spouseinfo;

import com.csinfotechbd.customerloanprofile.referenceinfo.ReferenceInfoEntity;
import com.csinfotechbd.customerloanprofile.referenceinfo.ReferenceInfoStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpouseInfoRepository extends JpaRepository<SpouseInfo,Long> {

    SpouseInfo findByContractId(String contractId);
}
