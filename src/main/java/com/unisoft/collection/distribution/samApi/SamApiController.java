package com.unisoft.collection.distribution.samApi;
/*
Created by   Islam at 8/1/2019
*/

import com.unisoft.collection.allocationLogic.PeopleAllocationLogicInfo;
import com.unisoft.collection.allocationLogic.PeopleAllocationLogicRepository;
import com.unisoft.retail.card.dataEntry.distribution.accountDistributionInfo.CardAccountDistributionRepository;
import com.unisoft.retail.card.dataEntry.distribution.accountBasicInfo.CardAccountBasicInfo;
import com.unisoft.retail.card.dataEntry.distribution.accountBasicInfo.CardAccountBasicService;
import com.unisoft.retail.card.dataEntry.distribution.accountDistributionInfo.CardAccountDistributionInfo;
import com.unisoft.retail.card.dataEntry.distribution.accountDistributionInfo.CardAccountDistributionService;
import com.unisoft.collection.distribution.loan.LoanAccountDistributionRepository;
import com.unisoft.collection.distribution.loan.loanAccountBasic.LoanAccountBasicInfo;
import com.unisoft.collection.distribution.loan.loanAccountBasic.LoanAccountBasicService;
import com.unisoft.collection.distribution.loan.loanAccountDistribution.LoanAccountDistributionInfo;
import com.unisoft.collection.distribution.loan.loanAccountDistribution.LoanAccountDistributionService;
import com.unisoft.collection.distribution.loan.loanApi.LoanApiPayload;
import com.unisoft.collection.distribution.samAccountHandover.SamAccountHandoverInfo;
import com.unisoft.collection.distribution.samAccountHandover.SamAccountHandoverRepository;
import com.unisoft.collection.distribution.samAccountHandover.SamAccountHandoverService;
import com.unisoft.user.UserPrincipal;
import com.unisoft.utillity.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/collection/distribution/api")
public class SamApiController {

    private final SamAccountHandoverService samAccountHandoverService;

    private final LoanAccountDistributionService loanAccountDistributionService;

    private final CardAccountDistributionService cardAccountDistributionService;

    private final LoanAccountBasicService loanAccountBasicService;

    private final CardAccountBasicService cardAccountBasicService;

    private final SamAccountHandoverRepository samAccountHandoverRepository;

    private final PeopleAllocationLogicRepository peopleAllocationLogicRepository;

    private final LoanAccountDistributionRepository loanAccountDistributionRepository;

    private final CardAccountDistributionRepository cardAccountDistributionRepository;

    private final DateUtils dateUtils;

