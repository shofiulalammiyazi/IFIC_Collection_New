package com.unisoft.detailsOfCollection.controller;


import com.unisoft.collection.reasonDelinquency.ReasonDelinquencyServiceImpl;
import com.unisoft.collection.settings.deferredAccount.DeferredAccount;
import com.unisoft.collection.settings.deferredAccount.DeferredAccountService;
import com.unisoft.collection.settings.employee.EmployeeInfoEntity;
import com.unisoft.collection.settings.employee.EmployeeService;
import com.unisoft.collection.settings.loansectorcode.SectorCodeEntity;
import com.unisoft.collection.settings.loansectorcode.SectorCodeService;
import com.unisoft.customerloanprofile.contactInfo.ContactInfoService;
import com.unisoft.detailsOfCollection.cardviewmodels.AccountInformation;
import com.unisoft.loanApi.model.LoanAccDetails;
import com.unisoft.loanApi.model.LoanAccInfo;
import com.unisoft.collection.datamigration.VechileRepository;
import com.unisoft.collection.distribution.accountReschedule.AccountRescheduleRepository;
import com.unisoft.collection.distribution.agencyAllocation.loan.LoanAgencyDistributionInfo;
import com.unisoft.collection.distribution.loan.LoanAccountSearchService;
import com.unisoft.collection.distribution.loan.LoanDistributionService;
import com.unisoft.collection.distribution.loan.loanAccountBasic.LoanAccountBasicInfo;
import com.unisoft.collection.distribution.loan.loanAccountBasic.LoanAccountBasicService;
import com.unisoft.collection.distribution.loan.loanAccountDistribution.LoanAccountDistributionInfo;
import com.unisoft.collection.distribution.loan.loanAccountDistribution.LoanAccountDistributionService;
import com.unisoft.collection.distribution.samAccountHandover.SamAccountHandoverInfo;
import com.unisoft.collection.distribution.samAccountHandover.SamAccountHandoverRepository;
import com.unisoft.collection.distribution.writeOff.WriteOffAccountInfo;
import com.unisoft.collection.distribution.writeOff.WriteOffAccountRepository;
import com.unisoft.collection.settings.PARaccountRuleLoan.PARaccountRuleLoanEntity;
import com.unisoft.collection.settings.PARaccountRuleLoan.PARaccountRuleLoanService;
import com.unisoft.collection.settings.nplAccountRule.NPLAccountRuleEntity;
import com.unisoft.collection.settings.nplAccountRule.NPLAccountRuleService;
import com.unisoft.collection.settings.vipStatus.VipStatusService;
import com.unisoft.collection.settings.visitLedger.VisitLedgerEntity;
import com.unisoft.collection.settings.visitLedger.VisitLedgerRepository;
import com.unisoft.customerbasicinfo.CustomerBasicInfoEntity;
import com.unisoft.customerbasicinfo.CustomerBasicInfoService;
import com.unisoft.customerloanprofile.dailynote.DailyNoteService;
import com.unisoft.customerloanprofile.diarynote.DairyNotesLoanService;
import com.unisoft.customerloanprofile.followup.FollowUpService;
import com.unisoft.customerloanprofile.guarantorinfo.GuarantorInfoService;
import com.unisoft.customerloanprofile.hotnote.HotNoteSerivce;
import com.unisoft.customerloanprofile.letterinformation.LetterInformationRepository;
import com.unisoft.customerloanprofile.loanpayment.LoanPaymentRepository;
import com.unisoft.loanApi.service.RetailLoanUcbApiService;
import com.unisoft.retail.card.dataEntry.distribution.accountBasicInfo.CardAccountBasicInfo;
import com.unisoft.retail.card.dataEntry.distribution.accountBasicInfo.CardAccountBasicService;
import com.unisoft.retail.loan.dataEntry.CustomerUpdate.accountInformation.AccountInformationEntity;
import com.unisoft.retail.loan.dataEntry.CustomerUpdate.accountInformationService.AccountInformationService;
import com.unisoft.retail.loan.dataEntry.ptp.LoanPtpRepository;
import com.unisoft.customerloanprofile.referenceinfo.ReferenceInfoService;
import com.unisoft.user.UserPrincipal;
import com.unisoft.utillity.DateUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import com.unisoft.utillity.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/profile")
@Slf4j
@RequiredArgsConstructor
public class ProfileLoanController {

