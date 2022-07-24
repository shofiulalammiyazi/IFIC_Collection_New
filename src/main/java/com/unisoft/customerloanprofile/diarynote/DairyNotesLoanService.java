package com.unisoft.customerloanprofile.diarynote;

import com.unisoft.audittrail.AuditTrailService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DairyNotesLoanService {
    @Autowired
    DairyNotesLoanRepository repository;

    @Autowired
    private AuditTrailService auditTrailService;

    public List<DairyNotesLoan> list() {
        return repository.findByEnabledIs(true);
    }

    public List<DairyNotesLoan> findByCustomerId(Long customerId) {
        return repository.findByCustomerBasicInfoId(customerId);
    }

    public DairyNotesLoan findbyId(Long id) {
        return repository.findById(id).get();
    }

    public DairyNotesLoan save(DairyNotesLoan dairyNotesLoan) {
        boolean isNewEntity = true;
        DairyNotesLoan oldEntity = new DairyNotesLoan();
        if (dairyNotesLoan.getId() != null){
            DairyNotesLoan entity = repository.getOne(dairyNotesLoan.getId());
            BeanUtils.copyProperties(entity, oldEntity);
            isNewEntity = false;
        }

        repository.save(dairyNotesLoan);
        if (isNewEntity)
            auditTrailService.saveCreatedData("Diary Notes",  dairyNotesLoan);
        else
            auditTrailService.saveUpdatedData("Diary Notes", oldEntity, dairyNotesLoan);
        return dairyNotesLoan;
    }

    ;

    public boolean delete(Long id) {
        repository.deleteById(id);
        return true;
    }


}
