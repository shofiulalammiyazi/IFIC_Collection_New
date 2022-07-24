package com.unisoft.collection.samd.setup.proposedRescheduleTimes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProposedRescheduleTimesServiceImpl implements ProposedRescheduleTimesService {

    @Autowired
    private ProposedRescheduleTimesRepository proposedRescheduleTimesRepository;

    @Override
    public List<ProposedRescheduleTimes> getAll() {
        return proposedRescheduleTimesRepository.findAll();
    }

    @Override
    public ProposedRescheduleTimes save(ProposedRescheduleTimes proposedRescheduleTimes) {
        return proposedRescheduleTimesRepository.save(proposedRescheduleTimes);
    }

    @Override
    public ProposedRescheduleTimes findProposedRescheduleTimesById(Long id) {
        return proposedRescheduleTimesRepository.findProposedRescheduleTimesById(id);
    }
}
