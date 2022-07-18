package com.csinfotechbd.retail.card.dataEntry.distribution.agency;

import com.csinfotechbd.retail.card.dataEntry.distribution.accountOtherInfo.CardAccountOtherInfo;
import com.csinfotechbd.retail.card.dataEntry.distribution.accountOtherInfo.CardAccountOtherService;
import com.csinfotechbd.retail.card.dataEntry.distribution.accountDistributionInfo.CardAccountDistributionRepository;
import com.csinfotechbd.retail.card.dataEntry.distribution.accountInfo.CardAccountInfo;
import com.csinfotechbd.retail.card.dataEntry.distribution.accountInfo.CardAccountService;
import com.csinfotechbd.retail.card.dataEntry.distribution.accountBasicInfo.CardAccountBasicInfo;
import com.csinfotechbd.retail.card.dataEntry.distribution.accountBasicInfo.CardAccountBasicService;
import com.csinfotechbd.retail.card.dataEntry.distribution.accountDistributionInfo.CardAccountDistributionInfo;
import com.csinfotechbd.collection.distribution.loan.LoanDualEnum;
import com.csinfotechbd.customerbasicinfo.CustomerBasicInfoEntity;
import com.csinfotechbd.customerbasicinfo.CustomerBasicInfoEntityRepository;
import com.csinfotechbd.user.UserPrincipal;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/*
Created by Monirul Islam at 7/22/2019 
*/
@Controller
@RequestMapping("/collection/cardagency/distribution/")
public class CardAgencyDistributionController {
    @Autowired
    private CardAccountBasicService cardAccountBasicService;
    @Autowired
    private CardAgencyDistributionService cardAgencyDistributionService;
    @Autowired
    private CardAccountDistributionRepository cardAccountDistributionRepository;
    @Autowired
    private CardAgencyDistributionRepository cardAgencyDistributionRepository;
    @Autowired
    private CustomerBasicInfoEntityRepository customerBasicInfoEntityRepository;
    @Autowired
    private CardAccountService cardAccountService;
    @Autowired
    private CardAccountOtherService cardAccountOtherService;

    @GetMapping("/add")
    public String addDistribution(Model model){
        return "/collection/distribution/agencyAllocation/card/create";
    }

