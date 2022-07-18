package com.csinfotechbd.legal.dataEntry.fileFollowUp;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.history.Revision;

import java.util.List;

public interface LitigationFileFollowUpService {

    LitigationFileFollowUpDto getAccountInfoFromApi(String accountNo);

    String save(LitigationFileFollowUp litigationFileFollowUp);

    List<LitigationFileFollowUp> findAll();

    List<LitigationFileFollowUp> findByEnabled(boolean enabled);

    Page<LitigationFileFollowUp> findAll(Pageable pageable);

    LitigationFileFollowUp findById(Long id);

    Page<LitigationFileFollowUp> getLitigationFileFollowUpByAccountNumber(String accountNo, Pageable pageable);

    List<LitigationFileFollowUp> getByAccountNumber(String accountNo);

    Page<Revision<Integer, LitigationFileFollowUp>> getRevisions(Long id, Pageable pageable);

    List<LitigationFileFollowUp> getRevisions(Long id);
}
