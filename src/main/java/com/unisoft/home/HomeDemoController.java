package com.unisoft.home;

import com.unisoft.collection.KPI.Card.TargetByAmount.CardKPITargetByAmountEntity;
import com.unisoft.collection.KPI.Card.TargetByAmount.CardKPITargetByAmountService;
import com.unisoft.collection.allocationLogic.PeopleAllocationLogicInfo;
import com.unisoft.collection.allocationLogic.PeopleAllocationLogicRepository;
import com.unisoft.collection.dashboard.*;
import com.unisoft.collection.distribution.loan.loanAccountDistribution.LoanAccountDistributionInfo;
import com.unisoft.collection.settings.ageCode.AgeCodeEntity;
import com.unisoft.collection.settings.ageCode.AgeCodeService;
import com.unisoft.collection.settings.dpdBucket.DPDBucketEntity;
import com.unisoft.collection.settings.dpdBucket.DPDBucketService;
import com.unisoft.collection.settings.employee.EmployeeInfoEntity;
import com.unisoft.collection.settings.employee.EmployeeRepository;
import com.unisoft.collection.settings.lateReasonExplain.LateReasonExplainInfo;
import com.unisoft.collection.settings.lateReasonExplain.LateReasonExplainRepository;
import com.unisoft.collection.settings.officeTimeSetup.OfficeTimeSetupInfo;
import com.unisoft.collection.settings.officeTimeSetup.OfficeTimeSetupService;
import com.unisoft.collection.settings.productType.ProductTypeEntity;
import com.unisoft.collection.settings.productType.ProductTypeService;
import com.unisoft.retail.card.dataEntry.distribution.accountDistributionInfo.CardAccountDistributionInfo;
import com.unisoft.user.UserPrincipal;
import com.unisoft.utillity.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/teamleader/to/dealer")
public class HomeDemoController {
    private final EmployeeRepository employeeRepository;

    private final OfficeTimeSetupService officeTimeSetupService;

    private final DashboardService dashboardService;

    private final DPDBucketService dpdBucketService;

    private final LateReasonExplainRepository lateReasonRepository;

    private final PeopleAllocationLogicRepository peopleAllocationLogicRepository;

    private final ProductTypeService productTypeService;

    private final CardKpiAchDao cardKpiAchDao;

    private final AgeCodeService ageCodeService;

    private final CardBackendAccDetailService cardBackendAccDetailService;

    private final CardKPITargetByAmountService cardKPITargetByAmountService;

    private final DateUtils dateUtils;

