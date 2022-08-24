package com.unisoft.cardprofile;
import com.unisoft.cardprofile.cardOtherAccountInfo.CardOtherAccountInfo;
import com.unisoft.cardprofile.cardOtherAccountInfo.CardOtherAccountInfoRepository;
import com.unisoft.collection.emi.EmiEmtityDto;
import com.unisoft.collection.emi.EmiEntity;
import com.unisoft.collection.emi.EmiRepository;
import com.unisoft.collection.emi.EmiService;
import com.unisoft.collection.settings.ExchangeRate.ExchangeRateEntity;
import com.unisoft.collection.settings.ExchangeRate.ExchangeRateService;
import com.unisoft.collection.settings.customerinfouploadcard.professionsegment.ProfessionSegment;
import com.unisoft.collection.settings.customerinfouploadcard.professionsegment.ProfessionSegmentRepository;
import com.unisoft.collection.settings.customerinfouploadcard.sourcechannel.SourceChannel;
import com.unisoft.collection.settings.customerinfouploadcard.sourcechannel.SourceChannelRepository;
import com.unisoft.collection.settings.marketBy.MarketByRepository;
import com.unisoft.customerbasicinfo.CustomerBasicInfoEntity;
import com.unisoft.customerbasicinfo.CustomerBasicInfoService;
import com.unisoft.customerloanprofile.ptpsummary.MiscellaneousThreeSixtyFields;
import com.unisoft.detailsOfCollection.cardviewmodels.*;
import com.unisoft.retail.card.cardAPI.CardApiService;
import com.unisoft.retail.card.cardAPIDto.PaymentDuringMonthDto;
import com.unisoft.retail.card.cardAPIDto.TotalEmiDues;
import com.unisoft.retail.card.dataEntry.contactInfoCard.ContactInfoCardService;
import com.unisoft.retail.card.dataEntry.dailyNotes.DailyNotesRepository;
import com.unisoft.retail.card.dataEntry.dairynotes.DairyNotesService;
import com.unisoft.retail.card.dataEntry.distribution.accountBasicInfo.CardAccountBasicService;
import com.unisoft.retail.card.dataEntry.distribution.accountDistributionInfo.CardAccountDistributionService;
import com.unisoft.retail.card.dataEntry.distribution.accountInfo.CardAccountInfo;
import com.unisoft.retail.card.dataEntry.distribution.accountInfo.CardAccountService;
import com.unisoft.retail.card.dataEntry.followup.CardFolllowUpService;
import com.unisoft.retail.card.dataEntry.hotnotes.CardHotNotesRepository;
import com.unisoft.retail.card.dataEntry.ptp.CardPtpRepository;
import com.unisoft.collection.dashboard.CardBackendAccDetailDao;
import com.unisoft.collection.dashboard.CardBackendAccDetailsEntity;
import com.unisoft.retail.card.dataEntry.distribution.agency.CardAgencyDistributionInfo;
import com.unisoft.retail.card.dataEntry.distribution.agency.CardAgencyDistributionRepository;
import com.unisoft.collection.distribution.card.AccountLocationMappingRepository;
import com.unisoft.retail.card.dataEntry.distribution.accountDistributionInfo.CardAccountDistributionRepository;
import com.unisoft.retail.card.dataEntry.distribution.accountBasicInfo.CardAccountBasicInfo;
import com.unisoft.retail.card.dataEntry.distribution.accountBasicInfo.CardAccountBasicRepository;
import com.unisoft.retail.card.dataEntry.distribution.accountDistributionInfo.CardAccountDistributionInfo;
import com.unisoft.collection.distribution.samAccountHandover.SamAccountHandoverInfo;
import com.unisoft.collection.distribution.samAccountHandover.SamAccountHandoverRepository;
import com.unisoft.collection.distribution.writeOff.WriteOffAccountInfo;
import com.unisoft.collection.distribution.writeOff.WriteOffAccountRepository;
import com.unisoft.collection.securedcard.SecuredCardSampleRepository;
import com.unisoft.collection.settings.ageAndClassificationRule.AgeAndClassificationRuleEntityRepository;
import com.unisoft.collection.settings.employee.EmployeeInfoEntity;
import com.unisoft.collection.settings.employee.EmployeeRepository;
import com.unisoft.collection.settings.securedCardCriteria.SecuredCardCriteriaEntity;
import com.unisoft.collection.settings.securedCardCriteria.SecuredCardCriteriaService;
import com.unisoft.collection.settings.visitLedger.VisitLedgerEntity;
import com.unisoft.collection.settings.visitLedger.VisitLedgerRepository;
import com.unisoft.collection.settings.vipStatus.VipStatusService;
import com.unisoft.customerloanprofile.letterinformation.LetterInformationRepository;
import com.unisoft.customerloanprofile.ptpsummary.PtpSummaryDetailsDao;
import com.unisoft.retail.card.dataEntry.reasonDelinquencyCard.ReasonDelinquencyCardService;
import com.unisoft.retail.card.reporttextfileupload.*;
import com.unisoft.utillity.DateUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.Tuple;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/collection/card")
public class CardProfileController {

    private final CardHotNotesRepository cardHotNotesRepository;

    private final DailyNotesRepository dailyNotesRepository;

    private final DairyNotesService dairyNotesService;

    private final CardFolllowUpService cardFolllowUpService;

    private final CardAccountBasicRepository cardAccountBasicRepository;

    private final CardAccountDistributionRepository cardAccountDistributionRepository;

    private final CardAgencyDistributionRepository cardAgencyDistributionRepository;

    private final VisitLedgerRepository visitLedgerRepository;

    private final EmployeeRepository employeeRepository;

    private final CardOtherAccountInfoRepository cardOtherAccountInfoRepository;

    private final LetterInformationRepository letterInformationRepository;

    private final PtpSummaryDetailsDao ptpSummaryDetailsDao;

    private final VipStatusService vipStatusService;

    private final DateUtils dateUtils;

    private final CardPtpRepository cardPtpRepository;

    private final AccountLocationMappingRepository accountLocationMappingRepository;

