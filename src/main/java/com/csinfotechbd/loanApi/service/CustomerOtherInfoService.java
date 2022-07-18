package com.csinfotechbd.loanApi.service;

import com.csinfotechbd.common.CommonRepository;
import com.csinfotechbd.common.CommonServiceImpl;
import com.csinfotechbd.loanApi.model.CustomerAndBorrowerInfo;
import com.csinfotechbd.loanApi.model.CustomerAndBorrowerInfoDao;
import com.csinfotechbd.loanApi.model.CustomerAndBorrowerInfoDto;
import com.csinfotechbd.loanApi.repository.CustomerOtherInfoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Entity;
import java.util.List;

@Service
public class CustomerOtherInfoService extends CommonServiceImpl<CustomerAndBorrowerInfo> {

    @Autowired
    private CustomerAndBorrowerInfoDao customerAndBorrowerInfoDao;

    @Autowired
    private CustomerOtherInfoRepository customerOtherInfoRepository;

    @Autowired
    public CustomerOtherInfoService(CommonRepository<CustomerAndBorrowerInfo> commonRepository) {
        this.repository = commonRepository;
    }

    public CustomerAndBorrowerInfo findByCustomerId(Long customerId) {
        return customerOtherInfoRepository.findByCustomerId(customerId);
    }

    public CustomerAndBorrowerInfoDto findById(Long id) {
        CustomerAndBorrowerInfoDto entityDto = new CustomerAndBorrowerInfoDto();
        CustomerAndBorrowerInfo entity = customerAndBorrowerInfoDao.findById(id);
        BeanUtils.copyProperties(entity, entityDto);
        return entityDto;
    }

    public List<CustomerAndBorrowerInfo> findCustomerAndBorrowerInfoByCustomerIdandStatus(String customerId) {
        List<CustomerAndBorrowerInfo> customerAndBorrowerInfoList= customerOtherInfoRepository.findCustomerAndBorrowerInfoByCustomerIdAndStatus(customerId, "PENDING");
        return customerAndBorrowerInfoList;
    }


    public List<CustomerAndBorrowerInfo> findCustomerAndBorrowerInfoByDealerPinBndStatus(String pin, String pending) {
        List<CustomerAndBorrowerInfo> customerAndBorrowerInfoList = customerOtherInfoRepository.findCustomerAndBorrowerInfoByDealerPinBndStatus(pin, pending);
        return customerAndBorrowerInfoList;
    }

    public CustomerAndBorrowerInfo findCustomerBorrowerById(Long id) {

        return customerOtherInfoRepository.getOne(id);
    }

    public CustomerAndBorrowerInfo save(CustomerAndBorrowerInfo referenceInfoEntity) {
        return customerOtherInfoRepository.save(referenceInfoEntity);
    }
}
