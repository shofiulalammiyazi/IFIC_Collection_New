package com.unisoft.retail.card.dataEntry.hotnotes;

import com.unisoft.user.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/collection/card/hotnotes")
public class CardHotNotesController {

    @Autowired
    private CardHotNotesRepository cardHotNotesRepository;

    @PostMapping(value="/save")
    public ResponseEntity<Map<String, Object>> saveReferenceInfo(CardHotNotes cardFollowUp) throws IOException {
        Map<String,Object> resultMap=new HashMap<>();
        System.out.println( "data" +cardFollowUp);
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        cardFollowUp.setUsername(user.getFirstName().concat(" ").concat(user.getLastName()));
        cardFollowUp.setCreatedBy(user.getUsername());
        cardFollowUp.setCreatedDate(new Date());
        cardFollowUp.setEnabled(true);
        cardHotNotesRepository.save(cardFollowUp);
        resultMap.put("followUp", cardFollowUp);
        resultMap.put("successMsg", "Hot Notes Up Info Successfully Saved.");
        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
    }

    @GetMapping(value="/listById")
    public ResponseEntity<Map<String, Object>> view(@RequestParam(value = "hnId", required = true) String hnId ) throws IOException{
        UserPrincipal user = (UserPrincipal)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Map<String,Object> resultMap=new HashMap<String,Object>();
        Optional<CardHotNotes> hn =cardHotNotesRepository.findById(Long.parseLong(hnId));
        resultMap.put("hn", hn);
        return new ResponseEntity<Map<String, Object>>(resultMap,HttpStatus.OK);

    }
}