    private final CardBackendAccDetailDao cardBackendAccDetailDao;

    private final SecuredCardCriteriaService securedCardCriteriaService;

    private final SecuredCardSampleRepository securedCardSampleRepository;

    private final AgeAndClassificationRuleEntityRepository ageAndClassificationRuleEntityRepository;

    private final WriteOffAccountRepository writeOffAccountRepository;

    private final SamAccountHandoverRepository samAccountHandoverRepository;

    private final CustomerBasicInfoService customerBasicInfoService;

    private final CardAccountBasicService cardAccountBasicService;

    private final ReasonDelinquencyCardService reasonDelinquencyCardService;

    private final CardAccountService cardAccountService;

    private final CollectionReportOneRepository collectionReportOneRepository;

    private final ApproveTransactionsUnbilledTransactionsPaymentRepository approveTransactionsUnbilledTransactionsPaymentRepository;

    private final CollectionReportTwoRepository collectionReportTwoRepository;

    private final CollectionReportThreeRepository collectionReportThreeRepository;

    private final CardApiService cardApiService;

    private final EmiRepository emiRepository;

    private final SourceChannelRepository sourceChannelRepository;

    private final ProfessionSegmentRepository professionSegmentRepository;

    private final AgingHistoryRepository agingHistoryRepository;

    private final MarketByRepository marketByRepository;

    @Autowired
    private final CollectionReportTwoService collectionReportService;

    @Autowired
    private final CollectionReportOneService collectionReportOneService;

    @Autowired
    private final ContactInfoCardService contactInfoCardService;

    @Autowired
    private final CardAccountDistributionService cardAccountDistributionService;

    @Autowired
    private final AllocationDetailsService allocationDetailsService;

    @Autowired
    private final ExchangeRateService exchangeRateService;

    @Autowired
    private RecoveryViewService recoveryViewService;

    @Autowired
    private EmiService emiService;



    @GetMapping("/search")
    public String searchCardProfile(@RequestParam(value = "contractNo") String contractNo) {
        CustomerBasicInfoEntity customerEntity = customerBasicInfoService.findByContractId(contractNo);
        CardAccountBasicInfo cardBasicInfo = cardAccountBasicService.getByContractId(contractNo);
        if (customerEntity == null || cardBasicInfo == null) {
            return "redirect:/profile/loanprofile";
        }
        return "redirect:/collection/card/profile?custid=" + customerEntity.getId() + "&cardid=" + cardBasicInfo.getId();
    }

    @GetMapping("/searchclientid")
    public String searchCardProfileClientId(@RequestParam(value = "clientId") String clientId) {
        CustomerBasicInfoEntity customerEntity = customerBasicInfoService.findByClientId(clientId);
        CardAccountBasicInfo cardBasicInfo = cardAccountBasicService.getByClientId(clientId);
        if (customerEntity == null || cardBasicInfo == null) {
            return "redirect:/profile/loanprofile";
        }
        return "redirect:/collection/card/profile?custid=" + customerEntity.getId() + "&cardid=" + cardBasicInfo.getId();
    }

    @GetMapping("/searchlist")
    public String searchCardFromList(@RequestParam(value = "contractNo") String contractNo, @RequestParam(value = "clientId") String clientId) {
        CustomerBasicInfoEntity customerEntity = customerBasicInfoService.findByContractIdAndClientId(contractNo, clientId);
        CardAccountBasicInfo cardBasicInfo = cardAccountBasicService.getByContractIdAndClientId(contractNo,clientId);
        if (customerEntity == null || cardBasicInfo == null) {
            return "redirect:/profile/loanprofile";
        }
        return "redirect:/collection/card/profile?custid=" + customerEntity.getId() + "&cardid=" + cardBasicInfo.getId();
    }


    @GetMapping("/profile")
    public String profileLoanDetails(Model model, @RequestParam(value = "custid") Long customerId,
                                     @RequestParam(value = "cardid") String cardId) throws ParseException {
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("dd-MMM-YYYY");

        CardAccountBasicInfo cardAccountBasicInfo = cardAccountBasicRepository.findById(new Long(cardId)).get();
        String contractNo = cardAccountBasicInfo.getContractId();
        CardAccountInfo cardAccountInfo = cardAccountService.findByCardAccountBasicId(cardAccountBasicInfo).get(0);
        CollectionReportOne collectionReportOne =
                collectionReportOneRepository.
                        getByContractNoAndCreatedDate(contractNo, simpleDateFormat1.format(new Date()).toUpperCase()) == null
                        ? new CollectionReportOne() : collectionReportOneRepository.
                        getByContractNoAndCreatedDate(contractNo, simpleDateFormat1.format(new Date()).toUpperCase());

        CollectionReportTwo collectionReportTwo =
                collectionReportTwoRepository.
                        getByContractNoAndCreatedDate(contractNo, simpleDateFormat1.format(new Date()).toUpperCase()) == null
                        ? new CollectionReportTwo() : collectionReportTwoRepository.
                        getByContractNoAndCreatedDate(contractNo, simpleDateFormat1.format(new Date()).toUpperCase());

        List<CollectionReportThree> collectionReportThree =
                collectionReportThreeRepository.
                        getByContractNoAndCreatedDate(contractNo, simpleDateFormat1.format(new Date()).toUpperCase()).size()<1
                        ? new ArrayList<>() : collectionReportThreeRepository.
                        getByContractNoAndCreatedDate(contractNo, simpleDateFormat1.format(new Date()).toUpperCase());

        List<EmiEntity> emiEntity = emiRepository.findByContractId(contractNo) == null ? new ArrayList<>() : emiRepository.findByContractId(contractNo);
        SourceChannel sourceChannel = sourceChannelRepository.findByContractId(contractNo) == null ? new SourceChannel():sourceChannelRepository.findByContractId(contractNo);
       TotalEmiDues totalEmiDues = new TotalEmiDues();//cardApiService.getTotalEmiDuesByContractNo(contractNo);

//        TotalEmiDues totalEmiDues = cardApiService.getTotalEmiDuesByContractNo(contractNo);
//        TotalEmiDues totalEmiDues = new TotalEmiDues();


        SimpleDateFormat format = new SimpleDateFormat("MMM-YYYY");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);

