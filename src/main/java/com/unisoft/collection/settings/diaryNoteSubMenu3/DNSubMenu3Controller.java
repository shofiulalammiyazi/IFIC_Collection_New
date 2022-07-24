package com.unisoft.collection.settings.diaryNoteSubMenu3;

import com.unisoft.audittrail.AuditTrailService;
import com.unisoft.collection.settings.diaryNoteMenu.DiaryNoteMenuEntity;
import com.unisoft.collection.settings.diaryNoteMenu.DnoteMenuService;
import com.unisoft.collection.settings.diaryNoteSubMenu1.DNSubMenu1Service;
import com.unisoft.collection.settings.diaryNoteSubMenu1.DiaryNoteSubMenu1Entity;
import com.unisoft.collection.settings.diaryNoteSubMenu2.DNSubMenu2Service;
import com.unisoft.collection.settings.diaryNoteSubMenu2.DiaryNoteSubMenu2Entity;
import com.unisoft.user.UserPrincipal;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.*;

/*
Created by   Islam at 6/30/2019
*/
@Controller
@RequestMapping(value = "/collection/dn_submenu3/")
public class DNSubMenu3Controller {

    @Autowired
    public DNSubMenu3Service dnSubMenu3Service;

    @Autowired
    private DnoteMenuService menuService;

    @Autowired
    private DNSubMenu1Service menu1Service;

    @Autowired
    private DNSubMenu2Service menu2Service;

    @Autowired
    private DNSubMenu3Repository dnSubMenu3Repository;

    @Autowired
    private AuditTrailService auditTrailService;

    @GetMapping(value = "list")
    public String viewAll(Model model)
    {
        model.addAttribute("DNsubMenu3List",dnSubMenu3Service.getAll());
        return "collection/settings/diaryNoteSubMenu3/dnote_submenu3";
    }

    @GetMapping(value = "create")
    public String viewAddPage(Model model)
    {
        model.addAttribute("submenu1List",menu1Service.getActiveList());
        model.addAttribute("submenu2List",menu2Service.getActiveList());
        model.addAttribute("menuList",menuService.getAllActive());
        model.addAttribute("DNsubMenu3",new DiaryNoteSubMenu3Entity());

        return "collection/settings/diaryNoteSubMenu3/create";
    }

    @PostMapping(value = "create")
    public String saveSM(@Valid @ModelAttribute("DNsubMenu3") DiaryNoteSubMenu3Entity subMenu3, BindingResult bindingResult, @RequestParam(value = "menuId") Long menuId,
                         @RequestParam(value = "subMenu1Id") Long subMenu1Id,
                         @RequestParam(value = "subMenu2Id") Long subMenu2Id, Model model)
    {
        DiaryNoteSubMenu1Entity  menu1Entity=new DiaryNoteSubMenu1Entity();
        DiaryNoteSubMenu2Entity menu2Entity=new DiaryNoteSubMenu2Entity();
        DiaryNoteMenuEntity menuEntity=new DiaryNoteMenuEntity();

        Optional<DiaryNoteSubMenu3Entity> subMenu3Entity = Optional.ofNullable(dnSubMenu3Repository.findByCode(subMenu3.getCode()));
        if(subMenu3Entity.isPresent()){
            bindingResult.rejectValue("code", "error.subMenu3", "Dairy note menu with this code already exist");
            model.addAttribute("submenu1List",menu1Service.getActiveList());
            model.addAttribute("submenu2List",menu2Service.getActiveList());
            model.addAttribute("menuList",menuService.getAllActive());
//            model.addAttribute("DNsubMenu3",new DiaryNoteSubMenu3Entity());
            model.addAttribute("DNsubMenu3", subMenu3);
            return "collection/settings/diaryNoteSubMenu3/create";
        }


        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        menu1Entity.setId(subMenu1Id);
        menu2Entity.setId(subMenu2Id);
        menuEntity.setId(menuId);

        subMenu3.setDiaryNoteMenu(menuEntity);
        subMenu3.setDiaryNoteSubMenu1(menu1Entity);
        subMenu3.setDiaryNoteSubMenu2(menu2Entity);

        if(subMenu3.getId() == null)
        {
            subMenu3.setCreatedBy(user.getUsername());
            subMenu3.setCreatedDate(new Date());
            boolean save=dnSubMenu3Service.saveDNSM(subMenu3);
            auditTrailService.saveCreatedData("Diary Note Sub Menu 3",subMenu3);
        }else {
            DiaryNoteSubMenu3Entity oldEntity = dnSubMenu3Service.getById(subMenu3.getId());
            DiaryNoteSubMenu3Entity previousEntity = new DiaryNoteSubMenu3Entity();
            BeanUtils.copyProperties(oldEntity,previousEntity);

            subMenu3.setModifiedBy(user.getUsername());
            subMenu3.setModifiedDate(new Date());
            boolean update=dnSubMenu3Service.updateDNSM(subMenu3);
            auditTrailService.saveUpdatedData("Diary Note Sub Menu 3",previousEntity,subMenu3);
        }
        return "redirect:/collection/dn_submenu3/list";
    }

    @GetMapping(value = "edit")
    public String editPage(Model model,@RequestParam(value = "id")Long Id)
    {
        model.addAttribute("submenu1List",menu1Service.getActiveList());
        model.addAttribute("submenu2List",menu2Service.getActiveList());
        model.addAttribute("menuList",menuService.getAllActive());
        model.addAttribute("DNsubMenu3",dnSubMenu3Service.getById(Id));

        return "collection/settings/diaryNoteSubMenu3/create";
    }

    @GetMapping(value = "view")
    public String viewPage(@RequestParam(value = "id")Long id,Model model)
    {
        model.addAttribute("DNsubMenu3",dnSubMenu3Service.getById(id));
        System.err.println("DNsubMenu3 "+dnSubMenu3Service.getById(id));
        return "collection/settings/diaryNoteSubMenu3/view";
    }

    @GetMapping(value="listall")
    public ResponseEntity<Map<String, Object>> list(@RequestParam(value="sub2id") String id) throws IOException {
        Map<String,Object> resultMap=new HashMap<String,Object>();
        List<DiaryNoteSubMenu3Entity> diaryNotesub1MenuEntityList =dnSubMenu3Service.getActiveListbysub2Id(Long.parseLong(id));
        resultMap.put("sub3List", diaryNotesub1MenuEntityList);
        resultMap.put("successMsg", "sub 3 List Info Successfully Received!");
        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);

    }
}
