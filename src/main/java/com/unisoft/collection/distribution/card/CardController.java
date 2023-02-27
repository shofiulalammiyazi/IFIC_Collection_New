package com.unisoft.collection.distribution.card;
/*
Created by   Islam at 7/16/2019
*/

import com.google.gson.Gson;
import com.unisoft.collection.allocationLogic.PeopleAllocationLogicRepository;
import com.unisoft.collection.distribution.loan.LoanDualEnum;
import com.unisoft.collection.settings.ExchangeRate.ExchangeRateEntity;
import com.unisoft.collection.settings.ExchangeRate.ExchangeRateService;
import com.unisoft.collection.settings.agency.AgencyService;
import com.unisoft.collection.settings.employee.EmployeeInfoEntity;
import com.unisoft.collection.settings.employee.EmployeeService;
import com.unisoft.customerbasicinfo.CustomerBasicInfoEntity;
import com.unisoft.customerbasicinfo.CustomerBasicInfoEntityRepository;
import com.unisoft.customerbasicinfo.CustomerBasicInfoService;
import com.unisoft.retail.card.dataEntry.distribution.accountBasicInfo.CardAccountBasicInfo;
import com.unisoft.retail.card.dataEntry.distribution.accountBasicInfo.CardAccountBasicService;
import com.unisoft.retail.card.dataEntry.distribution.accountDistributionInfo.CardAccountDistributionInfo;
import com.unisoft.retail.card.dataEntry.distribution.accountDistributionInfo.CardAccountDistributionRepository;
import com.unisoft.retail.card.dataEntry.distribution.accountDistributionInfo.CardAccountDistributionService;
import com.unisoft.retail.card.dataEntry.distribution.accountInfo.CardAccountInfo;
import com.unisoft.retail.card.dataEntry.distribution.accountInfo.CardAccountRepository;
import com.unisoft.retail.card.dataEntry.distribution.accountInfo.CardAccountService;
import com.unisoft.retail.card.dataEntry.distribution.accountOtherInfo.CardAccountOtherInfo;
import com.unisoft.retail.card.dataEntry.distribution.accountOtherInfo.CardAccountOtherRepository;
import com.unisoft.retail.card.dataEntry.distribution.accountOtherInfo.CardAccountOtherService;
import com.unisoft.retail.card.dataEntry.distribution.agency.CardAgencyDistributionInfo;
import com.unisoft.retail.card.dataEntry.distribution.agency.CardAgencyDistributionRepository;
import com.unisoft.user.UserPrincipal;
import lombok.AllArgsConstructor;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Controller
@AllArgsConstructor
@RequestMapping(value = "/distribution/card/")
public class CardController {

    private CardAccountBasicService cardAccountBasicService;

    private CardAccountDistributionService cardAccountDistributionService;

    private CardAccountService cardAccountService;

    private CardAccountOtherService cardAccountOtherService;

    private CardAgencyDistributionRepository cardAgencyDistributionRepository;

    private CardAccountDistributionRepository cardAccountDistributionRepository;

    private EmployeeService employeeService;

    private AgencyService agencyService;

    private PeopleAllocationLogicRepository peopleAllocationLogicRepository;

    private CustomerBasicInfoEntityRepository customerBasicInfoEntityRepository;

    private CardAccountRepository cardAccountRepository;

    private CardAccountOtherRepository cardAccountOtherRepository;

    @Autowired
    private CustomerBasicInfoService customerBasicInfoService;

    @Autowired
    private ExchangeRateService exchangeRateService;


    @GetMapping("create")
    public String addCard() {
        return "collection/distribution/card/create";
    }

    @PostMapping("create")
    public String saveCard(@RequestParam("file") MultipartFile multipartFile, Model model,
                           @RequestParam("bdtUsdConversionRate") Double bdtUsdConversionRate, HttpSession session) {
        if (multipartFile.isEmpty()) {
            model.addAttribute("validationError", "Attach a excel file");
            return "collection/distribution/card/create";
        }

        if (!multipartFile.getContentType().equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet") &&
                !multipartFile.getContentType().equals("application/vnd.ms-excel")) {
            model.addAttribute("validationError", "Only excel file format is supported");
            return "collection/distribution/card/create";
        }
        Map<String, String> errors = new LinkedHashMap<>();

        errors = saveDistribution(multipartFile);
        session.setAttribute("distributionErrors", errors);

        return "redirect:/distribution/card/list";
    }

