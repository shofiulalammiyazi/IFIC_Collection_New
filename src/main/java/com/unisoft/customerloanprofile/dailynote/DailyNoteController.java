package com.unisoft.customerloanprofile.dailynote;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.unisoft.user.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/customerloanprofile/dailynote/")
public class DailyNoteController {
    @Autowired
    private DailyNoteService dailyNoteService;

    @Autowired
    private DailyNoteRepository repository;

    @Autowired
    private DailyNoteRepository dailyNoteRepository;

    @GetMapping("listAccount")
    @ResponseBody
    public List<DailyNoteEntity> viewDailyNoteAccountList(@RequestParam(value = "id") String accountNo){
        List<DailyNoteEntity> dailyNoteList = dailyNoteService.getDailyNoteAccList(accountNo);
        return dailyNoteList;
    }
//    @GetMapping("list")
//    @ResponseBody
//    public List<DailyNoteEntity> viewDailyNoteList(@RequestParam(value = "id") String id){
//        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
//        List<DailyNoteEntity> dailyNoteList = dailyNoteService.getDailyNoteList(new Long(id));
//        dailyNoteList.forEach(dailyNoteEntity1 -> {
////            String noteFromLobObject = gson.fromJson(dailyNoteEntity1.getNote(), String.class);
//
//        });
//        return dailyNoteList;
//    }


    @PostMapping(value = "save")
    @ResponseBody
    public List<DailyNoteEntity> save( DailyNoteEntity dailyNoteEntity){
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String note = dailyNoteEntity.getNote();
        dailyNoteEntity.setNote(note);
        dailyNoteEntity.setPin(user.getUsername());
        dailyNoteEntity.setCreatedBy(user.getFirstName());
        dailyNoteEntity.setCreatedDate(new Date());
//        dailyNoteService.saveDailyNoteInfo(dailyNoteEntity);
        dailyNoteService.save(dailyNoteEntity);
        List<DailyNoteEntity> dailyNoteList =repository.findByCustomerBasicInfo(dailyNoteEntity.getCustomerBasicInfo());

//        List<DailyNoteEntity> dailyNoteList = dailyNoteService.getDailyNoteList(new Long(dailyNoteEntity.getCustomerBasicInfo().getId()));
//        dailyNoteList.forEach(dailyNoteEntity1 -> {
//            String noteFromLobObject = gson.fromJson(dailyNoteEntity1.getNote(), String.class);
//            dailyNoteEntity1.setNoteFromLobObject(noteFromLobObject);
//        });
        return dailyNoteList;
    }

    @GetMapping(value="/ById")
    public ResponseEntity<Map<String, Object>> view(@RequestParam(value = "id", required = true) String id ) throws IOException {
        Map<String,Object> resultMap=new HashMap<String,Object>();
        Optional<DailyNoteEntity> dn =dailyNoteRepository.findById(Long.parseLong(id));
        resultMap.put("dn", dn);
        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);

    }
}
