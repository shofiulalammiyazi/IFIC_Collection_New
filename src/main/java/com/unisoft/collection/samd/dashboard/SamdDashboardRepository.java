package com.unisoft.collection.samd.dashboard;


import com.unisoft.collection.distribution.loan.loanAccountDistribution.LoanAccountDistributionInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Yasir Araphat
 * Created on 24 August, 2021
 */
@Repository
public interface SamdDashboardRepository extends JpaRepository<LoanAccountDistributionInfo, Long> {





}