    private Map<String,String> saveDistribution(MultipartFile multipartFile){

        Map<String,String> errors = new LinkedHashMap<>();

        List<CardAccountDistributionInfo> cardAccountDistributionInfos = new ArrayList<>();
        ExchangeRateEntity exchangeRateEntity = exchangeRateService.findExchaneRate();
        try {
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(multipartFile.getInputStream());
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
            for (int i = 1; i < xssfSheet.getPhysicalNumberOfRows(); i++) {
                XSSFRow row = xssfSheet.getRow(i);

                String contractId = row.getCell(0) != null ? row.getCell(0).toString() : null;
                String dealerPin = row.getCell(1) != null ? row.getCell(1).toString() : null;
                String dealerName = row.getCell(2) != null ? row.getCell(2).toString() : null;
                //String location = row.getCell(3) != null ? row.getCell(3).toString() : null;

                CardAccountDistributionInfo cardAccountDistributionInfo = new CardAccountDistributionInfo();
                CustomerBasicInfoEntity customerBasicInfoEntity = customerBasicInfoService.findFirstByContractIdAndCardType(contractId,"BASIC");
                CardAccountBasicInfo cardAccountBasicInfo = cardAccountBasicService.getByContractId(contractId);
                CardAccountInfo cardAccountInfo = cardAccountRepository.findFirstByContractNoAndCardType(contractId, "BASIC");

                List<CardAccountDistributionInfo>accountDistributionInfos = cardAccountDistributionRepository.findByClientIdAndLatest(contractId,"1");

                if(accountDistributionInfos.size()>0)
                    cardAccountDistributionInfo = accountDistributionInfos.get(0);

                if(!isExists(contractId,customerBasicInfoEntity)){
                    errors.put(contractId,"No Contract Id Found!");
                    continue;
                }
                if(cardAccountInfo != null)
                    cardAccountDistributionInfo.setAgeCode(cardAccountInfo.getAge());
                cardAccountDistributionInfo.setDealerName(dealerName);
                cardAccountDistributionInfo.setDealerPin(dealerPin);
                cardAccountDistributionInfo.setCardAccountBasicInfo(cardAccountBasicInfo);
                cardAccountDistributionInfo.setClientId(contractId);
                cardAccountDistributionInfo.setStatusDate(new Date());
                cardAccountDistributionInfo.setCreatedDate(new Date());

                cardAccountDistributionInfos.add(cardAccountDistributionInfo);
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        cardAccountDistributionRepository.saveAll(cardAccountDistributionInfos);

        return errors;
    }

    private boolean isBasic(String contractId, CustomerBasicInfoEntity customerBasicInfoEntity){
            if(!customerBasicInfoEntity.getCardType().equalsIgnoreCase("basic"))
                return false;
        return true;
    }

    private boolean isExists(String contractId, CustomerBasicInfoEntity customerBasicInfoEntity){
        if(customerBasicInfoEntity == null)
            return false;
        return true;
    }

    private void propagateDataToRelatedTable(List<CardAccountBasicInfo> basicInfoList,
                                             List<CardAccountDistributionInfo> distributionInfoList,
                                             List<CardAccountInfo> accountInfoList, Date startDate, Date endDate,
                                             List<CardAccountOtherInfo> accOtherInfoList) {

        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        for (int i = 0; i < basicInfoList.size(); i++) {

            CardAccountBasicInfo basicInfo = basicInfoList.get(i);

            CardAccountInfo cardAccountInfo = accountInfoList.get(i);
            cardAccountInfo.setCardAccountBasicInfo(basicInfo);
            cardAccountInfo.setEnabled(true);

            CardAccountOtherInfo cardAccountOtherInfo = accOtherInfoList.get(i);
            cardAccountOtherInfo.setCardAccountBasicInfo(basicInfo);
            cardAccountOtherInfo.setEnabled(true);

            CardAccountBasicInfo fetchFromDatabase = cardAccountBasicService.getByContractId(basicInfo.getContractId());

            if (fetchFromDatabase != null) {

                basicInfo.setId(fetchFromDatabase.getId());
                basicInfo.setModifiedBy(userPrincipal.getUsername());
                basicInfo.setModifiedDate(new Date());
                basicInfo.setCustomer(fetchFromDatabase.getCustomer());
                cardAccountBasicService.updateAgency(basicInfo);

                CardAccountDistributionInfo distributionInfoFromDb =
                        cardAccountDistributionRepository.findFirstByCreatedDateIsBetweenAndCardAccountBasicInfoAndLatestOrderByCreatedDateDesc(
                                startDate, endDate, basicInfo, "1");
                if (distributionInfoFromDb != null) {
                    distributionInfoFromDb.setLatest("0");
                    cardAccountDistributionRepository.save(distributionInfoFromDb);
                }

                CardAgencyDistributionInfo cardAgencyDistributionInfo = cardAgencyDistributionRepository.findFirstByCreatedDateGreaterThanEqualAndCreatedDateLessThanEqualAndCardAccountBasicInfoAndLatestOrderByCreatedDateDesc(startDate, endDate, basicInfo, "1");
                if (cardAgencyDistributionInfo != null) {
                    cardAgencyDistributionInfo.setLatest("0");
                    cardAgencyDistributionRepository.save(cardAgencyDistributionInfo);
                }

                CardAccountInfo byCardAccountInfo = cardAccountRepository.findFirstByCardAccountBasicInfo(basicInfo);
                cardAccountInfo.setId(byCardAccountInfo.getId());
                cardAccountInfo.setCreatedBy(byCardAccountInfo.getCreatedBy());
                cardAccountInfo.setCreatedDate(byCardAccountInfo.getCreatedDate());
                cardAccountInfo.setModifiedBy(userPrincipal.getUsername());
                cardAccountInfo.setModifiedDate(new Date());
                cardAccountService.updateAgency(cardAccountInfo);

                CardAccountOtherInfo byCardAccountOtherInfo = cardAccountOtherRepository.findFirstByCardAccountBasicInfo(basicInfo);
                if(byCardAccountOtherInfo == null){
                    byCardAccountOtherInfo = new CardAccountOtherInfo();
                }
                cardAccountOtherInfo.setId(byCardAccountOtherInfo.getId());
                cardAccountOtherInfo.setCreatedBy(byCardAccountOtherInfo.getCreatedBy());
                cardAccountOtherInfo.setCreatedDate(byCardAccountOtherInfo.getCreatedDate());
                cardAccountOtherInfo.setModifiedBy(userPrincipal.getUsername());
                cardAccountOtherInfo.setModifiedDate(new Date());
                cardAccountOtherService.updateAgency(cardAccountOtherInfo);


            } else {
                CustomerBasicInfoEntity customerBasicInfoEntity =
                        new CustomerBasicInfoEntity("Card", basicInfo.getContractId(), basicInfo.getCardName(),
                                basicInfo.getClientId(), new Date());


                customerBasicInfoEntity = customerBasicInfoEntityRepository.save(customerBasicInfoEntity);

                basicInfo.setCreatedBy(userPrincipal.getUsername());
                basicInfo.setEnabled(true);
                basicInfo.setCreatedDate(new Date());
                basicInfo.setCustomer(customerBasicInfoEntity);

                cardAccountBasicService.saveNew(basicInfo);

                cardAccountInfo.setCreatedBy(userPrincipal.getUsername());
                cardAccountInfo.setCreatedDate(new Date());
                cardAccountService.saveNew(cardAccountInfo);


                cardAccountOtherInfo.setCreatedBy(userPrincipal.getUsername());
                cardAccountOtherInfo.setCreatedDate(new Date());
                cardAccountOtherService.saveNew(cardAccountOtherInfo);
            }

            CardAccountDistributionInfo cardAccountDistributionInfo = distributionInfoList.get(i);
            cardAccountDistributionInfo.setCardAccountBasicInfo(basicInfo);
            cardAccountDistributionInfo.setCreatedBy(userPrincipal.getUsername());
            cardAccountDistributionInfo.setEnabled(true);
            cardAccountDistributionInfo.setCreatedDate(new Date());
            cardAccountDistributionInfo.setMonitoringStatus(LoanDualEnum.SINGLE.name());
            cardAccountDistributionInfo.setLatest("1");

            cardAccountDistributionService.saveNew(cardAccountDistributionInfo);
        }
    }

    private void getDataFromSheet(XSSFSheet xssfSheet, List<CardAccountBasicInfo> basicInfoList,
                                  List<CardAccountDistributionInfo> distributionInfoList,
                                  List<CardAccountInfo> accountInfoList, double bdtUsdConversionRate,
                                  List<CardAccountOtherInfo> accOtherInfoList) {
        for (int i = 1; i < xssfSheet.getPhysicalNumberOfRows(); i++) {
            XSSFRow row = xssfSheet.getRow(i);

            if (row.getCell(0) != null && row.getCell(0).toString().length() > 1) {

                String contractId = row.getCell(0) != null ? row.getCell(0).toString() : null;
                String dealerPin = row.getCell(1) != null ? row.getCell(1).toString() : null;
                String dealerName = row.getCell(2) != null ? row.getCell(2).toString() : null;
                String location = row.getCell(3) != null ? row.getCell(3).toString() : null;

                CustomerBasicInfoEntity customerBasicInfoEntity = customerBasicInfoEntityRepository.findByContractIdAndCardType(contractId, "BASIC");

                CardAccountBasicInfo basicInfo = new CardAccountBasicInfo(customerBasicInfoEntity.getClientId(), contractId, location);
                basicInfoList.add(basicInfo);

                CardAccountDistributionInfo distributionInfo =
                        new CardAccountDistributionInfo(dealerName, dealerPin, contractId, bdtUsdConversionRate);
                distributionInfoList.add(distributionInfo);

                CardAccountInfo cardAccountInfo = new CardAccountInfo();
                cardAccountInfo.setEnabled(true);
                cardAccountInfo.setCardAccountBasicInfo(basicInfo);
                accountInfoList.add(cardAccountInfo);

                CardAccountOtherInfo cardAccountOtherInfo = new CardAccountOtherInfo();
                accOtherInfoList.add(cardAccountOtherInfo);
            }
        }
    }

    private Date getEndDate(LocalDate today) {
        LocalDate endDateOfMonth = today.withDayOfMonth(today.lengthOfMonth());
        String endDateOfMonthString = endDateOfMonth.toString();
        endDateOfMonthString = endDateOfMonthString + " 11:59 PM";

        Date endDate = null;
        try {
            endDate = new SimpleDateFormat("yyyy-MM-dd hh:mm a").parse(endDateOfMonthString);
        } catch (ParseException e) {
            System.err.println(e.getMessage());
        }
        return endDate;
    }


    @GetMapping("list")
    public String list(Model model, HttpSession session) {

        LocalDate today = LocalDate.now();
        LocalDate startDateOfMonth = today.withDayOfMonth(1);
        Date startDate = Date.from(startDateOfMonth.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endDate = getEndDate(today);


        List<CardViewModel> cardViewModels = new ArrayList<>();

        List<CardAccountDistributionInfo> distributionInfoList = cardAccountDistributionRepository
                .findByCreatedDateIsBetweenAndSamAccountAndWriteOffAccountAndLatestOrderByCreatedDateDesc(
                        startDate, endDate, "0", "0", "1");

        distributionInfoList.forEach(cardAccountDistributionInfo -> {
            CardViewModel loanViewModelForBasicInfo =
                    getCardViewModelForBasicInfo(cardAccountDistributionInfo, new CardViewModel());
            cardViewModels.add(loanViewModelForBasicInfo);
        });

        List<EmployeeInfoEntity> dealterList = employeeService.getDealerList();
        List<EmployeeInfoEntity> superVisorList = employeeService.getSuperVisorList();
        List<EmployeeInfoEntity> teamLeaderList = employeeService.getTeamLeaderList();
        dealterList.addAll(superVisorList);
        dealterList.addAll(teamLeaderList);

        Gson gson = new Gson();
        model.addAttribute("cardlist", cardViewModels);
        model.addAttribute("cardviewlistJson", gson.toJson(cardViewModels));
        model.addAttribute("dealerList", dealterList);
        model.addAttribute("agencyList", agencyService.getAll());

        Map distributionErrors = (Map) session.getAttribute("distributionErrors");
        if (distributionErrors != null) {
            session.removeAttribute("distributionErrors");
            model.addAttribute("distributionErrors", distributionErrors);
        }

        return "collection/distribution/card/card";
    }

    @GetMapping("agency")
    public String getAgencyList(Model model) {

        LocalDate today = LocalDate.now();
        LocalDate startDateOfMonth = today.withDayOfMonth(1);
        Date startDate = Date.from(startDateOfMonth.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endDate = getEndDate(today);

        List<CardViewModel> cardViewModels = new ArrayList<>();

        List<CardAgencyDistributionInfo> cardAgencyDistributionInfos = cardAgencyDistributionRepository.findByCreatedDateGreaterThanEqualAndCreatedDateLessThanEqualAndLatest(startDate, endDate, "1");

        Date finalEndDate = endDate;
        cardAgencyDistributionInfos.forEach(cardAgencyDistributionInfo -> {
            CardViewModel cardViewModelFormLoanAgencyDistributionInfo = getCardViewModelFormCardAgencyDistributionInfo(new CardViewModel(), cardAgencyDistributionInfo);
            if (cardAgencyDistributionInfo.getLoanDualEnum().toString().equals("DUAL")) {
                CardAccountDistributionInfo dual = cardAccountDistributionRepository.findFirstByCreatedDateIsBetweenAndCardAccountBasicInfoAndLatestOrderByCreatedDateDesc(startDate, finalEndDate, cardAgencyDistributionInfo.getCardAccountBasicInfo(), "1");
                if (dual != null)
                    cardViewModelFormLoanAgencyDistributionInfo.setDelaerName(dual.getDealerName());
            }
            cardViewModels.add(cardViewModelFormLoanAgencyDistributionInfo);
        });

        Gson gson = new Gson();
        model.addAttribute("cardlist", cardViewModels);
        model.addAttribute("cardviewlistJson", gson.toJson(cardViewModels));
        model.addAttribute("dealerList", peopleAllocationLogicRepository.findByUnitAndDistributionType("Card", "Regular"));
        model.addAttribute("agencyList", agencyService.getAll());
        return "collection/distribution/card/agency";
    }

    @GetMapping("cardunallocatedlist")
    public String cardUnallocatedList(Model model) {

        LocalDate today = LocalDate.now();
        LocalDate startDateOfMonth = today.withDayOfMonth(1);
        Date startDate = Date.from(startDateOfMonth.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endDate = getEndDate(today);

        List<CardAccountDistributionInfo> byCreatedDateLessThan = cardAccountDistributionRepository.findByCreatedDateIsBetweenAndDealerPinAndSupervisorPin(
                startDate, endDate, "0", "0");
        List<CardViewModel> cardViewModels = new ArrayList<>();

        for (CardAccountDistributionInfo l : byCreatedDateLessThan) {
            CardViewModel cardViewModel = getCardViewModelForBasicInfo(l, new CardViewModel());
            cardViewModels.add(cardViewModel);
        }

        Gson gson = new Gson();
        model.addAttribute("cardlist", cardViewModels);
        model.addAttribute("cardviewlistJson", gson.toJson(cardViewModels));
        model.addAttribute("dealerList", employeeService.getDealerList());
        model.addAttribute("agencyList", agencyService.getAll());
        return "collection/distribution/card/cardunallocatedlist";
    }

    private CardViewModel getCardViewModelFormCardAgencyDistributionInfo(CardViewModel cardViewModel, CardAgencyDistributionInfo cardAgencyDistributionInfo) {
        cardViewModel.setCreatedDate(cardAgencyDistributionInfo.getCreatedDate());
        cardViewModel.setAccountNo(cardAgencyDistributionInfo.getCardAccountBasicInfo().getContractId());
        cardViewModel.setCustomerName(cardAgencyDistributionInfo.getCardAccountBasicInfo().getCardName());
        cardViewModel.setCustomerId(cardAgencyDistributionInfo.getCardAccountBasicInfo().getClientId());
        cardViewModel.setAgeCode(cardAgencyDistributionInfo.getAgeCode());
        cardViewModel.setAgencyName(cardAgencyDistributionInfo.getAgencyName());
        cardViewModel.setOutstandingAmount(cardAgencyDistributionInfo.getOutstanding());
        cardViewModel.setLocation(cardAgencyDistributionInfo.getCardAccountBasicInfo().getLocation());
        cardViewModel.setMonirotingStatus(cardAgencyDistributionInfo.getLoanDualEnum().name());
        return cardViewModel;
    }

    private CardViewModel getCardViewModelForBasicInfo(CardAccountDistributionInfo cardAccountDistributionInfo, CardViewModel cardViewModel) {
        CardAccountInfo cardAccountInfo = getCardAccountInfo(cardAccountDistributionInfo.getCardAccountBasicInfo());

        cardViewModel.setCreatedDate(cardAccountDistributionInfo.getCreatedDate());
        cardViewModel.setAccountNo(cardAccountDistributionInfo.getCardAccountBasicInfo().getContractId());
        cardViewModel.setCustomerId(cardAccountDistributionInfo.getCardAccountBasicInfo().getClientId());
        cardViewModel.setCustomerName(cardAccountDistributionInfo.getCardAccountBasicInfo().getCardName());
        cardViewModel.setAgeCode(cardAccountInfo.getMoAgeCode());
        cardViewModel.setLocation(cardAccountDistributionInfo.getCardAccountBasicInfo().getLocation());
        cardViewModel.setOutstandingAmount(cardAccountInfo.getMoAccOutstanding() + "");
        cardViewModel.setSupervisorName(cardAccountDistributionInfo.getSupervisorName());
        cardViewModel.setDelaerName(cardAccountDistributionInfo.getDealerName());
        cardViewModel.setMonirotingStatus(cardAccountDistributionInfo.getMonitoringStatus());
        cardViewModel.setDealerPin(cardAccountDistributionInfo.getDealerPin());

        return cardViewModel;
    }

    private CardAccountInfo getCardAccountInfo(CardAccountBasicInfo cardAccountBasicInfo) {
        List<CardAccountInfo> byCardAccountBasicId = cardAccountService.findByCardAccountBasicId(cardAccountBasicInfo);
        Collections.sort(byCardAccountBasicId, new Comparator<CardAccountInfo>() {
            @Override
            public int compare(CardAccountInfo o1, CardAccountInfo o2) {
                if (o1.getCreatedDate() == null || o2.getCreatedDate() == null)
                    return 0;
                return o1.getCreatedDate().compareTo(o2.getCreatedDate());
            }
        });
        int index = byCardAccountBasicId.size() - 1;
        return byCardAccountBasicId.get(index);
    }

    public List<CardViewModel> getCardViewModels() {

        LocalDate today = LocalDate.now();
        LocalDate startDateOfMonth = today.withDayOfMonth(1);
        Date startDate = Date.from(startDateOfMonth.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endDate = getEndDate(today);

        List<CardViewModel> loanViewModels = new ArrayList<>();

        List<CardAccountDistributionInfo> loanAccountDistributionRepositoryByCreatedDateIsBetween
                = cardAccountDistributionRepository.
                findByCreatedDateIsBetweenAndSamAccountAndWriteOffAccountAndLatestOrderByCreatedDateDesc(startDate, endDate, "0", "0", "1");

        loanAccountDistributionRepositoryByCreatedDateIsBetween.forEach(loanAccountDistributionInfo -> {
            CardViewModel loanViewModelForBasicInfo = getCardViewModelForBasicInfo(loanAccountDistributionInfo, new CardViewModel());
            loanViewModels.add(loanViewModelForBasicInfo);
        });
        return loanViewModels;

    }

}
