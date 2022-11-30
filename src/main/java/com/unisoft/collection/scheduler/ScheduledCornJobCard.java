package com.unisoft.collection.scheduler;

import com.unisoft.customerbasicinfo.CustomerBasicInfoEntity;
import com.unisoft.dataStore.loanListing.LoanListingService;
import com.unisoft.retail.card.dataEntry.distribution.accountBasicInfo.CardAccountBasicInfo;
import com.unisoft.retail.card.dataEntry.distribution.accountInfo.CardAccountInfo;
import com.unisoft.retail.card.dataEntry.ptp.CardPtp;
import com.unisoft.retail.card.dataEntry.ptp.CardPtpDao;
import com.unisoft.retail.card.dataEntry.ptp.CardPtpRepository;
import com.unisoft.cardprofile.regulardelenquent.RegularDelenquentAccountCardDao;
import com.unisoft.retail.card.dataEntry.distribution.accountOtherInfo.CardAccountOtherRepository;
import com.unisoft.retail.card.dataEntry.distribution.accountDistributionInfo.CardAccountDistributionRepository;
import com.unisoft.retail.card.dataEntry.distribution.accountInfo.CardAccountRepository;
import com.unisoft.retail.card.dataEntry.distribution.accountBasicInfo.CardAccountBasicRepository;
import com.unisoft.retail.card.dataEntry.distribution.accountDistributionInfo.CardAccountDistributionInfo;
import com.unisoft.collection.distribution.unallocated.UnallocatedCardList;
import com.unisoft.collection.distribution.unallocated.UnallocatedCardListRepository;
import com.unisoft.collection.settingshelper.SettingsHelper;
import com.unisoft.customerbasicinfo.CustomerBasicInfoEntityRepository;
import com.unisoft.customerloanprofile.ptpsummary.PtpSummaryDetailsDao;
import com.unisoft.retail.card.reporttextfileupload.*;
import com.unisoft.user.UserPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@EnableAsync
@Slf4j
@Component
public class ScheduledCornJobCard {
    private RegularDelenquentAccountCardDao regularDelenquentAccountCardDao;
    private CardAccountBasicRepository cardAccountBasicRepository;
    private CustomerBasicInfoEntityRepository customerBasicInfoEntityRepository;
    private UnallocatedCardListRepository unallocatedCardListRepository;
    private CardAccountOtherRepository cardAccountOtherRepository;
    private CardAccountRepository cardAccountRepository;
    private CardPtpDao cardPtpDao;
    private PtpSummaryDetailsDao ptpSummaryDetailsDao;
    private CardPtpRepository cardPtpRepository;
    private CardAccountDistributionRepository cardAccountDistributionRepository;

    @Autowired
    private CollectionReportOneRepository collectionReportOneRepository;

    @Autowired
    private CollectionReportTwoRepository collectionReportTwoRepository;

    @Autowired
    private CollectionReportThreeRepository collectionReportThreeRepository;

    @Autowired
    private CollectionReportFourRepository collectionReportFourRepository;

    @Autowired
    private LoanListingService loanListingService;

    @Autowired
    private Environment environment;

    @Autowired
    private AgingHistoryRepository agingHistoryRepository;

    @Autowired
    private InterestSuspenseReportRepository interestSuspenseReportRepository;

    @Autowired
    private ApproveTransactionsUnbilledTransactionsPaymentRepository approveTransactionsUnbilledTransactionsPaymentRepository;


    public ScheduledCornJobCard(RegularDelenquentAccountCardDao regularDelenquentAccountCardDao, CardAccountBasicRepository cardAccountBasicRepository, CustomerBasicInfoEntityRepository customerBasicInfoEntityRepository, UnallocatedCardListRepository unallocatedCardListRepository, CardAccountOtherRepository cardAccountOtherRepository, CardAccountRepository cardAccountRepository, CardPtpDao cardPtpDao, PtpSummaryDetailsDao ptpSummaryDetailsDao, CardPtpRepository cardPtpRepository, CardAccountDistributionRepository cardAccountDistributionRepository) {
        this.regularDelenquentAccountCardDao = regularDelenquentAccountCardDao;
        this.cardAccountBasicRepository = cardAccountBasicRepository;
        this.customerBasicInfoEntityRepository = customerBasicInfoEntityRepository;
        this.unallocatedCardListRepository = unallocatedCardListRepository;
        this.cardAccountOtherRepository = cardAccountOtherRepository;
        this.cardAccountRepository = cardAccountRepository;
        this.cardPtpDao = cardPtpDao;
        this.ptpSummaryDetailsDao = ptpSummaryDetailsDao;
        this.cardPtpRepository = cardPtpRepository;
        this.cardAccountDistributionRepository = cardAccountDistributionRepository;
    }

    @Async
    @Scheduled(cron = "0 0 5 * * ?")
    public void getDelenquentAccountCard() {
        log.info("DELENQUENT CARD ACCOUNT SCHEDULER INVOKED: " + new Date().toString());
        return;
//        List<RgularDelenquentAccountCard> rgularDelenquentAccountCardCard = regularDelenquentAccountCardDao.getRgularDelenquentAccountCardCard();
//
//        for(RgularDelenquentAccountCard r: rgularDelenquentAccountCardCard){
//            CardAccountBasicInfo cardAccountBasicInfo = cardAccountBasicRepository.findByCardNo(r.getCardNumber());
//            if(cardAccountBasicInfo == null){
//                CustomerBasicInfoEntity customerBasicInfoEntity = new CustomerBasicInfoEntity(r.getClientId(), r.getCardNumber(), r.getCustomerName(),"", "", "", "Card");
//                customerBasicInfoEntityRepository.save(customerBasicInfoEntity);
//                CardAccountBasicInfo cardAccountBasicInfo1 = new CardAccountBasicInfo(r.getClientId(), r.getCardNumber(), r.getCustomerName(), "", "", "", "", "", "", customerBasicInfoEntity);
//                cardAccountBasicRepository.save(cardAccountBasicInfo1);
//
//                UnallocatedCardList firstByCardAccountBasicInfo = unallocatedCardListRepository.findFirstByCardAccountBasicInfo(cardAccountBasicInfo1);
//                if(firstByCardAccountBasicInfo == null){
//                    UnallocatedCardList unallocatedCardList = new UnallocatedCardList();
//                    unallocatedCardList.setCardAccountBasicInfo(cardAccountBasicInfo1);
//                    unallocatedCardList.setAgeCode(r.getAgeCode());
//                    unallocatedCardList.setBillingCycle("");
//                    unallocatedCardList.setOutstandingAmount(r.getOutstanding());
//                    unallocatedCardList.setDealerPin("0");
//                    unallocatedCardList.setDealerName("0");
//                    unallocatedCardList.setSupervisorPin("0");
//                    unallocatedCardList.setSupervisorName("0");
//                    unallocatedCardList.setSamAccount("0");
//                    unallocatedCardList.setWriteOffAccount("0");
//                    unallocatedCardList.setVvip("");
//                    unallocatedCardList.setSecureCard("");
//                    unallocatedCardList.setCreatedDate(new Date());
//                    unallocatedCardList.setProductGroup(r.getProductType());
//                    unallocatedCardList.setPlasticCd("");
//                    unallocatedCardListRepository.save(unallocatedCardList);
//                }
//                CardAccountOtherInfo cardAccountOtherInfo = new CardAccountOtherInfo(cardAccountBasicInfo, "", "", "", "", "", "");
//                cardAccountOtherRepository.save(cardAccountOtherInfo);
//                CardAccountInfo cardAccountInfo = new CardAccountInfo(cardAccountBasicInfo, r.getProductType(), "", r.getOutstanding(), "", r.getAgeCode(), "", "", "");
//                cardAccountRepository.save(cardAccountInfo);
//            }else{
//                UnallocatedCardList firstByCardAccountBasicInfo = unallocatedCardListRepository.findFirstByCardAccountBasicInfo(cardAccountBasicInfo);
//                if(firstByCardAccountBasicInfo == null){
//                    UnallocatedCardList unallocatedCardList = new UnallocatedCardList();
//                    unallocatedCardList.setCardAccountBasicInfo(cardAccountBasicInfo);
//                    unallocatedCardList.setAgeCode(r.getAgeCode());
//                    unallocatedCardList.setBillingCycle("");
//                    unallocatedCardList.setOutstandingAmount(r.getOutstanding());
//                    unallocatedCardList.setDealerPin("0");
//                    unallocatedCardList.setDealerName("0");
//                    unallocatedCardList.setSupervisorPin("0");
//                    unallocatedCardList.setSupervisorName("0");
//                    unallocatedCardList.setSamAccount("0");
//                    unallocatedCardList.setWriteOffAccount("0");
//                    unallocatedCardList.setVvip("");
//                    unallocatedCardList.setSecureCard("");
//                    unallocatedCardList.setCreatedDate(new Date());
//                    unallocatedCardList.setProductGroup(r.getProductType());
//                    unallocatedCardList.setPlasticCd("");
//                    unallocatedCardListRepository.save(unallocatedCardList);
//                }
//            }
//        }

    }

