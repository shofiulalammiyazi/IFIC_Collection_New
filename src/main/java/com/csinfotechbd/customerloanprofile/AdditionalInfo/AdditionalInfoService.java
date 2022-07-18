package com.csinfotechbd.customerloanprofile.AdditionalInfo;

import com.csinfotechbd.customerbasicinfo.CustomerBasicInfoEntity;
import com.csinfotechbd.user.UserPrincipal;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.Access;
import java.util.List;

@Service
public class AdditionalInfoService {

    @Autowired
    private AdditionalInfoDao additionalInfoDao;

    @Autowired
    private AdditionalInfoRepository additionalInfoRepository;

    public List<AdditionalInfo> findByCustomer(CustomerBasicInfoEntity customer){
       return additionalInfoDao.findByCustomer(customer);
    }

    public boolean saveReferenceInfo(AdditionalInfo additionalInfo){
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        additionalInfo.setDealerPin(userPrincipal.getUsername());
        additionalInfo.setStatus(AdditionalInfoStatus.PENDING);
        return additionalInfoDao.save(additionalInfo);
    }


    public AdditionalInfo findById(Long id) {
        AdditionalInfo entityDto = new AdditionalInfo();
        AdditionalInfo entity = additionalInfoDao.findById(id);
        BeanUtils.copyProperties(entity, entityDto);
        return entityDto;
    }

//    public List<AdditionalInfo> findAdditionalInfoByCustomerId(String customerId) {
//        return additionalInfoRepository.findAdditionalInfoByCustomerId(customerId);
//    }

    public List<AdditionalInfo> findAdditionalInfoByLoanAcNo(String accountNo) {
        return additionalInfoRepository.findAdditionalInfoByAccountNo(accountNo);
    }

    public List<AdditionalInfo> findAdditionalInfoByCustomerIdAndStatus(String customerId) {
        List<AdditionalInfo> additionalInfoList= additionalInfoRepository.findAdditionalInfoByCustomerIdAndStatus(customerId, "PENDING");
        System.out.println("test");
        return additionalInfoList;
    }


    public List<AdditionalInfo> findAdditionalInfoByDealerPinBndStatus(String pin, String pending) {
        List<AdditionalInfo> referenceInfoEntityList = additionalInfoRepository.findAdditionalInfoByDealerPinAndStatus(pin, pending);
        return referenceInfoEntityList;
    }

    public AdditionalInfo findAdditionalInfoById(Long id) {
        return additionalInfoRepository.getOne(id);
    }

    public AdditionalInfo save(AdditionalInfo additionalInfo) {
        return additionalInfoRepository.save(additionalInfo);
    }

//    public AdditionalInfo findByLastId(String customerId){
//        return additionalInfoRepository.findTopByCustomerIdOrderByIdDesc(customerId);
//    }

    public AdditionalInfo findByLastAccountNo(String accountNo){
        return additionalInfoRepository.findTopByAccountNoOrderByIdDesc(accountNo);
    }

}
