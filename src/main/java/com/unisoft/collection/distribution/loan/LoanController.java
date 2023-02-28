package com.unisoft.collection.distribution.loan;


import com.unisoft.collection.allocationLogic.PeopleAllocationLogicRepository;
import com.unisoft.collection.distribution.agencyAllocation.loan.LoanAgencyDistributionInfo;
import com.unisoft.collection.distribution.agencyAllocation.loan.LoanAgencyDistributionRepository;
import com.unisoft.collection.distribution.loan.loanAccount.LoanAccountService;
import com.unisoft.collection.distribution.loan.loanAccountBasic.LoanAccountBasicInfo;
import com.unisoft.collection.distribution.loan.loanAccountBasic.LoanAccountBasicService;
import com.unisoft.collection.distribution.loan.loanAccountDistribution.LoanAccountDistributionInfo;
import com.unisoft.collection.distribution.loan.loanAccountDistribution.LoanAccountDistributionService;
import com.unisoft.collection.distribution.loan.loanApi.LoanApiPayload;
import com.unisoft.collection.settings.agency.AgencyService;
import com.unisoft.customerloanprofile.letterinformation.LetterInformation;
import com.unisoft.customerloanprofile.letterinformation.LetterInformationRepository;
import com.unisoft.retail.loan.dataEntry.CustomerUpdate.accountInformation.AccountInformationEntity;
import com.unisoft.retail.loan.dataEntry.CustomerUpdate.accountInformationRepository.AccountInformationRepository;
import com.unisoft.retail.loan.dataEntry.CustomerUpdate.accountInformationService.AccountInformationService;
import com.unisoft.utillity.DateUtils;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/distribution/loan/")
public class LoanController {

    private final LoanAccountBasicService loanAccountBasicService;

    private final LoanAccountDistributionService loanAccountDistributionService;

    private final LoanAccountService loanAccountService;

    private final LoanAgencyDistributionRepository loanAgencyDistributionRepository;

    private final LoanAccountDistributionRepository loanAccountDistributionRepository;

    private final AgencyService agencyService;

    private final PeopleAllocationLogicRepository peopleAllocationLogicRepository;

    private final LetterInformationRepository letterInformationRepository;

    private final LoanDistributionService loanDistributionService;

    private final DateUtils dateUtils;

    @Autowired
    private AccountInformationRepository accountInformationRepository;

    @Autowired
    AccountInformationService accountInformationService;

    @GetMapping(value = "/dealerAllocationList")
    public String dealerList(Model model,HttpSession session){

        Date startDate = dateUtils.getDateOfCurrentMonth(1);
        Date endDate = dateUtils.getLocalMonthEndDate();

        List<AllocationDetails> accDetailsList = loanAccountDistributionService.findCurrentMonthAllocationDistributionList(startDate,endDate);
        model.addAttribute("accountDistributionInfoList",accDetailsList);

        return "collection/distribution/loan/dealerList";
    }

