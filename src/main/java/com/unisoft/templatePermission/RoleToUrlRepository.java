package com.unisoft.templatePermission;


import com.unisoft.role.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleToUrlRepository extends JpaRepository<RoleToUrl, Long> {
    RoleToUrl findByRole(Role role);
}
