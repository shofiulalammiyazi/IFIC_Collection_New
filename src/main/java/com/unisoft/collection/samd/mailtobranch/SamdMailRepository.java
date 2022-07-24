package com.unisoft.collection.samd.mailtobranch;

import com.unisoft.collection.distribution.loan.loanAccountBasic.LoanAccountBasicInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Tuple;
import java.util.List;

@Repository
@Transactional
public interface SamdMailRepository extends JpaRepository<LoanAccountBasicInfo,Long> {

@Query(value = "SELECT labi.ACCOUNT_NO, labi.ACCOUNT_NAME,cbie.BRANCH_CODE \n" +
        "FROM CUSTOMER_BASIC_INFO_ENTITY cbie\n" +
        "       JOIN LOAN_ACCOUNT_BASIC_INFO labi ON cbie.ID = labi.CUSTOMER_ID\n" +
        "       JOIN LMS_COLLECTION_SAM_LOAN_ACCOUNT_DISTRIBUTIONS slad ON labi.ID = slad.LOAN_ACCOUNT_BASIC_INFO_ID" ,nativeQuery = true)
    List<Tuple> getBranchWiseSamdDistributionAccount();

}