    @GetMapping("list")
    public String list(Model model, HttpSession session) {

        Date startDate = dateUtils.getDateOfCurrentMonth(1);
        Date endDate = dateUtils.getLocalMonthEndDate();

        List<LoanAccountDistributionInfo> loanAccountDistributionInfos
                = loanAccountDistributionService.findCurrentMonthDistributionList();
        List<LoanAgencyDistributionInfo> loanAgencyDistributionInfos =
                loanAgencyDistributionRepository.findByCreatedDateGreaterThanEqualAndCreatedDateLessThanEqual(startDate, endDate);

//        List<AllocationDetails> accDetailsList = loanAccountDistributionService.findCurrentMonthAllocationDistributionList(startDate,endDate);

        List<LoanViewModel> loanViewModels = new ArrayList<>();

        for (LoanAccountDistributionInfo loanAccountDistributionInfo : loanAccountDistributionInfos) {
            List<LoanAgencyDistributionInfo> collectedLoanAgencyDistributionInfos = loanAgencyDistributionInfos
                    .stream()
                    .filter(loanAgencyDistributionInfo ->
                            loanAccountDistributionInfo.getLoanAccountBasicInfo()
                                    .getId()
                                    .equals(loanAgencyDistributionInfo.getLoanAccountBasicInfo().getId()))
                    .collect(Collectors.toList());

            LoanViewModel loanViewModel = new LoanViewModel();

            if (collectedLoanAgencyDistributionInfos.size() == 0) {
                //LoanAccountBasicInfo
                LoanViewModel loanViewModelForBasicInfo = getLoanViewModelForBasicInfo(loanAccountDistributionInfo, loanViewModel);
                loanViewModelForBasicInfo.setMonirotingStatus(LoanDualEnum.SINGLE.name());
                loanViewModels.add(loanViewModelForBasicInfo);

            } else {
                if (collectedLoanAgencyDistributionInfos.size() > 1) {
                    Collections.sort(collectedLoanAgencyDistributionInfos, new Comparator<LoanAgencyDistributionInfo>() {
                        @Override
                        public int compare(LoanAgencyDistributionInfo o1, LoanAgencyDistributionInfo o2) {
                            if (o1.getCreatedDate() == null || o2.getCreatedDate() == null)
                                return 0;
                            return o1.getCreatedDate().compareTo(o2.getCreatedDate());
                        }
                    });

                    int index = collectedLoanAgencyDistributionInfos.size() - 1;

                    if (loanAccountDistributionInfo.getCreatedDate().compareTo(collectedLoanAgencyDistributionInfos.get(index).getCreatedDate()) > 0) {
                        //LoanAccountBasicInfo
                        LoanViewModel loanViewModelForBasicInfo = getLoanViewModelForBasicInfo(loanAccountDistributionInfo, loanViewModel);
                        loanViewModels.add(loanViewModelForBasicInfo);
                    } else if (loanAccountDistributionInfo.getCreatedDate().compareTo(collectedLoanAgencyDistributionInfos.get(index).getCreatedDate()) < 0) {
                        LoanAgencyDistributionInfo loanAgencyDistributionInfo = collectedLoanAgencyDistributionInfos.get(index);
                        LoanViewModel loanViewModelFormLoanAgencyDistributionInfo = getLoanViewModelFormLoanAgencyDistributionInfo(loanViewModel, loanAgencyDistributionInfo);
                        if (loanAgencyDistributionInfo.getLoanDualEnum().equals(LoanDualEnum.DUAL)) {
                            LoanAccountDistributionInfo byLoanAccountBasicInfo = loanAccountDistributionRepository.findFirstByLoanAccountBasicInfoOrderByCreatedDateDesc(loanAgencyDistributionInfo.getLoanAccountBasicInfo());
                            loanViewModel.setDelaerName(byLoanAccountBasicInfo.getDealerName());
                            loanViewModel.setMonirotingStatus(LoanDualEnum.DUAL.name());
                        }
                        loanViewModels.add(loanViewModelFormLoanAgencyDistributionInfo);

                    }

                } else {
                    if (loanAccountDistributionInfo.getCreatedDate().compareTo(collectedLoanAgencyDistributionInfos.get(0).getCreatedDate()) > 0) {
                        //LoanAccountBasicInfo
                        LoanViewModel loanViewModelForBasicInfo = getLoanViewModelForBasicInfo(loanAccountDistributionInfo, loanViewModel);
                        loanViewModels.add(loanViewModelForBasicInfo);
                    } else if (loanAccountDistributionInfo.getCreatedDate().compareTo(collectedLoanAgencyDistributionInfos.get(0).getCreatedDate()) < 0) {
                        LoanAgencyDistributionInfo loanAgencyDistributionInfo = collectedLoanAgencyDistributionInfos.get(0);

                        LoanViewModel loanViewModelFormLoanAgencyDistributionInfo = getLoanViewModelFormLoanAgencyDistributionInfo(loanViewModel, loanAgencyDistributionInfo);
                        if (loanAgencyDistributionInfo.getLoanDualEnum().equals(LoanDualEnum.DUAL)) {
                            LoanAccountDistributionInfo byLoanAccountBasicInfo = loanAccountDistributionRepository.findFirstByLoanAccountBasicInfoOrderByCreatedDateDesc(loanAgencyDistributionInfo.getLoanAccountBasicInfo());
                            loanViewModel.setDelaerName(byLoanAccountBasicInfo.getDealerName());
                            loanViewModel.setMonirotingStatus(LoanDualEnum.DUAL.name());
                        }
                        loanViewModels.add(loanViewModelFormLoanAgencyDistributionInfo);
                    }
                }

            }
        }

        Map distributionErrors = (Map) session.getAttribute("distributionErrors");
        if (distributionErrors != null) {
            session.removeAttribute("distributionErrors");
            model.addAttribute("distributionErrors", distributionErrors);
        }

        Gson gson = new Gson();
       // model.addAttribute("loanviewlist", loanViewModels);
        //model.addAttribute("loanviewlistJson", gson.toJson(loanViewModels));
        model.addAttribute("dealerList", peopleAllocationLogicRepository.findByUnitAndDistributionType("Loan", "Regular"));
        model.addAttribute("agencyList", agencyService.getAll());
        model.addAttribute("autoDistributionList",loanAccountDistributionService.findDistributionList());
        model.addAttribute("loanviewlist", loanAccountDistributionService.findDistributionList());
        model.addAttribute("loanviewlistJson", gson.toJson(loanAccountDistributionService.findDistributionList()));
//        model.addAttribute("accountDistributionInfoList",accDetailsList);

        return "collection/distribution/loan/loan";
    }

