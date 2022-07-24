package com.unisoft.customerloanprofile.referenceinfo;

import com.unisoft.customerbasicinfo.CustomerBasicInfoEntity;
import com.unisoft.user.UserService;
import org.hibernate.PersistentObjectException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReferenceInfoService {

    @Autowired
    private ReferenceInfoDao referenceInfoDao;
    @Autowired
    private ReferenceInoRepository referenceInoRepository;
    @Autowired
    private UserService userService;

    public List<ReferenceInfoEntity> getReferenceList(){
        return referenceInfoDao.getList();
    }

    public List<ReferenceInfoEntity> findByCustomer(CustomerBasicInfoEntity customer){
        return referenceInfoDao.findByCustomer(customer);
    }

//    public boolean saveReferenceInfo(ReferenceInfoEntity referenceInfoEntity){
//        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        referenceInfoEntity.setDealerPin(userPrincipal.getUsername());
//        referenceInfoEntity.setStatus(ReferenceInfoStatus.PENDING);
//        return referenceInfoDao.save(referenceInfoEntity);
//    }
//
//
public ReferenceInfoEntity saveReferenceInfo(ReferenceInfoEntity obj) {
    try {
        obj.setStatus(ReferenceInfoStatus.PENDING);
        return referenceInoRepository.save(obj);
    } catch (Exception e) {
        e.printStackTrace();
        throw new PersistentObjectException(e.getCause().toString());
    }


}





    public ReferenceInfoEntityDto findById(Long id) {
        ReferenceInfoEntityDto entityDto = new ReferenceInfoEntityDto();
        ReferenceInfoEntity entity = referenceInfoDao.findById(id);
        BeanUtils.copyProperties(entity, entityDto);
        return entityDto;
    }

    public void deleteReference(Long id){
        ReferenceInfoEntity  referenceInfoEntity = referenceInfoDao.findById(id);
        referenceInfoDao.deleteReference(referenceInfoEntity);
    }

    public List<ReferenceInfoEntity> findReferenceInfoEntitiesByCustomerId(String customerId) {
        return referenceInoRepository.findReferenceInfoEntitiesByCustomerId(customerId);
    }

    public List<ReferenceInfoEntity> findReferenceInfoEntitiesByCustomerIdAndStatus(String customerId) {
        List<ReferenceInfoEntity> referenceInfoEntityList= referenceInoRepository.findReferenceInfoEntitiesByCustomerIdAAndStatus(customerId, "PENDING");
        System.out.println("test");
        return referenceInfoEntityList;
    }


    public List<ReferenceInfoEntity> findReferenceInfoEntityByDealerPinBndStatus(String pin, String pending) {
        List<ReferenceInfoEntity> referenceInfoEntityList = referenceInoRepository.findReferenceInfoEntityByDealerPinBndStatus(pin, pending);
        return referenceInfoEntityList;
    }

    public ReferenceInfoEntity findReferenceInfoEntityById(Long id) {
        return referenceInoRepository.getOne(id);
    }

    public ReferenceInfoEntity save(ReferenceInfoEntity referenceInfoEntity) {
        return referenceInoRepository.save(referenceInfoEntity);
    }

    public  List<ReferenceInfoEntity>referenceInfoEntityList(String loanAccountNo){
        return referenceInoRepository.findByLoanAccountNo(loanAccountNo);

    }
}
