package com.unisoft.retail.loan.dataEntry.CustomerUpdate.accountInformationService;

import com.unisoft.collection.dashboard.AdvanceSearchPayload;
import com.unisoft.collection.distribution.loan.LoanAccountDistributionRepository;
import com.unisoft.collection.distribution.loan.loanAccountDistribution.LoanAccountDistributionInfo;
import com.unisoft.retail.loan.dataEntry.CustomerUpdate.accountInformation.AccountInfoSMSDto;
import com.unisoft.retail.loan.dataEntry.CustomerUpdate.accountInformation.AccountInformationDto;
import com.unisoft.retail.loan.dataEntry.CustomerUpdate.accountInformation.AccountInformationEntity;
import com.unisoft.retail.loan.dataEntry.CustomerUpdate.accountInformationRepository.AccountInformationDao;
import com.unisoft.retail.loan.dataEntry.CustomerUpdate.accountInformationRepository.AccountInformationRepository;
import com.unisoft.user.UserPrincipal;
import com.unisoft.utillity.DateUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import javax.persistence.Tuple;

@Service
public class AccountInformationService {

    @Autowired
    private AccountInformationRepository accountInformationRepository;

    @Autowired
    private AccountInformationDao accountInformationDao;

    @Autowired
    private DateUtils dateUtils;

    @Autowired
    private LoanAccountDistributionRepository loanAccountDistributionRepository;

    @Value("${ific.excel.file-path}")
    private String excelServerPath;