    @GetMapping("create")
    public String addLoan() {
        return "collection/distribution/loan/create";
    }

    @PostMapping("save")
    public String saveLoan(@RequestParam("file") MultipartFile multipartFile, HttpSession session) {
        Map errors = loanDistributionService.saveManuallyDistributedAccounts(multipartFile);
        session.setAttribute("distributionErrors", errors);
        return "redirect:/distribution/loan/list";
    }

    @PostMapping("dealer-wise-distribution")
    public String saveMannualDealerWiseDistribution(@Valid @RequestBody LoanApiPayload loanApiPayload, HttpSession session) {
        Map errors = loanDistributionService.saveMannualDealerWiseDistribution(loanApiPayload);
        session.setAttribute("distributionErrors", errors);
        return "redirect:/distribution/loan/list";
    }

    @GetMapping("createletter")
    public String addLetterInformation() {

        return "collection/distribution/loan/createletter";
    }

    @PostMapping("uploadletter")
    public String saveLetter(@RequestParam("file") MultipartFile multipartFile) {

        try {
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(multipartFile.getInputStream());
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);

            List<LetterInformation> letterInformations = new ArrayList<>();
            for (int i = 1; i < xssfSheet.getPhysicalNumberOfRows(); i++) {
                XSSFRow row = xssfSheet.getRow(i);


                if (row.getCell(0) != null && row.getCell(0).toString().length() > 1) {
                    Date letterSendDate = null;
                    Date letterReturnDate = null;

                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
                    letterSendDate = simpleDateFormat.parse(row.getCell(3).toString());
                    letterReturnDate = simpleDateFormat.parse(row.getCell(5).toString());

                    LetterInformation letterInformation = new LetterInformation();
                    letterInformation.setLetterSendDate(letterSendDate);
                    letterInformation.setLetterReturnDate(letterReturnDate);
                    letterInformation.setAccountNo(row.getCell(0).toString());
                    letterInformation.setAccountName(row.getCell(1).toString());
                    letterInformation.setRefNo(row.getCell(2).toString());
                    letterInformation.setLetterType(row.getCell(4).toString());
                    letterInformation.setLetterReason(row.getCell(6).toString());
                    letterInformation.setReciever(row.getCell(7).toString());
                    letterInformation.setRecieverName(row.getCell(8).toString());
                    letterInformations.add(letterInformation);

                }

            }

            letterInformationRepository.saveAll(letterInformations);
        } catch (IOException | ParseException e) {
            System.err.println(e.getMessage());
        }
        return "redirect:/";
    }

    // TODO: review the code
    public List<LoanViewModel> getLoanViewModels() {
        LocalDate today = LocalDate.now();
        LocalDate startDateOfMonth = today.withDayOfMonth(1);
        Date startDate = Date.from(startDateOfMonth.atStartOfDay(ZoneId.systemDefault()).toInstant());

        LocalDate endDateOfMonth = today.withDayOfMonth(today.lengthOfMonth());
        String endDateOfMonthString = endDateOfMonth.toString().trim();
        endDateOfMonthString = endDateOfMonthString + " 11:59 PM";

        Date endDate = null;
        try {
            endDate = new SimpleDateFormat("yyyy-MM-dd hh:mm a").parse(endDateOfMonthString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        List<LoanViewModel> loanViewModels = new ArrayList<>();

        List<LoanAccountDistributionInfo> loanAccountDistributionRepositoryByCreatedDateIsBetween
                = loanAccountDistributionRepository.
                findByCreatedDateIsBetweenAndSamAccountAndWriteOffAccountAndLatestOrderByCreatedDateDesc(startDate, endDate, "0", "0", "1");

        loanAccountDistributionRepositoryByCreatedDateIsBetween.forEach(loanAccountDistributionInfo -> {
            LoanViewModel loanViewModelForBasicInfo = getLoanViewModelForBasicInfo(loanAccountDistributionInfo, new LoanViewModel());
            loanViewModels.add(loanViewModelForBasicInfo);

        });


        return loanViewModels;

    }

    private LoanViewModel getLoanViewModelFormLoanAgencyDistributionInfo(LoanViewModel loanViewModel, LoanAgencyDistributionInfo loanAgencyDistributionInfo) {
        loanViewModel.setAccountNo(loanAgencyDistributionInfo.getLoanAccountBasicInfo().getAccountNo());
//        loanViewModel.setCustomerName(loanAgencyDistributionInfo.getLoanAccountBasicInfo().getAccountName());
//        loanViewModel.setClientId(loanAgencyDistributionInfo.getLoanAccountBasicInfo().getCustomer().getClientId());
        loanViewModel.setDpdBucket(loanAgencyDistributionInfo.getDpdBucket());
        loanViewModel.setAgencyName(loanAgencyDistributionInfo.getAgencyName());
        loanViewModel.setOutstandingAmount(loanAgencyDistributionInfo.getOutstanding());
        loanViewModel.setLocation(loanAgencyDistributionInfo.getLoanAccountBasicInfo().getLocation());
        return loanViewModel;
    }

    private LoanViewModel getLoanViewModelForBasicInfo(LoanAccountDistributionInfo loanAccountDistributionInfo,  LoanViewModel loanViewModel) {
//        LoanAccountInfo loanAccountInfo = loanAccountService.findByLoanAccountBasicId(
//                loanAccountDistributionInfo.getLoanAccountBasicInfo());

        //List<AccountInformationEntity> accountInformationEntity = accountInformationService.findAll();
                /*accountInformationService.findAll()*/

       /* for (int i = 0; i<accountInformationEntity.size(); i++){
            loanViewModel.getAccountNo();
            loanViewModel.setMobile(accountInformationEntity.get(i).getMobile());
    }*/



       // loanViewModel.setMobile(accountInformationEntity.getMobile());
        loanViewModel.setAccountNo(loanAccountDistributionInfo.getLoanAccountBasicInfo().getAccountNo().substring(0,13));
//        loanViewModel.setClientId(loanAccountDistributionInfo.getLoanAccountBasicInfo().getCustomer().getClientId());
        loanViewModel.setCustomerId(loanAccountDistributionInfo.getLoanAccountBasicInfo().getCustomer().getCustomerId());
        loanViewModel.setCustomerName(loanAccountDistributionInfo.getLoanAccountBasicInfo().getAccountName());
//        loanViewModel.setDpdBucket(loanAccountInfo.getDpbBucket());
        loanViewModel.setDpdBucket(loanAccountDistributionInfo.getDpdBucket());
        loanViewModel.setLocation(loanAccountDistributionInfo.getLoanAccountBasicInfo().getLocation());
//        loanViewModel.setOutstandingAmount(loanAccountInfo.getOutstandingPrincipal() + "");
        loanViewModel.setOutstandingAmount(loanAccountDistributionInfo.getOutStanding());
//        loanViewModel.setSupervisorName(loanAccountDistributionInfo.getSupervisorName());
//        loanViewModel.setDelaerName(loanAccountDistributionInfo.getDealerName());                    // get firstname by username from userservice and setdealername //
        loanViewModel.setDelaerPin(loanAccountDistributionInfo.getDealerPin());                       // get firstname by username from userservice and setdealername //

        return loanViewModel;
    }

    private LoanAccountDistributionInfo getLoanAccountDistributionInfo(LoanAccountBasicInfo loanAccountBasicInfo) {
        List<LoanAccountDistributionInfo> byLoanAccountBasicInfo = loanAccountDistributionService.findByLoanAccountBasicInfo(loanAccountBasicInfo);
        Collections.sort(byLoanAccountBasicInfo, new Comparator<LoanAccountDistributionInfo>() {
            @Override
            public int compare(LoanAccountDistributionInfo o1, LoanAccountDistributionInfo o2) {
                if (o1.getCreatedDate() == null || o2.getCreatedDate() == null)
                    return 0;
                return o1.getCreatedDate().compareTo(o2.getCreatedDate());
            }
        });
        int index = byLoanAccountBasicInfo.size() - 1;
        return byLoanAccountBasicInfo.get(index);
    }

    private LoanViewModelForSMS getLoanViewModelForSMS(LoanAccountDistributionInfo loanAccountDistributionInfo, LoanViewModelForSMS loanViewModel) {

        AccountInformationEntity accountInformationEntity = accountInformationRepository.getByLoanAccountNo(loanAccountDistributionInfo.getLoanAccountBasicInfo().getAccountNo());
        //accountInformationRepository.findByCustId(loanAccountDistributionInfo.getLoanAccountBasicInfo().getCustomer().getId());

        loanViewModel.setAccountNo(loanAccountDistributionInfo.getLoanAccountBasicInfo().getAccountNo());
        loanViewModel.setCustomerId(loanAccountDistributionInfo.getLoanAccountBasicInfo().getCustomer().getCustomerId());
        loanViewModel.setCustomerName(loanAccountDistributionInfo.getLoanAccountBasicInfo().getAccountName());
        loanViewModel.setMobileNo(accountInformationEntity.getMobile());
        loanViewModel.setProductName(accountInformationEntity == null?"":accountInformationEntity.getProductName());
        loanViewModel.setInstallmentAmount(loanAccountDistributionInfo.getEmiAmount().toString());
        loanViewModel.setNextEmiDate(loanAccountDistributionInfo.getEmiDueDate());
        try {
            loanViewModel.setCurrentMonth(this.getMonth(new Date()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return loanViewModel;
    }

    private String getMonth(Date date) throws ParseException{
        Date d = date;
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        String monthName = new SimpleDateFormat("MMMM").format(cal.getTime());
        return monthName;
    }

    public List<LoanViewModelForSMS> getLoanViewModelsForSms() {
        LocalDate today = LocalDate.now();
        LocalDate startDateOfMonth = today.withDayOfMonth(1);
        Date startDate = Date.from(startDateOfMonth.atStartOfDay(ZoneId.systemDefault()).toInstant());

        LocalDate endDateOfMonth = today.withDayOfMonth(today.lengthOfMonth());
        String endDateOfMonthString = endDateOfMonth.toString().trim();
        endDateOfMonthString = endDateOfMonthString + " 11:59 PM";

        Date endDate = null;
        try {
            endDate = new SimpleDateFormat("yyyy-MM-dd hh:mm a").parse(endDateOfMonthString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        List<LoanViewModelForSMS> loanViewModels = new ArrayList<>();

        List<LoanAccountDistributionInfo> loanAccountDistributionRepositoryByCreatedDateIsBetween
                = loanAccountDistributionRepository.
                findByCreatedDateIsBetweenAndSamAccountAndWriteOffAccountAndLatestOrderByCreatedDateDesc(startDate, endDate, "0", "0", "1");

        loanAccountDistributionRepositoryByCreatedDateIsBetween.forEach(loanAccountDistributionInfo -> {
            LoanViewModelForSMS loanViewModelForBasicInfo = getLoanViewModelForSMS(loanAccountDistributionInfo, new LoanViewModelForSMS());
            loanViewModels.add(loanViewModelForBasicInfo);
        });
        return loanViewModels;

    }


//    private LoanAccountInfo getLoanAccountInfo(LoanAccountBasicInfo loanAccountBasicInfo) {
//        return loanAccountService.findByLoanAccountBasicId(loanAccountBasicInfo);
//    }

//    public boolean isRowEmpty(Row row) {
//        for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
//            Cell cell = row.getCell(c);
//            if (cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK)
//                return false;
//        }
//        return true;
//    }

}
