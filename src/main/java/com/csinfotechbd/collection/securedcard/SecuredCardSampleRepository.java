package com.csinfotechbd.collection.securedcard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecuredCardSampleRepository extends JpaRepository<SecuredCardSample, Long> {
    SecuredCardSample findFirstBySecuredCard(String securecard);
}
