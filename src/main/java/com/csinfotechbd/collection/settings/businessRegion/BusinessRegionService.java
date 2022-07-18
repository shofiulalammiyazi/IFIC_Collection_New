package com.csinfotechbd.collection.settings.businessRegion;

import java.util.List;

public interface BusinessRegionService {

    public void save(BusinessRegionManagement businessRegionManagement);

    public List<BusinessRegionManagement> getList();

    public BusinessRegionManagement getById(Long id);

    public void update(BusinessRegionManagement businessRegionManagement);
}
