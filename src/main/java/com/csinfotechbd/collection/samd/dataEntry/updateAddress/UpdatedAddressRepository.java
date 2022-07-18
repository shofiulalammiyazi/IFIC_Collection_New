package com.csinfotechbd.collection.samd.dataEntry.updateAddress;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UpdatedAddressRepository extends JpaRepository<UpdateAddress, Long> {

    @Query(value = "SELECT * FROM UPDATE_ADDRESS WHERE CUSTOMER_ID = ? ", nativeQuery = true)
    UpdateAddress findUpdateAddressByCustomerId(String customerId);


    UpdateAddress findUpdateAddressById(Long id);
}
