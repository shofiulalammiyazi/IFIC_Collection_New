package com.unisoft.reports.ptp;

import com.unisoft.retail.loan.dataEntry.ptp.LoanPtp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
@Transactional
public interface PtpRepository extends JpaRepository<LoanPtp, Long> {

    List<LoanPtp> findByCreatedDateIsBetween(Date start,
                                             Date end);

//    @Query(
//            value = "", nativeQuery = true)
//           List<LoanPtp> findptp();
}
