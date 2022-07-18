package com.csinfotechbd.collection.customerComplain;

import java.util.List;

public interface CustomerComplainService {
    CustomerComplainEntity saveComplain(CustomerComplainEntity customerComplainEntity);

    List<CustomerComplainEntity> getAllComplain();

    List<CustomerComplainDto> getComplainByCustomerId(Long customerId);

    String getDmsFileUrl(String dmsFileId);

    CustomerComplainFile findCustomerComplainFileByDmsFileId(String dmsFileId);

    CustomerComplainEntity findByCustomerId(Long customerId);

    CustomerComplainEntity findByid(Long id);

    CustomerComplainEntity save(CustomerComplainEntity customerComplainEntity);

    List<CustomerComplainDto> findCustomerComplainEntityByDealerPinBndStatus(String pin, String pending);

    List<CustomerComplainDto> getCustomerComplainEntityByDealerPinList(List<String> pin);

    List<CustomerComplainViewModel> getCustomerComplainByDealerPinList(List<String> pin);

    List<CustomerComplainEntity> getCustomerComplainList(Long custId);

    List<CustomerComplainDto> getCustomerCompainFileById(Long id);
}
