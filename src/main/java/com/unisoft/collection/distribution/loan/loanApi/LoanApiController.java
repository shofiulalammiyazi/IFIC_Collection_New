package com.unisoft.collection.distribution.loan.loanApi;
/*
Created by   Islam at 7/28/2019
*/

import com.unisoft.collection.allocationLogic.PeopleAllocationLogicInfo;
import com.unisoft.collection.allocationLogic.PeopleAllocationLogicRepository;
import com.unisoft.collection.distribution.agencyAllocation.loan.LoanAgencyDistributionInfo;
import com.unisoft.collection.distribution.agencyAllocation.loan.LoanAgencyDistributionRepository;
import com.unisoft.collection.distribution.agencyAllocation.loan.LoanAgencyDistributionService;
import com.unisoft.collection.distribution.loan.LoanAccountDistributionRepository;
import com.unisoft.collection.distribution.loan.LoanAccountSearchService;
import com.unisoft.collection.distribution.loan.LoanDualEnum;
import com.unisoft.collection.distribution.loan.loanAccountBasic.LoanAccountBasicInfo;
import com.unisoft.collection.distribution.loan.loanAccountBasic.LoanAccountBasicService;
import com.unisoft.collection.distribution.loan.loanAccountDistribution.LoanAccountDistributionInfo;
import com.unisoft.collection.distribution.samAccountHandover.SamAccountHandoverInfo;
import com.unisoft.collection.distribution.samAccountHandover.SamAccountHandoverRepository;
import com.unisoft.collection.distribution.writeOff.WriteOffAccountInfo;
import com.unisoft.collection.distribution.writeOff.WriteOffAccountRepository;
import com.unisoft.collection.settings.agency.AgencyEntity;
import com.unisoft.collection.settings.agency.AgencyService;
import com.unisoft.user.UserPrincipal;
import com.unisoft.utillity.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/collection/distribution/api")
public class LoanApiController {

    private final LoanAccountBasicService loanAccountBasicService;

    private final LoanAgencyDistributionService loanAgencyDistributionService;

    private final AgencyService agencyService;

    private final LoanAccountDistributionRepository loanAccountDistributionRepository;

    private final SamAccountHandoverRepository samAccountHandoverRepository;

    private final WriteOffAccountRepository writeOffAccountRepository;

    private final PeopleAllocationLogicRepository peopleAllocationLogicRepository;

    private final LoanAgencyDistributionRepository loanAgencyDistributionRepository;

    private final LoanAccountSearchService loanAccountSearchService;

    private final DateUtils dateUtils;

