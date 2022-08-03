package com.unisoft.collection.distribution.loan.loanAccountDistribution;
/*
Created by   Islam at 9/1/2019
*/

import com.unisoft.collection.distribution.loan.loanAccountBasic.LoanAccountBasicInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AutoDistributionTempRepository extends JpaRepository<AutoDistributionTemp, Long> {

    List<AutoDistributionTemp> findAllByOrderByIdDesc();

    @Query(value = "DELETE FROM AUTO_DISTRIBUTION_TEMP", nativeQuery = true)
    void deleteAllData();

    AutoDistributionTemp findByLoanAccountBasicInfo(LoanAccountBasicInfo loanAccountBasicInfo);

    //List<Tuple> getSummarizeDataForDealerDashboard();
}
