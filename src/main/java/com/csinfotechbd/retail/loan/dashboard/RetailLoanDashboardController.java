package com.csinfotechbd.retail.loan.dashboard;

import com.csinfotechbd.collection.allocationLogic.PeopleAllocationLogicInfo;
import com.csinfotechbd.collection.allocationLogic.PeopleAllocationLogicService;
import com.csinfotechbd.collection.dashboard.AdvanceSearchPayload;
import com.csinfotechbd.collection.distribution.loan.loanAccountDistribution.LoanAccountDistributionInfo;
import com.csinfotechbd.collection.distribution.loan.loanAccountDistribution.LoanAccountDistributionService;
import com.csinfotechbd.collection.distribution.loan.loanAccountDistribution.LoanAccountDistributionSummary;
import com.csinfotechbd.collection.settings.employee.EmployeeInfoEntity;
import com.csinfotechbd.collection.settings.employee.EmployeeService;
import com.csinfotechbd.customerloanprofile.followup.DealerWiseFollowUpSummary;
import com.csinfotechbd.customerloanprofile.followup.FollowUpEntity;
import com.csinfotechbd.customerloanprofile.followup.FollowUpService;
import com.csinfotechbd.customerloanprofile.followup.FollowUpSummaryModel;
import com.csinfotechbd.customerloanprofile.loanpayment.DealerWisePayment;
import com.csinfotechbd.customerloanprofile.loanpayment.LoanPaymentSummaryModel;
import com.csinfotechbd.loanApi.model.AdvanceSearchDataModel;
import com.csinfotechbd.loanApi.service.RetailLoanUcbApiService;
import com.csinfotechbd.retail.loan.dashboard.esau.LoanPerformanceAndEsauTrendDataModel;
import com.csinfotechbd.retail.loan.dashboard.esau.LoanPerformanceAndEsauTrendService;
import com.csinfotechbd.retail.loan.dashboard.kpi.LoanKpiTargetVsAchievement;
import com.csinfotechbd.retail.loan.dashboard.kpi.LoanKpiTargetVsAchievementSevrice;
import com.csinfotechbd.retail.loan.dataEntry.ptp.DealerWisePtpSummary;
import com.csinfotechbd.retail.loan.dataEntry.ptp.LoanPtpService;
import com.csinfotechbd.retail.loan.dataEntry.ptp.PtpStatusSummary;
import com.csinfotechbd.user.UserPrincipal;
import com.csinfotechbd.utillity.DateUtils;
import com.csinfotechbd.utillity.StringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Created at 01 April, 2021
 */

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/retail/loan/dashboard/")
public class RetailLoanDashboardController {

    private final RetailLoanDashboardService dashboardService;
    private final FollowUpService followUpService;
    private final LoanPtpService loanPtpService;
    private final RetailLoanUcbApiService retailLoanUcbApiService;
    private final LoanAccountDistributionService loanAccountDistributionService;
    private final LoanKpiTargetVsAchievementSevrice kpiTargetVsAchievementSevrice;
    private final LoanPerformanceAndEsauTrendService performanceAndEsauTrendService;
    private final DateUtils dateUtils;
    @Autowired
    private  EmployeeService employeeService;
    @Autowired
    private PeopleAllocationLogicService peopleAllocationLogicService;

    @ResponseBody
    @GetMapping(value = "account-details")
    public List<LoanAccountDistributionSummary> getLoanAccountDetails(@RequestParam("dealerPin") String dealerPin) {
        List<LoanAccountDistributionSummary> loanAccountDetails=loanAccountDistributionService.getCurrentMonthLoanDistributionSummaryForDealer(dealerPin);
//        return loanAccountDistributionService.getCurrentMonthLoanDistributionSummaryForDealer(dealerPin);
        return loanAccountDetails;
    }

    @GetMapping(value = "date-wise-follow-up")
    public Map getDateWiseFollowUpSummary(String userId, HttpSession session) {
        Map map = new HashMap();
        List<FollowUpEntity> followUpList = new ArrayList<>();
        List<LoanAccountDistributionInfo> distributionInfos =
                (List<LoanAccountDistributionInfo>) session.getAttribute("loanDistributionList");

        if (distributionInfos == null) {
            map.put("loanFollowup", followUpList);
            return map;
        }

        for (LoanAccountDistributionInfo distributionInfo : distributionInfos) {
            Long customerId = distributionInfo.getLoanAccountBasicInfo().getCustomer().getId();
            List<FollowUpEntity> followups = dashboardService.getLoanFollowUpByCusBasicInfo(customerId, userId);
            for (FollowUpEntity follow : followups) {
                follow.setOutstanding(distributionInfo.getOutStanding());
                follow.setAccNo(distributionInfo.getLoanAccountBasicInfo().getAccountNo());
            }
            followUpList.addAll(followups);
        }
        map.put("loanFollowup", followUpList);
        return map;
    }

    @GetMapping(value = "follow-up")
    public List<FollowUpSummaryModel> getMonthlyFollowUpSummary(String designation, String userPin) {
        return followUpService.getCurrentMonthFollowUpSummmary(userPin, designation);
    }

    @GetMapping(value = "ptp-status-summary")
    public List<PtpStatusSummary> getPtpStatusWiseSummary(String designation, String userPin) {
        List<PtpStatusSummary> ptpStatusSummaries =  loanPtpService.getCurrentMonthFollowUpSummmary(designation, userPin);
        return ptpStatusSummaries;
    }

