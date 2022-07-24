package com.unisoft.collection.samd.dataEntry.writtenOffExecution;

import com.unisoft.beans.Validation;
import com.unisoft.collection.distribution.loan.loanAccount.LoanAccountInfo;
import com.unisoft.collection.distribution.loan.loanAccount.LoanAccountService;
import com.unisoft.collection.distribution.loan.loanAccountBasic.LoanAccountBasicInfo;
import com.unisoft.collection.distribution.loan.loanAccountBasic.LoanAccountBasicService;
import com.unisoft.collection.distribution.loan.loanAccountOther.LoanAccountOtherInfo;
import com.unisoft.collection.distribution.loan.loanAccountOther.LoanAccountOtherService;
import com.unisoft.common.CommonService;
import com.unisoft.customerbasicinfo.CustomerBasicInfoEntity;
import com.unisoft.customerbasicinfo.CustomerBasicInfoService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class SamWrittenOffExecutionService implements CommonService<SamWrittenOffExecution> {

    @Autowired
    private CustomerBasicInfoService customerBasicInfoService;

    @Autowired
    private LoanAccountBasicService loanAccountBasicService;

    @Autowired
    private LoanAccountService loanAccountService;

    @Autowired
    private LoanAccountOtherService loanAccountOtherService;

    @Autowired
    private SamWrittenOffExecutionRepository samWrittenOffExecutionRepository;

    @Override
    public Map<String, Object> storeData(SamWrittenOffExecution data) {
        return null;
    }

    public Map<String, Object> storeData(MultipartFile file){
        Map<String, Object> response = new HashMap<>();
        List<String> errorMessages = this.validate(file);

        if (errorMessages.size() == 0){
            List<SamWrittenOffExecution> allWrittenOffExecutions = new ArrayList<>();
            List<String> accountNos = new ArrayList<>();

            try {
                XSSFWorkbook xssfWorkbook = new XSSFWorkbook(file.getInputStream());
                XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);

                /*Get First Row for Column Names*/
                Iterator<Row> rows = xssfSheet.iterator();
                Row row = rows.next();

                /*Get All Column Names*/
                Iterator<Cell> cells = row.cellIterator();
                List<String> cellNames = new ArrayList<>();
                while (cells.hasNext()) {
                    cellNames.add(Objects.toString(cells.next(), "").toUpperCase());
                }
                System.out.println(cellNames);

                while (rows.hasNext()) {
                    row = rows.next();

                    SamWrittenOffExecution writtenOffExecution = new SamWrittenOffExecution();

                    Cell cellBranchCode = row.getCell(cellNames.indexOf("BRANCH CODE"));
                    String branchCode = Objects.toString(cellBranchCode, "");

                    Cell cellBranchName = row.getCell(cellNames.indexOf("BRANCH NAME"));
                    String branchName = Objects.toString(cellBranchName, "");

                    Cell cellBusinessRegion = row.getCell(cellNames.indexOf("BUSINESS REGION"));
                    String businessRegion = Objects.toString(cellBusinessRegion, "");

                    Cell cellBusinessSegment = row.getCell(cellNames.indexOf("BUSINESS SEGMENT"));
                    String businessSegment = Objects.toString(cellBusinessSegment, "");

//                    Cell cellLoanLiabilityNo = row.getCell(cellNames.indexOf("LOAN LIABILITY (LLN) NO"));
//                    String loanLiabilityNo = Objects.toString(cellLoanLiabilityNo, "");
//
//                    Cell cellCustomerCIFNo = row.getCell(cellNames.indexOf("CUSTOMER ID (CIF) NO"));
//                    String customerCIFNo = Objects.toString(cellCustomerCIFNo, "");

                    Cell cellLoanAccountNo = row.getCell(cellNames.indexOf("LOAN ACCOUNT NO"));
                    String loanAccountNo = Objects.toString(cellLoanAccountNo, "");
                    accountNos.add(loanAccountNo);

                    Cell cellLoanAccountName = row.getCell(cellNames.indexOf("LOAN ACCOUNT NAME"));
                    String loanAccountName = Objects.toString(cellLoanAccountName, "");

//                    Cell cellBorrowerName = row.getCell(cellNames.indexOf("BORROWER NAME"));
//                    String borrowerName = Objects.toString(cellBorrowerName, "");
//
//                    Cell cellFacilityNature = row.getCell(cellNames.indexOf("FACILITY NATURE"));
//                    String facilityNature = Objects.toString(cellFacilityNature, "");

                    Cell cellWrittenOffDate = row.getCell(cellNames.indexOf("WRITTEN-OFF DATE"));
                    String writtenOffDate = Objects.toString(cellWrittenOffDate, "");

                    Cell cellWrittenOffInttSuspenseAmount = row.getCell(cellNames.indexOf("WRITTEN-OFF INTT. SUSPENSE  AMOUNT"));
                    String writtenOffInttSuspenseAmount = Objects.toString(cellWrittenOffInttSuspenseAmount, "");

                    Cell cellWrittenOffProvisionAmount = row.getCell(cellNames.indexOf("WRITTEN-OFF PROVISION AMOUNT"));
                    String writtenOffProvisionAmount = Objects.toString(cellWrittenOffProvisionAmount, "");

                    Cell cellClStatus = row.getCell(cellNames.indexOf("CL STATUS"));
                    String clStatus = Objects.toString(cellClStatus, "");

                    Cell cellLegalStatus = row.getCell(cellNames.indexOf("LEGAL STATUS"));
                    String legalStatus = Objects.toString(cellLegalStatus, "");

//                    Cell cellCaseFillingDate = row.getCell(cellNames.indexOf("CASE FILLING DATE"));
//                    String caseFillingDate = Objects.toString(cellCaseFillingDate, "");
//
//                    Cell cellTypeOfCase = row.getCell(cellNames.indexOf("TYPE OF CASE"));
//                    String typeOfCase = Objects.toString(cellTypeOfCase, "");
//
//                    Cell cellObtainedBBGuideline = row.getCell(cellNames.indexOf("OBTAINED BB GUIDELINE/ REQUIREMENTS"));
//                    String obtainedBBGuideline = Objects.toString(cellObtainedBBGuideline, "");
//
//                    Cell cellRemarks = row.getCell(cellNames.indexOf("REMARKS"));
//                    String remarks = Objects.toString(cellRemarks, "");

                    CustomerBasicInfoEntity customerInfo = new CustomerBasicInfoEntity(loanAccountNo);
                    customerInfo = customerBasicInfoService.findOrSave(customerInfo);
                    if (customerInfo == null || customerInfo.getId() == null) continue;

                    Date expiryDate = new Date();
                    LoanAccountBasicInfo loanAccountBasicInfo = new LoanAccountBasicInfo("-", expiryDate, customerInfo);

                    loanAccountBasicInfo.setAccountName(loanAccountName);
                    loanAccountBasicInfo.setDisbursementDate(null);
                    loanAccountBasicInfo = loanAccountBasicService.findOrSave(loanAccountBasicInfo);

                    LoanAccountInfo loanAccountInfo = new LoanAccountInfo();
                    loanAccountInfo.setAte(0d);
                    loanAccountInfo.setAssetClassification("-");
                    loanAccountInfo.setInstallmentAmount(0d);
                    loanAccountInfo.setSchemeCode("-");
                    loanAccountInfo.setTotalOverdue(0d);
                    loanAccountInfo.setTotalOutstanding(0.00);
                    loanAccountInfo.setDpd(0.00);
                    loanAccountInfo.setEmiDueDate("-");
                    loanAccountInfo.setDpbBucket(null);
                    loanAccountInfo.setBranchName("-");
                    loanAccountInfo.setLoanAccountBasicInfo(loanAccountBasicInfo);
                    loanAccountService.saveOrUpdate(loanAccountInfo);

                    LoanAccountOtherInfo loanAccountOtherInfo = new LoanAccountOtherInfo();
                    loanAccountOtherInfo.setNotificationStatus("-");
                    loanAccountOtherInfo.setLoanAccountBasicInfo(loanAccountBasicInfo);
                    loanAccountOtherService.saveOrUpdate(loanAccountOtherInfo);

                    writtenOffExecution.setBranchCode(branchCode);
                    writtenOffExecution.setBranchName(branchName);

                    writtenOffExecution.setBusinessRegion(businessRegion);
                    writtenOffExecution.setBusinessSegment(businessSegment);

                    writtenOffExecution.setLoanAccountNo(loanAccountNo);
                    writtenOffExecution.setLoanAccountName(loanAccountName);
                    writtenOffExecution.setLoanAccountBasicInfo(loanAccountBasicInfo);

                    writtenOffExecution.setWrittenOffDate(writtenOffDate);

                    writtenOffExecution.setWrittenOffInttSuspenseAmount(Double.valueOf(writtenOffInttSuspenseAmount));
                    writtenOffExecution.setWrittenOffProvisionAmount(Double.valueOf(writtenOffProvisionAmount));
                    Double writtenOffAmount = writtenOffExecution.getWrittenOffInttSuspenseAmount() + writtenOffExecution.getWrittenOffProvisionAmount();
                    writtenOffExecution.setWrittenOffAmount(Double.valueOf(writtenOffAmount));

                    writtenOffExecution.setClStatus(clStatus);
                    writtenOffExecution.setLegalStatus(legalStatus);


                    writtenOffExecution.setLatest(true);

                    allWrittenOffExecutions.add(writtenOffExecution);
                }

                List<SamWrittenOffExecution> prevData = new ArrayList<>();
                for (String accountNo : accountNos){
                    SamWrittenOffExecution samWrittenOffExecution = samWrittenOffExecutionRepository.findSamWrittenOffExecutionByLoanAccountNoAndLatest(accountNo);
                    if (samWrittenOffExecution != null){
                        samWrittenOffExecution.setLatest(false);
                        prevData.add(samWrittenOffExecution);
                    }
                }

                samWrittenOffExecutionRepository.saveAll(prevData);
                samWrittenOffExecutionRepository.saveAll(allWrittenOffExecutions);
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

    @Override
    public Map<String, Object> updateData(SamWrittenOffExecution data) {
        return null;
    }

    @Override
    public Map<String, Object> deleteData(SamWrittenOffExecution data) {
        return null;
    }

    @Override
    public SamWrittenOffExecution findDataById(Long id) {
        return null;
    }

    @Override
    public List<SamWrittenOffExecution> findAllData() {
        List<SamWrittenOffExecution> allData = samWrittenOffExecutionRepository.findAllByLatest(true);
        return allData;
    }

    @Override
    public List<String> validate(SamWrittenOffExecution data) {
        return null;
    }

    public List<String> validate(MultipartFile file) {
        ArrayList<String> errors = new ArrayList<>();
        List<String> validCells = Arrays.asList(
                new String[]{"BRANCH CODE", "BRANCH NAME", "BUSINESS REGION", "BUSINESS SEGMENT",
                "LOAN ACCOUNT NO", "LOAN ACCOUNT NAME", "WRITTEN-OFF DATE",
                "WRITTEN-OFF INTT. SUSPENSE  AMOUNT", "WRITTEN-OFF PROVISION AMOUNT", "CL STATUS", "LEGAL STATUS"}
        );

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
}
