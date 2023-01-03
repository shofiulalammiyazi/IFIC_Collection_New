package com.unisoft.collection.distribution.loan.loanAccountDistribution;
/*
Created by   Islam at 7/17/2019
*/

import com.unisoft.collection.distribution.loan.AllocationDetails;
import com.unisoft.collection.distribution.loan.LoanAccountDistributionRepository;
import com.unisoft.collection.distribution.loan.loanAccountBasic.LoanAccountBasicInfo;
import com.unisoft.collection.distribution.loan.loanAccountBasic.LoanAccountBasicService;
import com.unisoft.user.UserService;
import com.unisoft.utillity.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sun.java2d.pipe.SpanShapeRenderer;

import javax.persistence.Tuple;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LoanAccountDistributionService {

    private final LoanAccountDistributionRepository repository;

    private final LoanAccountBasicService loanAccountBasicService;

    private final DateUtils dateUtils;

    public List<LoanAccountDistributionInfo> getAll() {
        return repository.findAll();
    }

    public boolean saveNew(LoanAccountDistributionInfo entity) {
        return repository.save(entity).getId() != null;
    }

    public boolean updateAgency(LoanAccountDistributionInfo entity) {
        return repository.save(entity).getId() != null;
    }

    public LoanAccountDistributionInfo getById(Long id) {
        return repository.getOne(id);
    }

    public List<LoanAccountDistributionInfo> getActiveList() {
        return repository.findByEnabled(true);
    }

    public List<LoanAccountDistributionInfo> findByLoanAccountBasicInfo(LoanAccountBasicInfo loanAccountBasicInfo) {
        return repository.findByLoanAccountBasicInfo(loanAccountBasicInfo);
    }

    public List<LoanAccountDistributionInfo> findCurrentMonthAccountDistList() {
        Date startDate = dateUtils.getDateOfCurrentMonth(1);
        Date endDate = dateUtils.getLocalMonthEndDate();
        return repository.findByCreatedDateIsBetweenAndSamAccountAndWriteOffAccountAndLatestOrderByIdDesc(startDate, endDate, "0", "0", "1");
    }

    public Set<String> getCurrentMonthDistributedAccountNumbers() {
        return repository.getCurrentMonthDistributedAccountNumbers();
    }

    public boolean isValidDistribution(LoanAccountDistributionInfo distributionInfo) {
        return distributionInfo != null && loanAccountBasicService.isExistingAccount(distributionInfo.getLoanAccountBasicInfo());
    }

    public LoanAccountDistributionInfo save(LoanAccountDistributionInfo entity) {
        if (!isValidDistribution(entity)) return null;
        repository.updateLatestStatus(entity.getLoanAccountBasicInfo().getId());
        setDefaultDistributionValues(entity);
        return repository.save(entity);
    }

    public int updateLoanAccountDistributionLatestStatus(Long loanAccountBasicInfoId) {
        return repository.updateLatestStatus(loanAccountBasicInfoId);
    }

    public int updateCurrentBucketOfDistributedAccounts(String accountNo, String bucket, double overdue) {
        return repository.updateCurrentBucketRelatedStatus(accountNo, bucket, overdue);
    }

    public LoanAccountDistributionInfo update(LoanAccountDistributionInfo entity) {
//        setDefaultDistributionValues(entity);
        return isValidDistribution(entity) ? repository.save(entity) : null;
    }

    private void setDefaultDistributionValues(LoanAccountDistributionInfo entity) {
        entity.setMonitoringStatus("SINGLE");
        entity.setLatest("1");
        entity.setSamAccount("0");
        entity.setWriteOffAccount("0");
        entity.setCreatedBy(UserService.getSessionUsername());
        entity.setCreatedDate(new Date());
    }

    public List<LoanAccountDistributionSummary> getCurrentMonthLoanDistributionSummaryForDealer(String dealerPin) {
        Date startDate = dateUtils.getMonthStartDate();
        Date endDate = dateUtils.getMonthEndDate();
        List<Tuple> summaries = repository.getLoanAccountDistributionSummary(dealerPin, startDate, endDate);
        return summaries.stream().map(item -> new LoanAccountDistributionSummary(item)).collect(Collectors.toList());
//    return summaries.stream().map(LoanAccountDistributionSummary::new).collect(Collectors.toList());
    }

    public List<Map<String, Object>> getLoanAccountDealerAllocationHistory(String accountNo, Date startDate, Date endDate) {
        List<Map<String, Object>> histories = new ArrayList<>();
        List<Tuple> allHistoriesData = repository.getLoanAccountDealerAllocationHistory(accountNo);
        for (Tuple historyData : allHistoriesData) {

            Object dealerId = historyData.get("dealerId");
            Object dealerName = historyData.get("dealerName");
            String dealer = dealerId == null ? "" : (String) dealerId;
            dealer += (dealerId == null || dealerName == null ? "" : " - ");
            dealer += dealerName;

            Object teamleaderName = historyData.get("teamleaderName");
            Object teamleaderId = historyData.get("teamleaderId");
//            String teamlear = teamleaderId == null ? "" : (String) teamleaderId;
//            teamlear += (teamleaderId == null || teamleaderName == null ? "" : " - ");
//            teamlear += teamleaderName;

            Object createdById = historyData.get("createdById");
            Object createdByName = historyData.get("createByName");
//            Object supervisorName = historyData.get("supervisorName");
            String createdBy = (String) createdById;
            createdBy += (createdById == null || createdByName == null ? "" : " - ");
            createdBy += createdByName;

            SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy hh:mm aa");
            String createdAt = formatter.format(historyData.get("createdDate"));
            /*------------------------------------------------------------*/
            Map<String, Object> history = new HashMap<>();
            history.put("dealer", dealer);
            history.put("createdBy", createdBy);
            history.put("createdAt", createdAt);
            history.put("teamleaderName", teamleaderName);
            history.put("teamleaderId", teamleaderId);

            histories.add(history);
        }
        return histories;
    }

    public List<LoanAccountDistributionInfo> findCurrnetLoanAccountDistributionInfoByDealerPin(String pin) {
        Date startDate = dateUtils.getDateOfCurrentMonth(1);
        Date endDate = dateUtils.getLocalMonthEndDate();
        return repository.findCurrnetLoanAccountDistributionInfoByDealerPin(pin, startDate, endDate);
    }

    public List<AllocationDetails> findCurrentMonthAllocationDistributionList(Date startDate, Date endDate){

        List<AllocationDetails> dataList = new ArrayList<>();
        List<Tuple> tuples = repository.findByCurrentMonthAllocationDistribution(startDate, endDate);

        for(Tuple tuple : tuples){
            AllocationDetails allocationDetails = new AllocationDetails(tuple);
            dataList.add(allocationDetails);
        }

        return dataList;
    }

    public List<LoanAccountDistributionInfo> findLoanAccountDistributionInfoByLatest(String s) {
        return repository.findLoanAccountDistributionInfosByLatest(s);
    }


    public LoanAccountDistributionInfo saveDistribution(LoanAccountDistributionInfo loanAccountBasicInfo){
        return repository.save(loanAccountBasicInfo);
    }

    public List<LoanAccountDistributionInfo> findCurrentMonthDistributionList() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-mm-yyyy");
        Date startDate = dateUtils.getDateOfCurrentMonth(1);
        Date endDate = dateUtils.getLocalMonthEndDate();
        return repository.findLoanAccountDistributionInfosByCreatedDateAndLatest(simpleDateFormat.format(startDate), simpleDateFormat.format(endDate));
    }

    public LoanAccountDistributionInfo findLoanAccountDistributionInfoByAccountNo(String accountNo, String latest){
        return repository.findByAccountNoAndLatest(accountNo,latest);
    }
}
