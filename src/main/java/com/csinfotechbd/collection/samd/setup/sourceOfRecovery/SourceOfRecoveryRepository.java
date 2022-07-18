package com.csinfotechbd.collection.samd.setup.sourceOfRecovery;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SourceOfRecoveryRepository extends JpaRepository<SourceOfRecovery, Long> {

    SourceOfRecovery findSourceOfRecoveryById(Long id);
}
