package com.unisoft.collection.samd.customerprofile.detailsaccount;

import com.unisoft.user.UserPrincipal;
import com.unisoft.utillity.JsonHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/samd/customer-profile/details-account")
public class DetailsOfTheAccountController {

    @Autowired
    private DetailsOfTheAccountService service;
    @Autowired
    private JsonHandler jsonHandler;
    @PostMapping(value="/save", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Map<String, Object>> saveDetails(@RequestBody DetailsOfTheAccount detailsAccount ){
        Map<String,Object> resultMap=new HashMap<String,Object>();
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        detailsAccount.setCreatedBy(Long.toString(user.getId()));
        detailsAccount.setCreatedDate(new Date());
        detailsAccount.setDealerPin(user.getEmpId());
        HttpHeaders header = jsonHandler.setHeader(Collections.singletonMap("user-id", String.valueOf(user.getId())));
        HttpEntity<String> req = new HttpEntity<>(jsonHandler.convertToJSON(detailsAccount),header);
        resultMap.put("detailsOfAccount", service.saveDetailsAccount(detailsAccount));
        resultMap.put("successMsg", "Details of the Account info Successfully Saved.");
        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);}



    @GetMapping("view")
    public ResponseEntity<Map<String, Object>> view(@RequestParam(value = "accountNo", required = true) String accountNo) throws IOException {
        DetailsOfTheAccount detailsAccount=service.viewByAccountNo(accountNo);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("detailsOfAccount", detailsAccount);
        resultMap.put("successMsg", "Account  Info viewed!");
        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }
}
