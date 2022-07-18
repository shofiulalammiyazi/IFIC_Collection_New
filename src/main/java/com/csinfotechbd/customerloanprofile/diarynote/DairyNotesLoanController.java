package com.csinfotechbd.customerloanprofile.diarynote;

import com.csinfotechbd.customerbasicinfo.CustomerBasicInfoEntity;
import com.csinfotechbd.customerbasicinfo.CustomerBasicInfoEntityRepository;
import com.csinfotechbd.user.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/collection/loan/dairy-notes")
public class DairyNotesLoanController {
    @Autowired
    DairyNotesLoanService service;
    @Autowired
    private DairyNotesLoanRepository repository;
    @Autowired
    private CustomerBasicInfoEntityRepository customerBasicInfoEntityRepository;

    @PostMapping(value="/save")
    public List<DairyNotesLoan> saveReferenceInfo(DairyNotesLoan dairyNotesLoan) throws IOException {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        dairyNotesLoan.setCreatedBy(user.getUsername());
        dairyNotesLoan.setCreatedDate(new Date());
        dairyNotesLoan.setEnabled(true);
        dairyNotesLoan.setPin(user.getUsername());
        dairyNotesLoan.setUsername(user.getLastName());
        DairyNotesLoan notes = service.save(dairyNotesLoan);
        List<DairyNotesLoan> dairyNotesLoanList =repository.findByCustomerBasicInfo(notes.getCustomerBasicInfo());
        return dairyNotesLoanList;
    }




    @GetMapping(value="/list")
    public List<DairyNotesLoan> list(@RequestParam(value = "id") String id) throws IOException{
        CustomerBasicInfoEntity customerBasicInfoEntity = customerBasicInfoEntityRepository.findById(new Long(id)).get();
        List<DairyNotesLoan> dairyNotesLoanList =repository.findByCustomerBasicInfo(customerBasicInfoEntity);
        return dairyNotesLoanList;

    }
    @GetMapping(value="/listById")
    public ResponseEntity<Map<String, Object>> view(@RequestParam(value = "loanId", required = true) String loanId ) throws IOException{
        UserPrincipal user = (UserPrincipal)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Map<String,Object> resultMap=new HashMap<String,Object>();
        List<DairyNotesLoan> followUpList =service.list();
        resultMap.put("followUpList", followUpList);
        resultMap.put("successMsg", "Dairy Notes Info Successfully Received!");

        return new ResponseEntity<Map<String, Object>>(resultMap,HttpStatus.OK);

    }

    @GetMapping("/remove")
    public ResponseEntity<Map<String, Object>> remove(@RequestParam(value = "id", required = true) String id){
        Map<String, Object> resultMap=new HashMap<>();
        DairyNotesLoan dairyNotesLoan =service.findbyId(Long.parseLong(id));
        dairyNotesLoan.setEnabled(false);
        service.save(dairyNotesLoan);
        resultMap.put("successMsg","Successful deleted");
        resultMap.put("dairyNotes", dairyNotesLoan);
        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }

}
