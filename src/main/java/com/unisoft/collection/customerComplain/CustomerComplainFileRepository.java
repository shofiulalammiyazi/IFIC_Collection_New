package com.unisoft.collection.customerComplain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerComplainFileRepository extends JpaRepository<CustomerComplainFile, Long> {
    CustomerComplainFile findCustomerComplainFileByDmsFileId(String dmsFileId);
}
