package com.unisoft.collection.accountescalation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountEscalationService {

    @Autowired
    private AccountEscalationRepository accountEscalationRepository;

    @Autowired
    private AccountEscalationHistoryRepository accountEscalationHistoryRepository;

    public List<AccountEscalation> findByToUserPinAndStatus(List<String> pin) {
        return accountEscalationRepository.findByToUserPinAndStatus(pin);
    }

    public AccountEscalation findById(Long id) {
        return accountEscalationRepository.findAccountEscalationById(id);
    }

    public AccountEscalation save(AccountEscalation accountEscalation) {
        return accountEscalationRepository.save(accountEscalation);
    }

    public List<AccountEscalation> findAccountEscalationByDealerPin(String pin) {
        return accountEscalationRepository.findAccountEscalationByDealerPinOrderByCreatedDateDesc(pin);
    }

    public AccountEscalationHistory saveHistory(AccountEscalationHistory accountEscalation) {
        return accountEscalationHistoryRepository.save(accountEscalation);
    }

    public Integer getCountByAccNo(String accNo){
        return accountEscalationRepository.countAllByAndAccountNumber(accNo);
    }


    public List<AccountEscalation> findAccountEscalationByAccountNo(String accountNo) {
        return accountEscalationRepository.findAccountEscalationsByAccountNumber(accountNo);
    }
}
