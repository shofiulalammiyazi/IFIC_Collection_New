package com.unisoft.collection.distribution.card;

import com.unisoft.retail.card.dataEntry.distribution.accountDistributionInfo.CardAccountDistributionRepository;
import com.unisoft.retail.card.dataEntry.distribution.agency.CardAgencyDistributionInfo;
import com.unisoft.retail.card.dataEntry.distribution.agency.CardAgencyDistributionRepository;
import com.unisoft.retail.card.dataEntry.distribution.accountBasicInfo.CardAccountBasicInfo;
import com.unisoft.retail.card.dataEntry.distribution.accountDistributionInfo.CardAccountDistributionInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Service
public class CardAccountSearchService {

    @Autowired
    private CardAccountDistributionRepository cardAccountDistributionRepository;

    @Autowired
    private CardAgencyDistributionRepository cardAgencyDistributionRepository;

    public CardAccountDistributionInfo findFromCardAccountDistributionInfo(CardAccountBasicInfo cardAccountBasicInfo) {
        return cardAccountDistributionRepository
                .findFirstByCreatedDateIsBetweenAndCardAccountBasicInfoAndLatestOrderByCreatedDateDesc(
                        getStartDate(), getEndDate(), cardAccountBasicInfo, "1");
    }

    public CardAgencyDistributionInfo findFromCardAgencyDistributionInfo(CardAccountBasicInfo cardAccountBasicInfo) {
        return cardAgencyDistributionRepository
                .findFirstByCreatedDateGreaterThanEqualAndCreatedDateLessThanEqualAndCardAccountBasicInfoAndLatestOrderByCreatedDateDesc(
                        getStartDate(), getEndDate(), cardAccountBasicInfo, "1");
    }

    public Date getStartDate() {
        LocalDate today = LocalDate.now();
        LocalDate startDateOfMonth = today.withDayOfMonth(1);
        return Date.from(startDateOfMonth.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public Date getEndDate() {
        LocalDate today = LocalDate.now();
        LocalDate endDateOfMonth = today.withDayOfMonth(today.lengthOfMonth());
        String endDateOfMonthString = endDateOfMonth.toString();
        endDateOfMonthString = endDateOfMonthString + " 11:59 PM";
        try {
            return new SimpleDateFormat("yyyy-MM-dd hh:mm a").parse(endDateOfMonthString);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