    @GetMapping("/dashboard")
    public String showLandingPage(@RequestParam("id") String id, Principal principal,
                                  HttpSession session, Model model, HttpServletRequest request)
            throws ParseException {

        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        EmployeeInfoEntity employee = employeeRepository.findByPin(userPrincipal.getUsername());
        System.out.println(employee.getDesignation().getName());

        model.addAttribute("designationForExcel", employee.getDesignation().getName());

        Date startDate = dateUtils.getMonthStartDate();

        Date endDate = dateUtils.getMonthEndDate();

        EmployeeInfoEntity employeeInfoEntity = employeeRepository.findByPin(id);
        OfficeTimeSetupInfo timeSetupInfo = officeTimeSetupService.getOfficeTimeSetup();
        String format = LocalDate.now().toString() + " " + timeSetupInfo.getLogoutHour().toString();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        Date logoutTime = simpleDateFormat.parse(format);

        model.addAttribute("logoutTime", logoutTime);
        model.addAttribute("currentUser",employee);

        if (employeeInfoEntity.getUnit().contains("Loan")) {
//            List<PeopleAllocationLogicInfo> allocationLogicList = dashboardService.getAllDealerList(employeeInfoEntity.getId().toString(), employeeInfoEntity.getDesignation().getName(), "Loan");

            model.addAttribute("employee", employeeInfoEntity);
            model.addAttribute("loginTime", new Date());

            List<LoanAccountDistributionInfo> loanDist = getLoanDistributionByEmp(employeeInfoEntity);

            double totalOutstanding = 0;
            double totalMindue = 0;
            double performanceAvg = 0;

            for (LoanAccountDistributionInfo distributionInfo : loanDist) {
                totalOutstanding += Double.valueOf(distributionInfo.getOutStanding());
                totalMindue += distributionInfo.getOpeningOverDue();
            }

//                    model.addAttribute("accountList", loanDist);
            model.addAttribute("totalAccounts", loanDist != null ? loanDist.size() : 0);
            model.addAttribute("totalOutstanding", totalOutstanding);
            model.addAttribute("totalMindue", totalMindue);
            model.addAttribute("performance", performanceAvg);


            model.addAttribute("accountList", loanDist);
            session = request.getSession();
            session.setAttribute("loanDistributionList", loanDist);
            List<LateReasonExplainInfo> lateReasonExplainInfos =
                    lateReasonRepository.findByCreatedDateIsBetweenAndUserOrderByCreatedDateDesc(
                            startDate, endDate, employeeInfoEntity.getUser());
            model.addAttribute("lateReason", lateReasonExplainInfos);

            //model.addAttribute("agencyloan", agencyService.getActiveList());
            //model.addAttribute("productList", productTypeService.getAllActive());
            //model.addAttribute("dpdBucket", activeList);
            model.addAttribute("peopleAllocationLogic",
                    peopleAllocationLogicRepository.findFirstByDealerAndUnitOrderByCreatedDateDesc(employeeInfoEntity, "Loan"));
            //model.addAttribute("accountList", loanAccountDistributionRepository.findByCreatedDateIsBetweenAndDealerPin(startDate,endDate,employeeInfoEntity.getPin()));

            return "dashboard/loan/dealer/main";

        } else {

            model.addAttribute("employee", employeeInfoEntity);
            model.addAttribute("loginTime", new Date());

            List<DPDBucketEntity> activeList = dpdBucketService.getActiveList();

            List<DPDBucketEntity> x = activeList.stream().filter(d -> {
                char c = d.getBucketName().charAt(0);
                return (!(c >= 'A' && c <= 'Z'));
            }).collect(Collectors.toList());

            activeList.removeAll(x);
            activeList.sort(Comparator.comparing(DPDBucketEntity::getBucketName));
            x.sort(Comparator.comparingInt(o -> Integer.parseInt(o.getBucketName())));
            activeList.addAll(x);

            List<ProductTypeEntity> productTypeList = productTypeService.getAllActive();

            List<CardAccountDistributionInfo> cardAccountDist = getCardDistributionByEmp(employeeInfoEntity);


//                    List<AgeCodeEntity> ageCodeList=ageCodeService.getActiveList();
//                    AgeCodeEntity ageCode=new AgeCodeEntity();
//
//                    NPLAccountRuleCardEntity npLlogic=npLlogicSetupService.getNPL();
//                    PARAccountRuleCardEntity parLogic=parLogicSetupService.getPar();


            List<AgeCodeEntity> ageCodeList = ageCodeService.getActiveList();
            AgeCodeEntity ageCode = new AgeCodeEntity();

            String finAccNo = "";

            for (CardAccountDistributionInfo distributionInfo : cardAccountDist) {

                double regularAmnt = 0;
                double backAmnt = 0;
                double saveAmnt = 0;
                double nplRelAmount = 0;
                double parRelAmount = 0;

                double remSaveAmnt = 0;
                double remBackAmnt = 0;
                double remRegAmnt = 0;
                double remRawamnt = 0;
                double totalPayment = 0;

                CardBackendAccDetailsEntity cardBackendAccDetail =
                        cardBackendAccDetailService.getByAccNo(distributionInfo.getCardAccountBasicInfo().getCardNo(),
                                distributionInfo.getCreatedDate(), getEndDate());

                if (cardBackendAccDetail != null) {
                    regularAmnt = cardBackendAccDetail.getMoRegularAmnt();
                    backAmnt = cardBackendAccDetail.getMoBackAmnt();
                    saveAmnt = cardBackendAccDetail.getMoSaveAmnt();
                    nplRelAmount = cardBackendAccDetail.getMoNPLRelReqAmnt();
                    parRelAmount = cardBackendAccDetail.getMoPARRelReqAmnt();

                    for (AgeCodeEntity ageCodeEntity : ageCodeList) {
                        if (ageCodeEntity.getName().equals(distributionInfo.getAgeCode())) {
                            ageCode = ageCodeEntity;
                        }
                    }

                    //List<CardPaymentModel> cardDetailPaymentList = dashboardService.getCardPaymentAmntOnlyByCardNo(distributionInfo.getCardAccountBasicInfo().getCardNo(), distributionInfo.getCreatedDate());

                    CardKpiAchEntity kpiAchEntity = cardKpiAchDao.getByAccNo(distributionInfo.getCardAccountBasicInfo().getCardNo());


                    for (ProductTypeEntity productType : productTypeList) {
                        String temp = distributionInfo.getProductGroup();

                        if (temp.contains("."))
                            temp = temp.substring(0, temp.indexOf("."));
                        //System.err.println("CODE :"+temp);
                        distributionInfo.setProductGroup(temp);
                        if (productType.getProductGroupEntity().getCode().equals(temp)) {
                            distributionInfo.setProductType(productType);
                        }
                    }

                    CardKPITargetByAmountEntity kpiTarget = null;
                    try {
                        kpiTarget = cardKPITargetByAmountService
                                .getByProductAgeCodeAndDealerPin(distributionInfo.getProductType(), ageCode,
                                        distributionInfo.getDealerPin(), employeeInfoEntity.getLocation());

                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }

//                                if (kpiTarget != null)
//                                    distributionInfo.setCashCollection((kpiTarget.getRawCollectionTarget() / 100) * Double.parseDouble(distributionInfo.getOutstandingAmount()));
//                                else
//                                    distributionInfo.setCashCollection(0);

                    distributionInfo.setCashCollection(cardBackendAccDetail.getTargetRawCol());

                    for (ProductTypeEntity productType : productTypeList) {
                        String temp = distributionInfo.getProductGroup();
                        if (temp.contains("."))
                            temp = temp.substring(0, temp.indexOf("."));
                        //System.err.println("CODE :"+temp);
                        distributionInfo.setProductGroup(temp);
                        if (productType.getProductGroupEntity().getCode().equals(temp)) {
                            distributionInfo.setProductType(productType);
                        }
                    }

//                            if(cardDetailPaymentList.size()>0)
//                            {
//                                for (CardPaymentModel detailPaymentModel : cardDetailPaymentList) {
//                                    System.err.println("PAYMENT : "+lastPayment);
//                                    lastPayment = lastPayment + detailPaymentModel.getAmount();
//                                }
//                                distributionInfo.setLastPayment(lastPayment);
//                            }
                    if (kpiAchEntity != null)
                        totalPayment = kpiAchEntity.getTotalPayment();
                    else totalPayment = 0;
                    distributionInfo.setTotalPayment(totalPayment);
                    try {
                        distributionInfo.setCurrentMonthPayDueDate(cardBackendAccDetail.getCurrentMnthPayDueDate());
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(cardBackendAccDetail.getCurrentMnthPayDueDate());
                        cal.add(Calendar.DATE, 2);
                        distributionInfo.setDueDateWithGracePeriod(cal.getTime());
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }

                    remRegAmnt = distributionInfo.getMinDuePayment() - totalPayment;
                    remSaveAmnt = saveAmnt - totalPayment;
                    remBackAmnt = backAmnt - totalPayment;
                    remRawamnt = distributionInfo.getCashCollection() - totalPayment;

                    //System.err.println("view :b"+remBackAmnt+" s "+remSaveAmnt+" r "+remRegAmnt+" + rw "+remRawamnt);

                    distributionInfo.setSaveAmount(saveAmnt);
                    distributionInfo.setBackAmount(backAmnt);
                    distributionInfo.setNplRelAmnt(nplRelAmount);
                    distributionInfo.setParRelAmnt(parRelAmount);

                    if (remSaveAmnt > 0)
                        distributionInfo.setRemSaveAmount(remSaveAmnt);
                    else
                        distributionInfo.setRemSaveAmount(0);

                    if (remBackAmnt > 0)
                        distributionInfo.setRemBackAmount(remBackAmnt);
                    else
                        distributionInfo.setRemBackAmount(0);

                    if (remRegAmnt > 0)
                        distributionInfo.setRemRegularkAmount(remRegAmnt);
                    else
                        distributionInfo.setRemRegularkAmount(0);

                    if (remRawamnt > 0)
                        distributionInfo.setRemRawCollAmount(remRawamnt);
                    else
                        distributionInfo.setRemRawCollAmount(0);

                    distributionInfo.setMobileNo(cardBackendAccDetail.getMobileNo());

                }
            }


            session = request.getSession();
            session.setAttribute("cardDistributionList", cardAccountDist);

            List<LateReasonExplainInfo> lateReasonExplainInfos =
                    lateReasonRepository.findByCreatedDateIsBetweenAndUserOrderByCreatedDateDesc(startDate,
                            endDate, employeeInfoEntity.getUser());
            model.addAttribute("lateReason", lateReasonExplainInfos);
//
//                    model.addAttribute("productList",productTypeService.getAllActive());
//                    model.addAttribute("dpdBucket", activeList);
//                    model.addAttribute("agencycard",agencyService.getActiveList());
//                    model.addAttribute("agecode",ageCodeService.getActiveList());
            model.addAttribute("peopleAllocationLogic",
                    peopleAllocationLogicRepository.findFirstByDealerAndUnitOrderByCreatedDateDesc(employeeInfoEntity, "Card"));
            model.addAttribute("cardAccList", cardAccountDist);
            return "dashboard/card/dealer/main";

        }
    }

