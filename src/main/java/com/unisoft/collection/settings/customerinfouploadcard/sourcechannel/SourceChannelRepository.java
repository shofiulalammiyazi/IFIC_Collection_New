package com.unisoft.collection.settings.customerinfouploadcard.sourcechannel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SourceChannelRepository extends JpaRepository<SourceChannel,Long> {

    SourceChannel findByContractId(String contractId);
}