    @Scheduled(cron = "0 30 9 * * *")
    public String getAccountInformationData() {

        List<AccountInformationDto> dataList = accountInformationDao.getData();
        if(dataList.size()<1)
            return "400";
        List<AccountInformationEntity> accountInformationEntities = new ArrayList<>();

        for (AccountInformationDto dto : dataList) {
            if ((dto.getLoanACNo() != null && dto.getLoanACNo().trim().length() == 13)
                    && (dto.getBranchMnemonic() != null && dto.getBranchMnemonic().trim().length() > 0)
                    && (dto.getProductCode() != null && dto.getProductCode().trim().length() > 0)
                    && (dto.getDealReference() != null && dto.getDealReference().trim().length() > 0)) {

                String account = dto.getLoanACNo().trim();
                String branchMnemonic = dto.getBranchMnemonic().trim();
                String productCode = dto.getProductCode().trim();
                String dealReference = dto.getDealReference().trim();

                AccountInformationEntity accountInformationEntity;

                accountInformationEntity = accountInformationRepository.
                        findByLoanACNoAndBranchMnemonicAndProductCodeAndDealReference(account, branchMnemonic, productCode, dealReference);


                if (accountInformationEntity == null) {
                    accountInformationEntity = new AccountInformationEntity();
                    accountInformationEntity.setCreatedDate(new Date());

                    accountInformationEntity.setLoanACNo(dto.getLoanACNo().trim());

                } else {
                    accountInformationEntity.setModifiedDate(new Date());
                }


                String lastPaymentAmount = dto.getLastPaymentAmount();
                String settlementLinkAccountBalance = dto.getSettlementLinkAccountBalance();
                String linkMotherAccountNo = dto.getLinkMotherAccountNo();
                String routingNo = dto.getRoutingNo();
                String mobile = dto.getMobile();
                String branchName = dto.getBranchName();
                String branchCode = dto.getBranchCode();
                String overDue = dto.getOverdue();
                String emiAmount = dto.getEmiAmount();

                String productType = dto.getProductType();
                String actualTenor = dto.getActualTenor();
                String totalOutstanding = dto.getTotalOutstanding();
                String borrowerName = dto.getBorrowersName();
                String profession = dto.getProfession();
                String email = dto.getEmail();
                String nid = dto.getNid();
                String professionSegment = dto.getProfessionSegment();

                String disbursementAmount = dto.getDisbursementAmount();
                String customerId = dto.getCustomerId();
                String customerName = dto.getCustomerName();
                String customerType = dto.getCustomerType();
                String spouse = dto.getSpouse();

                String gender = dto.getGender();
                String fatherName = dto.getFatherName();
                String motherName = dto.getMotherName();
                String ni = dto.getNi();
                String tin = dto.getTin();

                String contractNo = dto.getContractNo();
                String contractNoHome = dto.getContractNoHome();
                String economicPurposeName = dto.getEconomicPurposeName();
                String economicPurposeCode = dto.getEconomicPurposeCode();
                String productName = dto.getProductName();
                String sectorCode = dto.getSectorCode();
                String sectorName = dto.getSectorName();
                String address1 = dto.getAddress1();
                String address2 = dto.getAddress2();
                String address3 = dto.getAddress3();
                String address4 = dto.getAddress4();
                String address5 = dto.getAddress5();
                String accountTitle = dto.getAccountTitle();
                String smeCodeIndustryScalID = dto.getSmeCodeIndustryScaleID();
                String interestRate = dto.getInterestRate();
                String sanctionAmount = dto.getSanctionAmount();
                String linkAccountStatus = dto.getLinkAccountStatus();
                String linkAcProductCode = dto.getLinkACProductCode();
                String dealAcBasic = dto.getDealAcBasic();
                String dealAcSuffix = dto.getDealAcSuffix();
                String partyId = dto.getPartyId();

                String docType = dto.getDocType();

                accountInformationEntity.setLastPaymentAmount(lastPaymentAmount);
                accountInformationEntity.setSettlementLinkAccountBalance(settlementLinkAccountBalance);
                accountInformationEntity.setLinkMotherAccountNo(linkMotherAccountNo);
                accountInformationEntity.setRoutingNo(routingNo);
                accountInformationEntity.setMobile(mobile);
                accountInformationEntity.setBranchName(branchName);
                accountInformationEntity.setBranchCode(branchCode);
                accountInformationEntity.setOverdue(overDue);
                accountInformationEntity.setEmiAmount(emiAmount);

                accountInformationEntity.setProductType(productType);
                accountInformationEntity.setActualTenor(actualTenor);
                accountInformationEntity.setTotalOutstanding(totalOutstanding);
                accountInformationEntity.setBorrowersName(borrowerName);
                accountInformationEntity.setProfession(profession);
                accountInformationEntity.setEmail(email);
                accountInformationEntity.setNid(nid);
                accountInformationEntity.setProfessionSegment(professionSegment);

                accountInformationEntity.setDisbursementAmount(disbursementAmount);
                accountInformationEntity.setCustomerId(customerId);
                accountInformationEntity.setCustomerName(customerName);
                accountInformationEntity.setCustomerType(customerType);
                accountInformationEntity.setSpouse(spouse);

                accountInformationEntity.setGender(gender);
                accountInformationEntity.setFatherName(fatherName);
                accountInformationEntity.setMotherName(motherName);
                accountInformationEntity.setNi(ni);
                accountInformationEntity.setTin(tin);

                accountInformationEntity.setContractNo(contractNo);
                accountInformationEntity.setContractNoHome(contractNoHome);
                accountInformationEntity.setEconomicPurposeName(economicPurposeName);
                accountInformationEntity.setEconomicPurposeCode(economicPurposeCode);
                accountInformationEntity.setProductName(productName);
                accountInformationEntity.setSectorCode(sectorCode);
                accountInformationEntity.setSectorName(sectorName);
                accountInformationEntity.setAddress1(address1);
                accountInformationEntity.setAddress2(address2);
                accountInformationEntity.setAddress3(address3);
                accountInformationEntity.setAddress4(address4);
                accountInformationEntity.setAddress5(address5);
                accountInformationEntity.setAccountTitle(accountTitle);
                accountInformationEntity.setSmeCodeIndustryScaleID(smeCodeIndustryScalID);
                accountInformationEntity.setInterestRate(interestRate);
                accountInformationEntity.setSanctionAmount(sanctionAmount);

                //new added at 16-01-2023
                accountInformationEntity.setDivision(dto.getDivision());
                accountInformationEntity.setDistrict(dto.getDistrict());
                accountInformationEntity.setScheduleStartDate(dto.getScheduleStartDate());
                accountInformationEntity.setDealBalanceAtStartDate(dto.getDealBalanceAtStartDate());
                accountInformationEntity.setCalculatedMaturityDate(dto.getCalculatedMaturityDate());
                accountInformationEntity.setFirstRepaymentAmount(dto.getFirstRepaymentAmount());
                accountInformationEntity.setLastRepaymentAmount(dto.getLastRepaymentAmount());
                accountInformationEntity.setTotalNoOfInstallment(dto.getTotalNoOfInstallment());
                accountInformationEntity.setFrequencyCode(dto.getFrequencyCode());
                accountInformationEntity.setLoanCLStatus(dto.getLoanCLStatus());
                accountInformationEntity.setLatestDisbursementAmount(dto.getLatestDisbursementAmount());
                accountInformationEntity.setSanctionAmount(dto.getSanctionAmount());
                accountInformationEntity.setNoOfInstallmentDue(dto.getNoOfInstallmentDue());
                //accountInformationEntity.setNextEMIDate(dto.getNextEMIDate());

                try {
                    accountInformationEntity.setNextEMIDate(dateUtils.db2ToOracleDateFormat(dto.getNextEMIDate().trim()));
                } catch (Exception e) {
                    //accountInformationEntity.setLatestDisbursementDate(dto.getLatestDisbursementDate());
                    System.out.println("accountNo===" + dto.getLoanACNo() + "getLatestDisbursementDate = " + dto.getNextEMIDate());
                }

                if(dto.getJointStatus() == null || dto.getJointStatus().equalsIgnoreCase("n"))
                    accountInformationEntity.setJointStatus("NOT JOINT");
                else
                    accountInformationEntity.setJointStatus("JOINT");

                try {
                    accountInformationEntity.setLatestDisbursementDate(dateUtils.db2ToOracleDateFormat(dto.getLatestDisbursementDate().trim()));
                } catch (Exception e) {
                    accountInformationEntity.setLatestDisbursementDate(dto.getLatestDisbursementDate());
                    System.out.println("accountNo===" + dto.getLoanACNo() + "getLatestDisbursementDate = " + dto.getLatestDisbursementDate());
                }


                try {
                    if (linkAccountStatus.toLowerCase().equals("n")) {
                        accountInformationEntity.setLinkAccountStatus("Active");
                    } else if (linkAccountStatus.toLowerCase().equals("y")) {
                        accountInformationEntity.setLinkAccountStatus("Inactive");
                    }
                } catch (Exception e) {
                    System.out.println("status");
                }

                accountInformationEntity.setLinkACProductCode(linkAcProductCode);
                accountInformationEntity.setDealAcBasic(dealAcBasic);
                accountInformationEntity.setDealAcSuffix(dealAcSuffix);
                accountInformationEntity.setPartyId(partyId);

                accountInformationEntity.setDocType(docType);
                accountInformationEntity.setBranchMnemonic(branchMnemonic);
                accountInformationEntity.setProductCode(productCode);
                accountInformationEntity.setDealReference(dealReference);

                accountInformationEntity.setLoanAccountNew(account + "" + branchMnemonic + "" + productCode + "" + dealReference);

                if (!accountInformationEntity.getIsDistributed().equalsIgnoreCase("Y"))
                    accountInformationEntity.setIsDistributed("N");
                else
                    accountInformationEntity.setIsDistributed("N");

                try {
                    accountInformationEntity.setLastPaymentDate(dateUtils.db2ToOracleDateFormat(dto.getLastPaymentDate().trim()));
                } catch (Exception e) {
                    accountInformationEntity.setLastPaymentDate(dto.getLastPaymentDate());
                    System.out.println("accountNo===" + dto.getLoanACNo() + "emidate = " + dto.getLastPaymentDate());
                }
                try {
                    accountInformationEntity.setFirstEmiDate(dateUtils.db2ToOracleDateFormat(dto.getEmiDate().trim()));

                } catch (Exception e) {
                    accountInformationEntity.setFirstEmiDate(dto.getEmiDate());
                    System.out.println("accountNo===" + dto.getLoanACNo() + "emidate = " + dto.getEmiDate());
                }
                try {
                    accountInformationEntity.setDisbursementDate(dateUtils.db2ToOracleDateFormat(dto.getDisbursementDate().trim()));
                } catch (Exception e) {
                    accountInformationEntity.setDisbursementDate(dto.getDisbursementDate());
                    System.out.println("accountNo===" + dto.getLoanACNo() + "emidate = " + dto.getDisbursementDate());
                }
                String expiryDate = dto.getExpiryDate() != null ? dateUtils.db2ToOracleDateFormat(dto.getExpiryDate().trim()):"";
                try {
                    accountInformationEntity.setExpiryDate(expiryDate);
                } catch (Exception e) {
                    accountInformationEntity.setExpiryDate(dto.getExpiryDate());
                    System.out.println("accountNo===" + dto.getLoanACNo() + "emidate = " + dto.getExpiryDate());
                }
                try {
                    accountInformationEntity.setDob(dateUtils.db2ToOracleDateFormat(dto.getDob().trim()));
                } catch (Exception e) {
                    accountInformationEntity.setDob(dto.getDob());
                    System.out.println("accountNo===" + dto.getLoanACNo() + "emidate = " + dto.getDob());
                }
                String firstInstallmentDueDate = dto.getFirstInstDueDate() != null ? dateUtils.db2ToOracleDateFormat(dto.getFirstInstDueDate().trim()): "";
                try{

                    accountInformationEntity.setFirstInstDueDate(firstInstallmentDueDate);
                }
                catch (Exception e) {
                    accountInformationEntity.setFirstInstDueDate(dto.getFirstInstDueDate());
                    System.out.println("accountNo===" + dto.getLoanACNo() + "emidate = " + dto.getFirstInstDueDate());
                }
                accountInformationEntity.setISEscalated("N");
                if(!expiryDate.equals(""))
                    accountInformationEntity.setDpdAfterExpiryDate(String.valueOf(dateUtils.getDiffernceBetweenTwoDate(expiryDate,new Date(),"yyyy-MM-dd")));
                if(!firstInstallmentDueDate.equals(""))
                    accountInformationEntity.setDpd(String.valueOf(dateUtils.getDiffernceBetweenTwoDate(firstInstallmentDueDate,new Date(),"yyyy-MM-dd")));
                accountInformationEntities.add(accountInformationEntity);

                System.out.println("test " + dto.getLoanACNo());

                LoanAccountDistributionInfo loanAccountDistributionInfo =
                        loanAccountDistributionRepository.findByAccountNoAndLatest(dto.getLoanACNo().trim(), "1");

                if (loanAccountDistributionInfo != null &&
                        (!loanAccountDistributionInfo.getOutStanding().equals(dto.getTotalOutstanding())
                                || !String.valueOf(loanAccountDistributionInfo.getOpeningOverDue()).equals(dto.getOverdue()))) {
                    UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                    LoanAccountDistributionInfo loanAccountDistributionInfo1 = new LoanAccountDistributionInfo();
                    BeanUtils.copyProperties(loanAccountDistributionInfo, loanAccountDistributionInfo1);
                    loanAccountDistributionInfo.setLatest("0");
                    loanAccountDistributionInfo.setStartDate(loanAccountDistributionInfo.getCreatedDate());
                    loanAccountDistributionInfo.setEndDate(new Date());

                    loanAccountDistributionRepository.save(loanAccountDistributionInfo);

                    loanAccountDistributionInfo1.setLatest("1");
                    loanAccountDistributionInfo1.setWriteOffAccount("0");
                    loanAccountDistributionInfo1.setSamAccount("0");
                    loanAccountDistributionInfo1.setCreatedDate(new Date());
                    loanAccountDistributionInfo1.setCreatedBy(user.getUsername());
                    loanAccountDistributionInfo1.setStatusDate(new Date());
                    loanAccountDistributionInfo1.setOutStanding(dto.getTotalOutstanding());
                    loanAccountDistributionInfo1.setOpeningOverDue(dto.getOverdue() != null ? Double.parseDouble(dto.getOverdue()) : 0.0);
                    loanAccountDistributionInfo1.setDpdBucket(accountInformationEntity.getDpd());
                    loanAccountDistributionInfo1.setEmiAmount(Double.parseDouble(dto.getEmiAmount()));
                    loanAccountDistributionInfo1.setStartDate(new Date());
                    loanAccountDistributionRepository.save(loanAccountDistributionInfo1);
                }

                if (accountInformationEntities.size() == 1000) {
                    accountInformationRepository.saveAll(accountInformationEntities);
                    System.out.println("innerlopp");
                    accountInformationEntities.clear();
                }
            }
        }


        if (accountInformationEntities.size() > 0 && accountInformationEntities.size() < 1000) {
            System.out.println("outerloop");

            accountInformationRepository.saveAll(accountInformationEntities);
            accountInformationEntities.clear();
        }
        return "200";
    }

