package com.csinfotechbd.customerloanprofile.guarantorinfo;

import com.csinfotechbd.customerbasicinfo.CustomerBasicInfoEntity;
import com.csinfotechbd.user.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.hibernate.PersistentObjectException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GuarantorInfoService {

    private final GurantorInfoRepository repository;

    public List<GuarantorInfoEntity> getGuarantorList(){
        return repository.findAll();
    }

    public List<GuarantorInfoEntity> findByCustomer(CustomerBasicInfoEntity customerBasicInfoEntity){
        return repository.findByCustomerBasicInfo(customerBasicInfoEntity);
    }

    public List<GuarantorInfoEntity> findByCustomerId(Long customerId){
        return repository.findByCustomerBasicInfoId(customerId);
    }

    public boolean saveGuarantorInfo(GuarantorInfoEntity guarantorInfoEntity){
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        guarantorInfoEntity.setStatus(GuarantorInfoStatus.PENDING);
        guarantorInfoEntity.setDealerPin(userPrincipal.getUsername());
        return repository.save(guarantorInfoEntity).getId() != null;
    }

    public GuarantorInfoEntity findByid(Long id) {
        return repository.findGurantorInfoEntityByid(id);
    }

    public List<GuarantorInfoEntity> findGuarantorInfoEntityByDealerPinBndStatus(String pin, String pending) {
        return repository.findGuarantorInfoEntityByDealerPinBndStatus(pin, pending);
    }

    public void save(GuarantorInfoEntity guarantorInfoEntity) {
        repository.save(guarantorInfoEntity);
    }


    public  List<GuarantorInfoEntity>guarantorInfoEntityList(String loanAccountNo){
        return repository.findByLoanAccountNo(loanAccountNo);

    }


    public GuarantorInfoEntity guarantorInfoSave(GuarantorInfoEntity obj) {
        try {
            obj.setStatus(GuarantorInfoStatus.PENDING);
            return repository.save(obj);
        } catch (Exception e) {
            e.printStackTrace();
            throw new PersistentObjectException(e.getCause().toString());
        }


    }
}
