package com.unisoft.collection.distribution.card;
/*
Created by   Islam at 9/25/2019
*/

import com.unisoft.customerbasicinfo.CustomerBasicInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountLocationMappingRepository extends JpaRepository<AccountLocationMapping, Long> {

    AccountLocationMapping findFirstByCustomer(CustomerBasicInfoEntity customerBasicInfoEntity);
}
