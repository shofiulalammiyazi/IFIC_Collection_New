package com.csinfotechbd.loanApi.utils;

import com.csinfotechbd.collection.dashboard.AdvanceSearchPayload;
import com.csinfotechbd.collection.dashboard.AdvancedSearchPayload;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;


@Component
public class ParameterManager {

    public Map<String, Object> getPersonalInfoParams(
            String customerNo) {
        Map<String, Object> params = new LinkedHashMap<>();
        params.put("P_CUSTOMERNO", customerNo);
        return params;
    }

    public Map<String, Object> getPersonalInfoParams(
            String customerNo, String email, String tin,
            String nid, String dateOfBirth, String mobileNo,
            String fatherName, String motherName, String customerName) {
        Map<String, Object> params = new LinkedHashMap<>();
        params.put("P_CUSTOMERNO", customerNo);
        params.put("P_EMAIL", email);
        params.put("P_TIN_NO", tin);
        params.put("P_NID", nid);
        params.put("P_DOB", dateOfBirth);
        params.put("P_MOB_NO", mobileNo);
        params.put("P_FATHER_NAME", fatherName);
        params.put("P_MOTHER_NAME", motherName);
        params.put("P_CUSTOMER_NAME", customerName);
        return params;
    }

    public Map<String, Object> getCustomerIdParams(@Nullable String customerNo) {
        Map<String, Object> params = new LinkedHashMap<>();
        params.put("P_CUSTOMERNO", customerNo);
        return params;
    }

    public Map<String, Object> getLienBlockParams(String branchCode, String accountNo) {
        Map<String, Object> params = new LinkedHashMap<>();
        params.put("P_BRANCH_CODE", branchCode);
        params.put("P_AC_NO", accountNo);
        return params;
    }

    public Map<String, Object> getAccountParams(@Nullable String accountNo) {
        Map<String, Object> params = new LinkedHashMap<>();
        params.put("P_ACCOUNT", accountNo);
        return params;
    }

    public Map<String, Object> getLoanListingParams(@Nullable String accountNo){
        Map<String, Object> params = new LinkedHashMap<>();
      
        params.put("p_Account_No",accountNo);
        params.put("cif","");
        params.put("r_m_code","");
        params.put("r_m_date",null);

        return params;
    }

    public Map<String, Object> getLoanAccStatementParams(String accountNo, Date startDate, Date endDate) {
        Map<String, Object> params = new LinkedHashMap<>();
        params.put("P_ACCOUNT", accountNo);
        params.put("P_FDATE", new java.sql.Date(startDate.getTime()));
        params.put("P_TDATE", new java.sql.Date(endDate.getTime()));
        return params;
    }

    public Map<String, Object> getLinkAccStatementParams(String accountNo, Date startDate, Date endDate) {
        Map<String, Object> params = new LinkedHashMap<>();
        params.put("P_AC_NO", accountNo);
        params.put("P_FROM_DATE", new java.sql.Date(startDate.getTime()));
        params.put("P_TO_DATE", new java.sql.Date(endDate.getTime()));
        return params;
    }

    public Map<String, Object> getBranchParams(String branchCode) {
        Map<String, Object> params = new LinkedHashMap<>();
        params.put("P_BRANCH_CODE", branchCode);
        return params;
    }

    public Map<String, Object> getCasaOrTdrdAccountParams(String branchCode, String accountNo) {
        Map<String, Object> params = new LinkedHashMap<>();
        params.put("P_BRANCH", branchCode);
        params.put("P_ACCOUNT", accountNo);
        params.put("P_OLDACFLAG", "U");
        return params;
    }

    public Map<String, Object> getAdvanceSearchParams(AdvanceSearchPayload payload) {
        Map<String, Object> params = new LinkedHashMap<>();
        params.put("p_date", "31-JUL-21");
        params.put("p_Account_No", payload.getAccountNo());
        params.put("p_cif_no", payload.getCif());
        params.put("p_Customer_Name", payload.getCustomerName());
        params.put("p_Mother_Name", payload.getMotherName());
        params.put("p_Mobile_No", payload.getMobile());
        params.put("p_NID", payload.getNationalId());
        params.put("p_DOB", payload.getDateOfBirth());
        params.put("p_Email", payload.getEmail());
        params.put("p_Passport_No", payload.getPassport());
        params.put("p_Organization", payload.getOrganization());
        params.put("p_Link_Acc", payload.getLinkAccount());
        params.put("p_TIN", payload.getTin());
        params.put("p_Auto_Debit_Acc", payload.getAutoDebit());
        params.put("p_Cls_Flag", payload.getClsFlag());
        params.put("p_Status", payload.getStatus());
        return params;
    }


    //mrthod for SAMD advanced search
    public Map<String, Object> getSamdAdvancedSearchParams(AdvancedSearchPayload payload) {
        Map<String, Object> params = new LinkedHashMap<>();

        params.put("p_ld_number",payload.getLdNumber());
        params.put("p_loan_account_number",payload.getLoanAccountNumber());
        params.put("p_loan_account_name",payload.getLoanAccountName());
        params.put("p_case_number",payload.getLoanAccountNumber());
        params.put("p_customer_id",payload.getCustomerId());
        params.put("p_lln",payload.getLln());
        params.put("p_lawyers_id",payload.getLawyersId());
        params.put("p_lawyers_name",payload.getLawyersName());
        params.put("p_case_type",payload.getCaseType());
        params.put("p_case_status",payload.getCaseStatus());
        params.put("p_next_date",payload.getNextDate());
        params.put("p_next_date_status",payload.getNextDateStatus());
        params.put("p_borrowers_organization",payload.getBorrowersOrganization());
        params.put("p_borrowers_name",payload.getBorrowersName());
        params.put("p_borrowers_dob",payload.getBorrowersDob());
        params.put("p_borrowers_dl",payload.getBorrowersDrivingLicense());
        params.put("p_borrowers_mobile_no",payload.getBorrowersMobileNo());
        params.put("p_existing_case_status",payload.getExistingCaseStatus());
        params.put("p_borrowers_email",payload.getBorrowersEmail());
        params.put("p_borrowers_nid",payload.getBorrowersNid());
        params.put("p_result_of_the_case",payload.getResultOfTheCase());
        params.put("p_borrowers_passport",payload.getBorrowersPassport());
        params.put("p_borrowers_father_name",payload.getBorrowersFathersName());
        params.put("p_status",payload.getAcStatus());

        return params;
    }

    public Map<String, Object> getRmInfoParams(String accNo, String cif, String rmCode, Date date) {
        Map<String, Object> params = new LinkedHashMap<>();
        params.put("p_Account_No", accNo);
        params.put("cif", cif);
        params.put("r_m_code", rmCode);
        params.put("r_m_date", date);
        return params;
    }

    public Map<String, Object> getInterestRateHistoryParameter(String accNo, String rateType) {
        Map<String, Object> params = new LinkedHashMap<>();
        params.put("p_Account_No", accNo);
        params.put("r_type", rateType);
        return params;
    }

//    public Map<String, Object> getInterestDetailsParams(String accountNo) {
//        Map<String, Object> params = new LinkedHashMap<>();
//        params.put("P_ACCOUNT", accountNo);
//        return params;
//    }
//    public Map<String, Object> getAccScheduleDetailsParams(String accountNo) {
//        Map<String, Object> params = new LinkedHashMap<>();
//        params.put("P_ACCOUNT", accountNo);
//        return params;
//    }

}
