package com.csinfotechbd.retail.card.dashboard.kpi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CardKpiTargetVsAchievementService {
    
    @Autowired
    private CardKpiTargetVsAchievementRepo repo;
    
    public List<CardKpiTargetVsAchievement> getKpiAccountWiseSummary(List<String> dealerPins, Date startDate, Date endDate) {
        return repo.getKpiAccountWiseSummary(dealerPins, startDate, endDate)
                .stream().map(CardKpiTargetVsAchievement::new).collect(Collectors.toList());
    }
    
    public List<CardKpiTargetVsAchievement> getKpiAmountWiseSummary(List<String> dealerPins, Date startDate, Date endDate) {
        return repo.getKpiAmountWiseSummary(dealerPins, startDate, endDate)
                .stream().map(CardKpiTargetVsAchievement::new).collect(Collectors.toList());
    }
}