        String dates = format.format(cal.getTime()).toUpperCase();
        List<PaymentDuringMonthDto> paymentDuringMonthDtoList = new ArrayList<>();


//       List<PaymentDuringMonthDto> paymentDuringMonthDto = cardApiService.getPaymentInfoDuringMonth(contractNo, dates);
       List<PaymentDuringMonthDto> paymentDuringMonthDto = new ArrayList<>();


        PaymentDuringMonthDto paymentDuringMonthDto1 = new PaymentDuringMonthDto();

        if(paymentDuringMonthDto.size() >0) {
            paymentDuringMonthDto1 = paymentDuringMonthDto.get(0);

            paymentDuringMonthDto1.setBdtPayment(paymentDuringMonthDto1.getBdtPayment());
            paymentDuringMonthDto1.setUsdPayment(paymentDuringMonthDto1.getBdtPayment());
            paymentDuringMonthDto1.setContractNo(paymentDuringMonthDto1.getContractNo());
        }

        model.addAttribute("paymentInfoDuringMonth", paymentDuringMonthDto1);

        double curntTotalpayBdt = 0;
        double curntTotalpayUsd = 0;
        Calendar cal2 = Calendar.getInstance();
        String currentMonth = format.format(cal2.getTime()).toUpperCase();
//        List<PaymentDuringMonthDto> currentMnthPayment = cardApiService.getPaymentInfoDuringMonth(contractNo, currentMonth);
        List<PaymentDuringMonthDto> currentMnthPayment = new ArrayList<>();
        for(PaymentDuringMonthDto duringMonthDto: currentMnthPayment){
            curntTotalpayBdt += Double.parseDouble(duringMonthDto.getBdtPayment());
            curntTotalpayUsd += Double.parseDouble(duringMonthDto.getUsdPayment());
        }
        Calendar curr = Calendar.getInstance();
        String currentDate1 = format.format(cal.getTime()).toUpperCase();

//        List<PaymentDuringMonthDto> paymentDuringMonthDtoList1 =  cardApiService.getPaymentInfoDuringMonth(contractNo, currentDate1);
        List<PaymentDuringMonthDto> paymentDuringMonthDtoList1 =  new ArrayList<>();

        Double currBdtPayment = 0.00;
        Double CurrUsdPayment = 0.00;

        for ( PaymentDuringMonthDto payment:paymentDuringMonthDtoList1){
            currBdtPayment +=payment.getCurrBdtPayment();
            CurrUsdPayment +=payment.getCurrUsdPayment();
        }

        model.addAttribute("currBdtPayment",currBdtPayment);
        model.addAttribute("CurrUsdPayment",CurrUsdPayment);



        SimpleDateFormat simpleDateFormat3 = new SimpleDateFormat("dd-MMM-YY");
        String currDate = simpleDateFormat3.format(new Date()).toUpperCase();




//        RecoveryViewDTO autoPayAcBdtObj = recoveryViewService.getRecoveryView(cardAccountInfo.getDomAccountNo(), currDate);
        RecoveryViewDTO autoPayAcBdtObj = null;
//        RecoveryViewDTO autoPayAcUsdObj = recoveryViewService.getRecoveryView(cardAccountInfo.getIntAccountNo(), currDate);
        RecoveryViewDTO autoPayAcUsdObj = null;



        model.addAttribute("autoPayAcBdt",autoPayAcBdtObj ==null? new RecoveryViewDTO():autoPayAcBdtObj );
        model.addAttribute("autoPayAcUsd", autoPayAcUsdObj ==null? new RecoveryViewDTO(): autoPayAcUsdObj);


        ProfessionSegment professionSegment = professionSegmentRepository.findByContractId(contractNo) == null ? new ProfessionSegment() : professionSegmentRepository.findByContractId(contractNo);
        YearMonth thisMonth    = YearMonth.now();
        YearMonth lastMonth    = thisMonth.minusMonths(1);
        YearMonth twoMonthsAgo = thisMonth.minusMonths(2);
        List<String>monthNumber = new ArrayList<>();
        List<String>dealerName = new ArrayList<>();
        monthNumber.add(String.valueOf(lastMonth).substring(5));
        monthNumber.add(String.valueOf(twoMonthsAgo).substring(5));


        List<Tuple> tuples = cardAccountDistributionRepository.findPreviousTwoMonthDealerByClientId(cardAccountBasicInfo.getClientId(),monthNumber);
        for(Tuple t : tuples){
            dealerName.add(t.get("months").toString()+'-'+t.get("DEALER_NAME").toString());
        }

        ProfileInformation profileInfo = new ProfileInformation();
        PersonalInformation personalInfo = new PersonalInformation();
        AccountInformation accountInformation=new AccountInformation();

        CustomerBasicInfoEntity customer = cardAccountBasicInfo.getCustomer();

        personalInfo.setEmailAddress1(customer.getEmail());
        personalInfo.setFatherName(customer.getCustomerFatherName());
        personalInfo.setMotherName(customer.getCustomerMotherName());
        personalInfo.setSpouseName(customer.getSpouseName());
        personalInfo.setPassport(customer.getPassportNo());
        personalInfo.setNid(customer.getNid());
        personalInfo.setProfessionSegment(professionSegment.getProfessionSegment());
//        personalInfo.setSpouseOccupation();


        profileInfo.setAccountName(customer.getCustomerName());
        //profileInfo.setOccupation(customer.getOccupation());
        profileInfo.setDesignation(customer.getDesignation());
//        profileInfo.setNatureOfBusiness();
        profileInfo.setCompanyName(customer.getCompanyName());
        profileInfo.setCustomerName(customer.getCustomerName());
        profileInfo.setCompanyName(customer.getCompanyName());
        profileInfo.setDateofBirth(customer.getDob() != null ? new SimpleDateFormat("dd-MMM-yyyy").format(customer.getDob()) : "");
        profileInfo.setGender(customer.getSex());
        profileInfo.setHomeAddress(customer.getResAddress());
        profileInfo.setOfficeAddress(customer.getCAddress());