    public AccountInformationEntity getAccountInformation(String accountNo) {
        return accountInformationRepository.getByLoanAccountNo(accountNo);
    }

    public AccountInformationEntity getAllAccountInformation(String accountNo, String branchMnemonic, String productCode, String dealReference) {
        return accountInformationRepository.getAllByLoanAccountNo(accountNo, branchMnemonic, productCode, dealReference);
    }


    public AccountInformationEntity findAccountInformationByLoanAccountNo(String accountNumber) {
        return accountInformationRepository.findAccountInformationEntityByLoanACNo(accountNumber);
    }

    public List<AccountInformationEntity> findAccountInformationEntityByLoanAccountNo(String accountNumber) {
        return accountInformationRepository.findAllByLoanACNo(accountNumber);
    }

    public AccountInformationEntity findAccountInformationEntityByLoanAccountNew(String accountNumber) {
        return accountInformationRepository.findAccountInformationEntityByLoanAccountNew(accountNumber);
    }

    public List<AccountInformationEntity> findAll() {
        return accountInformationRepository.findAll();
    }

    public List<AccountInformationEntity> advancedSearch(String accountNo, String cif, String customerName, String motherName, String mobileNo, String nid, String dob, String email, String passportNo, String organization, String linkAccount, String customerId, String autoDebit, String loanId, String clsFlag, String active) {
        if (!dob.isEmpty()) {
            String[] monthShortNames = {"Jan", "Feb", "Mar", "Apr", "May", "Jun",
                    "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
            String[] date = dob.split("/");
            for (int i = 0; i < monthShortNames.length; i++) {
                if (monthShortNames[i].equals(date[1])) {
                    int index = i + 1;
                    if (index < 10) date[1] = "0" + String.valueOf(i + 1);
                    else date[1] = String.valueOf(i + 1);
                    break;
                }
            }
            dob = date[2] + "-" + date[1] + "-" + date[0];
            System.out.println(date[0]);
        }
        return accountInformationRepository.advancedSearch(accountNo, customerName, motherName, mobileNo, nid, dob, email, linkAccount, customerId);
//        advanceSearchPayload.getOrganization(), advanceSearchPayload.getAutoDebit(),advanceSearchPayload.getCif(), advanceSearchPayload.getClsFlag(),
    }

    public List<AccountInformationEntity> advancedSearch(AdvanceSearchPayload payload) {
        return accountInformationRepository.advancedSearchDashboard(payload.getAccountNo(), payload.getCustomerName(), payload.getMotherName(), payload.getFatherName(), payload.getMobile(),
                payload.getNationalId(), payload.getDateOfBirth(), payload.getEmail(), payload.getLinkAccount(), payload.getCustomerId(), payload.getTin());
    }

    public ResponseEntity findAllAndPagination(int page, int length, String accountNo) {
        Pageable pageElements = PageRequest.of(page, length);
        Page<AccountInfoSMSDto> accountInfoSMSDtos1 = null;

        int size = accountInformationRepository.finAllEligibleSmsListCount();

        if (accountNo != null && accountNo != "") {
            List<Tuple> tuples = accountInformationRepository.finAllEligibleSmsList(accountNo);
            List<AccountInfoSMSDto> accountInfoSMSDtos = setValueToSMSDto(tuples);
            accountInfoSMSDtos1 = new PageImpl<AccountInfoSMSDto>(accountInfoSMSDtos, new PageRequest(page, length), size);
        } else {
            List<Tuple> tuples1 = accountInformationRepository.finAllEligibleSmsList1(pageElements);//accountInformationRepository.finAllEligibleSmsList(accountNo, pageElements);
            List<AccountInfoSMSDto> accountInfoSMSDtos = setValueToSMSDto(tuples1);
            accountInfoSMSDtos1 = new PageImpl<AccountInfoSMSDto>(accountInfoSMSDtos, new PageRequest(page, length), size);
        }
        return ResponseEntity.ok(accountInfoSMSDtos1);
    }


    public List<AccountInfoSMSDto> setValueToSMSDto(List<Tuple> tuples){
        List<AccountInfoSMSDto> accountInfoSMSDtos = new ArrayList<>();

        for(Tuple t : tuples){
            accountInfoSMSDtos.add(new AccountInfoSMSDto(t));
        }

        return accountInfoSMSDtos;
    }



    public ResponseEntity findAllByOverdueGreaterThanZero(int page, int length, String accountNo) {
        Pageable pageElements = PageRequest.of(page, length);
        Page<AccountInformationEntity> allProducts;
        List<AccountInformationEntity> allProducts1 = new ArrayList<>();
        if (accountNo != null && accountNo != "") {
            allProducts = accountInformationRepository.findAllByOverdueGreaterThanZero(accountNo, pageElements);
        } else {
            allProducts1 = accountInformationRepository.findAllByOverdueGreaterThanZero();
        }
        return ResponseEntity.ok(allProducts1);
    }

    public ResponseEntity findAllAndPaginationByIsSmsSent(int page, int length, String accountNo) {
        Page<AccountInformationEntity> allProducts;

        Pageable pageElements = PageRequest.of(page, length);
        Page<AccountInfoSMSDto> accountInfoSMSDtos1 = null;

        int size = accountInformationRepository.finAllEligibleDistributionListCount();

        if (accountNo != null && accountNo != "") {
            List<Tuple> tuples = accountInformationRepository.finAllEligibleDistributionList(accountNo);
            List<AccountInfoSMSDto> accountInfoSMSDtos = setValueToSMSDto(tuples);
            accountInfoSMSDtos1 = new PageImpl<AccountInfoSMSDto>(accountInfoSMSDtos, new PageRequest(page, length), size);
        } else {
            List<Tuple> tuples1 = accountInformationRepository.finAllEligibleDistributionList1(pageElements);//accountInformationRepository.finAllEligibleSmsList(accountNo, pageElements);
            List<AccountInfoSMSDto> accountInfoSMSDtos = setValueToSMSDto(tuples1);
            accountInfoSMSDtos1 = new PageImpl<AccountInfoSMSDto>(accountInfoSMSDtos, new PageRequest(page, length), size);
        }
        return ResponseEntity.ok(accountInfoSMSDtos1);

//        if (accountNo != null && accountNo != "") {
//            allProducts = accountInformationRepository.findAllByLoanACNoByIsSmsEntityAndOverdueGreaterThanZero(accountNo, pageElements);
//        } else {
//            allProducts = accountInformationRepository.findAllAccIsSmsEntityAndOverdueGreaterThanZero(pageElements);
//        }
//        return ResponseEntity.ok(allProducts);
    }

    public void writeExcel() throws IOException {
        List<AccountInformationEntity> accountInformationEntities = accountInformationRepository.finAllEligibleDistributionList();
        SXSSFWorkbook workbook = new SXSSFWorkbook();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        Sheet sheet = workbook.createSheet("UnAllocated_Account_List");
        Map<String, Object[]> data = new TreeMap<>();
        //data.put("1", );

        String arr[] = {"Account No","Dealer PIN","Dealer Name","Branch Mnemonic","Product Code",
                "Deal Reference"};

        Cell cell0 = null;
        Row row1 = sheet.createRow(0);
        for(int h=0; h<arr.length; h++){
            cell0 = row1.createCell(h);
            cell0.setCellValue(arr[h]);
        }

        //iterating r number of rows
        AccountInformationEntity accountInformationEntity;
        for (int r = 0; r < accountInformationEntities.size(); r++) {
            Row row = sheet.createRow(r+1);
            accountInformationEntity = accountInformationEntities.get(r);
            //iterating c number of columns
            Cell cell1 = row.createCell(0);
            cell1.setCellValue(accountInformationEntity.getLoanACNo()==null?"":accountInformationEntity.getLoanACNo());
            Cell cell2 = row.createCell(1);
            cell2.setCellValue("");
            Cell cell3 = row.createCell(2);
            cell3.setCellValue("");
            Cell cell4 = row.createCell(3);
            cell4.setCellValue(accountInformationEntity.getBranchMnemonic() == null?"":accountInformationEntity.getBranchMnemonic());
            Cell cell5 = row.createCell(4);
            cell5.setCellValue(accountInformationEntity.getProductCode() ==null?"":accountInformationEntity.getProductCode());
            Cell cell6 = row.createCell(5);
            cell6.setCellValue(accountInformationEntity.getDealReference()==null?"":accountInformationEntity.getDealReference());
        }

        //String fileLocation = new File("src\\main\\resources\\generatedExcel").getAbsolutePath() + "\\" + sheet.getSheetName()+".xlsx";
        String fileLocation = new File(excelServerPath).getAbsolutePath() + "/" + sheet.getSheetName()+".xlsx";
        FileOutputStream out = new FileOutputStream(fileLocation);
        workbook.write(out);

        out.close();
    }

    public void writeDelinquentExcel() throws IOException {
        List<AccountInformationEntity> accountInformationEntities = accountInformationRepository.findAllByOverdueGreaterThanZero();
        SXSSFWorkbook workbook = new SXSSFWorkbook();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        Sheet sheet = workbook.createSheet("Delinquent_Account_List");
        Map<String, Object[]> data = new TreeMap<>();
        //data.put("1", );

        String arr[] = {"Account No","Customer Name","Mobile","Branch Mnemonic","Product Code",
                "Deal Reference", "Outstanding", "Overdue", "EMI Amount", "No Of Installment Due",
        "Loan Status", "Distribution Status","SMS Sending Status"};

        Cell cell0 = null;
        Row row1 = sheet.createRow(0);
        for(int h=0; h<arr.length; h++){
            cell0 = row1.createCell(h);
            cell0.setCellValue(arr[h]);
        }

        //iterating r number of rows
        AccountInformationEntity accountInformationEntity;
        for (int r = 0; r < accountInformationEntities.size(); r++) {
            Row row = sheet.createRow(r+1);
            accountInformationEntity = accountInformationEntities.get(r);
            //iterating c number of columns
            Cell cell1 = row.createCell(0);
            cell1.setCellValue(accountInformationEntity.getLoanACNo()==null?"":accountInformationEntity.getLoanACNo());
            Cell cell2 = row.createCell(1);
            cell2.setCellValue(accountInformationEntity.getCustomerName() == null ? "":accountInformationEntity.getCustomerName());
            Cell cell3 = row.createCell(2);
            cell3.setCellValue(accountInformationEntity.getMobile() == null ? "":accountInformationEntity.getMobile());
            Cell cell4 = row.createCell(3);
            cell4.setCellValue(accountInformationEntity.getBranchMnemonic() == null?"":accountInformationEntity.getBranchMnemonic());
            Cell cell5 = row.createCell(4);
            cell5.setCellValue(accountInformationEntity.getProductCode() ==null?"":accountInformationEntity.getProductCode());
            Cell cell6 = row.createCell(5);
            cell6.setCellValue(accountInformationEntity.getDealReference()==null?"":accountInformationEntity.getDealReference());
            Cell cell7 = row.createCell(6);
            cell7.setCellValue(accountInformationEntity.getTotalOutstanding()==null?"":accountInformationEntity.getTotalOutstanding());
            Cell cell8 = row.createCell(7);
            cell8.setCellValue(accountInformationEntity.getOverdue()==null?"":accountInformationEntity.getOverdue());
            Cell cell9 = row.createCell(8);
            cell9.setCellValue(accountInformationEntity.getEmiAmount()==null?"":accountInformationEntity.getEmiAmount());
            Cell cell10 = row.createCell(9);
            cell10.setCellValue(accountInformationEntity.getNoOfInstallmentDue()==null?"":accountInformationEntity.getNoOfInstallmentDue());
            Cell cell11 = row.createCell(10);
            cell11.setCellValue(accountInformationEntity.getLoanCLStatus()==null?"":accountInformationEntity.getLoanCLStatus());
            Cell cell12 = row.createCell(11);
            cell12.setCellValue(accountInformationEntity.getIsDistributed()==null?"":accountInformationEntity.getIsDistributed());
            Cell cell13 = row.createCell(12);
            cell13.setCellValue(accountInformationEntity.getIsSmsSent()==null?"":accountInformationEntity.getIsSmsSent());
        }

        //String fileLocation = new File("src\\main\\resources\\generatedExcel").getAbsolutePath() + "\\" + sheet.getSheetName()+".xlsx";
        String fileLocation = new File(excelServerPath).getAbsolutePath() + "/" + sheet.getSheetName()+".xlsx";
        FileOutputStream out = new FileOutputStream(fileLocation);
        workbook.write(out);

        out.close();
    }

    public ResponseEntity findAllEscalationAccount(int page, int length, String accountNo) {
        Pageable pageElements = PageRequest.of(page, length);
        Page<AccountInformationEntity> allProducts;
        if (accountNo != null && accountNo != "") {
            allProducts = accountInformationRepository.findAllByLoanACNoByIsSmsEntityAndOverdueGreaterThanZeroAndEscalation(accountNo, pageElements);
        } else {
            allProducts = accountInformationRepository.findAllAccIsSmsEntityAndOverdueGreaterThanZeroEscalation(pageElements);
        }
        return ResponseEntity.ok(allProducts);
    }

}
