package com.unisoft.retail.loan.dataEntry.distribution.auto;

import com.unisoft.collection.distribution.loan.loanAccount.LoanAccountInfo;
import com.unisoft.collection.distribution.loan.loanAccount.LoanAccountService;
import com.unisoft.collection.distribution.loan.loanAccountBasic.LoanAccountBasicInfo;
import com.unisoft.collection.distribution.loan.loanAccountBasic.LoanAccountBasicService;
import com.unisoft.collection.distribution.loan.loanAccountDistribution.LoanAccountDistributionService;
import com.unisoft.collection.distribution.loan.loanAccountOther.LoanAccountOtherInfo;
import com.unisoft.collection.distribution.loan.loanAccountOther.LoanAccountOtherService;
import com.unisoft.collection.settings.dealerGroupBasedOnCollectorAreaForAutoDistribution.DealerGroupBasedOnCollectorAreaForAutoDistribution;
import com.unisoft.collection.settings.dealerGroupBasedOnCollectorAreaForAutoDistribution.DealerGroupBasedOnCollectorAreaForAutoDistributionService;
import com.unisoft.collection.settings.dpdBucket.DPDBucketEntity;
import com.unisoft.collection.settings.employee.EmployeeInfoDto;
import com.unisoft.collection.settings.employee.EmployeeService;
import com.unisoft.collection.settings.location.LocationEntity;
import com.unisoft.collection.settings.productType.ProductTypeEntity;
import com.unisoft.common.CommonServiceImpl;
import com.unisoft.customerbasicinfo.CustomerBasicInfoEntity;
import com.unisoft.customerbasicinfo.CustomerBasicInfoService;
import com.unisoft.loanApi.model.CustomerInfo;
import com.unisoft.loanApi.model.LoanAccDetails;
import com.unisoft.loanApi.service.RetailLoanUcbApiService;
import com.unisoft.retail.loan.dataEntry.CustomerUpdate.accountInformation.AccountInformationEntity;
import com.unisoft.utillity.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.IIOException;
import javax.persistence.Tuple;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Implemented by    on May 31, 2021
 */
@Slf4j
@Service
public class LoanAutoDistributionServiceImpl extends CommonServiceImpl<LoanAutoDistributionInfo> implements LoanAutoDistributionService {

    private final LoanAccountDistributionService loanAccountDistributionService;
    private final LoanAutoDistributionRepository distributionRepository;
    private final DealerGroupBasedOnCollectorAreaForAutoDistributionService dealerGroupService;
    private final EmployeeService employeeService;
    private final CustomerBasicInfoService customerBasicInfoService;
    private final LoanAccountBasicService loanAccountBasicService;
    private final LoanAccountService loanAccountService;
    private final LoanAccountOtherService loanAccountOtherService;
    private final RetailLoanUcbApiService apiService;

    @Autowired
    public LoanAutoDistributionServiceImpl(LoanAutoDistributionRepository repository,
                                           DealerGroupBasedOnCollectorAreaForAutoDistributionService dealerGroupService,
                                           EmployeeService employeeService,
                                           RetailLoanUcbApiService apiService,
                                           CustomerBasicInfoService customerBasicInfoService,
                                           LoanAccountBasicService loanAccountBasicService,
                                           LoanAccountService loanAccountService,
                                           LoanAccountOtherService loanAccountOtherService,
                                           LoanAccountDistributionService loanAccountDistributionService) {
        this.repository = distributionRepository = repository;
        this.loanAccountDistributionService = loanAccountDistributionService;
        this.dealerGroupService = dealerGroupService;
        this.employeeService = employeeService;
        this.apiService = apiService;
        this.customerBasicInfoService = customerBasicInfoService;
        this.loanAccountBasicService = loanAccountBasicService;
        this.loanAccountService = loanAccountService;
        this.loanAccountOtherService = loanAccountOtherService;
    }

    @Override
    public Map<String, Object> distributeAccounts(List<LoanAutoDistributionInfo> loanList) {
        return null;
    }