    @PostMapping("/loanupdate")
    public boolean updateLoanDistribution(@Valid @RequestBody LoanApiPayload loanApiPayload) {
        String dealername = "";
        String dealerPin = "";
        String supervisorPin = "";
        String supervisorName = "";
        PeopleAllocationLogicInfo peopleAllocationLogicInfo = peopleAllocationLogicRepository.findById(new Long(loanApiPayload.getId())).get();

        if (peopleAllocationLogicInfo != null) {
            dealerPin = peopleAllocationLogicInfo.getDealer().getPin();
            dealername = peopleAllocationLogicInfo.getDealer().getUser().getFirstName();
            supervisorPin = peopleAllocationLogicInfo.getSupervisor().getPin();
            supervisorName = peopleAllocationLogicInfo.getSupervisor().getUser().getFirstName();
        }

        for (String s : loanApiPayload.getList()) {
            LoanAccountBasicInfo loanAccountBasicInfo = loanAccountBasicService.getByAccountNoNew(s);
            LoanAccountDistributionInfo loanAccountDistributionInfo = loanAccountSearchService.findFromLoanAccountDistributionInfo(loanAccountBasicInfo);

            if (loanAccountDistributionInfo != null) {

                if (loanAccountDistributionInfo.getMonitoringStatus().toUpperCase().equals("SINGLE")) {
                    loanAccountDistributionInfo.setDealerPin(dealerPin);
                    loanAccountDistributionInfo.setDealerName(dealername);
                    loanAccountDistributionInfo.setSupervisorPin(supervisorPin);
                    loanAccountDistributionInfo.setSupervisorName(supervisorName);
                    loanAccountDistributionRepository.save(loanAccountDistributionInfo);
                } else {
                    LoanAgencyDistributionInfo loanAgencyDistributionInfo = loanAccountSearchService.findFromLoanAgencyDistributionInfo(loanAccountBasicInfo);
                    loanAgencyDistributionInfo.setLatest("0");
                    loanAgencyDistributionRepository.save(loanAgencyDistributionInfo);

                    loanAccountDistributionInfo.setMonitoringStatus("SINGLE");
                    loanAccountDistributionInfo.setDealerPin(dealerPin);
                    loanAccountDistributionInfo.setDealerName(dealername);
                    loanAccountDistributionInfo.setSupervisorPin(supervisorPin);
                    loanAccountDistributionInfo.setSupervisorName(supervisorName);
                    loanAccountDistributionRepository.save(loanAccountDistributionInfo);
                }
            } else {
                LoanAgencyDistributionInfo loanAgencyDistributionInfo = loanAccountSearchService.findFromLoanAgencyDistributionInfo(loanAccountBasicInfo);

                if (loanAgencyDistributionInfo.getLoanDualEnum().toString().equals("SINGLE")) {
                    loanAgencyDistributionInfo.setLatest("0");
                    loanAgencyDistributionRepository.save(loanAgencyDistributionInfo);

                    LoanAccountDistributionInfo loanAccountDistributionInfo1 = new LoanAccountDistributionInfo();
                    loanAccountDistributionInfo1.setMonitoringStatus("SINGLE");
                    loanAccountDistributionInfo1.setLatest("1");
                    loanAccountDistributionInfo1.setWriteOffAccount("0");
                    loanAccountDistributionInfo1.setSamAccount("0");
                    loanAccountDistributionInfo1.setCreatedDate(new Date());
                    loanAccountDistributionInfo1.setDealerPin(dealerPin);
                    loanAccountDistributionInfo1.setDealerName(dealername);
                    loanAccountDistributionInfo1.setSupervisorPin(supervisorPin);
                    loanAccountDistributionInfo1.setSupervisorName(supervisorName);
                    loanAccountDistributionInfo1.setOutStanding(loanAgencyDistributionInfo.getOutstanding());
                    loanAccountDistributionInfo1.setDpdBucket(loanAgencyDistributionInfo.getDpdBucket());
                    loanAccountDistributionInfo1.setLoanAccountBasicInfo(loanAccountBasicInfo);

                    LoanAccountDistributionInfo loanAccountDistributionInfo2 = loanAccountDistributionRepository.findFirstByCreatedDateIsBetweenAndLoanAccountBasicInfoOrderByCreatedDateDesc(dateUtils.getMonthStartDate(), dateUtils.getMonthEndDate(), loanAccountBasicInfo);
                    if (loanAccountDistributionInfo2 != null) {
                        loanAccountDistributionInfo1.setProductGroup(loanAccountDistributionInfo2.getProductGroup());
                        loanAccountDistributionInfo1.setDpd(loanAccountDistributionInfo2.getDpd());
                        loanAccountDistributionInfo1.setSchemeCode(loanAccountDistributionInfo2.getSchemeCode());
                        loanAccountDistributionInfo1.setEmiAmount(loanAccountDistributionInfo2.getEmiAmount());
                        loanAccountDistributionInfo1.setEmiDueDate(loanAccountDistributionInfo2.getEmiDueDate());
                        loanAccountDistributionInfo1.setStatusDate(loanAccountDistributionInfo2.getStatusDate());
                    }
                    loanAccountDistributionRepository.save(loanAccountDistributionInfo1);
                } else {
                    loanAgencyDistributionInfo.setLatest("0");
                    loanAgencyDistributionRepository.save(loanAgencyDistributionInfo);

                    LoanAccountDistributionInfo loanAccountDistributionInfo3 = loanAccountSearchService.findFromLoanAccountDistributionInfo(loanAccountBasicInfo);
                    loanAccountDistributionInfo3.setMonitoringStatus("SINGLE");
                    loanAccountDistributionRepository.save(loanAccountDistributionInfo3);
                }
            }
        }

        return true;
    }

