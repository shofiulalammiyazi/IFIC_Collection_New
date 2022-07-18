package com.csinfotechbd.retail.card.dataEntry.ptp;

import com.csinfotechbd.base.BaseInfo;
import com.csinfotechbd.customerbasicinfo.CustomerBasicInfoEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.Expose;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@SqlResultSetMappings({
        @SqlResultSetMapping(
                name = "cardPtpSummary",
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


@Data
@Entity


@NamedNativeQueries({
        @NamedNativeQuery(name = "CardPtp.getPtpSummaryByDealer",
                query = "SELECT LP.CARD_PTP_STATUS AS STATUS, " +
                        "       REPLACE('[' || " +
                        "               RTRIM( " +
                        "                 XMLAGG( " +
                        "                   XMLELEMENT(e, '{' " +
                        "                                   || '\"accountNo\":\"' || LB.CARD_NO || '\", ' " +
                        "                                   || '\"accountName\":\"' || LB.CONTRACT_ID || '\", ' " +
                        "                                   || '\"amount\":' || LP.CARD_AMOUNT || ', ' " +
                        "                                   || '\"dealerPin\":\"' || LD.DEALER_PIN || '\", ' " +
                        "                                   || '\"ptpDate\":\"' || TO_CHAR(LP.CARD_PTP_DATE, 'DD-MON-YYYY') || '\", ' " +
                        "                                   || '\"ptpTime\":\"' || LP.CARD_PTP_TIME || '\", ' " +
                        "                                   || '\"contactDetail\":\"' || LP.CARD_CONTACT_DETAILS || '\", ' " +
                        "                                   || '\"contactLocation\":\"' || LP.CARD_CONTACT_LOCATION || '\", ' " +
                        "                                   || '\"promisorDetails\":\"' || LP.CARD_PROMISOR_DETAILS || '\", ' " +
//                        "                                   || '\"productName\":\"' || PTE.NAME || '\", ' " +
                        "                                   || '\"dpdBucket\":\"' || LD.AGE_CODE || '\"' " +
                        "                                   || '}', ',') " +
                        "                       .extract('//text()') ORDER BY LP.CARD_PTP_DATE) " +
                        "                     .getclobval(), ', ') " +
                        "                 || ']', '&quot;', '\"') AS PTP_DETAILS_LIST, " +
                        "       COUNT(DISTINCT LP.CUSTOMER_ID)                                            AS TOTAL_ACCOUNT, " +
                        "       COALESCE(SUM(LP.CARD_AMOUNT), 0)                                          AS OUTSTANDING_AMOUNT " +
                        " FROM CARD_PTP LP " +
                        "       JOIN CUSTOMER_BASIC_INFO_ENTITY C ON LP.CUSTOMER_ID = C.ID " +
                        "       JOIN CARD_ACCOUNT_BASIC_INFO LB ON C.ACCOUNT_NO = LB.CONTRACT_ID " +
                        "       JOIN CARD_ACCOUNT_DISTRIBUTION_INFO LD  ON LD.CARD_ACCOUNT_BASIC_INFO_ID = LB.ID " +
//                        "       JOIN PRODUCT_TYPE_ENTITY PTE ON PTE.CODE = LD.SCHEME_CODE "+
                        " AND LD.DEALER_PIN = :dealerPin AND " +
                        "            LD.CREATED_DATE >= TRUNC(SYSDATE, 'MM') AND LD.latest = '1' " +
                        "WHERE LP.CREATED_DATE >= TRUNC(SYSDATE, 'MM') " +
                        "GROUP BY LP.CARD_PTP_STATUS", resultSetMapping = "cardPtpSummary"
        ),
        @NamedNativeQuery(name = "CardPtp.getPtpSummaryBySupervisor",
                query = "SELECT LP.CARD_PTP_STATUS AS STATUS, " +
                        "       REPLACE('[' || " +
                        "               RTRIM( " +
                        "                 XMLAGG( " +
                        "                   XMLELEMENT(e, '{' " +
                        "                                   || '\"accountNo\":\"' || LB.CARD_NO || '\", ' " +
                        "                                   || '\"accountName\":\"' || LB.CONTRACT_ID || '\", ' " +
                        "                                   || '\"amount\":' || LP.CARD_AMOUNT || ', ' " +
                        "                                   || '\"dealerPin\":\"' || LD.DEALER_PIN || '\", ' " +
                        "                                   || '\"ptpDate\":\"' || TO_CHAR(LP.CARD_PTP_DATE, 'DD-MON-YYYY') || '\", ' " +
                        "                                   || '\"ptpTime\":\"' || LP.CARD_PTP_TIME || '\", ' " +
                        "                                   || '\"contactDetail\":\"' || LP.CARD_CONTACT_DETAILS || '\", ' " +
                        "                                   || '\"contactLocation\":\"' || LP.CARD_CONTACT_LOCATION || '\", ' " +
                        "                                   || '\"promisorDetails\":\"' || LP.CARD_PROMISOR_DETAILS || '\", ' " +
//                        "                                   || '\"productName\":\"' || PTE.NAME || '\", ' " +
                        "                                   || '\"dpdBucket\":\"' || LD.AGE_CODE || '\"' " +
                        "                                   || '}', ',') " +
                        "                       .extract('//text()') ORDER BY LP.CARD_PTP_DATE) " +
                        "                     .getclobval(), ', ') " +
                        "                 || ']', '&quot;', '\"') AS PTP_DETAILS_LIST, " +
                        "COUNT(DISTINCT LP.CUSTOMER_ID) AS TOTAL_ACCOUNT, " +
                        "COALESCE(SUM(LP.CARD_AMOUNT), 0) AS OUTSTANDING_AMOUNT " +
                        
                        "FROM CARD_PTP LP " +
                        "JOIN CUSTOMER_BASIC_INFO_ENTITY C ON LP.CUSTOMER_ID = C.ID " +
                        "JOIN CARD_ACCOUNT_BASIC_INFO LB ON C.ACCOUNT_NO = LB.CONTRACT_ID " +
                        "JOIN CARD_ACCOUNT_DISTRIBUTION_INFO LD ON LD.CARD_ACCOUNT_BASIC_INFO_ID = LB.ID  AND LD.latest = '1' " +
//                        "JOIN PRODUCT_TYPE_ENTITY PTE ON PTE.CODE = LD.SCHEME_CODE "+
                        "JOIN EMPLOYEE_INFO_ENTITY EMP ON EMP.PIN = LD.DEALER_PIN " +
                        "JOIN PEOPLE_ALLOCATION_LOGIC PAL ON PAL.DEALER_ID = EMP.ID " +
                        "JOIN EMPLOYEE_INFO_ENTITY ESP on ESP.ID = PAL.SUPERVISOR_ID " +
                        
                        "WHERE ESP.PIN = :supervisorPin AND LD.CREATED_DATE >= TRUNC(SYSDATE, 'MM')   AND LP.CREATED_DATE >= TRUNC(SYSDATE, 'MM') " +
                        "GROUP BY LP.CARD_PTP_STATUS", resultSetMapping = "cardPtpSummary"
        ),
        @NamedNativeQuery(name = "CardPtp.getPtpSummaryByTeamLeader",
                query = "SELECT LP.CARD_PTP_STATUS AS STATUS, " +
                        "       REPLACE('[' || " +
                        "               RTRIM( " +
                        "                 XMLAGG( " +
                        "                   XMLELEMENT(e, '{' " +
                        "                                   || '\"accountNo\":\"' || LB.CARD_NO || '\", ' " +
                        "                                   || '\"accountName\":\"' || LB.CONTRACT_ID || '\", ' " +
                        "                                   || '\"amount\":' || LP.CARD_AMOUNT || ', ' " +
                        "                                   || '\"dealerPin\":\"' || LD.DEALER_PIN || '\", ' " +
                        "                                   || '\"ptpDate\":\"' || TO_CHAR(LP.CARD_PTP_DATE, 'DD-MON-YYYY') || '\", ' " +
                        "                                   || '\"ptpTime\":\"' || LP.CARD_PTP_TIME || '\", ' " +
                        "                                   || '\"contactDetail\":\"' || LP.CARD_CONTACT_DETAILS || '\", ' " +
                        "                                   || '\"contactLocation\":\"' || LP.CARD_CONTACT_LOCATION || '\", ' " +
                        "                                   || '\"promisorDetails\":\"' || LP.CARD_PROMISOR_DETAILS || '\", ' " +
//                        "                                   || '\"productName\":\"' || PTE.NAME || '\", ' " +
                        "                                   || '\"dpdBucket\":\"' || LD.AGE_CODE || '\"' " +
                        "                                   || '}', ',') " +
                        "                       .extract('//text()') ORDER BY LP.CARD_PTP_DATE) " +
                        "                     .getclobval(), ', ') " +
                        "                 || ']', '&quot;', '\"') AS PTP_DETAILS_LIST, " +
                        "COUNT(DISTINCT LP.CUSTOMER_ID) AS TOTAL_ACCOUNT, " +
                        "COALESCE(SUM(LP.CARD_AMOUNT), 0) AS OUTSTANDING_AMOUNT " +
                        
                        "FROM CARD_PTP LP " +
                        "JOIN CUSTOMER_BASIC_INFO_ENTITY C ON LP.CUSTOMER_ID = C.ID " +
                        "JOIN CARD_ACCOUNT_BASIC_INFO LB ON C.ACCOUNT_NO = LB.CONTRACT_ID " +
                        "JOIN CARD_ACCOUNT_DISTRIBUTION_INFO LD ON LD.CARD_ACCOUNT_BASIC_INFO_ID = LB.ID  AND LD.latest = '1' " +
//                        "JOIN PRODUCT_TYPE_ENTITY PTE ON PTE.CODE = LD.SCHEME_CODE "+
                        "JOIN EMPLOYEE_INFO_ENTITY EMP ON EMP.PIN = LD.DEALER_PIN " +
                        "JOIN PEOPLE_ALLOCATION_LOGIC PAL ON PAL.DEALER_ID = EMP.ID " +
                        "JOIN EMPLOYEE_INFO_ENTITY ETM on ETM.ID = PAL.TEAM_LEAD_ID " +
                        
                        "WHERE ETM.PIN = :teamLeaderPin AND LD.CREATED_DATE >= TRUNC(SYSDATE, 'MM')   AND LP.CREATED_DATE >= TRUNC(SYSDATE, 'MM') " +
                        "GROUP BY LP.CARD_PTP_STATUS", resultSetMapping = "cardPtpSummary"
        ),
        
        @NamedNativeQuery(name = "CardPtp.getPtpSummaryByManager",
                query = "SELECT LP.CARD_PTP_STATUS AS STATUS, " +
                        "       REPLACE('[' || " +
                        "               RTRIM( " +
                        "                 XMLAGG( " +
                        "                   XMLELEMENT(e, '{' " +
                        "                                   || '\"accountNo\":\"' || LB.CARD_NO|| '\", ' " +
                        "                                   || '\"accountName\":\"' || LB.CONTRACT_ID || '\", ' " +
                        "                                   || '\"amount\":' || LP.CARD_AMOUNT|| ', ' " +
                        "                                   || '\"dealerPin\":\"' || LD.DEALER_PIN || '\", ' " +
                        "                                   || '\"ptpDate\":\"' || TO_CHAR(LP.CARD_PTP_DATE, 'DD-MON-YYYY') || '\", ' " +
                        "                                   || '\"ptpTime\":\"' || LP.CARD_PTP_TIME || '\", ' " +
                        "                                   || '\"contactDetail\":\"' || LP.CARD_CONTACT_DETAILS|| '\", ' " +
                        "                                   || '\"contactLocation\":\"' || LP.CARD_CONTACT_LOCATION|| '\", ' " +
                        "                                   || '\"promisorDetails\":\"' || LP.CARD_PROMISOR_DETAILS || '\", ' " +
//                        "                                   || '\"productName\":\"' || PTE.NAME || '\", ' " +
                        "                                   || '\"dpdBucket\":\"' || LD.AGE_CODE || '\"' " +
                        "                                   || '}', ',') " +
                        "                       .extract('//text()') ORDER BY LP.CARD_PTP_DATE) " +
                        "                     .getclobval(), ', ') " +
                        "                 || ']', '&quot;', '\"') AS PTP_DETAILS_LIST, " +
                        "COUNT(DISTINCT LP.CUSTOMER_ID) AS TOTAL_ACCOUNT, " +
                        "COALESCE(SUM(LP.CARD_AMOUNT), 0) AS OUTSTANDING_AMOUNT " +
                        
                        "FROM CARD_PTP LP " +
                        "JOIN CUSTOMER_BASIC_INFO_ENTITY C ON LP.CUSTOMER_ID = C.ID " +
                        "JOIN CARD_ACCOUNT_BASIC_INFO LB ON C.ACCOUNT_NO = LB.CONTRACT_ID " +
                        "JOIN CARD_ACCOUNT_DISTRIBUTION_INFO LD ON LD.CARD_ACCOUNT_BASIC_INFO_ID = LB.ID  AND LD.latest = '1' " +
//                        "JOIN PRODUCT_TYPE_ENTITY PTE ON PTE.CODE = LD.SCHEME_CODE "+
                        "JOIN EMPLOYEE_INFO_ENTITY EMP ON EMP.PIN = LD.DEALER_PIN " +
                        "JOIN PEOPLE_ALLOCATION_LOGIC PAL ON PAL.DEALER_ID = EMP.ID " +
                        "JOIN EMPLOYEE_INFO_ENTITY EMN on EMN.ID = PAL.MANAGER_ID " +
                        
                        "WHERE EMN.PIN = :managerPin AND LD.CREATED_DATE >= TRUNC(SYSDATE, 'MM')  AND LP.CREATED_DATE >= TRUNC(SYSDATE, 'MM') " +
                        "GROUP BY LP.CARD_PTP_STATUS", resultSetMapping = "cardPtpSummary"
        ),
})

public class CardPtp extends BaseInfo {

    @Expose
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Long id;

    private String contractNo;

    @Expose
    private String card_amount;

    @Expose
    private String card_ptp_time;

    @Expose
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern ="yyyy-MM-dd" )
    private Date card_ptp_date;
    
    @Expose
    private String pin;
    
    @Expose
    private String username;

    @Expose
    private String card_contact_details;

    @Expose
    private String card_contact_details_id;

    @Expose
    private String card_contact_location;

    @Expose
    private String card_contact_location_id;

    @Expose
    private String card_promisor_details;

    @Expose
    private String card_promisor_details_id;

    @Expose
    private String card_remarks;

    @Expose
    private String card_ptp_status = "Kept";


    @Transient
    private String card_ptp_dates;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "CUSTOMER_ID")
    private CustomerBasicInfoEntity customerBasicInfo;

}