        //account information
        accountInformation.setClientId(cardAccountBasicInfo.getClientId());
        accountInformation.setContractNo(cardAccountBasicInfo.getContractId());
        accountInformation.setFileNo(customer.getFileNo());
        accountInformation.setCardNo(cardAccountBasicInfo.getCardNo());
        accountInformation.setCardType(customer.getCardType());
        accountInformation.setCardProfile(customer.getCardProfile());
        accountInformation.setTitle(customer.getTitle());
        accountInformation.setCardState(customer.getCardState());
        accountInformation.setCardStatus(customer.getCardStatus());
        accountInformation.setCardProductName(customer.getCardProductName());
        accountInformation.setMarketBy(customer.getMarketBy());

        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher=null;
        if(customer.getDeliveryInstr()!=null)
            matcher = pattern.matcher(customer.getDeliveryInstr());

        if (matcher !=null && matcher.matches() ){
            accountInformation.setDeliveryInst("Email");
        }else {
            accountInformation.setDeliveryInst("Courrier");
        }

        if (cardAccountInfo.getState() !=null) {
            if (cardAccountInfo.getState().equalsIgnoreCase("92[R]")) {
                accountInformation.setRegularWriteOff("Write Off");
            } else {
                accountInformation.setRegularWriteOff("Regular");
            }
        }


        accountInformation.setResCity(customer.getResCity());
        accountInformation.setResRegion(customer.getResRegion());
        accountInformation.setResAddress(customer.getResAddress());
        accountInformation.setRegCity(customer.getRegCity());
        accountInformation.setRegRegion(customer.getRegRegion());
        accountInformation.setCompanyName(customer.getCompanyName());

        accountInformation.setCompanyAddress(customer.getCAddress());

        accountInformation.setBillAddress(customer.getBillAddress());
        accountInformation.setHomeAddress(customer.getResAddress());
        accountInformation.setTelephoneNo(customer.getTelephone());
        accountInformation.setFaxNo(customer.getFax());
        accountInformation.setMobileNo(customer.getMobileNo());
        accountInformation.setEmailNo(customer.getEmail());
        accountInformation.setTin(customer.getTin());
        //accountInformation.setPassport(customer.getPppass());
        accountInformation.setCreatedDate(customer.getCreatedDate() != null ? new SimpleDateFormat("dd-MMM-yyyy").format(customer.getCreatedDate()) : "");
        accountInformation.setGivenDate(customer.getGivenDate() != null ? new SimpleDateFormat("dd-MMM-yyyy").format(customer.getGivenDate()) : "");
        accountInformation.setCloseDate(customer.getCloseDate() != null ? new SimpleDateFormat("dd-MMM-yyyy").format(customer.getCloseDate()) : "");
        accountInformation.setAnniDate(customer.getAnniveDate() != null ? new SimpleDateFormat("dd-MMM-yyyy").format(customer.getAnniveDate()) : "");
        accountInformation.setExpDate(customer.getExpDate() != null ? new SimpleDateFormat("dd-MMM-yyyy").format(customer.getExpDate()) : "");
        //accountInformation.setContState();

        //accountInformation.setDomLimitAmt(cardAccountInfo.getBdtLimit()+"");

        //BDT
        accountInformation.setDomAccountNo(cardAccountInfo.getDomAccountNo());
        accountInformation.setDomLimitAmt(collectionReportOne.getBdtLimit());
        accountInformation.setDomOutstandingAmt(collectionReportOne.getCurrBdtOs());
        accountInformation.setDomMinDueAmt(collectionReportOne.getStMinPayBdt());
        accountInformation.setStatementOsBDT(collectionReportOne.getStOsBdt());
        accountInformation.setAutoPayBDT(cardAccountInfo.getAutoPayBdt());
        accountInformation.setAutoPayAccBDT(cardAccountInfo.getAutoPayAcBdt());
        double olBdt = 0;
        if(collectionReportOne.getBdtLimit() != null && Double.parseDouble(collectionReportOne.getBdtLimit()) > 0.0)
            olBdt = (Double.parseDouble(collectionReportOne.getCurrBdtOs()) - Double.parseDouble(collectionReportOne.getBdtLimit()));
        accountInformation.setOverLimitAmtBDT(String.valueOf(olBdt));
        //accountInformation.setDomMinDueAmt(cardAccountInfo.getBdtMinDue()+"");
        //accountInformation.setP
        accountInformation.setAutoPayAccountIndicatorBdt(cardAccountInfo.getAutoPayBdt());



        //USD
        accountInformation.setIntAccNo(cardAccountInfo.getIntAccountNo());
        accountInformation.setLimitAmtInt(collectionReportOne.getUsdLimit());
        accountInformation.setStatementOsUSD(collectionReportOne.getStOsUsd());
        accountInformation.setOutstandingAmtInt(collectionReportOne.getCurrUsdOs());
        accountInformation.setMinDueAmtInt(collectionReportOne.getStMinPayUsd());
        accountInformation.setAutoPayUSD(cardAccountInfo.getAutoPayUsd());
        accountInformation.setAutoPayAccUSD(cardAccountInfo.getAutoPayAcUsd());
        double olUsd = 0;
        if(collectionReportOne.getUsdLimit()!=null && Double.parseDouble(collectionReportOne.getUsdLimit()) > 0.0)
            olUsd = (Double.parseDouble(collectionReportOne.getCurrUsdOs()) - Double.parseDouble(collectionReportOne.getUsdLimit()));
        accountInformation.setOverLimitAmtUSD(String.valueOf(olUsd));

        accountInformation.setAutoPayAccountIndicatorUsd(cardAccountInfo.getAutoPayUsd());

        accountInformation.setRiskGroup(cardAccountInfo.getRiskGroup());
        accountInformation.setState(cardAccountInfo.getState());
        accountInformation.setNoOfDays(cardAccountInfo.getNoOfDays());
        accountInformation.setAgeCode(cardAccountInfo.getAge());

