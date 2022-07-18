package com.csinfotechbd.collection.settings.productGroup;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductGroupRepository extends JpaRepository<ProductGroupEntity, Long> {

    ProductGroupEntity findByName(String name);

    List<ProductGroupEntity> findByCardAccount(String name);


//    @Query("SELECT name FROM ProductGroupEntity WHERE enabled = :enabled")
//    List<String> findProductNamesByEnabled(@Param("enabled") boolean enabled);
}
