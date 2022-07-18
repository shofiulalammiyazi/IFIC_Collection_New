package com.csinfotechbd.retail.card.dataEntry.ptp;

import com.csinfotechbd.customerbasicinfo.CustomerBasicInfoEntity;
import com.csinfotechbd.customerbasicinfo.CustomerBasicInfoEntityRepository;
import com.csinfotechbd.retail.loan.dataEntry.ptp.LoanPtp;
import com.csinfotechbd.user.UserPrincipal;
import com.csinfotechbd.utillity.InputSanitizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/collection/card/ptp")
public class CardPtpController {

    @Autowired
    CardPtpService service;

    @Autowired
    private CustomerBasicInfoEntityRepository customerBasicInfoEntityRepository;

    private InputSanitizer inputSanitizer = new InputSanitizer();

    @PostMapping(value = "/save")
    public ResponseEntity<Map<String, Object>> saveReferenceInfo(CardPtp cardPtp) throws IOException, ParseException {
        Map<String, Object> resultMap = new HashMap<>();
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        cardPtp.setUsername(user.getFirstName().concat(" ").concat(user.getLastName()));
        cardPtp.setCreatedBy(user.getUsername());
        cardPtp.setCreatedDate(new Date());
        cardPtp.setEnabled(true);

        Map<String, String> monthNameToNumeric = new HashMap<>();
        monthNameToNumeric.put("Jan", "01");
        monthNameToNumeric.put("Feb", "02");
        monthNameToNumeric.put("Mar", "03");
        monthNameToNumeric.put("Apr", "04");
        monthNameToNumeric.put("May", "05");
        monthNameToNumeric.put("Jun", "06");
        monthNameToNumeric.put("Jul", "07");
        monthNameToNumeric.put("Aug", "08");
        monthNameToNumeric.put("Sep", "09");
        monthNameToNumeric.put("Oct", "10");
        monthNameToNumeric.put("Nov", "11");
        monthNameToNumeric.put("Dec", "12");

        Date ptpDate = null;
        String stringToDate = "";
        cardPtp.setCard_ptp_status("kept");
        stringToDate = cardPtp.getCard_ptp_dates().replace('/', '-');
        ptpDate = new SimpleDateFormat("MM-dd-yyyy").parse(stringToDate);
        cardPtp.setCard_ptp_date(ptpDate);

        cardPtp.setPin(user.getUsername());

        CardPtp ptp = service.save(cardPtp);
        resultMap.put("ptp", ptp);
        resultMap.put("successMsg", "PTP Successfully Saved.");
        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
    }


    @GetMapping(value = "/list")
    public ResponseEntity<Map<String, Object>> list(@RequestParam(value = "id") String id) throws IOException {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        CustomerBasicInfoEntity customerBasicInfoEntity = customerBasicInfoEntityRepository.findById(new Long(id)).get();
        List<CardPtp> ptpList = service.list(true, customerBasicInfoEntity);
        resultMap.put("ptp", ptpList);
        resultMap.put("successMsg", "Ptp Successfully Received!");
        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);

    }

    @GetMapping(value = "/listById")
    public ResponseEntity<Map<String, Object>> view(@RequestParam(value = "cardId", required = true) String cardId) throws IOException {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        CardPtp cardPtpList = service.findbyId(Long.parseLong(cardId));
        resultMap.put("cardptplist", cardPtpList);
        resultMap.put("successMsg", "PTP Info Successfully Received!");

        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);

    }

    @GetMapping("summary-by-id")
    public ResponseEntity<?> findPtpSummary(@RequestParam("customerId") String id)  {
        if (!inputSanitizer.isSafe(id)) {
            return ResponseEntity.badRequest().build();
        }

        CustomerBasicInfoEntity customerBasicInfoEntity = customerBasicInfoEntityRepository.findById(new Long(id)).get();
        List<CardPtp> ptpList = service.list(true, customerBasicInfoEntity);
        CardPtpSummary summary = new CardPtpSummary();

        ptpList.forEach(ptp -> {
            boolean cured = "cured".equalsIgnoreCase(ptp.getCard_ptp_status());
            boolean broken = "broken".equalsIgnoreCase(ptp.getCard_ptp_status());
            boolean kept = "kept".equalsIgnoreCase(ptp.getCard_ptp_status());

            summary.setBroken(summary.getBroken() + (broken ? 1 : 0));
            summary.setCured(summary.getCured() + (cured ? 1 : 0));
            summary.setKept(summary.getKept() + (kept ? 1 : 0));
            summary.setTotal(summary.getTotal() + 1);
        });

        return ResponseEntity.ok(summary);
    }

    @GetMapping("/remove")
    public ResponseEntity<Map<String, Object>> remove(@RequestParam(value = "id", required = true) String id) {
        Map<String, Object> resultMap = new HashMap<>();
        CardPtp dairyNotes = service.findbyId(Long.parseLong(id));
        dairyNotes.setEnabled(false);
        service.save(dairyNotes);
        resultMap.put("successMsg", "Successful deleted");
        resultMap.put("dairyNotes", dairyNotes);
        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }

   /* @GetMapping("/ptp-list")
    @ResponseBody
    public List<CardPtp> getCardPtpByCustomerIdAndDateRange(@RequestParam(name = "customerId") String customerId, @RequestParam(name = "startDate") String startDate,
                                                            @RequestParam(name = "endDate") String endDate){
        List<CardPtp> cardPtpList = service.getCardPtpByCustomerIdAndDateRange(customerId, startDate, endDate);
        return cardPtpList;
    }*/

    @GetMapping("/ptp-list")
    public ResponseEntity<?> getCardPtpByCustomerIdAndDateRange(@RequestParam(name = "customerId") String customerId, @RequestParam(name = "startDate") String startDate,
                                                            @RequestParam(name = "endDate") String endDate){
        List<CardPtp> cardPtpList = service.getCardPtpByCustomerIdAndDateRange(customerId, startDate, endDate);
        CardPtpSummary summary = new CardPtpSummary();

        cardPtpList.forEach(ptp -> {
            boolean cured = "cured".equalsIgnoreCase(ptp.getCard_ptp_status());
            boolean broken = "broken".equalsIgnoreCase(ptp.getCard_ptp_status());
            boolean kept = "kept".equalsIgnoreCase(ptp.getCard_ptp_status());

            summary.setBroken(summary.getBroken() + (broken ? 1 : 0));
            summary.setCured(summary.getCured() + (cured ? 1 : 0));
            summary.setKept(summary.getKept() + (kept ? 1 : 0));
            summary.setTotal(summary.getTotal() + 1);
        });

        return ResponseEntity.ok(summary);
    }

    @GetMapping(value="/list1")
    public List<CardPtp> list1(@RequestParam(value = "id") String id) throws IOException{
        CustomerBasicInfoEntity customerBasicInfoEntity = customerBasicInfoEntityRepository.findById(new Long(id)).get();
        List<CardPtp> ptpList =service.list(true, customerBasicInfoEntity);
        return ptpList;

    }

}
