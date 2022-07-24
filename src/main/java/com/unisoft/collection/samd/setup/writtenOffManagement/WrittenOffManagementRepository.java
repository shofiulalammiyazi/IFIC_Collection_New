package com.unisoft.collection.samd.setup.writtenOffManagement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface WrittenOffManagementRepository extends JpaRepository<WrittenOffManagement, Long> {
    WrittenOffManagement findWrittenOffManagementByCustomerId(String customerId);
}
