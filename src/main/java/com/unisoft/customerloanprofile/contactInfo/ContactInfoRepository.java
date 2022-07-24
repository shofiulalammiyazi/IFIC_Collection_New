package com.unisoft.customerloanprofile.contactInfo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactInfoRepository extends JpaRepository<ContactInfo, Long> {

    @Query(value = "SELECT * FROM CONTACT_INFO WHERE CUSTOMER_ID = ? ", nativeQuery = true)
    List<ContactInfo> findAttemptCallListByCustomerId(Long customerId);

    @Query(value = "SELECT * FROM CONTACT_INFO " +
            "WHERE CUSTOMER_ID = ? and ATTEMPT = 'Phone not received' ", nativeQuery = true)
    List<ContactInfo> findUnAttemptCallListByCustomerId(Long customerId);
}
