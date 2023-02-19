package com.unisoft.collection.settings.visitLedger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VisitLedgerService {

    @Autowired
    private VisitLedgerDao visitLedgerDao;
    @Autowired
    private VisitLedgerRepository visitLedgerRepository;

    public List<VisitLedgerEntity> getVisitLedgerList() {
        return visitLedgerRepository.findAll();
    }

    public boolean saveVisitLedger(VisitLedgerEntity visitLedgerEntity) {
        return visitLedgerDao.save(visitLedgerEntity);
    }

    public VisitLedgerEntity getByID(Long id) {
        return visitLedgerDao.getById(id);
    }
    public boolean updateVisitLedger (VisitLedgerEntity visitLedgerEntity) {
        return visitLedgerDao.updateVisitLedger(visitLedgerEntity);
    }

    public List<VisitLedgerEntity> getActiveList() {
        return visitLedgerDao.getActiveOnly();
    }

    public List<VisitLedgerEntity> findVisitLedgerEntityByCreatedByAndStatus(String pin) {
        return visitLedgerRepository.findVisitLedgerEntityByCreatedByAndStatus(pin);
    }

    public VisitLedgerEntity findVisitLedgerEntityById(Long id) {
        return visitLedgerRepository.findVisitLedgerEntityById(id);
    }

    public VisitLedgerEntity save(VisitLedgerEntity visitLedgerEntity) {
        return visitLedgerRepository.save(visitLedgerEntity);
    }

    public List<VisitLedgerEntity> findVisitLedgerEntityByAccountCardNumberAndStatus(String accountNo) {
        return visitLedgerRepository.findVisitLedgerEntityByAccountCardNumberAndStatus(accountNo);
    }

    public List<VisitLedgerEntity> findVisitLedgerEntityByCreatedByAndStatusForSupervisor(String pin) {
        return visitLedgerRepository.findVisitLedgerEntityByCreatedByAndStatusForSuperVisor(pin);
    }
}
