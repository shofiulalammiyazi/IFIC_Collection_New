package com.unisoft.retail.loan.dataEntry.distribution.auto;

import com.google.gson.Gson;
import com.unisoft.retail.loan.dataEntry.CustomerUpdate.accountInformation.AccountInformationEntity;
import com.unisoft.retail.loan.dataEntry.CustomerUpdate.accountInformationService.AccountInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/retail/loan/data-entry/distribution/auto/data/")
public class LoanAutoDistributionRestController {
    @Autowired
    AccountInformationService accountInformationService;
    @Autowired
    LoanAutoDistributionServiceImpl loanAutoDistributionService;

    @RequestMapping("get-pagination")
    ResponseEntity accountInformationEntitiesByPagination(@RequestParam("page") int page,
                                                                                    @RequestParam(required = false) String search,
                                                                                    @RequestParam("length") int length, HttpServletRequest request){
        return accountInformationService.findAllAndPagination(page, length, search);
    }

    @RequestMapping("get-all-overdue-not-zero")
    ResponseEntity findAllByOverdueGreaterThanZero(@RequestParam("page") int page, @RequestParam(required = false) String search,
                                                          @RequestParam("length") int length){
        return accountInformationService.findAllByOverdueGreaterThanZero(page, length, search);
    }

    @RequestMapping("get-page-by-issmssent")
    ResponseEntity accountInformationEntitiesByPaginationByIsSmsSent(@RequestParam("page") int page,
                                                          @RequestParam(required = false) String search,
                                                          @RequestParam("length") int length, HttpServletRequest request){
        return accountInformationService.findAllAndPaginationByIsSmsSent(page, length, search);
    }

    @RequestMapping("get-all-data")
    List<AccountInformationEntity> getAllAccountInformation(){
        return accountInformationService.findAll();
    }

    @PostMapping(value = "export-data-to-excel")
    Map<String,Object> saveDistributionExcel(@RequestBody List<AccountInformationEntity> accountInformationEntity) throws IOException {
        Gson gson = new Gson();
        return null;
//        return loanAutoDistributionService.saveDistributionExcel(dataToBeInExcel);
    }
}