        SimpleDateFormat formatter1=new SimpleDateFormat("dd/MM/yyyy");

        String statementDate = collectionReportOne.getStatementDate() != null ? collectionReportOne.getStatementDate():"";
        String payDueDate = collectionReportOne.getPayDueDate()!=null ? collectionReportOne.getPayDueDate():"";

        accountInformation.setStatementDate(statementDate);
        accountInformation.setDueDate(payDueDate);

        /*Date date1=null;
        Date date2=null;

        if (statementDate != "") {
           date1 = formatter1.parse(statementDate);
            accountInformation.setStatementDate(date1.toString());
        }

        if (payDueDate != "") {
            date2 = formatter1.parse(payDueDate);
            accountInformation.setDueDate(date2.toString());
        }
*/
       /* accountInformation.setStatementDate(collectionReportOne.getStatementDate() != null ? new SimpleDateFormat("dd-MMM-yyyy").format(collectionReportOne.getStatementDate()):"");
        accountInformation.setDueDate(collectionReportOne.getPayDueDate()!=null ? new SimpleDateFormat("dd-MMM-yyyy").format(collectionReportOne.getPayDueDate()):"");*/





        accountInformation.setBranchName(collectionReportTwo.getBranchValue());
        accountInformation.setCardName(customer.getNameOnCard());

        accountInformation.setCustomerAddress(customer.getHomeAddress());
        accountInformation.setProductTypeName(collectionReportOne.getCardProduct());
        accountInformation.setRewardPoint(collectionReportTwo.getRewardPoint());
        accountInformation.setBankStuff(collectionReportOne.getCardProduct() != null ? collectionReportOne.getCardProduct().equalsIgnoreCase("employee")?"Y":"N":"");

        double totalPaidAmt = 0.0;
        if(collectionReportTwo.getTotalBdtPay() != null && collectionReportTwo.getTotalUsdPay() != null)
            totalPaidAmt = Double.parseDouble(collectionReportTwo.getTotalBdtPay()) + Double.parseDouble(collectionReportTwo.getTotalUsdPay());
        accountInformation.setTotalPaidAmt(String.valueOf(totalPaidAmt));

        //EMI
        accountInformation.setTotalEmiAmt(totalEmiDues.getTotalEMIAmount());
        accountInformation.setTotalPaidEmi(totalEmiDues.getTotalPaidEMIAmount());
        accountInformation.setTotalUnpaidEmiAmt(totalEmiDues.getTotalUnpaidEMIAmount());

        double emiBdtTotal = 0.0;
        double emiUsdTotal = 0.0;
        for(EmiEntity emiEntity1 : emiEntity){
            emiBdtTotal+=emiEntity1.getBdtEmi();
            emiUsdTotal+=emiEntity1.getUsdEmi();
        }
        accountInformation.setUsdEmiTotal(String.valueOf(emiUsdTotal));
        accountInformation.setBdtEmiTotal(String.valueOf(emiBdtTotal));

        accountInformation.setSourceChannel(sourceChannel.getSource());

        accountInformation.setIssueDate(customer.getCreateDate() != null ? new SimpleDateFormat("dd-MMM-yyyy").format(customer.getCreateDate()) : "");
        if(customer.getEmail() == null || customer.getEmail().equals(""))
            accountInformation.setStatementDelivery("Hard Copy");
        else
            accountInformation.setStatementDelivery("Email");

        accountInformation.setActivationStatus(customer.getGivenDate() != null ?  new SimpleDateFormat("dd-MMM-yyyy").format(customer.getGivenDate()) : "");

        if(customer.getCardProductName() == null)
            accountInformation.setVvip("No");
        else if(customer.getCardProductName().contains("DNF") || customer.getCardProductName().contains("VIP") || customer.getCardProductName().contains("Director"))
            accountInformation.setVvip("Yes");
        //accountInformation.set

        accountInformation.setCreditShield(collectionReportTwo.getCreditShield());
        accountInformation.setEmbossDate(cardAccountBasicInfo.getCreatedDate().toString());
        accountInformation.setStatusCd(customer.getCardStatus());
        accountInformation.setFirstUseDate(collectionReportTwo.getFirstTxnDate());

        Double autoPayAcBdt = 0.00;
        Double autoPayAcUsd= 0.00;
        if (cardAccountInfo.getAutoPayAcBdt() !=null){
            autoPayAcBdt = Double.parseDouble(cardAccountInfo.getAutoPayAcBdt());
        }
        if (cardAccountInfo.getAutoPayAcUsd() !=null ){
            autoPayAcUsd = Double.parseDouble(cardAccountInfo.getAutoPayAcUsd());
        }

        /*if(autoPayAcBdt>0 && autoPayAcUsd >0){
            accountInformation.setTransactionMode("MULTI");
        }else if(autoPayAcBdt>0 && autoPayAcUsd<=0){
            accountInformation.setTransactionMode("BDT");
        } else if(autoPayAcBdt<=0 && autoPayAcUsd>0){
            accountInformation.setTransactionMode("USD");
        }*/

        if(customer.getCardState()!=null){
            if((customer.getCardState().equalsIgnoreCase("Given") || customer.getCardState().equalsIgnoreCase("Pin Generated") || customer.getCardState().equalsIgnoreCase("Pin Generated(G)")
                    && customer.getCardStatus().equalsIgnoreCase("Open"))){
                accountInformation.setTransactionMode("Yes");
            }else{
                accountInformation.setTransactionMode("No");
            }
        }


        Integer age = cardAccountInfo.getAge() ==null?0:Integer.parseInt(cardAccountInfo.getAge());

        if (age>=0 && age<=1){
            accountInformation.setClStatus("UC");
        }else if(age ==2){
            accountInformation.setClStatus("SMA");
        }else if(age>=3 && age <=8){
            accountInformation.setClStatus("SS");
        }else if(age>=9 && age <=11){
            accountInformation.setClStatus("DF");
        }else if(age>=12){
            accountInformation.setClStatus("DF");
        }



            Double nonStarterFlag = 0.00;

