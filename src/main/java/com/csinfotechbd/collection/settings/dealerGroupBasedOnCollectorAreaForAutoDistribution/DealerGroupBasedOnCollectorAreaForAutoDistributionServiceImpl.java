package com.csinfotechbd.collection.settings.dealerGroupBasedOnCollectorAreaForAutoDistribution;


import com.csinfotechbd.common.CommonRepository;
import com.csinfotechbd.common.CommonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DealerGroupBasedOnCollectorAreaForAutoDistributionServiceImpl extends CommonServiceImpl<DealerGroupBasedOnCollectorAreaForAutoDistribution> implements DealerGroupBasedOnCollectorAreaForAutoDistributionService {

    @Autowired
    public DealerGroupBasedOnCollectorAreaForAutoDistributionServiceImpl(CommonRepository<DealerGroupBasedOnCollectorAreaForAutoDistribution> commonRepository) {
        repository = commonRepository;
    }
}
