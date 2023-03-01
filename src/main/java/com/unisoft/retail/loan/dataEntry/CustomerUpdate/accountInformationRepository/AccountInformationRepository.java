package com.unisoft.retail.loan.dataEntry.CustomerUpdate.accountInformationRepository;

import com.unisoft.collection.dashboard.AdvanceSearchPayload;
import com.unisoft.detailsOfCollection.cardviewmodels.AccountInformation;
import com.unisoft.loanApi.model.AdvanceSearchDataModel;
import com.unisoft.retail.loan.dataEntry.CustomerUpdate.accountInformation.AccountInfoSMSDto;
import com.unisoft.retail.loan.dataEntry.CustomerUpdate.accountInformation.AccountInformationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
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


    AccountInformationEntity findByLoanACNoAndBranchMnemonicAndProductCodeAndDealReference(String accountNumber, String branchMnemonic, String productCode, String dealReference);

    @Query(value = "SELECT * FROM ACCOUNT_INFORMATION_ENTITY WHERE LOANACNO= ?1 and BRANCH_MNEMONIC=?2 and PRODUCT_CODE = ?3 and DEAL_REFERENCE=?4", nativeQuery = true)
    public AccountInformationEntity getAllByLoanAccountNo(String accountNo, String branchMnemonic, String productCode, String dealReference);

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
    public List<AccountInformationEntity> advancedSearchDashboard(String accountNo, String customerName, String motherName, String fatherName, String mobileNo, String nid, String dob, String email, String linkAccount, String customerId, String tin);


    public List<AccountInformationEntity> findAllByLoanACNo(String accountNo);

    //TO DO: need to change 2022-03-11 to SYSDATE+3
    @Query(value = "SELECT * FROM ACCOUNT_INFORMATION_ENTITY WHERE LOANACNO like %?1% AND IS_SMS_SENT = 'N' " +
            "AND TO_DATE(EMI_DATE,'yyyy-MM-dd') <= (SELECT TO_DATE('2022-03-11','yyyy-MM-dd') FROM dual)", nativeQuery = true)
    public Page<AccountInformationEntity> findAllByLoanACNoAndCurrentDatePlusThree(String accountNo, Pageable pageable);

    @Query(value = "SELECT * FROM ACCOUNT_INFORMATION_ENTITY WHERE LOANACNO like %?1% AND IS_SMS_SENT = 'N'", nativeQuery = true)
    public Page<AccountInformationEntity> findAllByLoanACNo(String accountNo, Pageable pageable);

    //TO DO: need to change 2022-03-11 to SYSDATE+3
    @Query(value = "SELECT * from ACCOUNT_INFORMATION_ENTITY WHERE IS_SMS_SENT = 'N' AND NEXTEMIDATE IS NOT NULL AND MOBILE IS NOT NULL AND " +
            "TO_DATE(NEXTEMIDATE,'yyyy-MM-dd')<= (SELECT TO_DATE('2022-03-11','yyyy-MM-dd') FROM dual)", nativeQuery = true)
    Page<AccountInformationEntity> findAllAccByCurrentDatePlusThree(Pageable pageable);

    @Query(value = "SELECT * from ACCOUNT_INFORMATION_ENTITY WHERE IS_SMS_SENT = 'N' AND TO_DATE(NEXTEMIDATE,'yyyy-MM-dd') " +
            "<= (SELECT TO_DATE('2022-03-11','yyyy-MM-dd') FROM dual)", nativeQuery = true)
    List<AccountInformationEntity> findAllByEmiDatePlusThree();

    @Query(value = "SELECT * from ACCOUNT_INFORMATION_ENTITY WHERE IS_SMS_SENT = 'N' AND TO_DATE(NEXTEMIDATE,'yyyy-MM-dd') " +
            "= (SELECT TO_DATE('2022-03-11','yyyy-MM-dd') FROM dual)", nativeQuery = true)
    List<AccountInformationEntity> findAllByEmiDateOnlyPlusThree();

    @Query(value = "SELECT * FROM ACCOUNT_INFORMATION_ENTITY WHERE IS_SMS_SENT = 'N'", nativeQuery = true)
    Page<AccountInformationEntity> findAllAcc(Pageable pageable);

    @Query(value = "SELECT * FROM ACCOUNT_INFORMATION_ENTITY WHERE LOANACNO like %?1% AND IS_SMS_SENT = 'Y' AND IS_DISTRIBUTED = 'N'", nativeQuery = true)
    public Page<AccountInformationEntity> findAllByLoanACNoByIsSmsEntity(String accountNo, Pageable pageable);

    @Query(value = "SELECT * FROM ACCOUNT_INFORMATION_ENTITY WHERE IS_SMS_SENT = 'Y' AND IS_DISTRIBUTED = 'N'", nativeQuery = true)
    Page<AccountInformationEntity> findAllAccIsSmsEntity(Pageable pageable);

    @Query(value = "SELECT * FROM ACCOUNT_INFORMATION_ENTITY WHERE IS_SMS_SENT = 'Y' AND IS_DISTRIBUTED = 'N' AND TO_NUMBER(OVERDUE)>0", nativeQuery = true)
    Page<AccountInformationEntity> findAllAccIsSmsEntityAndOverdueGreaterThanZero(Pageable pageable);

    @Query(value = "SELECT * FROM ACCOUNT_INFORMATION_ENTITY WHERE LOANACNO like %?1% AND IS_SMS_SENT = 'Y' AND IS_DISTRIBUTED = 'N' AND TO_NUMBER(OVERDUE)>0", nativeQuery = true)
    Page<AccountInformationEntity> findAllByLoanACNoByIsSmsEntityAndOverdueGreaterThanZero(String accountNo, Pageable pageable);

    @Query(value = "SELECT * FROM ACCOUNT_INFORMATION_ENTITY WHERE IS_SMS_SENT = 'Y' AND IS_DISTRIBUTED = 'N'", nativeQuery = true)
    List<AccountInformationEntity> findAllAccIsSmsEntity();

    @Query(value = "SELECT * from ACCOUNT_INFORMATION_ENTITY WHERE TO_NUMBER(OVERDUE)>0", nativeQuery = true)
    Page<AccountInformationEntity> findAllByOverdueGreaterThanZero(Pageable pageable);

    @Query(value = "SELECT * from ACCOUNT_INFORMATION_ENTITY WHERE TO_NUMBER(OVERDUE)>0", nativeQuery = true)
    List<AccountInformationEntity> findAllByOverdueGreaterThanZero();

    @Query(value = "SELECT * FROM ACCOUNT_INFORMATION_ENTITY WHERE LOANACNO like %?1% AND TO_NUMBER(OVERDUE)>0", nativeQuery = true)
    Page<AccountInformationEntity> findAllByOverdueGreaterThanZero(String accountNo, Pageable pageable);


    @Query(value = "SELECT * FROM ACCOUNT_INFORMATION_ENTITY WHERE LOANACNO like %?1% AND ISESCALATED = 'Y'", nativeQuery = true)
    Page<AccountInformationEntity> findAllByLoanACNoByIsSmsEntityAndOverdueGreaterThanZeroAndEscalation(String accountNo, Pageable pageable);

    @Query(value = "SELECT * FROM ACCOUNT_INFORMATION_ENTITY WHERE ISESCALATED = 'Y'", nativeQuery = true)
    Page<AccountInformationEntity> findAllAccIsSmsEntityAndOverdueGreaterThanZeroEscalation(Pageable pageable);


    @Query(value = "SELECT AIE.* " +
            "FROM ACCOUNT_INFORMATION_ENTITY AIE " +
            "WHERE AIE.IS_SMS_SENT = 'N' " +
            "  AND AIE.NEXTEMIDATE = (SELECT to_char((SYSDATE+3),'YYYY-MM-DD') FROM dual) " +
            "  AND AIE.EMI_AMOUNT IS NOT NULL " +
            "  AND AIE.MOBILE IS NOT NULL " +
            "  AND LTRIM(RTRIM(UPPER(AIE.LOANCLSTATUS))) " +
            "        IN (SELECT DISTINCT LTRIM(RTRIM(UPPER(LSE.NAME))) " +
            "            FROM SMS_AND_AUTO_DISTRIBUTION_RULES_ENTITY SADRE " +
            "                   LEFT JOIN SMS_AND_AUTO_DISTRIBUTION_RULES_ENTITY_LOAN_STATUS_ENTITY SADRELSE " +
            "                     ON SADRE.ID = SADRELSE.SMS_AND_AUTO_DISTRIBUTION_RULES_ENTITY_ID " +
            "                   LEFT JOIN LOAN_STATUS_ENTITY LSE ON SADRELSE.LOAN_STATUS_ENTITY_ID = LSE.ID " +
            "            WHERE LSE.ENABLED = 1 AND SADRE.TYPE = 'SMS') " +
            "  AND LTRIM(RTRIM(UPPER(AIE.PRODUCT_CODE))) " +
            "        IN(SELECT DISTINCT LTRIM(RTRIM(UPPER(LTE.LOAN_TYPE))) " +
            "           FROM SMS_AND_AUTO_DISTRIBUTION_RULES_ENTITY SADRE1 " +
            "                  LEFT JOIN SMS_AND_AUTO_DISTRIBUTION_RULES_ENTITY_LOAN_TYPE_ENTITIES SADRELTE " +
            "                    ON SADRE1.ID = SADRELTE.SMS_AND_AUTO_DISTRIBUTION_RULES_ENTITY_ID " +
            "                  LEFT JOIN LOAN_TYPE_ENTITY LTE ON SADRELTE.LOAN_TYPE_ENTITIES_ID = LTE.ID " +
            "           WHERE LTE.ENABLED = 1 AND SADRE1.TYPE = 'SMS') " +
            "  AND TO_NUMBER(AIE.NO_OF_INSTALLMENT_DUE) < (SELECT TO_NUMBER(SADRE3.UNPAID_INSTALLMENT_NUMBER) FROM SMS_AND_AUTO_DISTRIBUTION_RULES_ENTITY SADRE3 WHERE " +
            "    SADRE3.TYPE = 'SMS')", nativeQuery = true)
    List<AccountInformationEntity> finAllEligibleSmsList();


    @Query(value = "SELECT AIE.LOANACNO, AIE.CUSTOMER_NAME, AIE.MOBILE, AIE.BRANCH_MNEMONIC, " +
            "       AIE.PRODUCT_CODE, AIE.DEAL_REFERENCE, AIE.TOTAL_OUTSTANDING, AIE.OVERDUE, " +
            "       AIE.EMI_AMOUNT, AIE.NEXTEMIDATE, AIE.NO_OF_INSTALLMENT_DUE, AIE.LOANCLSTATUS " +
            "FROM ACCOUNT_INFORMATION_ENTITY AIE " +
            "WHERE AIE.IS_SMS_SENT = 'N' " +
            "  AND AIE.NEXTEMIDATE = (SELECT to_char((SYSDATE+3),'YYYY-MM-DD') FROM dual) " +
            "  AND AIE.EMI_AMOUNT IS NOT NULL " +
            "  AND AIE.MOBILE IS NOT NULL " +
            "  AND LTRIM(RTRIM(UPPER(AIE.LOANCLSTATUS))) " +
            "        IN (SELECT DISTINCT LTRIM(RTRIM(UPPER(LSE.NAME))) " +
            "            FROM SMS_AND_AUTO_DISTRIBUTION_RULES_ENTITY SADRE " +
            "                   LEFT JOIN SMS_AND_AUTO_DISTRIBUTION_RULES_ENTITY_LOAN_STATUS_ENTITY SADRELSE " +
            "                     ON SADRE.ID = SADRELSE.SMS_AND_AUTO_DISTRIBUTION_RULES_ENTITY_ID " +
            "                   LEFT JOIN LOAN_STATUS_ENTITY LSE ON SADRELSE.LOAN_STATUS_ENTITY_ID = LSE.ID " +
            "            WHERE LSE.ENABLED = 1 AND SADRE.TYPE = 'SMS') " +
            "  AND LTRIM(RTRIM(UPPER(AIE.PRODUCT_CODE))) " +
            "        IN(SELECT DISTINCT LTRIM(RTRIM(UPPER(LTE.LOAN_TYPE))) " +
            "           FROM SMS_AND_AUTO_DISTRIBUTION_RULES_ENTITY SADRE1 " +
            "                  LEFT JOIN SMS_AND_AUTO_DISTRIBUTION_RULES_ENTITY_LOAN_TYPE_ENTITIES SADRELTE " +
            "                    ON SADRE1.ID = SADRELTE.SMS_AND_AUTO_DISTRIBUTION_RULES_ENTITY_ID " +
            "                  LEFT JOIN LOAN_TYPE_ENTITY LTE ON SADRELTE.LOAN_TYPE_ENTITIES_ID = LTE.ID " +
            "           WHERE LTE.ENABLED = 1 AND SADRE1.TYPE = 'SMS') " +
            "  AND TO_NUMBER(AIE.NO_OF_INSTALLMENT_DUE) < (SELECT TO_NUMBER(SADRE3.UNPAID_INSTALLMENT_NUMBER) FROM SMS_AND_AUTO_DISTRIBUTION_RULES_ENTITY SADRE3 WHERE " +
            "    SADRE3.TYPE = 'SMS')",nativeQuery = true)
    List<Tuple> finAllEligibleSmsList1(Pageable pageable);

    @Query(value = "SELECT AIE.LOANACNO, AIE.CUSTOMER_NAME, AIE.MOBILE, AIE.BRANCH_MNEMONIC, " +
            "       AIE.PRODUCT_CODE, AIE.DEAL_REFERENCE, AIE.TOTAL_OUTSTANDING, AIE.OVERDUE, " +
            "       AIE.EMI_AMOUNT, AIE.NEXTEMIDATE, AIE.NO_OF_INSTALLMENT_DUE, AIE.LOANCLSTATUS " +
            "FROM ACCOUNT_INFORMATION_ENTITY AIE " +
            "WHERE AIE.IS_SMS_SENT = 'N' " +
            "  AND AIE.NEXTEMIDATE = (SELECT to_char((SYSDATE+3),'YYYY-MM-DD') FROM dual) " +
            "  AND AIE.EMI_AMOUNT IS NOT NULL " +
            "  AND AIE.MOBILE IS NOT NULL " +
            "  AND LTRIM(RTRIM(UPPER(AIE.LOANCLSTATUS))) " +
            "        IN (SELECT DISTINCT LTRIM(RTRIM(UPPER(LSE.NAME))) " +
            "            FROM SMS_AND_AUTO_DISTRIBUTION_RULES_ENTITY SADRE " +
            "                   LEFT JOIN SMS_AND_AUTO_DISTRIBUTION_RULES_ENTITY_LOAN_STATUS_ENTITY SADRELSE " +
            "                     ON SADRE.ID = SADRELSE.SMS_AND_AUTO_DISTRIBUTION_RULES_ENTITY_ID " +
            "                   LEFT JOIN LOAN_STATUS_ENTITY LSE ON SADRELSE.LOAN_STATUS_ENTITY_ID = LSE.ID " +
            "            WHERE LSE.ENABLED = 1 AND SADRE.TYPE = 'SMS') " +
            "  AND LTRIM(RTRIM(UPPER(AIE.PRODUCT_CODE))) " +
            "        IN(SELECT DISTINCT LTRIM(RTRIM(UPPER(LTE.LOAN_TYPE))) " +
            "           FROM SMS_AND_AUTO_DISTRIBUTION_RULES_ENTITY SADRE1 " +
            "                  LEFT JOIN SMS_AND_AUTO_DISTRIBUTION_RULES_ENTITY_LOAN_TYPE_ENTITIES SADRELTE " +
            "                    ON SADRE1.ID = SADRELTE.SMS_AND_AUTO_DISTRIBUTION_RULES_ENTITY_ID " +
            "                  LEFT JOIN LOAN_TYPE_ENTITY LTE ON SADRELTE.LOAN_TYPE_ENTITIES_ID = LTE.ID " +
            "           WHERE LTE.ENABLED = 1 AND SADRE1.TYPE = 'SMS') " +
            "  AND TO_NUMBER(AIE.NO_OF_INSTALLMENT_DUE) < (SELECT TO_NUMBER(SADRE3.UNPAID_INSTALLMENT_NUMBER) FROM SMS_AND_AUTO_DISTRIBUTION_RULES_ENTITY SADRE3 WHERE " +
            "    SADRE3.TYPE = 'SMS')",nativeQuery = true)
    List<Tuple> finAllEligibleSmsList1();

    @Query(value = "SELECT COUNT(AIE.LOANACNO) "+
            "FROM ACCOUNT_INFORMATION_ENTITY AIE " +
            "WHERE AIE.IS_SMS_SENT = 'N' " +
            "  AND AIE.NEXTEMIDATE = (SELECT to_char((SYSDATE+3),'YYYY-MM-DD') FROM dual) " +
            "  AND AIE.EMI_AMOUNT IS NOT NULL " +
            "  AND AIE.MOBILE IS NOT NULL " +
            "  AND LTRIM(RTRIM(UPPER(AIE.LOANCLSTATUS))) " +
            "        IN (SELECT DISTINCT LTRIM(RTRIM(UPPER(LSE.NAME))) " +
            "            FROM SMS_AND_AUTO_DISTRIBUTION_RULES_ENTITY SADRE " +
            "                   LEFT JOIN SMS_AND_AUTO_DISTRIBUTION_RULES_ENTITY_LOAN_STATUS_ENTITY SADRELSE " +
            "                     ON SADRE.ID = SADRELSE.SMS_AND_AUTO_DISTRIBUTION_RULES_ENTITY_ID " +
            "                   LEFT JOIN LOAN_STATUS_ENTITY LSE ON SADRELSE.LOAN_STATUS_ENTITY_ID = LSE.ID " +
            "            WHERE LSE.ENABLED = 1 AND SADRE.TYPE = 'SMS') " +
            "  AND LTRIM(RTRIM(UPPER(AIE.PRODUCT_CODE))) " +
            "        IN(SELECT DISTINCT LTRIM(RTRIM(UPPER(LTE.LOAN_TYPE))) " +
            "           FROM SMS_AND_AUTO_DISTRIBUTION_RULES_ENTITY SADRE1 " +
            "                  LEFT JOIN SMS_AND_AUTO_DISTRIBUTION_RULES_ENTITY_LOAN_TYPE_ENTITIES SADRELTE " +
            "                    ON SADRE1.ID = SADRELTE.SMS_AND_AUTO_DISTRIBUTION_RULES_ENTITY_ID " +
            "                  LEFT JOIN LOAN_TYPE_ENTITY LTE ON SADRELTE.LOAN_TYPE_ENTITIES_ID = LTE.ID " +
            "           WHERE LTE.ENABLED = 1 AND SADRE1.TYPE = 'SMS') " +
            "  AND TO_NUMBER(AIE.NO_OF_INSTALLMENT_DUE) < (SELECT TO_NUMBER(SADRE3.UNPAID_INSTALLMENT_NUMBER) FROM SMS_AND_AUTO_DISTRIBUTION_RULES_ENTITY SADRE3 WHERE " +
            "    SADRE3.TYPE = 'SMS')",nativeQuery = true)
    int finAllEligibleSmsListCount();

    @Query(value = "SELECT AIE.LOANACNO, AIE.CUSTOMER_NAME, AIE.MOBILE, AIE.BRANCH_MNEMONIC, " +
            "       AIE.PRODUCT_CODE, AIE.DEAL_REFERENCE, AIE.TOTAL_OUTSTANDING, AIE.OVERDUE, " +
            "       AIE.EMI_AMOUNT, AIE.NEXTEMIDATE, AIE.NO_OF_INSTALLMENT_DUE, AIE.LOANCLSTATUS " +
            "FROM ACCOUNT_INFORMATION_ENTITY AIE " +
            "WHERE AIE.IS_SMS_SENT = 'N' " +
            "  AND AIE.NEXTEMIDATE = (SELECT to_char((SYSDATE+3),'YYYY-MM-DD') FROM dual) " +
            "  AND AIE.EMI_AMOUNT IS NOT NULL " +
            "  AND AIE.LOANACNO LIKE %?1% " +
            "  AND AIE.MOBILE IS NOT NULL " +
            "  AND LTRIM(RTRIM(UPPER(AIE.LOANCLSTATUS))) " +
            "        IN (SELECT DISTINCT LTRIM(RTRIM(UPPER(LSE.NAME))) " +
            "            FROM SMS_AND_AUTO_DISTRIBUTION_RULES_ENTITY SADRE " +
            "                   LEFT JOIN SMS_AND_AUTO_DISTRIBUTION_RULES_ENTITY_LOAN_STATUS_ENTITY SADRELSE " +
            "                     ON SADRE.ID = SADRELSE.SMS_AND_AUTO_DISTRIBUTION_RULES_ENTITY_ID " +
            "                   LEFT JOIN LOAN_STATUS_ENTITY LSE ON SADRELSE.LOAN_STATUS_ENTITY_ID = LSE.ID " +
            "            WHERE LSE.ENABLED = 1 AND SADRE.TYPE = 'SMS') " +
            "  AND LTRIM(RTRIM(UPPER(AIE.PRODUCT_CODE))) " +
            "        IN(SELECT DISTINCT LTRIM(RTRIM(UPPER(LTE.LOAN_TYPE))) " +
            "           FROM SMS_AND_AUTO_DISTRIBUTION_RULES_ENTITY SADRE1 " +
            "                  LEFT JOIN SMS_AND_AUTO_DISTRIBUTION_RULES_ENTITY_LOAN_TYPE_ENTITIES SADRELTE " +
            "                    ON SADRE1.ID = SADRELTE.SMS_AND_AUTO_DISTRIBUTION_RULES_ENTITY_ID " +
            "                  LEFT JOIN LOAN_TYPE_ENTITY LTE ON SADRELTE.LOAN_TYPE_ENTITIES_ID = LTE.ID " +
            "           WHERE LTE.ENABLED = 1 AND SADRE1.TYPE = 'SMS') " +
            "  AND TO_NUMBER(AIE.NO_OF_INSTALLMENT_DUE) < (SELECT TO_NUMBER(SADRE3.UNPAID_INSTALLMENT_NUMBER) FROM SMS_AND_AUTO_DISTRIBUTION_RULES_ENTITY SADRE3 WHERE " +
            "    SADRE3.TYPE = 'SMS')", nativeQuery = true)
    List<Tuple> finAllEligibleSmsList(String accNo);


    @Query(value = "SELECT AIE.* " +
            "FROM ACCOUNT_INFORMATION_ENTITY AIE " +
            "WHERE TO_NUMBER(AIE.OVERDUE) > 0 " +
            "  AND LTRIM(RTRIM(UPPER(AIE.LOANCLSTATUS))) " +
            "        IN (SELECT DISTINCT LTRIM(RTRIM(UPPER(LSE.NAME))) " +
            "            FROM SMS_AND_AUTO_DISTRIBUTION_RULES_ENTITY SADRE " +
            "                   LEFT JOIN SMS_AND_AUTO_DISTRIBUTION_RULES_ENTITY_LOAN_STATUS_ENTITY SADRELSE " +
            "                     ON SADRE.ID = SADRELSE.SMS_AND_AUTO_DISTRIBUTION_RULES_ENTITY_ID " +
            "                   LEFT JOIN LOAN_STATUS_ENTITY LSE ON SADRELSE.LOAN_STATUS_ENTITY_ID = LSE.ID " +
            "            WHERE LSE.ENABLED = 1 " +
            "              AND SADRE.TYPE = 'Distribution') " +
            "  AND LTRIM(RTRIM(UPPER(AIE.PRODUCT_CODE))) " +
            "        IN(SELECT DISTINCT LTRIM(RTRIM(UPPER(LTE.LOAN_TYPE))) " +
            "           FROM SMS_AND_AUTO_DISTRIBUTION_RULES_ENTITY SADRE1 " +
            "                  LEFT JOIN SMS_AND_AUTO_DISTRIBUTION_RULES_ENTITY_LOAN_TYPE_ENTITIES SADRELTE " +
            "                    ON SADRE1.ID = SADRELTE.SMS_AND_AUTO_DISTRIBUTION_RULES_ENTITY_ID " +
            "                  LEFT JOIN LOAN_TYPE_ENTITY LTE ON SADRELTE.LOAN_TYPE_ENTITIES_ID = LTE.ID " +
            "           WHERE LTE.ENABLED = 1 " +
            "             AND SADRE1.TYPE = 'Distribution') " +
            "  AND TO_NUMBER(AIE.DPD) > (SELECT TO_NUMBER(SADRE2.NO_OF_DAYS_BEFORE_EMI_DATE) " +
            "                            FROM SMS_AND_AUTO_DISTRIBUTION_RULES_ENTITY SADRE2 " +
            "                            WHERE SADRE2.TYPE = 'Distribution') " +
            "  AND TO_NUMBER(AIE.NO_OF_INSTALLMENT_DUE) < (SELECT TO_NUMBER(SADRE3.UNPAID_INSTALLMENT_NUMBER) " +
            "                                              FROM SMS_AND_AUTO_DISTRIBUTION_RULES_ENTITY SADRE3 " +
            "                                              WHERE SADRE3.TYPE = 'Distribution')", nativeQuery = true)
    List<AccountInformationEntity> finAllEligibleDistributionList();


    @Query(value = "SELECT AIE.LOANACNO, " +
            "       AIE.CUSTOMER_NAME, " +
            "       AIE.MOBILE, " +
            "       AIE.BRANCH_MNEMONIC, " +
            "       AIE.PRODUCT_CODE, " +
            "       AIE.DEAL_REFERENCE, " +
            "       AIE.TOTAL_OUTSTANDING, " +
            "       AIE.OVERDUE, " +
            "       AIE.EMI_AMOUNT, " +
            "       AIE.NEXTEMIDATE, " +
            "       AIE.NO_OF_INSTALLMENT_DUE, " +
            "       AIE.LOANCLSTATUS, " +
            "       AIE.IS_SMS_SENT " +
            "FROM ACCOUNT_INFORMATION_ENTITY AIE " +
            "WHERE TO_NUMBER(AIE.OVERDUE) > 0 " +
            "  AND LTRIM(RTRIM(UPPER(AIE.LOANCLSTATUS))) " +
            "        IN (SELECT DISTINCT LTRIM(RTRIM(UPPER(LSE.NAME))) " +
            "            FROM SMS_AND_AUTO_DISTRIBUTION_RULES_ENTITY SADRE " +
            "                   LEFT JOIN SMS_AND_AUTO_DISTRIBUTION_RULES_ENTITY_LOAN_STATUS_ENTITY SADRELSE " +
            "                     ON SADRE.ID = SADRELSE.SMS_AND_AUTO_DISTRIBUTION_RULES_ENTITY_ID " +
            "                   LEFT JOIN LOAN_STATUS_ENTITY LSE ON SADRELSE.LOAN_STATUS_ENTITY_ID = LSE.ID " +
            "            WHERE LSE.ENABLED = 1 " +
            "              AND SADRE.TYPE = 'Distribution') " +
            "  AND LTRIM(RTRIM(UPPER(AIE.PRODUCT_CODE))) " +
            "        IN(SELECT DISTINCT LTRIM(RTRIM(UPPER(LTE.LOAN_TYPE))) " +
            "           FROM SMS_AND_AUTO_DISTRIBUTION_RULES_ENTITY SADRE1 " +
            "                  LEFT JOIN SMS_AND_AUTO_DISTRIBUTION_RULES_ENTITY_LOAN_TYPE_ENTITIES SADRELTE " +
            "                    ON SADRE1.ID = SADRELTE.SMS_AND_AUTO_DISTRIBUTION_RULES_ENTITY_ID " +
            "                  LEFT JOIN LOAN_TYPE_ENTITY LTE ON SADRELTE.LOAN_TYPE_ENTITIES_ID = LTE.ID " +
            "           WHERE LTE.ENABLED = 1 " +
            "             AND SADRE1.TYPE = 'Distribution') " +
            "  AND TO_NUMBER(AIE.DPD) > (SELECT TO_NUMBER(SADRE2.NO_OF_DAYS_BEFORE_EMI_DATE) " +
            "                            FROM SMS_AND_AUTO_DISTRIBUTION_RULES_ENTITY SADRE2 " +
            "                            WHERE SADRE2.TYPE = 'Distribution') " +
            "  AND TO_NUMBER(AIE.NO_OF_INSTALLMENT_DUE) < (SELECT TO_NUMBER(SADRE3.UNPAID_INSTALLMENT_NUMBER) " +
            "                                              FROM SMS_AND_AUTO_DISTRIBUTION_RULES_ENTITY SADRE3 " +
            "                                              WHERE SADRE3.TYPE = 'Distribution')",nativeQuery = true)
    List<Tuple> finAllEligibleDistributionList1(Pageable pageable);

    @Query(value = "SELECT AIE.LOANACNO, " +
            "       AIE.CUSTOMER_NAME, " +
            "       AIE.MOBILE, " +
            "       AIE.BRANCH_MNEMONIC, " +
            "       AIE.PRODUCT_CODE, " +
            "       AIE.DEAL_REFERENCE, " +
            "       AIE.TOTAL_OUTSTANDING, " +
            "       AIE.OVERDUE, " +
            "       AIE.EMI_AMOUNT, " +
            "       AIE.NEXTEMIDATE, " +
            "       AIE.NO_OF_INSTALLMENT_DUE, " +
            "       AIE.LOANCLSTATUS, " +
            "       AIE.IS_SMS_SENT " +
            "FROM ACCOUNT_INFORMATION_ENTITY AIE " +
            "WHERE TO_NUMBER(AIE.OVERDUE) > 0 " +
            "  AND LTRIM(RTRIM(UPPER(AIE.LOANCLSTATUS))) " +
            "        IN (SELECT DISTINCT LTRIM(RTRIM(UPPER(LSE.NAME))) " +
            "            FROM SMS_AND_AUTO_DISTRIBUTION_RULES_ENTITY SADRE " +
            "                   LEFT JOIN SMS_AND_AUTO_DISTRIBUTION_RULES_ENTITY_LOAN_STATUS_ENTITY SADRELSE " +
            "                     ON SADRE.ID = SADRELSE.SMS_AND_AUTO_DISTRIBUTION_RULES_ENTITY_ID " +
            "                   LEFT JOIN LOAN_STATUS_ENTITY LSE ON SADRELSE.LOAN_STATUS_ENTITY_ID = LSE.ID " +
            "            WHERE LSE.ENABLED = 1 " +
            "              AND SADRE.TYPE = 'Distribution') " +
            "  AND LTRIM(RTRIM(UPPER(AIE.PRODUCT_CODE))) " +
            "        IN(SELECT DISTINCT LTRIM(RTRIM(UPPER(LTE.LOAN_TYPE))) " +
            "           FROM SMS_AND_AUTO_DISTRIBUTION_RULES_ENTITY SADRE1 " +
            "                  LEFT JOIN SMS_AND_AUTO_DISTRIBUTION_RULES_ENTITY_LOAN_TYPE_ENTITIES SADRELTE " +
            "                    ON SADRE1.ID = SADRELTE.SMS_AND_AUTO_DISTRIBUTION_RULES_ENTITY_ID " +
            "                  LEFT JOIN LOAN_TYPE_ENTITY LTE ON SADRELTE.LOAN_TYPE_ENTITIES_ID = LTE.ID " +
            "           WHERE LTE.ENABLED = 1 " +
            "             AND SADRE1.TYPE = 'Distribution') " +
            "  AND TO_NUMBER(AIE.DPD) > (SELECT TO_NUMBER(SADRE2.NO_OF_DAYS_BEFORE_EMI_DATE) " +
            "                            FROM SMS_AND_AUTO_DISTRIBUTION_RULES_ENTITY SADRE2 " +
            "                            WHERE SADRE2.TYPE = 'Distribution') " +
            "  AND TO_NUMBER(AIE.NO_OF_INSTALLMENT_DUE) < (SELECT TO_NUMBER(SADRE3.UNPAID_INSTALLMENT_NUMBER) " +
            "                                              FROM SMS_AND_AUTO_DISTRIBUTION_RULES_ENTITY SADRE3 " +
            "                                              WHERE SADRE3.TYPE = 'Distribution')",nativeQuery = true)
    List<Tuple> finAllEligibleDistributionList1();

    @Query(value = "SELECT COUNT(AIE.LOANACNO) " +
            "FROM ACCOUNT_INFORMATION_ENTITY AIE " +
            "WHERE TO_NUMBER(AIE.OVERDUE) > 0 " +
            "  AND LTRIM(RTRIM(UPPER(AIE.LOANCLSTATUS))) " +
            "        IN (SELECT DISTINCT LTRIM(RTRIM(UPPER(LSE.NAME))) " +
            "            FROM SMS_AND_AUTO_DISTRIBUTION_RULES_ENTITY SADRE " +
            "                   LEFT JOIN SMS_AND_AUTO_DISTRIBUTION_RULES_ENTITY_LOAN_STATUS_ENTITY SADRELSE " +
            "                     ON SADRE.ID = SADRELSE.SMS_AND_AUTO_DISTRIBUTION_RULES_ENTITY_ID " +
            "                   LEFT JOIN LOAN_STATUS_ENTITY LSE ON SADRELSE.LOAN_STATUS_ENTITY_ID = LSE.ID " +
            "            WHERE LSE.ENABLED = 1 " +
            "              AND SADRE.TYPE = 'Distribution') " +
            "  AND LTRIM(RTRIM(UPPER(AIE.PRODUCT_CODE))) " +
            "        IN(SELECT DISTINCT LTRIM(RTRIM(UPPER(LTE.LOAN_TYPE))) " +
            "           FROM SMS_AND_AUTO_DISTRIBUTION_RULES_ENTITY SADRE1 " +
            "                  LEFT JOIN SMS_AND_AUTO_DISTRIBUTION_RULES_ENTITY_LOAN_TYPE_ENTITIES SADRELTE " +
            "                    ON SADRE1.ID = SADRELTE.SMS_AND_AUTO_DISTRIBUTION_RULES_ENTITY_ID " +
            "                  LEFT JOIN LOAN_TYPE_ENTITY LTE ON SADRELTE.LOAN_TYPE_ENTITIES_ID = LTE.ID " +
            "           WHERE LTE.ENABLED = 1 " +
            "             AND SADRE1.TYPE = 'Distribution') " +
            "  AND TO_NUMBER(AIE.DPD) > (SELECT TO_NUMBER(SADRE2.NO_OF_DAYS_BEFORE_EMI_DATE) " +
            "                            FROM SMS_AND_AUTO_DISTRIBUTION_RULES_ENTITY SADRE2 " +
            "                            WHERE SADRE2.TYPE = 'Distribution') " +
            "  AND TO_NUMBER(AIE.NO_OF_INSTALLMENT_DUE) < (SELECT TO_NUMBER(SADRE3.UNPAID_INSTALLMENT_NUMBER) " +
            "                                              FROM SMS_AND_AUTO_DISTRIBUTION_RULES_ENTITY SADRE3 " +
            "                                              WHERE SADRE3.TYPE = 'Distribution')",nativeQuery = true)
    int finAllEligibleDistributionListCount();

    @Query(value = "SELECT AIE.LOANACNO, " +
            "       AIE.CUSTOMER_NAME, " +
            "       AIE.MOBILE, " +
            "       AIE.BRANCH_MNEMONIC, " +
            "       AIE.PRODUCT_CODE, " +
            "       AIE.DEAL_REFERENCE, " +
            "       AIE.TOTAL_OUTSTANDING, " +
            "       AIE.OVERDUE, " +
            "       AIE.EMI_AMOUNT, " +
            "       AIE.NEXTEMIDATE, " +
            "       AIE.NO_OF_INSTALLMENT_DUE, " +
            "       AIE.LOANCLSTATUS " +
            "FROM ACCOUNT_INFORMATION_ENTITY AIE " +
            "WHERE TO_NUMBER(AIE.OVERDUE) > 0 " +
            "  AND AIE.LOANACNO LIKE %?1% " +
            "  AND LTRIM(RTRIM(UPPER(AIE.LOANCLSTATUS))) " +
            "        IN (SELECT DISTINCT LTRIM(RTRIM(UPPER(LSE.NAME))) " +
            "            FROM SMS_AND_AUTO_DISTRIBUTION_RULES_ENTITY SADRE " +
            "                   LEFT JOIN SMS_AND_AUTO_DISTRIBUTION_RULES_ENTITY_LOAN_STATUS_ENTITY SADRELSE " +
            "                     ON SADRE.ID = SADRELSE.SMS_AND_AUTO_DISTRIBUTION_RULES_ENTITY_ID " +
            "                   LEFT JOIN LOAN_STATUS_ENTITY LSE ON SADRELSE.LOAN_STATUS_ENTITY_ID = LSE.ID " +
            "            WHERE LSE.ENABLED = 1 " +
            "              AND SADRE.TYPE = 'Distribution') " +
            "  AND LTRIM(RTRIM(UPPER(AIE.PRODUCT_CODE))) " +
            "        IN(SELECT DISTINCT LTRIM(RTRIM(UPPER(LTE.LOAN_TYPE))) " +
            "           FROM SMS_AND_AUTO_DISTRIBUTION_RULES_ENTITY SADRE1 " +
            "                  LEFT JOIN SMS_AND_AUTO_DISTRIBUTION_RULES_ENTITY_LOAN_TYPE_ENTITIES SADRELTE " +
            "                    ON SADRE1.ID = SADRELTE.SMS_AND_AUTO_DISTRIBUTION_RULES_ENTITY_ID " +
            "                  LEFT JOIN LOAN_TYPE_ENTITY LTE ON SADRELTE.LOAN_TYPE_ENTITIES_ID = LTE.ID " +
            "           WHERE LTE.ENABLED = 1 " +
            "             AND SADRE1.TYPE = 'Distribution') " +
            "  AND TO_NUMBER(AIE.DPD) > (SELECT TO_NUMBER(SADRE2.NO_OF_DAYS_BEFORE_EMI_DATE) " +
            "                            FROM SMS_AND_AUTO_DISTRIBUTION_RULES_ENTITY SADRE2 " +
            "                            WHERE SADRE2.TYPE = 'Distribution') " +
            "  AND TO_NUMBER(AIE.NO_OF_INSTALLMENT_DUE) < (SELECT TO_NUMBER(SADRE3.UNPAID_INSTALLMENT_NUMBER) " +
            "                                              FROM SMS_AND_AUTO_DISTRIBUTION_RULES_ENTITY SADRE3 " +
            "                                              WHERE SADRE3.TYPE = 'Distribution')", nativeQuery = true)
    List<Tuple> finAllEligibleDistributionList(String accNo);






}