package com.unisoft.collection.settings.diaryNoteMenu;

import com.unisoft.audittrail.AuditTrailService;
import com.unisoft.user.UserPrincipal;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/collection/dnote_menu/")
public class DnoteMenuController {

    @Autowired
    private DnoteMenuService dnoteMenuService;

    @Autowired
    private AuditTrailService auditTrailService;

    @GetMapping(value = "list")
    public String viewAll(Model model) {
        model.addAttribute("dnoteMenuList", dnoteMenuService.getAll());
        return "collection/settings/diaryNoteMenu/dnote_menu";
    }

    @GetMapping(value = "create")
    public String viewAddPage(Model model) {
        model.addAttribute("dnoteMenu", new DiaryNoteMenuEntity());
        return "collection/settings/diaryNoteMenu/create";
    }

    @PostMapping(value = "create")
    public String create(DiaryNoteMenuEntity diaryNoteMenu) {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (diaryNoteMenu.getId() == null) {
            diaryNoteMenu.setCreatedBy(user.getUsername());
            diaryNoteMenu.setCreatedDate(new Date());
            boolean save = dnoteMenuService.saveDNM(diaryNoteMenu);
        } else  {
            diaryNoteMenu.setModifiedBy(user.getUsername());
            diaryNoteMenu.setModifiedDate(new Date());
            boolean update = dnoteMenuService.updateDNM(diaryNoteMenu);

            DiaryNoteMenuEntity oldData = dnoteMenuService.getById(diaryNoteMenu.getId());
            DiaryNoteMenuEntity previousEntity = new DiaryNoteMenuEntity();
            BeanUtils.copyProperties(oldData,previousEntity);

            auditTrailService.saveUpdatedData("Diary Note Menu",previousEntity,diaryNoteMenu);
        }
        return "redirect:/collection/dnote_menu/list";
    }

    @GetMapping(value = "view")
    public String viewDNM(@RequestParam(value = "id") Long Id, Model model) {
        model.addAttribute("dnoteMenu", dnoteMenuService.getById(Id));
        return "collection/settings/diaryNoteMenu/view";
    }

    @GetMapping(value = "edit")
    public String updateDNM(@RequestParam(value = "id") Long Id, Model model) {
        model.addAttribute("dnoteMenu", dnoteMenuService.getById(Id));
        return "collection/settings/diaryNoteMenu/create";
    }


    @GetMapping(value = "listall")
    public ResponseEntity<Map<String, Object>> list() throws IOException {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        List<DiaryNoteMenuEntity> diaryNoteMenuEntityList = dnoteMenuService.getAllActive();
        resultMap.put("menuList", diaryNoteMenuEntityList);
        resultMap.put("successMsg", "Menu List Info Successfully Received!");
        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);

    }

    @GetMapping("find-list")
    @ResponseBody
    public List<DiaryNoteMenuEntity> getDiaryNoteList() {
        List<DiaryNoteMenuEntity> getAll = dnoteMenuService.getAllActive();
        return getAll;
    }


    @PostMapping("save")
    public ResponseEntity<?> saveNote(DiaryNoteMenuEntity diaryNoteMenuEntity) {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (diaryNoteMenuEntity.getId() == null) {
            diaryNoteMenuEntity.setCreatedBy(user.getUsername());
            diaryNoteMenuEntity.setCreatedDate(new Date());
            DiaryNoteMenuEntity diaryNoteMenuEntity1 = dnoteMenuService.saveNote(diaryNoteMenuEntity);
            return new ResponseEntity<>(diaryNoteMenuEntity1, HttpStatus.OK);
        } else {
            diaryNoteMenuEntity.setModifiedBy(user.getUsername());
            diaryNoteMenuEntity.setModifiedDate(new Date());
            DiaryNoteMenuEntity diaryNoteMenuEntity1 = dnoteMenuService.updateDiaryNoteMenu(diaryNoteMenuEntity);
            return new ResponseEntity<>(diaryNoteMenuEntity1, HttpStatus.OK);
        }
    }
}
