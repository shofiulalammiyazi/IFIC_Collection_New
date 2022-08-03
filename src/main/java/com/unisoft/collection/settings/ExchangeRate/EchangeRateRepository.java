package com.unisoft.collection.settings.ExchangeRate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EchangeRateRepository extends JpaRepository<ExchangeRateEntity, Long> {
    @Query(value = "select * from EXCHANGE_RATE_ENTITY " +
            "ORDER BY CREATED_DATE DESC FETCH FIRST 1 ROWS  ONLY ", nativeQuery = true)
    ExchangeRateEntity findExchangeRateLastCreated();

    @Query(value = "select * from EXCHANGE_RATE_ENTITY where LATEST = 1", nativeQuery = true)
    ExchangeRateEntity findExchangeRateLatestOld();
}
