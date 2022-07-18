package com.csinfotechbd.collection.settings.PTPContactLocation;
/*
Created by Monirul Islam at 6/30/2019 
*/

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/collection/ptpContact_location/")
public class PTPContactLocController {

    @Autowired
    private PTPContLocService ptpContLocService;

    @RequestMapping(value = "list")
    public String viewAll(Model model) {
        model.addAttribute("ptpContLocList", ptpContLocService.getList());
        return "collection/settings/ptpContactLocation/contact_location";
    }

    @GetMapping(value = "create")
    public String viewAddPage(Model model) {
        model.addAttribute("ptpContLoc", new PTPContactLocationEntity());
        return "collection/settings/ptpContactLocation/create";
    }

    @PostMapping(value = "create")
    public String create(PTPContactLocationEntity ptpContactLocationEntity, Model model) {
        String output = ptpContLocService.save(ptpContactLocationEntity);
        switch (output) {
            case "1":
                return "redirect:/collection/ptpContact_location/list";
            default:
                model.addAttribute("error", output);
        }
        model.addAttribute("ptpContLoc", ptpContactLocationEntity);
        return "collection/settings/ptpContactLocation/create";
    }

    @GetMapping(value = "edit")
    public String editPage(@RequestParam(value = "id") Long id, Model model) {
        model.addAttribute("ptpContLoc", ptpContLocService.getById(id));
        return "collection/settings/ptpContactLocation/create";
    }

    @GetMapping(value = "view")
    public String viewPage(@RequestParam(value = "id") Long id, Model model) {
        model.addAttribute("ptpContLoc", ptpContLocService.getById(id));
        return "collection/settings/ptpContactLocation/view";
    }

    @GetMapping(value = "listall")
    public ResponseEntity<Map<String, Object>> list() {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        List<PTPContactLocationEntity> location = ptpContLocService.getActiveList();
        resultMap.put("location", location);
        resultMap.put("successMsg", "Location Successfully Received!");
        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
    }
}
