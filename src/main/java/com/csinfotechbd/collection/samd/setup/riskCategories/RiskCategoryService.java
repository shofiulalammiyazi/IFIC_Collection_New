package com.csinfotechbd.collection.samd.setup.riskCategories;

import java.util.List;

public interface RiskCategoryService {
    RiskCategory saveRiskCategory(RiskCategory riskCategory);

    RiskCategory findRiskCategoryById(Long id);

    List<RiskCategory> findAll();
}
