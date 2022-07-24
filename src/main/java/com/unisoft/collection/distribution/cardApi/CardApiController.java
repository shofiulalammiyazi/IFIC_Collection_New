package com.unisoft.collection.distribution.cardApi;
/*
Created by   Islam at 7/29/2019
*/

import com.unisoft.collection.allocationLogic.PeopleAllocationLogicInfo;
import com.unisoft.collection.allocationLogic.PeopleAllocationLogicRepository;
import com.unisoft.retail.card.dataEntry.distribution.agency.CardAgencyDistributionInfo;
import com.unisoft.retail.card.dataEntry.distribution.agency.CardAgencyDistributionRepository;
import com.unisoft.retail.card.dataEntry.distribution.agency.CardAgencyDistributionService;
import com.unisoft.retail.card.dataEntry.distribution.accountDistributionInfo.CardAccountDistributionRepository;
import com.unisoft.collection.distribution.card.CardAccountSearchService;
import com.unisoft.retail.card.dataEntry.distribution.accountBasicInfo.CardAccountBasicInfo;
import com.unisoft.retail.card.dataEntry.distribution.accountBasicInfo.CardAccountBasicService;
import com.unisoft.retail.card.dataEntry.distribution.accountDistributionInfo.CardAccountDistributionInfo;
import com.unisoft.collection.distribution.loan.LoanDualEnum;
import com.unisoft.collection.distribution.loan.loanApi.LoanApiPayload;
import com.unisoft.collection.distribution.samAccountHandover.SamAccountHandoverInfo;
import com.unisoft.collection.distribution.samAccountHandover.SamAccountHandoverRepository;
import com.unisoft.collection.distribution.writeOff.WriteOffAccountInfo;
import com.unisoft.collection.distribution.writeOff.WriteOffAccountRepository;
import com.unisoft.collection.settings.agency.AgencyEntity;
import com.unisoft.collection.settings.agency.AgencyService;
import com.unisoft.collection.settings.employee.EmployeeInfoEntity;
import com.unisoft.collection.settings.employee.EmployeeService;
import com.unisoft.collection.settings.location.LocationEntity;
import com.unisoft.collection.settings.location.LocationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/collection/distribution/api/")
public class CardApiController {

    private EmployeeService employeeService;

    private CardAccountBasicService cardAccountBasicService;

    private AgencyService agencyService;

    private CardAgencyDistributionService cardAgencyDistributionService;

    private CardAccountDistributionRepository cardAccountDistributionRepository;

    private SamAccountHandoverRepository samAccountHandoverRepository;

    private WriteOffAccountRepository writeOffAccountRepository;

    private CardAgencyDistributionRepository cardAgencyDistributionRepository;

    private PeopleAllocationLogicRepository peopleAllocationLogicRepository;

    private LocationService locationService;

    private CardAccountSearchService cardAccountSearchService;


