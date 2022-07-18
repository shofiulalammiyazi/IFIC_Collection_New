package com.csinfotechbd.collection.settings.diaryNoteSubMenu2;
/*
Created by Monirul Islam at 6/27/2019 
*/

import com.csinfotechbd.audittrail.AuditTrailService;
import com.csinfotechbd.collection.settings.diaryNoteMenu.DiaryNoteMenuEntity;
import com.csinfotechbd.collection.settings.diaryNoteMenu.DnoteMenuService;
import com.csinfotechbd.collection.settings.diaryNoteSubMenu1.DNSubMenu1Service;
import com.csinfotechbd.collection.settings.diaryNoteSubMenu1.DiaryNoteSubMenu1Entity;
import com.csinfotechbd.user.UserPrincipal;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/collection/dn_submenu2/")
public class DNSubMenu2Controller {

    @Autowired
    private DNSubMenu2Service dnSubMenu2Service;

    @Autowired
    private DNSubMenu1Service dnSubMenu1Service;

    @Autowired
    private DnoteMenuService dnoteMenuService;

    @Autowired
    private AuditTrailService auditTrailService;

    @GetMapping(value = "list")
    public String viewAll(Model model)
    {
        model.addAttribute("DNsubMenu2List",dnSubMenu2Service.getAll());
        return "collection/settings/diaryNoteSubMenu2/dnote_submenu2";
    }

    @GetMapping(value = "create")
    public String addPage(Model model)
    {
        model.addAttribute("DNsubMenu2",new DiaryNoteSubMenu2Entity());
        model.addAttribute("submenu1List",dnSubMenu1Service.getActiveList());
        model.addAttribute("menuList",dnoteMenuService.getAllActive());
        return "collection/settings/diaryNoteSubMenu2/create";
    }

    @PostMapping(value = "create")
    public String create(DiaryNoteSubMenu2Entity diaryNoteSubMenu2,@RequestParam(value = "menuId") Long menuId,
                         @RequestParam(value = "subMenu1Id")Long subMenu1Id)
    {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        DiaryNoteMenuEntity menu=new DiaryNoteMenuEntity();
        menu.setId(menuId);
        diaryNoteSubMenu2.setDiaryNoteMenu(menu);

        DiaryNoteSubMenu1Entity subMenu1=new DiaryNoteSubMenu1Entity();
        subMenu1.setId(subMenu1Id);
        diaryNoteSubMenu2.setDiaryNoteSubMenu1(subMenu1);

        if(diaryNoteSubMenu2.getId() == null)
        {
            diaryNoteSubMenu2.setCreatedBy(user.getUsername());
            diaryNoteSubMenu2.setCreatedDate(new Date());
            boolean save=dnSubMenu2Service.saveSM(diaryNoteSubMenu2);
            auditTrailService.saveCreatedData("Diary Note Sub Menu 2", diaryNoteSubMenu2);
        }else {
            DiaryNoteSubMenu2Entity oldEntity = dnSubMenu2Service.getById(diaryNoteSubMenu2.getId());
            DiaryNoteSubMenu2Entity previousEntity = new DiaryNoteSubMenu2Entity();
            BeanUtils.copyProperties(oldEntity,previousEntity);

            diaryNoteSubMenu2.setModifiedBy(user.getUsername());
            diaryNoteSubMenu2.setModifiedDate(new Date());
            boolean update=dnSubMenu2Service.updateSM(diaryNoteSubMenu2);
            auditTrailService.saveUpdatedData("Diary Note Sub Menu 2",previousEntity,diaryNoteSubMenu2);
        }
        return "redirect:/collection/dn_submenu2/list";
    }

    @GetMapping(value = "edit")
    public String addPage(@RequestParam(value = "id")Long Id,Model model)
    {
        //System.err.println(dnSubMenu2Service.getById(Id).toString());
       // System.err.println(dnoteMenuService.getAllActive().toString());

        model.addAttribute("menuList",dnoteMenuService.getAllActive());
        model.addAttribute("DNsubMenu2",dnSubMenu2Service.getById(Id));
        model.addAttribute("submenu1List",dnSubMenu1Service.getActiveList());
        return "collection/settings/diaryNoteSubMenu2/create";
    }

    @GetMapping(value = "view")
    public String viewPage(@RequestParam(value = "id")Long Id,Model model)
    {
        model.addAttribute("DNsubMenu2",dnSubMenu2Service.getById(Id));
        return "collection/settings/diaryNoteSubMenu2/view";
    }

//@abdullah  list of menu 2  according menu one id
    @GetMapping(value="listall")
    public ResponseEntity<Map<String, Object>> list(@RequestParam(value="submenuid1") String id,@RequestParam(value="mid") String mid) throws IOException {
        Map<String,Object> resultMap=new HashMap<String,Object>();
        List<DiaryNoteSubMenu2Entity> diaryNotesub2MenuEntityList =dnSubMenu2Service.getActiveListbysubMenu1Id(Long.parseLong(id),Long.parseLong(mid));
        resultMap.put("sub2List", diaryNotesub2MenuEntityList);
        resultMap.put("successMsg", "sub 2 List Info Successfully Received!");
        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);

    }
}
