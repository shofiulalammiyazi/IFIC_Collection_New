package com.csinfotechbd.collection.settings.dunningLetterRulesLoan;

import com.csinfotechbd.audittrail.AuditTrailService;
import org.hibernate.envers.Audited;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DunningLetterRulesLoanService {

    @Autowired
    DunningLetterRulesLoanDao dunningLetterRulesLoanDao;
    @Autowired
    AuditTrailService auditTrailService;

    public boolean saveDunningLetterLoanInfo(DunningLetterRulesLoanInfo dunningLetterRulesLoanInfo){
        boolean saveStatus = dunningLetterRulesLoanDao.save(dunningLetterRulesLoanInfo);
        auditTrailService.saveCreatedData("Dunning Letter Rules- Loan", dunningLetterRulesLoanInfo);
        return saveStatus;
    }

    public List<DunningLetterRulesLoanInfo> getDunningLetterRulesLoanInfoList(){
        return dunningLetterRulesLoanDao.getList();
    }

    public DunningLetterRulesLoanInfo getDunningLetterInfo(Long id){
        return dunningLetterRulesLoanDao.getById(id);
    }

    public boolean update(DunningLetterRulesLoanInfo dunningLetterRulesLoanInfo){
        boolean status = dunningLetterRulesLoanDao.update(dunningLetterRulesLoanInfo);
        DunningLetterRulesLoanInfo previous = new DunningLetterRulesLoanInfo();
        auditTrailService.saveUpdatedData("Dunning Letter Rules- Loan",previous, dunningLetterRulesLoanInfo);
        return status;
    }
}