    @Override
    public List<LoanAutoDistributionInfo> getCurrentMonthDelinquentAccountsFromClientApi() {
        // Clear previous data
        repository.deleteAll();
        // Get current month delinquent accounts from client API
        List<LoanAccDetails> allLoanAccounts = apiService.getLoanAccountDetails();

        Set<String> distributedAccountNumbers = loanAccountDistributionService.getCurrentMonthDistributedAccountNumbers();

        List<LoanAutoDistributionInfo> delinquentAccounts = allLoanAccounts.parallelStream()
                .filter(accountDetails -> accountDetails.getOverdue() > 0 && !distributedAccountNumbers.contains(accountDetails.getAccountNumber()))
                .map(this::getAccountInfoFromAccountDetails)
                .collect(Collectors.toList());

        // Save primary information for further processing
        repository.saveAll(delinquentAccounts);

        return delinquentAccounts;
    }

    @Override
    public List<LoanAutoDistributionDto> getCurrentMonthUnallocatedList() {
        return distributionRepository.findUnallocatedAccounts().stream().map(LoanAutoDistributionDto::new).collect(Collectors.toList());
    }

    @Override
    public List<LoanAutoDistributionDto> getCurrentMonthAllocatedList() {
        return distributionRepository.findAutomaticallyDistributedAccounts().stream().map(LoanAutoDistributionDto::new).collect(Collectors.toList());
    }

    @Override
    public List<LoanAutoDistributionViewModel> getCurrentMonthAutoDistributionSummary() {
        List<Tuple> rawSummary = distributionRepository.findAutoDistributionSummary();
        return rawSummary.parallelStream().map(LoanAutoDistributionViewModel::new).collect(Collectors.toList());
    }

//    @Override
//    public void saveAll(List<LoanAutoDistributionInfo> delinquentAccounts) {
//        String username = UserService.getSessionUsername();
//        delinquentAccounts.forEach(delinquentAccount -> {
//            delinquentAccount.setCreatedBy(username);
//            delinquentAccount.setCreatedDate(new Date());
//        });
//        repository.saveAll(delinquentAccounts);
//    }

