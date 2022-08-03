package com.unisoft.audittrail;

import com.unisoft.collection.settings.zone.ZoneEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/audittrail")
public class AuditTrailController {

    @Autowired
    private AuditTrailService auditTrailService;

    @GetMapping(value = "/list")
    public String viewAll(Model model) {
        //model.addAttribute("auditTrailList", auditTrailService.getAuditData());
        return "collection/auditTrail/audittrail";
    }

    @GetMapping(value = "/find-all")
    public ResponseEntity<?> findAll() {
        List<AuditTrail> list = auditTrailService.getAuditData();
        return ResponseEntity.ok(list);
    }

    @GetMapping(value = "/list/{pageNo}")
    public String viewAll(Model model, @PathVariable(name = "pageNo") int pageNo) {

        Page<AuditTrail> auditData = auditTrailService.getAuditData(pageNo);

        List<AuditTrail> auditTrails = auditData.getContent();

        model.addAttribute("auditTrailList",auditTrails);
        model.addAttribute("currentPage",pageNo);
        model.addAttribute("totalPages",auditData.getTotalPages());
        model.addAttribute("totalItems",auditData.getTotalElements());

        return "collection/auditTrail/audittrail";
    }

    @GetMapping(value = "/view")
    public String viewLoc(@RequestParam(value = "id") Long auditId, Model model) {
        model.addAttribute("auditdata", auditTrailService.getById(auditId));
        return "collection/auditTrail/view";
    }

    @GetMapping("/test")
    @ResponseBody
    public void test() {
        auditTrailService.saveDeletedData("Zone", new ZoneEntity());
    }
}
