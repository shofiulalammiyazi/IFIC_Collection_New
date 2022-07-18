package com.csinfotechbd.collection.settings.producttypecard;
/*
Created by Monirul Islam at 9/11/2019 
*/

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ProductTypeCardRepository extends JpaRepository<ProductTypeCardEntity, Long> {
    List<ProductTypeCardEntity> findByProductGroupEntityCardAccount(String cardAccount);
    List<ProductTypeCardEntity> findByName(String name);
    ProductTypeCardEntity findByCode(String name);
    List<ProductTypeCardEntity> findByEnabled(boolean enabled);

    @Query(value = "select * from PRODUCT_TYPE_ENTITY where CODE in (select distinct(PRODUCT_GROUP) from CARD_ACCOUNT_DISTRIBUTION_INFO where DEALER_PIN= ?1 and CREATED_DATE >=?2 and CREATED_DATE <=?3)", nativeQuery = true)
    List<ProductTypeCardEntity> findByDelaerPin(String dealerPin, Date startDate, Date endDate);

}