    private final DailyNoteService dailyNoteService;

    private final DairyNotesLoanService dairyNotesLoanService;

    private final FollowUpService followUpService;

    private final LoanPtpRepository loanPtpRepository;

    private final ContactInfoService contactInfoService;

    private final ReasonDelinquencyServiceImpl reasonDelinquencyService;

    private final GuarantorInfoService guarantorInfoService;

    private final ReferenceInfoService referenceInfoService;

    private final LoanAccountBasicService loanAccountBasicService;

    private final CustomerBasicInfoService customerBasicInfoService;

    private final AccountRescheduleRepository accountRescheduleRepository;

    private final VisitLedgerRepository visitLedgerRepository;

    private final LetterInformationRepository letterInformationRepository;

    private final LoanDistributionService loanDistributionService;

    private final LoanPaymentRepository loanPaymentDetailsRepository;

    private final PARaccountRuleLoanService paRaccountRuleLoanService;

    private final NPLAccountRuleService nplAccountRuleService;

    private final HotNoteSerivce hotNoteSerivce;

    private final VechileRepository vechileRepository;

    private final LoanAccountSearchService loanAccountSearchService;

    private final WriteOffAccountRepository writeOffAccountRepository;

    private final SamAccountHandoverRepository samAccountHandoverRepository;

    private final RetailLoanUcbApiService retailLoanUcbApiService;

    private final DateUtils dateUtils;

    private final VipStatusService vipStatusService;

    private final LoanAccountDistributionService loanAccountDistributionService;

    private final HttpSession session;



    private final EmployeeService employeeService;

    private final CardAccountBasicService cardBasicService;


    @Autowired
    private SectorCodeService service;

    @Autowired
    private DeferredAccountService deferredAccountService;

    @Autowired
    private AccountInformationService accountInformationService;



