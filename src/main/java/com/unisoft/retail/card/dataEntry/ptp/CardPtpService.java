package com.unisoft.retail.card.dataEntry.ptp;

import com.unisoft.customerbasicinfo.CustomerBasicInfoEntity;
import com.unisoft.retail.loan.dataEntry.ptp.DealerWisePtpSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CardPtpService {

    @Autowired
    CardPtpRepository repository;

    public List<CardPtp> list(boolean a, CustomerBasicInfoEntity customerBasicInfoEntity) {
        return repository.findByEnabledIsAndCustomerBasicInfoOrderByCreatedDateDesc(true, customerBasicInfoEntity);
    }

    public CardPtp findbyId(Long id) {
        return repository.findById(id).get();
    }

    public CardPtp save(CardPtp cardPtp) {
        repository.save(cardPtp);
        return cardPtp;
    }


    public boolean delete(Long id) {
        repository.deleteById(id);
        return true;
    }

    public List<DealerWisePtpSummary> getDealerWisePtpSummary(List<String> dealerPins) {
        List<Tuple> tuples = repository.getDealerWisePtpSummary(dealerPins);
        return tuples.stream().map(DealerWisePtpSummary::new).collect(Collectors.toList());
    }
    
    public List<PtpStatusSummary> getCurrentMonthFollowUpSummmary(String designation, String employeePin) {
        List<PtpStatusSummary> ptpStatusSummaryList = new LinkedList<>();
        switch (designation.replaceAll(" ", "").toLowerCase()) {
            case "teamleader":
                ptpStatusSummaryList = repository.getPtpSummaryByTeamLeader(employeePin);
                break;
            case "manager":
                ptpStatusSummaryList = repository.getPtpSummaryByManager(employeePin);
                break;
            case "supervisor":
                ptpStatusSummaryList = repository.getPtpSummaryBySupervisor(employeePin);
                break;
            default:
                ptpStatusSummaryList = repository.getPtpSummaryByDealer(employeePin);
        }
        setAllPtpTypes(ptpStatusSummaryList);
        return ptpStatusSummaryList;
    }
    
    private void setAllPtpTypes(List<PtpStatusSummary> ptpStatusSummaryList) {
        
        List<String> ptpTypes = new ArrayList<String>(3) {{
            add("Promise Taken");
            add("broken");
            add("kept");
        }};
        
        for (PtpStatusSummary ptp : ptpStatusSummaryList)
            ptpTypes.remove(ptp.getStatus().toLowerCase());
        
        for (String ptpType : ptpTypes)
            ptpStatusSummaryList.add(new PtpStatusSummary(ptpType, "", 0L, 0D));
        
    }

    public List<CardPtp> getCardPtpByCustomerIdAndDateRange(String customerId, String startDate, String endDate) {
        List<CardPtp> cardPtpList = repository.getCardPtpByCustomerIdAndDateRange(customerId, startDate,endDate);
        return cardPtpList;
    }
}
