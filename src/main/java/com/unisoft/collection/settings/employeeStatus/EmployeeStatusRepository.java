package com.unisoft.collection.settings.employeeStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeStatusRepository extends JpaRepository<EmployeeStatusEntity, Long> {
    EmployeeStatusEntity findByName(String name);
}
