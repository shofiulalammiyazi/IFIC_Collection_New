package com.csinfotechbd.retail.card.dataEntry.cardreferenceinfo;

import com.csinfotechbd.user.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/collection/reference")
public class CardReferenceInfoController {
    @Autowired
    CardReferenceInfoService service;

    @PostMapping(value = "/save")
    public ResponseEntity<Map<String, Object>> saveReferenceInfo(CardReferenceInfo cardReferenceInfo) throws IOException {
        Map<String, Object> resultMap = new HashMap<>();
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        cardReferenceInfo.setCreatedBy(user.getUsername());
        cardReferenceInfo.setCreatedDate(new Date());
        cardReferenceInfo.setEnabled(true);
        CardReferenceInfo referenceInfo = service.save(cardReferenceInfo);
        resultMap.put("referenceInfo", referenceInfo);
        resultMap.put("successMsg", "Reference Info Successfully Saved.");
        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
    }


    @GetMapping(value = "/list")
    public ResponseEntity<Map<String, Object>> list() throws IOException {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        List<CardReferenceInfo> referenceInfoList = service.list();
        resultMap.put("referenceInfoList", referenceInfoList);
        resultMap.put("successMsg", "Reference Info Successfully Received!");
        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);

    }

    @GetMapping(value = "/listById")
    public ResponseEntity<Map<String, Object>> view(@RequestParam(value = "cardId", required = true) String cardId) throws IOException {

        Map<String, Object> resultMap = new HashMap<String, Object>();
        List<CardReferenceInfo> referenceInfoList = service.list();
        resultMap.put("referenceInfoList", referenceInfoList);
        resultMap.put("successMsg", "Reference Info Successfully Received!");

        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);

    }
//    @GetMapping(value = "/listById")
//    public ResponseEntity<Map<String, Object>> view(@RequestParam(value = "cardId", required = true) String cardId) throws IOException {
//        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        Map<String, Object> resultMap = new HashMap<String, Object>();
//        List<CardReferenceInfo> referenceInfoList = service.list();
//        resultMap.put("referenceInfoList", referenceInfoList);
//        resultMap.put("successMsg", "Reference Info Successfully Received!");
//
//        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
//
//    }

    @GetMapping("/remove")
    public ResponseEntity<Map<String, Object>> remove(@RequestParam(value = "id", required = true) String id) {
        Map<String, Object> resultMap = new HashMap<>();
        CardReferenceInfo cardReferenceInfo = service.findbyId(Long.parseLong(id));
        cardReferenceInfo.setEnabled(false);
        cardReferenceInfo.setDeleted(true);
        service.save(cardReferenceInfo);
        resultMap.put("successMsg", "Successful deleted");
        resultMap.put("referenceInfo", cardReferenceInfo);
        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }

}
