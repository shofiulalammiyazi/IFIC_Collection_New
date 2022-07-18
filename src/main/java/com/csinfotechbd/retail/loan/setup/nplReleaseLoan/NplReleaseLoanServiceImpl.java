package com.csinfotechbd.retail.loan.setup.nplReleaseLoan;
/*
Created by Monirul Islam on 7/10/2019
*/

import com.csinfotechbd.audittrail.AuditTrailService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NplReleaseLoanServiceImpl implements NplReleaseLoanService {

    @Autowired
    private NplReleaseLoanRepository nplReleaseLoanRepository;

    @Autowired
    private AuditTrailService auditTrailService;

    public NplReleaseLoan getNPL() {
        List<NplReleaseLoan> list = nplReleaseLoanRepository.findAll();
        return list.isEmpty() ? new NplReleaseLoan() : list.get(0);
    }

    @Override
    public List<NplReleaseLoan> findAll() {
        return nplReleaseLoanRepository.findAll();
    }

    @Override
    public NplReleaseLoan save(NplReleaseLoan nplReleaseLoan) {
        boolean isNewEntity = true;
        NplReleaseLoan previousEntity = new NplReleaseLoan();
        if (nplReleaseLoan.getId() != null){
            NplReleaseLoan oldEntity = nplReleaseLoanRepository.getOne(nplReleaseLoan.getId());
            BeanUtils.copyProperties(oldEntity, previousEntity);
            isNewEntity = false;
        }

        nplReleaseLoanRepository.save(nplReleaseLoan);

        if (isNewEntity)
            auditTrailService.saveCreatedData("SAMD Npl Release Loan", nplReleaseLoan);
        else
            auditTrailService.saveUpdatedData("SAMD Npl Release Loan", previousEntity, nplReleaseLoan);

        return nplReleaseLoan;
    }

    @Override
    public NplReleaseLoan findNplReleaseLoanById(Long id) {
        NplReleaseLoan nplReleaseLoan = nplReleaseLoanRepository.findNplReleaseLoanById(id);
        return nplReleaseLoan;
    }
}
