package com.unisoft.retail.loan.dashboard;


import com.unisoft.customerloanprofile.followup.FollowUpEntity;
import com.unisoft.customerloanprofile.loanpayment.DealerWisePayment;
import com.unisoft.customerloanprofile.loanpayment.LoanPaymentSummaryModel;
import com.unisoft.retail.loan.dashboard.kpi.LoanKpiTargetVsAchievementRepository;
import com.unisoft.utillity.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Yasir Araphat
 * Created at 01 April, 2021
 */

@Service
@RequiredArgsConstructor
public class RetailLoanDashboardService {

    private final RetailLoanDashboardDao dashboardDao;
    private final RetailLoanDashboardRepository dashboardRepository;
    private final LoanKpiTargetVsAchievementRepository kpiTargetVsAchievementRepository;
    private final DateUtils dateUtils;

    public List<FollowUpEntity> getLoanFollowUpByCusBasicInfo(Long cusId, String userId) {
        return dashboardDao.getLoanFollowUpByCusBasicInfo(cusId, userId);
    }

    public List<DealerWisePayment> getDealerWisePaymentSummary(List<String> dealerPins) {
        return dashboardRepository.getTeamPaymentSummary(dealerPins)
                .stream().map(DealerWisePayment::new).collect(Collectors.toList());
    }

    public List<LoanPaymentSummaryModel> getCategorizedPaymentSummary(List<String> dealerPins, Date startDate, Date endDate) {

        Map<String, LoanPaymentSummaryModel> categoryWiseMap = new LinkedHashMap<>();

        // Ensure all the user defined categories available in list
        categoryWiseMap.put("Full amount paid", new LoanPaymentSummaryModel("Full amount paid"));
        categoryWiseMap.put("Partial paid but forward flow to upper bucket", new LoanPaymentSummaryModel("Partial paid but forward flow to upper bucket"));
        categoryWiseMap.put("No payment during the month", new LoanPaymentSummaryModel("No payment during the month"));
        categoryWiseMap.put("Probable CL (As on date on Dealer Queue)", new LoanPaymentSummaryModel("Probable CL (As on date on Dealer Queue"));
        categoryWiseMap.put("Probable CL list as per month beginning distribution", new LoanPaymentSummaryModel("Probable CL list as per month beginning distribution"));
        categoryWiseMap.put("Existing CL", new LoanPaymentSummaryModel("Existing CL"));
        categoryWiseMap.put("Others", new LoanPaymentSummaryModel("Others"));

        // Get category wise payment summary
        List<Tuple> data = dashboardRepository.getCategorizedPaymentSummary(dealerPins, startDate, endDate);
        for (Tuple paymentInfo : data) {
            LoanPaymentSummaryModel paymentStatusModel = new LoanPaymentSummaryModel();
            paymentStatusModel.setFieldValuesFromTuple(paymentInfo);
            categoryWiseMap.put(paymentStatusModel.getCategory(), paymentStatusModel);
        }

        return new LinkedList<>(categoryWiseMap.values());
    }


}
