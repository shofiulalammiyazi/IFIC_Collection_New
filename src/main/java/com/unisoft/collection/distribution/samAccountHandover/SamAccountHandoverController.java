package com.unisoft.collection.distribution.samAccountHandover;
/*
Created by   Islam at 8/1/2019
*/

import com.unisoft.dms.DmsFileSaver;
import com.unisoft.collection.allocationLogic.PeopleAllocationLogicInfo;
import com.unisoft.collection.allocationLogic.PeopleAllocationLogicRepository;
import com.unisoft.retail.card.dataEntry.distribution.accountOtherInfo.CardAccountOtherInfo;
import com.unisoft.retail.card.dataEntry.distribution.accountOtherInfo.CardAccountOtherService;
import com.unisoft.retail.card.dataEntry.distribution.accountDistributionInfo.CardAccountDistributionRepository;
import com.unisoft.retail.card.dataEntry.distribution.accountInfo.CardAccountInfo;
import com.unisoft.retail.card.dataEntry.distribution.accountInfo.CardAccountService;
import com.unisoft.retail.card.dataEntry.distribution.accountBasicInfo.CardAccountBasicInfo;
import com.unisoft.retail.card.dataEntry.distribution.accountBasicInfo.CardAccountBasicRepository;
import com.unisoft.retail.card.dataEntry.distribution.accountDistributionInfo.CardAccountDistributionInfo;
import com.unisoft.collection.distribution.loan.LoanAccountDistributionRepository;
import com.unisoft.collection.distribution.loan.loanAccount.LoanAccountInfo;
import com.unisoft.collection.distribution.loan.loanAccount.LoanAccountService;
import com.unisoft.collection.distribution.loan.loanAccountBasic.LoanAccountBasicInfo;
import com.unisoft.collection.distribution.loan.loanAccountBasic.LoanAccountBasicService;
import com.unisoft.collection.distribution.loan.loanAccountDistribution.LoanAccountDistributionInfo;
import com.unisoft.collection.distribution.loan.loanAccountOther.LoanAccountOtherInfo;
import com.unisoft.collection.distribution.loan.loanAccountOther.LoanAccountOtherService;
import com.unisoft.collection.settings.employee.EmployeeInfoEntity;
import com.unisoft.collection.settings.employee.EmployeeService;
import com.unisoft.customerbasicinfo.CustomerBasicInfoEntity;
import com.unisoft.customerbasicinfo.CustomerBasicInfoEntityRepository;
import com.unisoft.user.UserPrincipal;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping(value = "/collection/distribution/samaccount/")
public class SamAccountHandoverController {

    private SamAccountHandoverService samAccountHandoverService;

    private DmsFileSaver dmsFileSaver;

    private EmployeeService employeeService;

    private LoanAccountBasicService loanAccountBasicService;

    private LoanAccountDistributionRepository loanAccountDistributionRepository;

    private LoanAccountOtherService loanAccountOtherService;

    private LoanAccountService loanAccountService;

    private CardAccountBasicRepository cardAccountBasicRepository;

    private CardAccountDistributionRepository cardAccountDistributionRepository;

    private CardAccountOtherService cardAccountOtherService;

    private CardAccountService cardAccountService;

    private CustomerBasicInfoEntityRepository customerBasicInfoEntityRepository;

    private PeopleAllocationLogicRepository peopleAllocationLogicRepository;

    private SamAccountHandoverRepository samAccountHandoverRepository;


    @GetMapping(value = "loanlist")
    public String viewAllLoan(Model model) {
        List<SamAccountHandoverInfo> all = samAccountHandoverRepository.findByCreatedDateIsBetweenAndProductUnitAndLatest(getStartDate(), getEndDate(), "Loan", "1");
        model.addAttribute("samList", all);
        Gson gson = new Gson();
        model.addAttribute("samListJson", gson.toJson(all));
        List<EmployeeInfoEntity> dealterList = employeeService.getDealerList();
        List<EmployeeInfoEntity> superVisorList = employeeService.getSuperVisorList();
        List<PeopleAllocationLogicInfo> peopleAllocationLogicInfoList = peopleAllocationLogicRepository.findByUnitAndDistributionType("Loan", "Regular");
        model.addAttribute("dealerList", peopleAllocationLogicInfoList);
        model.addAttribute("supervisorList", superVisorList);
        return "collection/distribution/samAccountHandover/sam";
    }