    @GetMapping("/loan")
    public String profileLoanDetails(@RequestParam(value = "custid") Long custId,
                                     @RequestParam(value = "loanid") Long loanId, Model model) {

        model.addAttribute("branchMnemonic","");
        model.addAttribute("productCode","");
        model.addAttribute("dealReference","");

        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        EmployeeInfoEntity employeeInfoEntity = employeeService.getByPin(user.getUsername());




        CustomerBasicInfoEntity customerBasicInfoEntity = customerBasicInfoService.findById(custId);
        LoanAccountBasicInfo loanAccountBasicInfo = loanAccountBasicService.getById(loanId);

        model.addAttribute("loanConcatNumber",loanAccountBasicInfo.getAccountNo());


        Date startDate = dateUtils.getDateOfCurrentMonth(1);
        Date endDate = dateUtils.getLocalMonthEndDate();

        List<VisitLedgerEntity> visitLedgerEntities = visitLedgerRepository.findByAccountCardNumberOrderByIdDesc(loanAccountBasicInfo.getAccountNo());
        /*visitLedgerEntities.forEach(v -> {
            EmployeeInfoEntity employeeInfoEntity = employeeRepository.findByPin(v.getName());
            v.setName(employeeInfoEntity.getUser().getFirstName());
        });*/

        LoanAccountDistributionInfo distributionInfo = loanAccountSearchService.findLatestLoanAccountDistributionInfo(loanAccountBasicInfo);

        double dayDiff = 0;
        double parAmount = 0;
        double nplAmount = 0;

        Gson gson = new Gson();

        List<PARaccountRuleLoanEntity> paRaccountRuleLoanList = paRaccountRuleLoanService.getActiveList();
        List<NPLAccountRuleEntity> nplAccountRuleList = nplAccountRuleService.getActiveList();

        model.addAttribute("nplList", gson.toJson(nplAccountRuleList));
        model.addAttribute("parList", gson.toJson(paRaccountRuleLoanList));

        if (distributionInfo != null) {
            model.addAttribute("distributionInfo", distributionInfo);
            model.addAttribute("monitoringStatus", distributionInfo.getMonitoringStatus());
            if (distributionInfo.getMonitoringStatus().equalsIgnoreCase("DUAL")) {
                LoanAgencyDistributionInfo loanAgencyDistributionInfo =
                        loanAccountSearchService.findFromLoanAgencyDistributionInfo(loanAccountBasicInfo);
                model.addAttribute("distributionAgencyInfo", loanAgencyDistributionInfo);
            }

            boolean isWriteOffAccount = distributionInfo.getWriteOffAccount() != null;
            if (isWriteOffAccount) {
                WriteOffAccountInfo writeOffAccountInfo =
                        writeOffAccountRepository
                                .findFirstByCreatedDateIsBetweenAndLoanAccountBasicInfoOrderByCreatedDateDesc(
                                        startDate, endDate, loanAccountBasicInfo);
                model.addAttribute("writeOffDetails", writeOffAccountInfo);
            }
            model.addAttribute("writeOff", isWriteOffAccount);

            boolean isSamAccount = distributionInfo.getSamAccount() != null;
            if (isSamAccount) {
                SamAccountHandoverInfo samAccountHandoverInfo =
                        samAccountHandoverRepository
                                .findFirstByCreatedDateIsBetweenAndLoanAccountBasicInfoAndLatest(
                                        startDate, endDate, loanAccountBasicInfo, "1");
                model.addAttribute("samDetails", samAccountHandoverInfo);
            }
            model.addAttribute("sam", isSamAccount);

            double loanPaymentByAcc = loanPaymentDetailsRepository.findByPaymentDateBetweenAndAccountNo(distributionInfo.getStatusDate(), distributionInfo.getLoanAccountBasicInfo().getAccountNo());
            model.addAttribute("paymentAfterAllocationUpdated", loanPaymentByAcc);
            Duration day;

            LocalDate monthOpenDate = distributionInfo.getStatusDate()
                    .toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate monthEndDate = dateUtils.getMonthEndDate()
                    .toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            day = Duration.between(monthOpenDate.atStartOfDay(), monthEndDate.atStartOfDay());
            dayDiff = (double) day.toDays();

//            double totalDaysInMonth = dateManager.getTotalDaysInMonth();
            double MO_DPD = distributionInfo.getDpd();
            double monthEndDpd = MO_DPD + 30;
            double emiPerDay = distributionInfo.getEmiAmount() / 30;

            parAmount = emiPerDay * (monthEndDpd - 29.50);
            nplAmount = emiPerDay * (monthEndDpd - 89.50);
            nplAmount = nplAmount > 0 ? nplAmount : 0;

            model.addAttribute("parAmount", parAmount);
            model.addAttribute("nplAmount", nplAmount);
            model.addAttribute("dayDiff", dayDiff);
        } else {
            model.addAttribute("parAmount", parAmount);
            model.addAttribute("nplAmount", nplAmount);
            model.addAttribute("dayDiff", dayDiff);
        }

        Gson gson1 = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

//        Optional<DbLinkData> dblink = dbLinkDataRepository.findById(loanAccountBasicInfo.getAccountNo());
//        if (dblink.isPresent()) {
//            model.addAttribute("dblink", dblink.get());
//            List<AssetClassificationLoanEntity> assetClassificationLoanEntities = assetClassificationLoanRepository.findByCode(dblink.get().getScs());
//            if (assetClassificationLoanEntities.size() > 0)
//                model.addAttribute("assetclassification", assetClassificationLoanEntities.get(0));
//            else model.addAttribute("assetclassification", null);
//
//        } else {
//            model.addAttribute("dblink", null);
//            model.addAttribute("assetclassification", null);
//        }

//        model.addAttribute("mode", modeRepaymentFidRepository.findByAccountNo(loanAccountBasicInfo.getAccountNo()));
//        model.addAttribute("asset", gson.toJson(assetClassificationLoanRepository.findAll()));


        model.addAttribute("designation", employeeInfoEntity.getDesignation().getName().toLowerCase());
        model.addAttribute("distributionInfo1", distributionInfo);
        model.addAttribute("guarantorInfoList", guarantorInfoService.findByCustomerId(custId));
        model.addAttribute("referenceInfoList", referenceInfoService.findByCustomer(customerBasicInfoEntity));
        model.addAttribute("followUpList", followUpService.getByCustomerId(custId));

        // Hot note related attributes
        model.addAttribute("hotNotes", gson1.toJson(hotNoteSerivce.getHotNoteList(custId)));
        model.addAttribute("vipStatusList", vipStatusService.getActiveList());

        model.addAttribute("dairyNotes", gson1.toJson(dairyNotesLoanService.findByCustomerId(custId)));
//        model.addAttribute("dailyNotes", gson1.toJson(dailyNoteService.getDailyNoteAccList(custId)));
        model.addAttribute("ptps",
                gson1.toJson(loanPtpRepository.findByEnabledIsAndCustomerBasicInfo(true, loanAccountBasicInfo.getCustomer())));
        model.addAttribute("folllowups", gson1.toJson(followUpService.getByCustomerId(custId)));
        model.addAttribute("contacts", gson1.toJson(contactInfoService.getContactInfoList(custId)));
//        model.addAttribute("reasonDelis", gson1.toJson(reasonDelinquencyService.findReasonDelinquencyById(custId)));
        model.addAttribute("loanprofileview", customerBasicInfoEntity);
        model.addAttribute("dailynotelist", dailyNoteService.getDailyNoteList(customerBasicInfoEntity.getId()));
//        model.addAttribute("visitsLedger", gson1.toJson(visitLedgerEntities));
        model.addAttribute("visitsLedger", visitLedgerEntities);
        model.addAttribute("visitLedger", visitLedgerEntities);
        model.addAttribute("accountReschedule",
                accountRescheduleRepository.findFirstByAccountNoOrderByCreatedDateDesc(loanAccountBasicInfo.getAccountNo()));
        model.addAttribute("custid", custId);
        model.addAttribute("loanAccountBasicInfo", loanAccountBasicInfo);
        model.addAttribute("loanid", loanId);
        model.addAttribute("vehcile", vechileRepository.findByAccountNo(loanAccountBasicInfo.getAccountNo()));
        model.addAttribute("letterInformation",
                letterInformationRepository.findByAccountNo(loanAccountBasicInfo.getAccountNo()));
        SectorCodeEntity professionSegment = service.findSectorCodeEntityByAccountNo(customerBasicInfoEntity.getAccountNo());
        DeferredAccount deferredAccount = deferredAccountService.findDeferredAccount(customerBasicInfoEntity.getAccountNo());

        model.addAttribute("professionSegment",professionSegment);
        model.addAttribute("deferredAccount",deferredAccount);

//        Legal Attributes
//        List<CaseFiledType> caseFiledTypeList = caseFiledTypeService.findAll();
//        List<CaseType> caseTypeList = caseTypeService.findAllCaseType();
//        List<Lawyer> lawyerList = lawyerService.findByEnabled(true);
//        List<LitigationCaseInfo> litigationCaseInfoList = litigationCaseInfoService.getLitigationCaseInfoByCusAccNum(loanAccountBasicInfo.getAccountNo());

//        model.addAttribute("litigationCaseInfo", new LitigationCaseInfoDto());
//        model.addAttribute("caseFiledTypeList", caseFiledTypeList);

//        model.addAttribute("caseTypeList", caseTypeList);
//        model.addAttribute("lawyersList", lawyerList);
//        model.addAttribute("litigationCaseInfoList", litigationCaseInfoList);
//        model.addAttribute("litigationFileFollowUp", new LitigationFileFollowUp());

        String accountNo = loanAccountBasicInfo.getAccountNo();
//        Date lastMonthStartDate = dateManager.getMonthStartDate(-1);
//        Date lastMonthEndDate = dateManager.getMonthEndDate(-1);
//        Tuple tuple = loanPaymentDetailsRepository.getLoanPaymentSummaryByAccount(accountNo, lastMonthStartDate, lastMonthEndDate);
//        model.addAttribute("lastMonthPayment", tuple != null ? tuple.get("lastMonthPayment") : 0.00);
//        model.addAttribute("cumulativePayment", tuple != null ? tuple.get("cumulativePayment") : 0.00);

        model.addAttribute("dailyNotes", gson1.toJson(dailyNoteService.getDailyNoteAccList(accountNo)));
        model.addAttribute("reasonDelis", gson1.toJson(reasonDelinquencyService.findByAccountNo(accountNo)));


        Date currentMonthStartDate = dateUtils.getMonthStartDate();
        Date currentMonthEndDate = dateUtils.getMonthEndDate();


            model.addAttribute("dealerAllocationHistory",
                    loanAccountDistributionService.getLoanAccountDealerAllocationHistory(accountNo, currentMonthStartDate, currentMonthEndDate));

        return "collection/details/main";
    }

