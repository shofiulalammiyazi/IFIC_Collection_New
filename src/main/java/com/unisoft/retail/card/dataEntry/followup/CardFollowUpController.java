package com.unisoft.retail.card.dataEntry.followup;

import com.unisoft.user.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/collection/card/follow-up")
public class CardFollowUpController {
    @Autowired
    CardFolllowUpService service;

    @PostMapping(value = "/save")
    public ResponseEntity<Map<String, Object>> saveReferenceInfo(CardFollowUp cardFollowUp) throws IOException, ParseException {

        Map<String, Object> resultMap = new HashMap<>();
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        cardFollowUp.setUsername(user.getFirstName().concat(" ").concat(user.getLastName()));
        cardFollowUp.setCreatedBy(user.getUsername());
        cardFollowUp.setCreatedDate(new Date());
        cardFollowUp.setEnabled(true);
        cardFollowUp.setPin(user.getUsername());

       /* String stringToDate = cardFollowUp.getTemdates().replace('/', '-');
        Date followUpDate = new SimpleDateFormat("MM-dd-yyyy").parse(stringToDate);
        cardFollowUp.setFollowUpDate(followUpDate);*/

        CardFollowUp followUp = service.save(cardFollowUp);
        resultMap.put("followUp", followUp);
        resultMap.put("successMsg", "Follow Up Info Successfully Saved.");
        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
    }


    @GetMapping(value = "/list")
    public ResponseEntity<Map<String, Object>> list() throws IOException {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        List<CardFollowUp> followUpList = service.list();
        resultMap.put("followUpList", followUpList);
        resultMap.put("successMsg", "Follow Up  Info Successfully Received!");
        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);

    }

    @GetMapping(value = "/listById")
    public ResponseEntity<Map<String, Object>> view(@RequestParam(value = "cardId", required = true) String cardId) throws IOException {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        List<CardFollowUp> followUpList = service.list();
        resultMap.put("followUpList", followUpList);
        resultMap.put("successMsg", "Reference Info Successfully Received!");

        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);

    }

    @GetMapping("/remove")
    public ResponseEntity<Map<String, Object>> remove(@RequestParam(value = "id", required = true) String id) {
        Map<String, Object> resultMap = new HashMap<>();
        CardFollowUp cardFollowUp = service.findbyId(Long.parseLong(id));
        cardFollowUp.setEnabled(false);
        service.save(cardFollowUp);
        resultMap.put("successMsg", "Successful deleted");
        resultMap.put("followUp", cardFollowUp);
        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }

}
