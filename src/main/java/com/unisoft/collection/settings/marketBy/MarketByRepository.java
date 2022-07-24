package com.unisoft.collection.settings.marketBy;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MarketByRepository extends JpaRepository<MarketByEntity, Long> {
    List<MarketByEntity> findByContractId(String contractId);
}