        if(collectionReportOne.getCurrBdtOs() !=null)
            nonStarterFlag =  Double.parseDouble(collectionReportOne.getCurrBdtOs());

        if (nonStarterFlag>=0 ) {
            accountInformation.setNonStarterFlag("Active");
        }else {
            accountInformation.setNonStarterFlag("Not Active");
        }

        List<AgingHistory> agingHistories = agingHistoryRepository.findAllByContractNo(contractNo);

        model.addAttribute("agingHistories",agingHistories);




        List<CardOtherAccountInfo> otherAccountInfoList =
                cardOtherAccountInfoRepository.findByCustomerBasicInfoEntity(cardAccountBasicInfo.getCustomer());
        Gson gson = new Gson();
        model.addAttribute("cardOtherInfoList", gson.toJson(otherAccountInfoList.stream()
                .map(o -> o.getOtherAccountNo()).collect(Collectors.toList())));

        CardAccountDistributionInfo cardAccountDistributionInfo =
                cardAccountDistributionRepository.findFirstByCardAccountBasicInfoOrderByCreatedDateDesc(cardAccountBasicInfo);
        if (cardAccountDistributionInfo != null) {
            if (cardAccountDistributionInfo.getSamAccount().equals("0") &&
                    cardAccountDistributionInfo.getWriteOffAccount().equals("0")) {
                CardAgencyDistributionInfo cardAgencyDistributionInfo =
                        cardAgencyDistributionRepository.findFirstByCardAccountBasicInfoOrderByCreatedDateDesc(cardAccountBasicInfo);
                if (cardAgencyDistributionInfo == null)
                    model.addAttribute("distributionInfo", cardAccountDistributionInfo);
                else {
                    boolean lastDistribution = cardAccountDistributionInfo.getCreatedDate()
                            .after(cardAgencyDistributionInfo.getCreatedDate());
                    if (lastDistribution)
                        model.addAttribute("distributionInfo", cardAccountDistributionInfo);
                    else {
                        if (cardAgencyDistributionInfo.getLoanDualEnum().toString().equals("DUAL")) {
                            model.addAttribute("distributionInfo", cardAccountDistributionInfo);
                            model.addAttribute("distributionInfoAgency", cardAgencyDistributionInfo);
                        } else {
                            cardAccountDistributionInfo.setDealerName("");
                            cardAccountDistributionInfo.setDealerPin("");
                            model.addAttribute("distributionInfo", cardAccountDistributionInfo);
                            model.addAttribute("distributionInfoAgency", cardAgencyDistributionInfo);
                        }
                    }
                }
            } else {
                cardAccountDistributionInfo.setDealerName("");
                cardAccountDistributionInfo.setDealerPin("");
                model.addAttribute("distributionInfo", cardAccountDistributionInfo);
                if (cardAccountDistributionInfo.getSamAccount().equals("1")) {
                    SamAccountHandoverInfo samDetails = samAccountHandoverRepository
                            .findFirstByCreatedDateIsBetweenAndCardAccountBasicInfoAndLatest(
                                    dateUtils.getMonthStartDate(), dateUtils.getMonthEndDate(),
                                    cardAccountDistributionInfo.getCardAccountBasicInfo(), "1");
                    if (samDetails != null)
                        model.addAttribute("samDetails", samDetails);
                }
                if (cardAccountDistributionInfo.getWriteOffAccount().equals("1")) {
                    WriteOffAccountInfo writeOffDetails = writeOffAccountRepository
                            .findFirstByCreatedDateIsBetweenAndCardAccountBasicInfoOrderByCreatedDateDesc(
                                    dateUtils.getMonthStartDate(), dateUtils.getMonthEndDate(),
                                    cardAccountDistributionInfo.getCardAccountBasicInfo());
                    if (writeOffDetails != null)
                        model.addAttribute("writeOffDetails", writeOffDetails);
                }
            }
        } else {
            CardAccountDistributionInfo cardAccountDistributionInfo1 = new CardAccountDistributionInfo();
            cardAccountDistributionInfo1.setSamAccount("0");
            cardAccountDistributionInfo1.setWriteOffAccount("0");
            cardAccountDistributionInfo1.setDealerName("");
            cardAccountDistributionInfo1.setDealerPin("");
            model.addAttribute("distributionInfo", cardAccountDistributionInfo1);
        }

       // accountInformation.setCreditShield(collectionReportTwo.getCreditShield());

        if (cardAccountDistributionInfo !=null) {

            if (cardAccountDistributionInfo.getClientId().equals(cardAccountBasicInfo.getClientId())) {
                accountInformation.setMonitoringStatus("Allocated");
            } else {
                accountInformation.setMonitoringStatus("Non Allocated");
            }
        }

        Double totalBdtPay = 0.00;
        if(collectionReportOne.getCurrBdtOs() !=null)
            totalBdtPay = Double.parseDouble(collectionReportOne.getCurrBdtOs());

        Double totalUsdPay = 0.00;
        if(collectionReportOne.getCurrUsdOs() !=null)
            totalUsdPay = Double.parseDouble(collectionReportOne.getCurrUsdOs());


        ExchangeRateEntity exchangeRateEntity = exchangeRateService.findExchaneRate();

        Double usdToBdtConversion =0.00;
        if (cardAccountDistributionInfo !=null) {
            if (cardAccountDistributionInfo.getBdtUsdConversionRate() != null) {
                usdToBdtConversion = cardAccountDistributionInfo.getBdtUsdConversionRate();
            }
        }


        accountInformation.setTotalPaidAmount(totalBdtPay+(totalUsdPay*usdToBdtConversion));




                List<VisitLedgerEntity> visitLedgerEntities = visitLedgerRepository.findByAccountCardNumberOrderByIdDesc(contractNo);
        visitLedgerEntities.forEach(v -> {
            EmployeeInfoEntity employeeInfoEntity = employeeRepository.findByPin(v.getName());
            v.setName(employeeInfoEntity.getUser().getFirstName());
        });

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String currentDateString = simpleDateFormat.format(new Date());
        Date currentDate = simpleDateFormat.parse(currentDateString);

