package com.csinfotechbd.collection.settings.vipStatus;


import java.util.List;

public interface VipStatusService {
    
    List<VipStatus> getList();

    String save(VipStatus entity);

    VipStatus getById(Long id);

    List<VipStatus> getActiveList();

    List<String> getVipStatusNameList();
}
