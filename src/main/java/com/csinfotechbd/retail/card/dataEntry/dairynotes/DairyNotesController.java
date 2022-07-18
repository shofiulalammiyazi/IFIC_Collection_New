package com.csinfotechbd.retail.card.dataEntry.dairynotes;

import com.csinfotechbd.customerbasicinfo.CustomerBasicInfoEntity;
import com.csinfotechbd.customerbasicinfo.CustomerBasicInfoEntityRepository;
import com.csinfotechbd.customerloanprofile.diarynote.DairyNotesLoan;
import com.csinfotechbd.user.UserPrincipal;
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
@RequestMapping("/collection/card/dairy-notes")
public class DairyNotesController {
    @Autowired
    DairyNotesService service;

    @Autowired
    private CustomerBasicInfoEntityRepository customerBasicInfoEntityRepository;

    @Autowired
    DairyNotesRepository repository;

   /* @PostMapping(value="/save")
    public ResponseEntity<Map<String, Object>> saveReferenceInfo(DairyNotes dairyNotes) throws IOException {
        Map<String,Object> resultMap=new HashMap<>();
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        dairyNotes.setCreatedBy(user.getUsername());
        dairyNotes.setCreatedDate(new Date());
        dairyNotes.setEnabled(true);
        DairyNotes notes = service.save(dairyNotes);
        resultMap.put("dairyNotes", notes);
        resultMap.put("successMsg", "Dairy Notes  Successfully Saved.");
        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
    }*/


    @PostMapping(value="/save")
    public List<DairyNotes> saveReferenceInfo(DairyNotes dairyNotes) throws IOException {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        dairyNotes.setCreatedBy(user.getUsername());
        dairyNotes.setCreatedDate(new Date());
        dairyNotes.setEnabled(true);
        dairyNotes.setPin(user.getUsername());
        dairyNotes.setUsername(user.getLastName());
        DairyNotes notes = service.save(dairyNotes);
        List<DairyNotes> dairyNotesCardList =repository.findByCustomerBasicInfo(notes.getCustomerBasicInfo());
        return dairyNotesCardList;
    }

    @GetMapping(value="/list1")
    public List<DairyNotes> list(@RequestParam(value = "id") String id) throws IOException{
        CustomerBasicInfoEntity customerBasicInfoEntity = customerBasicInfoEntityRepository.findById(new Long(id)).get();
        List<DairyNotes> dairyNotesList =repository.findByCustomerBasicInfo(customerBasicInfoEntity);
        return dairyNotesList;

    }


    @GetMapping(value="/list")
    public ResponseEntity<Map<String, Object>> list() throws IOException{
        Map<String,Object> resultMap=new HashMap<String,Object>();
        List<DairyNotes> dairyNotesList =service.list();
        resultMap.put("dairyNotesList", dairyNotesList);
        resultMap.put("successMsg", "Dairy Notes Info Successfully Received!");
        return new ResponseEntity<Map<String, Object>>(resultMap,HttpStatus.OK);

    }
    @GetMapping(value="/listById")
    public ResponseEntity<Map<String, Object>> view(@RequestParam(value = "cardId", required = true) String cardId ) throws IOException{
        UserPrincipal user = (UserPrincipal)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Map<String,Object> resultMap=new HashMap<String,Object>();
        List<DairyNotes> followUpList =service.list();
        resultMap.put("followUpList", followUpList);
        resultMap.put("successMsg", "Dairy Notes Info Successfully Received!");

        return new ResponseEntity<Map<String, Object>>(resultMap,HttpStatus.OK);

    }

    @GetMapping("/remove")
    public ResponseEntity<Map<String, Object>> remove(@RequestParam(value = "id", required = true) String id){
        Map<String, Object> resultMap=new HashMap<>();
        DairyNotes dairyNotes =service.findbyId(Long.parseLong(id));
        dairyNotes.setEnabled(false);
        service.save(dairyNotes);
        resultMap.put("successMsg","Successful deleted");
        resultMap.put("dairyNotes", dairyNotes);
        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }

}
