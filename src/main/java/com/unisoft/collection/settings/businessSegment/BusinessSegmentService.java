package com.unisoft.collection.settings.businessSegment;



import java.util.List;

public interface BusinessSegmentService {

    public void save(BusinessSegment BusinessSegment);

    public List<BusinessSegment> getList();

    public BusinessSegment getById(Long id);

    public void update(BusinessSegment BusinessSegment);
}
