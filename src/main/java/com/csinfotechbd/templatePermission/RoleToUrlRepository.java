package com.csinfotechbd.templatePermission;
/*
Created by Monirul Islam at 9/29/2019 
*/

import com.csinfotechbd.role.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleToUrlRepository extends JpaRepository<RoleToUrl, Long> {
    RoleToUrl findByRole(Role role);
}
