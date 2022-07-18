package com.csinfotechbd.collection.samd.setup.proposedRescheduleTimes;

import java.util.List;

public interface ProposedRescheduleTimesService {
    List<ProposedRescheduleTimes> getAll();

    ProposedRescheduleTimes save(ProposedRescheduleTimes proposedRescheduleTimes);

    ProposedRescheduleTimes findProposedRescheduleTimesById(Long id);
}
