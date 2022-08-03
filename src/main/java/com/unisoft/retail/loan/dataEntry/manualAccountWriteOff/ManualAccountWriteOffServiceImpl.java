package com.unisoft.retail.loan.dataEntry.manualAccountWriteOff;

import com.unisoft.collection.settings.employee.EmployeeService;
import com.unisoft.customerbasicinfo.CustomerBasicInfoService;
import com.unisoft.loanApi.model.CustomerInfo;
import com.unisoft.loanApi.model.LoanAccDetails;
import com.unisoft.loanApi.model.LoanAccInfo;
import com.unisoft.loanApi.service.RetailLoanUcbApiService;
import com.unisoft.utillity.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@EnableScheduling
public class ManualAccountWriteOffServiceImpl implements ManualAccountWriteOffService{

    @Autowired
    private RetailLoanUcbApiService apiService;
    @Autowired
    private CustomerBasicInfoService customerBasicInfoService;
    @Autowired
    private ManualAccountWriteOffRepository manualAccountWriteOffRepository;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private DateUtils dateUtils;

    @Override
    public List<ManualAccountWriteOff> getCurrentMonthManualAccountWriteOffFromApi() {

        //delete previous data
        manualAccountWriteOffRepository.deleteAll();

        // Get current month write off accounts from client API
        List<LoanAccDetails> allLoanAccoutDetails = apiService.getLoanAccountDetails();

        List<ManualAccountWriteOff> manualAccountWriteOffList = new ArrayList<>();

        for (LoanAccDetails loanAccDetails: allLoanAccoutDetails){

            try{
                LoanAccInfo loanAccInfo = apiService.getLoanAccountInfo(loanAccDetails.getAccountNumber());

                if (loanAccInfo.getIsWriteOff()=="Y"){
                    CustomerInfo customerInfo = apiService.getCustomerInfo(loanAccInfo.getAccountNumber());

                    ManualAccountWriteOff manualAccountWriteOff = new ManualAccountWriteOff();
                    manualAccountWriteOff.setAccountNumber(loanAccInfo.getAccountNumber());
                    manualAccountWriteOff.setAccountName(loanAccInfo.getAccountName());
                    manualAccountWriteOff.setAccountStatus(loanAccInfo.getAccountStatus());
                    manualAccountWriteOff.setLocation(customerInfo.getLocation());
                    manualAccountWriteOff.setProductCode(loanAccDetails.getProductCode());
                    manualAccountWriteOff.setBranchCode(loanAccDetails.getBranchCode());

                    manualAccountWriteOffList.add(manualAccountWriteOff);
                }
            }catch (NullPointerException e){
                e.printStackTrace();
            }

        }
        manualAccountWriteOffRepository.saveAll(manualAccountWriteOffList);
        return manualAccountWriteOffList;
    }

    @Override
    public List<ManualAccountWriteOff> findCurrentMonthWriteOffAccount() {
        return manualAccountWriteOffRepository.findAll();
    }
}
