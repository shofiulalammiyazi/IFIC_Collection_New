package com.csinfotechbd.collection.samd.setup.sourceofrecoverytools;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface SourceOfRecoveryToolsRepository extends JpaRepository<SourceOfRecoveryTools,Long> {


}
