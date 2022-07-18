package com.csinfotechbd.retail.card.dashboard;

import com.csinfotechbd.collection.allocationLogic.PeopleAllocationLogicInfo;
import com.csinfotechbd.collection.dashboard.AdvanceSearchPayload;
import com.csinfotechbd.collection.settings.ExchangeRate.ExchangeRateService;
import com.csinfotechbd.collection.settings.employee.EmployeeInfoEntity;
import com.csinfotechbd.collection.settings.employee.EmployeeRepository;
import com.csinfotechbd.customerloanprofile.followup.*;
import com.csinfotechbd.retail.card.dashboard.kpi.CardKpiTargetVsAchievement;
import com.csinfotechbd.retail.card.dashboard.kpi.CardKpiTargetVsAchievementService;
import com.csinfotechbd.retail.card.dashboard.model.AdvanceSearchDataModelCard;
import com.csinfotechbd.retail.card.dataEntry.distribution.accountDistributionInfo.CardAccountDistributionInfo;
import com.csinfotechbd.retail.card.dataEntry.distribution.accountDistributionInfo.CardAccountDistributionService;
import com.csinfotechbd.retail.card.dataEntry.distribution.accountDistributionInfo.CardAccountDistributionSummary;
import com.csinfotechbd.retail.card.dataEntry.followup.CardFollowUp;
import com.csinfotechbd.retail.card.dataEntry.ptp.CardPtpService;
import com.csinfotechbd.retail.card.dataEntry.ptp.PtpStatusSummary;
import com.csinfotechbd.retail.loan.dataEntry.ptp.DealerWisePtpSummary;
import com.csinfotechbd.utillity.DateUtils;
import com.csinfotechbd.utillity.HttpSessionUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Yasir Araphat
 * Created at 01 April, 2021
 */

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/collection/card/dashboard/")
public class RetailCardDashboardController {

    private final RetailCardDashboardService dashboardService;

    private final CardAccountDistributionService distributionService;

    private final EmployeeRepository employeeRepository;

    private final FollowUpService followUpService;

    private final HttpSessionUtils httpSessionUtils;

    private final DateUtils dateUtils;
    private final CardPtpService cardPtpService;
    
    private final CardKpiTargetVsAchievementService cardKpiTargetVsAchievementService;

    @Autowired
    private ExchangeRateService exchangeRateService;


    @ResponseBody
    @GetMapping(value = "account-details")
    public List<CardAccountDistributionSummary> getLoanAccountDetails(@RequestParam("dealerPin") String dealerPin) {
        Date startDate = dateUtils.getMonthStartDate();
        Date endDate = dateUtils.getMonthEndDate();
        List<CardAccountDistributionSummary> currentMonthCardDistributionSummaryForDealer = distributionService.getCurrentMonthCardDistributionSummaryForDealer(dealerPin, startDate, endDate);
        //currentMonthCardDistributionSummaryForDealer.add("exchangeRate",exchangeRateService.getById())
        return currentMonthCardDistributionSummaryForDealer;
    }

    @GetMapping(value = "date-wise-follow-up")
    public Map getFollowUpLoan(String userId, HttpSession session) {
        Map map = new HashMap();

        List<CardFollowUp> cardFollowUpList = new ArrayList<>();
        List<CardAccountDistributionInfo> cardDist = (List<CardAccountDistributionInfo>) session.getAttribute("cardDistributionList");

        if (cardDist == null) {
            map.put("cardFollowup", cardFollowUpList);
            return map;
        }

        for (CardAccountDistributionInfo card : cardDist) {

            Long customerId = card.getCardAccountBasicInfo().getCustomer().getId();
            List<CardFollowUp> followups = dashboardService.getCardFollowUpByCusBasicInfo(customerId, userId);

            for (CardFollowUp follow : followups) {
                follow.setOutstanding(card.getOutstandingAmount());
                follow.setAccNo(card.getCardAccountBasicInfo().getContractId());
            }
            cardFollowUpList.addAll(followups);
        }
        map.put("cardFollowup", cardFollowUpList);
        return map;
    }

    @GetMapping(value = "followups")
    @ResponseBody
    public List<FollowUpSummaryModel> cardFollowUpByUser(@RequestParam(value = "userPin") String userPin,
                                                         @RequestParam(value = "unit") String unit,
                                                         @RequestParam(value = "designation") String designation,
                                                         @RequestParam(value = "userId") String userId,
                                                         HttpSession session) {

        List<FollowUpSummaryModel> followUpList = new ArrayList<>();

        List<CardAccountDistributionInfo> distributionList = (List<CardAccountDistributionInfo>) session.getAttribute("cardDistributionList");
        Optional<EmployeeInfoEntity> employee = employeeRepository.findById(Long.parseLong(userId));

        if (distributionList.isEmpty() || !employee.isPresent())
            return followUpList;

        return followUpService.getCardFollowUpSummmary(userPin, designation);
    }

