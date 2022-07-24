package com.unisoft.collection.settings.dealerGroupBasedOnCollectorAreaForAutoDistribution;


import com.unisoft.common.CommonRepository;
import com.unisoft.common.CommonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DealerGroupBasedOnCollectorAreaForAutoDistributionServiceImpl extends CommonServiceImpl<DealerGroupBasedOnCollectorAreaForAutoDistribution> implements DealerGroupBasedOnCollectorAreaForAutoDistributionService {

    @Autowired
    public DealerGroupBasedOnCollectorAreaForAutoDistributionServiceImpl(CommonRepository<DealerGroupBasedOnCollectorAreaForAutoDistribution> commonRepository) {
        repository = commonRepository;
    }
}
