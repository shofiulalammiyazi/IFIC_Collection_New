package com.unisoft.collection.samd.setup.proposedRescheduleTimes;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProposedRescheduleTimesRepository extends JpaRepository<ProposedRescheduleTimes, Long> {
    ProposedRescheduleTimes findProposedRescheduleTimesById(Long id);
}
