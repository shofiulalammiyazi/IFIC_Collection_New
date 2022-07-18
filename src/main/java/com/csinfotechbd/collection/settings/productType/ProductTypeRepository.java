package com.csinfotechbd.collection.settings.productType;
/*
Created by Monirul Islam at 9/11/2019 
*/

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ProductTypeRepository extends JpaRepository<ProductTypeEntity, Long> {
    List<ProductTypeEntity> findByProductGroupEntityCardAccount(String cardAccount);
    List<ProductTypeEntity> findByName(String name);
    ProductTypeEntity findByCode(String name);
    List<ProductTypeEntity> findByEnabled(boolean enabled);

    @Query(value = "select * from PRODUCT_TYPE_ENTITY where CODE in (select distinct(PRODUCT_GROUP) from CARD_ACCOUNT_DISTRIBUTION_INFO where DEALER_PIN= ?1 and CREATED_DATE >=?2 and CREATED_DATE <=?3)", nativeQuery = true)
    List<ProductTypeEntity> findByDelaerPin(String dealerPin, Date startDate, Date endDate);

}
