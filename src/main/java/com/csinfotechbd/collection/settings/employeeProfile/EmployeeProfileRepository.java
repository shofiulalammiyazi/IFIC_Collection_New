package com.csinfotechbd.collection.settings.employeeProfile;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeProfileRepository extends JpaRepository<EmployeeProfileInfo, Long> {

    List<EmployeeProfileInfo> findByEnabled(boolean enabled);

}
