package com.unisoft.collection.samd.dataEntry.latestApprovalRelatedToLoanRescheduling;

import com.unisoft.user.UserPrincipal;
import com.unisoft.utillity.JsonHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/samd/latest-approval-related-to-loan-rescheduling")
public class LatestApprovalRelatedToLoanReschedulingController {

    @Autowired
    private LatestApprovalRelatedToLoanReschedulingService service;
    @Autowired
    private JsonHandler jsonHandler;

    @PostMapping(value="/save", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Map<String, Object>> save(@RequestBody LatestApprovalRelatedToLoanRescheduling latestApprovalRelatedToLoanRescheduling){
        Map<String,Object> resultMap=new HashMap<String,Object>();
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        latestApprovalRelatedToLoanRescheduling.setCreatedBy(Long.toString(user.getId()));
        latestApprovalRelatedToLoanRescheduling.setCreatedDate(new Date());
        latestApprovalRelatedToLoanRescheduling.setDealerPin(user.getEmpId());
        HttpHeaders header = jsonHandler.setHeader(Collections.singletonMap("user-id", String.valueOf(user.getId())));
        HttpEntity<String> req = new HttpEntity<>(jsonHandler.convertToJSON(latestApprovalRelatedToLoanRescheduling),header);
        resultMap.put("latestApprovalRelatedToLoanRescheduling", service.saveDetailsAccount(latestApprovalRelatedToLoanRescheduling));
        resultMap.put("successMsg", "Successfully Saved.");
        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);}



    @GetMapping("view")
    public ResponseEntity<Map<String, Object>> view(@RequestParam(value = "accountNo", required = true) String accountNo) throws IOException {
        List<LatestApprovalRelatedToLoanRescheduling> latestApprovalRelatedToLoanRescheduling =service.viewByAccountNo(accountNo);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("latestApprovalRelatedToLoanRescheduling", latestApprovalRelatedToLoanRescheduling);
        resultMap.put("successMsg", "Account  Info viewed!");
        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }

}