    @PostMapping("/cardupdate")
    public boolean updateLoanDistribution(@Valid @RequestBody CardApiPayload cardApiPayload) {

        String dealername = "";
        String dealerPin = "";
        String supervisorPin = "";
        String supervisorName = "";

        EmployeeInfoEntity employeeInfoEntity = employeeService.getById(new Long(cardApiPayload.getId()));

        if (employeeInfoEntity.getDesignation().getName().toUpperCase().equals("Dealer")) {
            Optional<PeopleAllocationLogicInfo> peopleAllocationLogicInfo = peopleAllocationLogicRepository.findById(new Long(cardApiPayload.getId()));

            if (peopleAllocationLogicInfo.isPresent()) {
                dealerPin = peopleAllocationLogicInfo.get().getDealer().getPin();
                dealername = peopleAllocationLogicInfo.get().getDealer().getUser().getFirstName();
                supervisorPin = peopleAllocationLogicInfo.get().getSupervisor().getPin();
                supervisorName = peopleAllocationLogicInfo.get().getSupervisor().getUser().getFirstName();
            }

        } else {
            dealerPin = employeeInfoEntity.getPin();
            dealername = employeeInfoEntity.getUser().getFirstName();
            supervisorPin = employeeInfoEntity.getPin();
            supervisorName = employeeInfoEntity.getUser().getFirstName();
        }

        for (String s : cardApiPayload.getList()) {
            CardAccountBasicInfo cardAccountBasicInfo = cardAccountBasicService.getByAccountNo(s);
            CardAccountDistributionInfo cardAccountDistributionInfo = cardAccountSearchService.findFromCardAccountDistributionInfo(cardAccountBasicInfo);

            if (cardAccountDistributionInfo != null) {
                if (cardAccountDistributionInfo.getMonitoringStatus().toUpperCase().equals("SINGLE")) {
                    cardAccountDistributionInfo.setDealerPin(dealerPin);
                    cardAccountDistributionInfo.setDealerName(dealername);
                    cardAccountDistributionInfo.setSupervisorPin(supervisorPin);
                    cardAccountDistributionInfo.setSupervisorName(supervisorName);
                    cardAccountDistributionRepository.save(cardAccountDistributionInfo);
                } else {
                    CardAgencyDistributionInfo cardAgencyDistributionInfo = cardAccountSearchService.findFromCardAgencyDistributionInfo(cardAccountBasicInfo);
                    cardAgencyDistributionInfo.setLatest("0");
                    cardAgencyDistributionRepository.save(cardAgencyDistributionInfo);

                    cardAccountDistributionInfo.setMonitoringStatus("SINGLE");
                    cardAccountDistributionInfo.setDealerPin(dealerPin);
                    cardAccountDistributionInfo.setDealerName(dealername);
                    cardAccountDistributionInfo.setSupervisorPin(supervisorPin);
                    cardAccountDistributionInfo.setSupervisorName(supervisorName);
                    cardAccountDistributionRepository.save(cardAccountDistributionInfo);
                }
            } else {
                CardAgencyDistributionInfo firstByLoanAccountBasicInfoOrderByCreatedDateDesc = cardAccountSearchService.findFromCardAgencyDistributionInfo(cardAccountBasicInfo);

                if (firstByLoanAccountBasicInfoOrderByCreatedDateDesc.getLoanDualEnum().toString().equals("SINGLE")) {
                    firstByLoanAccountBasicInfoOrderByCreatedDateDesc.setLatest("0");
                    cardAgencyDistributionRepository.save(firstByLoanAccountBasicInfoOrderByCreatedDateDesc);

                    CardAccountDistributionInfo cardAccountDistributionInfo1 = new CardAccountDistributionInfo();
                    cardAccountDistributionInfo1.setMonitoringStatus("SINGLE");
                    cardAccountDistributionInfo1.setLatest("1");
                    cardAccountDistributionInfo1.setWriteOffAccount("0");
                    cardAccountDistributionInfo1.setSamAccount("0");
                    cardAccountDistributionInfo1.setCreatedDate(new Date());
                    cardAccountDistributionInfo1.setDealerPin(dealerPin);
                    cardAccountDistributionInfo1.setDealerName(dealername);
                    cardAccountDistributionInfo1.setSupervisorPin(supervisorPin);
                    cardAccountDistributionInfo1.setSupervisorName(supervisorName);
                    cardAccountDistributionInfo1.setOutstandingAmount(firstByLoanAccountBasicInfoOrderByCreatedDateDesc.getOutstanding());
                    cardAccountDistributionInfo1.setAgeCode(firstByLoanAccountBasicInfoOrderByCreatedDateDesc.getAgeCode());
                    cardAccountDistributionInfo1.setCardAccountBasicInfo(cardAccountBasicInfo);

                    CardAccountDistributionInfo cardAccountDistributionInfo2 = cardAccountDistributionRepository.findFirstByCreatedDateIsBetweenAndCardAccountBasicInfoOrderByCreatedDateDesc(getStartDate(), getEndDate(), cardAccountBasicInfo);

                    if (cardAccountDistributionInfo2 != null) {
                        cardAccountDistributionInfo1.setProductGroup(cardAccountDistributionInfo2.getProductGroup());
                        cardAccountDistributionInfo1.setMinDuePayment(cardAccountDistributionInfo2.getMinDuePayment());
                        cardAccountDistributionInfo1.setProductGroup(cardAccountDistributionInfo2.getProductGroup());
                        cardAccountDistributionInfo1.setProductType(cardAccountDistributionInfo2.getProductType());
                        cardAccountDistributionInfo1.setCashCollection(cardAccountDistributionInfo2.getCashCollection());
                        cardAccountDistributionInfo1.setStatusDate(cardAccountDistributionInfo2.getStatusDate());
                    }
                    cardAccountDistributionRepository.save(cardAccountDistributionInfo1);
                } else {
                    firstByLoanAccountBasicInfoOrderByCreatedDateDesc.setLatest("0");
                    cardAgencyDistributionRepository.save(firstByLoanAccountBasicInfoOrderByCreatedDateDesc);

                    CardAccountDistributionInfo cardAccountDistributionInfo3 = cardAccountSearchService.findFromCardAccountDistributionInfo(cardAccountBasicInfo);
                    cardAccountDistributionInfo3.setMonitoringStatus("SINGLE");
                    cardAccountDistributionRepository.save(cardAccountDistributionInfo3);
                }
            }
        }
        return true;
    }

