package com.csinfotechbd.collection.settings.customerinfouploadcard.sourcechannel;

import com.csinfotechbd.customerloanprofile.referenceinfo.ReferenceInfoEntity;
import com.csinfotechbd.customerloanprofile.referenceinfo.ReferenceInfoStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SourceChannelRepository extends JpaRepository<SourceChannel,Long> {

    SourceChannel findByContractId(String contractId);
}