    @GetMapping("/loanprofile/search")
    public String getLoanProfile(@RequestParam(value = "account") String accountNo,
                                 Model model, RedirectAttributes redirectAttributes) {
        log.info("search loan account basic info for account");


        List<AccountInformationEntity> accountInformationEntityList = accountInformationService.findAccountInformationEntityByLoanAccountNo(accountNo);

        CustomerBasicInfoEntity customerBasicInfoEntity = loanDistributionService.updateCustomerBasiscInfo(accountInformationEntityList.get(0));
        LoanAccountBasicInfo loanAccountBasicInfo = loanDistributionService.updateLoanAccountBasicInfo(accountInformationEntityList.get(0),customerBasicInfoEntity);


       // LoanAccountBasicInfo loanAccountBasicInfo = loanAccountBasicService.getByAccountNo(accountNo);


        if (accountInformationEntityList.size() >1) {
            model.addAttribute("loanList", accountInformationEntityList);

            return "collection/search/loanList";
        }else {
             if (loanAccountBasicInfo == null || loanAccountBasicInfo.getId() == null) {
                 return returnToSearchPage(redirectAttributes);
             }
             else {
/*
                 return profileLoanDetails(loanAccountBasicInfo.getCustomer().getId(), loanAccountBasicInfo.getId(), model,accountInformationEntityList.get(0).getBranchMnemonic(),
                         accountInformationEntityList.get(0).getProductCode(),accountInformationEntityList.get(0).getDealReference());
*/
                 model.addAttribute("accountConcatNumber",loanAccountBasicInfo.getAccountNo());

                 return profileLoanDetails(loanAccountBasicInfo.getCustomer().getId(), loanAccountBasicInfo.getId(), model);

             }

        }



      /*  if (accountNo != null) {
            List<CardAccountBasicInfo> cardList = cardBasicService.findAllByClientId(accountNo);
            List<CardAccountBasicInfo> cardList2 = cardBasicService.findAllByContractId(accountNo);

            List<CustomerBasicInfoEntity> custListClientId = customerBasicInfoService.findAllByClientId(accountNo);
            List<CustomerBasicInfoEntity> custListContractId = customerBasicInfoService.findAllByContractId(accountNo);


            if (cardList.size() > 1) {
                model.addAttribute("cardList", cardList);
                return "collection/search/cardList";
            }
            if (cardList2.size() >1){
                model.addAttribute("cardList", cardList2);
                return "collection/search/cardList";
            }
            if (custListClientId.size() >1){
                model.addAttribute("cardList", custListClientId);
                return "collection/search/cardList";
            }
            if (custListContractId.size() >1){
                model.addAttribute("cardList", custListContractId);
                return "collection/search/cardList";
            }

            if (cardList.size() ==1) {
                return "redirect:/collection/card/searchclientid?clientId=" + accountNo;
            }
            if (cardList2.size() ==1){
                return "redirect:/collection/card/search?contractNo=" + accountNo;
            }

            if (custListClientId.size() ==1) {
                return "redirect:/collection/card/searchclientid?clientId=" + accountNo;
            }
            if (custListContractId.size() ==1){
                return "redirect:/collection/card/search?contractNo=" + accountNo;
            }

        }
*/

    }