    @GetMapping(value = "cardlist")
    public String viewAllCard(Model model) {
        List<SamAccountHandoverInfo> all = samAccountHandoverRepository.findByCreatedDateIsBetweenAndProductUnitAndLatest(getStartDate(), getEndDate(), "Card", "1");
        model.addAttribute("samList", all);
        Gson gson = new Gson();
        model.addAttribute("samListJson", gson.toJson(all));
        List<EmployeeInfoEntity> dealterList = employeeService.getDealerList();
        List<EmployeeInfoEntity> superVisorList = employeeService.getSuperVisorList();
        List<PeopleAllocationLogicInfo> peopleAllocationLogicInfoList = peopleAllocationLogicRepository.findByUnitAndDistributionType("Card", "Regular");
        model.addAttribute("dealerList", peopleAllocationLogicInfoList);
        model.addAttribute("supervisorList", superVisorList);
        return "collection/distribution/samAccountHandover/sam-card";
    }

    @GetMapping(value = "create")
    public String addpage(Model model) {
        model.addAttribute("samAccount", new SamAccountHandoverInfo());
        return "collection/distribution/samAccountHandover/create";
    }

    @PostMapping(value = "create")
    public String create(Model model, @RequestParam("file") MultipartFile multipartFile, RedirectAttributes redirectAttributes) {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<SamAccountHandoverInfo> samAccountHandoverInfos = new ArrayList<>();

        if (multipartFile.isEmpty()) {
            model.addAttribute("validationError", "Attach a excel file");
            return "collection/distribution/samAccountHandover/create";
        }

        if (!multipartFile.getContentType().equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet") && !multipartFile.getContentType().equals("application/vnd.ms-excel")) {
            model.addAttribute("validationError", "Only excel file format is supported");
            return "collection/distribution/samAccountHandover/create";
        }


        try {
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(multipartFile.getInputStream());
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);


            Date disbrusmentDate = null;
            Date samReceivedDate = null;
            Date writeOffDate = null;
            Date firstLegalLetterNoticeDate = null;
            Date fileDateNiAct = null, fileDateArtharin = null, fileDatePenalCode = null, nextAskingDate = null, auctionNoticeDate = null,
                    newspaperPublicationDate = null, auctionDate = null;
            for (int i = 1; i < xssfSheet.getPhysicalNumberOfRows(); i++) {
                XSSFRow row = xssfSheet.getRow(i);

                Boolean dateFlag = null;
                if (row.getCell(0) != null && row.getCell(0).toString().length() > 1) {

                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");

                    dateFlag = (row.getCell(5) != null) ? checkDateFormat(row.getCell(5).toString()) : null;
                    if (dateFlag == null || dateFlag) {
                        disbrusmentDate = dateFlag != null && dateFlag ? simpleDateFormat.parse(row.getCell(5).toString()) : null;
                        dateFlag = null;
                    } else {
                        model.addAttribute("validationError", "Cell Number 6 has invalid date format");
                        return "collection/distribution/samAccountHandover/create";
                    }
                    dateFlag = (row.getCell(12) != null) ? checkDateFormat(row.getCell(12).toString()) : null;
                    if (dateFlag == null || dateFlag) {
                        samReceivedDate = dateFlag != null && dateFlag ? simpleDateFormat.parse(row.getCell(12).toString()) : null;
                        dateFlag = null;
                    } else {
                        model.addAttribute("validationError", "Cell Number 13 has invalid date format");
                        return "collection/distribution/samAccountHandover/create";
                    }
                    dateFlag = (row.getCell(13) != null) ? checkDateFormat(row.getCell(13).toString()) : null;
                    if (dateFlag == null || dateFlag) {
                        firstLegalLetterNoticeDate = dateFlag != null && dateFlag ? simpleDateFormat.parse(row.getCell(13).toString()) : null;
                        dateFlag = null;
                    } else {
                        model.addAttribute("validationError", "Cell Number 14 has invalid date format");
                        return "collection/distribution/samAccountHandover/create";
                    }
                    dateFlag = (row.getCell(14) != null) ? checkDateFormat(row.getCell(14).toString()) : null;
                    if (dateFlag == null || dateFlag) {
                        writeOffDate = dateFlag != null && dateFlag ? simpleDateFormat.parse(row.getCell(14).toString()) : null;
                        dateFlag = null;
                    } else {
                        model.addAttribute("validationError", "Cell Number 15 has invalid date format");
                        return "collection/distribution/samAccountHandover/create";
                    }
                    dateFlag = (row.getCell(16) != null) ? checkDateFormat(row.getCell(16).toString()) : null;
                    if (dateFlag == null || dateFlag) {
                        fileDateNiAct = dateFlag != null && dateFlag ? simpleDateFormat.parse(row.getCell(16).toString()) : null;
                        dateFlag = null;
                    } else {
                        model.addAttribute("validationError", "Cell Number 17 has invalid date format");
                        return "collection/distribution/samAccountHandover/create";
                    }
                    dateFlag = (row.getCell(18) != null) ? checkDateFormat(row.getCell(18).toString()) : null;
                    if (dateFlag == null || dateFlag) {
                        fileDateArtharin = dateFlag != null && dateFlag ? simpleDateFormat.parse(row.getCell(18).toString()) : null;
                        dateFlag = null;
                    } else {
                        model.addAttribute("validationError", "Cell Number 19 has invalid date format");
                        return "collection/distribution/samAccountHandover/create";
                    }
                    dateFlag = (row.getCell(20) != null) ? checkDateFormat(row.getCell(20).toString()) : null;
                    if (dateFlag == null || dateFlag) {
                        fileDatePenalCode = dateFlag != null && dateFlag ? simpleDateFormat.parse(row.getCell(20).toString()) : null;
                        dateFlag = null;
                    } else {
                        model.addAttribute("validationError", "Cell Number 21 has invalid date format");
                        return "collection/distribution/samAccountHandover/create";
                    }
                    dateFlag = (row.getCell(22) != null) ? checkDateFormat(row.getCell(22).toString()) : null;
                    if (dateFlag == null || dateFlag) {
                        nextAskingDate = dateFlag != null && dateFlag ? simpleDateFormat.parse(row.getCell(22).toString()) : null;
                        dateFlag = null;
                    } else {
                        model.addAttribute("validationError", "Cell Number 23 has invalid date format");
                        return "collection/distribution/samAccountHandover/create";
                    }
                    dateFlag = (row.getCell(28) != null) ? checkDateFormat(row.getCell(28).toString()) : null;
                    if (dateFlag == null || dateFlag) {
                        auctionNoticeDate = dateFlag != null && dateFlag ? simpleDateFormat.parse(row.getCell(28).toString()) : null;
                        dateFlag = null;
                    } else {
                        model.addAttribute("validationError", "Cell Number 29 has invalid date format");
                        return "collection/distribution/samAccountHandover/create";
                    }
                    dateFlag = (row.getCell(29) != null) ? checkDateFormat(row.getCell(29).toString()) : null;
                    if (dateFlag == null || dateFlag) {
                        newspaperPublicationDate = dateFlag != null && dateFlag ? simpleDateFormat.parse(row.getCell(29).toString()) : null;
                        dateFlag = null;
                    } else {
                        model.addAttribute("validationError", "Cell Number 30 has invalid date format");
                        return "collection/distribution/samAccountHandover/create";
                    }
                    dateFlag = (row.getCell(30) != null) ? checkDateFormat(row.getCell(30).toString()) : null;
                    if (dateFlag == null || dateFlag) {
                        auctionDate = dateFlag != null && dateFlag ? simpleDateFormat.parse(row.getCell(30).toString()) : null;
                        dateFlag = null;
                    } else {
                        model.addAttribute("validationError", "Cell Number 31 has invalid date format");
                        return "collection/distribution/samAccountHandover/create";
                    }


                    //disbrusmentDate = dateFlag==null||dateFlag?simpleDateFormat.parse(row.getCell(5).toString()):null;
                    //samReceivedDate = simpleDateFormat.parse(row.getCell(12).toString());
                    // = simpleDateFormat.parse(row.getCell(13)!=null?row.getCell(13).toString():null);
                    //writeOffDate = simpleDateFormat.parse(row.getCell(14)!=null?row.getCell(14).toString():null);

                    SamAccountHandoverInfo samAccountHandoverInfo = new SamAccountHandoverInfo();

                    samAccountHandoverInfo.setFirstLegalLetterNoticeDate(firstLegalLetterNoticeDate);
                    samAccountHandoverInfo.setWriteOffDate(writeOffDate);
                    samAccountHandoverInfo.setFileDateNiAct(fileDateNiAct);
                    samAccountHandoverInfo.setFileDateArtharin(fileDateArtharin);
                    samAccountHandoverInfo.setFileDatePenalCode(fileDatePenalCode);
                    samAccountHandoverInfo.setNextAskingDate(nextAskingDate);
                    samAccountHandoverInfo.setAuctionNoticeDate(auctionNoticeDate);
                    samAccountHandoverInfo.setNewspaperPublicationDate(newspaperPublicationDate);
                    samAccountHandoverInfo.setAuctionDate(auctionDate);

                    samAccountHandoverInfo.setCreatedDate(new Date());
                    samAccountHandoverInfo.setCreatedBy(user.getUsername());

                    if (row.getCell(14) != null) {
                        samAccountHandoverInfo.setWriteOffOrNot("1");
                        samAccountHandoverInfo.setWriteOffAmount(row.getCell(15) != null ? Double.parseDouble(row.getCell(15).toString()) : null);
                    }

                    /* * * * * * * */
                    samAccountHandoverInfo.setCaseAmountNiAct(row.getCell(17) != null ? Double.parseDouble(row.getCell(17).toString()) : null);
                    samAccountHandoverInfo.setCaseAmountArtharin(row.getCell(19) != null ? Double.parseDouble(row.getCell(19).toString()) : null);
                    samAccountHandoverInfo.setCaseAmountPenalCode(row.getCell(21) != null ? Double.parseDouble(row.getCell(21).toString()) : null);
                    samAccountHandoverInfo.setCaseStatus(row.getCell(23) != null ? row.getCell(23).toString() : null);
                    samAccountHandoverInfo.setCaseDealingPerson(row.getCell(24) != null ? row.getCell(24).toString() : null);
                    samAccountHandoverInfo.setLawyerName(row.getCell(25) != null ? row.getCell(25).toString() : null);
                    samAccountHandoverInfo.setCourtName(row.getCell(26) != null ? row.getCell(26).toString() : null);
                    samAccountHandoverInfo.setPresentOutstanding(row.getCell(27) != null ? row.getCell(27).toString() : null);
                    /* * * * * * * */

                    if (row.getCell(4).toString().equals("Loan")) {

                        Long l_loanAccNo = null, l_cusId = null;
                        String loanAccNo = null, cusId = null;

                        if (row.getCell(0).getCellType() == Cell.CELL_TYPE_NUMERIC) {
                            l_loanAccNo = Double.valueOf(row.getCell(0).toString()).longValue();
                            loanAccNo = l_loanAccNo.toString();
                        } else
                            loanAccNo = row.getCell(0).toString();
                        if (row.getCell(2) != null && row.getCell(2).getCellType() == Cell.CELL_TYPE_NUMERIC) {
                            l_cusId = Double.valueOf((row.getCell(2).toString())).longValue();
                            cusId = l_cusId.toString();
                        } else
                            cusId = row.getCell(2) != null ? row.getCell(2).toString() : null;

                        samAccountHandoverInfo.setLoanAccountNo(loanAccNo);
                        samAccountHandoverInfo.setAccountName(row.getCell(1) != null ? row.getCell(1).toString() : "");
                        samAccountHandoverInfo.setCustomerId(cusId);
                        samAccountHandoverInfo.setSchemaCode(row.getCell(3) != null ? row.getCell(3).toString() : "");
                        samAccountHandoverInfo.setProductUnit(row.getCell(4) != null ? row.getCell(4).toString() : "");
                        samAccountHandoverInfo.setSamRecievedDate(samReceivedDate);
                        samAccountHandoverInfo.setDisbursementDate(disbrusmentDate);
                        samAccountHandoverInfo.setOutstandingAmount(row.getCell(6) != null ? row.getCell(6).toString() : "");
                        samAccountHandoverInfo.setEmiAccount(row.getCell(7) != null ? row.getCell(7).toString() : "");
                        samAccountHandoverInfo.setOverDueAmount(row.getCell(8) != null ? row.getCell(8).toString() : "");
                        samAccountHandoverInfo.setDpdBucket(row.getCell(9) != null ? row.getCell(9).toString() : "");
                        samAccountHandoverInfo.setLetterType(row.getCell(10) != null ? row.getCell(10).toString() : "");
                        samAccountHandoverInfo.setRemarks(row.getCell(11) != null ? row.getCell(11).toString() : "");
                        samAccountHandoverInfo.setSamAccount("1");
                        samAccountHandoverInfo.setLatest("1");
                        //samAccountHandoverInfo.set

                        LoanAccountBasicInfo loanAccountBasicInfo = loanAccountBasicService.getByAccountNo(row.getCell(0) != null ? row.getCell(0).toString() : "");
                        if (loanAccountBasicInfo == null) {
                            loanAccountBasicInfo = new LoanAccountBasicInfo();
                            loanAccountBasicInfo.setAccountNo(row.getCell(0) != null ? row.getCell(0).toString() : "");
                            loanAccountBasicInfo.setAccountName(row.getCell(1) != null ? row.getCell(1).toString() : "");
                            loanAccountBasicInfo.setDisbursementDate(disbrusmentDate);
                            loanAccountBasicInfo.setDisbursementAmount(0);
                            loanAccountBasicInfo.setCreatedDate(new Date());
                            loanAccountBasicInfo.setLocation("");

                            CustomerBasicInfoEntity customerBasicInfoEntity = new CustomerBasicInfoEntity();
                            customerBasicInfoEntity.setAccountNo(row.getCell(0) != null ? row.getCell(0).toString() : "");
                            customerBasicInfoEntity.setTypeAccount("Loan");
                            customerBasicInfoEntity.setCreatedDate(new Date());
                            customerBasicInfoEntityRepository.save(customerBasicInfoEntity);
                            loanAccountBasicInfo.setCustomer(customerBasicInfoEntity);
                            loanAccountBasicService.saveOrUpdate(loanAccountBasicInfo);
                            samAccountHandoverInfo.setLoanAccountBasicInfo(loanAccountBasicInfo);

                            LoanAccountOtherInfo loanAccountOtherInfo = new LoanAccountOtherInfo();
                            loanAccountOtherInfo.setCompanyCategory("");
                            loanAccountOtherInfo.setModeOfPayment("");
                            loanAccountOtherInfo.setLoanAccountBasicInfo(loanAccountBasicInfo);
                            loanAccountOtherInfo.setNotificationStatus("");
                            loanAccountOtherInfo.setOrgType("");
                            loanAccountOtherInfo.setCreatedDate(new Date());
                            loanAccountOtherService.saveNew(loanAccountOtherInfo);


                            LoanAccountInfo loanAccountInfo = new LoanAccountInfo();
                            loanAccountInfo.setAssetClassification("");
                            loanAccountInfo.setAte(0d);
                            loanAccountInfo.setCumulativePayment(0);
                            loanAccountInfo.setDpbBucket(samAccountHandoverInfo.getDpdBucket());
                            loanAccountInfo.setDpd(0d);
                            loanAccountInfo.setEmiDueDate("");
                            loanAccountInfo.setGrossDistributionCriteriaNew("");
                            loanAccountInfo.setMoDpdBucket("");
                            loanAccountInfo.setNonPerformingLoan("");
                            loanAccountInfo.setOutstandingPrincipal(0);
                            loanAccountInfo.setSchemeCode("");
                            loanAccountInfo.setTotalOverdue(0);
                            loanAccountInfo.setCreatedDate(new Date());
                            loanAccountInfo.setLoanAccountBasicInfo(loanAccountBasicInfo);
                            loanAccountService.saveNew(loanAccountInfo);

                            samAccountHandoverRepository.save(samAccountHandoverInfo);

                        } else {
                            samAccountHandoverInfo.setLoanAccountBasicInfo(loanAccountBasicInfo);
                            LoanAccountDistributionInfo loanAccountDistributionInfo = loanAccountDistributionRepository.findFirstByLoanAccountBasicInfoOrderByCreatedDateDesc(loanAccountBasicInfo);
                            if (loanAccountDistributionInfo != null) {
                                loanAccountDistributionInfo.setSamAccount("1");
                                loanAccountDistributionRepository.save(loanAccountDistributionInfo);
                            }
                            SamAccountHandoverInfo samAccountHandoverFromDatabase = samAccountHandoverRepository.findFirstByCreatedDateIsBetweenAndLoanAccountBasicInfoAndLatest(getStartDate(), getEndDate(), loanAccountBasicInfo, "1");
                            if (samAccountHandoverFromDatabase != null) {
                                samAccountHandoverFromDatabase.setLatest("0");
                                samAccountHandoverRepository.save(samAccountHandoverFromDatabase);
                            }
                            samAccountHandoverRepository.save(samAccountHandoverInfo);

                        }


                        samAccountHandoverInfos.add(samAccountHandoverInfo);

                    } else if (row.getCell(4).toString().equals("Card")) {

                        Long l_cardNo = null, l_cusId = null;
                        String cardNo = null, cusId = null;

                        if (row.getCell(0).getCellType() == Cell.CELL_TYPE_NUMERIC) {
                            l_cardNo = Double.valueOf(row.getCell(0).toString()).longValue();
                            cardNo = l_cardNo.toString();
                        } else
                            cardNo = row.getCell(0).toString();
                        if (row.getCell(2) != null && row.getCell(2).getCellType() == Cell.CELL_TYPE_NUMERIC) {
                            l_cusId = Double.valueOf((row.getCell(2).toString())).longValue();
                            cusId = l_cusId.toString();
                        } else
                            cusId = row.getCell(2) != null ? row.getCell(2).toString() : null;

                        samAccountHandoverInfo.setCardNo(cardNo);
                        samAccountHandoverInfo.setCardName(row.getCell(1) != null ? row.getCell(1).toString() : "");
                        samAccountHandoverInfo.setCustomerId(cusId);
                        samAccountHandoverInfo.setSchemaCode(row.getCell(3) != null ? row.getCell(3).toString() : "");
                        samAccountHandoverInfo.setProductUnit(row.getCell(4) != null ? row.getCell(4).toString() : "");
                        samAccountHandoverInfo.setSamRecievedDate(samReceivedDate);
                        samAccountHandoverInfo.setEmbossedDate(disbrusmentDate);
                        samAccountHandoverInfo.setOutstandingAmount(row.getCell(6) != null ? row.getCell(6).toString() : "");
                        samAccountHandoverInfo.setEmiAccount(row.getCell(7) != null ? row.getCell(7).toString() : "");
                        samAccountHandoverInfo.setDueAmount(row.getCell(8) != null ? row.getCell(8).toString() : "");
                        samAccountHandoverInfo.setAgeCode(row.getCell(9) != null ? row.getCell(9).toString() : "");

                        samAccountHandoverInfo.setRemarks(row.getCell(11) != null ? row.getCell(11).toString() : "");
                        samAccountHandoverInfo.setLetterType(row.getCell(10) != null ? row.getCell(10).toString() : "");
                        samAccountHandoverInfo.setSamAccount("1");
                        samAccountHandoverInfo.setLatest("1");

                        CardAccountBasicInfo cardAccountBasicInfo = cardAccountBasicRepository.findByCardNo(row.getCell(0) != null ? row.getCell(0).toString() : "");
                        if (cardAccountBasicInfo == null) {
                            cardAccountBasicInfo = new CardAccountBasicInfo();
                            cardAccountBasicInfo.setCardNo(row.getCell(0) != null ? row.getCell(0).toString() : "");
                            cardAccountBasicInfo.setCardName(row.getCell(1) != null ? row.getCell(1).toString() : "");
                            cardAccountBasicInfo.setClientId(row.getCell(2) != null ? row.getCell(2).toString() : "");

                            CustomerBasicInfoEntity customerBasicInfoEntity = new CustomerBasicInfoEntity();
                            customerBasicInfoEntity.setAccountNo(row.getCell(0) != null ? row.getCell(0).toString() : "");
                            customerBasicInfoEntity.setTypeAccount("Card");
                            customerBasicInfoEntity.setCreatedDate(new Date());
                            customerBasicInfoEntityRepository.save(customerBasicInfoEntity);
                            cardAccountBasicInfo.setCustomer(customerBasicInfoEntity);
                            samAccountHandoverInfo.setCardAccountBasicInfo(cardAccountBasicInfo);
                            cardAccountBasicRepository.save(cardAccountBasicInfo);


                            /*CardOtherAccountInfo cardOtherAccountInfo = new CardOtherAccountInfo();
                            cardOtherAccountInfo.setOtherAccountNo("");
                            cardOtherAccountInfo.setOtherRemark("");
                            cardOtherAccountInfo.setEnabled();
                            cardOtherAccountInfo.setCustomerBasicInfoEntity(customerBasicInfoEntity);
                            cardOtherAccountInfo.setCreatedDate(new Date());*/

                            CardAccountOtherInfo cardAccountOtherInfo = new CardAccountOtherInfo();
                            cardAccountOtherInfo.setCompanyCategory("");
                            cardAccountOtherInfo.setNotificationStatus("");
                            cardAccountOtherInfo.setSecuredCreditcard("");
                            cardAccountOtherInfo.setVvip("");
                            cardAccountOtherInfo.setModeOfPayment("");
                            cardAccountOtherInfo.setOrgType("");
                            cardAccountOtherInfo.setCreatedDate(new Date());
                            cardAccountOtherInfo.setCardAccountBasicInfo(cardAccountBasicInfo);
                            cardAccountOtherService.saveNew(cardAccountOtherInfo);


                            CardAccountInfo cardAccountInfo = new CardAccountInfo();
                            cardAccountInfo.setLasOneMonthPayment("");
                            //cardAccountInfo.setMoAccLimit("");
                            cardAccountInfo.setMoAccMinPayment("");
                            cardAccountInfo.setMoAccOutstanding("");
                            cardAccountInfo.setMoAgeCode(samAccountHandoverInfo.getAgeCode());
                            cardAccountInfo.setMoBillCycle("");
                            cardAccountInfo.setMoStatus_Cd("");
                            cardAccountInfo.setPrdGrp("");
                            cardAccountInfo.setCreatedDate(new Date());
                            cardAccountInfo.setCardAccountBasicInfo(cardAccountBasicInfo);
                            cardAccountService.saveNew(cardAccountInfo);


                        } else {
                            samAccountHandoverInfo.setCardAccountBasicInfo(cardAccountBasicInfo);
                            CardAccountDistributionInfo cardAccountDistributionInfo = cardAccountDistributionRepository.findFirstByCardAccountBasicInfoOrderByCreatedDateDesc(cardAccountBasicInfo);
                            if (cardAccountDistributionInfo != null) {
                                cardAccountDistributionInfo.setSamAccount("1");
                                cardAccountDistributionRepository.save(cardAccountDistributionInfo);
                            }
                            SamAccountHandoverInfo samAccountHandoverFromDatabase = samAccountHandoverRepository.findFirstByCreatedDateIsBetweenAndCardAccountBasicInfoAndLatest(getStartDate(), getEndDate(), cardAccountBasicInfo, "1");
                            if (samAccountHandoverFromDatabase != null) {
                                samAccountHandoverFromDatabase.setLatest("0");
                                samAccountHandoverRepository.save(samAccountHandoverFromDatabase);
                            }
                            samAccountHandoverRepository.save(samAccountHandoverInfo);
                        }

                        samAccountHandoverRepository.save(samAccountHandoverInfo);

                        samAccountHandoverInfos.add(samAccountHandoverInfo);
                    }
                }

            }
        } catch (IOException | ParseException e) {
            System.err.println(e.getMessage());
        }

        /*for(SamAccountHandoverInfo s: samAccountHandoverInfos){
            if(s.getId()==null) {
                s.setCreatedBy(user.getUsername());
                s.setCreatedDate(new Date());
                boolean save= samAccountHandoverService.saveNew(s);
            }else {
                s.setModifiedBy(user.getUsername());
                s.setModifiedDate(new Date());
                boolean update= samAccountHandoverService.updateAgency(s);
            }
        }*/
        redirectAttributes.addFlashAttribute("saved", "SAM Handover saved Successfully.");
        return "redirect:/collection/distribution/samaccount/create";
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
            e.printStackTrace();
        }
        return null;
    }

    boolean checkDateFormat(String s) {
        return s.matches("[0-9]{2}-[A-Za-z]{3}-[0-9]{4}");
    }
}