    @PostMapping("/agencyloanupdate")
    public boolean updateLoanDistributionAgency(@Valid @RequestBody LoanApiPayload loanApiPayload) {
        AgencyEntity agencyEntity = agencyService.getById(Long.parseLong(loanApiPayload.getId()));

        for (String s : loanApiPayload.getList()) {
            LoanAccountBasicInfo loanAccountBasicInfo = loanAccountBasicService.getByAccountNo(s);
            LoanAccountDistributionInfo loanAccountDistributionInfo = loanAccountDistributionRepository.findFirstByLoanAccountBasicInfoOrderByCreatedDateDesc(loanAccountBasicInfo);

            LoanAgencyDistributionInfo loanAgencyDistributionInfo = new LoanAgencyDistributionInfo();
            loanAgencyDistributionInfo.setId(null);
            loanAgencyDistributionInfo.setCreatedDate(new Date());
            loanAgencyDistributionInfo.setAgencyName(agencyEntity.getName());
            loanAgencyDistributionInfo.setDpdBucket(loanAccountDistributionInfo.getDpdBucket());
            loanAgencyDistributionInfo.setOutstanding(loanAccountDistributionInfo.getOutStanding());
            loanAgencyDistributionInfo.setLatest("1");
            loanAgencyDistributionInfo.setLoanAccountBasicInfo(loanAccountBasicInfo);
            if (loanApiPayload.getDualorsingle().equals(LoanDualEnum.DUAL.toString())) {
                loanAgencyDistributionInfo.setLoanDualEnum(LoanDualEnum.DUAL);
            } else {
                loanAgencyDistributionInfo.setLoanDualEnum(LoanDualEnum.SINGLE);
            }
            /* According to Lanka Bangla Finance LoanApi Controller Logic */
            if (loanAccountDistributionInfo == null) {
                LoanAgencyDistributionInfo existingLoanAgencyDistributionInfo = loanAgencyDistributionService.findByCreatedDateIsBetweenAndLoanAccountBasicInfoAndLatest(loanAccountBasicInfo);
                loanAgencyDistributionInfo.setDpdBucket(existingLoanAgencyDistributionInfo.getDpdBucket());
                loanAgencyDistributionInfo.setOutstanding(existingLoanAgencyDistributionInfo.getOutstanding());
                existingLoanAgencyDistributionInfo.setLatest("0");
                loanAgencyDistributionService.updateAgency(loanAgencyDistributionInfo);
            } else {
                loanAgencyDistributionInfo.setDpdBucket(loanAccountDistributionInfo.getDpdBucket());
                loanAgencyDistributionInfo.setOutstanding(loanAccountDistributionInfo.getOutStanding());
                if (loanApiPayload.getDualorsingle().equals(LoanDualEnum.DUAL.toString()))
                    loanAccountDistributionInfo.setMonitoringStatus("DUAL");
                else
                    loanAccountDistributionInfo.setLatest("0");

                loanAccountDistributionRepository.save(loanAccountDistributionInfo);
                LoanAgencyDistributionInfo existingLoanAgencyDistributionInfo = loanAgencyDistributionService.findByCreatedDateIsBetweenAndLoanAccountBasicInfoAndLatest(loanAccountBasicInfo);
                if (existingLoanAgencyDistributionInfo != null) {
                    existingLoanAgencyDistributionInfo.setLatest("0");
                    loanAgencyDistributionService.updateAgency(existingLoanAgencyDistributionInfo);
                }
            }
            /* According to Lanka Bangla Finance LoanApi Controller Logic */

            loanAgencyDistributionService.saveNew(loanAgencyDistributionInfo);
        }
        return true;
    }