    @PostMapping("/samupdate")
    public boolean saveSam(@Valid @RequestBody LoanApiPayload loanApiPayload) {

        Date startDate = dateUtils.getMonthStartDate();
        Date endDate = dateUtils.getMonthEndDate();

        String dealerId = loanApiPayload.getId();
        String supervisorId = loanApiPayload.getSid();
        String remarks = loanApiPayload.getRemarks() != null ? loanApiPayload.getRemarks() : "";

        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<SamAccountHandoverInfo> samAccountHandoverInfos = new ArrayList<>();

        for (String s : loanApiPayload.getList()) {
            SamAccountHandoverInfo byId = samAccountHandoverService.getById(Long.parseLong(s));
            LoanAccountDistributionInfo loanAccountDistributionInfo = new LoanAccountDistributionInfo();
            CardAccountDistributionInfo cardAccountDistributionInfo = new CardAccountDistributionInfo();
            byId.setSamAccount("0");
            byId.setLatest("0");
            samAccountHandoverInfos.add(byId);
            if (byId.getProductUnit().equals("Loan")) {
                LoanAccountBasicInfo byAccountNo = loanAccountBasicService.getByAccountNo(byId.getLoanAccountNo());
                LoanAccountDistributionInfo loanAccountDistributionInfo1 = loanAccountDistributionRepository.findFirstByCreatedDateIsBetweenAndLoanAccountBasicInfoOrderByCreatedDateDesc(startDate, endDate, byAccountNo);
                PeopleAllocationLogicInfo peopleAllocationLogicInfo = peopleAllocationLogicRepository.findById(new Long(dealerId)).get();

                loanAccountDistributionInfo.setLoanAccountBasicInfo(byAccountNo);
                loanAccountDistributionInfo.setSamAccount("0");
                loanAccountDistributionInfo.setRemarks(remarks);
                loanAccountDistributionInfo.setWriteOffAccount("0");
                loanAccountDistributionInfo.setLatest("1");
                loanAccountDistributionInfo.setMonitoringStatus("SINGLE");
                loanAccountDistributionInfo.setDealerName(peopleAllocationLogicInfo.getDealer().getUser().getFirstName());
                loanAccountDistributionInfo.setDealerPin(peopleAllocationLogicInfo.getDealer().getPin());
                loanAccountDistributionInfo.setCreatedDate(new Date());
                loanAccountDistributionInfo.setStatusDate(new Date());
                loanAccountDistributionInfo.setSupervisorName(peopleAllocationLogicInfo.getSupervisor().getUser().getFirstName());
                loanAccountDistributionInfo.setSupervisorPin(peopleAllocationLogicInfo.getSupervisor().getPin());
                if (loanAccountDistributionInfo1 != null) {
                    loanAccountDistributionInfo.setDpdBucket(loanAccountDistributionInfo1.getDpdBucket());
                    loanAccountDistributionInfo.setOutStanding(loanAccountDistributionInfo1.getOutStanding());
                    loanAccountDistributionInfo.setEmiAmount(loanAccountDistributionInfo1.getEmiAmount());
                    loanAccountDistributionInfo.setProductGroup(loanAccountDistributionInfo1.getProductGroup());
                    loanAccountDistributionInfo.setSchemeCode(loanAccountDistributionInfo1.getSchemeCode());
                    loanAccountDistributionInfo.setDpd(loanAccountDistributionInfo1.getDpd());
                    loanAccountDistributionInfo.setOpeningOverDue(loanAccountDistributionInfo1.getOpeningOverDue());
                }
                loanAccountDistributionInfo.setCreatedBy(user.getUsername());

                loanAccountDistributionService.save(loanAccountDistributionInfo);
            } else if (byId.getProductUnit().equals("Card")) {
                CardAccountBasicInfo byAccountNo = cardAccountBasicService.getByAccountNo(byId.getCardNo());
                CardAccountDistributionInfo cardAccountDistributionInfo1 = cardAccountDistributionRepository.findFirstByCreatedDateIsBetweenAndCardAccountBasicInfoOrderByCreatedDateDesc(startDate, endDate, byAccountNo);
                PeopleAllocationLogicInfo peopleAllocationLogicInfo = peopleAllocationLogicRepository.findById(new Long(dealerId)).get();

                cardAccountDistributionInfo.setCardAccountBasicInfo(byAccountNo);
                cardAccountDistributionInfo.setSamAccount("0");
                cardAccountDistributionInfo.setRemarks(remarks);
                cardAccountDistributionInfo.setWriteOffAccount("0");
                cardAccountDistributionInfo.setLatest("1");
                cardAccountDistributionInfo.setMonitoringStatus("SINGLE");
                cardAccountDistributionInfo.setDealerName(peopleAllocationLogicInfo.getDealer().getUser().getFirstName());
                cardAccountDistributionInfo.setDealerPin(peopleAllocationLogicInfo.getDealer().getPin());

                cardAccountDistributionInfo.setOutstandingAmount(byId.getOutstandingAmount());
                cardAccountDistributionInfo.setAgeCode(byId.getAgeCode());
                cardAccountDistributionInfo.setCreatedDate(new Date());
                cardAccountDistributionInfo.setStatusDate(new Date());
                cardAccountDistributionInfo.setCreatedBy(user.getUsername());
                cardAccountDistributionInfo.setSupervisorName(peopleAllocationLogicInfo.getSupervisor().getUser().getFirstName());
                cardAccountDistributionInfo.setSupervisorPin(peopleAllocationLogicInfo.getSupervisor().getPin());
                cardAccountDistributionInfo.setBillingCycle(byId.getBillingCycle());
                cardAccountDistributionInfo.setPlasticCd(byId.getPlasticCd());
                cardAccountDistributionInfo.setSecureCard(byId.getSecureCard());
                cardAccountDistributionInfo.setStatusDate(byId.getStatusDate());
                cardAccountDistributionInfo.setVvip(byId.getVvip());

                cardAccountDistributionService.saveNew(cardAccountDistributionInfo);
            }
        }
        samAccountHandoverRepository.saveAll(samAccountHandoverInfos);
        return true;
    }
}