    @PostMapping("/agencycardupdate")
    public boolean updateLoanDistributionAgency(@Valid @RequestBody LoanApiPayload loanApiPayload) {
        AgencyEntity agencyEntity = agencyService.getById(Long.parseLong(loanApiPayload.getId()));

        for (String s : loanApiPayload.getList()) {
            CardAccountBasicInfo cardAccountBasicInfo = cardAccountBasicService.getByAccountNo(s);
            CardAccountDistributionInfo cardAccountDistributionInfo = cardAccountSearchService.findFromCardAccountDistributionInfo(cardAccountBasicInfo);

            CardAgencyDistributionInfo cardAgencyDistributionInfo = new CardAgencyDistributionInfo();
            cardAgencyDistributionInfo.setId(null);
            cardAgencyDistributionInfo.setCreatedDate(new Date());
            cardAgencyDistributionInfo.setAgencyName(agencyEntity.getName());
            cardAgencyDistributionInfo.setLatest("1");
            cardAgencyDistributionInfo.setCardAccountBasicInfo(cardAccountBasicInfo);

            if (cardAccountDistributionInfo == null) {
                CardAgencyDistributionInfo fromDataBase = cardAccountSearchService.findFromCardAgencyDistributionInfo(cardAccountBasicInfo);
                cardAgencyDistributionInfo.setAgeCode(fromDataBase.getAgeCode());
                cardAgencyDistributionInfo.setOutstanding(fromDataBase.getOutstanding());
                fromDataBase.setLatest("0");
                cardAgencyDistributionRepository.save(fromDataBase);
            } else {
                cardAgencyDistributionInfo.setAgeCode(cardAccountDistributionInfo.getAgeCode());
                cardAgencyDistributionInfo.setOutstanding(cardAccountDistributionInfo.getOutstandingAmount());
                if (loanApiPayload.getDualorsingle().equals(LoanDualEnum.DUAL.toString())) cardAccountDistributionInfo.setMonitoringStatus("DUAL");
                else cardAccountDistributionInfo.setLatest("0");
                cardAccountDistributionRepository.save(cardAccountDistributionInfo);
                CardAgencyDistributionInfo fromDataBase = cardAccountSearchService.findFromCardAgencyDistributionInfo(cardAccountBasicInfo);

                if (fromDataBase != null) {
                    fromDataBase.setLatest("0");
                    cardAgencyDistributionRepository.save(fromDataBase);
                }
            }

            if (loanApiPayload.getDualorsingle().equals(LoanDualEnum.DUAL.toString()))
                cardAgencyDistributionInfo.setLoanDualEnum(LoanDualEnum.DUAL);
            else
                cardAgencyDistributionInfo.setLoanDualEnum(LoanDualEnum.SINGLE);

            cardAgencyDistributionService.saveNew(cardAgencyDistributionInfo);
        }
        return true;
    }

