package com.unisoft.utillity;

/*
  Created by    on 5/1/2021
*/

import com.unisoft.collection.distribution.loan.loanAccountDistribution.LoanAccountDistributionInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class LmsCalculator {

    private final DateUtils dateUtils;

    public Double calculateDpd(String overDue, String ate, String emiAmount) {
        double overDueNum = parseDouble(overDue);
        double ateNum = parseDouble(ate);
        double emiAmountNum = parseDouble(emiAmount);
        return calculateDpd(overDueNum, ateNum, emiAmountNum);
    }

    public Double calculateDpd(double overDue, double ate, double emiAmount) {
        double dpd = 0.01;
//        ate = ate > 0 ? ate : calculateAte(overDue, emiAmount);
//        dpd = (overDue > 0 && ate < 1) ? ate * 30 - 1 : ate * 30;

        if (overDue > 0 && ate < 1 && emiAmount > 0) {
            dpd = ((overDue / emiAmount) * 30 - 1);
        } else if (overDue > 0) {
            dpd = ate * 30;
        }
        return (dpd < 1) ? 0.01 : dpd;
    }

    public Double calculateAte(double overDue, double emiAmount) {
        emiAmount = (emiAmount < 1) ? 1 : emiAmount;
        return overDue / emiAmount;
    }

    public Double calculateAte(String overDue, String emiAmount) {
        double overDueNum = parseDouble(overDue);
        double emiAmountNum = parseDouble(emiAmount);
        return calculateAte(overDueNum, emiAmountNum);
    }

    public double parseDouble(String value) {
        value = Objects.toString(value, "0");
        return NumberUtils.isNumber(value) ? Double.parseDouble(value) : 0d;
    }

    public void calculateSaveAndBackAmountForLoanDistribution(LoanAccountDistributionInfo distributionInfo) {

        Date monthEndDate = dateUtils.getMonthEndDate();
        Date monthOpenDate = distributionInfo.getStatusDate();

        LocalDate localDate1 = monthOpenDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate localDate2 = monthEndDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();


        double saveAmount = 0;
        double MO_DPD = 0;
        double dayDiff = 0;
        double backAmount = 0;

        //long ld=day.toDays();
        dayDiff = (double) Duration.between(localDate1.atStartOfDay(), localDate2.atStartOfDay()).toDays();
        MO_DPD = distributionInfo.getDpd();
        if (distributionInfo.getDpdBucket() != null && distributionInfo.getEmiAmount() != null){
            if (distributionInfo.getDpdBucket() != null && distributionInfo.getDpdBucket().equals("0")) {
                saveAmount = ((MO_DPD + (dayDiff - 29)) * distributionInfo.getEmiAmount()) / 30;
                distributionInfo.setBackAmount(0);
            } else {
                double highestDpdRange = Double.parseDouble(distributionInfo.getDpdBucket()) + 29;
                saveAmount = ((MO_DPD + (dayDiff - highestDpdRange)) * distributionInfo.getEmiAmount()) / 30;

                double highestDpdRangeBack = Double.parseDouble(distributionInfo.getDpdBucket()) - 1;
                backAmount = ((MO_DPD + dayDiff - highestDpdRangeBack) * distributionInfo.getEmiAmount()) / 30;

                distributionInfo.setBackAmount(backAmount);

            }
        }
        //distributionInfo.setBackAmount(backAmount);
        if (saveAmount > 0)
            distributionInfo.setSaveAmount(saveAmount);
        else
            distributionInfo.setSaveAmount(0);
    }


}