    @GetMapping(value = "advance-search")
    public List<AdvanceSearchDataModel> getAdvenceSearchData(@RequestParam("payload") String payloadString) throws Exception {

        payloadString = payloadString.replace("&quot;", "\"");

        AdvanceSearchPayload payload = new ObjectMapper().readValue(payloadString, AdvanceSearchPayload.class);

        return retailLoanUcbApiService.getAdvanceSearchData(payload);
    }

    @GetMapping(value = "dealer-wise-payment-summary")
    public List<DealerWisePayment> getDealerWisePaymentSummary(@RequestParam("dealerPins") String[] dealerPins) {
        return dashboardService.getDealerWisePaymentSummary(Arrays.asList(dealerPins));
    }

    @GetMapping(value = "dealer-wise-ptp-summary")
    public List<DealerWisePtpSummary> getDealerWisePtpSummary(@RequestParam("dealerPins") String[] dealerPins) {
        return loanPtpService.getDealerWisePtpSummary(Arrays.asList(dealerPins));
    }

    @GetMapping(value = "dealer-wise-follow-up-summary")
    public List<DealerWiseFollowUpSummary> getDealerWiseFollowUpSummary(@RequestParam("dealerPins") String[] dealerPins) {
        return followUpService.getDealerWiseFollowUpSummary(Arrays.asList(dealerPins));
    }

    @ResponseBody
    @GetMapping(value = "payment-summary")
    public List<LoanPaymentSummaryModel> getDealerPaymentSummary(@RequestParam("userPin") String userPin) {
        List<String> pinList = peopleAllocationLogicService.findDealerPinByAnyPin(userPin);
        Date startDate = dateUtils.getMonthStartDate();
        Date endDate = dateUtils.getMonthEndDate();
        return dashboardService.getCategorizedPaymentSummary(pinList, startDate, endDate);
    }

    @ResponseBody
    @GetMapping(value = "kpi-target-vs-achievement-account-wise")
    public List<LoanKpiTargetVsAchievement> getKpiAccountWiseSummaryForCurrentMonth(@RequestParam("userPin") String userPin) {
        Date startDate = dateUtils.getMonthStartDate();
        Date endDate = dateUtils.getMonthEndDate();
        return kpiTargetVsAchievementSevrice.getKpiAccountWiseSummary(Arrays.asList(userPin), startDate, endDate);
    }

    @ResponseBody
    @GetMapping(value = "kpi-target-vs-achievement-amount-wise")
    public List<LoanKpiTargetVsAchievement> getKpiAmountWiseSummaryForCurrentMonth(@RequestParam("userPin") String userPin) {
        Date startDate = dateUtils.getMonthStartDate();
        Date endDate = dateUtils.getMonthEndDate();
        return kpiTargetVsAchievementSevrice.getKpiAmountWiseSummary(Arrays.asList(userPin), startDate, endDate);
    }

    @ResponseBody
    @GetMapping(value = "performance-and-esau-trend")
    public LoanPerformanceAndEsauTrendDataModel getPerformanceAndEsauTrendSummary(@RequestParam("userPin") String userPin,
                                                                                  @RequestParam(name = "month", required = false) String month) {
        Date beginingMonth = StringUtils.hasText(month) ? dateUtils.getFormattedDate(month, "yyyy-MM") : new Date();
        List<LoanPerformanceAndEsauTrendDataModel> summary = performanceAndEsauTrendService.getPerformanceAndEsauTrendSummary(Arrays.asList(userPin), beginingMonth);
        return summary.isEmpty() ? new LoanPerformanceAndEsauTrendDataModel() : summary.get(0);
    }



    @ResponseBody
    @GetMapping(value = "team-lead-kpi-target-account-wise")
    public List<LoanKpiTargetVsAchievement> getTeamLeadKpiAccountWiseSummaryForCurrentMonth() {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        EmployeeInfoEntity employeeInfoEntity = employeeService.getByPin(user.getUsername());
        List<PeopleAllocationLogicInfo> peopleAllocationLogicInfoList = peopleAllocationLogicService.findDealerByTeamLeaderId(employeeInfoEntity.getId());
        List<String>userPins=new ArrayList<>();
        for(PeopleAllocationLogicInfo peopleAllocationLogicInfo:peopleAllocationLogicInfoList){
            EmployeeInfoEntity emp = employeeService.getById(peopleAllocationLogicInfo.getDealer().getId());
            userPins.add(emp.getPin());
        }
        Date startDate = dateUtils.getMonthStartDate();
        Date endDate = dateUtils.getMonthEndDate();
        return kpiTargetVsAchievementSevrice.getKpiAccountWiseSummary(userPins, startDate, endDate);
    }

    @ResponseBody
    @GetMapping(value = "team-lead-kpi-target-amount-wise")
    public List<LoanKpiTargetVsAchievement> getTeamLeadKpiAmountWiseSummaryForCurrentMonth() {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        EmployeeInfoEntity employeeInfoEntity = employeeService.getByPin(user.getUsername());
        List<PeopleAllocationLogicInfo> peopleAllocationLogicInfoList = peopleAllocationLogicService.findDealerByTeamLeaderId(employeeInfoEntity.getId());
        List<String>userPins=new ArrayList<>();
        for(PeopleAllocationLogicInfo peopleAllocationLogicInfo:peopleAllocationLogicInfoList){
            EmployeeInfoEntity emp = employeeService.getById(peopleAllocationLogicInfo.getDealer().getId());
            userPins.add(emp.getPin());
        }
        Date startDate = dateUtils.getMonthStartDate();
        Date endDate = dateUtils.getMonthEndDate();
        return kpiTargetVsAchievementSevrice.getKpiAmountWiseSummary(userPins, startDate, endDate);
    }

}
