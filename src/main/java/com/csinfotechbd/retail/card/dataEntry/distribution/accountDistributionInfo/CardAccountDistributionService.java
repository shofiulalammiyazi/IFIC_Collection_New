package com.csinfotechbd.retail.card.dataEntry.distribution.accountDistributionInfo;
/*
Created by Monirul Islam at 7/21/2019
*/

import com.csinfotechbd.user.UserPrincipal;
import com.csinfotechbd.utillity.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import java.util.*;
import java.util.stream.Collectors;

import static org.apache.logging.log4j.ThreadContext.isEmpty;

@Service
@RequiredArgsConstructor
public class CardAccountDistributionService {


    private final CardAccountDistributionRepository repository;
    private final DateUtils dateUtils;

    public List<CardAccountDistributionInfo> getAll() {
        return repository.findAll();
    }

    public boolean saveNew(CardAccountDistributionInfo entity) {
        return repository.save(entity).getId() != null;
    }

    public boolean updateAgency(CardAccountDistributionInfo entity) {
        return repository.save(entity).getId() != null;
    }

    public CardAccountDistributionInfo getById(Long id) {
        return repository.getOne(id);
    }

//    public List<CardAccountDistributionInfo> findByCardAccountBasicInfo(CardAccountBasicInfo CardAccountBasicInfo) {
//        return repository.findByCardAccountBasicInfo(CardAccountBasicInfo);
//    }

    public Set<String> getCurrentMonthDistributedCardNumbers() {
        return repository.getCurrentMonthDistributedCardNumbers();
    }

    public CardAccountDistributionInfo save(CardAccountDistributionInfo entity) {
        repository.updateLatestStatus(entity.getCardAccountBasicInfo().getId());
        setDefaultDistributionValues(entity);
        return repository.save(entity);
    }

    public int updateCardAccountDistributionLatestStatus(Long CardAccountBasicInfoId) {
        return repository.updateLatestStatus(CardAccountBasicInfoId);
    }

    public CardAccountDistributionInfo update(CardAccountDistributionInfo entity) {
//        setDefaultDistributionValues(entity);
        return repository.save(entity);
    }

    private void setDefaultDistributionValues(CardAccountDistributionInfo entity) {
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        entity.setMonitoringStatus("SINGLE");
        entity.setEnabled(true);
        entity.setLatest("1");
        entity.setSamAccount("0");
        entity.setWriteOffAccount("0");
        entity.setCreatedBy(userPrincipal.getUsername());
        entity.setCreatedDate(new Date());
    }

    public List<Map<String, Object>> getCardAccountDealerAllocationHistory(String accountNo, Date startDate, Date endDate, String clientId) {
        List<Map<String, Object>> histories = new ArrayList<>();
        List<Tuple> allHistoriesData = repository.getCardAccountDealerAllocationHistory(accountNo, startDate, endDate, clientId);
        for (Tuple historyData : allHistoriesData) {

            String dealerName = Objects.toString(historyData.get("dealerName"), "");
            String dealerId = Objects.toString(historyData.get("dealerId"), "");
            dealerName = dealerId.isEmpty() ? dealerName : dealerId + "-" + dealerName;


            Object teamleaderName = historyData.get("teamleaderName");
            Object teamleaderId = historyData.get("teamleaderId");
            String createdById = Objects.toString(historyData.get("createdById"), "");
            String createdByName = Objects.toString(historyData.get("createByName"), "");
            createdByName = createdById.isEmpty() ? createdByName : createdById + "-" + createdByName;
            String outStanding = Objects.toString(historyData.get("outStanding"));
            String minimumDue = Objects.toString(historyData.get("minumumDue"));
            String dpdBucket = Objects.toString(historyData.get("dpdBucket"));
            String ageCode = Objects.toString(historyData.get("ageCode"));
            String clStatus=null;
            if(ageCode!=null){
                try {
                    clStatus =getClstatus(Integer.parseInt(ageCode));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }


            String createdAt = Objects.toString(historyData.get("createdDate"), "");
            /*------------------------------------------------------------*/
            Map<String, Object> history = new HashMap<>();

            history.put("dealer", dealerName);
            history.put("createdBy", createdByName);
            history.put("createdAt", historyData.get("createdDate"));
            history.put("teamleaderName", teamleaderName);
            history.put("teamleaderId", teamleaderId);
            history.put("outStanding", outStanding);
            history.put("minimumDue", minimumDue);
            history.put("dpdBucket", dpdBucket);
            history.put("ageCode", ageCode);
            history.put("clStatus", clStatus);
            histories.add(history);
        }
        return histories;
    }

    public List<CardAccountDistributionSummary> getCurrentMonthCardDistributionSummaryForDealer(String dealerPin, Date startDate, Date endDate) {
        List<Tuple> summaries = repository.getCardAccountDistributionSummary(dealerPin, startDate, endDate);
        return summaries.stream().map(CardAccountDistributionSummary::new).collect(Collectors.toList());
    }

    public String getClstatus(Integer ageCode){
        String clStatus =null;
        if (ageCode>=0 && ageCode<=1){
           clStatus="UC";
        }else if(ageCode ==2){
           clStatus= "SMA";
        }else if(ageCode>=3 && ageCode <=8){
           clStatus= "SS";
        }else if(ageCode>=9 && ageCode <=11){
           clStatus= "DF";
        }else if(ageCode>=12){
            clStatus="DF";
        }

        return clStatus;
    }


}
