package com.csinfotechbd.retail.loan.dataEntry.writeOffAccountDistribution;

import com.csinfotechbd.audittrail.AuditTrailService;
import com.csinfotechbd.collection.distribution.loan.loanAccount.LoanAccountInfo;
import com.csinfotechbd.collection.distribution.loan.loanAccount.LoanAccountService;
import com.csinfotechbd.collection.distribution.loan.loanAccountBasic.LoanAccountBasicInfo;
import com.csinfotechbd.collection.distribution.loan.loanAccountBasic.LoanAccountBasicService;
import com.csinfotechbd.collection.distribution.loan.loanAccountDistribution.LoanAccountDistributionService;
import com.csinfotechbd.collection.distribution.loan.loanAccountOther.LoanAccountOtherInfo;
import com.csinfotechbd.collection.distribution.loan.loanAccountOther.LoanAccountOtherService;
import com.csinfotechbd.customerbasicinfo.CustomerBasicInfoEntity;
import com.csinfotechbd.customerbasicinfo.CustomerBasicInfoService;
import com.csinfotechbd.utillity.DateUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.apache.poi.ss.usermodel.Cell;

import javax.persistence.Tuple;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class WriteOffAccountDistributionServiceImpl implements WriteOffAccountDistributionService{

    @Autowired
    private WriteOffAccountDistributionRepository writeOffAccountDistributionRepository;

    @Autowired
    private CustomerBasicInfoService customerBasicInfoService;
    @Autowired
    private LoanAccountService loanAccountService;

    @Autowired
    private LoanAccountOtherService loanAccountOtherService;

    @Autowired
    private LoanAccountBasicService loanAccountBasicService;
    @Autowired
    private LoanAccountDistributionService loanAccountDistributionService;
    @Autowired
    private DateUtils dateUtils;
    @Autowired
    private AuditTrailService auditTrailService;

    @Override
    public List<WriteOffAccountDistribution> findAll() {
        return writeOffAccountDistributionRepository.findAll();
    }

    @Override
    public void storeData(MultipartFile file) {

        List<WriteOffAccountDistribution> writeOffAccountDistributionList = new ArrayList<>();
        List<LoanAccountInfo> allLoanAccountInfo = new ArrayList<>();
        List<LoanAccountOtherInfo> allLoanAccountOtherInfo = new ArrayList<>();
        List<String> accountNos = new ArrayList<>();
        WriteOffAccountDistribution previousData = new WriteOffAccountDistribution();

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
                cellNames.add(Objects.toString(cells.next(), ""));
            }

            while (rows.hasNext()){
                row = rows.next();
                WriteOffAccountDistribution writeOffAccountDistribution = new WriteOffAccountDistribution();
                writeOffAccountDistribution.setWOffDate(new Date());

                Cell cellAccountNo = row.getCell(cellNames.indexOf("Account No"));
                String accountNo = Objects.toString(cellAccountNo, "");
                writeOffAccountDistribution.setAccountNumber(accountNo);
                accountNos.add(accountNo);

                Cell cellAccountName = row.getCell(cellNames.indexOf("Account Name"));
                String accountName = Objects.toString(cellAccountName, "");
                writeOffAccountDistribution.setAccountName(accountName);

                Cell cellAccountStatus = row.getCell(cellNames.indexOf("Account Status"));
                String accountStatus = Objects.toString(cellAccountStatus, "");
                writeOffAccountDistribution.setAccountStatus(accountStatus);

                Cell location = row.getCell(cellNames.indexOf("Location"));
                writeOffAccountDistribution.setLocation(Objects.toString(location, ""));

                Cell cellProductCode = row.getCell(cellNames.indexOf("Product Code"));
                String productCode = Objects.toString(cellProductCode, "");
                writeOffAccountDistribution.setProductCode(productCode);

                Cell cellBranchCode = row.getCell(cellNames.indexOf("Branch Code"));
                String branchCode = Objects.toString(cellBranchCode, "");
                writeOffAccountDistribution.setBranchCode(branchCode);

                Cell cellDealerID = row.getCell(cellNames.indexOf("Dealer Id"));
                String dealerPin = Objects.toString(cellDealerID, "");
                writeOffAccountDistribution.setDealerPin(dealerPin);

                Cell cellDealerName = row.getCell(cellNames.indexOf("Dealer Name"));
                String dealerName = Objects.toString(cellDealerName, "");
                writeOffAccountDistribution.setDealerName(dealerName);

//                Cell cellCustomerId = row.getCell(cellNames.indexOf("Customer Id"));
//                writeOffAccountDistribution.setCustomerId(Objects.toString(cellCustomerId, ""));

                /* Process other data
                 * Update customer informations */
                CustomerBasicInfoEntity customerInfo = new CustomerBasicInfoEntity(accountNo);
                customerInfo = customerBasicInfoService.findOrSave(customerInfo);
//                writeOffAccountDistribution.setBranchCode(customerInfo.getBranchCode());
//                writeOffAccountDistribution.setBranchName(customerInfo.getBranchName());

                //implement by shahin start
                if (customerInfo == null || customerInfo.getId() == null)
                writeOffAccountDistributionList.add(writeOffAccountDistribution);
                //implement by shahin end

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
                writeOffAccountDistribution.setLoanAccountBasicInfo(loanAccountBasicInfo);

                writeOffAccountDistributionList.add(writeOffAccountDistribution);
                allLoanAccountInfo.add(loanAccountInfo);
                allLoanAccountOtherInfo.add(loanAccountOtherInfo);
            }

            List<WriteOffAccountDistribution> previousDistribution = writeOffAccountDistributionRepository.findAllByAccountNo(accountNos);
            for (WriteOffAccountDistribution writeOffAccountDistribution : previousDistribution){
                writeOffAccountDistribution.setLatest(false);
            }
            // Latest false for previous distributions
            writeOffAccountDistributionRepository.saveAll(previousDistribution);

            //Save ALL Data
            writeOffAccountDistributionRepository.saveAll(writeOffAccountDistributionList);
            auditTrailService.saveCreatedData("Write Off Account Distribution via Excel", writeOffAccountDistributionList);
            auditTrailService.saveUpdatedData("Write Off Account Distribution via Excel",previousData,writeOffAccountDistributionList);
            //Update data, fetch from client database
            //this.manageBulkLoanAccountDistributionAsync(allSamLoanAccountDistributions, allLoanAccountInfo, allLoanAccountOtherInfo);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public List<WriteOffAccountDistribution> findByDealerPinAndLatest(String pin) {
        return writeOffAccountDistributionRepository.findByDealerPinAndLatest(pin);
    }

    @Override
    public List<WriteOffAccountdistributionSummary> findCurrentMonthDealerWriteOffAccountDistribution(String username) {
        Date startDate = dateUtils.getMonthStartDate();
        Date endDate = dateUtils.getMonthEndDate();
        List<Tuple> summaries = writeOffAccountDistributionRepository.findWriteOffAccountDistributionByDealerPinAndMonth(username, startDate,endDate);
        return summaries.stream().map(item -> new WriteOffAccountdistributionSummary(item)).collect(Collectors.toList());
    }
}