    /*@GetMapping("/searchList")
    public String getTest(@RequestParam(value = "account") String accountNo,
                          Model model, RedirectAttributes redirectAttributes, @RequestParam(value = "branchMnemonic") String branchMnemonic,
                          @RequestParam(value = "productCode") String productCode,@RequestParam(value = "dealReference") String dealReference){

        LoanAccountBasicInfo loanAccountBasicInfo = loanAccountBasicService.getByAccountNo(accountNo);

        model.addAttribute("branchMnemonic",branchMnemonic);
        model.addAttribute("productCode",productCode);
        model.addAttribute("dealReference",dealReference);

        if (loanAccountBasicInfo == null || loanAccountBasicInfo.getId() == null)
            return returnToSearchPage(redirectAttributes);
//            return createLoanAccountAndReturnToLoan360(accountNo, redirectAttributes);
        else
            return profileLoanDetails(loanAccountBasicInfo.getCustomer().getId(), loanAccountBasicInfo.getId(), model, branchMnemonic,productCode,dealReference);

    }*/

    @GetMapping("/searchList")
    public String getTest(@RequestParam(value = "account") String accountNo,
                          Model model, RedirectAttributes redirectAttributes){

        LoanAccountBasicInfo loanAccountBasicInfo = loanAccountBasicService.getByAccountNo(accountNo);

        /*model.addAttribute("branchMnemonic",branchMnemonic);
        model.addAttribute("productCode",productCode);
        model.addAttribute("dealReference",dealReference);
*/


        model.addAttribute("accountConcatNumber",loanAccountBasicInfo.getAccountNo());


        if (loanAccountBasicInfo == null || loanAccountBasicInfo.getId() == null)
            return returnToSearchPage(redirectAttributes);
//            return createLoanAccountAndReturnToLoan360(accountNo, redirectAttributes);
        else
            return profileLoanDetails(loanAccountBasicInfo.getCustomer().getId(), loanAccountBasicInfo.getId(), model);

    }

