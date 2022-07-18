package com.csinfotechbd.collection.samd.dataEntry.agencyAllocationViaExcel;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface AgencyAllocationService {
    Map<String, Object> saveData(MultipartFile file);

    List<AgencyAllocation> findAllLatest();
}
