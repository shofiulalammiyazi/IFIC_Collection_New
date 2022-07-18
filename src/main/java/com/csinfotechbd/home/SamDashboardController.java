package com.csinfotechbd.home;

import com.csinfotechbd.collection.allocationLogic.PeopleAllocationLogicInfo;
import com.csinfotechbd.collection.allocationLogic.PeopleAllocationLogicRepository;
import com.csinfotechbd.collection.dashboard.AdvancedSearchPayload;
import com.csinfotechbd.collection.dashboard.DashboardService;
import com.csinfotechbd.collection.dashboard.TeamDetailModel;
import com.csinfotechbd.collection.samd.dashboard.SamdDashboardService;
import com.csinfotechbd.collection.samd.dataEntry.loanAccountDistribution.SamLoanAccountDistribution;
import com.csinfotechbd.collection.settings.PARaccountRuleLoan.PARaccountRuleLoanEntity;
import com.csinfotechbd.collection.settings.PARaccountRuleLoan.PARaccountRuleLoanService;
import com.csinfotechbd.collection.settings.agency.AgencyService;
import com.csinfotechbd.collection.settings.diaryNoteMenu.DnoteMenuService;
import com.csinfotechbd.collection.settings.diaryNoteSubMenu1.DNSubMenu1Service;
import com.csinfotechbd.collection.settings.dpdBucket.DPDBucketEntity;
import com.csinfotechbd.collection.settings.dpdBucket.DPDBucketService;
import com.csinfotechbd.collection.settings.employee.EmployeeInfoEntity;
import com.csinfotechbd.collection.settings.employee.EmployeeService;
import com.csinfotechbd.collection.settings.employeeStatusManagement.EmployeeStatusManagerDao;
import com.csinfotechbd.collection.settings.employeeStatusManagement.EmployeeStatusManagerEntity;
import com.csinfotechbd.collection.settings.lateReason.LateReasonEntity;
import com.csinfotechbd.collection.settings.lateReason.LateReasonService;
import com.csinfotechbd.collection.settings.lateReasonExplain.LateReasonExplainInfo;
import com.csinfotechbd.collection.settings.lateReasonExplain.LateReasonExplainRepository;
import com.csinfotechbd.collection.settings.nplAccountRule.NPLAccountRuleEntity;
import com.csinfotechbd.collection.settings.nplAccountRule.NPLAccountRuleService;
import com.csinfotechbd.collection.settings.officeTimeSetup.OfficeTimeSetupInfo;
import com.csinfotechbd.collection.settings.officeTimeSetup.OfficeTimeSetupService;
import com.csinfotechbd.collection.settings.productType.ProductTypeService;
import com.csinfotechbd.legal.setup.caseStatus.CaseStatusService;
import com.csinfotechbd.legal.setup.caseType.CaseTypeService;
import com.csinfotechbd.loanApi.model.AdvancedSearchDataModel;
import com.csinfotechbd.retail.card.dataEntry.distribution.accountDistributionInfo.CardAccountDistributionInfo;
import com.csinfotechbd.retail.loan.dataEntry.ptp.LoanPtp;
import com.csinfotechbd.user.UserPrincipal;
import com.csinfotechbd.utillity.DateUtils;
import com.csinfotechbd.utillity.HttpSessionUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;


@Controller
@RequiredArgsConstructor
@RequestMapping("/collection/samd")
public class SamDashboardController {

    private final LateReasonService lateReasonService;

    private final EmployeeService employeeService;

    private final PeopleAllocationLogicRepository peopleAllocationLogicRepository;

    private final DnoteMenuService dnoteMenuService;

    private final DNSubMenu1Service dnSubMenu1Service;

    private final DPDBucketService dpdBucketService;

    private final ProductTypeService productTypeService;

    private final AgencyService agencyService;

    private final LateReasonExplainRepository lateReasonRepository;

    private final EmployeeStatusManagerDao employeeStatusManagerDao;

    private final DashboardService dashboardService;

    private final SamdDashboardService samdDashboardService;

    private final PARaccountRuleLoanService paRaccountRuleLoanService;

    private final NPLAccountRuleService nplAccountRuleService;

    private final OfficeTimeSetupService officeTimeSetupService;

    private final DateUtils dateUtils;

    private final HttpSessionUtils httpSessionUtils;

    private final CaseTypeService caseTypeService;

    private final CaseStatusService caseStatusService;

