package com.unisoft.collection.samd.setup.riskCategories;


import com.unisoft.audittrail.AuditTrailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RiskCategoryServiceImpl implements RiskCategoryService{

    private final RiskCategoryRepository riskCategoryRepository;
    private final AuditTrailService auditTrailService;

    @Override
    public RiskCategory saveRiskCategory(RiskCategory riskCategory) {
        boolean isNewEntity = false;
        RiskCategory previousCategory = new RiskCategory();

        if (riskCategory.getId() == null)
            isNewEntity = true;

        if (!isNewEntity){
            RiskCategory oldEntity = riskCategoryRepository.getOne(riskCategory.getId());
            BeanUtils.copyProperties(oldEntity, previousCategory);
        }

        riskCategoryRepository.save(riskCategory);

        if (isNewEntity)
            auditTrailService.saveCreatedData("Risk Categories", riskCategory);
        else
            auditTrailService.saveUpdatedData("Risk Categories", previousCategory, riskCategory);

        return riskCategory;
    }

    @Override
    public RiskCategory findRiskCategoryById(Long id) {
        return riskCategoryRepository.findRiskCategoryById(id);
    }

    @Override
    public List<RiskCategory> findAll() {
        return riskCategoryRepository.findAll();
    }

    public List<RiskCategory> findByAllCard(String name){
        return riskCategoryRepository.findByUnitName(name);
    }

    public RiskCategory getById(Long id) {
        return riskCategoryRepository.findById(id).orElse(new RiskCategory());
    }

}