    @PostMapping("/loansendtosam")
    public boolean sendLoanToSam(@Valid @RequestBody LoanApiPayload loanApiPayload) {
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<SamAccountHandoverInfo> samAccountHandoverInfos = new ArrayList<>();

        for (String accountNo : loanApiPayload.getList()) {
            LoanAccountBasicInfo loanAccountBasicInfo = loanAccountBasicService.getByAccountNo(accountNo);
            LoanAccountDistributionInfo distributionInfo = loanAccountSearchService.findFromLoanAccountDistributionInfo(loanAccountBasicInfo);

            if (distributionInfo != null) {
                distributionInfo.setLatest("0");
                distributionInfo.setSamAccount("1");
                if (distributionInfo.getMonitoringStatus().toUpperCase().equals("DUAL")) {
                    LoanAgencyDistributionInfo loanAgencyDistributionInfo = loanAccountSearchService.findFromLoanAgencyDistributionInfo(loanAccountBasicInfo);
                    loanAgencyDistributionInfo.setLatest("0");
                    loanAgencyDistributionRepository.save(loanAgencyDistributionInfo);
                }
                loanAccountDistributionRepository.save(distributionInfo);


                SamAccountHandoverInfo samAccountHandoverInfo = new SamAccountHandoverInfo();
                samAccountHandoverInfo.setSamAccount("1");
                samAccountHandoverInfo.setProductGroup(distributionInfo.getProductGroup());
                samAccountHandoverInfo.setSchemaCode(distributionInfo.getSchemeCode());
                samAccountHandoverInfo.setOutstandingAmount(distributionInfo.getOutStanding());
                samAccountHandoverInfo.setDpdBucket(distributionInfo.getDpdBucket());
                samAccountHandoverInfo.setSamRecievedDate(new Date());
                samAccountHandoverInfo.setAccountName(loanAccountBasicInfo.getAccountName());
                samAccountHandoverInfo.setLoanAccountNo(loanAccountBasicInfo.getAccountNo());
                samAccountHandoverInfo.setCustomerId(loanAccountBasicInfo.getCustomer().getCustomerId());
                samAccountHandoverInfo.setCreatedDate(new Date());
                samAccountHandoverInfo.setLatest("1");
                samAccountHandoverInfo.setProductUnit("Loan");
                samAccountHandoverInfo.setLoanAccountBasicInfo(loanAccountBasicInfo);

                samAccountHandoverInfos.add(samAccountHandoverInfo);
            } else {
                LoanAgencyDistributionInfo loanAgencyDistributionInfo = loanAccountSearchService.findFromLoanAgencyDistributionInfo(loanAccountBasicInfo);
                loanAgencyDistributionInfo.setLatest("0");
                loanAgencyDistributionRepository.save(loanAgencyDistributionInfo);

                LoanAccountDistributionInfo loanAccountDistributionInfo = loanAccountDistributionRepository.findFirstByCreatedDateIsBetweenAndLoanAccountBasicInfoOrderByCreatedDateDesc(dateUtils.getMonthStartDate(), dateUtils.getMonthEndDate(), loanAccountBasicInfo);

                SamAccountHandoverInfo samAccountHandoverInfo = new SamAccountHandoverInfo();
                samAccountHandoverInfo.setSamAccount("1");
                samAccountHandoverInfo.setProductGroup(loanAccountDistributionInfo.getProductGroup());
                samAccountHandoverInfo.setSchemaCode(loanAccountDistributionInfo.getSchemeCode());
                samAccountHandoverInfo.setOutstandingAmount(loanAccountDistributionInfo.getOutStanding());
                samAccountHandoverInfo.setDpdBucket(loanAccountDistributionInfo.getDpdBucket());
                samAccountHandoverInfo.setSamRecievedDate(new Date());
                samAccountHandoverInfo.setAccountName(loanAccountBasicInfo.getAccountName());
                samAccountHandoverInfo.setLoanAccountNo(loanAccountBasicInfo.getAccountNo());
                samAccountHandoverInfo.setCustomerId(loanAccountBasicInfo.getCustomer().getCustomerId());
                samAccountHandoverInfo.setCreatedDate(new Date());
                samAccountHandoverInfo.setCreatedBy(userPrincipal.getUsername());
                samAccountHandoverInfo.setLatest("1");
                samAccountHandoverInfo.setProductUnit("Loan");
                samAccountHandoverInfo.setLoanAccountBasicInfo(loanAccountBasicInfo);

                samAccountHandoverInfos.add(samAccountHandoverInfo);


            }

        }
        samAccountHandoverRepository.saveAll(samAccountHandoverInfos);
        return true;
    }

