package com.csinfotechbd.collection.samd.setup.temporaryjobdelegation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TemporaryJobDelegationRepository extends JpaRepository<TemporaryJobDelegation, Long> {
    TemporaryJobDelegation findTemporaryJobDelegationById(Long id);
}