        SecuredCardCriteriaEntity securedCardLatest = securedCardCriteriaService.getSecuredCardLatest();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String dateFormatString = dateFormat.format(currentDate);

        MiscellaneousThreeSixtyFields mtsf = retrieveMiscellaneousFields(dateFormatString, contractNo);
        model.addAttribute("mtsf", mtsf);

        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("dd-MMM-YY");

        Date startDate1 = dateUtils.getMonthStartDate();
        String startDate2 = simpleDateFormat2.format(startDate1).toUpperCase();
        Date endDate1 = dateUtils.getMonthEndDate();
        String endDate2 = simpleDateFormat2.format(endDate1).toUpperCase();

        AllocationDetailsDTO allocationDetailsDTO = new AllocationDetailsDTO();//allocationDetailsService.findCardAccountInfoByContractNoAndClientId(contractNo, startDate2, endDate2, cardAccountBasicInfo.getClientId());

        String distributeDate = allocationDetailsDTO.getDistributionDate();

        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date =null;
        if (distributeDate !=null)
            date = dt.parse(distributeDate);

        SimpleDateFormat dt1 = new SimpleDateFormat("dd-MMM-YYYY");

        String distributeDate1 =  "";

        if (date !=null)
            distributeDate1 = dt1.format(date).toUpperCase();

        CollectionReportOne collectionReportOne1 =collectionReportOneRepository.getByContractNoAndCreatedDate(contractNo, distributeDate1);

        if(collectionReportOne1 !=null) {
            model.addAttribute("allocationDetails", collectionReportOne1);
            model.addAttribute("distributeDate1", distributeDate1);
        }else {
            model.addAttribute("allocationDetails", new CollectionReportOne());
            model.addAttribute("distributeDate1", "N/A");
        }

        // final sattlement calculator

        FinalSattlementCalculatorDTO finalSattlementCalculatorDTO = new FinalSattlementCalculatorDTO();

        Double presentOutStanding = 0.00;
        Double currUsdOs = 0.00;

        if( collectionReportOne.getCurrBdtOs() != null){
            presentOutStanding = Double.parseDouble(collectionReportOne.getCurrBdtOs());
        }
        if( collectionReportOne.getCurrUsdOs() != null){
            currUsdOs = Double.parseDouble(collectionReportOne.getCurrUsdOs());
        }

        finalSattlementCalculatorDTO.setPresentOutstanding(presentOutStanding+(currUsdOs*usdToBdtConversion));

        ApproveTransactionsUnbilledTransactionsPayment approveTransactionsUnbilledTransactionsPayment =
                approveTransactionsUnbilledTransactionsPaymentRepository.
                        findTrxnApprovalHistoryByContractNo(contractNo, simpleDateFormat1.format(new Date()).toUpperCase());

        if (approveTransactionsUnbilledTransactionsPayment !=null) {

            Double trxnAmount = 0.00;

            if (approveTransactionsUnbilledTransactionsPayment.getTxnAmount() !=null){
                trxnAmount = Double.parseDouble(approveTransactionsUnbilledTransactionsPayment.getTxnAmount());
            }

            finalSattlementCalculatorDTO.setUnbilledAmount(trxnAmount);
        }else {
            finalSattlementCalculatorDTO.setUnbilledAmount(0.00);
        }

        model.addAttribute("finalSattlementCalculatorDTO",finalSattlementCalculatorDTO);


        List<ApproveTransactionsUnbilledTransactionsPayment> approveTransactionList = approveTransactionsUnbilledTransactionsPaymentRepository.getTransactionListByContractId(contractNo);
        double totalTxnAmt = 0;
        for (ApproveTransactionsUnbilledTransactionsPayment obj: approveTransactionList){
            if (obj.getTxnType().equals("POS TXN") || obj.getTxnType().equals("ATM TXN") || obj.getTxnType().equals("CARD CHEQUE")){
                totalTxnAmt += new Double(obj.getTxnAmount());
            }
        }
                 Optional<String>cardStatus=Optional.ofNullable(customer.getCardState());
        if(cardStatus.isPresent()){
            if ((customer.getCardState().equals("Embossed") && customer.getCardStatus().equals("Declared")) &&(totalTxnAmt == 0)){
                accountInformation.setActiveInactiveStatus("Inactive And Nonstarter");
            }else if (((customer.getCardState().equals("Given") || customer.getCardState().equals("PIN-Generated") || customer.getCardState().equals("Renewal on Process")) && customer.getCardStatus().equals("Open")) &&(totalTxnAmt == 0)){
                accountInformation.setActiveInactiveStatus("Active And Nonstarter");
            }else if (((customer.getCardState().equals("Given") || customer.getCardState().equals("PIN-Generated") || customer.getCardState().equals("Renewal on Process")) && customer.getCardStatus().equals("Open")) &&(totalTxnAmt > 0)){
                accountInformation.setActiveInactiveStatus("Active And Starter");
            }else if (((customer.getCardState().equals("Embossed") || customer.getCardState().equals("Renewal on Process")) && (customer.getCardStatus().equals("Declared") || customer.getCardStatus().equals("Open"))) &&(totalTxnAmt > 0)){
                accountInformation.setActiveInactiveStatus("inactive And Starter");
            }else if (cardAccountInfo.getState().equals("97[C]") || cardAccountInfo.getState().equals("97[V]")){
                accountInformation.setActiveInactiveStatus("Cancel");
            }
        }






        CardBackendAccDetailsEntity cardBackendAccDetailsEntity =
                cardBackendAccDetailDao.getByAccNo(contractNo, dateUtils.getMonthStartDate(), dateUtils.getMonthEndDate());

        Gson gson1 = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

        List<EmiEmtityDto> emiEntities = emiService.findContractByDateRange(contractNo);

        double bdtEmiAmt = 0;
        double usdEmiAmt = 0;

        for (EmiEmtityDto emiEmtityDto: emiEntities){
            bdtEmiAmt += emiEmtityDto.getBdtEmi();
            usdEmiAmt += emiEmtityDto.getUsdEmi();
        }

