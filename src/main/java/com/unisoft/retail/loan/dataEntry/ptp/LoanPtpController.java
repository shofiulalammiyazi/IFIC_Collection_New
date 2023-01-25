package com.unisoft.retail.loan.dataEntry.ptp;

import com.unisoft.customerbasicinfo.CustomerBasicInfoEntity;
import com.unisoft.customerbasicinfo.CustomerBasicInfoEntityRepository;
import com.unisoft.user.UserPrincipal;
import com.unisoft.utillity.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/collection/loan/ptp")
public class LoanPtpController {
    @Autowired
    LoanPtpService service;
    @Autowired
    private CustomerBasicInfoEntityRepository customerBasicInfoEntityRepository;

    private DateUtils dateUtils;

    @PostMapping(value="/save")
    public boolean saveReferenceInfo(LoanPtp loanPtp) throws IOException, ParseException {

        //String dateString = loanPtp.getLoan_ptp_date();
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        loanPtp.setCreatedBy(user.getUsername());
        loanPtp.setCreatedDate(new Date());
        loanPtp.setEnabled(true);
        loanPtp.setPin(user.getUsername());
        loanPtp.setUsername(user.getLastName());


        //Date LoanPtpDate = dateUtils.getFormattedDate(dateString, "dd-MM-yyyy");
        //loanPtp.setLoan_ptp_date(LoanPtpDate);
        //loanPtp.setLoan_ptp_date(new Date());
        //loanPtp.setLoan_ptp_time(DateTimeFormatter.ofPattern("HH:mm:ss").format(LocalDateTime.now()));

       // Date ptpDate = null;
        //String stringToDate = "";
        //loanPtp.setLoan_ptp_status("kept");
        //stringToDate = loanPtp.getLoan_ptp_dates().replace('/', '-');
        //ptpDate = new SimpleDateFormat("dd-MM-yyyy").parse(stringToDate);

        //loanPtp.setLoan_ptp_date(ptpDate);
        service.save(loanPtp);
        return true;
    }

    @GetMapping(value="/list")
    public List<LoanPtp> list(@RequestParam(value = "id") String id) throws IOException{
        CustomerBasicInfoEntity customerBasicInfoEntity = customerBasicInfoEntityRepository.findById(new Long(id)).get();
        List<LoanPtp> ptpList =service.list(true, customerBasicInfoEntity);
        return ptpList;

    }
    @GetMapping(value="/listById")
    public ResponseEntity<Map<String, Object>> view(@RequestParam(value = "loanPtpId", required = true) String loanPtpId ) throws IOException{
        UserPrincipal user = (UserPrincipal)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Map<String,Object> resultMap=new HashMap<String,Object>();
        LoanPtp loanPtplist =service.findbyId(Long.parseLong(loanPtpId));
        resultMap.put("loanptpList", loanPtplist);
        return new ResponseEntity<Map<String, Object>>(resultMap,HttpStatus.OK);

    }

    @GetMapping("/remove")
    public ResponseEntity<Map<String, Object>> remove(@RequestParam(value = "id", required = true) String id){
        Map<String, Object> resultMap=new HashMap<>();
        LoanPtp dairyNotes =service.findbyId(Long.parseLong(id));
        dairyNotes.setEnabled(false);
        service.save(dairyNotes);
        resultMap.put("successMsg","Successful deleted");
        resultMap.put("dairyNotes", dairyNotes);
        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }


    @GetMapping("/ptp-list")
    @ResponseBody
    public List<LoanPtp> getLoanPtpByCustomerIdAndDateRange(@RequestParam(name = "customerId") String customerId,@RequestParam(name = "startDate") String startDate,
                                                            @RequestParam(name = "endDate") String endDate){
        List<LoanPtp> loanPtpList = service.getLoanPtpByCustomerIdAndDateRange(customerId, startDate, endDate);
        return loanPtpList;
    }

}
