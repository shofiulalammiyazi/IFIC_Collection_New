package com.unisoft.retail.loan.dataEntry.CustomerUpdate.accountInformationRepository;

import com.unisoft.collection.dashboard.AdvanceSearchPayload;
import com.unisoft.detailsOfCollection.cardviewmodels.AccountInformation;
import com.unisoft.loanApi.model.AdvanceSearchDataModel;
import com.unisoft.retail.loan.dataEntry.CustomerUpdate.accountInformation.AccountInformationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AccountInformationRepository extends JpaRepository<AccountInformationEntity, Long> {

    AccountInformationEntity getByLoanACNo(String accountNumber);

    @Query(value = "SELECT * FROM ACCOUNT_INFORMATION_ENTITY WHERE REPLACE(LOANACNO,' ','') = ?1", nativeQuery = true)
    public AccountInformationEntity getByLoanAccountNo(String accountNo);

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
            "   OR CUSTOMER_NAME = ?2 " +
            "   OR MOTHER_NAME = ?3 " +
            "   OR MOBILE = ?4 " +
            "   OR NID = ?5 " +
            "   OR DOB = ?6 " +
            "   OR EMAIL = ?7 " +
            "   OR LINK_MOTHER_ACCOUNT_NO = ?8 " +
//            "   OR CUSTOMER_ID = ?9 " +
            "   OR TIN = ?9", nativeQuery = true)
    public List<AccountInformationEntity> advancedSearchDashboard(String accountNo, String customerName, String motherName, String mobileNo, String nid, String dob, String email, String linkAccount, String tin);
}
