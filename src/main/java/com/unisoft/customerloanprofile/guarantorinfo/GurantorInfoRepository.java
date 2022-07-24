package com.unisoft.customerloanprofile.guarantorinfo;

import com.unisoft.customerbasicinfo.CustomerBasicInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GurantorInfoRepository extends JpaRepository<GuarantorInfoEntity, Long> {

    List<GuarantorInfoEntity> findByCustomerBasicInfo(CustomerBasicInfoEntity customerBasicInfoEntity);
    List<GuarantorInfoEntity> findByCustomerBasicInfoId(Long customerBasicInfoId);
    GuarantorInfoEntity findByAccountNo(String accountNo);

    @Query(value = "SELECT * FROM GUARANTOR_INFO_ENTITY WHERE ID = ? ", nativeQuery = true)
    GuarantorInfoEntity findGurantorInfoEntityByid(Long id);

    @Query(value = "SELECT * FROM GUARANTOR_INFO_ENTITY WHERE (DEALER_PIN = ? AND STATUS = ?) ", nativeQuery = true)
    List<GuarantorInfoEntity> findGuarantorInfoEntityByDealerPinBndStatus(String pin, String pending);

    List<GuarantorInfoEntity> findByLoanAccountNo(String loanAccountNo);
}
