package com.unisoft.collection.settings.dlgExceptionRulesLoan;


import com.unisoft.audittrail.AuditTrailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DlgExceptionRulesLoanService {

    @Autowired
    private AuditTrailService auditTrailService;
    @Autowired
    private DlgExceptionRulesLoanDao dlgExceptionRulesLoanDao;

    public DlgExceptionRulesLoanInfo getDlgExceptionRulesLoanInfo() {
        return dlgExceptionRulesLoanDao.getDlgExceptionRulesLoanInfo();
    }

    public boolean saveDlgExceptionRulesLoan(DlgExceptionRulesLoanInfo dlgExceptionRulesLoanInfo)
    {
        auditTrailService.saveCreatedData("Dunning letter generate exception rule:Loan", dlgExceptionRulesLoanInfo);
        return dlgExceptionRulesLoanDao.saveDlgExceptionRulesLoanInfo(dlgExceptionRulesLoanInfo);
    }

    public boolean updateDlgExceptionRulesLoan(DlgExceptionRulesLoanInfo dlgExceptionRulesLoanInfo)
    {
        DlgExceptionRulesLoanInfo previousDate = new DlgExceptionRulesLoanInfo();
        auditTrailService.saveUpdatedData("Dunning letter generate exception rule:Loan", previousDate,dlgExceptionRulesLoanInfo);
        return dlgExceptionRulesLoanDao.updateDlgExceptionRulesLoanInfo(dlgExceptionRulesLoanInfo);
    }
}