    @GetMapping(value = "dealer-wise-follow-up-summary")
    public List<DealerWiseFollowUpSummary> getDealerWiseFollowUpSummary(@RequestParam("dealerPins") String[] dealerPins) {
        return followUpService.getCardDealerWiseFollowUpSummary(Arrays.asList(dealerPins));
    }

    @GetMapping(value = "distribution/follwUp/card/details")
    public String getCardFollowUpDetail(HttpSession session, @RequestParam(value = "cat") String followUpType, Model model) {

        List<FollowUpSummaryModel> followUpList = new ArrayList<>();

        List<CardAccountDistributionInfo> distributionList = (List<CardAccountDistributionInfo>) session.getAttribute("cardDistributionList");

        if (distributionList.isEmpty())
            return "dashboard/card/dealer/details/panels/middle/followUpdetails";

        List<PeopleAllocationLogicInfo> allocationList = new ArrayList<>();
        followUpType = followUpType.toLowerCase();

        if (followUpType.equals("phone"))
            followUpType = "call";

        for (CardAccountDistributionInfo dist : distributionList) {
            try {


                //System.err.println("CUS ID :" + dist.getCardAccountBasicInfo().getCustomer().getId());
                List<CardFollowUp> followUps = dashboardService.getCardFollowUpByCusBasicInfo(dist.getCardAccountBasicInfo().getCustomer().getId(), "");
                for (CardFollowUp followUp : followUps) {

                    for (String followUpReason : followUp.getFollowUpReason()) {
                        if (followUpReason.toLowerCase().contains(followUpType)) {
                            FollowUpSummaryModel phoneFollwUp = new FollowUpSummaryModel();
                            if (followUpType.matches("[0-9a-zA-Z_]+"))
                                phoneFollwUp = new FollowUpSummaryModel(followUpType, 0l, 0);
                            phoneFollwUp.setTotalAccount(phoneFollwUp.getTotalAccount() + 1);
//                            phoneFollwUp.setAccNo(dist.getCardAccountBasicInfo().getCardNo());
                            phoneFollwUp.setOutstandingAmount(phoneFollwUp.getOutstandingAmount() + Double.parseDouble(dist.getOutstandingAmount()));

                            followUpList.add(phoneFollwUp);
                        }


                    }
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        //followUpList.add(phoneFollwUp);
        model.addAttribute("followUpList", followUpList);
        return "dashboard/card/dealer/details/panels/middle/followUpdetails";
    }
    
    @GetMapping(value = "ptp-status-summary")
    public List<PtpStatusSummary> getPtpStatusWiseSummary(String designation, String userPin) {
        return cardPtpService.getCurrentMonthFollowUpSummmary(designation, userPin);
    }
    
    @ResponseBody
    @GetMapping(value = "kpi-target-vs-achievement-account-wise")
    public List<CardKpiTargetVsAchievement> getKpiAccountWiseSummaryForCurrentMonth(@RequestParam("userPin") String userPin) {
        Date startDate = dateUtils.getMonthStartDate();
        Date endDate = dateUtils.getMonthEndDate();
        return cardKpiTargetVsAchievementService.getKpiAccountWiseSummary(Arrays.asList(userPin), startDate, endDate);
    }
    
    @ResponseBody
    @GetMapping(value = "kpi-target-vs-achievement-amount-wise")
    public List<CardKpiTargetVsAchievement> getKpiAmountWiseSummaryForCurrentMonth(@RequestParam("userPin") String userPin) {
        Date startDate = dateUtils.getMonthStartDate();
        Date endDate = dateUtils.getMonthEndDate();
        return cardKpiTargetVsAchievementService.getKpiAmountWiseSummary(Arrays.asList(userPin), startDate, endDate);
    }

    @GetMapping(value = "dealer-wise-ptp-summary")
    public List<DealerWisePtpSummary> getDealerWisePtpSummary(@RequestParam("dealerPins") String[] dealerPins) {
        List<DealerWisePtpSummary> ptpSummaries = cardPtpService.getDealerWisePtpSummary(Arrays.asList(dealerPins));
        return ptpSummaries;
    }
    
    @GetMapping(value = "advanced-search")
    public List<AdvanceSearchDataModelCard> getAdvanceSearchData(@RequestParam("payload") String payloadString) throws Exception{
        payloadString = payloadString.replace("&quot;", "\"");
        AdvanceSearchPayload payload = new ObjectMapper().readValue(payloadString, AdvanceSearchPayload.class);
        return dashboardService.getAdvanceSearchData(payload);
    }
}
