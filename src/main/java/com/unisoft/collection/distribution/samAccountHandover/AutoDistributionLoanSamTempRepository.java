package com.unisoft.collection.distribution.samAccountHandover;

import com.unisoft.collection.distribution.loan.loanAccountBasic.LoanAccountBasicInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AutoDistributionLoanSamTempRepository extends JpaRepository<AutoDistributionLoanSamTemp, Long> {

    List<AutoDistributionLoanSamTemp> findAllByOrderByIdDesc();

    List<AutoDistributionLoanSamTemp> findByLoanAccountBasicInfo(LoanAccountBasicInfo loanAccountBasicInfo);
}
