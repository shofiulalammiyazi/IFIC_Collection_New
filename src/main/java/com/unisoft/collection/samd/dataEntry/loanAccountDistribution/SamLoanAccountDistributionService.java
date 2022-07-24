package com.unisoft.collection.samd.dataEntry.loanAccountDistribution;

import com.unisoft.beans.Validation;
import com.unisoft.loanApi.model.LoanAccDetails;
import com.unisoft.loanApi.model.LoanAccInfo;
import com.unisoft.collection.distribution.loan.LoanDistributionService;
import com.unisoft.collection.distribution.loan.loanAccount.LoanAccountInfo;
import com.unisoft.collection.distribution.loan.loanAccount.LoanAccountService;
import com.unisoft.collection.distribution.loan.loanAccountBasic.LoanAccountBasicInfo;
import com.unisoft.collection.distribution.loan.loanAccountBasic.LoanAccountBasicService;
import com.unisoft.collection.distribution.loan.loanAccountDistribution.LoanAccountDistributionService;
import com.unisoft.collection.distribution.loan.loanAccountOther.LoanAccountOtherInfo;
import com.unisoft.collection.distribution.loan.loanAccountOther.LoanAccountOtherService;
import com.unisoft.collection.settings.agency.AgencyEntity;
import com.unisoft.collection.settings.agency.AgencyService;
import com.unisoft.collection.settings.employee.EmployeeInfoEntity;
import com.unisoft.collection.settings.employee.EmployeeService;
import com.unisoft.common.CommonService;
import com.unisoft.customerbasicinfo.CustomerBasicInfoEntity;
import com.unisoft.customerbasicinfo.CustomerBasicInfoService;
import com.unisoft.loanApi.service.RetailLoanUcbApiService;
import com.unisoft.user.UserPrincipal;
import com.unisoft.utillity.DateUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Tuple;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service("samLoanAccountDistributionService")
public class SamLoanAccountDistributionService implements CommonService<SamLoanAccountDistribution> {

    @Autowired
    private DateUtils dateUtils;

    @Autowired
    private AgencyService agencyService;

    @Autowired
    private RetailLoanUcbApiService retailLoanUcbApiService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private LoanAccountService loanAccountService;

    @Autowired
    private LoanDistributionService loanDistributionService;

    @Autowired
    private LoanAccountOtherService loanAccountOtherService;

    @Autowired
    private LoanAccountBasicService loanAccountBasicService;

    @Autowired
    private CustomerBasicInfoService customerBasicInfoService;

    @Autowired
    private LoanAccountDistributionService loanAccountDistributionService;

    @Autowired
    private SamLoanAccountDistributionRepository samLoanAccountDistributionRepository;