    private LoanAutoDistributionInfo getAccountInfoFromAccountDetails(LoanAccDetails loanAccDetails) {
        try {
            String accountNumber = loanAccDetails.getAccountNumber();
            CustomerBasicInfoEntity customer = customerBasicInfoService.findOrSave(accountNumber);
            LoanAccountBasicInfo accountBasicInfo = loanAccountBasicService.findOrSave(new LoanAccountBasicInfo(customer));
            loanAccountService.findOrSave(new LoanAccountInfo(accountBasicInfo));
            loanAccountOtherService.findOrSave(new LoanAccountOtherInfo(accountBasicInfo));

            LoanAutoDistributionInfo delinquentAccount = null;
            if (loanAccountBasicService.isExistingAccount(accountBasicInfo)) {
                CustomerInfo customerInfo = apiService.getCustomerInfo(accountNumber);
                delinquentAccount = new LoanAutoDistributionInfo();
                delinquentAccount.setAccountNo(accountNumber);
                delinquentAccount.setDpdBucket(String.format("%.2f", loanAccDetails.getDpdBucket()));
                delinquentAccount.setLocation(customerInfo.getLocation());
                delinquentAccount.setProductCode(loanAccDetails.getProductCode());
                delinquentAccount.setOutstanding(loanAccDetails.getOutStandingLocalCurrency());
            }
            return delinquentAccount;
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    @Override
    public void temporarilyDistributeDelinquentAccounts() {
        // Get auto distribution rules
        List<DealerGroupBasedOnCollectorAreaForAutoDistribution> distributionRules = dealerGroupService.findByEnabled(true);
        List<LoanAutoDistributionInfo> delinquentAccounts = new ArrayList<>();
        List<EmployeeInfoDto> employeeList;
        List<String> exceptionUnits = Arrays.asList("SAM", "Write Off");

        if (distributionRules.isEmpty()) {
            // When no dealer group based on location found
            delinquentAccounts = distributionRepository.findByEnabled(true);
            employeeList = employeeService.findEmployeePinsByUnitAndDesignation(Arrays.asList("Dealer"), "Loan", exceptionUnits);

            distributeAccountsToDealers(delinquentAccounts, employeeList);
        } else {
            for (DealerGroupBasedOnCollectorAreaForAutoDistribution distributionRule : distributionRules) {
                // When dealer group rules are set
                List<String> dpdBuckets = distributionRule.getDpdBucketEntities().stream().map(DPDBucketEntity::getBucketName).collect(Collectors.toList());
                List<String> locations = distributionRule.getLocation().stream().map(LocationEntity::getCity).collect(Collectors.toList());
                List<String> productCodes = distributionRule.getProductType().stream().map(ProductTypeEntity::getCode).collect(Collectors.toList());

                delinquentAccounts = distributionRepository.findUnallocatedAccountsByLocationAndProductCodeAndDpdBucket(locations, productCodes, dpdBuckets);
                employeeList = distributionRule.getDealer().stream().map(EmployeeInfoDto::new).collect(Collectors.toList());

                distributeAccountsToDealers(delinquentAccounts, employeeList);
            }
        }
        // Save temporary distribution data for approval
        repository.saveAll(delinquentAccounts);

    }

    private List<String> distributeAccountsToDealers(List<LoanAutoDistributionInfo> delinquentAccounts, List<EmployeeInfoDto> employeeList) {
        List<String> errors = new LinkedList<>();
        int totalDelinquentAccounts = delinquentAccounts.size();
        int totalEmployees = employeeList.size();
        int currentEmployeeIndex;
        for (int i = 0; i < totalDelinquentAccounts; i++) {
            try {
                currentEmployeeIndex = getArrayFlipRotationIndex(i, totalEmployees);
                String dealerPin = employeeList.get(currentEmployeeIndex).getPin();
                String dealerName = StringUtils.concat(employeeList.get(currentEmployeeIndex).getFirstName(), " ", employeeList.get(currentEmployeeIndex).getLastName());
                delinquentAccounts.get(i).setDealerPin(dealerPin);
                delinquentAccounts.get(i).setDealerName(dealerName);
            } catch (Exception e) {
                errors.add("Could not distribute account " + delinquentAccounts.get(i).getAccountNo() + " to employee " + employeeList.get(i).getPin());
            }
        }
        return errors;
    }

    /*
    Find out the rotation turn (i / totalEmployees) % 2 == 0).
    Flip employee selection from the list in the following way -
    In case of even rotation turn, select employee from the beginning of the employee list (i % totalEmployees).
    For odd turn, select from the end (totalEmployees - i % totalEmployees - 1).
   * */
    private int getArrayFlipRotationIndex(int index, int totalElements) {
        return (index / totalElements) % 2 == 0 ? index % totalElements : totalElements - index % totalElements - 1;
    }

    public Map<String, Object> saveDistributionExcel(List<AccountInformationEntity> dataToBeInExcel) throws IOException {
        Map<String, Object> y = new HashMap<String, Object>();
        //create blank workbook
        XSSFWorkbook workbook = new XSSFWorkbook();
        //Create a blank sheet
        XSSFSheet sheet = workbook.createSheet("Loan Distribution");
        //Iterate over data and write to sheet
        int rownum = 0;
        for (AccountInformationEntity accountInformationEntity : dataToBeInExcel)
        {
            Row row = sheet.createRow(rownum++);
            int cellnum = 0;
            Cell cell = row.createCell(cellnum++);
            cell.setCellValue((String)accountInformationEntity.getLoanACNo());
            cell.setCellValue((String)accountInformationEntity.getBranchName());
            cell.setCellValue((String)accountInformationEntity.getProductCode());
            cell.setCellValue((String)accountInformationEntity.getDealReference());
            cell.setCellValue((String)accountInformationEntity.getTotalOutstanding());
        }
        try
        {
            //Write the workbook in file system
            FileOutputStream out = new FileOutputStream(new File("CountriesDetails.xlsx"));
            workbook.write(out);
            out.close();
            System.out.println("CountriesDetails.xlsx has been created successfully");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
            workbook.close();
        }
        return y;
    }

//
//    @Override
//    public void deleteAll() {
//        repository.deleteAll();
//    }
}
