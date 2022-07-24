package com.unisoft.collection.samd.dataEntry.monitoringFollowUp;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MonitoringFollowUpService {

    List<MonitoringAndFollowUpDto> findMonitoringFollowUpByCustomerId(String customerId);

    MonitoringAndFollowUp saveMonitoringFollowUp(MonitoringAndFollowUp monitoringFollowUp, MultipartFile file);

    MonitoringAndFollowUp findMonitoringFollowUpById(Long id);
}
