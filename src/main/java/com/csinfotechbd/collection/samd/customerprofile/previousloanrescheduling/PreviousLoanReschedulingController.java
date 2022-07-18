package com.csinfotechbd.collection.samd.customerprofile.previousloanrescheduling;

import com.csinfotechbd.user.UserPrincipal;
import com.csinfotechbd.utillity.JsonHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/samd/customer-profile/loan-rescheduling")
public class PreviousLoanReschedulingController {

    @Autowired
    private PreviousLoanReschedulingService service;
    @Autowired
    private JsonHandler jsonHandler;
    @PostMapping(value="/save", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Map<String, Object>> save(@RequestBody PreviousLoanRescheduling loanrescheduling ){
        Map<String,Object> resultMap=new HashMap<String,Object>();
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        loanrescheduling.setCreatedBy(Long.toString(user.getId()));
        loanrescheduling.setCreatedDate(new Date());
        loanrescheduling.setDealerPin(user.getEmpId());
        HttpHeaders header = jsonHandler.setHeader(Collections.singletonMap("user-id", String.valueOf(user.getId())));
        HttpEntity<String> req = new HttpEntity<>(jsonHandler.convertToJSON(loanrescheduling),header);
        resultMap.put("loanRescheduling", service.saveDetailsAccount(loanrescheduling));
        resultMap.put("successMsg", "Details of the Account info Successfully Saved.");
        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);}



    @GetMapping("view")
    public ResponseEntity<Map<String, Object>> view(@RequestParam(value = "accountNo", required = true) String accountNo) throws IOException {
        List<PreviousLoanRescheduling> loanRescheduling=service.viewByAccountNo(accountNo);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("loanRescheduling", loanRescheduling);
        resultMap.put("successMsg", "Account  Info viewed!");
        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }
}
