package com.csinfotechbd.customerloanprofile.followup;


import com.csinfotechbd.audittrail.AuditTrailService;
import com.csinfotechbd.user.UserPrincipal;
import com.csinfotechbd.utillity.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FollowUpService {

    private final FollowUpRepository repository;
    private final FollowUpSummaryDao followUpDao;
    private final DateUtils dateUtils;
    private final AuditTrailService auditTrailService;

    public List<FollowUpEntity> getByCustomerId(Long id) {
        Date startDate = dateUtils.getMonthStartDate();
        return repository.findByCustomerBasicInfoIdAndFollowUpDateGreaterThanEqualOrderByIdDesc(id, startDate);
    }

    public List<FollowUpSummaryModel> getCurrentMonthFollowUpSummmary(String employeePin, String designation) {
        List<FollowUpSummaryModel> summary = new ArrayList<>();
        switch (designation.replaceAll(" ", "").toLowerCase()) {
            case "teamleader":
                summary = followUpDao.getMonthlyFollowUpSummaryByTeamLeader(employeePin);
                break;
            case "manager":
                summary = followUpDao.getMonthlyFollowUpSummaryByManager(employeePin);
                break;
            case "supervisor":
                summary = followUpDao.getMonthlyFollowUpSummaryBySupervisor(employeePin);
                break;
            default:
                summary = followUpDao.getMonthlyFollowUpSummaryByDealer(employeePin);
        }
        setAllFollowUpReasons(summary);
        return summary;

    }

    public List<FollowUpSummaryModel> getCardFollowUpSummmary(String employeePin, String designation) {
        List<FollowUpSummaryModel> summary = new ArrayList<>();
        switch (designation.replaceAll(" ", "").toLowerCase()) {
            case "teamleader":
                summary = followUpDao.getMonthlyFollowUpSummaryByTeamLeader(employeePin);
                break;
            case "manager":
                summary = followUpDao.getMonthlyFollowUpSummaryByManager(employeePin);
                break;
            case "supervisor":
                summary = followUpDao.getMonthlyCardFollowUpSummaryBySupervisor(employeePin);
                break;
            case "dealer":
                summary = followUpDao.getMonthlyCardFollowUpSummaryByDealer(employeePin);
                break;
            default:
                break;
        }
        setAllFollowUpReasons(summary);
        return summary;
    }

    public List<FollowUpSummaryModel> getSamdCurrentMonthFollowUpSummmary(String employeePin, String designation) {
        List<FollowUpSummaryModel> summary = new ArrayList<>();
        switch (designation.replaceAll(" ", "").toLowerCase()) {
            case "teamleader":
                summary = followUpDao.getSamdMonthlyFollowUpSummaryByTeamLeader(employeePin);
                break;
            case "manager":
                summary = followUpDao.getSamdMonthlyFollowUpSummaryByManager(employeePin);
                break;
            case "supervisor":
                summary = followUpDao.getSamdMonthlyFollowUpSummaryBySupervisor(employeePin);
                break;
            default:
                summary = followUpDao.getSamdMonthlyFollowUpSummaryByDealer(employeePin);
        }
        setAllFollowUpReasons(summary);
        return summary;

    }

    public List<Map<String, String>> getTodaysFollowUpNotificationForDealer(String dealerPin) {
        List<Tuple> followupListToday = repository.findByTodaysFollowupsByDealer(dealerPin);
        List<Map<String, String>> list = new LinkedList<>();
        for (Tuple followup : followupListToday) {
            Map<String, String> notificationInfo = new LinkedHashMap<>();

            String accountNo = Objects.toString(followup.get("ACCOUNT_NO"), "-");
            String followUpTime = Objects.toString(followup.get("TIME"), "-");
            String reasons = Objects.toString(followup.get("REASONS"), "-");
            notificationInfo.put("accountNo", accountNo);
            notificationInfo.put("time", followUpTime);
            notificationInfo.put("reasons", reasons);

            list.add(notificationInfo);
        }
        return list;
    }


    public void setAllFollowUpReasons(List<FollowUpSummaryModel> followUpDataModels) {

        List<String> followUpReasons = Arrays.stream(FollowUpName.values())
                .map(FollowUpName::toString).collect(Collectors.toList());

        followUpReasons = followUpReasons.stream().map(String::toLowerCase).collect(Collectors.toList());

        for (FollowUpSummaryModel followUpSummaryModel : followUpDataModels)
            followUpReasons.remove(followUpSummaryModel.getReason().toLowerCase().replace(" ", "_"));

        for (String followUpReason : followUpReasons)
            followUpDataModels.add(new FollowUpSummaryModel(followUpReason.replace("_", " "), "", 0L, 0D));

    }

    public FollowUpEntity save(FollowUpEntity followUpEntity) {
        boolean isNewEntity = true;
        FollowUpEntity previousEntity = new FollowUpEntity();
        if (followUpEntity.getId() != null) {
            FollowUpEntity entity = repository.getOne(followUpEntity.getId());
            BeanUtils.copyProperties(entity, followUpEntity);
            isNewEntity = false;
        }

        String dateString = followUpEntity.getFollowUpDates().concat("")
                .concat(followUpEntity.getFollowUpTime().equals("&quot;") ? "" : " "+followUpEntity.getFollowUpTime());
        Date followUpDate = dateUtils.getFormattedDate(dateString, "dd-MM-yyyy");
        followUpEntity.setFollowUpDate(followUpDate);
        followUpEntity.setFollowUpTime(followUpEntity.getFollowUpTime().equals("&quot;") ? "" : " "+followUpEntity.getFollowUpTime().replace("&quot;",""));
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        followUpEntity.setCreatedBy(user.getUsername());
        followUpEntity.setCreatedDate(new Date());
        followUpEntity.setPin(user.getUsername());
        followUpEntity.setUsername(user.getFirstName().concat(" ").concat(user.getLastName()));

        repository.save(followUpEntity);

        if (isNewEntity)
            auditTrailService.saveCreatedData("Follow Up", followUpEntity);
        else
            auditTrailService.saveUpdatedData("Follow Up", previousEntity, followUpEntity);
        return followUpEntity;
    }


    public List<DealerWiseFollowUpSummary> getDealerWiseFollowUpSummary(List<String> dealerPins) {
        return repository.getDealerWiseFollowUpSummary(dealerPins)
                .stream().map(DealerWiseFollowUpSummary::new).collect(Collectors.toList());
    }

    public List<DealerWiseFollowUpSummary> getCardDealerWiseFollowUpSummary(List<String> dealerPins) {
        return repository.getCardDealerWiseFollowUpSummary(dealerPins)
                .stream().map(DealerWiseFollowUpSummary::new).collect(Collectors.toList());
    }


}