    @PostMapping("/save")
    public String saveDistribution(Model model, @RequestParam("file") MultipartFile multipartFile, @RequestParam("bdtUsdConversionRate") Double bdtUsdConversionRate, RedirectAttributes redirectAttributes){
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        XSSFWorkbook xssfWorkbook = null;
        try {
            xssfWorkbook = new XSSFWorkbook(multipartFile.getInputStream());
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);

            for(int i=1; i<xssfSheet.getPhysicalNumberOfRows(); i++) {
                XSSFRow row = xssfSheet.getRow(i);
                boolean insertOrNot = false;

                if(row.getCell(0) != null && row.getCell(0).toString().length() > 1){
                    CardAccountBasicInfo byAccountNo = cardAccountBasicService.getByAccountNo(row.getCell(0).toString());

                    CardAgencyDistributionInfo cardAgencyDistributionInfo = new CardAgencyDistributionInfo();
                    cardAgencyDistributionInfo.setCardAccountBasicInfo(byAccountNo);

                    cardAgencyDistributionInfo.setProductGroup(row.getCell(1)!=null?row.getCell(1).toString():null);

                    Double domesticOutstanding = (row.getCell(4)!=null && row.getCell(4).toString().length()>1)?Double.parseDouble(row.getCell(4).toString()):null;
                    Double internationalOutstanding = (row.getCell(5)!=null && row.getCell(5).toString().length()>1)?Double.parseDouble((row.getCell(4).toString()))*bdtUsdConversionRate:null;
                    Double totalOutStanding = (domesticOutstanding!=null && internationalOutstanding!=null)? domesticOutstanding + internationalOutstanding : null;

                    cardAgencyDistributionInfo.setBdtUsdConversionRate(bdtUsdConversionRate);

                    cardAgencyDistributionInfo.setTotalOutstanding(totalOutStanding);
                    cardAgencyDistributionInfo.setOutstanding(totalOutStanding!=null?totalOutStanding.toString():null);
                    //cardAgencyDistributionInfo.setOverdue(row.getCell(3).toString());
                    cardAgencyDistributionInfo.setAgeCode(row.getCell(10)!=null?row.getCell(10).toString():null);
                    cardAgencyDistributionInfo.setClientId(row.getCell(11)!=null?row.getCell(11).toString():null);
                    cardAgencyDistributionInfo.setBillingCycle(row.getCell(12) != null ?
                            row.getCell(12).toString().contains(".")
                                    ? row.getCell(12).toString().substring(0, row.getCell(12).toString().indexOf('.'))
                                    : row.getCell(12).toString() : "");
                    //cardAgencyDistributionInfo.setCashCollection(row.getCell(5).toString());
                    cardAgencyDistributionInfo.setAgencyName(row.getCell(18)!=null?row.getCell(18).toString():null);
                    /*cardAgencyDistributionInfo.setAgentName(row.getCell(7).toString());
                    cardAgencyDistributionInfo.setMobileNo(row.getCell(8).toString());
                    cardAgencyDistributionInfo.setHandoverDate(row.getCell(9).toString());
                    cardAgencyDistributionInfo.setExpireDate(row.getCell(10).toString());*/
                    cardAgencyDistributionInfo.setVvip(row.getCell(14)!=null?row.getCell(14).toString():null);
                    cardAgencyDistributionInfo.setSecureCard(row.getCell(15)!=null?row.getCell(15).toString():null);
                    cardAgencyDistributionInfo.setStateCode(row.getCell(16)!=null?row.getCell(16).toString():null);
                    cardAgencyDistributionInfo.setMonitorStatus(row.getCell(19)!=null?row.getCell(19).toString():null);
                    cardAgencyDistributionInfo.setCreatedDate(new Date());
                    cardAgencyDistributionInfo.setCreatedBy(userPrincipal.getUsername());
                    cardAgencyDistributionInfo.setEnabled(true);
                    cardAgencyDistributionInfo.setLatest("1");
                    if(cardAgencyDistributionInfo.getMonitorStatus().toUpperCase().equals("SINGLE")) cardAgencyDistributionInfo.setLoanDualEnum(LoanDualEnum.SINGLE);
                    else cardAgencyDistributionInfo.setLoanDualEnum(LoanDualEnum.DUAL);

                    if(cardAgencyDistributionInfo.getMonitorStatus().toUpperCase().equals("DUAL")){
                        CardAccountDistributionInfo cardAccountDistributionFromDatabase = cardAccountDistributionRepository.findFirstByCreatedDateIsBetweenAndCardAccountBasicInfoAndLatestOrderByCreatedDateDesc(getStartDate(), getEndDate(), byAccountNo, "1");
                        if(cardAccountDistributionFromDatabase != null){
                            cardAccountDistributionFromDatabase.setMonitoringStatus("DUAL");
                            cardAccountDistributionRepository.save(cardAccountDistributionFromDatabase);
                        }else insertOrNot = true;

                    }
                    if (insertOrNot) continue;

                    if(byAccountNo != null){
                        cardAgencyDistributionInfo.setCardAccountBasicInfo(byAccountNo);
                        CardAgencyDistributionInfo cardAgencyDistributionInfoFromDatabase = cardAgencyDistributionRepository
                                .findFirstByCreatedDateGreaterThanEqualAndCreatedDateLessThanEqualAndCardAccountBasicInfoAndLatestOrderByCreatedDateDesc(getStartDate(), getEndDate(), byAccountNo, "1");

                        if(cardAgencyDistributionInfoFromDatabase != null){
                            cardAgencyDistributionInfoFromDatabase.setLatest("0");
                            cardAgencyDistributionRepository.save(cardAgencyDistributionInfoFromDatabase);
                        }
                        cardAgencyDistributionRepository.save(cardAgencyDistributionInfo);
                    }
                    else{
                        CustomerBasicInfoEntity customerBasicInfoEntity = new CustomerBasicInfoEntity();
                        customerBasicInfoEntity.setTypeAccount("Card");
                        customerBasicInfoEntity.setAccountNo(row.getCell(0).toString());
                        //customerBasicInfoEntity.setCustomerName(row.getCell(1).toString());
                        customerBasicInfoEntity.setCustomerId(null);
                        customerBasicInfoEntity.setCreatedDate(new Date());
                        customerBasicInfoEntityRepository.save(customerBasicInfoEntity);

                        CardAccountBasicInfo cardAccountBasicInfo = new CardAccountBasicInfo();
                        cardAccountBasicInfo.setContractId(row.getCell(0)!=null?row.getCell(0).toString():null); // cardNo previously
                        //cardAccountBasicInfo.setCardName(row.getCell(1).toString());
                        cardAccountBasicInfo.setEnabled(true);
                        cardAccountBasicInfo.setCreatedDate(new Date());
                        cardAccountBasicInfo.setCustomer(customerBasicInfoEntity);
                        cardAccountBasicService.saveNew(cardAccountBasicInfo);

                        CardAccountInfo cardAccountInfo = new CardAccountInfo();
                        cardAccountInfo.setCardAccountBasicInfo(cardAccountBasicInfo);
                        cardAccountInfo.setPrdGrp(row.getCell(1)!=null?row.getCell(1).toString():null);
                        cardAccountInfo.setBdtLimit(row.getCell(2)!=null?Double.parseDouble(row.getCell(2).toString()):null);
                        cardAccountInfo.setInternationalLimit(row.getCell(3)!=null?Double.parseDouble(row.getCell(3).toString()):null);
                        cardAccountInfo.setBdtOutstanding(domesticOutstanding);
                        cardAccountInfo.setInternationalOutstanding(internationalOutstanding);
                        cardAccountInfo.setBdtMinDue(row.getCell(7)!=null? Double.parseDouble(row.getCell(7).toString()) :null);
                        cardAccountInfo.setInternationalMinDue(row.getCell(8)!=null? Double.parseDouble(row.getCell(8).toString()) :null);
                        cardAccountInfo.setEnabled(true);
                        cardAccountInfo.setCreatedDate(new Date());
                        cardAccountService.saveNew(cardAccountInfo);

                        CardAccountOtherInfo cardAccountOtherInfo = new CardAccountOtherInfo();
                        cardAccountOtherInfo.setCardAccountBasicInfo(cardAccountBasicInfo);
                        cardAccountOtherInfo.setEnabled(true);
                        cardAccountOtherInfo.setCreatedDate(new Date());
                        //cardAccountOtherService.saveNew(cardAccountOtherInfo);
                        cardAgencyDistributionInfo.setCardAccountBasicInfo(cardAccountBasicInfo);
                        cardAgencyDistributionRepository.save(cardAgencyDistributionInfo);
                    }




                    //cardAgencyDistributionService.saveNew(cardAgencyDistributionInfo);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        redirectAttributes.addFlashAttribute("saved","Agency allocation saved successfully");
        return "redirect:/distribution/card/agency";
    }
    public Date getStartDate(){
        LocalDate today = LocalDate.now();
        LocalDate startDateOfMonth = today.withDayOfMonth(1);
        return Date.from(startDateOfMonth.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public Date getEndDate(){
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
