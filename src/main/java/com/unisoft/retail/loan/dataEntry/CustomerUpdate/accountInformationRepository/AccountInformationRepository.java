package com.unisoft.retail.loan.dataEntry.CustomerUpdate.accountInformationRepository;

import com.unisoft.collection.dashboard.AdvanceSearchPayload;
import com.unisoft.detailsOfCollection.cardviewmodels.AccountInformation;
import com.unisoft.loanApi.model.AdvanceSearchDataModel;
import com.unisoft.retail.loan.dataEntry.CustomerUpdate.accountInformation.AccountInformationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AccountInformationRepository extends JpaRepository<AccountInformationEntity, Long> {

    @Query(value = "select * From account_information_entity Where CUSTOMER_ID = ? fetch first row only ", nativeQuery = true)
    AccountInformationEntity findByCustomerId(String customerId);

    @Query(value = "select * From account_information_entity Where ID = ?", nativeQuery = true)
    AccountInformationEntity findByCustId(Long id);

    @Query(value = "select * From account_information_entity Where replace(loanacno,' ','') = ? fetch first row only ", nativeQuery = true)
    AccountInformationEntity findAccountInformationEntityByLoanACNo(String loanACNo);


    AccountInformationEntity findAccountInformationEntityByLoanAccountNew(String loanAccountNew);



    AccountInformationEntity findByLoanACNoAndBranchMnemonicAndProductCodeAndDealReference(String accountNumber,String branchMnemonic,String productCode, String dealReference);

    @Query(value = "SELECT * FROM ACCOUNT_INFORMATION_ENTITY WHERE LOANACNO= ?1 and BRANCH_MNEMONIC=?2 and PRODUCT_CODE = ?3 and DEAL_REFERENCE=?4", nativeQuery = true)
    public AccountInformationEntity getAllByLoanAccountNo(String accountNo, String branchMnemonic,String productCode,String dealReference );

    @Query(value = "SELECT * FROM ACCOUNT_INFORMATION_ENTITY WHERE LOAN_ACCOUNT_NEW= ?1", nativeQuery = true)
    public AccountInformationEntity getByLoanAccountNo(String accountNo);

    @Query(value = "SELECT AIE.* FROM ACCOUNT_INFORMATION_ENTITY AIE WHERE AIE.LOANACNO= ?1", nativeQuery = true)
    public AccountInformationEntity getByLoanAccountNoSub(String accountNo);

    @Query(value = "SELECT * " +
            "FROM ACCOUNT_INFORMATION_ENTITY " +
            "WHERE LOANACNO = ?1 " +
            "   OR CUSTOMER_NAME = ?2 " +
            "   OR MOTHER_NAME = ?3 " +
            "   OR MOBILE = ?4 " +
            "   OR NID = ?5 " +
            "   OR DOB = ?6 " +
            "   OR EMAIL = ?7 " +
            "   OR LINK_MOTHER_ACCOUNT_NO = ?8 " +
            "   OR CUSTOMER_ID = ?9", nativeQuery = true)
    public List<AccountInformationEntity> advancedSearch(String accountNo, String customerName, String motherName, String mobileNo, String nid, String dob, String email, String linkAccount, String customerId);

    @Query(value = "SELECT * " +
            "FROM ACCOUNT_INFORMATION_ENTITY " +
            "WHERE LOANACNO = ?1 " +
            "   OR lower(CUSTOMER_NAME) like '%'||DECODE(?2, NULL, 'CUSTOMER_NAME', lower(?2))||'%' " +
            "   OR lower(MOTHER_NAME) like '%'||DECODE(?3, NULL, 'MOTHER_NAME', lower(?3))||'%' " +
            "   OR lower(FATHER_NAME) like '%'||DECODE(?4, NULL, 'FATHER_NAME', lower(?4))||'%' " +
            "   OR MOBILE = ?5 " +
            "   OR NID = ?6 " +
            "   OR lower(DOB) = LOWER(TO_CHAR(TO_DATE(?7),'YYYY-MON-DD')) " +
            "   OR EMAIL = ?8 " +
            "   OR LINK_MOTHER_ACCOUNT_NO = ?9 " +
            "   OR CUSTOMER_ID = ?10 " +
            "   OR TIN = ?11", nativeQuery = true)
    public List<AccountInformationEntity> advancedSearchDashboard(String accountNo, String customerName, String motherName, String fatherName, String mobileNo, String nid, String dob, String email, String linkAccount,String customerId, String tin);


    public List<AccountInformationEntity> findAllByLoanACNo(String accountNo);

    //TO DO: need to change 2022-03-11 to SYSDATE+3
    @Query(value = "SELECT * FROM ACCOUNT_INFORMATION_ENTITY WHERE LOANACNO like %?1% AND IS_SMS_SENT = 'N' " +
            "AND TO_DATE(EMI_DATE,'YYYY-MM-DD') <= (SELECT TO_DATE('2022-03-11','YYYY-MM-DD') FROM dual)", nativeQuery = true)
    public Page<AccountInformationEntity> findAllByLoanACNoAndCurrentDatePlusThree(String accountNo, Pageable pageable);

    @Query(value = "SELECT * FROM ACCOUNT_INFORMATION_ENTITY WHERE LOANACNO like %?1% AND IS_SMS_SENT = 'N'", nativeQuery = true)
    public Page<AccountInformationEntity> findAllByLoanACNo(String accountNo, Pageable pageable);

    //TO DO: need to change 2022-03-11 to SYSDATE+3
    @Query(value = "SELECT * from ACCOUNT_INFORMATION_ENTITY WHERE IS_SMS_SENT = 'N' AND TO_DATE(EMI_DATE,'YYYY-MM-DD') " +
            "<= (SELECT TO_DATE('2022-03-11','YYYY-MM-DD') FROM dual)", nativeQuery = true)
    Page<AccountInformationEntity> findAllAccByCurrentDatePlusThree(Pageable pageable);

    @Query(value = "SELECT * FROM ACCOUNT_INFORMATION_ENTITY WHERE IS_SMS_SENT = 'N'", nativeQuery = true)
    Page<AccountInformationEntity> findAllAcc(Pageable pageable);

    @Query(value = "SELECT * FROM ACCOUNT_INFORMATION_ENTITY WHERE LOANACNO like %?1% AND IS_SMS_SENT = 'Y' AND IS_DISTRIBUTED = 'N'", nativeQuery = true)
    public Page<AccountInformationEntity> findAllByLoanACNoByIsSmsEntity(String accountNo, Pageable pageable);

    @Query(value = "SELECT * FROM ACCOUNT_INFORMATION_ENTITY WHERE IS_SMS_SENT = 'Y' AND IS_DISTRIBUTED = 'N'", nativeQuery = true)
    Page<AccountInformationEntity> findAllAccIsSmsEntity(Pageable pageable);

}