    @Async
    @Scheduled(cron = "0 0 6 * * ?")
    public void updateCardPaymentAndPtpStatus(){
        log.info("PTP STATUS CARD SCHEDULER INVOKED: " + new Date().toString());

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = simpleDateFormat.format(new Date(cal.getTimeInMillis()));
        Date startDte = null;
        try {
            startDte = simpleDateFormat.parse(format);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
        String currentDateString = simpleDateFormat.format(new Date());
        Date currentDate = null;
        try {
            currentDate = simpleDateFormat.parse(currentDateString);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }

        List<CardPtp> cardPtpList = cardPtpDao.getCardPtpByCustomer(startDte);
        Map<Long, List<CardPtp>> customerToCardPtpMap = new HashMap<>();

        for(CardPtp cardPtp: cardPtpList){
            Long cid = cardPtp.getCustomerBasicInfo().getId();
            boolean b = customerToCardPtpMap.containsKey(cid);
            if(b) {
                List<CardPtp> cardPtps = customerToCardPtpMap.get(cid);
                cardPtps.add(cardPtp);
                customerToCardPtpMap.put(cid, cardPtps);
            }
            else {
                List<CardPtp> cardPtps = new ArrayList<>();
                cardPtps.add(cardPtp);
                customerToCardPtpMap.put(cid, cardPtps);
            }
        }

        for(Map.Entry<Long, List<CardPtp>> entry: customerToCardPtpMap.entrySet()){
            List<CardPtp> cardPtps = entry.getValue();
            cardPtps.sort((o1, o2) -> {
                if (o1.getCreatedDate() == null || o2.getCreatedDate() == null)
                    return 0;
                return o1.getCreatedDate().compareTo(o2.getCreatedDate());
            });
            String firstCreatedDateString = simpleDateFormat.format(cardPtps.get(0).getCreatedDate());
            Date firstCreatedDate = null;
            try {
                firstCreatedDate = simpleDateFormat.parse(firstCreatedDateString);
            } catch (ParseException e) {
                System.out.println(e.getMessage());
            }

            double payment = 0;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            String dateFormatString = dateFormat.format(startDte);
            String dateFormatCreatedDate = dateFormat.format(firstCreatedDate);
            String accountNo = cardPtps.get(0).getCustomerBasicInfo().getAccountNo();
            List<Double> ptpPaymentList = ptpSummaryDetailsDao.ptp(dateFormatCreatedDate,dateFormatString, accountNo);
            for(Double d: ptpPaymentList) payment += d;


            for(CardPtp l : cardPtps){
                double cardAmount = Double.parseDouble(l.getCard_amount());

                if(payment >= cardAmount){
                    l.setCard_ptp_status("cured");
                    payment = payment - cardAmount;

                }
                else l.setCard_ptp_status("broken");
                cardPtpRepository.save(l);
            }
        }
    }

    @Async
    @Scheduled(cron = "0 0 2 1 1/1 ?")
    public void getDelenquentAccountFromCardAccountDistributionInfo(){
        regularDelenquentAccountCardDao.deletePreviousMonthUnallocatedData();
        List<CardAccountDistributionInfo> cardAccountDistributionInfos = cardAccountDistributionRepository.findByCreatedDateIsBetweenAndSamAccountAndWriteOffAccountAndLatestOrderByCreatedDateDesc(SettingsHelper.getStartDate(), SettingsHelper.getEndDate(), "0", "0", "1");
        for(CardAccountDistributionInfo c: cardAccountDistributionInfos){
            UnallocatedCardList firstByCardAccountBasicInfo = unallocatedCardListRepository.findFirstByCardAccountBasicInfo(c.getCardAccountBasicInfo());
            if(firstByCardAccountBasicInfo == null){
                UnallocatedCardList unallocatedCardList = new UnallocatedCardList();
                unallocatedCardList.setCardAccountBasicInfo(c.getCardAccountBasicInfo());
                unallocatedCardList.setAgeCode(c.getAgeCode());
                unallocatedCardList.setBillingCycle(c.getBillingCycle());
                unallocatedCardList.setOutstandingAmount(c.getOutstandingAmount());
                unallocatedCardList.setDealerPin("0");
                unallocatedCardList.setDealerName("0");
                unallocatedCardList.setSupervisorPin("0");
                unallocatedCardList.setSupervisorName("0");
                unallocatedCardList.setSamAccount("0");
                unallocatedCardList.setWriteOffAccount("0");
                unallocatedCardList.setVvip(c.getVvip());
                unallocatedCardList.setSecureCard(c.getSecureCard());
                unallocatedCardList.setCreatedDate(new Date());
                unallocatedCardList.setProductGroup(c.getProductGroup());
                unallocatedCardList.setPlasticCd(c.getPlasticCd());
                unallocatedCardListRepository.save(unallocatedCardList);
            }

        }
    }

    boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }



    /*-----------------------------------------------------------------------*/
            /*Scheduler for txt file data upload starts from here ${card.scheduler.timer}*/
    /*-----------------------------------------------------------------------*/
    @Scheduled(cron = "${card.scheduler.timer}")
    public void getTextFileData() {
        try {
            File file = new File("D:/Tajkia Apu/Card text file latest/83_Report_Partial_05062022.txt");
            FileInputStream fstream = new FileInputStream(file);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String read;
            int i = 0;
            List<CardAccountBasicInfo> cardAccountBasicInfos = new ArrayList<>();
            List<CardAccountInfo> cardAccountInfos = new ArrayList<>();
            List<CustomerBasicInfoEntity> customerBasicInfoEntities = new ArrayList<>();
            while ((read = br.readLine()) != null) {
                String[] splicedLine = read.split("[|]");

                String[] trimmedArray = Arrays.stream(splicedLine).map(String::trim).toArray(String[]::new);

                if (splicedLine.length == 65) {
                    i++;
                    if (i > 1) {
                        System.out.println("---------------- 0: :"+trimmedArray[1]);
                        if(!trimmedArray[1].equals("") && !trimmedArray[2].equals("")) {
                            CardAccountInfo cardAccountInfo;
                            CardAccountBasicInfo cardAccountBasicInfo;
                            CustomerBasicInfoEntity customerBasicInfoEntity;
                            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

                            //get customer basic info entity by customer id
                            customerBasicInfoEntity = customerBasicInfoEntityRepository.findFirstByContractId(trimmedArray[2]);
                            if (customerBasicInfoEntity == null) {
                                customerBasicInfoEntity = new CustomerBasicInfoEntity();
                            }

                            //customer basic info data set
                            customerBasicInfoEntity.setClientId(trimmedArray[1]);
                            customerBasicInfoEntity.setContractId(trimmedArray[2]);
                            customerBasicInfoEntity.setAccountNo(trimmedArray[2]);
                            customerBasicInfoEntity.setFileNo(trimmedArray[3]);
                            customerBasicInfoEntity.setCardNo(trimmedArray[4]);
                            customerBasicInfoEntity.setCardType(trimmedArray[5]);
                            customerBasicInfoEntity.setCardProfile(trimmedArray[6]);
                            customerBasicInfoEntity.setTitle(trimmedArray[7]);
                            customerBasicInfoEntity.setCustomerName(trimmedArray[8]);
                            customerBasicInfoEntity.setNameOnCard(trimmedArray[9]);
                            customerBasicInfoEntity.setSpouseName(trimmedArray[10]);
                            customerBasicInfoEntity.setCustomerMotherName(trimmedArray[11]);
                            customerBasicInfoEntity.setCustomerFatherName(trimmedArray[12]);
                            customerBasicInfoEntity.setDesignation(trimmedArray[13]);
                            //trimmedArray[14] = formatter.format(trimmedArray[14]);
                            customerBasicInfoEntity.setDob(trimmedArray[14].equals("") ? null : formatter.parse(trimmedArray[14]));
                            customerBasicInfoEntity.setSex(trimmedArray[15]);
                            customerBasicInfoEntity.setPassportNo(trimmedArray[16]);
                            customerBasicInfoEntity.setNid(trimmedArray[17]);
                            customerBasicInfoEntity.setMarketBy(trimmedArray[18]);
                            customerBasicInfoEntity.setDeliveryInstr(trimmedArray[19]);
                            customerBasicInfoEntity.setResAddress(trimmedArray[20]);
                            customerBasicInfoEntity.setResCity(trimmedArray[21]);
                            customerBasicInfoEntity.setResRegion(trimmedArray[22]);
                            customerBasicInfoEntity.setCAddress(trimmedArray[23]);
                            customerBasicInfoEntity.setCCity(trimmedArray[24]);
                            customerBasicInfoEntity.setCRegion(trimmedArray[25]);
                            customerBasicInfoEntity.setRegAddress(trimmedArray[26]);
                            customerBasicInfoEntity.setRegCity(trimmedArray[27]);
                            customerBasicInfoEntity.setRegRegion(trimmedArray[28]);
                            customerBasicInfoEntity.setCompanyName(trimmedArray[29]);
                            customerBasicInfoEntity.setCompanyAddress(trimmedArray[30]);
                            customerBasicInfoEntity.setBillAddress(trimmedArray[31]);
                            customerBasicInfoEntity.setHomeAddress(trimmedArray[32]);
                            customerBasicInfoEntity.setTelephone(trimmedArray[33]);
                            customerBasicInfoEntity.setFax(trimmedArray[34]);
                            customerBasicInfoEntity.setMobileNumber(trimmedArray[35]);
                            customerBasicInfoEntity.setEmail(trimmedArray[36]);
                            customerBasicInfoEntity.setTin(trimmedArray[37]);
                            customerBasicInfoEntity.setPppass(trimmedArray[38]);
                            customerBasicInfoEntity.setCardProductName(trimmedArray[39]);
                            customerBasicInfoEntity.setCreateDate(trimmedArray[40].equals("") ? null : formatter.parse(trimmedArray[40]));
                            customerBasicInfoEntity.setGivenDate(trimmedArray[41].equals("") ? null : formatter.parse(trimmedArray[41]));
                            customerBasicInfoEntity.setCloseDate(trimmedArray[42].equals("") ? null : formatter.parse(trimmedArray[42]));
                            customerBasicInfoEntity.setAnniveDate(trimmedArray[43].equals("") ? null : formatter.parse(trimmedArray[43]));
                            customerBasicInfoEntity.setExpDate(trimmedArray[44].equals("") ? null : formatter.parse(trimmedArray[44]));
                            customerBasicInfoEntity.setCardState(trimmedArray[45]);
                            customerBasicInfoEntity.setCardStatus(trimmedArray[46]);

                            //customerBasicInfoEntity.setCreateDate(new Date());

                            CustomerBasicInfoEntity savedCustomerBasicInfoEntity = customerBasicInfoEntityRepository.save(customerBasicInfoEntity);
                            cardAccountBasicInfo = cardAccountBasicRepository.findFirstByContractIdAndCardType(trimmedArray[2],trimmedArray[5]);
                            if (cardAccountBasicInfo == null) {
                                cardAccountBasicInfo = new CardAccountBasicInfo();
                            }
                            cardAccountBasicInfo.setClientId(trimmedArray[1]);
                            cardAccountBasicInfo.setContractId(trimmedArray[2]);
                            cardAccountBasicInfo.setCardNo(trimmedArray[4]);
                            cardAccountBasicInfo.setCardName(trimmedArray[9]);
                            cardAccountBasicInfo.setCustomer(savedCustomerBasicInfoEntity);
                            cardAccountBasicInfo.setCreatedDate(new Date());

                            CardAccountBasicInfo savedCardAccountBasicInfo = cardAccountBasicRepository.save(cardAccountBasicInfo);

                            //card account info data set
                            cardAccountInfo = cardAccountRepository.findFirstByContractNoAndCardType(trimmedArray[2],trimmedArray[5]);
                            if (cardAccountInfo == null) {
                                cardAccountInfo = new CardAccountInfo();
                            }
                            cardAccountInfo.setClientId(trimmedArray[1]);
                            cardAccountInfo.setContractNo(trimmedArray[2]);
                            cardAccountInfo.setContState(trimmedArray[47]);
                            cardAccountInfo.setDomAccountNo(trimmedArray[48]);
                            cardAccountInfo.setBdtLimit(isDouble(trimmedArray[49].replace(",", "")) == true ? Double.parseDouble(trimmedArray[49].replace(",", "")) : 0.0);
                            cardAccountInfo.setBdtOutstanding(isDouble(trimmedArray[50].replace(",", "")) == true ? Double.parseDouble(trimmedArray[50].replace(",", "")) : 0.0);
                            cardAccountInfo.setBdtMinDue(isDouble(trimmedArray[51].replace(",", "")) == true ? Double.parseDouble(trimmedArray[51].replace(",", "")) : 0.0);
                            cardAccountInfo.setAutoPayBdt(trimmedArray[52]);
                            cardAccountInfo.setAutoPayAcBdt(trimmedArray[53]);
                            cardAccountInfo.setIntAccountNo(trimmedArray[54]);
                            cardAccountInfo.setInternationalLimit(isDouble(trimmedArray[55].replace(",", "")) == true ? Double.parseDouble(trimmedArray[55].replace(",", "")) : 0.0);
                            cardAccountInfo.setInternationalOutstanding(isDouble(trimmedArray[56].replace(",", "")) == true ? Double.parseDouble(trimmedArray[56].replace(",", "")) : 0.0);
                            cardAccountInfo.setInternationalMinDue(isDouble(trimmedArray[57].replace(",", "")) == true ? Double.parseDouble(trimmedArray[57].replace(",", "")) : 0.0);
                            cardAccountInfo.setAutoPayUsd(trimmedArray[58]);
                            cardAccountInfo.setAutoPayAcUsd(trimmedArray[59]);
                            cardAccountInfo.setRiskGroup(trimmedArray[60]);
                            cardAccountInfo.setState(trimmedArray[61]);
                            cardAccountInfo.setNoOfDays(trimmedArray[62]);
                            cardAccountInfo.setAge(trimmedArray[63]);
                            cardAccountInfo.setStatementDate(trimmedArray[64].equals("") ? null : formatter.parse(trimmedArray[64]));
                            //cardAccountInfo.setDueDate(trimmedArray[65].equals("") ? null : formatter.parse(trimmedArray[65]));
                            cardAccountInfo.setCardAccountBasicInfo(savedCardAccountBasicInfo);

                            cardAccountInfo.setCreatedDate(new Date());

                            cardAccountRepository.save(cardAccountInfo);
                        }
                    }
                }

            }
        } catch (Exception e) {
            System.out.println("There was a problem: " + e);
            e.printStackTrace();
        }

    }

