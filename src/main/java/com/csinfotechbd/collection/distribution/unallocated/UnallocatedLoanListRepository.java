package com.csinfotechbd.collection.distribution.unallocated;

import com.csinfotechbd.collection.distribution.loan.loanAccountBasic.LoanAccountBasicInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface UnallocatedLoanListRepository extends JpaRepository<UnallocatedLoanList, Long> {

    List<UnallocatedLoanList> findByCreatedDateIsBetweenAndSamAccountNotInAndWriteOffAccountNotInAndSchemaCodeNotIn(Date startDate, Date endDate, List<String> samIgnore, List<String> writeOffIgnore, List<String> productGroup);

    UnallocatedLoanList findFirstByLoanAccountBasicInfo(LoanAccountBasicInfo loanAccountBasicInfo1);
}