    /*To Insert Data From Excel*/
    public Map<String, Object> storeData(MultipartFile file){
        Map<String, Object> response = new HashMap<>();
        // Check Validity
        List<String> errorMessages = this.validate(file);

        // check has errors or not
        if (errorMessages.size() == 0){
            List<SamLoanAccountDistribution> allSamLoanAccountDistributions = new ArrayList<>();
            List<LoanAccountInfo> allLoanAccountInfo = new ArrayList<>();
            List<LoanAccountOtherInfo> allLoanAccountOtherInfo = new ArrayList<>();
            List<String> accountNos = new ArrayList<>();

            try{
                XSSFWorkbook xssfWorkbook = new XSSFWorkbook(file.getInputStream());
                XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);

                /*Get First Row for Column Names*/
                Iterator<Row> rows = xssfSheet.iterator();
                Row row = rows.next();

                /*Get All Column Names*/
                Iterator<Cell> cells = row.cellIterator();
                List<String> cellNames = new ArrayList<>();
                while (cells.hasNext()){
                    cellNames.add(Objects.toString(cells.next(), "").toUpperCase());
                }

                while (rows.hasNext()){
                    row = rows.next();
                    SamLoanAccountDistribution samLoanAccountDistribution = new SamLoanAccountDistribution();
                    samLoanAccountDistribution.setStatusDate(new Date());

                    Cell cellAccountNo = row.getCell(cellNames.indexOf("ACCOUNT NO"));
                    String accountNo = Objects.toString(cellAccountNo, "");
                    accountNos.add(accountNo);

                    Cell cellAccountName = row.getCell(cellNames.indexOf("A/C NAME"));
                    String accountName = Objects.toString(cellAccountName, "");

                    Cell cellCustomerID = row.getCell(cellNames.indexOf("CUSTOMER ID"));
                    String customerId = Objects.toString(cellCustomerID, "");

                    Cell cellLoanLiabilityNo = row.getCell(cellNames.indexOf("LOAN LIABILITY NO"));
                    samLoanAccountDistribution.setLoanLiabilityNo(Objects.toString(cellLoanLiabilityNo, ""));

                    Cell cellDealerID = row.getCell(cellNames.indexOf("DEALER ID"));
                    samLoanAccountDistribution.setDealerPin(Objects.toString(cellDealerID, ""));

                    Cell cellDealerName = row.getCell(cellNames.indexOf("DEALER NAME"));
                    samLoanAccountDistribution.setDealerName(Objects.toString(cellDealerName, ""));

                    /* Process other data
                     * Update customer informations */
                    CustomerBasicInfoEntity customerInfo = new CustomerBasicInfoEntity(accountNo);
                    customerInfo.setCustomerId(customerId);
                    customerInfo = customerBasicInfoService.findOrSave(customerInfo);
                    if (customerInfo == null || customerInfo.getId() == null) continue;

                    /*Update Loan Account Basic Information*/
                    Date expiryDate = new Date();

                    LoanAccountBasicInfo loanAccountBasicInfo = new LoanAccountBasicInfo("-", expiryDate, customerInfo);
                    loanAccountBasicInfo.setAccountName(accountName);
                    loanAccountBasicInfo.setDisbursementDate(null);
                    loanAccountBasicInfo = loanAccountBasicService.findOrSave(loanAccountBasicInfo);

                    /*Set loan account distribution information */
                    Double outStanding = 0d;
                    Double dpd = 0d;
                    String dpdBucket = "X";

                    Long loanAccountBasicInfoId = loanAccountBasicInfo.getId();
                    loanAccountDistributionService.updateLoanAccountDistributionLatestStatus(loanAccountBasicInfoId);

                    /* Loan Account Information */
                    LoanAccountInfo loanAccountInfo = new LoanAccountInfo();
                    loanAccountInfo.setAte(0d);
                    loanAccountInfo.setAssetClassification("-");
                    loanAccountInfo.setInstallmentAmount(0d);
                    loanAccountInfo.setSchemeCode("-");
                    loanAccountInfo.setTotalOverdue(0d);
                    loanAccountInfo.setTotalOutstanding(outStanding);
                    loanAccountInfo.setDpd(dpd);
                    loanAccountInfo.setEmiDueDate("-");
                    loanAccountInfo.setDpbBucket(dpdBucket);
                    loanAccountInfo.setBranchName("-");
                    loanAccountInfo.setLoanAccountBasicInfo(loanAccountBasicInfo);
                    loanAccountService.saveOrUpdate(loanAccountInfo);

                    LoanAccountOtherInfo loanAccountOtherInfo = new LoanAccountOtherInfo();
                    loanAccountOtherInfo.setNotificationStatus("-");
                    loanAccountOtherInfo.setLoanAccountBasicInfo(loanAccountBasicInfo);
                    loanAccountOtherService.saveOrUpdate(loanAccountOtherInfo);

                    /*Remember data in memory to updateLoanAccountDistributionLatestStatus all data*/
                    samLoanAccountDistribution.setLoanAccountBasicInfo(loanAccountBasicInfo);

                    allSamLoanAccountDistributions.add(samLoanAccountDistribution);
                    allLoanAccountInfo.add(loanAccountInfo);
                    allLoanAccountOtherInfo.add(loanAccountOtherInfo);
                }

                List<SamLoanAccountDistribution> previousDistribution = samLoanAccountDistributionRepository.findAllByAccountNo(accountNos);
                for (SamLoanAccountDistribution samLoanAccountDistribution : previousDistribution){
                    samLoanAccountDistribution.setLatest(false);
                }
                // Latest false for previous distributions
                samLoanAccountDistributionRepository.saveAll(previousDistribution);

                //Save ALL Data
                samLoanAccountDistributionRepository.saveAll(allSamLoanAccountDistributions);

                //Update data, fetch from client database
                this.manageBulkLoanAccountDistributionAsync(allSamLoanAccountDistributions, allLoanAccountInfo, allLoanAccountOtherInfo);
            } catch (IOException e){
                e.printStackTrace();
            }

            response.put("outcome", "success");
        }
        else{
            response.put("outcome", "failure");
            response.put("errors", errorMessages);
        }

