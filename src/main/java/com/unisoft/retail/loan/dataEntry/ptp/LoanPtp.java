package com.unisoft.retail.loan.dataEntry.ptp;

import com.unisoft.base.BaseInfo;
import com.unisoft.customerbasicinfo.CustomerBasicInfoEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.Expose;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * Common ResultSetMapping used for mapping PTP summary Named Queries to the
 * constructor of PtpStatusSummary class.
 * One ResultSetMapping for four queries as the named queries returns
 * same columns and requires same constructor to extract the values.
 *
 * Implemented by
 * At 28 February, 2021
 */
@SqlResultSetMappings({
        @SqlResultSetMapping(
                name = "loanPtpSummary",
                classes = {
                        @ConstructorResult(
                                targetClass = PtpStatusSummary.class,
                                columns = {
                                        @ColumnResult(name = "STATUS", type = String.class),
                                        @ColumnResult(name = "PTP_DETAILS_LIST", type = String.class),
                                        @ColumnResult(name = "TOTAL_ACCOUNT", type = Long.class),
                                        @ColumnResult(name = "OUTSTANDING_AMOUNT", type = Double.class)
                                }
                        )
                }
        )
})

/**
 * javax.persistence.NamedNativeQuery used to map native queries to LoanPtp entity.
 * The queries are accessible from any @Repository
 *
 * Implemented by
 * At 28 February, 2021
 */
