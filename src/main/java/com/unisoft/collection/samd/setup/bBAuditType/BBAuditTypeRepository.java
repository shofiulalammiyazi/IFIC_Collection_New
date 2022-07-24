package com.unisoft.collection.samd.setup.bBAuditType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BBAuditTypeRepository extends JpaRepository<BBAuditType, Long> {


    BBAuditType findBBAuditTypeById(Long id);
}
