package com.unisoft.collection.settings.loanguarantorInfo;

import com.unisoft.customerloanprofile.guarantorinfo.GuarantorInfoEntity;
import com.unisoft.customerloanprofile.guarantorinfo.GuarantorInfoStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface LoanCustomerGuarantorInfoSettingsRepository  extends JpaRepository<GuarantorInfoEntity,Long> {

    GuarantorInfoEntity findByAccountNoAndLoanAccountNo(String accountNo,String loanAccountNo);
    List<GuarantorInfoEntity> findByStatus(GuarantorInfoStatus status);
}