        model.addAttribute("bdtEmiAmt", bdtEmiAmt);
        model.addAttribute("usdEmiAmt", usdEmiAmt);

        model.addAttribute("securedcard", securedCardSampleRepository.findFirstBySecuredCard(contractNo));
        model.addAttribute("location", accountLocationMappingRepository.findFirstByCustomer(cardAccountBasicInfo.getCustomer()));
        model.addAttribute("agecode", gson.toJson(ageAndClassificationRuleEntityRepository.findAll()));
        model.addAttribute("secured", gson.toJson(securedCardLatest));
        model.addAttribute("cardBackend", cardBackendAccDetailsEntity);
        model.addAttribute("approvedPayment", 0.0);
        model.addAttribute("approvedTransaction", 0.0);
        model.addAttribute("approvedCash", 0.0);
        model.addAttribute("approvedIPP", 0.0);
        model.addAttribute("billingCycle", "");
        model.addAttribute("statementDate", "");

        model.addAttribute("hotNotes", cardHotNotesRepository.findByCustomerBasicInfoIdOrderByCreatedDateDesc(customerId));
        model.addAttribute("vipStatusList", vipStatusService.getActiveList());

        model.addAttribute("dairyNotes", dairyNotesService.findByCustomerId(customerId));
        model.addAttribute("dailyNotes", dailyNotesRepository.findByCustomerBasicInfoIdOrderByCreatedDateDesc(customerId));
        model.addAttribute("ptps",
                gson1.toJson(cardPtpRepository.findByEnabledIsAndCustomerBasicInfoOrderByCreatedDateDesc(true, cardAccountBasicInfo.getCustomer())));
        model.addAttribute("folllowups", cardFolllowUpService.findByCustomerId(customerId));
        model.addAttribute("visitLedger", visitLedgerEntities);
        model.addAttribute("cardCustomerInfo", Hibernate.unproxy(cardAccountBasicInfo.getCustomer()));
        model.addAttribute("customerId", customer.getId());
        model.addAttribute("cardAccountBasic", cardAccountBasicInfo);
        model.addAttribute("letterInformation", letterInformationRepository.findByAccountNo(contractNo));
        List<String> prevCollectors = new ArrayList<>(dealerName);
        model.addAttribute("prevCollectors", prevCollectors);
        model.addAttribute("profileInformation", profileInfo);
        model.addAttribute("personalInformation", personalInfo);
        model.addAttribute("contractNo", contractNo);
        model.addAttribute("accountInformation", accountInformation);
        model.addAttribute("reasonDelis", gson1.toJson(reasonDelinquencyCardService.findReasonDelinquencyCardById(customerId)));

        //model.addAttribute("unbilledTransaction",collectionReportService.findAllByContractNo(contractNo));
        model.addAttribute("unbilledTransaction",collectionReportOneService.findAllByContractNo(contractNo));
//        model.addAttribute("transactionApprovalHistory",collectionReportOneService.findAllTrxnApprovalHistoryByContractNo(contractNo));
        model.addAttribute("transactionApprovalHistory",collectionReportOneService.getLastThreeMonthTransactionApprovalHistory(contractNo));
       // model.addAttribute("transactionApprovalHistoryCardNo", collectionReportService.findCardNoByContractNo(contractNo));

        TransactionApprovalHistoryHeaderDTO transactionApprovalHistoryHeaderDTO = collectionReportService.findCardNoByContractNoAndClientId(contractNo, cardAccountBasicInfo.getClientId());

        model.addAttribute("transactionApprovalHistoryCardNo",transactionApprovalHistoryHeaderDTO);

        model.addAttribute("unbilledTransactionHeader",collectionReportService.findUnbilledByContractNoAndClientId(contractNo, cardAccountBasicInfo.getClientId()));

        model.addAttribute("ptpsNotGson",
                cardPtpRepository.findByEnabledIsAndCustomerBasicInfoOrderByCreatedDateDesc(true, cardAccountBasicInfo.getCustomer()));
        model.addAttribute("reasonDelins", reasonDelinquencyCardService.findReasonDelinquencyCardById(customerId));
        model.addAttribute("contacts", contactInfoCardService.findAttemptCallListByCustomerId(customerId));
        model.addAttribute("dealerAllocationHistory", cardAccountDistributionService.getCardAccountDealerAllocationHistory(contractNo,dateUtils.getMonthStartDate(),dateUtils.getMonthEndDate(), cardAccountBasicInfo.getClientId()));
        model.addAttribute("payFlexDetails", collectionReportThreeRepository.findByContractNo(contractNo));
        model.addAttribute("loyalityProgram", collectionReportTwoRepository.findAllByContractNo(contractNo));
        model.addAttribute("marketBy",marketByRepository.findByContractId(contractNo));

        model.addAttribute("emiList", emiEntities);
        return "collection/cardprofile/details/main";
    }

    private MiscellaneousThreeSixtyFields retrieveMiscellaneousFields(String dateFormatString, String contractNo) {
        MiscellaneousThreeSixtyFields mtsf = new MiscellaneousThreeSixtyFields();

//        double approvedPayment = ptpSummaryDetailsDao.getApprovedPayment(dateFormatString, contractNo);
//        double approvedTransaction = ptpSummaryDetailsDao.getApprovedTransaction(dateFormatString, contractNo);
//        double approvedCash = ptpSummaryDetailsDao.getApprovedCash(dateFormatString, contractNo);
//        double approvedIPP = ptpSummaryDetailsDao.getApprovedIPP(dateFormatString, contractNo);
//        String billingCycle = ptpSummaryDetailsDao.getBillingCycle(contractNo);
//        String statementDate = ptpSummaryDetailsDao.getStatementDate(contractNo);
//
//        mtsf.setApprovedCash(approvedCash);
//        mtsf.setApprovedIPP(approvedIPP);
//        mtsf.setApprovedPayment(approvedPayment);
//        mtsf.setApprovedTransaction(approvedTransaction);
//        mtsf.setBillingCycle(billingCycle);
//        mtsf.setStatementDate(statementDate);

        return mtsf;
    }
}