    @GetMapping("/dashboard")
    public String main(Principal principal, HttpSession session, Model model,
                       @RequestParam(value = "viewCardDash", required = false) boolean viewCardDash,
                       HttpServletRequest request) throws ParseException {

        Date startDate = dateUtils.getMonthStartDate();
        Date endDate = dateUtils.getMonthEndDate();

        if (principal != null) {
            UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            EmployeeInfoEntity employeeInfoEntity = employeeService.getByPin(userPrincipal.getEmpId());
            OfficeTimeSetupInfo timeSetupInfo = officeTimeSetupService.getOfficeTimeSetup();

            String format = LocalDate.now().toString() + " " + timeSetupInfo.getLogoutHour().toString();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
            Date logoutTime = simpleDateFormat.parse(format);

            model.addAttribute("logoutTime", logoutTime);
            model.addAttribute("viewCardDash", viewCardDash);
//            if (employeeInfoEntity.getDesignation().getName().equals("Admin")) {
//                return "card/contents/home/card";
//            }

//            if (employeeInfoEntity.getDepartment().getName().toLowerCase().contains("sam")) {
//                return "card/contents/home/card";
//            } else if (employeeInfoEntity.getDepartment().getName().toLowerCase().contains("retail")) {
//
//            }

            session.setAttribute("unit", employeeInfoEntity.getUnit());

            if (employeeInfoEntity.getUnit().toUpperCase().contains("SAM")) {

                //Only for Loan
                List<PeopleAllocationLogicInfo> allocationLogicList =
                        dashboardService.getAllDealerList(employeeInfoEntity.getId().toString(),
                                employeeInfoEntity.getDesignation().getName().toUpperCase(), "SAM");

                if (employeeInfoEntity.getDesignation().getName().equals("Dealer"))
                    return samLoanDealer(session, model, employeeInfoEntity, startDate, endDate);

                if (employeeInfoEntity.getDesignation().getName().equals("Team Leader"))
                    return samLoanTeamLeader(session, model, employeeInfoEntity, allocationLogicList, startDate, endDate);

                if (employeeInfoEntity.getDesignation().getName().equals("Manager"))
                    return samLoanManager(session, model, employeeInfoEntity, startDate, endDate);

                if (employeeInfoEntity.getDesignation().getName().equals("Supervisor"))
                    return samLoanSupervisor(session, model, employeeInfoEntity, allocationLogicList, startDate, endDate);

            } else {
                return null;
            }
        }

        OfficeTimeSetupInfo timeSetupInfo = officeTimeSetupService.getOfficeTimeSetup();
        String format = LocalDate.now().toString() + " " + timeSetupInfo.getOfficeHour().toString();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        Date lateTime = simpleDateFormat.parse(format);
        Date currentTime = new Date();

        boolean after = currentTime.after(lateTime);

        List<LateReasonEntity> lateReasonServiceAll = lateReasonService.getAll();
        if (after) {

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
        return "common/login/login";
    }

    private String samLoanTeamLeader(HttpSession session, Model model, EmployeeInfoEntity employeeInfoEntity,
                                     List<PeopleAllocationLogicInfo> allocationLogicList, Date startDate, Date endDate) {

        model.addAttribute("employee", employeeInfoEntity);
        model.addAttribute("loginTime", new Date());

        List<DPDBucketEntity> activeList = dpdBucketService.getActiveList();


        model.addAttribute("productList", productTypeService.getAllActive());
        model.addAttribute("dpdBucket", activeList);


        List<SamLoanAccountDistribution> loanDist = getLoanDistributionByEmp(employeeInfoEntity);
        Duration day;

        for (SamLoanAccountDistribution distributionInfo : loanDist) {
            LoanPtp loanPtp = new LoanPtp();
            try {
                loanPtp = dashboardService.getLoanPtpByCustomer(distributionInfo.getLoanAccountBasicInfo().getCustomer().getId()).get(0);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }

            distributionInfo.setLoanPtp(loanPtp);

            Date monthEndDate = dateUtils.getMonthEndDate();
            Date monthOpenDate = distributionInfo.getStatusDate();

            LocalDate localDate1 = monthOpenDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate localDate2 = monthEndDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();


            double saveAmount = 0;
            double MO_DPD = 0;
            double dayDiff = 0;
            double backAmount = 0;

            day = Duration.between(localDate1.atStartOfDay(), localDate2.atStartOfDay());

            //long ld=day.toDays();
            dayDiff = (double) day.toDays();
            MO_DPD = distributionInfo.getDpd();
            if (distributionInfo.getDpdBucket().toUpperCase().equals("X") || distributionInfo.getDpdBucket().toUpperCase().equals("INTERIM")) {
                saveAmount = ((MO_DPD + (dayDiff - 29)) * distributionInfo.getEmiAmount()) / 30;
                distributionInfo.setBackAmount(0);
            } else {
                double highestDpdRange = Double.parseDouble(distributionInfo.getDpdBucket()) + 29;
                saveAmount = ((MO_DPD + (dayDiff - highestDpdRange)) * distributionInfo.getEmiAmount()) / 30;

                double highestDpdRangeBack = Double.parseDouble(distributionInfo.getDpdBucket()) - 1;
                backAmount = ((MO_DPD + dayDiff - highestDpdRangeBack) * distributionInfo.getEmiAmount()) / 30;

                distributionInfo.setBackAmount(backAmount);

            }
            //distributionInfo.setBackAmount(backAmount);
            if (saveAmount > 0)
                distributionInfo.setSaveAmount(saveAmount);
            else
                distributionInfo.setSaveAmount(0);

        }


        //dealer detail
        List<EmployeeInfoEntity> dealerList = getDealerList(allocationLogicList);
        List<TeamDetailModel> dealerDetail = getDealerDetailListLoan(dealerList, loanDist);
        model.addAttribute("dealerDetail", dealerDetail);


        //user total
        long userTotalAcc = 0;
        Double userTotalOutStanding = 0D;
        for (TeamDetailModel detailModel : dealerDetail) {
            userTotalAcc = userTotalAcc + detailModel.getNoOfAcc();
            userTotalOutStanding = userTotalOutStanding + detailModel.getOutstanding();
        }
        model.addAttribute("userTotalAcc", userTotalAcc);
        model.addAttribute("userTotalOutStanding", userTotalOutStanding);


        session.setAttribute("loanDistributionList", loanDist);

        model.addAttribute("teamDetailList", getLoanTeamDetail(loanDist, employeeInfoEntity));

        List<LateReasonExplainInfo> lateReasonExplainInfos =
                lateReasonRepository.findByCreatedDateIsBetweenAndUserOrderByCreatedDateDesc(
                        startDate, endDate, employeeInfoEntity.getUser());
        model.addAttribute("lateReason", lateReasonExplainInfos);

        model.addAttribute("peopleAllocationLogic",
                peopleAllocationLogicRepository.findFirstByDealerAndUnitOrderByCreatedDateDesc(employeeInfoEntity, "SAM"));
        model.addAttribute("teamleadloan",
                peopleAllocationLogicRepository.findFirstByTeamlead(employeeInfoEntity));
        model.addAttribute("rfd", dnoteMenuService.getAllActive());
        model.addAttribute("subrfd", dnSubMenu1Service.getActiveList());
        model.addAttribute("agencyloan", agencyService.getActiveList());
        return "samd/dashboard/loan/teamleader/main";
    }


    //    SAMD Manager
    private String samLoanManager(HttpSession session, Model model, EmployeeInfoEntity employeeInfoEntity,
                                  Date startDate, Date endDate) {
        model.addAttribute("employee", employeeInfoEntity);
        model.addAttribute("loginTime", new Date());

        List<DPDBucketEntity> activeList = dpdBucketService.getActiveList();
        double monitoringBucket = 0.0;

        List<PeopleAllocationLogicInfo> samPeopleAllocationLogicInfoList =
                peopleAllocationLogicRepository.getSamPeopleAllocationLogicInfoListByManagerId(employeeInfoEntity.getId());


        List<SamLoanAccountDistribution> loanDist = getLoanDistributionByEmp(employeeInfoEntity);
        Duration day;

//        for (SamLoanAccountDistribution distributionInfo : loanDist) {
//            LoanPtp loanPtp = new LoanPtp();
//            try {
//                loanPtp = dashboardService.getLoanPtpByCustomer(distributionInfo.getLoanAccountBasicInfo().getCustomer().getId()).get(0);
//            } catch (Exception e) {
//
//            }
//
//            distributionInfo.setLoanPtp(loanPtp);
//
//
//            Date monthEndDate = dateUtils.getMonthEndDate();
//            Date monthOpenDate = distributionInfo.getCreatedDate();
//
//            LocalDate localDate1 = monthOpenDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//            LocalDate localDate2 = monthEndDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//
//
//            double saveAmount;
//            double MO_DPD;
//            double dayDiff;
//            double backAmount;
//
//            day = Duration.between(localDate1.atStartOfDay(), localDate2.atStartOfDay());
//
//            //long ld=day.toDays();
//            dayDiff = (double) day.toDays();
//            MO_DPD = distributionInfo.getDpd();
//            if (distributionInfo.getDpdBucket().toUpperCase().equals("X") || distributionInfo.getDpdBucket().toUpperCase().equals("INTERIM")) {
//                saveAmount = ((MO_DPD + (dayDiff - 29)) * distributionInfo.getEmiAmount()) / 30;
//                distributionInfo.setBackAmount(0);
//            } else {
//                double highestDpdRange = Double.parseDouble(distributionInfo.getDpdBucket()) + 29;
//                saveAmount = ((MO_DPD + (dayDiff - highestDpdRange)) * distributionInfo.getEmiAmount()) / 30;
//
//                double highestDpdRangeBack = Double.parseDouble(distributionInfo.getDpdBucket()) - 1;
//                backAmount = ((MO_DPD + dayDiff - highestDpdRangeBack) * distributionInfo.getEmiAmount()) / 30;
//
//                distributionInfo.setBackAmount(backAmount);
//
//            }
//            //distributionInfo.setBackAmount(backAmount);
//            if (saveAmount > 0)
//                distributionInfo.setSaveAmount(saveAmount);
//            else
//                distributionInfo.setSaveAmount(0);
//        }

        //supervisor detail
        List<EmployeeInfoEntity> supervisorList = getSuperVisorList(samPeopleAllocationLogicInfoList);
        List<TeamDetailModel> supervisorDetail = new ArrayList<>();
        for (EmployeeInfoEntity supervisor : supervisorList) {
            List<EmployeeInfoEntity> dealerForSupervisor = new ArrayList<>();
            for (PeopleAllocationLogicInfo allocationLogic : samPeopleAllocationLogicInfoList) {
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
        List<EmployeeInfoEntity> teamLeadList = getTeamLeadList(samPeopleAllocationLogicInfoList);

        for (EmployeeInfoEntity teamLead : teamLeadList) {
            List<EmployeeInfoEntity> dealerForTeamLead = new ArrayList<>();
            for (PeopleAllocationLogicInfo allocationLogic : samPeopleAllocationLogicInfoList) {
                if (allocationLogic.getTeamlead().getPin().equals(teamLead.getPin())) {
                    EmployeeInfoEntity dealer = allocationLogic.getDealer();
                    dealerForTeamLead.add(dealer);
                }
            }
            teamLeadDetail.addAll(getTeamLeadOrSuperVisorDetailListLoan(dealerForTeamLead, loanDist, teamLead));
        }
        model.addAttribute("teamLeadDetail", teamLeadDetail);

        //dealer detail
        List<EmployeeInfoEntity> dealerList = getDealerList(samPeopleAllocationLogicInfoList);
        List<TeamDetailModel> dealerDetail = getDealerDetailListLoan(dealerList, loanDist);
        model.addAttribute("dealerDetail", dealerDetail);

        // user detail
        long userTotalAcc = 0;
        Double userTotalOutStanding = 0D;
        for (TeamDetailModel detailModel : dealerDetail) {
            userTotalAcc = userTotalAcc + detailModel.getNoOfAcc();
            userTotalOutStanding = userTotalOutStanding + detailModel.getOutstanding();
        }
        model.addAttribute("userTotalAcc", userTotalAcc);
        model.addAttribute("userTotalOutStanding", userTotalOutStanding);

        // employee status manager
        List<EmployeeInfoEntity> onBoardEmployeeList = new ArrayList<>();
        List<EmployeeInfoEntity> onLeaveEmployeeList = new ArrayList<>();
        List<EmployeeInfoEntity> onLeaveTodayEmployeeList = new ArrayList<>();

        int onBoard = 0, onLeave = 0, onLeaveToday = 0;

        for (EmployeeInfoEntity emp : dealerList) {
            EmployeeStatusManagerEntity empStMan = employeeStatusManagerDao.getEmpLastStatById(emp.getId());
            if (empStMan.getStartDate() == null || empStMan.getEndDate() == null) {
//                            onBoardEmployeeList.add(emp);
                onBoard++;
                continue;
            }
            if (DateUtils.isSameDay(empStMan.getStartDate(), empStMan.getEndDate()) &&
                    DateUtils.isSameDay(empStMan.getStartDate(), Calendar.getInstance().getTime())) {
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
            if (DateUtils.isSameDay(empStMan.getStartDate(), empStMan.getEndDate()) &&
                    DateUtils.isSameDay(empStMan.getStartDate(), Calendar.getInstance().getTime())) {
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
            if (DateUtils.isSameDay(empStMan.getStartDate(), empStMan.getEndDate()) &&
                    DateUtils.isSameDay(empStMan.getStartDate(), Calendar.getInstance().getTime())) {
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

        model.addAttribute("noOfEmployee", supervisorDetail.size() +
                teamLeadDetail.size() + dealerDetail.size());

        model.addAttribute("onLeaveEmp", onLeave);
        model.addAttribute("onLeaveTodayEmp", onLeaveToday);
        model.addAttribute("onBoardEmp", onBoard);


        session.setAttribute("loanDistributionList", loanDist);

        List<LateReasonExplainInfo> lateReasonExplainInfos =
                lateReasonRepository.findByCreatedDateIsBetweenAndUserOrderByCreatedDateDesc(
                        startDate, endDate, employeeInfoEntity.getUser());
        model.addAttribute("lateReason", lateReasonExplainInfos);

        model.addAttribute("unit", session.getAttribute("unit"));

        model.addAttribute("productList", productTypeService.getAllActive());
        model.addAttribute("dpdBucket", activeList);
        model.addAttribute("managerloan", peopleAllocationLogicRepository.findFirstByManager(employeeInfoEntity));
        model.addAttribute("peopleAllocationLogic",
                peopleAllocationLogicRepository.findFirstByDealerAndUnitOrderByCreatedDateDesc(
                        employeeInfoEntity, "SAM"));
        model.addAttribute("rfd", dnoteMenuService.getAllActive());
        model.addAttribute("subrfd", dnSubMenu1Service.getActiveList());
        model.addAttribute("agencyloan", agencyService.getActiveList());
        return "samd/dashboard/loan/manager/main";
    }

    private String samLoanSupervisor(HttpSession session, Model model, EmployeeInfoEntity employeeInfoEntity,
                                     List<PeopleAllocationLogicInfo> allocationLogicList, Date startDate, Date endDate) {
        model.addAttribute("employee", employeeInfoEntity);
        model.addAttribute("loginTime", new Date());
        List<DPDBucketEntity> activeList = dpdBucketService.getActiveList();
        Set<String> locations = new HashSet<>();


        model.addAttribute("monitoringBuckets", activeList);

        List<SamLoanAccountDistribution> loanDist = getLoanDistributionByEmp(employeeInfoEntity);
        Duration day;

        for (SamLoanAccountDistribution distributionInfo : loanDist) {
            Date monthEndDate = dateUtils.getMonthEndDate();
            Date monthOpenDate = distributionInfo.getCreatedDate();

            LocalDate localDate1 = monthOpenDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate localDate2 = monthEndDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();


            double saveAmount;
            double MO_DPD;
            double dayDiff;
            double backAmount;

            day = Duration.between(localDate1.atStartOfDay(), localDate2.atStartOfDay());

            //long ld=day.toDays();
            dayDiff = (double) day.toDays();
            MO_DPD = distributionInfo.getDpd();
            if (distributionInfo.getDpdBucket().toUpperCase().equals("X") || distributionInfo.getDpdBucket().toUpperCase().equals("INTERIM")) {
                saveAmount = ((MO_DPD + (dayDiff - 29)) * distributionInfo.getEmiAmount()) / 30;
                distributionInfo.setBackAmount(0);
            } else {
                double highestDpdRange = Double.parseDouble(distributionInfo.getDpdBucket()) + 29;
                saveAmount = ((MO_DPD + (dayDiff - highestDpdRange)) * distributionInfo.getEmiAmount()) / 30;

                double highestDpdRangeBack = Double.parseDouble(distributionInfo.getDpdBucket()) - 1;
                backAmount = ((MO_DPD + dayDiff - highestDpdRangeBack) * distributionInfo.getEmiAmount()) / 30;

                distributionInfo.setBackAmount(backAmount);

            }
            //distributionInfo.setBackAmount(backAmount);
            if (saveAmount > 0)
                distributionInfo.setSaveAmount(saveAmount);
            else
                distributionInfo.setSaveAmount(0);
            locations.add(distributionInfo.getLoanAccountBasicInfo().getLocation());
        }

        //team lead detail
        List<TeamDetailModel> teamLeadDetail = new ArrayList<>();
        List<EmployeeInfoEntity> teamLeadList = getTeamLeadList(allocationLogicList);
        int totalEmployee = 0;

        for (EmployeeInfoEntity teamLead : teamLeadList) {
            List<EmployeeInfoEntity> dealerForTeamLead = new ArrayList<>();
            for (PeopleAllocationLogicInfo allocationLogic : allocationLogicList) {
                if (allocationLogic.getTeamlead().getPin().equals(teamLead.getPin())) {
                    EmployeeInfoEntity dealer = allocationLogic.getDealer();
                    dealerForTeamLead.add(dealer);
                }
            }
            teamLeadDetail.addAll(getTeamLeadOrSuperVisorDetailListLoan(dealerForTeamLead, loanDist, teamLead));
            totalEmployee += dealerForTeamLead.size();
        }
        model.addAttribute("totalEmployee", totalEmployee);
        model.addAttribute("teamLeadDetail", teamLeadDetail);
        model.addAttribute("totalLocation", locations);

        //dealer detail
        List<EmployeeInfoEntity> dealerList = getDealerList(allocationLogicList);
        List<TeamDetailModel> dealerDetail = getDealerDetailListLoan(dealerList, loanDist);
        model.addAttribute("dealerDetail", dealerDetail);


        //user total
        long userTotalAcc = 0;
        Double userTotalOutStanding = 0D;
        for (TeamDetailModel detailModel : dealerDetail) {
            userTotalAcc = userTotalAcc + detailModel.getNoOfAcc();
            userTotalOutStanding = userTotalOutStanding + detailModel.getOutstanding();
        }
        model.addAttribute("userTotalAcc", userTotalAcc);
        model.addAttribute("userTotalOutStanding", BigDecimal.valueOf(userTotalOutStanding));


        session.setAttribute("loanDistributionList", loanDist);

        List<LateReasonExplainInfo> lateReasonExplainInfos =
                lateReasonRepository.findByCreatedDateIsBetweenAndUserOrderByCreatedDateDesc(
                        startDate, endDate, employeeInfoEntity.getUser());

        // TODO: Should be common for all users
        httpSessionUtils.setEmployeeSessionAttributes(session, employeeInfoEntity, lateReasonExplainInfos);

        model.addAttribute("lateReason", lateReasonExplainInfos);
        model.addAttribute("peopleAllocationLogic",
                peopleAllocationLogicRepository.findFirstByDealerAndUnitOrderByCreatedDateDesc(
                        employeeInfoEntity, "SAM"));
        return "samd/dashboard/loan/supervisor/main";
    }

    private String samLoanDealer(HttpSession session, Model model, EmployeeInfoEntity employeeInfoEntity,
                                 Date startDate, Date endDate) {
        model.addAttribute("employee", employeeInfoEntity);
        model.addAttribute("loginTime", new Date());

//                    List<DPDBucketEntity> activeList = dpdBucketService.getActiveList();


        List<SamLoanAccountDistribution> loanDist = getLoanDistributionByEmp(employeeInfoEntity);
        double totalOutstanding = 0;
        double totalMindue = 0;
        double performanceAvg = 0;

        List<PARaccountRuleLoanEntity> paRaccountRuleLoanList = paRaccountRuleLoanService.getActiveList();
        List<NPLAccountRuleEntity> nplAccountRuleList = nplAccountRuleService.getActiveList();
        Duration day;

        for (SamLoanAccountDistribution distributionInfo : loanDist) {
            totalOutstanding += distributionInfo.getOutStanding() == null ? 0 : Double.valueOf(distributionInfo.getOutStanding());
            totalMindue += distributionInfo.getOpeningOverDue() == null ? 0 : distributionInfo.getOpeningOverDue();

            // Todo: Update save and back amount logic when provided by UCBL
            distributionInfo.setSaveAmount(distributionInfo.getEmiAmount());
            distributionInfo.setBackAmount(distributionInfo.getEmiAmount() + 1);
        }

        model.addAttribute("totalAccounts", loanDist != null ? loanDist.size() : 0);
        model.addAttribute("totalOutstanding", totalOutstanding);
        model.addAttribute("totalMindue", totalMindue);
        model.addAttribute("performance", performanceAvg);

        model.addAttribute("accountList", loanDist);
        session.setAttribute("loanDistributionList", loanDist);
        List<LateReasonExplainInfo> lateReasonExplainInfos =
                lateReasonRepository.findByCreatedDateIsBetweenAndUserOrderByCreatedDateDesc(
                        startDate, endDate, employeeInfoEntity.getUser());
        model.addAttribute("lateReason", lateReasonExplainInfos);

//                    TODO: Should be common for all users
        httpSessionUtils.setEmployeeSessionAttributes(session, employeeInfoEntity, lateReasonExplainInfos);

        model.addAttribute("peopleAllocationLogic",
                peopleAllocationLogicRepository.findFirstByDealerAndUnitOrderByCreatedDateDesc(
                        employeeInfoEntity, "SAM"));
        model.addAttribute("caseTypes", caseTypeService.getEnabledCaseTypeDtoList());
        model.addAttribute("caseStaus", caseStatusService.findAll());

        return "samd/dashboard/loan/dealer/main";
    }

    List<SamLoanAccountDistribution> getLoanDistributionByEmp(EmployeeInfoEntity employeeInfoEntity) {
        List<PeopleAllocationLogicInfo> allocationList = new ArrayList<>();
        List<SamLoanAccountDistribution> loanDist = new ArrayList<>();

        if (!employeeInfoEntity.getDesignation().getName().equals("Dealer")) {
            allocationList = dashboardService.getAllDealerList(employeeInfoEntity.getId().toString(),
                    employeeInfoEntity.getDesignation().getName().toUpperCase(), "SAM Loan");

            for (PeopleAllocationLogicInfo logic : allocationList) {
                List<SamLoanAccountDistribution> temp = samdDashboardService.getSamAccountListByUser(logic.getDealer().getPin());
                loanDist.addAll(temp);
            }

        } else {
            loanDist = samdDashboardService.getSamAccountListByUser(employeeInfoEntity.getPin());
        }

        return loanDist;
    }

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

    List<TeamDetailModel> getTeamLeadOrSuperVisorDetailListLoan(List<EmployeeInfoEntity> dealerList,
                                                                List<SamLoanAccountDistribution> loanDist,
                                                                EmployeeInfoEntity emp) {
        List<TeamDetailModel> teamDetailModelList = new ArrayList<>();
        TeamDetailModel detailModel = new TeamDetailModel();
        detailModel.setDesignation(emp.getDesignation().getName());
        detailModel.setDealerName(emp.getUser().getFirstName());
        detailModel.setDealerPin(emp.getPin());

        for (EmployeeInfoEntity dealer : dealerList) {
            List<SamLoanAccountDistribution> checkedItem = new ArrayList<>();
            for (SamLoanAccountDistribution distributionInfo : loanDist) {
                if (distributionInfo.getDealerPin().equals(dealer.getPin())) {
                    detailModel.setNoOfAcc(detailModel.getNoOfAcc() + 1);
                    detailModel.setOutstanding(detailModel.getOutstanding() +
                            Double.parseDouble(distributionInfo.getOutStanding()));

                    checkedItem.add(distributionInfo);
                }
            }
            loanDist.remove(checkedItem);
        }
        teamDetailModelList.add(detailModel);
        return teamDetailModelList;
    }

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

    List<TeamDetailModel> getDealerDetailListLoan(List<EmployeeInfoEntity> dealerList,
                                                  List<SamLoanAccountDistribution> cardDist) {
        List<TeamDetailModel> teamDetailModelList = new ArrayList<>();

        for (EmployeeInfoEntity dealer : dealerList) {
            TeamDetailModel detailModel = new TeamDetailModel();
            detailModel.setDesignation("Dealer");
            detailModel.setDealerName(dealer.getUser().getFirstName());
            detailModel.setDealerPin(dealer.getPin());

            List<SamLoanAccountDistribution> checkedItem = new ArrayList<>();
            for (SamLoanAccountDistribution distributionInfo : cardDist) {
                if (distributionInfo.getDealerPin().equals(dealer.getPin())) {
                    detailModel.setNoOfAcc(detailModel.getNoOfAcc() + 1);
                    detailModel.setOutstanding(detailModel.getOutstanding() +
                            Double.parseDouble(distributionInfo.getOutStanding()));

                    checkedItem.add(distributionInfo);
                }
            }
            teamDetailModelList.add(detailModel);
            cardDist.remove(checkedItem);
        }

        return teamDetailModelList;
    }


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

    List<CardAccountDistributionInfo> getCardDistributionByEmp(EmployeeInfoEntity employeeInfoEntity) {

        List<CardAccountDistributionInfo> distributionList = new ArrayList<>();

        List<PeopleAllocationLogicInfo> allocationList = new ArrayList<>();
        List<String> tempDealerList = new ArrayList<>();


        if (!employeeInfoEntity.getDesignation().getName().toUpperCase().equals("DEALER")) {
            allocationList = dashboardService.getAllDealerList(employeeInfoEntity.getId().toString(),
                    employeeInfoEntity.getDesignation().getName(), "SAM Card");

            for (PeopleAllocationLogicInfo logic : allocationList) {
                Optional<EmployeeInfoEntity> dealer = Optional.ofNullable(logic.getDealer());
                if (dealer.isPresent()) {
                    if (!tempDealerList.contains(dealer.get().getPin())) {
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

    List<TeamDetailModel> getLoanTeamDetail(List<SamLoanAccountDistribution> loanDist, EmployeeInfoEntity employeeInfoEntity) {
        List<TeamDetailModel> teamDetailList = new ArrayList<>();

        List<PeopleAllocationLogicInfo> allocationList =
                dashboardService.getAllDealerList(employeeInfoEntity.getId().toString(),
                        employeeInfoEntity.getDesignation().getName().toUpperCase(), "SAM");

        for (PeopleAllocationLogicInfo allocationLogic : allocationList) {
            TeamDetailModel detailModel = new TeamDetailModel();
            detailModel.setDesignation("Dealer");
            detailModel.setDealerPin(allocationLogic.getDealer().getPin());
            detailModel.setDealerName(allocationLogic.getDealer().getUser().getFirstName() + ' ' +
                    allocationLogic.getDealer().getUser().getLastName());
            //detailModel.setOutstanding(0d);
            for (SamLoanAccountDistribution distributionInfo : loanDist) {

                if (allocationLogic.getDealer().getPin().equals(distributionInfo.getDealerPin())) {
                    detailModel.setNoOfAcc(detailModel.getNoOfAcc() + 1);
                    detailModel.setOutstanding(detailModel.getOutstanding() +
                            Double.parseDouble(distributionInfo.getOutStanding()));
                }
            }
            teamDetailList.add(detailModel);

        }
        return teamDetailList;

    }

    List<TeamDetailModel> getDealerDetailListCard(List<EmployeeInfoEntity> dealerList,
                                                  List<CardAccountDistributionInfo> cardDist) {
        List<TeamDetailModel> teamDetailModelList = new ArrayList<>();

        for (EmployeeInfoEntity dealer : dealerList) {
            TeamDetailModel detailModel = new TeamDetailModel();
            detailModel.setDesignation("Dealer");
            if (dealer != null) {
                detailModel.setDealerName(dealer.getUser().getFirstName());
                detailModel.setDealerPin(dealer.getPin());
            }

            List<CardAccountDistributionInfo> checkedItem = new ArrayList<>();
            for (CardAccountDistributionInfo distributionInfo : cardDist) {
                if (dealer != null) {
                    if (distributionInfo.getDealerPin().equals(dealer.getPin())) {
                        detailModel.setNoOfAcc(detailModel.getNoOfAcc() + 1);
                        detailModel.setOutstanding(detailModel.getOutstanding() +
                                Double.parseDouble(distributionInfo.getOutstandingAmount()));

                        checkedItem.add(distributionInfo);
                    }
                }
            }
            teamDetailModelList.add(detailModel);
            cardDist.remove(checkedItem);
        }

        return teamDetailModelList;
    }

    List<TeamDetailModel> getTeamLeadOrSuperVisorDetailListCard(List<EmployeeInfoEntity> dealerList,
                                                                List<CardAccountDistributionInfo> cardDist, EmployeeInfoEntity emp) {
        List<TeamDetailModel> teamDetailModelList = new ArrayList<>();
        TeamDetailModel detailModel = new TeamDetailModel();
        detailModel.setDesignation(emp.getDesignation().getName());
        detailModel.setDealerName(emp.getUser().getFirstName());
        detailModel.setDealerPin(emp.getPin());
        List<String> strings = new ArrayList<>();

        for (EmployeeInfoEntity dealer : dealerList) {
            if (!strings.contains(dealer.getPin())) {
                strings.add(dealer.getPin());
                List<CardAccountDistributionInfo> checkedItem = new ArrayList<>();
                for (CardAccountDistributionInfo distributionInfo : cardDist) {
                    if (distributionInfo.getDealerPin().equals(dealer.getPin())) {
                        detailModel.setNoOfAcc(detailModel.getNoOfAcc() + 1);
                        detailModel.setOutstanding(detailModel.getOutstanding() +
                                Double.parseDouble(distributionInfo.getOutstandingAmount()));
                        checkedItem.add(distributionInfo);
                    }
                }
                cardDist.remove(checkedItem);
            }


        }
        teamDetailModelList.add(detailModel);
        return teamDetailModelList;
    }

    //method for SAMD advanced search
    @GetMapping(value = "/advanced-search")
    @ResponseBody
    public List<AdvancedSearchDataModel> getSamdAdvancedSearchData(@RequestParam("payload") String payloadString) throws Exception {
        payloadString = payloadString.replace("&quot;", "\"");
        System.out.println("==========================> "+payloadString);
        AdvancedSearchPayload payload = new ObjectMapper().readValue(payloadString, AdvancedSearchPayload.class);

        return samdDashboardService.getSamdAdvancedSearchData(payload);
    }
}
