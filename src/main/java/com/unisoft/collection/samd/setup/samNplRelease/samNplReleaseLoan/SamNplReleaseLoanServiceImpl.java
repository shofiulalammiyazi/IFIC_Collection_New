package com.unisoft.collection.samd.setup.samNplRelease.samNplReleaseLoan;

import com.unisoft.audittrail.AuditTrailService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SamNplReleaseLoanServiceImpl implements SamNplReleaseLoanService{

    @Autowired
    private SamNplReleaseLoanRepository samNplReleaseLoanRepository;

    @Autowired
    private AuditTrailService auditTrailService;

    @Override
    public List<SamNplReleaseLoan> findAll() {
        return samNplReleaseLoanRepository.findAll();
    }

    @Override
    public SamNplReleaseLoan save(SamNplReleaseLoan samNplReleaseLoan) {
        boolean isNewEntity = true;
        SamNplReleaseLoan previousEntity = new SamNplReleaseLoan();
        if (samNplReleaseLoan.getId() != null){
            SamNplReleaseLoan oldEntity = samNplReleaseLoanRepository.getOne(samNplReleaseLoan.getId());
            BeanUtils.copyProperties(oldEntity, previousEntity);
            isNewEntity = false;
        }

        samNplReleaseLoanRepository.save(samNplReleaseLoan);

        if (isNewEntity)
            auditTrailService.saveCreatedData("SAMD Npl Release Loan", samNplReleaseLoan);
        else
            auditTrailService.saveUpdatedData("SAMD Npl Release Loan", previousEntity, samNplReleaseLoan);

        return samNplReleaseLoan;
    }

    @Override
    public SamNplReleaseLoan findSamNplReleaseLoanById(Long id) {
        SamNplReleaseLoan samNplReleaseLoan = samNplReleaseLoanRepository.findSamNplReleaseLoanById(id);
        return samNplReleaseLoan;
    }
}