    @GetMapping("/loanprofile")
    public String getLoanProfile() {
        return "collection/search/customer_search";
    }


    private String createLoanAccountAndReturnToLoan360(String accountNumber, RedirectAttributes redirectAttributes) {
        if (!StringUtils.hasText(accountNumber) || accountNumber.length() < 16)
            return returnToSearchPage(redirectAttributes);
        CustomerBasicInfoEntity customerBasicInfo;
        LoanAccountBasicInfo loanAccountBasicInfo;
        log.info("search loan api for account");
        LoanAccInfo loanAccInfo = searchLoanApi(accountNumber);
        if (loanAccInfo == null || !StringUtils.hasText(loanAccInfo.getCustomerId())) {
            return returnToSearchPage(redirectAttributes);
        } else {
            customerBasicInfo = customerBasicInfoService.findOrSave(new CustomerBasicInfoEntity(accountNumber));
            loanAccountBasicInfo = loanAccountBasicService.findOrSave(new LoanAccountBasicInfo(customerBasicInfo));
            log.info("save account data to cms");
            asyncUpdateCustomerInfo(customerBasicInfo, loanAccountBasicInfo, loanAccInfo);
        }
        return "redirect:/profile/loan?custid=" + customerBasicInfo.getId() + "&loanid=" + loanAccountBasicInfo.getId();
    }

    private String returnToSearchPage(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("accountNotFound", true);
        return "redirect:" +
                "/profile/loanprofile";
    }

    private LoanAccInfo searchLoanApi(String accountNumber) {
        return retailLoanUcbApiService.getLoanAccountInfo(accountNumber);
    }

    private void asyncUpdateCustomerInfo(CustomerBasicInfoEntity customerBasicInfo,
                                         LoanAccountBasicInfo loanAccountBasicInfo, LoanAccInfo loanAccInfo) {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = user.getUsername();
        new Thread(new Runnable() {
            @Override
            public void run() {
                LoanAccDetails loanAccDetails = retailLoanUcbApiService.getLoanAccountDetails(loanAccInfo.getLoanAccountNo());
                loanDistributionService.updateCustomerInfo(customerBasicInfo, loanAccInfo, loanAccDetails, username);
                loanDistributionService.updateLoanAccountBasicInfo(loanAccountBasicInfo, loanAccInfo, loanAccDetails, username);
            }
        }).start();
    }

}
