package com.csinfotechbd.collection.accountescalation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountEscalationNoteService {

    @Autowired
    private AccountEscalationNoteRepository accountEscalationNoteRepository;

    public AccountEscalationNote save(AccountEscalationNote accountEscalationNote) {
        return accountEscalationNoteRepository.save(accountEscalationNote);
    }

    public List<AccountEscalationNote> getAccountEscalationNoteByAccountEscalationId(Long accountEscalationId) {
        return accountEscalationNoteRepository.findAccountEscalationNoteByAccountEscalationId(accountEscalationId);
    }

    public AccountEscalationNote findAccountEscalationNoteByCreatedBy(String createdBy) {
        return accountEscalationNoteRepository.findAccountEscalationNoteByCreatedBy(createdBy);
    }
}
