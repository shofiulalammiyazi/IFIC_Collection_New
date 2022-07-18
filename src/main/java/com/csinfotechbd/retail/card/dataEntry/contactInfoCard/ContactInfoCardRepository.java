package com.csinfotechbd.retail.card.dataEntry.contactInfoCard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactInfoCardRepository extends JpaRepository<ContactInfoCard,Long> {

    @Query(value = "SELECT * FROM CONTACT_INFO_CARD WHERE CUSTOMER_ID=?", nativeQuery = true)
    List<ContactInfoCard> findAttemptCallListByCustomerId(Long customerId);

    @Query(value = "SELECT * FROM CONTACT_INFO_CARD WHERE CUSTOMER_ID=? AND ATTEMPT = 'Phone not received'",nativeQuery = true)
    List<ContactInfoCard>findUnAttemptCallListByCustomerId(Long customerId);
}
