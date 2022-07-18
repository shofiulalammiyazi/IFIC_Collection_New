package com.csinfotechbd.collection.settings.employeeStatusManagement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeStatusManagerRepository extends JpaRepository<EmployeeStatusManagerEntity, Long> {
    EmployeeStatusManagerEntity findFirstByUserIdOrderByIdDesc(Long id);
}