    @PostMapping("/cardsendtosam")
    public boolean sendCardToSam(@Valid @RequestBody LoanApiPayload loanApiPayload) {

        List<SamAccountHandoverInfo> samAccountHandoverInfos = new ArrayList<>();

        for (String s : loanApiPayload.getList()) {
            CardAccountBasicInfo cardAccountBasicInfo = cardAccountBasicService.getByAccountNo(s);
            CardAccountDistributionInfo distributionInfo = cardAccountSearchService.findFromCardAccountDistributionInfo(cardAccountBasicInfo);

            if (distributionInfo != null) {
                distributionInfo.setLatest("0");
                distributionInfo.setSamAccount("1");
                cardAccountDistributionRepository.save(distributionInfo);

                if (distributionInfo.getMonitoringStatus().toUpperCase().equals("DUAL")) {
                    CardAgencyDistributionInfo cardAgencyDistributionInfo = cardAccountSearchService.findFromCardAgencyDistributionInfo(cardAccountBasicInfo);
                    cardAgencyDistributionInfo.setLatest("0");
                    cardAgencyDistributionRepository.save(cardAgencyDistributionInfo);
                }

                SamAccountHandoverInfo samAccountHandoverInfo = new SamAccountHandoverInfo();
                samAccountHandoverInfo.setSamAccount("1");
                samAccountHandoverInfo.setProductGroup(distributionInfo.getProductGroup());
                samAccountHandoverInfo.setOutstandingAmount(distributionInfo.getOutstandingAmount());
                samAccountHandoverInfo.setAgeCode(distributionInfo.getAgeCode());
                samAccountHandoverInfo.setSamRecievedDate(new Date());
                samAccountHandoverInfo.setAccountName(cardAccountBasicInfo.getCardName());
                samAccountHandoverInfo.setCardNo(cardAccountBasicInfo.getCardNo());
                samAccountHandoverInfo.setCustomerId(cardAccountBasicInfo.getCustomer().getCustomerId());
                samAccountHandoverInfo.setCreatedDate(new Date());
                samAccountHandoverInfo.setLatest("1");
                samAccountHandoverInfo.setCardAccountBasicInfo(cardAccountBasicInfo);
                samAccountHandoverInfo.setProductUnit("Card");

                samAccountHandoverInfos.add(samAccountHandoverInfo);
            } else {
                CardAgencyDistributionInfo loanAgencyDistributionInfo = cardAccountSearchService.findFromCardAgencyDistributionInfo(cardAccountBasicInfo);
                loanAgencyDistributionInfo.setLatest("0");
                cardAgencyDistributionRepository.save(loanAgencyDistributionInfo);

                CardAccountDistributionInfo loanAccountDistributionInfo = cardAccountDistributionRepository.findFirstByCreatedDateIsBetweenAndCardAccountBasicInfoOrderByCreatedDateDesc(getStartDate(), getEndDate(), cardAccountBasicInfo);

                SamAccountHandoverInfo samAccountHandoverInfo = new SamAccountHandoverInfo();
                samAccountHandoverInfo.setSamAccount("1");
                if (loanAccountDistributionInfo != null) {
                    samAccountHandoverInfo.setProductGroup(loanAccountDistributionInfo.getProductGroup());
                    samAccountHandoverInfo.setOutstandingAmount(loanAccountDistributionInfo.getOutstandingAmount());
                    samAccountHandoverInfo.setAgeCode(loanAccountDistributionInfo.getAgeCode());
                }

                samAccountHandoverInfo.setSamRecievedDate(new Date());
                samAccountHandoverInfo.setAccountName(cardAccountBasicInfo.getCardName());
                samAccountHandoverInfo.setCardNo(cardAccountBasicInfo.getCardNo());
                samAccountHandoverInfo.setCustomerId(cardAccountBasicInfo.getCustomer().getCustomerId());
                samAccountHandoverInfo.setCreatedDate(new Date());
                samAccountHandoverInfo.setCardAccountBasicInfo(cardAccountBasicInfo);
                samAccountHandoverInfo.setLatest("1");
                samAccountHandoverInfo.setProductUnit("Card");

                samAccountHandoverInfos.add(samAccountHandoverInfo);
            }
        }
        samAccountHandoverRepository.saveAll(samAccountHandoverInfos);
        return true;
    }

