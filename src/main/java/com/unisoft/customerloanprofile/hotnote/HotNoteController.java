package com.unisoft.customerloanprofile.hotnote;


import com.unisoft.collection.settings.employee.EmployeeService;
import com.unisoft.user.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/customerloanprofile/hotnote/")
public class HotNoteController {

    @Autowired
    private HotNoteSerivce hotNoteSerivce;

    @Autowired
    private EmployeeService employeeService;


    @GetMapping("list")
    @ResponseBody
    public List<HotNoteEntity> viewHotNoteList(@RequestParam(value = "id") String id){
        List<HotNoteEntity> hotNoteList = hotNoteSerivce.getHotNoteList(new Long(id));
        return hotNoteList;
    }


    @PostMapping(value = "save")
    @ResponseBody
    public List<HotNoteEntity> save(HotNoteEntity hotNoteEntity){
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        hotNoteEntity.setUsername(user.getFirstName().concat(" ").concat(user.getLastName()));
        hotNoteEntity.setCreatedBy(user.getUsername());
        hotNoteEntity.setCreatorPin(user.getUsername());
        hotNoteEntity.setCreatedDate(new Date());

        hotNoteSerivce.saveHotNoteInfo(hotNoteEntity);
        return hotNoteSerivce.getHotNoteList(hotNoteEntity.getCustomerBasicInfo().getId());
    }



    @GetMapping("find")
    @ResponseBody
    public HotNoteEntity editHotNotById(@RequestParam Long id){
        HotNoteEntity hotNoteEntity = hotNoteSerivce.findHotNoteEntityById(id);
        return hotNoteEntity;
    }


    @GetMapping("find/hotenote")
    @ResponseBody
    public List<HotNoteEntity> findHoteNoteBycustomerId(String customerId){
        Long custId = null;
        try{
            custId =  Long.valueOf(customerId);
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        return hotNoteSerivce.findHoteNoteBycustomerId(custId);
    }
}
