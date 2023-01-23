package com.unisoft.home;

import com.unisoft.collection.KPI.Card.TargetByAmount.CardKPITargetByAmountService;
import com.unisoft.collection.accountescalation.AccountEscalation;
import com.unisoft.collection.accountescalation.AccountEscalationService;
import com.unisoft.collection.allocationLogic.PeopleAllocationLogicInfo;
import com.unisoft.collection.allocationLogic.PeopleAllocationLogicRepository;
import com.unisoft.collection.allocationLogic.PeopleAllocationLogicService;
import com.unisoft.collection.customerComplain.CustomerComplainDto;
import com.unisoft.collection.customerComplain.CustomerComplainService;
import com.unisoft.collection.customerComplain.CustomerComplainViewModel;
import com.unisoft.collection.dashboard.*;
import com.unisoft.collection.distribution.loan.loanAccountDistribution.LoanAccountDistributionInfo;
import com.unisoft.collection.settings.PARaccountRuleLoan.PARaccountRuleLoanService;
import com.unisoft.collection.settings.ageCode.AgeCodeService;
import com.unisoft.collection.settings.agency.AgencyEntity;
import com.unisoft.collection.settings.agency.AgencyService;
import com.unisoft.collection.settings.customerRequest.CustomerRequestsEntity;
import com.unisoft.collection.settings.customerRequest.CustomerRequestsService;
import com.unisoft.collection.settings.designation.DesignationEntity;
import com.unisoft.collection.settings.designation.DesignationService;
import com.unisoft.collection.settings.designation.DesignationViewModel;
import com.unisoft.collection.settings.dpdBucket.DPDBucketService;
import com.unisoft.collection.settings.employee.EmployeeInfoEntity;
import com.unisoft.collection.settings.employee.EmployeeService;
import com.unisoft.collection.settings.employeeStatusManagement.EmployeeStatusManagerDao;
import com.unisoft.collection.settings.employeeStatusManagement.EmployeeStatusManagerEntity;
import com.unisoft.collection.settings.esau.loan.ESAULoanEntity;
import com.unisoft.collection.settings.esau.loan.ESAULoanService;
import com.unisoft.collection.settings.lateReason.LateReasonEntity;
import com.unisoft.collection.settings.lateReason.LateReasonService;
import com.unisoft.collection.settings.lateReasonExplain.LateReasonExplainInfo;
import com.unisoft.collection.settings.lateReasonExplain.LateReasonExplainRepository;
import com.unisoft.collection.settings.nplAccountRule.NPLAccountRuleService;
import com.unisoft.collection.settings.officeTimeSetup.OfficeTimeSetupInfo;
import com.unisoft.collection.settings.officeTimeSetup.OfficeTimeSetupService;
import com.unisoft.collection.settings.productType.ProductTypeService;
import com.unisoft.collection.settings.visitLedger.VisitLedgerDao;
import com.unisoft.collection.settings.visitLedger.VisitLedgerEntity;
import com.unisoft.collection.settings.visitLedger.VisitLedgerService;
import com.unisoft.collection.settingshelper.SettingsHelper;
import com.unisoft.customerloanprofile.AdditionalInfo.AdditionalInfo;
import com.unisoft.customerloanprofile.AdditionalInfo.AdditionalInfoService;
import com.unisoft.customerloanprofile.followup.FollowUpService;
import com.unisoft.customerloanprofile.guarantorinfo.GuarantorInfoEntity;
import com.unisoft.customerloanprofile.guarantorinfo.GuarantorInfoService;
import com.unisoft.customerloanprofile.loanpayment.LoanPaymentRepository;
import com.unisoft.customerloanprofile.referenceinfo.ReferenceInfoEntity;
import com.unisoft.customerloanprofile.referenceinfo.ReferenceInfoService;
import com.unisoft.loanApi.model.CustomerAndBorrowerInfo;
import com.unisoft.loanApi.service.CustomerOtherInfoService;
import com.unisoft.retail.card.dataEntry.distribution.accountDistributionInfo.CardAccountDistributionInfo;
import com.unisoft.retail.loan.dashboard.esau.LoanPerformanceAndEsauTrendService;
import com.unisoft.user.User;
import com.unisoft.user.UserPrincipal;
import com.unisoft.user.UserService;
import com.unisoft.utillity.DateUtils;
import com.unisoft.utillity.HttpSessionUtils;
import com.unisoft.utillity.LmsCalculator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


@Controller
@RequiredArgsConstructor
public class HomeController {
    @Autowired
    private VisitLedgerService visitLedgerService;

    @Autowired
    private AccountEscalationService escalationService;
    private final LateReasonService lateReasonService;

    private final EmployeeService employeeService;

    private final PeopleAllocationLogicRepository peopleAllocationLogicRepository;

    private final DealerPerformanceDataService dealerPerformanceService;

    private final DPDBucketService dpdBucketService;

    private final ProductTypeService productTypeService;

    private final AgeCodeService ageCodeService;

    private final AgencyService agencyService;

    private final LateReasonExplainRepository lateReasonRepository;

    private final LoanPaymentRepository loanPaymentDetailsRepository;

    private final EmployeeStatusManagerDao employeeStatusManagerDao;

    private final DashboardService dashboardService;

    private final PARaccountRuleLoanService paRaccountRuleLoanService;

    private final NPLAccountRuleService nplAccountRuleService;

    private final CardKPITargetByAmountService cardKPITargetByAmountService;

    private final CardBackendAccDetailService cardBackendAccDetailService;

    private final OfficeTimeSetupService officeTimeSetupService;

    private final CardKpiAchDao cardKpiAchDao;

    private final FollowUpService followUpService;

    private final VisitLedgerDao visitLedgerDao;

    private final DateUtils dateUtils;

    private final LmsCalculator lmsCalculator;

    private final HttpSessionUtils httpSessionUtils;

    private final ReferenceInfoService referenceInfoService;

    private final GuarantorInfoService guarantorInfoService;

    private final CustomerOtherInfoService customerOtherInfoService;

    private final CustomerComplainService customerComplainService;

    private final CustomerRequestsService customerRequestsService;

    private final AdditionalInfoService additionalInfoService;

    private final UserService userService;
    @Autowired
    private LoanPerformanceAndEsauTrendService loanPerformanceAndEsauTrendService;
    @Autowired
    private ESAULoanService esauLoanService;

    @Autowired
    private PeopleAllocationLogicService peopleAllocationLogicService;

    @Autowired
    private DesignationService designationService;