    @PostMapping("/loansendtowriteOff")
    public boolean sendLoanToWriteoff(@Valid @RequestBody LoanApiPayload loanApiPayload) {
        List<WriteOffAccountInfo> writeOffAccountInfos = new ArrayList<>();

        for (String s : loanApiPayload.getList()) {
            LoanAccountBasicInfo loanAccountBasicInfo = loanAccountBasicService.getByAccountNo(s);
            LoanAccountDistributionInfo distributionInfo = loanAccountSearchService.findFromLoanAccountDistributionInfo(loanAccountBasicInfo);

            if (distributionInfo != null) {
                distributionInfo.setLatest("0");
                distributionInfo.setWriteOffAccount("1");
                loanAccountDistributionRepository.save(distributionInfo);

                LoanAgencyDistributionInfo loanAgencyDistributionInfo = loanAccountSearchService.findFromLoanAgencyDistributionInfo(loanAccountBasicInfo);
                if (loanAgencyDistributionInfo != null) {
                    loanAgencyDistributionInfo.setLatest("0");
                    loanAgencyDistributionRepository.save(loanAgencyDistributionInfo);
                }


                WriteOffAccountInfo writeOffAccountInfo = new WriteOffAccountInfo();
                writeOffAccountInfo.setWriteOffAccount("1");
                writeOffAccountInfo.setSchemeCode(distributionInfo.getSchemeCode());
                writeOffAccountInfo.setDpdBucket(distributionInfo.getDpdBucket());
                writeOffAccountInfo.setProductType(distributionInfo.getProductGroup());
                writeOffAccountInfo.setPresentOutstanding(distributionInfo.getOutStanding());
                writeOffAccountInfo.setLoanAccountName(loanAccountBasicInfo.getAccountName());
                writeOffAccountInfo.setLoanAccountNo(loanAccountBasicInfo.getAccountNo());
                writeOffAccountInfo.setPrductUnit("Loan");
                writeOffAccountInfo.setCreatedDate(new Date());
                writeOffAccountInfo.setLoanAccountBasicInfo(loanAccountBasicInfo);
                writeOffAccountInfos.add(writeOffAccountInfo);
            } else {
                LoanAgencyDistributionInfo loanAgencyDistributionInfo = loanAccountSearchService.findFromLoanAgencyDistributionInfo(loanAccountBasicInfo);
                loanAgencyDistributionInfo.setLatest("0");
                loanAgencyDistributionRepository.save(loanAgencyDistributionInfo);

                LoanAccountDistributionInfo loanAccountDistributionInfo = loanAccountDistributionRepository.findFirstByCreatedDateIsBetweenAndLoanAccountBasicInfoOrderByCreatedDateDesc(dateUtils.getMonthStartDate(), dateUtils.getMonthEndDate(), loanAccountBasicInfo);
                WriteOffAccountInfo writeOffAccountInfo = new WriteOffAccountInfo();
                writeOffAccountInfo.setWriteOffAccount("1");
                writeOffAccountInfo.setSchemeCode(loanAccountDistributionInfo.getSchemeCode());
                writeOffAccountInfo.setDpdBucket(loanAccountDistributionInfo.getDpdBucket());
                writeOffAccountInfo.setProductType(loanAccountDistributionInfo.getProductGroup());
                writeOffAccountInfo.setPresentOutstanding(loanAccountDistributionInfo.getOutStanding());
                writeOffAccountInfo.setLoanAccountName(loanAccountBasicInfo.getAccountName());
                writeOffAccountInfo.setLoanAccountNo(loanAccountBasicInfo.getAccountNo());
                writeOffAccountInfo.setPrductUnit("Loan");
                writeOffAccountInfo.setCreatedDate(new Date());
                writeOffAccountInfo.setLoanAccountBasicInfo(loanAccountBasicInfo);
                writeOffAccountInfos.add(writeOffAccountInfo);

            }
        }
        writeOffAccountRepository.saveAll(writeOffAccountInfos);
        return true;
    }

}
