package com.csinfotechbd.collection.distribution.loan.loanAccountOther;

import com.csinfotechbd.collection.distribution.loan.loanAccountBasic.LoanAccountBasicInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanAccountOtherRepository extends JpaRepository<LoanAccountOtherInfo, Long> {

    LoanAccountOtherInfo findFirstByLoanAccountBasicInfo(LoanAccountBasicInfo loanAccountBasicInfo);

    List<LoanAccountOtherInfo> findByEnabled(boolean enabled);
}
