package com.unisoft.retail.loan.setup.parReleaseLoan;
/*
 * Created by    on 25 April, 2021
 */

import com.unisoft.audittrail.AuditTrailService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParReleaseServiceImpl implements ParReleaseLoanService {

    @Autowired
    private ParReleaseLoanRepository parReleaseLoanRepository;

    @Autowired
    private AuditTrailService auditTrailService;


    @Override
    public ParReleaseLoan getPAR() {
        List<ParReleaseLoan> list = parReleaseLoanRepository.findAll();
        return list.isEmpty() ? new ParReleaseLoan() : list.get(0);
    }


    @Override
    public List<ParReleaseLoan> findAll() {
        return parReleaseLoanRepository.findAll();
    }

    @Override
    public ParReleaseLoan save(ParReleaseLoan parReleaseLoan) {
        boolean isNewEntity = true;
        ParReleaseLoan previousEntity = new ParReleaseLoan();
        if (parReleaseLoan.getId() != null){
            ParReleaseLoan oldEntity = parReleaseLoanRepository.getOne(parReleaseLoan.getId());
            BeanUtils.copyProperties(oldEntity, previousEntity);
            isNewEntity = false;
        }

        parReleaseLoanRepository.save(parReleaseLoan);

        if (isNewEntity)
            auditTrailService.saveCreatedData("SAMD Par Release Loan", parReleaseLoan);
        else
            auditTrailService.saveUpdatedData("SAMD Par Release Loan", previousEntity, parReleaseLoan);

        return parReleaseLoan;
    }

    @Override
    public ParReleaseLoan findParReleaseLoanById(Long id) {
        ParReleaseLoan parReleaseLoan = parReleaseLoanRepository.findParReleaseLoanById(id);
        return parReleaseLoan;
    }
}