        return response;
    }

    public Map<String, Object> updateManualDealerAllocationData(SamLoanAccountDistributionDTO distributionDTO){
        Map<String, Object> response = new HashMap<>();
        List<String> errorMessages = this.validate(distributionDTO, "DEALER ALLOCATION");

        if (errorMessages.size() == 0) {
            List<Long> selectedIds = Arrays.asList(distributionDTO.getSelectedIds());
            List<SamLoanAccountDistribution> distributions = samLoanAccountDistributionRepository.findAllByIds(selectedIds);
            EmployeeInfoEntity dealer = employeeService.getById(distributionDTO.getDealerId());

            for (SamLoanAccountDistribution distribution : distributions) {

                distribution.setDealerPin(dealer.getUser().getUsername());
                distribution.setDealerName(dealer.getUser().getFirstName());
            }

            samLoanAccountDistributionRepository.saveAll(distributions);
            response.put("outcome", "success");
        }
        else {
            response.put("errors", errorMessages);
            response.put("outcome", "failure");
        }

        return response;
    }

    public Map<String, Object> updateManualAgencyAllocationData(SamLoanAccountDistributionDTO distributionDTO){
        Map<String, Object> response = new HashMap<>();
        List<String> errorMessages = this.validate(distributionDTO, "AGENCY ALLOCATION");

        if (errorMessages.size() == 0) {
            List<Long> selectedIds = Arrays.asList(distributionDTO.getSelectedIds());
            List<SamLoanAccountDistribution> distributions = samLoanAccountDistributionRepository.findAllByIds(selectedIds);
            AgencyEntity agency = agencyService.getById(distributionDTO.getAgencyId());

            for (SamLoanAccountDistribution distribution : distributions) {
                distribution.setAgencyId(agency.getId());
                distribution.setAgencyName(agency.getName());
                distribution.setMonitoringStatus(distributionDTO.getMonitoringStatus());
            }

            samLoanAccountDistributionRepository.saveAll(distributions);
            response.put("outcome", "success");
        }
        else {
            response.put("errors", errorMessages);
            response.put("outcome", "failure");
        }

        return response;
    }

    @Override
    public Map<String, Object> storeData(SamLoanAccountDistribution data) {
        return null;
    }

    @Override
    public Map<String, Object> updateData(SamLoanAccountDistribution data) {
        return null;
    }

    @Override
    public Map<String, Object> deleteData(SamLoanAccountDistribution data) {
        return null;
    }

    @Override
    public SamLoanAccountDistribution findDataById(Long id) {
        return null;
    }

    @Override
    public List<SamLoanAccountDistribution> findAllData() {
        List<SamLoanAccountDistribution> allDistributions = samLoanAccountDistributionRepository.findAllLatest();
        return allDistributions;
    }

    @Override
    public List<String> validate(SamLoanAccountDistribution data) {
        ArrayList<String> errors = new ArrayList<>();
        return errors;
    }

    public List<String> validate(MultipartFile file) {
        ArrayList<String> errors = new ArrayList<>();
        List<String> validCells = Arrays.asList(
                new String[]{"ACCOUNT NO", "A/C NAME", "CUSTOMER ID", "LOAN LIABILITY NO", "DEALER ID", "DEALER NAME"});

        if (Validation.isStringEmpty(file.getOriginalFilename())){
            errors.add("Attachment File required");
        }
        else {
            try{
                XSSFWorkbook xssfWorkbook = new XSSFWorkbook(file.getInputStream());
                XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);

                Iterator<Row> rows = xssfSheet.iterator();
                Row row = rows.next();

                Iterator<Cell> cells = row.cellIterator();
                List<String> addedCellNames = new ArrayList<String>();

                while (cells.hasNext()){
                    addedCellNames.add(Objects.toString(cells.next(), "").toUpperCase());
                }

                //Check Columns Validity
                for (String cellName:validCells){
                    if (!addedCellNames.contains(cellName)){
                        errors.add(cellName + " is required");
                    }
                }

            } catch (IOException e){
                e.printStackTrace();
            }
        }

        return errors;
    }

    public List<String> validate(SamLoanAccountDistributionDTO distributionDTO, String methodFlag) {
        ArrayList<String> errors = new ArrayList<>();

        if (methodFlag.equals("DEALER ALLOCATION")){
            if (distributionDTO.getDealerId() == null){
                errors.add("Dealer empty is not allowed.");
            }
        }
        else if (methodFlag.equals("AGENCY ALLOCATION")){
            if (distributionDTO.getAgencyId() == null){
                errors.add("Agency empty is not allowed.");
            }

            if (Validation.isStringEmpty(distributionDTO.getMonitoringStatus())){
                errors.add("Monitoring Status empty is not allowed.");
            }
        }

        return errors;
    }

    public void manageBulkLoanAccountDistributionAsync(List<SamLoanAccountDistribution> distributionInfos,
                                                       List<LoanAccountInfo> accountInfos,
                                                       List<LoanAccountOtherInfo> accountOtherInfos) {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = user.getUsername();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                ExecutorService executorService = Executors.newFixedThreadPool(100);
                int index = 0;

                for (SamLoanAccountDistribution distribution : distributionInfos){
                    LoanAccountInfo loanAccountInfo = accountInfos.get(index);
                    LoanAccountOtherInfo loanAccountOtherInfo = accountOtherInfos.get(index);
                    index += 1;

                    executorService.execute(new Runnable() {
                        @Override
                        public void run() {
                            if (!isValidDistribution(distribution)) return;

                            LoanAccountBasicInfo loanAccountBasicInfo = distribution.getLoanAccountBasicInfo();
                            String accountNumber = loanAccountBasicInfo.getAccountNo();

                            /* Fetch account information from client database server */
                            LoanAccInfo clientLoanAccountInfo = retailLoanUcbApiService.getLoanAccountInfo(accountNumber);
                            LoanAccDetails clienLoanAccountDetails = retailLoanUcbApiService.getLoanAccountDetails(accountNumber);

                            String customerId = clientLoanAccountInfo.getCustomerId();
                            CustomerBasicInfoEntity customer = loanAccountBasicInfo.getCustomer();
                            // Todo: update the below statement
//                            customer = loanDistributionService.updateCustomerInfo(customer, customerId, clienLoanAccountDetails, username);
                            loanAccountBasicInfo = loanDistributionService.updateLoanAccountBasicInfo(loanAccountBasicInfo, clientLoanAccountInfo, clienLoanAccountDetails, username);

                            updateLoanAccountDistributionInfo(distribution, loanAccountBasicInfo, clienLoanAccountDetails);
                            // Todo: update the below statement
//                            loanDistributionService.updateLoanAccountInfo(loanAccountInfo, loanAccountBasicInfo, username);
                            loanDistributionService.updateLoanAccountOtherInfo(loanAccountOtherInfo, loanAccountBasicInfo, clientLoanAccountInfo, username);
                        }
                    });
                }
                executorService.shutdown();
            }
        });
        thread.start();
    }

    public SamLoanAccountDistribution updateLoanAccountDistributionInfo(SamLoanAccountDistribution distribution,
                                                                         LoanAccountBasicInfo loanAccountBasicInfo, LoanAccDetails loanAccDetails) {
        loanDistributionService.populateLoanAccDetails(loanAccDetails, loanAccountBasicInfo.getAccountNo());

        String liabilityNo = loanAccDetails.getLiabilityNo();

        String schemeCode = loanAccDetails.getProductCode();
        Double totalOverdue = loanAccDetails.getOverdueAmount();
        Double outStanding = loanAccDetails.getPrincipalOutstanding();
        Double emiAmount = loanAccDetails.getEmiAmount();

        Double lastPayment = loanAccDetails.getAmountPaidLastMnth();
        Date lastPaymentDate = dateUtils.getFormattedDate(loanAccDetails.getLastPaymentDate(), "dd-MM-yyyy");
        Double totalPayment = loanAccDetails.getTotalAmountPaid();

        distribution.setLoanLiabilityNo(liabilityNo);

        distribution.setOutStanding(outStanding.toString());
        distribution.setOpeningOverDue(totalOverdue);
        distribution.setSchemeCode(schemeCode);
        distribution.setLoanAccountBasicInfo(loanAccountBasicInfo);
        distribution.setEmiAmount(emiAmount);

        distribution.setLastPaidAmount(lastPayment);
        distribution.setLastPaymnetDate(lastPaymentDate);
        distribution.setTotalPayment(totalPayment);

        samLoanAccountDistributionRepository.save(distribution);
        return distribution;
    }

    /*
     * Created By :~ Hasibul Islam
     * Software Engineer
     * 13-Jan-2021 11:40 AM
     * For Loan Account Distribution Summary Dealer Dashboard.
     * */
    public List<SamLoanAccountDistributionSummary> getLoanAccountDistributionSummary(String dealerPin){
        List<Tuple> summaries = samLoanAccountDistributionRepository.getLoanAccountDistributionSummary(dealerPin);
        List<SamLoanAccountDistributionSummary> finalizedSummary = new ArrayList<>();

        for (Tuple summary : summaries){
            SamLoanAccountDistributionSummary distributionSummary = new SamLoanAccountDistributionSummary();

            distributionSummary.setAccountNo(summary.get("accountNo"));
            distributionSummary.setContact(summary.get("contact"));
            distributionSummary.setNoContact(summary.get("noContact"));

            distributionSummary.setBrokenPtp(summary.get("brokenPtp"));
            distributionSummary.setKeptPtp(summary.get("keptPtp"));
            distributionSummary.setCuredPtp(summary.get("curedPtp"));

            distributionSummary.setSaveAmount(summary.get("saveAmount"));
            distributionSummary.setBackAmount(summary.get("backAmount"));
            distributionSummary.setFlowAmount(summary.get("flowAmount"));
            distributionSummary.setFollowUpDate(summary.get("followUpDate"));

            distributionSummary.setCurrentMonthPayment(summary.get("currentMonthPayment"));
            distributionSummary.setLastPayment(summary.get("lastPayment"));
            distributionSummary.setLastPaymentDate(summary.get("lastPaymentDate"));

            distributionSummary.setOverdueAmount(summary.get("overdueAmount"));
            distributionSummary.setBranchName(summary.get("branchName"));
            distributionSummary.setDpdBucket(summary.get("dpdBucket"));

            distributionSummary.setLinkAccountNo(summary.get("linkAccountNo"));
            distributionSummary.setLinkAccountBalance(summary.get("linkAccountBalance"));
            distributionSummary.setRiskCategory(summary.get("riskCategory"));

            finalizedSummary.add(distributionSummary);
        }

        return finalizedSummary;
    }

    public SamLoanAccountDistribution findLatestLoanAccountDistributionInfo(LoanAccountBasicInfo loanAccountBasicInfo) {
        return samLoanAccountDistributionRepository
                .findFirstByLoanAccountBasicInfoOrderByCreatedDateDesc(loanAccountBasicInfo);
    }

    public List<Map<String, Object>> getLoanAccountDealerAllocationHistory(String accountNo, Date startDate, Date endDate) {
        List<Map<String, Object>> histories = new ArrayList<>();
        List<Tuple> allHistoriesData = samLoanAccountDistributionRepository.getLoanAccountDealerAllocationHistory(accountNo, startDate, endDate);
        for (Tuple historyData : allHistoriesData) {

            Object dealerId = historyData.get("dealerId");
            Object dealerName = historyData.get("dealerName");
            String dealer = dealerId == null ? "" : (String) dealerId;
            dealer += (dealerId == null || dealerName == null ? "" : " - ");
            dealer += dealerName;

            Object createdById = historyData.get("createdById");
            Object createdByName = historyData.get("createByName");
            String createdBy = (String) createdById;
            createdBy += (createdById == null || createdByName == null ? "" : " - ");
            createdBy += createdByName;

            SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy hh:mm aa");
            String createdAt = formatter.format(historyData.get("createdDate"));
            /*------------------------------------------------------------*/
            Map<String, Object> history = new HashMap<>();

            history.put("dealer", dealer);
            history.put("createdBy", createdBy);
            history.put("createdAt", createdAt);

            histories.add(history);
        }
        return histories;
    }

    public boolean isValidDistribution(SamLoanAccountDistribution distributionInfo) {
        return distributionInfo != null && loanAccountBasicService.isValidAccount(distributionInfo.getLoanAccountBasicInfo())
                && customerBasicInfoService.isValidCustomer(distributionInfo.getLoanAccountBasicInfo().getCustomer());
    }
}
