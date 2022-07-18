package com.csinfotechbd.collection.settings.dealerGroupBasedOnCollectorAreaForAutoDistribution;

import com.csinfotechbd.common.CommonService;

import java.util.List;

public interface DealerGroupBasedOnCollectorAreaForAutoDistributionService extends CommonService<DealerGroupBasedOnCollectorAreaForAutoDistribution> {
    List<DealerGroupBasedOnCollectorAreaForAutoDistribution> findByEnabled(boolean enabled);
}