    @PostMapping("/cardsendtowriteOff")
    public boolean sendCardToWriteoff(@Valid @RequestBody LoanApiPayload loanApiPayload) {

        List<WriteOffAccountInfo> writeOffAccountInfos = new ArrayList<>();

        for (String s : loanApiPayload.getList()) {
            CardAccountBasicInfo cardAccountBasicInfo = cardAccountBasicService.getByAccountNo(s);
            CardAccountDistributionInfo distributionInfo = cardAccountSearchService.findFromCardAccountDistributionInfo(cardAccountBasicInfo);

            if (distributionInfo != null) {
                distributionInfo.setLatest("0");
                distributionInfo.setWriteOffAccount("1");
                cardAccountDistributionRepository.save(distributionInfo);

                CardAgencyDistributionInfo cardAgencyDistributionInfo = cardAccountSearchService.findFromCardAgencyDistributionInfo(cardAccountBasicInfo);
                if (cardAgencyDistributionInfo != null) {
                    cardAgencyDistributionInfo.setLatest("0");
                    cardAgencyDistributionRepository.save(cardAgencyDistributionInfo);
                }

                WriteOffAccountInfo writeOffAccountInfo = new WriteOffAccountInfo();
                writeOffAccountInfo.setWriteOffAccount("1");
                writeOffAccountInfo.setAgeCode(distributionInfo.getAgeCode());
                writeOffAccountInfo.setProductType(distributionInfo.getProductGroup());
                writeOffAccountInfo.setPresentOutstanding(distributionInfo.getOutstandingAmount());
                writeOffAccountInfo.setCardName(cardAccountBasicInfo.getCardName());
                writeOffAccountInfo.setContractId(cardAccountBasicInfo.getCardNo());
                writeOffAccountInfo.setPrductUnit("Card");
                writeOffAccountInfo.setCreatedDate(new Date());
                writeOffAccountInfo.setCardAccountBasicInfo(cardAccountBasicInfo);
                writeOffAccountInfos.add(writeOffAccountInfo);
            } else {
                CardAgencyDistributionInfo cardAgencyDistributionInfo = cardAccountSearchService.findFromCardAgencyDistributionInfo(cardAccountBasicInfo);
                cardAgencyDistributionInfo.setLatest("0");
                cardAgencyDistributionRepository.save(cardAgencyDistributionInfo);

                CardAccountDistributionInfo cardAccountDistributionInfo = cardAccountDistributionRepository.findFirstByCreatedDateIsBetweenAndCardAccountBasicInfoOrderByCreatedDateDesc(getStartDate(), getEndDate(), cardAccountBasicInfo);
                WriteOffAccountInfo writeOffAccountInfo = new WriteOffAccountInfo();
                writeOffAccountInfo.setWriteOffAccount("1");
                writeOffAccountInfo.setAgeCode(cardAccountDistributionInfo.getAgeCode());
                writeOffAccountInfo.setProductType(cardAccountDistributionInfo.getProductGroup());
                writeOffAccountInfo.setPresentOutstanding(cardAccountDistributionInfo.getOutstandingAmount());
                writeOffAccountInfo.setCardName(cardAccountBasicInfo.getCardName());
                writeOffAccountInfo.setContractId(cardAccountBasicInfo.getCardNo());
                writeOffAccountInfo.setPrductUnit("Loan");
                writeOffAccountInfo.setCreatedDate(new Date());
                writeOffAccountInfo.setCardAccountBasicInfo(cardAccountBasicInfo);
                writeOffAccountInfos.add(writeOffAccountInfo);
            }
        }
        writeOffAccountRepository.saveAll(writeOffAccountInfos);
        return true;
    }

    @PostMapping("/locationupdate")
    public boolean updateManualLocation(@Valid @RequestBody LoanApiPayload loanApiPayload) {
        LocationEntity locationEntity = locationService.getById(new Long(loanApiPayload.getId()));

        for (String accountNo : loanApiPayload.getList()) {
            CardAccountBasicInfo cardAccountBasicInfo = cardAccountBasicService.getByAccountNo(accountNo);
            cardAccountBasicInfo.setLocation(locationEntity.getCity());
            cardAccountBasicService.updateAgency(cardAccountBasicInfo);
        }
        return true;
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
