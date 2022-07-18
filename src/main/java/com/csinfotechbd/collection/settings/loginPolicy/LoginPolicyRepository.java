package com.csinfotechbd.collection.settings.loginPolicy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginPolicyRepository extends JpaRepository<LoginPolicyEntity, Long> {

}
