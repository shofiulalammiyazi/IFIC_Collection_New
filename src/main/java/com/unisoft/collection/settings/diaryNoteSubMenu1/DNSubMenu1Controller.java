package com.unisoft.collection.settings.diaryNoteSubMenu1;
/*
Created by   Islam at 6/26/2019
*/

import com.unisoft.audittrail.AuditTrailService;
import com.unisoft.collection.settings.diaryNoteMenu.DiaryNoteMenuEntity;
import com.unisoft.collection.settings.diaryNoteMenu.DnoteMenuService;
import com.unisoft.user.UserPrincipal;
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
@RequestMapping(value = "/collection/dn_submenu1/")
public class DNSubMenu1Controller {

    @Autowired
    private DNSubMenu1Service dnSubMenu1Service;

    @Autowired
    private DnoteMenuService dnoteMenuService;

    @Autowired
    private AuditTrailService auditTrailService;

    @GetMapping(value = "list")
    public String viewAll(Model model)
    {
        model.addAttribute("DNsubMenu1List",dnSubMenu1Service.getAll());
        return "collection/settings/diaryNoteSubMenu1/dnote_submenu1";
    }

    @GetMapping(value = "create")
    public String addPage(Model model)
    {
        model.addAttribute("DNsubMenu1",new DiaryNoteSubMenu1Entity());
        model.addAttribute("menuList",dnoteMenuService.getAllActive());
        return "collection/settings/diaryNoteSubMenu1/create";
    }

    @PostMapping(value = "create")
    public String create(DiaryNoteSubMenu1Entity diaryNoteSubMenu1,@RequestParam(value = "menuId") Long menuId)
    {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        DiaryNoteMenuEntity menu=new DiaryNoteMenuEntity();
        menu.setId(menuId);
        diaryNoteSubMenu1.setDiaryNoteMenu(menu);

        if(diaryNoteSubMenu1.getId() == null)
        {
            diaryNoteSubMenu1.setCreatedBy(user.getUsername());
            diaryNoteSubMenu1.setCreatedDate(new Date());
            boolean save=dnSubMenu1Service.saveSM(diaryNoteSubMenu1);
            auditTrailService.saveCreatedData("Diary Note Sub Menu 1 ",diaryNoteSubMenu1);

        }else {
            DiaryNoteSubMenu1Entity oldEntity = dnSubMenu1Service.getById(diaryNoteSubMenu1.getId());
            DiaryNoteSubMenu1Entity previousEntity = new DiaryNoteSubMenu1Entity();
            BeanUtils.copyProperties(oldEntity,previousEntity);

            diaryNoteSubMenu1.setModifiedBy(user.getUsername());
            diaryNoteSubMenu1.setModifiedDate(new Date());
            boolean update=dnSubMenu1Service.updateSM(diaryNoteSubMenu1);
            auditTrailService.saveUpdatedData("Diary Note Sub Menu 1",previousEntity,diaryNoteSubMenu1);
        }
        return "redirect:/collection/dn_submenu1/list";
    }

    @GetMapping(value = "edit")
    public String addPage(@RequestParam(value = "id")Long Id,Model model)
    {
        System.err.println(dnSubMenu1Service.getById(Id).toString());
        System.err.println(dnoteMenuService.getAllActive().toString());

        model.addAttribute("menuList",dnoteMenuService.getAllActive());
        model.addAttribute("DNsubMenu1",dnSubMenu1Service.getById(Id));
        return "collection/settings/diaryNoteSubMenu1/create";
    }

    @GetMapping(value = "view")
    public String viewPage(@RequestParam(value = "id")Long Id,Model model)
    {
        model.addAttribute("DNsubMenu1",dnSubMenu1Service.getById(Id));
        return "collection/settings/diaryNoteSubMenu1/view";
    }


    @GetMapping(value="listall")
    public ResponseEntity<Map<String, Object>> list(@RequestParam(value="menuid") String id) throws IOException {
        Map<String,Object> resultMap=new HashMap<String,Object>();
        List<DiaryNoteSubMenu1Entity> diaryNotesub1MenuEntityList =dnSubMenu1Service.getActiveListbyMenuId(Long.parseLong(id));
        resultMap.put("sub1List", diaryNotesub1MenuEntityList);
        resultMap.put("successMsg", "sub 1 List Info Successfully Received!");
        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);

    }
}
