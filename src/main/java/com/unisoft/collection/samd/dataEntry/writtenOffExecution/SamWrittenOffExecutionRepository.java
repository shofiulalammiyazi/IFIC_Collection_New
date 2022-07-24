package com.unisoft.collection.samd.dataEntry.writtenOffExecution;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SamWrittenOffExecutionRepository extends JpaRepository<SamWrittenOffExecution, Long> {

    @Query(name = "FROM SamWrittenOffExecution WHERE loanAccountNo IN ?1 AND latest = 'true'")
    List<SamWrittenOffExecution> findAllByLoanAccountNo(List<String> accountNo);


    @Query(value = "SELECT * FROM LMS_COLLECTION_SAM_WRITTEN_OFF_EXECUTION " +
            "WHERE LOAN_ACCOUNT_BASIC_INFO_ID IN (SELECT id FROM LOAN_AUTO_DISTRIBUTION_INFO WHERE ACCOUNT_NO IN ?1) " +
            "AND LATEST = '1' ", nativeQuery = true)
    List<SamWrittenOffExecution> findSamWrittenOffExecutionsByLoanAccountNo(List<String> accountNos);



    @Query(name = "FROM SamWrittenOffExecution WHERE latest = ?1")
    List<SamWrittenOffExecution> findAllByLatest(boolean latest);

    @Query(value = "SELECT * FROM LMS_COLLECTION_SAM_WRITTEN_OFF_EXECUTION WHERE LOAN_ACCOUNT_NO = ? AND LATEST='1' ", nativeQuery = true)
    SamWrittenOffExecution findSamWrittenOffExecutionByLoanAccountNoAndLatest(String accountNo);
}
