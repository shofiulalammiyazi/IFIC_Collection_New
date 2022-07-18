package com.csinfotechbd.collection.settings.jobRole;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRoleRepository extends JpaRepository<JobRoleEntity, Long> {
    JobRoleEntity findByName(String name);

    List<JobRoleEntity> findByEnabled(boolean enaled);

    boolean existsByCode(String code);
}
