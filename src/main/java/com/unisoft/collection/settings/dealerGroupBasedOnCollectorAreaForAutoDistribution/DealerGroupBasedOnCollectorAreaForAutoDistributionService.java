package com.unisoft.collection.settings.dealerGroupBasedOnCollectorAreaForAutoDistribution;

import com.unisoft.common.CommonService;

import java.util.List;

public interface DealerGroupBasedOnCollectorAreaForAutoDistributionService extends CommonService<DealerGroupBasedOnCollectorAreaForAutoDistribution> {
    List<DealerGroupBasedOnCollectorAreaForAutoDistribution> findByEnabled(boolean enabled);
}