    /*-----------------------------------------------------------------------*/
            /*Scheduler for txt file data upload ends from here*/
    /*-----------------------------------------------------------------------*/





    /*-----------------------------------------------------------------------*/
    /*Scheduler for collection report 1 txt data upload starts from here ${card.collectionReportOne.timer}*/
    /*-----------------------------------------------------------------------*/
    @Async
    @Scheduled(cron = "${card.collectionReportOne.timer}")
    public void getCollectionReportOneTextFileData() {
        try {

            File file = new File("C:/Users/User/Downloads/card_data/card/card data format/CollectionReport_1.txt");
            FileInputStream fstream = new FileInputStream(file);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String read;
            int i = 0;
            List<CollectionReportOne> collectionReportOnes = new ArrayList<CollectionReportOne>();
            while ((read = br.readLine()) != null) {
                String[] splicedLine = read.split("[|]");

                String[] trimmedArray = Arrays.stream(splicedLine).map(String::trim).toArray(String[]::new);

                if (splicedLine.length == 17) {
                    if (!trimmedArray[0].equals("") && !trimmedArray[1].equals("")) {
                        i++;
                        if (i > 1) {
                            CollectionReportOne collectionReportOne = new CollectionReportOne();

                            collectionReportOne.setContractNo(trimmedArray[0]);
                            collectionReportOne.setClientId(trimmedArray[1]);
                            collectionReportOne.setLastCard(trimmedArray[2]);
                            collectionReportOne.setCardProduct(trimmedArray[3]);
                            collectionReportOne.setContractType(trimmedArray[4]);
                            collectionReportOne.setContractStatus(trimmedArray[5]);
                            collectionReportOne.setStatementDate(trimmedArray[6]);
                            collectionReportOne.setPayDueDate(trimmedArray[7]);
                            collectionReportOne.setBdtLimit(trimmedArray[8]);
                            collectionReportOne.setCurrBdtOs(trimmedArray[9]);
                            collectionReportOne.setStOsBdt(trimmedArray[10]);
                            collectionReportOne.setStMinPayBdt(trimmedArray[11]);
                            collectionReportOne.setUsdLimit(trimmedArray[12]);
                            collectionReportOne.setCurrUsdOs(trimmedArray[13]);
                            collectionReportOne.setStOsUsd(trimmedArray[14]);
                            collectionReportOne.setStMinPayUsd(trimmedArray[15]);
                            collectionReportOne.setAgeCode(trimmedArray[16]);

                            collectionReportOne.setCreatedDate(new Date());

                            collectionReportOnes.add(collectionReportOne);
                            //collectionReportOneRepository.save(collectionReportOne);
                        }

                        if (i % 30 == 0) {
                            collectionReportOneRepository.saveAll(collectionReportOnes);
                            collectionReportOnes.clear();
                        }
                        if (collectionReportOnes.size() > 0) {
                            collectionReportOneRepository.saveAll(collectionReportOnes);
                            collectionReportOnes.clear();
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("There was a problem: " + e);
            e.printStackTrace();
        }

    }

    /*-----------------------------------------------------------------------*/
    /*Scheduler for collection report 1 txt data upload ends here*/
    /*-----------------------------------------------------------------------*/



    /*-----------------------------------------------------------------------*/
    /*Scheduler for collection report 2 txt data upload starts from here ${card.collectionReportTwo.timer}*/
    /*-----------------------------------------------------------------------*/
    @Async
    @Scheduled(cron = "${card.collectionReportTwo.timer}")
    public void getCollectionReportTwoTextFileData() {
        try {

            File file = new File("C:/Users/User/Downloads/card_data/card/card data format/CollectionReport_2.txt");
            FileInputStream fstream = new FileInputStream(file);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String read;
            int i = 0;
            List<CollectionReportTwo> collectionReportTwos = new ArrayList<>();
            while ((read = br.readLine()) != null) {
                String[] splicedLine = read.split("[|]");

                String[] trimmedArray = Arrays.stream(splicedLine).map(String::trim).toArray(String[]::new);

                if (splicedLine.length == 20) {
                    if (!trimmedArray[2].equals("") && !trimmedArray[0].equals("")) {
                        i++;
                        if (i > 1) {
                            CollectionReportTwo collectionReportTwo = new CollectionReportTwo();

                            collectionReportTwo.setClientId(trimmedArray[0]);
                            collectionReportTwo.setCmOnBoardDate(trimmedArray[1]);
                            collectionReportTwo.setContractNo(trimmedArray[2]);
                            collectionReportTwo.setContractStatus(trimmedArray[3]);
                            collectionReportTwo.setContractType(trimmedArray[4]);
                            collectionReportTwo.setFdrValue(trimmedArray[5]);
                            collectionReportTwo.setBranchValue(trimmedArray[6]);
                            collectionReportTwo.setInstaBuy(trimmedArray[7]);
                            collectionReportTwo.setInstaBuyAmt(trimmedArray[8]);
                            collectionReportTwo.setInstaBuyPaid(trimmedArray[9]);
                            collectionReportTwo.setInstaBuyUnpaid(trimmedArray[10]);
                            collectionReportTwo.setMrContract(trimmedArray[11]);
                            collectionReportTwo.setMrAccount(trimmedArray[12]);
                            collectionReportTwo.setRewardPoint(trimmedArray[13]);
                            collectionReportTwo.setCreditShield(trimmedArray[14]);
                            collectionReportTwo.setTotalBdtPay(trimmedArray[15]);
                            collectionReportTwo.setTotalUsdPay(trimmedArray[16]);
                            collectionReportTwo.setFirstTxnDate(trimmedArray[17]);
                            collectionReportTwo.setFirstTxnPoste(trimmedArray[18]);
                            collectionReportTwo.setFirstTxnAmt(trimmedArray[19]);

                            collectionReportTwo.setCreatedDate(new Date());

                            collectionReportTwos.add(collectionReportTwo);
                            //collectionReportOneRepository.save(collectionReportOne);
                        }
                        if (i % 30 == 0) {
                            collectionReportTwoRepository.saveAll(collectionReportTwos);
                            collectionReportTwos.clear();
                        }
                        if (collectionReportTwos.size() > 0) {
                            collectionReportTwoRepository.saveAll(collectionReportTwos);
                            collectionReportTwos.clear();
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("There was a problem: " + e);
            e.printStackTrace();
        }

    }

    /*-----------------------------------------------------------------------*/
    /*Scheduler for collection report 2 txt data upload ends here*/
    /*-----------------------------------------------------------------------*/


    /*-----------------------------------------------------------------------*/
    /*Scheduler for collection report 3 txt data upload starts from here ${card.collectionReportThree.timer}*/
    /*-----------------------------------------------------------------------*/
    @Async
    @Scheduled(cron = "${card.collectionReportThree.timer}")
    public void getCollectionReportThreeTextFileData() {
        try {

            File file = new File("C:/Users/User/Downloads/card_data/card/card data format/CollectionReport_3.txt");
            FileInputStream fstream = new FileInputStream(file);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String read;
            int i = 0;
            List<CollectionReportThree> collectionReportThrees = new ArrayList<>();
            while ((read = br.readLine()) != null) {
                String[] splicedLine = read.split("[|]");

                String[] trimmedArray = Arrays.stream(splicedLine).map(String::trim).toArray(String[]::new);

                if (splicedLine.length == 9) {
                    if (!trimmedArray[0].equals("")) {
                        i++;
                        if (i > 1) {
                            CollectionReportThree collectionReportThree = new CollectionReportThree();

                            collectionReportThree.setContractNo(trimmedArray[0]);
                            collectionReportThree.setLoanNo(trimmedArray[1]);
                            collectionReportThree.setStartDate(trimmedArray[2]);
                            collectionReportThree.setLastPayDate(trimmedArray[3]);
                            collectionReportThree.setTotalNoOfIns(trimmedArray[4]);
                            collectionReportThree.setPaidNoOfInst(trimmedArray[5]);
                            collectionReportThree.setLoanAmt(trimmedArray[6]);
                            collectionReportThree.setPaidAmt(trimmedArray[7]);
                            collectionReportThree.setUnpaidAmt(trimmedArray[8]);

                            collectionReportThree.setCreatedDate(new Date());

                            collectionReportThrees.add(collectionReportThree);
                        }
                        if (i % 30 == 0) {
                            collectionReportThreeRepository.saveAll(collectionReportThrees);
                            collectionReportThrees.clear();
                        }
                        if (collectionReportThrees.size() > 0) {
                            collectionReportThreeRepository.saveAll(collectionReportThrees);
                            collectionReportThrees.clear();
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("There was a problem: " + e);
            e.printStackTrace();
        }

    }

    /*-----------------------------------------------------------------------*/
    /*Scheduler for collection report 3 txt data upload ends here*/
    /*-----------------------------------------------------------------------*/

    /*-----------------------------------------------------------------------*/
    /*Scheduler for collection report 4 txt data upload start here*/
    /*-----------------------------------------------------------------------*/
    @Async
    @Scheduled(cron = "0 6 15 * * ?")
    public void getCollectionReportFourTextFileData() {
        try {
            File file = new File("C:/Users/User/Downloads/card_data/card/card data format/Collection_Report_4 - Sample.txt");
            FileInputStream fstream = new FileInputStream(file);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String read;
            int i = 0;
            List<CollectionReportFour> collectionReportFours = new ArrayList<>();

            while ((read = br.readLine()) != null) {
                String[] splicedLine = read.split("[|]");

                String[] trimmedArray = Arrays.stream(splicedLine).map(String::trim).toArray(String[]::new);

                    if (splicedLine.length == 10) {
                        if (!trimmedArray[0].equals("")) {
                            i++;
                            if (i > 1) {
                                CollectionReportFour collectionReportFour = new CollectionReportFour();

                                collectionReportFour.setContractId(trimmedArray[0]);
                                collectionReportFour.setContractType(trimmedArray[1]);
                                collectionReportFour.setContractStatus(trimmedArray[2]);
                                collectionReportFour.setFdrInformation(trimmedArray[3]);
                                collectionReportFour.setReferenceNameFirst(trimmedArray[4]);
                                collectionReportFour.setRelationShip(trimmedArray[5]);
                                collectionReportFour.setReferenceMobileFirst(trimmedArray[6]);
                                collectionReportFour.setReferenceMobileSecond(trimmedArray[7]);
                                collectionReportFour.setReferenceMobileSecond(trimmedArray[8]);
                                collectionReportFour.setAgingDate(trimmedArray[9]);

                                collectionReportFour.setCreatedDate(new Date());

                                collectionReportFours.add(collectionReportFour);
                            }
                            if (i % 30 == 0) {
                                collectionReportFourRepository.saveAll(collectionReportFours);
                                collectionReportFours.clear();
                            }
                            if (collectionReportFours.size() > 0) {
                                collectionReportFourRepository.saveAll(collectionReportFours);
                                collectionReportFours.clear();
                            }
                        }
                    }

            }
            } catch (Exception e) {
            System.out.println("There was a problem: " + e);
            e.printStackTrace();
        }

    }
    /*-----------------------------------------------------------------------*/
    /*Scheduler for collection report 4 txt data upload ends here*/
    /*-----------------------------------------------------------------------*/


    /*-----------------------------------------------------------------------*/
    /*Scheduler for aging History txt data upload starts from here ${card.aginghistory.timer}*/
    /*-----------------------------------------------------------------------*/
    @Async
    @Scheduled(cron = "${card.aginghistory.timer}")
    public void getAgingHistoryTextFileData() {
        try {
            File file = new File("E:/Move/Shahin/csinfotech data/card data format/SampleData_05042022/AgingHistory.txt");
            FileInputStream fstream = new FileInputStream(file);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String read;
            int i = 0;
            List<AgingHistory> agingHistories = new ArrayList<>();
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
            while ((read = br.readLine()) != null) {
                String[] splicedLine = read.split("[|]");

                String[] trimmedArray = Arrays.stream(splicedLine).map(String::trim).toArray(String[]::new);

                if (splicedLine.length == 7) {
                    if (!trimmedArray[0].equals("")) {
                        i++;
                        if (i > 1) {
                            AgingHistory agingHistory = new AgingHistory();

                            agingHistory.setContractNo(trimmedArray[0]);
                            agingHistory.setOpDate(trimmedArray[1].equals("") ? null : formatter.parse(trimmedArray[1]).toString());
                            agingHistory.setAging(trimmedArray[2]);
                            agingHistory.setOperation(trimmedArray[3]);
                            agingHistory.setComments(trimmedArray[4]);
                            agingHistory.setRecNo(trimmedArray[5]);
                            agingHistory.setStatus(trimmedArray[6]);

                            agingHistory.setCreatedDate(new Date());

                            agingHistories.add(agingHistory);
                        }
                        if (i % 30 == 0) {
                            agingHistoryRepository.saveAll(agingHistories);
                            agingHistories.clear();
                        }
                        if (agingHistories.size() > 0) {
                            agingHistoryRepository.saveAll(agingHistories);
                            agingHistories.clear();
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("There was a problem: " + e);
            e.printStackTrace();
        }

    }

    /*-----------------------------------------------------------------------*/
    /*Scheduler for aging History txt data upload ends here*/
    /*-----------------------------------------------------------------------*/

    /*-----------------------------------------------------------------------*/
    /*Scheduler for aging History txt data upload starts from here ${card.interestSuspenseReport.timer}*/
    /*-----------------------------------------------------------------------*/
    @Async
    @Scheduled(cron = "${card.interestSuspenseReport.timer}")
    public void getInterestSuspenseReportTextFileData() {
        try {
            File file = new File("E:/Move/Shahin/csinfotech data/card data format/SampleData_05042022/InterestSuspenseReport.txt");
            FileInputStream fstream = new FileInputStream(file);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String read;
            int i = 0;
            List<InterestSuspenseReport> interestSuspenseReports = new ArrayList<>();
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
            while ((read = br.readLine()) != null) {
                String[] splicedLine = read.split("[|]");

                String[] trimmedArray = Arrays.stream(splicedLine).map(String::trim).toArray(String[]::new);

                if (splicedLine.length == 19) {
                    if (!trimmedArray[2].equals("")) {
                        i++;
                        if (i > 1) {
                            InterestSuspenseReport interestSuspenseReport = new InterestSuspenseReport();

                            interestSuspenseReport.setCustomerId(trimmedArray[1]);
                            interestSuspenseReport.setContractNo(trimmedArray[2]);
                            interestSuspenseReport.setContractAccount(trimmedArray[3]);
                            interestSuspenseReport.setAccCurrency(trimmedArray[4]);
                            interestSuspenseReport.setOutstanding(trimmedArray[5]);
                            interestSuspenseReport.setFeeAndCharges(trimmedArray[6]);
                            interestSuspenseReport.setInterest(trimmedArray[7]);
                            interestSuspenseReport.setAtmTxn(trimmedArray[8]);
                            interestSuspenseReport.setPosTxn(trimmedArray[9]);
                            interestSuspenseReport.setFundTransfer(trimmedArray[10]);
                            interestSuspenseReport.setBalanceTransferUnpaid(trimmedArray[11]);
                            interestSuspenseReport.setCardCheque(trimmedArray[12]);
                            interestSuspenseReport.setFowardBalance(trimmedArray[13]);
                            interestSuspenseReport.setUnadjustedDebitTxn(trimmedArray[14]);
                            interestSuspenseReport.setOtherTxn(trimmedArray[15]);
                            interestSuspenseReport.setReversal(trimmedArray[16]);
                            interestSuspenseReport.setPayment(trimmedArray[17]);
                            interestSuspenseReport.setUnadjustedCreditTxn(trimmedArray[18]);

                            interestSuspenseReport.setCreatedDate(new Date());

                            interestSuspenseReports.add(interestSuspenseReport);
                        }
                        if (i % 30 == 0) {
                            interestSuspenseReportRepository.saveAll(interestSuspenseReports);
                            interestSuspenseReports.clear();
                        }
                        if (interestSuspenseReports.size() > 0) {
                            interestSuspenseReportRepository.saveAll(interestSuspenseReports);
                            interestSuspenseReports.clear();
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("There was a problem: " + e);
            e.printStackTrace();
        }

    }

    /*-----------------------------------------------------------------------*/
    /*Scheduler for interest suspense report txt data upload ends here*/
    /*-----------------------------------------------------------------------*/


    /*-----------------------------------------------------------------------*/
    /*Scheduler for Approve Transactions Unbilled Transactions Payment txt data upload starts from here ${card.approveUnbilledPayment.timer}*/
    /*-----------------------------------------------------------------------*/
    @Async
    @Scheduled(cron = "${card.approveUnbilledPayment.timer}")
    public void getApproveTransactionsUnbilledTransactionsPaymentTextFileData() {
        try {
            File file = new File("C:/Users/User/Downloads/card_data/card/card data format/SampleData_05042022/ApproveTransactionsUnbilledTransactionsPayment.txt");
            FileInputStream fstream = new FileInputStream(file);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String read;
            int i = 0;
            List<ApproveTransactionsUnbilledTransactionsPayment> atutps = new ArrayList<>();
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
            while ((read = br.readLine()) != null) {
                String[] splicedLine = read.split("[|]");

                String[] trimmedArray = Arrays.stream(splicedLine).map(String::trim).toArray(String[]::new);

                if (splicedLine.length == 19) {
                    if (!trimmedArray[2].equals("")) {
                        i++;
                        if (i > 1) {
                            ApproveTransactionsUnbilledTransactionsPayment atutp = new ApproveTransactionsUnbilledTransactionsPayment();

                            atutp.setPostDate(trimmedArray[0]);
                            atutp.setTxnDate(trimmedArray[1]);
                            atutp.setContractNo(trimmedArray[2]);
                            atutp.setAcNo(trimmedArray[3]);
                            atutp.setCardNo(trimmedArray[4]);
                            atutp.setBillingCurrenc(trimmedArray[5]);
                            atutp.setBillingAmount(trimmedArray[6]);
                            atutp.setAcqCountry(trimmedArray[7]);
                            atutp.setTxnAmount(trimmedArray[8]);
                            atutp.setTxnCurrency(trimmedArray[9]);
                            atutp.setTxnDescription(trimmedArray[10]);
                            atutp.setTxnType(trimmedArray[11]);
                            atutp.setApprovalCode(trimmedArray[12]);
                            atutp.setTermId(trimmedArray[13]);
                            atutp.setRetailerId(trimmedArray[14]);
                            atutp.setMcc(trimmedArray[15]);
                            atutp.setTermLocation(trimmedArray[16]);
                            atutp.setPosEntry(trimmedArray[17]);
                            atutp.setSIndicator(trimmedArray[18]);

                            atutp.setCreatedDate(new Date());

                            atutps.add(atutp);
                        }
                        if (i % 30 == 0) {
                            approveTransactionsUnbilledTransactionsPaymentRepository.saveAll(atutps);
                            atutps.clear();
                        }
                        if (atutps.size() > 0) {
                            approveTransactionsUnbilledTransactionsPaymentRepository.saveAll(atutps);
                            atutps.clear();
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("There was a problem: " + e);
            e.printStackTrace();
        }

    }

    /*-----------------------------------------------------------------------*/
    /*Scheduler for Approve Transactions Unbilled Transactions Payment txt data upload ends here*/
    /*-----------------------------------------------------------------------*/



    /*-----------------------------------------------------------------------*/
    /*Scheduler for txt file data upload starts from here ${card.scheduler.timer}*/
    /*-----------------------------------------------------------------------*/
    @Scheduled(cron = "${card.scheduler.timer}")
    public void  get83TextFileData() {
        try {
//            File file = new File("D:/Tajkia Apu/Card text file latest/83_Report_Partial_05062022.txt");
            File file = new File("C:/Users/User/Downloads/card/card/card data format/83_Report_Partial_05062022.txt");
            System.out.println(file.getName());
            FileInputStream fstream = new FileInputStream(file);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String read;
            int i = 0;
            List<CardAccountBasicInfo> cardAccountBasicInfos = new ArrayList<>();
            List<CardAccountInfo> cardAccountInfos = new ArrayList<>();
            List<CustomerBasicInfoEntity> customerBasicInfoEntities = new ArrayList<>();
            Map<String,CustomerBasicInfoEntity> cbieMap = new HashMap<>();
            Map<String,CardAccountBasicInfo> cabiMap = new HashMap<>();
            Map<String,CardAccountInfo> caiMap = new HashMap<>();
            UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            while ((read = br.readLine()) != null) {
                String[] splicedLine = read.split("[|]");

                String[] trimmedArray = Arrays.stream(splicedLine).map(String::trim).toArray(String[]::new);

                if (splicedLine.length == 65) {
                    i++;
                    if (i > 1) {
                        if(!trimmedArray[1].equals("") && !trimmedArray[2].equals("")) {
                            CardAccountInfo cardAccountInfo;
                            CardAccountBasicInfo cardAccountBasicInfo;
                            CustomerBasicInfoEntity customerBasicInfoEntity;
                            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

                            //get customer basic info entity by customer id
                            customerBasicInfoEntity = customerBasicInfoEntityRepository.findFirstByContractIdAndCardType(trimmedArray[2],trimmedArray[5]);
                            if (customerBasicInfoEntity == null) {
                                customerBasicInfoEntity = new CustomerBasicInfoEntity();
                                customerBasicInfoEntity.setCreateDate(new Date());
                                customerBasicInfoEntity.setCreatedBy(user.getUsername());
                            }
                            else{
                                customerBasicInfoEntity.setModifiedBy(user.getUsername());
                                customerBasicInfoEntity.setModifiedDate(new Date());
                            }

                            //customer basic info data set
                            customerBasicInfoEntity.setClientId(trimmedArray[1]);
                            customerBasicInfoEntity.setContractId(trimmedArray[2]);
                            //customerBasicInfoEntity.setAccountNo(trimmedArray[2]);
                            customerBasicInfoEntity.setFileNo(trimmedArray[3]);
                            customerBasicInfoEntity.setCardNo(trimmedArray[4]);
                            customerBasicInfoEntity.setCardType(trimmedArray[5]);
                            customerBasicInfoEntity.setCardProfile(trimmedArray[6]);
                            customerBasicInfoEntity.setTitle(trimmedArray[7]);
                            customerBasicInfoEntity.setCustomerName(trimmedArray[8]);
                            customerBasicInfoEntity.setNameOnCard(trimmedArray[9]);
                            customerBasicInfoEntity.setSpouseName(trimmedArray[10]);
                            customerBasicInfoEntity.setCustomerMotherName(trimmedArray[11]);
                            customerBasicInfoEntity.setCustomerFatherName(trimmedArray[12]);
                            customerBasicInfoEntity.setDesignation(trimmedArray[13]);
                            //trimmedArray[14] = formatter.format(trimmedArray[14]);
                            customerBasicInfoEntity.setDob(trimmedArray[14].equals("") ? null : formatter.parse(trimmedArray[14]));
                            customerBasicInfoEntity.setSex(trimmedArray[15]);
                            customerBasicInfoEntity.setPassportNo(trimmedArray[16]);
                            customerBasicInfoEntity.setNid(trimmedArray[17]);
                            customerBasicInfoEntity.setMarketBy(trimmedArray[18]);
                            customerBasicInfoEntity.setDeliveryInstr(trimmedArray[19]);
                            customerBasicInfoEntity.setResAddress(trimmedArray[20]);
                            customerBasicInfoEntity.setResCity(trimmedArray[21]);
                            customerBasicInfoEntity.setResRegion(trimmedArray[22]);
                            customerBasicInfoEntity.setCAddress(trimmedArray[23]);
                            customerBasicInfoEntity.setCCity(trimmedArray[24]);
                            customerBasicInfoEntity.setCRegion(trimmedArray[25]);
                            customerBasicInfoEntity.setRegAddress(trimmedArray[26]);
                            customerBasicInfoEntity.setRegCity(trimmedArray[27]);
                            customerBasicInfoEntity.setRegRegion(trimmedArray[28]);
                            customerBasicInfoEntity.setCompanyName(trimmedArray[29]);
                            customerBasicInfoEntity.setCompanyAddress(trimmedArray[30]);
                            customerBasicInfoEntity.setBillAddress(trimmedArray[31]);
                            customerBasicInfoEntity.setHomeAddress(trimmedArray[32]);
                            customerBasicInfoEntity.setTelephone(trimmedArray[33]);
                            customerBasicInfoEntity.setFax(trimmedArray[34]);
                            customerBasicInfoEntity.setMobileNumber(trimmedArray[35]);
                            customerBasicInfoEntity.setEmail(trimmedArray[36]);
                            customerBasicInfoEntity.setTin(trimmedArray[37]);
                            customerBasicInfoEntity.setPppass(trimmedArray[38]);
                            customerBasicInfoEntity.setCardProductName(trimmedArray[39]);
                            customerBasicInfoEntity.setCreateDate((trimmedArray[40].equals("") || trimmedArray[40].length()<10) ? null : formatter.parse(trimmedArray[40]));
                            customerBasicInfoEntity.setGivenDate((trimmedArray[41].equals("") || trimmedArray[41].length()<10) ? null : formatter.parse(trimmedArray[41]));
                            customerBasicInfoEntity.setCloseDate((trimmedArray[42].equals("") || trimmedArray[42].length()<10) ? null : formatter.parse(trimmedArray[42]));
                            customerBasicInfoEntity.setAnniveDate((trimmedArray[43].equals("") || trimmedArray[43].length()<10) ? null : formatter.parse(trimmedArray[43]));
                            customerBasicInfoEntity.setExpDate((trimmedArray[44].equals("") || trimmedArray[44].length()<10) ? null : formatter.parse(trimmedArray[44]));
                            customerBasicInfoEntity.setCardState(trimmedArray[45]);
                            customerBasicInfoEntity.setCardStatus(trimmedArray[46]);


                            customerBasicInfoEntities.add(customerBasicInfoEntity);
                            cbieMap.put(trimmedArray[2]+""+trimmedArray[5],customerBasicInfoEntity);

                            //CustomerBasicInfoEntity savedCustomerBasicInfoEntity = customerBasicInfoEntityRepository.save(customerBasicInfoEntity);
                            cardAccountBasicInfo = cardAccountBasicRepository.findFirstByContractIdAndCardType(trimmedArray[2],trimmedArray[5]);
                            if (cardAccountBasicInfo == null) {
                                cardAccountBasicInfo = new CardAccountBasicInfo();
                                cardAccountBasicInfo.setCreatedDate(new Date());
                                cardAccountBasicInfo.setCreatedBy(user.getUsername());
                            }
                            else{
                                cardAccountBasicInfo.setModifiedBy(user.getUsername());
                                cardAccountBasicInfo.setModifiedDate(new Date());
                            }
                            cardAccountBasicInfo.setClientId(trimmedArray[1]);
                            cardAccountBasicInfo.setContractId(trimmedArray[2]);
                            cardAccountBasicInfo.setCardNo(trimmedArray[4]);
                            cardAccountBasicInfo.setCardName(trimmedArray[9]);
                            cardAccountBasicInfo.setCardType(trimmedArray[5]);
                            cardAccountBasicInfo.setCustomer(cbieMap.get(trimmedArray[2]+""+trimmedArray[5]));

                            cardAccountBasicInfos.add(cardAccountBasicInfo);
                            cabiMap.put(trimmedArray[2]+""+trimmedArray[5],cardAccountBasicInfo);
                            //CardAccountBasicInfo savedCardAccountBasicInfo = cardAccountBasicRepository.save(cardAccountBasicInfo);

                            //card account info data set
                            cardAccountInfo = cardAccountRepository.findFirstByContractNoAndCardType(trimmedArray[2], trimmedArray[5]);
                            if (cardAccountInfo == null) {
                                cardAccountInfo = new CardAccountInfo();
                                cardAccountInfo.setCreatedDate(new Date());
                                cardAccountInfo.setCreatedBy(user.getUsername());
                            }
                            else{
                                cardAccountInfo.setModifiedBy(user.getUsername());
                                cardAccountInfo.setModifiedDate(new Date());
                            }
                            cardAccountInfo.setClientId(trimmedArray[1]);
                            cardAccountInfo.setContractNo(trimmedArray[2]);
                            cardAccountInfo.setContState(trimmedArray[47]);
                            cardAccountInfo.setCardType(trimmedArray[5]);
                            cardAccountInfo.setDomAccountNo(trimmedArray[48]);
                            cardAccountInfo.setBdtLimit(isDouble(trimmedArray[49].replace(",", "")) ? Double.parseDouble(trimmedArray[49].replace(",", "")) : 0.0);
                            cardAccountInfo.setBdtOutstanding(isDouble(trimmedArray[50].replace(",", "")) ? Double.parseDouble(trimmedArray[50].replace(",", "")) : 0.0);
                            cardAccountInfo.setBdtMinDue(isDouble(trimmedArray[51].replace(",", "")) ? Double.parseDouble(trimmedArray[51].replace(",", "")) : 0.0);
                            cardAccountInfo.setAutoPayBdt(trimmedArray[52]);
                            cardAccountInfo.setAutoPayAcBdt(trimmedArray[53]);
                            cardAccountInfo.setIntAccountNo(trimmedArray[54]);
                            cardAccountInfo.setInternationalLimit(isDouble(trimmedArray[55].replace(",", "")) ? Double.parseDouble(trimmedArray[55].replace(",", "")) : 0.0);
                            cardAccountInfo.setInternationalOutstanding(isDouble(trimmedArray[56].replace(",", "")) ? Double.parseDouble(trimmedArray[56].replace(",", "")) : 0.0);
                            cardAccountInfo.setInternationalMinDue(isDouble(trimmedArray[57].replace(",", "")) ? Double.parseDouble(trimmedArray[57].replace(",", "")) : 0.0);
                            cardAccountInfo.setAutoPayUsd(trimmedArray[58]);
                            cardAccountInfo.setAutoPayAcUsd(trimmedArray[59]);
                            cardAccountInfo.setRiskGroup(trimmedArray[60]);
                            cardAccountInfo.setState(trimmedArray[61]);
                            cardAccountInfo.setNoOfDays(trimmedArray[62]);
                            cardAccountInfo.setAge(trimmedArray[63]);
                            cardAccountInfo.setStatementDate((trimmedArray[64].equals("") || trimmedArray[64].length()<10) ? null : formatter.parse(trimmedArray[64]));
                            //cardAccountInfo.setDueDate((trimmedArray[65].equals("") || trimmedArray[65].length()<10)? null : formatter.parse(trimmedArray[65]));
//                            cardAccountInfo.setDueDate((trimmedArray[65].equals("") || trimmedArray[65].length()<10)? null : formatter.parse(trimmedArray[65]));
                            cardAccountInfo.setCardAccountBasicInfo(cabiMap.get(trimmedArray[2]+""+trimmedArray[5]));

                            //cardAccountRepository.save(cardAccountInfo);
                            cardAccountInfos.add(cardAccountInfo);
                            caiMap.put(trimmedArray[2]+""+trimmedArray[5],cardAccountInfo);
                        }
                        if (i % 101 == 0) {
                            customerBasicInfoEntityRepository.saveAll(customerBasicInfoEntities);
                            customerBasicInfoEntities.clear();
                            cardAccountBasicRepository.saveAll(cardAccountBasicInfos);
                            cardAccountBasicInfos.clear();
                            cardAccountRepository.saveAll(cardAccountInfos);
                            cardAccountBasicInfos.clear();
                        }
                    }
                }
            }
            if (customerBasicInfoEntities.size() > 0) {
                customerBasicInfoEntityRepository.saveAll(customerBasicInfoEntities);
                customerBasicInfoEntities.clear();
            }
            if(cardAccountBasicInfos.size() > 0) {
                cardAccountBasicRepository.saveAll(cardAccountBasicInfos);
                cardAccountBasicInfos.clear();
            }
            if(cardAccountInfos.size() > 0){
                cardAccountRepository.saveAll(cardAccountInfos);
                cardAccountBasicInfos.clear();
            }
        } catch (Exception e) {
            System.out.println("There was a problem: " + e);
            e.printStackTrace();
        }

    }

    /*-----------------------------------------------------------------------*/
    /*Scheduler for txt file data upload ends from here*/
    /*-----------------------------------------------------------------------*/



//    update payment info dayend

    public void  updatePaymentInfoDayEnd(){

    }

}
