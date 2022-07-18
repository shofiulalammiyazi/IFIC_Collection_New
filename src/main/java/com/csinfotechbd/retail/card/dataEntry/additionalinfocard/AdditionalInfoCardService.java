package com.csinfotechbd.retail.card.dataEntry.additionalinfocard;

import com.csinfotechbd.customerbasicinfo.CustomerBasicInfoEntity;
import com.csinfotechbd.customerloanprofile.AdditionalInfo.AdditionalInfoStatus;
import com.csinfotechbd.user.UserPrincipal;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdditionalInfoCardService {

    @Autowired
    private AdditionalInfoCardDao additionalInfoDao;

    @Autowired
    private AdditionalInfoCardRepository additionalInfoRepository;

    public List<AdditionalInfoCard> findByCustomer(CustomerBasicInfoEntity customer){
       return additionalInfoDao.findByCustomer(customer);
    }

    public boolean saveReferenceInfo(AdditionalInfoCard additionalInfo){
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        additionalInfo.setDealerPin(userPrincipal.getUsername());
        additionalInfo.setStatus(AdditionalInfoStatus.PENDING);
        return additionalInfoDao.save(additionalInfo);
    }


    public AdditionalInfoCard findById(Long id) {
        AdditionalInfoCard entityDto = new AdditionalInfoCard();
        AdditionalInfoCard entity = additionalInfoDao.findById(id);
        BeanUtils.copyProperties(entity, entityDto);
        return entityDto;
    }


    public List<AdditionalInfoCard> findAdditionalInfoByClientIdAndContactId( String clientId,String contractId) {
        return additionalInfoRepository.findByClientIdAndContractIdOrderByIdDesc(clientId,contractId);
    }

//    public List<AdditionalInfoCard> findAdditionalInfoByCustomerIdAndStatus(String customerId) {
//        List<AdditionalInfoCard> additionalInfoList= additionalInfoRepository.findAdditionalInfoByCustomerIdAndStatus(customerId, "PENDING");
//        return additionalInfoList;
//    }


//    public List<AdditionalInfoCard> findAdditionalInfoByDealerPinBndStatus(String pin, String pending) {
//        List<AdditionalInfoCard> referenceInfoEntityList = additionalInfoRepository.findAdditionalInfoByDealerPinAndStatus(pin, pending);
//        return referenceInfoEntityList;
//    }

    public AdditionalInfoCard findAdditionalInfoById(Long id) {
        return additionalInfoRepository.getOne(id);
    }

    public AdditionalInfoCard save(AdditionalInfoCard additionalInfo) {
        return additionalInfoRepository.save(additionalInfo);
    }



//    public AdditionalInfoCard findByLastAccountNo(String accountNo){
//        return additionalInfoRepository.findTopByAccountNoOrderByIdDesc(accountNo);
//    }

}
