package com.unisoft.retail.card.dataEntry.dailyNotes;

import com.unisoft.user.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/collection/card/daily-notes")
public class DailyNotesCardController {
    @Autowired
    DailyNotesRepository dailyNotesRepository;

    @PostMapping(value="/save")
    public ResponseEntity<Map<String, Object>> saveReferenceInfo(@RequestBody DailyNotesCard dairyNotes) throws IOException {
        Map<String,Object> resultMap=new HashMap<>();
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        dairyNotes.setCreatedBy(user.getUsername());
        dairyNotes.setCreatedDate(new Date());
        dairyNotes.setEnabled(true);
        dairyNotes.setName(user.getFirstName());
        DailyNotesCard notes = dailyNotesRepository.save(dairyNotes);
        resultMap.put("dairyNotes", notes);
        resultMap.put("successMsg", "Dairy Notes  Successfully Saved.");
        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
    }
    
    @GetMapping(value = "/list")
    public List<DailyNotesCard> cardListByCustomerId(@RequestParam(value = "cusId") long cusId){
        return dailyNotesRepository.findByCustomerBasicInfoIdOrderByCreatedDateDesc(cusId);
    }
}
