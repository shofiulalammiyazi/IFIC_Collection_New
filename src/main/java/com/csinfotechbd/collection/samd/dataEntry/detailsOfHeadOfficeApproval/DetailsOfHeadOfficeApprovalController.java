package com.csinfotechbd.collection.samd.dataEntry.detailsOfHeadOfficeApproval;

import com.csinfotechbd.user.UserPrincipal;
import com.csinfotechbd.utillity.JsonHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/samd/details-of-head-office-approval")
public class DetailsOfHeadOfficeApprovalController {

    @Autowired
    private DetailsOfHeadOfficeApprovalService service;
    @Autowired
    private JsonHandler jsonHandler;
    @PostMapping(value="/save", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Map<String, Object>> save(@RequestBody DetailsOfHeadOfficeApproval detailsOfHeadOfficeApproval){
        Map<String,Object> resultMap=new HashMap<String,Object>();
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        detailsOfHeadOfficeApproval.setCreatedBy(Long.toString(user.getId()));
        detailsOfHeadOfficeApproval.setCreatedDate(new Date());
        detailsOfHeadOfficeApproval.setDealerPin(user.getEmpId());
        HttpHeaders header = jsonHandler.setHeader(Collections.singletonMap("user-id", String.valueOf(user.getId())));
        HttpEntity<String> req = new HttpEntity<>(jsonHandler.convertToJSON(detailsOfHeadOfficeApproval),header);
        resultMap.put("detailsOfHeadOfficeApproval", service.saveDetailsAccount(detailsOfHeadOfficeApproval));
        resultMap.put("successMsg", "Details of the Account info Successfully Saved.");
        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);}



    @GetMapping("view")
    public ResponseEntity<Map<String, Object>> view(@RequestParam(value = "accountNo", required = true) String accountNo) throws IOException {
        List<DetailsOfHeadOfficeApproval> detailsOfHeadOfficeApproval =service.viewByAccountNo(accountNo);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("detailsOfHeadOfficeApproval", detailsOfHeadOfficeApproval);
        resultMap.put("successMsg", "Account  Info viewed!");
        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }
}