    List<LoanAccountDistributionInfo> getLoanDistributionByEmp(EmployeeInfoEntity employeeInfoEntity) {
        List<PeopleAllocationLogicInfo> allocationList = new ArrayList<>();
        List<LoanAccountDistributionInfo> loanDist = new ArrayList<>();

//        if (!employeeInfoEntity.getDesignation().getName().toUpperCase().equals("DEALER")) {
//            allocationList = dashboardService.getAllDealerList(employeeInfoEntity.getId().toString(), employeeInfoEntity.getDesignation().getName(), "Loan");
//
//            for (PeopleAllocationLogicInfo logic : allocationList) {
//                List<LoanAccountDistributionInfo> temp = dashboardService.getAccountListByUser(logic.getDealer().getPin());
//                loanDist.addAll(temp);
//            }
//
//        } else {
//
//        }
        loanDist = dashboardService.getAccountListByUser(employeeInfoEntity.getPin());

        return loanDist;
    }

    double getTotalDaysIMonth() {
        Calendar cal = Calendar.getInstance();
        int totalDays = cal.getActualMaximum(Calendar.DATE);

        return Double.parseDouble(Integer.toString(totalDays));
    }

    Date getEndDate() {
        Calendar cal = Calendar.getInstance();
        int totalDays = cal.getActualMaximum(Calendar.DATE);

        Calendar c2 = Calendar.getInstance();
        c2.set(Calendar.DAY_OF_MONTH, totalDays);
        c2.set(Calendar.HOUR_OF_DAY, 23);
        c2.set(Calendar.MINUTE, 59);
        c2.set(Calendar.SECOND, 59);
        c2.set(Calendar.MILLISECOND, 0);
        Date endDate = c2.getTime();

        return endDate;
    }

    List<CardAccountDistributionInfo> getCardDistributionByEmp(EmployeeInfoEntity employeeInfoEntity) {

        List<CardAccountDistributionInfo> distributionList = new ArrayList<>();

        List<PeopleAllocationLogicInfo> allocationList = new ArrayList<>();

//        if (!employeeInfoEntity.getDesignation().getName().toUpperCase().equals("DEALER")) {
//            allocationList = dashboardService.getAllDealerList(employeeInfoEntity.getId().toString(), employeeInfoEntity.getDesignation().getName(), "Card");
//
//            for (PeopleAllocationLogicInfo logic : allocationList) {
//                List<CardAccountDistributionInfo> temp = dashboardService.getListBYUserPinCard(logic.getDealer().getPin());
//                distributionList.addAll(temp);
//            }
//        } else {
//
//        }
        distributionList = dashboardService.getListBYUserPinCard(employeeInfoEntity.getPin());
        return distributionList;
    }
}