    @GetMapping("/")
    public String showLandingPage(Principal principal, HttpSession session, Model model,
                                  @RequestParam(value = "viewCardDash", required = false) boolean viewCardDash,
                                  HttpServletResponse response) throws Exception {

        Date startDate = dateUtils.getMonthStartDate();
        Date endDate = dateUtils.getMonthEndDate();

        if (principal != null) {

            UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = userService.getUserByUsername(userPrincipal.getUsername());

            if(user.getIsAgency()){
                AgencyEntity agencyEntity = agencyService.getUserByPin(user.getUsername());
                PeopleAllocationLogicInfo peopleAllocationLogicInfo = peopleAllocationLogicRepository.findPeopleAllocationLogicInfoByAgency(agencyEntity);
                OfficeTimeSetupInfo timeSetupInfo = officeTimeSetupService.getOfficeTimeSetup();
                String logoutTimeString = LocalDate.now().toString() + " " + timeSetupInfo.getLogoutHour().toString();
                Date logoutTime = dateUtils.getFormattedDate(logoutTimeString, "yyyy-MM-dd hh:mm");
                model.addAttribute("logoutTime", logoutTime);
                model.addAttribute("loginTime", new Date());
                model.addAttribute("viewCardDash", viewCardDash);
                model.addAttribute("agencyEntity", agencyEntity);
                model.addAttribute("peopleAllocationLogicInfo", peopleAllocationLogicInfo);
                session.setAttribute("loginTime", new Date());

                return "agency/dashboard/main";
            }

            EmployeeInfoEntity employeeInfoEntity = employeeService.getByPin(userPrincipal.getEmpId());

            OfficeTimeSetupInfo timeSetupInfo = officeTimeSetupService.getOfficeTimeSetup();
            String logoutTimeString = LocalDate.now().toString() + " " + timeSetupInfo.getLogoutHour().toString();
            Date logoutTime = dateUtils.getFormattedDate(logoutTimeString, "yyyy-MM-dd hh:mm");

            model.addAttribute("logoutTime", logoutTime);
            model.addAttribute("viewCardDash", viewCardDash);

            model.addAttribute("employee", employeeInfoEntity);
            model.addAttribute("loginTime", new Date());


            List<LateReasonExplainInfo> lateReasonExplainInfos = lateReasonRepository.findByCreatedDateIsBetweenAndUserOrderByCreatedDateDesc(startDate, endDate, employeeInfoEntity.getUser());
            model.addAttribute("lateReason", lateReasonExplainInfos);

            httpSessionUtils.setEmployeeSessionAttributes(session, employeeInfoEntity, lateReasonExplainInfos);

            String designation = employeeInfoEntity.getDesignation().getName().toLowerCase();
            String unit = employeeInfoEntity.getUnit().toLowerCase();

            if ("Admin".equalsIgnoreCase(designation)) {
                return "card/contents/home/card";
            } else if (unit.toLowerCase().contains("sam")) {
                try {
                    response.sendRedirect("/collection/samd/dashboard");
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
            }

            if (unit.contains("loan") && !unit.contains("card")) {

                return loanDashboard(model, employeeInfoEntity, session, userPrincipal);

            } else if ((unit.contains("card") && !unit.contains("loan")) || viewCardDash) {

                return cardDashboard(model, employeeInfoEntity, session, userPrincipal);

            } else if (unit.contains("card") && unit.contains("loan")) {

                // both loan and card. default loan unit.
                return defaultUnit(model, employeeInfoEntity, session);

            } else if (unit.contains("legal")) {

                return "legal/home/main";

            } else if (unit.contains("sam")) {

                //                return "/dashboard/sam/sam";

            } else {

                //Except card and loan
                return "card/contents/home/card";

            }
        }

        addLateReasonInfo(model);

        return "common/login/login";
    }

    private void addLateReasonInfo(Model model) throws ParseException {
        OfficeTimeSetupInfo timeSetupInfo = officeTimeSetupService.getOfficeTimeSetup();
        String format = LocalDate.now().toString() + " " + timeSetupInfo.getOfficeHour().toString();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        Date lateTime = simpleDateFormat.parse(format);
        Date currentTime = new Date();
        boolean late = currentTime.after(lateTime);

        List<LateReasonEntity> lateReasonServiceAll = lateReasonService.getAll();
        if (late) {

            List<LateReasonEntity> nonRegular = new ArrayList<>();
            nonRegular.add(new LateReasonEntity(""));

            for (LateReasonEntity l : lateReasonServiceAll)
                if (!l.getReasonTitle().equals("Regular")) nonRegular.add(l);
            model.addAttribute("latereasonList", nonRegular);
        } else {
            model.addAttribute("latereasonList", lateReasonServiceAll
                    .parallelStream()
                    .filter(lateReasonEntity ->
                            lateReasonEntity.getReasonTitle().equals("Regular")
                    ).collect(Collectors.toList()));
        }

    }

    private String defaultUnit(Model model, EmployeeInfoEntity employee, HttpSession session) {

        //Both Card and Loan and others. Default Loan
        String designation = employee.getDesignation().getName().toLowerCase();

        List<PeopleAllocationLogicInfo> allocationLogicList =
                dashboardService.getAllDealerList(employee.getId().toString(), designation, "Loan");
        if ("team leader".equals(designation)) {

            List<LoanAccountDistributionInfo> loanDist = getLoanDistributionByEmp(employee);

            loanDist.forEach(lmsCalculator::calculateSaveAndBackAmountForLoanDistribution);

            //dealer detail
            List<EmployeeInfoEntity> dealerList = getDealerList(allocationLogicList);
            List<TeamDetailModel> dealerDetail = getDealerDetailListLoan(dealerList, loanDist);
            int delarDetailCount=dealerDetail.size();
            double dealerPerformanceSum=0;
            String teamLeadLastMonthRating=null;
            if(delarDetailCount>0){
                for(TeamDetailModel teamDetailModel:dealerDetail){
                    if(teamDetailModel.getPerformance()>0){
                        dealerPerformanceSum+=teamDetailModel.getPerformance();
                    }
                }
            }
            List<ESAULoanEntity>esau=esauLoanService.getAll();
            for(ESAULoanEntity esauLoanEntity:esau){
                if(esauLoanEntity.getFinalAvgLowerLimit()<=dealerPerformanceSum/delarDetailCount && esauLoanEntity.getFinalAvgUpperLimit()>=dealerPerformanceSum/delarDetailCount ){
                    teamLeadLastMonthRating=esauLoanEntity.getRatingName();
                }
            }
            model.addAttribute("dealerDetail", dealerDetail);
            model.addAttribute("teamLeadAchivment", dealerPerformanceSum/delarDetailCount);
            model.addAttribute("teamLeadLastMonthRating", teamLeadLastMonthRating);


            //user total
            long userTotalAcc = loanDist.size();
            Double userTotalOutStanding = loanDist.stream().mapToDouble(LoanAccountDistributionInfo::getNumericOutStanding).sum();
            for (TeamDetailModel detailModel : dealerDetail) {
                userTotalAcc = userTotalAcc + detailModel.getNoOfAcc();
                userTotalOutStanding = userTotalOutStanding + detailModel.getOutstanding();
            }
            model.addAttribute("userTotalAcc", userTotalAcc);
            model.addAttribute("userTotalOutStanding", userTotalOutStanding);

            session.setAttribute("loanDistributionList", loanDist);
            model.addAttribute("teamDetailList", getLoanTeamDetail(loanDist, employee));

            Set<String> activeList = loanDist.stream().map(LoanAccountDistributionInfo::getDpdBucket).collect(Collectors.toSet());
            model.addAttribute("monitoringBuckets", activeList);


            model.addAttribute("peopleAllocationLogic",
                    peopleAllocationLogicRepository.findFirstByDealerAndUnitOrderByCreatedDateDesc(employee, "Loan"));
//                    model.addAttribute("teamleadloan", peopleAllocationLogicRepository.findFirstByTeamlead(employeeInfoEntity));
//                    model.addAttribute("rfd",dnoteMenuService.getAllActive());
//                    model.addAttribute("subrfd",dnSubMenu1Service.getActiveList());
//                    model.addAttribute("agencyloan",agencyService.getActiveList());
            return "dashboard/loan/teamleader/main";
        }

        if ("manager".equals(designation)) {

            List<LoanAccountDistributionInfo> loanDist = getLoanDistributionByEmp(employee);

            Set<String> activeList = loanDist.stream().map(LoanAccountDistributionInfo::getDpdBucket).collect(Collectors.toSet());
            model.addAttribute("monitoringBuckets", activeList);

            loanDist.forEach(lmsCalculator::calculateSaveAndBackAmountForLoanDistribution);

            //supervisor detail
            List<EmployeeInfoEntity> supervisorList = getSuperVisorList(allocationLogicList);
            List<TeamDetailModel> supervisorDetail = new ArrayList<>();

            int superVisorcount=supervisorDetail.size();
            double superVisorcountPerformanceSum=0;
            String srMangerLastMonthRating="";
            if(superVisorcount>0){
                for(TeamDetailModel superVisorDetail:supervisorDetail){
                    if(superVisorDetail.getPerformance()>0.0){
                        superVisorcountPerformanceSum+=superVisorDetail.getPerformance();
                    }
                }
            }
            List<ESAULoanEntity>esau=esauLoanService.getAll();
            for(ESAULoanEntity esauLoanEntity:esau){
                if(esauLoanEntity.getFinalAvgLowerLimit()<=superVisorcountPerformanceSum/superVisorcount && esauLoanEntity.getFinalAvgUpperLimit()>=superVisorcountPerformanceSum/superVisorcount){
                    srMangerLastMonthRating=esauLoanEntity.getRatingName();
                }else srMangerLastMonthRating="N/A";
            }
            for (EmployeeInfoEntity supervisor : supervisorList) {
                List<EmployeeInfoEntity> dealerForSupervisor = new ArrayList<>();
                for (PeopleAllocationLogicInfo allocationLogic : allocationLogicList) {
                    if (allocationLogic.getSupervisor().getPin().equals(supervisor.getPin())) {
                        EmployeeInfoEntity dealer = allocationLogic.getDealer();
                        dealerForSupervisor.add(dealer);
                    }
                }
                supervisorDetail.addAll(getTeamLeadOrSuperVisorDetailListLoan(dealerForSupervisor, loanDist, supervisor));
            }
            model.addAttribute("srMangerLastMonthRating", srMangerLastMonthRating);
            model.addAttribute("srMangerPerforMance", superVisorcountPerformanceSum/superVisorcount);
            model.addAttribute("supervisorDetail", supervisorDetail);


            //team lead detail
            List<TeamDetailModel> teamLeadDetail = new ArrayList<>();
            List<EmployeeInfoEntity> teamLeadList = getTeamLeadList(allocationLogicList);

            for (EmployeeInfoEntity teamLead : teamLeadList) {
                List<EmployeeInfoEntity> dealerForTeamLead = new ArrayList<>();
                for (PeopleAllocationLogicInfo allocationLogic : allocationLogicList) {
                    if (allocationLogic.getTeamlead().getPin().equals(teamLead.getPin())) {
                        EmployeeInfoEntity dealer = allocationLogic.getDealer();
                        dealerForTeamLead.add(dealer);
                    }
                }
                teamLeadDetail.addAll(getTeamLeadOrSuperVisorDetailListLoan(dealerForTeamLead, loanDist, teamLead));
            }
            model.addAttribute("teamLeadDetail", teamLeadDetail);

            //dealer detail
            List<EmployeeInfoEntity> dealerList = getDealerList(allocationLogicList);
            List<TeamDetailModel> dealerDetail = getDealerDetailListLoan(dealerList, loanDist);
            model.addAttribute("dealerDetail", dealerDetail);


            //user total
            long userTotalAcc = loanDist.size();
            Double userTotalOutStanding = loanDist.stream().mapToDouble(LoanAccountDistributionInfo::getNumericOutStanding).sum();
            for (TeamDetailModel detailModel : dealerDetail) {
                userTotalAcc = userTotalAcc + detailModel.getNoOfAcc();
                userTotalOutStanding = userTotalOutStanding + detailModel.getOutstanding();
            }
            model.addAttribute("userTotalAcc", userTotalAcc);
            model.addAttribute("userTotalOutStanding", userTotalOutStanding);


            session.setAttribute("loanDistributionList", loanDist);

            model.addAttribute("managerloan", peopleAllocationLogicRepository.findFirstByManager(employee));
            model.addAttribute("peopleAllocationLogic",
                    peopleAllocationLogicRepository.findFirstByDealerAndUnitOrderByCreatedDateDesc(employee, "Loan"));
            return "dashboard/loan/manager/main";
        }

        if ("supervisor".equals(designation)) {

            List<LoanAccountDistributionInfo> loanDist = getLoanDistributionByEmp(employee);

            List<EmployeeInfoEntity> teamLeadList = getTeamLeadList(allocationLogicList);
            List<TeamDetailModel> teamLeadDetail = getLoanTeamDetail(allocationLogicList, teamLeadList, loanDist);

            int superVisorcount=teamLeadDetail.size();
            double superVisorcountPerformanceSum=0;
            String superVisorcountLastMonthRating="";
            if(superVisorcount>0){
                for(TeamDetailModel superVisorDetail:teamLeadDetail){
                    if(superVisorDetail.getPerformance()>0.0){
                        superVisorcountPerformanceSum+=superVisorDetail.getPerformance();
                    }
                }
            }
            List<ESAULoanEntity>esau=esauLoanService.getAll();
            for(ESAULoanEntity esauLoanEntity:esau){
                if(esauLoanEntity.getFinalAvgLowerLimit()<=superVisorcountPerformanceSum/superVisorcount && esauLoanEntity.getFinalAvgUpperLimit()>=superVisorcountPerformanceSum/superVisorcount){
                    superVisorcountLastMonthRating=esauLoanEntity.getRatingName();
                }else superVisorcountLastMonthRating="N/A";
            }

            model.addAttribute("teamLeadDetail", teamLeadDetail);
            model.addAttribute("superVisorAchivment", superVisorcountPerformanceSum/superVisorcount);
            model.addAttribute("superVisorLastMonthRating", superVisorcountLastMonthRating);

            //dealer detail
            List<EmployeeInfoEntity> dealerList = getDealerList(allocationLogicList);
            List<TeamDetailModel> dealerDetail = getDealerDetailListLoan(dealerList, loanDist);
            model.addAttribute("dealerDetail", dealerDetail);


            //user total
            long userTotalAcc = loanDist.size();
            Double userTotalOutStanding = loanDist.stream().mapToDouble(LoanAccountDistributionInfo::getNumericOutStanding).sum();
            for (TeamDetailModel detailModel : dealerDetail) {
                userTotalAcc = userTotalAcc + detailModel.getNoOfAcc();
                userTotalOutStanding = userTotalOutStanding + detailModel.getOutstanding();
            }
            model.addAttribute("userTotalAcc", userTotalAcc);
            model.addAttribute("userTotalOutStanding", userTotalOutStanding);


            session.setAttribute("loanDistributionList", loanDist);


            model.addAttribute("peopleAllocationLogic",
                    peopleAllocationLogicRepository.findFirstByDealerAndUnitOrderByCreatedDateDesc(employee, "Loan"));
            return "dashboard/loan/supervisor/main";
        }

        if ("dealer".equals(designation)) {

            // List<PeopleAllocationLogicInfo> allocationList=new ArrayList<>();

            List<LoanAccountDistributionInfo> loanDist = getLoanDistributionByEmp(employee);

//                    Set<String> activeList = loanDist.stream().map(LoanAccountDistributionInfo::getDpdBucket).collect(toSet().toList());


            loanDist.forEach(lmsCalculator::calculateSaveAndBackAmountForLoanDistribution);

            model.addAttribute("accountList", loanDist);
            session.setAttribute("loanDistributionList", loanDist);
            //loanDistributionListTransporter.setLoanDistList(loanDist);

            //model.addAttribute("agencyloan", agencyService.getActiveList());
            //model.addAttribute("productList", productTypeService.getAllActive());
            //model.addAttribute("dpdBucket", activeList);
            model.addAttribute("peopleAllocationLogic",
                    peopleAllocationLogicRepository.findFirstByDealerAndUnitOrderByCreatedDateDesc(employee, "Loan"));
            //model.addAttribute("accountList", loanAccountDistributionRepository.findByCreatedDateIsBetweenAndDealerPin(startDate,endDate,employeeInfoEntity.getPin()));

            return "dashboard/loan/dealer/main";
        }
        return "common/login/login";

    }

    private String cardDashboard(Model model, EmployeeInfoEntity employee, HttpSession session, UserPrincipal principal) {

        String designation = employee.getDesignation().getName().toLowerCase();
        //Only for Card
        List<PeopleAllocationLogicInfo> allocationLogicList =
                dashboardService.getAllDealerList(employee.getId().toString(), designation, "Card");
        model.addAttribute("currentUser",employee);
        // Team Leader Card
        if ("team leader".equalsIgnoreCase(designation)) {

            model.addAttribute("productList", productTypeService.getAllActive());
            List<CardAccountDistributionInfo> cardDist = getCardDistributionByEmp(employee);


            // Reference info entity
            List<ReferenceInfoEntity> referenceInfoEntities = new ArrayList<>();
            List<GuarantorInfoEntity> guarantorInfoEntities = new ArrayList<>();
            List<CustomerAndBorrowerInfo> customerAndBorrowerInfos = new ArrayList<>();
            List<CustomerComplainDto> customerComplainEntities = new ArrayList<>();
            List<CustomerRequestsEntity> customerRequestsEntities = new ArrayList<>();
            List<AdditionalInfo> additionalInfos = new ArrayList<>();

            for (PeopleAllocationLogicInfo peopleAllocationLogicInfo : allocationLogicList) {

                List<ReferenceInfoEntity> referenceInfoEntityList =
                        referenceInfoService.findReferenceInfoEntityByDealerPinBndStatus(
                                peopleAllocationLogicInfo.getDealer().getPin(), "PENDING");

                List<GuarantorInfoEntity> guarantorInfoEntityList =
                        guarantorInfoService.findGuarantorInfoEntityByDealerPinBndStatus(
                                peopleAllocationLogicInfo.getDealer().getPin(), "PENDING");

                List<CustomerAndBorrowerInfo> customerAndBorrowerInfoList =
                        customerOtherInfoService.findCustomerAndBorrowerInfoByDealerPinBndStatus(
                                peopleAllocationLogicInfo.getDealer().getPin(), "PENDING");

                List<CustomerComplainDto> customerComplainEntityList =
                        customerComplainService.findCustomerComplainEntityByDealerPinBndStatus(
                                peopleAllocationLogicInfo.getDealer().getPin(), "PENDING");

                List<CustomerRequestsEntity> customerRequestsEntityList =
                        customerRequestsService.findCustomerRequestsEntityByDealerPinBndStatus(
                                peopleAllocationLogicInfo.getDealer().getPin(), "PENDING");

                List<AdditionalInfo> additionalInfoList =
                        additionalInfoService.findAdditionalInfoByDealerPinBndStatus(
                                peopleAllocationLogicInfo.getDealer().getPin(),"PENDING");

                referenceInfoEntities.addAll(referenceInfoEntityList);

                guarantorInfoEntities.addAll(guarantorInfoEntityList);

                customerAndBorrowerInfos.addAll(customerAndBorrowerInfoList);

                customerComplainEntities.addAll(customerComplainEntityList);

                customerRequestsEntities.addAll(customerRequestsEntityList);

                additionalInfos.addAll(additionalInfoList);
            }

            for (ReferenceInfoEntity entity : referenceInfoEntities) {
                System.out.println("reference: " + entity.getAccountNo());
            }
            model.addAttribute("referenceInfoEntities", referenceInfoEntities);
            model.addAttribute("guarantorInfoEntities", guarantorInfoEntities);
            model.addAttribute("customerAndBorrowerInfos", customerAndBorrowerInfos);
            model.addAttribute("customerComplainEntities", customerComplainEntities);
            model.addAttribute("customerRequestsEntities", customerRequestsEntities);
            model.addAttribute("additionalInfos",additionalInfos);


//                    cardDist.forEach(lmsCalculator::calculateSaveAndBackAmountForCardDistribution);

            //dealer detail
            List<EmployeeInfoEntity> dealerList = getDealerList(allocationLogicList);
            List<TeamDetailModel> dealerDetail = getDealerDetailListCard(dealerList, cardDist);
            model.addAttribute("dealerDetail", dealerDetail);


            //user total
            long userTotalAcc = cardDist.size();
            Double userTotalOutStanding = cardDist.stream().mapToDouble(card -> Double.valueOf(card.getOutstandingAmount())).sum();
            for (TeamDetailModel detailModel : dealerDetail) {
                // userTotalAcc = userTotalAcc + detailModel.getNoOfAcc();
                userTotalOutStanding = userTotalOutStanding + detailModel.getOutstanding();
            }
            model.addAttribute("userTotalAcc", userTotalAcc);
            model.addAttribute("userTotalOutStanding", userTotalOutStanding);
            model.addAttribute("totalNoOfDealer",dealerList.size());


            Set<String> activeList = cardDist.stream().map(CardAccountDistributionInfo::getAgeCode).collect(Collectors.toSet());
            model.addAttribute("monitoringBuckets", activeList);

            session.setAttribute("cardDistributionList", cardDist);

            model.addAttribute("teamDetailList", getCardTeamDetail(cardDist, employee));

            model.addAttribute("peopleAllocationLogic",
                    peopleAllocationLogicRepository.findFirstByDealerAndUnitOrderByCreatedDateDesc(employee, "Card"));
            model.addAttribute("teamleadcard",
                    peopleAllocationLogicRepository.findFirstByTeamlead(employee));
//                    model.addAttribute("rfd", dnoteMenuService.getAllActive());
//                    model.addAttribute("subrfd", dnSubMenu1Service.getActiveList());
//                    model.addAttribute("agencycard", agencyService.getActiveList());
            return "dashboard/card/teamleader/main";
        }

        // Manager Card
        else if ("manager".equals(designation)) {

            List<CardAccountDistributionInfo> cardDist = getCardDistributionByEmp(employee);

            // Calculate save and back amount
//                    cardDist.forEach(lmsCalculator::calculateSaveAndBackAmountForCardDistribution);


            //supervisor detail
            List<EmployeeInfoEntity> supervisorList = getSuperVisorList(allocationLogicList);
            List<TeamDetailModel> supervisorDetail = new ArrayList<>();
            for (EmployeeInfoEntity supervisor : supervisorList) {
                List<EmployeeInfoEntity> dealerForSupervisor = new ArrayList<>();
                for (PeopleAllocationLogicInfo allocationLogic : allocationLogicList) {
                    if (allocationLogic.getSupervisor().getPin().equals(supervisor.getPin())) {
                        EmployeeInfoEntity dealer = allocationLogic.getDealer();
                        dealerForSupervisor.add(dealer);
                    }
                }
                supervisorDetail.addAll(getTeamLeadOrSuperVisorDetailListCard(dealerForSupervisor, cardDist, supervisor));
            }
            model.addAttribute("supervisorDetail", supervisorDetail);


            //team lead detail
            List<TeamDetailModel> teamLeadDetail = new ArrayList<>();
            List<EmployeeInfoEntity> teamLeadList = getTeamLeadList(allocationLogicList);

            for (EmployeeInfoEntity teamLead : teamLeadList) {
                List<EmployeeInfoEntity> dealerForTeamLead = new ArrayList<>();
                for (PeopleAllocationLogicInfo allocationLogic : allocationLogicList) {
                    if (allocationLogic.getTeamlead().getPin().equals(teamLead.getPin())) {
                        EmployeeInfoEntity dealer = allocationLogic.getDealer();
                        dealerForTeamLead.add(dealer);
                    }
                }
                teamLeadDetail.addAll(getTeamLeadOrSuperVisorDetailListCard(dealerForTeamLead, cardDist, teamLead));
            }
            model.addAttribute("teamLeadDetail", teamLeadDetail);

            //dealer detail
            List<EmployeeInfoEntity> dealerList = getDealerList(allocationLogicList);
            List<TeamDetailModel> dealerDetail = getDealerDetailListCard(dealerList, cardDist);
            model.addAttribute("dealerDetail", dealerDetail);

            // user detail
            int userTotalAcc = cardDist.size();
            Double userTotalOutStanding = cardDist.stream().mapToDouble(CardAccountDistributionInfo::getNumericOutStanding).sum();
            model.addAttribute("userTotalAcc", userTotalAcc);
            model.addAttribute("userTotalOutStanding", userTotalOutStanding);
            model.addAttribute("totalNoOfDealer",dealerList.size());

            // employee status manager
//                    List<EmployeeInfoEntity> onBoardEmployeeList = new ArrayList<>();
//                    List<EmployeeInfoEntity> onLeaveEmployeeList = new ArrayList<>();
//                    List<EmployeeInfoEntity> onLeaveTodayEmployeeList = new ArrayList<>();

            int onBoard = 0, onLeave = 0, onLeaveToday = 0;

            for (EmployeeInfoEntity emp : dealerList) {
                EmployeeStatusManagerEntity empStMan = employeeStatusManagerDao.getEmpLastStatById(emp.getId());
                if (empStMan.getStartDate() == null || empStMan.getEndDate() == null) {
//                            onBoardEmployeeList.add(emp);
                    onBoard++;
                    continue;
                }
                if (DateUtils.isSameDay(empStMan.getStartDate(), empStMan.getEndDate()) && DateUtils.isSameDay(empStMan.getStartDate(), Calendar.getInstance().getTime())) {
//                            onLeaveTodayEmployeeList.add(emp);
                    onLeaveToday++;
                } else if (Calendar.getInstance().getTime().compareTo(empStMan.getEndDate()) > 0) {
//                            onBoardEmployeeList.add(emp);
                    onBoard++;
                } else {
//                            onLeaveEmployeeList.add(emp);
                    onLeave++;
                }

            }

            for (EmployeeInfoEntity emp : supervisorList) {
                EmployeeStatusManagerEntity empStMan = employeeStatusManagerDao.getEmpLastStatById(emp.getId());
                if (empStMan.getStartDate() == null || empStMan.getEndDate() == null) {
//                            onBoardEmployeeList.add(emp);
                    onBoard++;
                    continue;
                }
                if (DateUtils.isSameDay(empStMan.getStartDate(), empStMan.getEndDate()) && DateUtils.isSameDay(empStMan.getStartDate(), Calendar.getInstance().getTime())) {
//                            onLeaveEmployeeList.add(emp);
                    onLeaveToday++;
                } else if (Calendar.getInstance().getTime().compareTo(empStMan.getEndDate()) > 0) {
//                            onBoardEmployeeList.add(emp);
                    onBoard++;
                } else {
//                            onLeaveEmployeeList.add(emp);
                    onLeave++;
                }

            }

            for (EmployeeInfoEntity emp : teamLeadList) {
                EmployeeStatusManagerEntity empStMan = employeeStatusManagerDao.getEmpLastStatById(emp.getId());
                if (empStMan.getStartDate() == null || empStMan.getEndDate() == null) {
//                            onBoardEmployeeList.add(emp);
                    onBoard++;
                    continue;
                }
                if (DateUtils.isSameDay(empStMan.getStartDate(), empStMan.getEndDate()) && DateUtils.isSameDay(empStMan.getStartDate(), Calendar.getInstance().getTime())) {
//                            onLeaveEmployeeList.add(emp);
                    onLeaveToday++;
                } else if (Calendar.getInstance().getTime().compareTo(empStMan.getEndDate()) > 0) {
//                            onBoardEmployeeList.add(emp);
                    onBoard++;
                } else {
//                            onLeaveEmployeeList.add(emp);
                    onLeave++;
                }

            }

            model.addAttribute("onLeaveEmp", onLeave);
            model.addAttribute("onLeaveTodayEmp", onLeaveToday);
            model.addAttribute("onBoardEmp", onBoard);

            Set<String> locations = cardDist.stream().map(dist -> dist.getCardAccountBasicInfo().getLocation()).collect(Collectors.toSet());
            model.addAttribute("totalLocation", locations);

            Set<String> activeList = cardDist.stream().map(CardAccountDistributionInfo::getAgeCode).collect(Collectors.toSet());
            model.addAttribute("monitoringBuckets", activeList);


            session.setAttribute("cardDistributionList", cardDist);

            int totalEmployee = onBoard + onLeave + onLeaveToday;
            model.addAttribute("totalEmployee", totalEmployee);
            model.addAttribute("productList", productTypeService.getAllActive());
            model.addAttribute("managercard", peopleAllocationLogicRepository.findFirstByManager(employee));
            model.addAttribute("peopleAllocationLogic",
                    peopleAllocationLogicRepository.findFirstByDealerAndUnitOrderByCreatedDateDesc(employee, "Card"));
//                    model.addAttribute("rfd", dnoteMenuService.getAllActive());
//                    model.addAttribute("subrfd", dnSubMenu1Service.getActiveList());
//                    model.addAttribute("agencycard", agencyService.getActiveList());
            return "dashboard/card/manager/main";
        }

        // Supervisor card
        else if ("supervisor".equals(designation)) {

            List<CardAccountDistributionInfo> cardDist = getCardDistributionByEmp(employee);

            Set<String> activeList = cardDist.stream().map(CardAccountDistributionInfo::getAgeCode).collect(Collectors.toSet());
            model.addAttribute("monitoringBuckets", activeList);

            List<EmployeeInfoEntity> teamLeadList = getTeamLeadList(allocationLogicList);
            List<TeamDetailModel> teamLeadDetail = getCardTeamDetail(allocationLogicList, teamLeadList, cardDist);

            Set<String> locations = cardDist.stream().map(dist -> dist.getCardAccountBasicInfo().getLocation()).collect(Collectors.toSet());

            //dealer detail
            List<EmployeeInfoEntity> dealerList = getDealerList(allocationLogicList);
            List<TeamDetailModel> dealerDetail = getDealerDetailListCard(dealerList, cardDist);


            //user total
            long userTotalAcc = cardDist.size();
            Double userTotalOutStanding = cardDist.stream().mapToDouble(CardAccountDistributionInfo::getNumericOutStanding).sum();
//                    long userTotalAcc = cardDist.size();
//                    Double userTotalOutStanding = cardDist.stream().mapToDouble(card -> Double.valueOf(card.getOutStanding())).sum();
            for (TeamDetailModel detailModel : dealerDetail) {
                userTotalAcc = userTotalAcc + detailModel.getNoOfAcc();
                userTotalOutStanding = userTotalOutStanding + detailModel.getOutstanding();
            }
            session.setAttribute("cardDistributionList", cardDist);


            model.addAttribute("totalEmployee", teamLeadDetail.size() + dealerDetail.size());
            model.addAttribute("teamLeadDetail", teamLeadDetail);
            model.addAttribute("totalLocation", locations);

            model.addAttribute("userTotalAcc", userTotalAcc);
            model.addAttribute("userTotalOutStanding", BigDecimal.valueOf(userTotalOutStanding));
            model.addAttribute("totalNoOfDealer",dealerList.size());

            model.addAttribute("dealerDetail", dealerDetail);

            model.addAttribute("peopleAllocationLogic", peopleAllocationLogicRepository.findFirstByDealerAndUnitOrderByCreatedDateDesc(employee, "Card"));
            return "dashboard/card/supervisor/main";
        }

        // Dealer card
        else if ("dealer".equals(designation)) {

            List<CardAccountDistributionInfo> cardDist = getCardDistributionByEmp(employee);

            double totalOutstanding = 0;
            double totalMindue = 0;
            double performanceAvg = 0;
            try {
                performanceAvg = dealerPerformanceService.getCurrentMonthDealerPerformanceAverage(
                        employee.getPin(), "Card");
            }catch (Exception e){
                e.printStackTrace();
            }


            for (CardAccountDistributionInfo distributionInfo : cardDist) {
                totalOutstanding += Optional.ofNullable(Double.valueOf(distributionInfo.getOutstandingAmount())).orElse(0.0);
                totalMindue += Optional.ofNullable(distributionInfo.getMinDuePayment()).orElse(0.0);

                // Todo: Update save and back amount logic when provided by IFIC
                distributionInfo.setSaveAmount(Optional.ofNullable(distributionInfo.getEmiAmount()).orElse(0.0));
                distributionInfo.setBackAmount(Optional.ofNullable(distributionInfo.getEmiAmount()).orElse(0.0) + 1);
            }

            // Follow up notification for today's followup reasons
            if (session.getAttribute("loginTime") == null) {
                List followupToday = followUpService.getTodaysFollowUpNotificationForDealer(principal.getEmpId());
                model.addAttribute("followupToday", followupToday);
            }

//                    model.addAttribute("accountList", cardDist);

            model.addAttribute("totalAccounts", cardDist != null ? cardDist.size() : 0);
            model.addAttribute("totalOutstanding", totalOutstanding);
            model.addAttribute("totalMindue", totalMindue);
            model.addAttribute("performance", performanceAvg);

            session.setAttribute("cardDistributionList", cardDist);
            try {
                PeopleAllocationLogicInfo peopleAllocationLogicInfo = peopleAllocationLogicRepository.findFirstByDealerAndUnitOrderByCreatedDateDesc(employee, "Card");
                model.addAttribute("peopleAllocationLogic",peopleAllocationLogicInfo);
            }catch (Exception e){
                e.printStackTrace();
            }

            return "dashboard/card/dealer/main";
        }
        return "common/login/login";
    }

    private String loanDashboard(Model model, EmployeeInfoEntity employee, HttpSession session, UserPrincipal principal) {

        String designation = employee.getDesignation().getName().toLowerCase();
        List<PeopleAllocationLogicInfo> allocationLogicList =  dashboardService.getAllDealerList(employee.getId().toString(), designation, "Loan");
        model.addAttribute("currentUser",employee);

        //List<PeopleAllocationLogicInfo>allocationLogicList = peopleAllocationLogicRepository.findByManagerIdAndUnit(employee.getId(), employee.getUnit());




        // Team Leader Loan

        if ("team leader".equalsIgnoreCase(designation)) {
            model.addAttribute("productList", productTypeService.getAllActive());
            List<LoanAccountDistributionInfo> loanDist = getLoanDistributionByEmp(employee);


            // Reference info entity
            List<ReferenceInfoEntity> referenceInfoEntities = new ArrayList<>();
            List<GuarantorInfoEntity> guarantorInfoEntities = new ArrayList<>();
            List<CustomerAndBorrowerInfo> customerAndBorrowerInfos = new ArrayList<>();
            List<CustomerComplainDto> customerComplainEntities = new ArrayList<>();
            List<CustomerRequestsEntity> customerRequestsEntities = new ArrayList<>();
            List<AdditionalInfo> additionalInfos = new ArrayList<>();
            List<String> tlPinList = new ArrayList<>();


            for (PeopleAllocationLogicInfo peopleAllocationLogicInfo : allocationLogicList) {
                List<ReferenceInfoEntity> referenceInfoEntityList =
                        referenceInfoService.findReferenceInfoEntityByDealerPinBndStatus(
                                peopleAllocationLogicInfo.getDealer().getPin(), "PENDING");

                List<GuarantorInfoEntity> guarantorInfoEntityList =
                        guarantorInfoService.findGuarantorInfoEntityByDealerPinBndStatus(
                                peopleAllocationLogicInfo.getDealer().getPin(), "PENDING");

                List<CustomerAndBorrowerInfo> customerAndBorrowerInfoList =
                        customerOtherInfoService.findCustomerAndBorrowerInfoByDealerPinBndStatus(
                                peopleAllocationLogicInfo.getDealer().getPin(), "PENDING");

                List<CustomerRequestsEntity> customerRequestsEntityList =
                        customerRequestsService.findCustomerRequestsEntityByDealerPinBndStatus(
                                peopleAllocationLogicInfo.getDealer().getPin(), "PENDING");

                List<AdditionalInfo> additionalInfoList =
                        additionalInfoService.findAdditionalInfoByDealerPinBndStatus(
                                peopleAllocationLogicInfo.getDealer().getPin(), "PENDING");

                referenceInfoEntities.addAll(referenceInfoEntityList);

                guarantorInfoEntities.addAll(guarantorInfoEntityList);

                customerAndBorrowerInfos.addAll(customerAndBorrowerInfoList);

                //customerComplainEntities.addAll(customerComplainEntityList);

                customerRequestsEntities.addAll(customerRequestsEntityList);

                additionalInfos.addAll(additionalInfoList);

                tlPinList.add(peopleAllocationLogicInfo.getDealer().getPin());
            }

            List<CustomerComplainViewModel> customerComplainEntityList =
                    customerComplainService.getCustomerComplainByDealerPinList(tlPinList);

            model.addAttribute("referenceInfoEntities", referenceInfoEntities);
            model.addAttribute("guarantorInfoEntities", guarantorInfoEntities);
            model.addAttribute("customerAndBorrowerInfos", customerAndBorrowerInfos);
            model.addAttribute("customerComplainEntities", customerComplainEntityList);
            model.addAttribute("customerRequestsEntities", customerRequestsEntities);
            model.addAttribute("additionalInfos", additionalInfos);


            loanDist.forEach(lmsCalculator::calculateSaveAndBackAmountForLoanDistribution);

            //dealer detail
            List<EmployeeInfoEntity> dealerList = getDealerList(allocationLogicList);
            List<TeamDetailModel> dealerDetail = getDealerDetailListLoan(dealerList, loanDist);
            model.addAttribute("dealerDetail", dealerDetail);


            //user total
            long userTotalAcc = loanDist.size();
            Double userTotalOutStanding = loanDist.stream().mapToDouble(LoanAccountDistributionInfo::getNumericOutStanding).sum();

            for (TeamDetailModel detailModel : dealerDetail) {
                // userTotalAcc = userTotalAcc + detailModel.getNoOfAcc();
                userTotalOutStanding = userTotalOutStanding + detailModel.getOutstanding();
            }

            model.addAttribute("userTotalAcc", userTotalAcc);
            model.addAttribute("userTotalOutStanding", userTotalOutStanding);


            Set<String> activeList = loanDist.stream().map(LoanAccountDistributionInfo::getDpdBucket).collect(Collectors.toSet());
            model.addAttribute("monitoringBuckets", activeList);

            session.setAttribute("loanDistributionList", loanDist);
            model.addAttribute("teamDetailList", getLoanTeamDetail(loanDist, employee));

            model.addAttribute("peopleAllocationLogic",
                    peopleAllocationLogicRepository.findFirstByDealerAndUnitOrderByCreatedDateDesc(employee, "Loan"));

            List<VisitLedgerEntity> visitLedgerEntityList = new ArrayList<>();
            for (PeopleAllocationLogicInfo logicInfo: allocationLogicList){
                List<VisitLedgerEntity> visitLedgerEntities = visitLedgerService.findVisitLedgerEntityByCreatedByAndStatus(logicInfo.getDealer().getPin());
                if (!visitLedgerEntities.isEmpty()){
                    visitLedgerEntityList.addAll(visitLedgerEntities);
                }
            }

            List<String> pinList = peopleAllocationLogicRepository.getAllDealerPinByAnyPin(employee.getPin());
            pinList.add(employee.getPin());
            List<AccountEscalation> escalationList = escalationService.findByToUserPinAndStatus(pinList);

            model.addAttribute("totalChecker", (referenceInfoEntities.size() + guarantorInfoEntities.size()
                    + customerAndBorrowerInfos.size() + customerComplainEntityList.size() + customerRequestsEntities.size() + visitLedgerEntityList.size() + escalationList.size()+additionalInfos.size()));
            model.addAttribute("teamleadloan",
                    peopleAllocationLogicRepository.findFirstByTeamlead(employee));

            model.addAttribute("selfPin", employee.getPin());
            model.addAttribute("referenceCount", referenceInfoEntities.size());
            model.addAttribute("guarantorInfoCount", guarantorInfoEntities.size());
            model.addAttribute("customerAndBorrowerCount", customerAndBorrowerInfos.size());
            model.addAttribute("customerComplainCount", customerComplainEntityList.size());
            model.addAttribute("customerRequestsCount", customerRequestsEntities.size());
            model.addAttribute("visitLedgerCount", visitLedgerEntityList.size());
            model.addAttribute("escalationCount", escalationList.size());
            model.addAttribute("additionalInfoCount", additionalInfos.size());
            List<TeamDetailModel>teamList=new ArrayList<>();
            TeamDetailModel teamDetailModel=new TeamDetailModel();
            teamDetailModel.setDealerName(employee.getUser().getFirstName());
            teamDetailModel.setDealerPin(employee.getPin());
            teamDetailModel.setDesignation(employee.getDesignation().getName());
            teamList.add(teamDetailModel);
            model.addAttribute("teamLeadDetail",teamList);
            return "dashboard/loan/teamleader/main";
        }

        // Manager Loan
        if ("manager".equals(designation)) {

            List<LoanAccountDistributionInfo> loanDist = getLoanDistributionByEmp(employee);

            // Calculate save and back amount
            loanDist.forEach(lmsCalculator::calculateSaveAndBackAmountForLoanDistribution);

            //supervisor detail
            List<EmployeeInfoEntity> supervisorList = getSuperVisorList(allocationLogicList);
            List<TeamDetailModel> supervisorDetail = new ArrayList<>();

            for (EmployeeInfoEntity supervisor : supervisorList) {

                List<EmployeeInfoEntity> dealerForSupervisor = new ArrayList<>();

                for (PeopleAllocationLogicInfo allocationLogic : allocationLogicList) {

                    if (allocationLogic.getSupervisor().getPin().equals(supervisor.getPin())) {
                        EmployeeInfoEntity dealer = allocationLogic.getDealer();
                        dealerForSupervisor.add(dealer);
                    }
                }
                supervisorDetail.addAll(getTeamLeadOrSuperVisorDetailListLoan(dealerForSupervisor, loanDist, supervisor));
            }
            model.addAttribute("supervisorDetail", supervisorDetail);


            //team lead detail
            List<TeamDetailModel> teamLeadDetail = new ArrayList<>();
            List<EmployeeInfoEntity> teamLeadList = getTeamLeadList(allocationLogicList);

            for (EmployeeInfoEntity teamLead : teamLeadList) {
                List<EmployeeInfoEntity> dealerForTeamLead = new ArrayList<>();
                for (PeopleAllocationLogicInfo allocationLogic : allocationLogicList) {
                    if (allocationLogic.getTeamlead().getPin().equals(teamLead.getPin())) {
                        EmployeeInfoEntity dealer = allocationLogic.getDealer();
                        dealerForTeamLead.add(dealer);
                    }
                }
                teamLeadDetail.addAll(getTeamLeadOrSuperVisorDetailListLoan(dealerForTeamLead, loanDist, teamLead));
            }
            model.addAttribute("teamLeadDetail", teamLeadDetail);

            //dealer detail
            List<EmployeeInfoEntity> dealerList = getDealerList(allocationLogicList);
            List<TeamDetailModel> dealerDetail = getDealerDetailListLoan(dealerList, loanDist);
            model.addAttribute("dealerDetail", dealerDetail);

            // user detail
            int userTotalAcc = loanDist.size();
            Double userTotalOutStanding = loanDist.stream().mapToDouble(LoanAccountDistributionInfo::getNumericOutStanding).sum();
            model.addAttribute("userTotalAcc", userTotalAcc);
            model.addAttribute("userTotalOutStanding", userTotalOutStanding);

            // employee status manager
//                    List<EmployeeInfoEntity> onBoardEmployeeList = new ArrayList<>();
//                    List<EmployeeInfoEntity> onLeaveEmployeeList = new ArrayList<>();
//                    List<EmployeeInfoEntity> onLeaveTodayEmployeeList = new ArrayList<>();

            int onBoard = 0, onLeave = 0, onLeaveToday = 0;

            for (EmployeeInfoEntity emp : dealerList) {
                EmployeeStatusManagerEntity empStMan = employeeStatusManagerDao.getEmpLastStatById(emp.getId());
                if (empStMan.getStartDate() == null || empStMan.getEndDate() == null) {
//                            onBoardEmployeeList.add(emp);
                    onBoard++;
                    continue;
                }
                if (DateUtils.isSameDay(empStMan.getStartDate(), empStMan.getEndDate()) && DateUtils.isSameDay(empStMan.getStartDate(), Calendar.getInstance().getTime())) {
//                            onLeaveTodayEmployeeList.add(emp);
                    onLeaveToday++;
                } else if (Calendar.getInstance().getTime().compareTo(empStMan.getEndDate()) > 0) {
//                            onBoardEmployeeList.add(emp);
                    onBoard++;
                } else {
//                            onLeaveEmployeeList.add(emp);
                    onLeave++;
                }

            }

            for (EmployeeInfoEntity emp : supervisorList) {
                EmployeeStatusManagerEntity empStMan = employeeStatusManagerDao.getEmpLastStatById(emp.getId());
                if (empStMan.getStartDate() == null || empStMan.getEndDate() == null) {
//                            onBoardEmployeeList.add(emp);
                    onBoard++;
                    continue;
                }
                if (DateUtils.isSameDay(empStMan.getStartDate(), empStMan.getEndDate()) && DateUtils.isSameDay(empStMan.getStartDate(), Calendar.getInstance().getTime())) {
//                            onLeaveEmployeeList.add(emp);
                    onLeaveToday++;
                } else if (Calendar.getInstance().getTime().compareTo(empStMan.getEndDate()) > 0) {
//                            onBoardEmployeeList.add(emp);
                    onBoard++;
                } else {
//                            onLeaveEmployeeList.add(emp);
                    onLeave++;
                }

            }

            for (EmployeeInfoEntity emp : teamLeadList) {
                EmployeeStatusManagerEntity empStMan = employeeStatusManagerDao.getEmpLastStatById(emp.getId());
                if (empStMan.getStartDate() == null || empStMan.getEndDate() == null) {
//                            onBoardEmployeeList.add(emp);
                    onBoard++;
                    continue;
                }
                if (DateUtils.isSameDay(empStMan.getStartDate(), empStMan.getEndDate()) && DateUtils.isSameDay(empStMan.getStartDate(), Calendar.getInstance().getTime())) {
//                            onLeaveEmployeeList.add(emp);
                    onLeaveToday++;
                } else if (Calendar.getInstance().getTime().compareTo(empStMan.getEndDate()) > 0) {
//                            onBoardEmployeeList.add(emp);
                    onBoard++;
                } else {
//                            onLeaveEmployeeList.add(emp);
                    onLeave++;
                }

            }

            model.addAttribute("onLeaveEmp", onLeave);
            model.addAttribute("onLeaveTodayEmp", onLeaveToday);
            model.addAttribute("onBoardEmp", onBoard);

            Set<String> locations = loanDist.stream().map(dist -> dist.getLoanAccountBasicInfo().getLocation()).collect(Collectors.toSet());
            model.addAttribute("totalLocation", locations);

            Set<String> activeList = loanDist.stream().map(LoanAccountDistributionInfo::getDpdBucket).collect(Collectors.toSet());
            List<Integer> activeListInt = new ArrayList<>();
                activeListInt = activeList.stream().map(s -> s == null?-1:Integer.parseInt(s)).collect(Collectors.toList());
            Collections.sort(activeListInt);
            model.addAttribute("monitoringBuckets", activeListInt);


            session.setAttribute("loanDistributionList", loanDist);

            int totalEmployee = onBoard + onLeave + onLeaveToday;
            model.addAttribute("totalEmployee", totalEmployee);
            model.addAttribute("productList", productTypeService.getAllActive());
            model.addAttribute("managerloan", peopleAllocationLogicRepository.findFirstByManager(employee));
            model.addAttribute("peopleAllocationLogic", peopleAllocationLogicRepository.findFirstByDealerAndUnitOrderByCreatedDateDesc(employee, "Loan"));
//                    model.addAttribute("rfd", dnoteMenuService.getAllActive());
//                    model.addAttribute("subrfd", dnSubMenu1Service.getActiveList());
//                    model.addAttribute("agencyloan", agencyService.getActiveList());
            List<String> pinList = peopleAllocationLogicRepository.getAllDealerPinByAnyPin(employee.getPin());
            pinList.add(employee.getPin());
            List<AccountEscalation> escalationList = escalationService.findByToUserPinAndStatus(pinList);
            List<CustomerComplainViewModel> customerComplainDtos1 = customerComplainService.getCustomerComplainByDealerPinList(pinList);
            model.addAttribute("escalationCount",escalationList.size());
            model.addAttribute("customerComplainCount",customerComplainDtos1.size());
            model.addAttribute("customerComplainEntities",customerComplainDtos1);
            model.addAttribute("totalChecker",escalationList.size()+customerComplainDtos1.size());
            List<String> managerPinList = new ArrayList<>();
            managerPinList.add(employee.getPin());
            //model.addAttribute("supervisorList",peopleAllocationLogicService.getAllSupervisor());

            List<DesignationEntity> designations = designationService.getAll();
            List<DesignationViewModel> designationViewModels = new ArrayList<>();
            for(DesignationEntity desig : designations){
                DesignationViewModel designationViewModel = new DesignationViewModel();
                designationViewModel.setId(desig.getId());
                designationViewModel.setDesCode(desig.getDesCode());
                designationViewModel.setName(desig.getName());

                designationViewModels.add(designationViewModel);
            }

            model.addAttribute("designationList",designationViewModels);

            return "dashboard/loan/manager/main";
        }

        // Supervisor loan
        if ("supervisor".equals(designation)) {

            List<LoanAccountDistributionInfo> loanDist = getLoanDistributionByEmp(employee);

            Set<String> activeList = loanDist.stream().map(LoanAccountDistributionInfo::getDpdBucket).collect(Collectors.toSet());
            model.addAttribute("monitoringBuckets", activeList);

            List<EmployeeInfoEntity> teamLeadList = getTeamLeadList(allocationLogicList);
            List<TeamDetailModel> teamLeadDetail = getLoanTeamDetail(allocationLogicList, teamLeadList, loanDist);

            Set<String> locations = loanDist.stream().map(dist -> dist.getLoanAccountBasicInfo().getLocation()).collect(Collectors.toSet());

            //dealer detail
            List<EmployeeInfoEntity> dealerList = getDealerList(allocationLogicList);
            List<TeamDetailModel> dealerDetail = getDealerDetailListLoan(dealerList, loanDist);


            //user total
            long userTotalAcc = loanDist.size();
            Double userTotalOutStanding = loanDist.stream().mapToDouble(LoanAccountDistributionInfo::getNumericOutStanding).sum();
//                    long userTotalAcc = loanDist.size();
//                    Double userTotalOutStanding = loanDist.stream().mapToDouble(loan -> Double.valueOf(loan.getOutStanding())).sum();
            for (TeamDetailModel detailModel : dealerDetail) {
                userTotalAcc = userTotalAcc + detailModel.getNoOfAcc();
                userTotalOutStanding = userTotalOutStanding + detailModel.getOutstanding();
            }
            session.setAttribute("loanDistributionList", loanDist);



            model.addAttribute("totalEmployee", teamLeadDetail.size() + dealerDetail.size());
            model.addAttribute("teamLeadDetail", teamLeadDetail);
            model.addAttribute("totalLocation", locations);

            model.addAttribute("userTotalAcc", userTotalAcc);
            model.addAttribute("userTotalOutStanding", BigDecimal.valueOf(userTotalOutStanding));

            model.addAttribute("dealerDetail", dealerDetail);

            List<String> pinList = peopleAllocationLogicRepository.getAllDealerPinByAnyPin(employee.getPin());
            pinList.add(employee.getPin());
            List<AccountEscalation> escalationList = escalationService.findByToUserPinAndStatus(pinList);
            List<PeopleAllocationLogicInfo> peopleAllocationLogicInfoList = peopleAllocationLogicRepository.findTeamLeadBySupervisorId(employee.getId());
            List<VisitLedgerEntity> visitLedgerEntityList = new ArrayList<>();
            List<CustomerComplainViewModel> customerComplainDtos1 = customerComplainService.getCustomerComplainByDealerPinList(pinList);
            for (PeopleAllocationLogicInfo logicInfo: peopleAllocationLogicInfoList){
                List<VisitLedgerEntity> visitLedgerEntities = visitLedgerService.findVisitLedgerEntityByCreatedByAndStatusForSupervisor(logicInfo.getDealer().getPin());
                if (!visitLedgerEntities.isEmpty()){
                    visitLedgerEntityList.addAll(visitLedgerEntities);
                }
            }
            model.addAttribute("selfPin", employee.getPin());
            model.addAttribute("escalationCount",escalationList.size());
            model.addAttribute("visitLedgerCount",visitLedgerEntityList.size());
            model.addAttribute("customerComplainCount",customerComplainDtos1.size());
            model.addAttribute("customerComplainEntities",customerComplainDtos1);
            model.addAttribute("totalChecker",escalationList.size()+visitLedgerEntityList.size()+customerComplainDtos1.size());
            model.addAttribute("peopleAllocationLogic", peopleAllocationLogicRepository.findFirstByDealerAndUnitOrderByCreatedDateDesc(employee, "Loan"));
            return "dashboard/loan/supervisor/main";
        }

        // Dealer loan
        if ("dealer".equals(designation)) {

            List<LoanAccountDistributionInfo> loanDist = getLoanDistributionByEmp(employee);

            double totalOutstanding = 0;
            double totalMindue = 0;
            double performanceAvg = 0;
            try{
                performanceAvg=dealerPerformanceService.getCurrentMonthDealerPerformanceAverage(
                        employee.getPin(), "Loan");
            }catch (Exception e){
                e.printStackTrace();
            }


            for (LoanAccountDistributionInfo distributionInfo : loanDist) {
                totalOutstanding += Double.valueOf(distributionInfo.getOutStanding());
                totalMindue += distributionInfo.getOpeningOverDue();

                // Todo: Update save and back amount logic when provided by IFIC
                distributionInfo.setSaveAmount(distributionInfo.getEmiAmount());
                distributionInfo.setBackAmount(distributionInfo.getEmiAmount() + 1);
            }

            // Follow up notification for today's followup reasons
            if (session.getAttribute("loginTime") == null) {
                List followupToday = followUpService.getTodaysFollowUpNotificationForDealer(principal.getEmpId());
                model.addAttribute("followupToday", followupToday);
            }

//                    model.addAttribute("accountList", loanDist);
            model.addAttribute("totalAccounts", loanDist != null ? loanDist.size() : 0);
            model.addAttribute("totalOutstanding", totalOutstanding);
            model.addAttribute("totalMindue", totalMindue);
            model.addAttribute("performance", performanceAvg);

            session.setAttribute("loanDistributionList", loanDist);

            model.addAttribute("peopleAllocationLogic", peopleAllocationLogicRepository.findFirstByDealerAndUnitOrderByCreatedDateDesc(employee, "Loan"));
            return "dashboard/loan/dealer/main";
        }
        return "common/login/login";
    }


    //*****
//    List<CardAccountDistributionInfo> getCardDistributionByEmp(EmployeeInfoEntity employeeInfoEntity) {
//
//        List<CardAccountDistributionInfo> distributionList = new ArrayList<>();
//
//        List<PeopleAllocationLogicInfo> allocationList = new ArrayList<>();
//        List<String> tempDealerList = new ArrayList<>();
//
//
//        if (!employeeInfoEntity.getDesignation().getName().toUpperCase().equals("DEALER")) {
//            allocationList = dashboardService.getAllDealerList(employeeInfoEntity.getId().toString(), employeeInfoEntity.getDesignation().getName(), "Card");
//
//            for (PeopleAllocationLogicInfo logic : allocationList) {
//                Optional<EmployeeInfoEntity> dealer = Optional.ofNullable(logic.getDealer());
//                if (dealer.isPresent()) {
//                    if (!tempDealerList.contains(dealer.get().getPin())) {
//                        List<CardAccountDistributionInfo> temp = dashboardService.getListBYUserPinCard(logic.getDealer().getPin());
//                        distributionList.addAll(temp);
//                        tempDealerList.add(dealer.get().getPin());
//                    }
//                }
//            }
//        } else {
//            distributionList = dashboardService.getListBYUserPinCard(employeeInfoEntity.getPin());
//        }
//
//        return distributionList;
//    }

    //*****
    List<LoanAccountDistributionInfo> getLoanDistributionByEmp(EmployeeInfoEntity employeeInfoEntity) {
        List<PeopleAllocationLogicInfo> allocationList = new ArrayList<>();
        List<LoanAccountDistributionInfo> loanDist = new ArrayList<>();
        boolean isDealer = employeeInfoEntity.getDesignation().getName().toUpperCase().equals("DEALER");
//        boolean isSupervisor = employeeInfoEntity.getDesignation().getName().toUpperCase().equals("SUPERVISOR");

        if (!isDealer) {
            allocationList = dashboardService.getAllDealerList(employeeInfoEntity.getId().toString(), employeeInfoEntity.getDesignation().getName(), "Loan");

            for (PeopleAllocationLogicInfo logic : allocationList) {
                List<LoanAccountDistributionInfo> temp = dashboardService.getAccountListByUser(logic.getDealer().getPin());
                loanDist.addAll(temp);
            }
//            if (isSupervisor) {
//                List<LoanAccountDistributionInfo> temp = dashboardService.getAccountListByUser(employeeInfoEntity.getPin());
//                loanDist.addAll(temp);
//            }

        } else {
            loanDist = dashboardService.getAccountListByUser(employeeInfoEntity.getPin());
        }

        return loanDist;
    }

    //*****
    List<EmployeeInfoEntity> getSuperVisorList(List<PeopleAllocationLogicInfo> allocationList) {
        List<EmployeeInfoEntity> superVisorList = new ArrayList<>();

        for (PeopleAllocationLogicInfo allocationLogic : allocationList) {
            EmployeeInfoEntity superVisor = allocationLogic.getSupervisor();
            if (!superVisorList.contains(superVisor)) {
                superVisorList.add(superVisor);
            }
        }

        return superVisorList;
    }

    //*****
    List<EmployeeInfoEntity> getTeamLeadList(List<PeopleAllocationLogicInfo> allocationList) {
        List<EmployeeInfoEntity> teamLeadList = new ArrayList<>();
        for (PeopleAllocationLogicInfo allocationLogic : allocationList) {
            EmployeeInfoEntity teamLead = allocationLogic.getTeamlead();
            if (!teamLeadList.contains(teamLead)) {
                teamLeadList.add(teamLead);
            }
        }
        return teamLeadList;
    }


    //*****
    List<EmployeeInfoEntity> getDealerList(List<PeopleAllocationLogicInfo> allocationList) {
        List<EmployeeInfoEntity> dealerList = new ArrayList<>();

        for (PeopleAllocationLogicInfo allocationLogic : allocationList) {
            EmployeeInfoEntity dealer = allocationLogic.getDealer();
            if (!dealerList.contains(dealer)) {
                dealerList.add(dealer);
            }
        }

        return dealerList;
    }

//    List<TeamDetailModel> getSuperVisorDetailListCard(List<EmployeeInfoEntity> supervisorList,List<CardAccountDistributionInfo> cardDist)
//    {
//        List<TeamDetailModel> teamDetailModelList=new ArrayList<>();
//
//        for(EmployeeInfoEntity supervisor: supervisorList)
//        {
//            TeamDetailModel  detailModel=new TeamDetailModel();
//            detailModel.setDesignation("Supervisor");
//            detailModel.setDealerName(supervisor.getUser().getFirstName());
//            detailModel.setDealerPin(supervisor.getPin());
//
//            List<CardAccountDistributionInfo> checkedItem=new ArrayList<>();
//            for(CardAccountDistributionInfo distributionInfo : cardDist)
//            {
//                if(distributionInfo.getSupervisorPin().equals(supervisor.getPin()))
//                {
//                    detailModel.setNoOfAcc(detailModel.getNoOfAcc()+1);
//                    detailModel.setOutstanding(detailModel.getOutstanding()+Double.parseDouble(distributionInfo.getOutstandingAmount()));
//                }
//            }
//            teamDetailModelList.add(detailModel);
//            cardDist.remove(checkedItem);
//        }
//        return teamDetailModelList;
//    }


    //*****
    List<TeamDetailModel> getTeamLeadOrSuperVisorDetailListLoan(List<EmployeeInfoEntity> dealerList, List<LoanAccountDistributionInfo> loanDist, EmployeeInfoEntity emp) {
        List<TeamDetailModel> teamDetailModelList = new ArrayList<>();
        TeamDetailModel detailModel = new TeamDetailModel();
        detailModel.setDesignation(emp.getDesignation().getName());
        detailModel.setDealerName(emp.getUser().getFirstName() + " " + emp.getUser().getLastName());
        detailModel.setDealerPin(emp.getPin());
        detailModel.setPerformance(superVisorPerformance(dealerList));
        detailModel.setLastMonthRating(supervisorLastMonthRating(detailModel.getPerformance()));


        for (EmployeeInfoEntity dealer : dealerList) {
            List<LoanAccountDistributionInfo> checkedItem = new ArrayList<>();
            for (LoanAccountDistributionInfo distributionInfo : loanDist) {
                if (distributionInfo.getDealerPin().equals(dealer.getPin())) {
                    detailModel.setNoOfAcc(detailModel.getNoOfAcc() + 1);
                    detailModel.setOutstanding(detailModel.getOutstanding() + Double.parseDouble(distributionInfo.getOutStanding()));

                    checkedItem.add(distributionInfo);
                }
            }
            loanDist.remove(checkedItem);
        }
        teamDetailModelList.add(detailModel);
        return teamDetailModelList;
    }

    private double superVisorPerformance(List<EmployeeInfoEntity> dealerList){
        double superVisorPerformance=0.0;
        for(EmployeeInfoEntity dealer:dealerList){
            PerforamnceAchivmentModel performance = loanPerformanceAndEsauTrendService.getPerformanceAchivment(dealer.getPin(), SettingsHelper.getStartDate(), SettingsHelper.getEndDate());
            if(performance!=null){
                superVisorPerformance+=performance.getPerformance();
            }
        }
        return superVisorPerformance/dealerList.size();
    }

    private String supervisorLastMonthRating(double superVisorperformance) {
        String lastMonthRating = "";
        if (superVisorperformance > 0.0) {
            List<ESAULoanEntity> esau = esauLoanService.getAll();
            for (ESAULoanEntity esauLoanEntity : esau) {
                if (esauLoanEntity.getFinalAvgLowerLimit() <= superVisorperformance && esauLoanEntity.getFinalAvgUpperLimit() >= superVisorperformance) {
                    lastMonthRating = esauLoanEntity.getRatingName();
                }else lastMonthRating="N/A";
            }
        }
        return lastMonthRating;
    }

    //*****
    List<TeamDetailModel> getTeamLeadOrSuperVisorDetailListCard(List<EmployeeInfoEntity> dealerList, List<CardAccountDistributionInfo> cardDist, EmployeeInfoEntity emp) {
        List<TeamDetailModel> teamDetailModelList = new ArrayList<>();
        TeamDetailModel detailModel = new TeamDetailModel();
        detailModel.setDesignation(emp.getDesignation().getName());
        detailModel.setDealerName(emp.getUser().getFirstName() + " " + emp.getUser().getLastName());
        detailModel.setDealerPin(emp.getPin());
        List<String> strings = new ArrayList<>();

        for (EmployeeInfoEntity dealer : dealerList) {
            if (!strings.contains(dealer.getPin())) {
                strings.add(dealer.getPin());
                List<CardAccountDistributionInfo> checkedItem = new ArrayList<>();
                for (CardAccountDistributionInfo distributionInfo : cardDist) {
                    if (distributionInfo.getDealerPin().equals(dealer.getPin())) {
                        detailModel.setNoOfAcc(detailModel.getNoOfAcc() + 1);
                        detailModel.setOutstanding(detailModel.getOutstanding() + Double.parseDouble(distributionInfo.getOutstandingAmount()));
                        checkedItem.add(distributionInfo);
                    }
                }
                cardDist.remove(checkedItem);
            }


        }
        teamDetailModelList.add(detailModel);
        return teamDetailModelList;
    }


    //*****
    List<TeamDetailModel> getDealerDetailListLoan(List<EmployeeInfoEntity> dealerList, List<LoanAccountDistributionInfo> cardDist) {
        List<TeamDetailModel> teamDetailModelList = new ArrayList<>();
        List<String>dealerPins=new ArrayList<>();
        for (EmployeeInfoEntity dealer : dealerList) {
            TeamDetailModel detailModel = new TeamDetailModel();
            if(dealer!=null){
                detailModel.setDesignation("Dealer");
                detailModel.setDealerName(dealer.getUser().getFirstName() + " " + dealer.getUser().getLastName());
                detailModel.setDealerPin(dealer.getPin());
                dealerPins.add(dealer.getPin());
                PerforamnceAchivmentModel performance = loanPerformanceAndEsauTrendService.getPerformanceAchivment(dealer.getPin(), SettingsHelper.getStartDate(), SettingsHelper.getEndDate());
                if(performance != null){
                    detailModel.setPerformance(performance.getPerformance());
                    List<ESAULoanEntity>esau=esauLoanService.getAll();
                    for(ESAULoanEntity esauLoanEntity:esau){
                        if(esauLoanEntity.getFinalAvgLowerLimit()<=performance.getPerformance() && esauLoanEntity.getFinalAvgUpperLimit()>=performance.getPerformance() ){
                            detailModel.setLastMonthRating(esauLoanEntity.getRatingName());
                        }
                    }
                }


            }

            List<LoanAccountDistributionInfo> checkedItem = new ArrayList<>();
            for (LoanAccountDistributionInfo distributionInfo : cardDist) {
                if (distributionInfo.getDealerPin().equals(dealer.getPin())) {
                    detailModel.setNoOfAcc(detailModel.getNoOfAcc() + 1);
                    detailModel.setOutstanding(detailModel.getOutstanding() + Double.parseDouble(distributionInfo.getOutStanding()));

                    checkedItem.add(distributionInfo);
                }
            }
            teamDetailModelList.add(detailModel);
            cardDist.remove(checkedItem);
        }

        return teamDetailModelList;
    }

    //*****
    List<TeamDetailModel> getDealerDetailListCard(List<EmployeeInfoEntity> dealerList, List<CardAccountDistributionInfo> cardDist) {
        List<TeamDetailModel> teamDetailModelList = new ArrayList<>();

        for (EmployeeInfoEntity dealer : dealerList) {
            TeamDetailModel detailModel = new TeamDetailModel();
            detailModel.setDesignation("Dealer");
            if (dealer != null) {
                detailModel.setDealerName(dealer.getUser().getFirstName() + " " + dealer.getUser().getLastName());
                detailModel.setDealerPin(dealer.getPin());
            }

            List<CardAccountDistributionInfo> checkedItem = new ArrayList<>();
            for (CardAccountDistributionInfo distributionInfo : cardDist) {
                if (dealer != null) {
                    if (distributionInfo.getDealerPin().equals(dealer.getPin())) {
                        detailModel.setNoOfAcc(detailModel.getNoOfAcc() + 1);
                        detailModel.setOutstanding(detailModel.getOutstanding() + Double.parseDouble(distributionInfo.getOutstandingAmount()));

                        checkedItem.add(distributionInfo);
                    }
                }
            }
            teamDetailModelList.add(detailModel);
            cardDist.remove(checkedItem);
        }

        return teamDetailModelList;
    }

    //*****
    List<TeamDetailModel> getLoanTeamDetail(List<LoanAccountDistributionInfo> loanDist, EmployeeInfoEntity employeeInfoEntity) {
        List<TeamDetailModel> teamDetailList = new ArrayList<>();

        List<PeopleAllocationLogicInfo> allocationList = dashboardService.getAllDealerList(employeeInfoEntity.getId().toString(), employeeInfoEntity.getDesignation().getName(), "Loan");

        for (PeopleAllocationLogicInfo allocationLogic : allocationList) {
            TeamDetailModel detailModel = new TeamDetailModel();
            detailModel.setDesignation("Dealer");
            detailModel.setDealerPin(allocationLogic.getDealer().getPin());
            detailModel.setDealerName(allocationLogic.getDealer().getUser().getFirstName() + ' ' + allocationLogic.getDealer().getUser().getLastName());
            //detailModel.setOutstanding(0d);
            for (LoanAccountDistributionInfo distributionInfo : loanDist) {

                if (allocationLogic.getDealer().getPin().equals(distributionInfo.getDealerPin())) {
                    detailModel.setNoOfAcc(detailModel.getNoOfAcc() + 1);
                    detailModel.setOutstanding(detailModel.getOutstanding() + Double.parseDouble(distributionInfo.getOutStanding()));
                }
            }
            teamDetailList.add(detailModel);

        }
        return teamDetailList;

    }

    List<TeamDetailModel> getLoanTeamDetail(List<PeopleAllocationLogicInfo> allocationLogicList, List<EmployeeInfoEntity> teamLeadList, List<LoanAccountDistributionInfo> loanDist) {
        List<TeamDetailModel> teamDetailList = new ArrayList<>();


        for (EmployeeInfoEntity teamLead : teamLeadList) {
            List<EmployeeInfoEntity> dealerForTeamLead = new ArrayList<>();
            for (PeopleAllocationLogicInfo allocationLogic : allocationLogicList) {
                if (allocationLogic.getTeamlead().getPin().equals(teamLead.getPin())) {
                    EmployeeInfoEntity dealer = allocationLogic.getDealer();
                    dealerForTeamLead.add(dealer);
                }
            }
            teamDetailList.addAll(getTeamLeadOrSuperVisorDetailListLoan(dealerForTeamLead, loanDist, teamLead));
        }
        return teamDetailList;

    }

    //*****
    List<TeamDetailModel> getCardTeamDetail(List<CardAccountDistributionInfo> CardDist, EmployeeInfoEntity employeeInfoEntity) {
        List<TeamDetailModel> teamDetailList = new ArrayList<>();

        List<PeopleAllocationLogicInfo> allocationList = dashboardService.getAllDealerList(employeeInfoEntity.getId().toString(), employeeInfoEntity.getDesignation().getName(), "Card");

        for (PeopleAllocationLogicInfo allocationLogic : allocationList) {
            TeamDetailModel detailModel = new TeamDetailModel();
            detailModel.setDesignation("Dealer");
            detailModel.setDealerPin(allocationLogic.getDealer().getPin());
            detailModel.setDealerName(allocationLogic.getDealer().getUser().getFirstName() + ' ' + allocationLogic.getDealer().getUser().getLastName());
            //detailModel.setOutstanding(0d);
            for (CardAccountDistributionInfo distributionInfo : CardDist) {

                if (allocationLogic.getDealer().getPin().equals(distributionInfo.getDealerPin())) {
                    detailModel.setNoOfAcc(detailModel.getNoOfAcc() + 1);
                    detailModel.setOutstanding(detailModel.getOutstanding() + Double.parseDouble(distributionInfo.getOutstandingAmount()));
                }
            }
            teamDetailList.add(detailModel);

        }
        return teamDetailList;

    }

    List<TeamDetailModel> getCardTeamDetail(List<PeopleAllocationLogicInfo> allocationLogicList, List<EmployeeInfoEntity> teamLeadList, List<CardAccountDistributionInfo> CardDist) {
        List<TeamDetailModel> teamDetailList = new ArrayList<>();


        for (EmployeeInfoEntity teamLead : teamLeadList) {
            List<EmployeeInfoEntity> dealerForTeamLead = new ArrayList<>();
            for (PeopleAllocationLogicInfo allocationLogic : allocationLogicList) {
                if (allocationLogic.getTeamlead().getPin().equals(teamLead.getPin())) {
                    EmployeeInfoEntity dealer = allocationLogic.getDealer();
                    dealerForTeamLead.add(dealer);
                }
            }
            teamDetailList.addAll(getTeamLeadOrSuperVisorDetailListCard(dealerForTeamLead, CardDist, teamLead));
        }
        return teamDetailList;

    }

    //*****
    List<CardAccountDistributionInfo> getCardDistributionByEmp(EmployeeInfoEntity employeeInfoEntity) {

        List<CardAccountDistributionInfo> distributionList = new ArrayList<>();

        List<PeopleAllocationLogicInfo> allocationList = new ArrayList<>();
        List<String> tempDealerList=new ArrayList<>();


        if (!employeeInfoEntity.getDesignation().getName().toUpperCase().equals("DEALER")) {
            allocationList = dashboardService.getAllDealerList(employeeInfoEntity.getId().toString(), employeeInfoEntity.getDesignation().getName(), "Card");

            for (PeopleAllocationLogicInfo logic : allocationList) {
                Optional<EmployeeInfoEntity> dealer = Optional.ofNullable(logic.getDealer());
                if(dealer.isPresent()){
                    if(!tempDealerList.contains(dealer.get().getPin())){
                        List<CardAccountDistributionInfo> temp = dashboardService.getListBYUserPinCard(logic.getDealer().getPin());
                        distributionList.addAll(temp);
                        tempDealerList.add(dealer.get().getPin());
                    }
                }
            }
        } else {
            distributionList = dashboardService.getListBYUserPinCard(employeeInfoEntity.getPin());
        }

        return distributionList;
    }


    @GetMapping("/signup")
    public String showSignupPage() {
        return "common/content/user-signup";
    }


}