@NamedNativeQueries({
        @NamedNativeQuery(name = "LoanPtp.getPtpSummaryByDealer",
                query = "SELECT LP.LOAN_PTP_STATUS                                                        AS STATUS, " +
                        "       REPLACE('[' || " +
                        "               RTRIM( " +
                        "                 XMLAGG( " +
                        "                   XMLELEMENT(e, '{' " +
                        "                                   || '\"accountNo\":\"' || LB.ACCOUNT_NO || '\", ' " +
                        "                                   || '\"accountName\":\"' || LB.ACCOUNT_NAME || '\", ' " +
                        "                                   || '\"amount\":' || LP.LOAN_AMOUNT || ', ' " +
                        "                                   || '\"dealerPin\":\"' || LD.DEALER_PIN || '\", ' " +
                        "                                   || '\"ptpDate\":\"' || TO_CHAR(LP.LOAN_PTP_DATE, 'DD-MON-YYYY') || '\", ' " +
                        "                                   || '\"ptpTime\":\"' || LP.LOAN_PTP_TIME || '\", ' " +
                        "                                   || '\"contactDetail\":\"' || LP.LOAN_CONTACT_DETAILS || '\", ' " +
                        "                                   || '\"contactLocation\":\"' || LP.LOAN_CONTACT_LOCATION || '\", ' " +
                        "                                   || '\"promisorDetails\":\"' || LP.LOAN_PROMISOR_DETAILS || '\", ' " +
//                        "                                   || '\"productName\":\"' || PTE.NAME || '\", ' " +
                        "                                   || '\"dpdBucket\":\"' || LD.DPD_BUCKET || '\"' " +
                        "                                   || '}', ',') " +
                        "                       .extract('//text()') ORDER BY LP.LOAN_PTP_DATE) " +
                        "                     .getclobval(), ', ') " +
                        "                 || ']', '&quot;', '\"') AS PTP_DETAILS_LIST, " +
                        "       COUNT(DISTINCT LP.CUSTOMER_ID)                                            AS TOTAL_ACCOUNT, " +
                        "       COALESCE(SUM(LP.LOAN_AMOUNT), 0)                                          AS OUTSTANDING_AMOUNT " +
                        " FROM LOAN_PTP LP " +
                        "       JOIN CUSTOMER_BASIC_INFO_ENTITY C ON LP.CUSTOMER_ID = C.ID " +
                        "       JOIN LOAN_ACCOUNT_BASIC_INFO LB ON C.ACCOUNT_NO = LB.ACCOUNT_NO " +
                        "       JOIN LOAN_ACCOUNT_DISTRIBUTION_INFO LD  ON LD.LOAN_ACCOUNT_BASIC_INFO_ID = LB.ID " +
//                        "       JOIN PRODUCT_TYPE_ENTITY PTE ON PTE.CODE = LD.SCHEME_CODE "+
                        " AND LD.DEALER_PIN = :dealerPin AND " +
                        "            LD.CREATED_DATE >= TRUNC(SYSDATE, 'MM') AND LD.latest = '1' " +
                        "WHERE LP.CREATED_DATE >= TRUNC(SYSDATE, 'MM') " +
                        "GROUP BY LP.LOAN_PTP_STATUS", resultSetMapping = "loanPtpSummary"
        ),

        @NamedNativeQuery(name = "LoanPtp.getPtpSummaryBySupervisor",
                query = "SELECT LP.LOAN_PTP_STATUS AS STATUS, " +
                        "       REPLACE('[' || " +
                        "               RTRIM( " +
                        "                 XMLAGG( " +
                        "                   XMLELEMENT(e, '{' " +
                        "                                   || '\"accountNo\":\"' || LB.ACCOUNT_NO || '\", ' " +
                        "                                   || '\"accountName\":\"' || LB.ACCOUNT_NAME || '\", ' " +
                        "                                   || '\"amount\":' || LP.LOAN_AMOUNT || ', ' " +
                        "                                   || '\"dealerPin\":\"' || LD.DEALER_PIN || '\", ' " +
                        "                                   || '\"ptpDate\":\"' || TO_CHAR(LP.LOAN_PTP_DATE, 'DD-MON-YYYY') || '\", ' " +
                        "                                   || '\"ptpTime\":\"' || LP.LOAN_PTP_TIME || '\", ' " +
                        "                                   || '\"contactDetail\":\"' || LP.LOAN_CONTACT_DETAILS || '\", ' " +
                        "                                   || '\"contactLocation\":\"' || LP.LOAN_CONTACT_LOCATION || '\", ' " +
                        "                                   || '\"promisorDetails\":\"' || LP.LOAN_PROMISOR_DETAILS || '\", ' " +
//                        "                                   || '\"productName\":\"' || PTE.NAME || '\", ' " +
                        "                                   || '\"dpdBucket\":\"' || LD.DPD_BUCKET || '\"' " +
                        "                                   || '}', ',') " +
                        "                       .extract('//text()') ORDER BY LP.LOAN_PTP_DATE) " +
                        "                     .getclobval(), ', ') " +
                        "                 || ']', '&quot;', '\"') AS PTP_DETAILS_LIST, " +
                        "COUNT(DISTINCT LP.CUSTOMER_ID) AS TOTAL_ACCOUNT, " +
                        "COALESCE(SUM(LP.LOAN_AMOUNT), 0) AS OUTSTANDING_AMOUNT " +

                        "FROM LOAN_PTP LP " +
                        "JOIN CUSTOMER_BASIC_INFO_ENTITY C ON LP.CUSTOMER_ID = C.ID " +
                        "JOIN LOAN_ACCOUNT_BASIC_INFO LB ON C.ACCOUNT_NO = LB.ACCOUNT_NO " +
                        "JOIN LOAN_ACCOUNT_DISTRIBUTION_INFO LD ON LD.LOAN_ACCOUNT_BASIC_INFO_ID = LB.ID  AND LD.latest = '1' " +
//                        "JOIN PRODUCT_TYPE_ENTITY PTE ON PTE.CODE = LD.SCHEME_CODE "+
                        "JOIN EMPLOYEE_INFO_ENTITY EMP ON EMP.PIN = LD.DEALER_PIN " +
                        "JOIN PEOPLE_ALLOCATION_LOGIC PAL ON PAL.DEALER_ID = EMP.ID " +
                        "JOIN EMPLOYEE_INFO_ENTITY ESP on ESP.ID = PAL.SUPERVISOR_ID " +

                        "WHERE ESP.PIN = :supervisorPin AND LD.CREATED_DATE >= TRUNC(SYSDATE, 'MM')   AND LP.CREATED_DATE >= TRUNC(SYSDATE, 'MM') " +
                        "GROUP BY LP.LOAN_PTP_STATUS", resultSetMapping = "loanPtpSummary"
        ),

        @NamedNativeQuery(name = "LoanPtp.getPtpSummaryByTeamLeader",
                query = "SELECT LP.LOAN_PTP_STATUS AS STATUS, " +
                        "       REPLACE('[' || " +
                        "               RTRIM( " +
                        "                 XMLAGG( " +
                        "                   XMLELEMENT(e, '{' " +
                        "                                   || '\"accountNo\":\"' || LB.ACCOUNT_NO || '\", ' " +
                        "                                   || '\"accountName\":\"' || LB.ACCOUNT_NAME || '\", ' " +
                        "                                   || '\"amount\":' || LP.LOAN_AMOUNT || ', ' " +
                        "                                   || '\"dealerPin\":\"' || LD.DEALER_PIN || '\", ' " +
                        "                                   || '\"ptpDate\":\"' || TO_CHAR(LP.LOAN_PTP_DATE, 'DD-MON-YYYY') || '\", ' " +
                        "                                   || '\"ptpTime\":\"' || LP.LOAN_PTP_TIME || '\", ' " +
                        "                                   || '\"contactDetail\":\"' || LP.LOAN_CONTACT_DETAILS || '\", ' " +
                        "                                   || '\"contactLocation\":\"' || LP.LOAN_CONTACT_LOCATION || '\", ' " +
                        "                                   || '\"promisorDetails\":\"' || LP.LOAN_PROMISOR_DETAILS || '\", ' " +
//                        "                                   || '\"productName\":\"' || PTE.NAME || '\", ' " +
                        "                                   || '\"dpdBucket\":\"' || LD.DPD_BUCKET || '\"' " +
                        "                                   || '}', ',') " +
                        "                       .extract('//text()') ORDER BY LP.LOAN_PTP_DATE) " +
                        "                     .getclobval(), ', ') " +
                        "                 || ']', '&quot;', '\"') AS PTP_DETAILS_LIST, " +
                        "COUNT(DISTINCT LP.CUSTOMER_ID) AS TOTAL_ACCOUNT, " +
                        "COALESCE(SUM(LP.LOAN_AMOUNT), 0) AS OUTSTANDING_AMOUNT " +

                        "FROM LOAN_PTP LP " +
                        "JOIN CUSTOMER_BASIC_INFO_ENTITY C ON LP.CUSTOMER_ID = C.ID " +
                        "JOIN LOAN_ACCOUNT_BASIC_INFO LB ON C.ACCOUNT_NO = LB.ACCOUNT_NO " +
                        "JOIN LOAN_ACCOUNT_DISTRIBUTION_INFO LD ON LD.LOAN_ACCOUNT_BASIC_INFO_ID = LB.ID  AND LD.latest = '1' " +
//                        "JOIN PRODUCT_TYPE_ENTITY PTE ON PTE.CODE = LD.SCHEME_CODE "+
                        "JOIN EMPLOYEE_INFO_ENTITY EMP ON EMP.PIN = LD.DEALER_PIN " +
                        "JOIN PEOPLE_ALLOCATION_LOGIC PAL ON PAL.DEALER_ID = EMP.ID " +
                        "JOIN EMPLOYEE_INFO_ENTITY ETM on ETM.ID = PAL.TEAM_LEAD_ID " +

                        "WHERE ETM.PIN = :teamLeaderPin AND LD.CREATED_DATE >= TRUNC(SYSDATE, 'MM')   AND LP.CREATED_DATE >= TRUNC(SYSDATE, 'MM') " +
                        "GROUP BY LP.LOAN_PTP_STATUS", resultSetMapping = "loanPtpSummary"
        ),

        @NamedNativeQuery(name = "LoanPtp.getPtpSummaryByManager",
                query = "SELECT LP.LOAN_PTP_STATUS AS STATUS, " +
                        "       REPLACE('[' || " +
                        "               RTRIM( " +
                        "                 XMLAGG( " +
                        "                   XMLELEMENT(e, '{' " +
                        "                                   || '\"accountNo\":\"' || LB.ACCOUNT_NO || '\", ' " +
                        "                                   || '\"accountName\":\"' || LB.ACCOUNT_NAME || '\", ' " +
                        "                                   || '\"amount\":' || LP.LOAN_AMOUNT || ', ' " +
                        "                                   || '\"dealerPin\":\"' || LD.DEALER_PIN || '\", ' " +
                        "                                   || '\"ptpDate\":\"' || TO_CHAR(LP.LOAN_PTP_DATE, 'DD-MON-YYYY') || '\", ' " +
                        "                                   || '\"ptpTime\":\"' || LP.LOAN_PTP_TIME || '\", ' " +
                        "                                   || '\"contactDetail\":\"' || LP.LOAN_CONTACT_DETAILS || '\", ' " +
                        "                                   || '\"contactLocation\":\"' || LP.LOAN_CONTACT_LOCATION || '\", ' " +
                        "                                   || '\"promisorDetails\":\"' || LP.LOAN_PROMISOR_DETAILS || '\", ' " +
//                        "                                   || '\"productName\":\"' || PTE.NAME || '\", ' " +
                        "                                   || '\"dpdBucket\":\"' || LD.DPD_BUCKET || '\"' " +
                        "                                   || '}', ',') " +
                        "                       .extract('//text()') ORDER BY LP.LOAN_PTP_DATE) " +
                        "                     .getclobval(), ', ') " +
                        "                 || ']', '&quot;', '\"') AS PTP_DETAILS_LIST, " +
                        "COUNT(DISTINCT LP.CUSTOMER_ID) AS TOTAL_ACCOUNT, " +
                        "COALESCE(SUM(LP.LOAN_AMOUNT), 0) AS OUTSTANDING_AMOUNT " +

                        "FROM LOAN_PTP LP " +
                        "JOIN CUSTOMER_BASIC_INFO_ENTITY C ON LP.CUSTOMER_ID = C.ID " +
                        "JOIN LOAN_ACCOUNT_BASIC_INFO LB ON C.ACCOUNT_NO = LB.ACCOUNT_NO " +
                        "JOIN LOAN_ACCOUNT_DISTRIBUTION_INFO LD ON LD.LOAN_ACCOUNT_BASIC_INFO_ID = LB.ID  AND LD.latest = '1' " +
//                        "JOIN PRODUCT_TYPE_ENTITY PTE ON PTE.CODE = LD.SCHEME_CODE "+
                        "JOIN EMPLOYEE_INFO_ENTITY EMP ON EMP.PIN = LD.DEALER_PIN " +
                        "JOIN PEOPLE_ALLOCATION_LOGIC PAL ON PAL.DEALER_ID = EMP.ID " +
                        "JOIN EMPLOYEE_INFO_ENTITY EMN on EMN.ID = PAL.MANAGER_ID " +

                        "WHERE EMN.PIN = :managerPin AND LD.CREATED_DATE >= TRUNC(SYSDATE, 'MM')  AND LP.CREATED_DATE >= TRUNC(SYSDATE, 'MM') " +
                        "GROUP BY LP.LOAN_PTP_STATUS", resultSetMapping = "loanPtpSummary"
        ),

        @NamedNativeQuery(name = "LoanPtp.getSamdPtpSummaryByDealer",
                query = "SELECT LP.LOAN_PTP_STATUS                                                        AS STATUS, " +
                        "       REPLACE('[' || " +
                        "               RTRIM( " +
                        "                 XMLAGG( " +
                        "                   XMLELEMENT(e, '{' " +
                        "                                   || '\"accountNo\":\"' || LB.ACCOUNT_NO || '\", ' " +
                        "                                   || '\"accountName\":\"' || LB.ACCOUNT_NAME || '\", ' " +
                        "                                   || '\"amount\":' || LP.LOAN_AMOUNT || ', ' " +
                        "                                   || '\"dealerPin\":\"' || LD.DEALER_PIN || '\", ' " +
                        "                                   || '\"ptpDate\":\"' || TO_CHAR(LP.LOAN_PTP_DATE, 'DD-MON-YYYY') || '\", ' " +
                        "                                   || '\"ptpTime\":\"' || LP.LOAN_PTP_TIME || '\", ' " +
                        "                                   || '\"contactDetail\":\"' || LP.LOAN_CONTACT_DETAILS || '\", ' " +
                        "                                   || '\"contactLocation\":\"' || LP.LOAN_CONTACT_LOCATION || '\", ' " +
                        "                                   || '\"promisorDetails\":\"' || LP.LOAN_PROMISOR_DETAILS || '\"' " +
                        "                                   || '}', ',') " +
                        "                       .extract('//text()') ORDER BY LP.LOAN_PTP_DATE) " +
                        "                     .getclobval(), ', ') " +
                        "                 || ']', '&quot;', '\"') AS PTP_DETAILS_LIST, " +
                        "       COUNT(DISTINCT LP.CUSTOMER_ID)                                            AS TOTAL_ACCOUNT, " +
                        "       COALESCE(SUM(LP.LOAN_AMOUNT), 0)                                          AS OUTSTANDING_AMOUNT " +
                        "FROM LOAN_PTP LP " +
                        "       JOIN CUSTOMER_BASIC_INFO_ENTITY C ON LP.CUSTOMER_ID = C.ID " +
                        "       JOIN LOAN_ACCOUNT_BASIC_INFO LB ON C.ACCOUNT_NO = LB.ACCOUNT_NO " +
                        "       JOIN LMS_COLLECTION_SAM_LOAN_ACCOUNT_DISTRIBUTIONS LD " +
                        "         ON LD.LOAN_ACCOUNT_BASIC_INFO_ID = LB.ID AND LD.DEALER_PIN = :dealerPin AND " +
                        "            LD.CREATED_DATE >= TRUNC(SYSDATE, 'MM') AND LD.latest = '1' " +
                        "WHERE LP.CREATED_DATE >= TRUNC(SYSDATE, 'MM') " +
                        "GROUP BY LP.LOAN_PTP_STATUS", resultSetMapping = "loanPtpSummary"
        )
})

@Data
@Entity
public class LoanPtp extends BaseInfo {

    @Expose
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    //    @Expose
//    private Long customer_id;
    @Expose
    private String loan_amount;
    @Expose
    private String loan_ptp_time;
    @Expose
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern ="dd-MM-yyyy" )
    private Date loan_ptp_date;
    @Expose
    private String loan_contact_details;
    @Expose
    private String loan_contact_details_id;
    @Expose
    private String loan_contact_location;
    @Expose
    private String loan_contact_location_id;
    @Expose
    private String loan_promisor_details;
    @Expose
    private String loan_promisor_details_id;
    @Expose
    private String loan_ptp_remarks;
    @Expose
    private String loan_ptp_status = "kept";
    @Expose
    private String pin;
    @Expose
    private String username;

    private String contactLoacation;

    @Transient
    private String loan_ptp_dates;

    @Transient
    private String accNo;

    @Transient
    private String considerAsAttempt;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "CUSTOMER_ID")
    private CustomerBasicInfoEntity customerBasicInfo;

}
