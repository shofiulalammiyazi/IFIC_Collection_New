package com.csinfotechbd.legal.dataEntry.fileFollowUp;


// Created by Yasir Araphat on 19 March, 2021

import com.csinfotechbd.legal.setup.legalExpense.LegalExpenseEntity;
import com.csinfotechbd.legal.setup.legalExpense.LegalExpenseService;
import com.csinfotechbd.workflow.propertyBasedMakerChecker.PropertyBasedMakerCheckerService;
import lombok.RequiredArgsConstructor;
import org.apache.chemistry.opencmis.commons.exceptions.CmisUnauthorizedException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
@RequestMapping("/legal/data-entry/litigation-file-follow-up/")
public class LitigationFileFollowUpController {


    private final LitigationFileFollowUpService fileFollowUpService;
    private final PropertyBasedMakerCheckerService<LitigationFileFollowUp> makerCheckerService;
    private final LegalExpenseService legalExpenseRepository;

    @GetMapping("list")
    public String list(Model model) {
        model.addAttribute("fileFollowUpList", fileFollowUpService.findAll());
        return "legal/dataEntry/fileFollowUp/list";
    }

    @GetMapping("create")
    public String save(Model model) {
        List<LegalExpenseEntity> legalExpenseEntityList = legalExpenseRepository.findByEnabled(true);

        model.addAttribute("legalExpense", legalExpenseEntityList);
        model.addAttribute("fileFollowUp", new LitigationFileFollowUpDto());
        return "legal/dataEntry/fileFollowUp/create";
    }
//
//    @PostMapping("create")
//    public String save(@Valid LitigationFileFollowUp fileFollowUp, BindingResult result, Model model) {
//        if (result.hasErrors()) return "legal/dataEntry/fileFollowUp/create";
//        String output = fileFollowUpService.save(fileFollowUp);
//        switch (output) {
//            case "0":
//                model.addAttribute("error", "Court name already exists");
//                return "legal/dataEntry/fileFollowUp/create";
//            default:
//                return "redirect:/legal/setup/fileFollowUp/list";
//        }
//    }

    @GetMapping("edit")
    public String edit(@RequestParam("id") Long id, Model model) {
        LitigationFileFollowUp followUp = fileFollowUpService.findById(id);
        List<LegalExpenseEntity> legalExpenseEntityList = legalExpenseRepository.findByEnabled(true);

        model.addAttribute("legalExpense", legalExpenseEntityList);
        model.addAttribute("fileFollowUp", new LitigationFileFollowUpDto(followUp));
        model.addAttribute("edit", true);
        return "legal/dataEntry/fileFollowUp/create";
    }

    @GetMapping("view")
    public String view(@RequestParam(value = "id") Long id, Model model) {
        model.addAttribute("fileFollowUp", fileFollowUpService.findById(id));
        model.addAttribute("view", true);
        return "legal/dataEntry/fileFollowUp/create";
    }

    @GetMapping("approval")
    public String approve(Model model) {
        List<LitigationFileFollowUp> list = fileFollowUpService.findAll();
        model.addAttribute("list", list);
        return "card/contents/settings/role/approval";
    }

    @GetMapping(value = "approve")
    @ResponseBody
    public String approve(Integer[] ids, HttpSession session) {
        String urlList = Objects.toString(session.getAttribute("urlList"), "");
        if (urlList.contains("/legal/data-entry/litigation-file-follow-up/-checker")) {
            int numberOfModifiedEntries = makerCheckerService.approve(LitigationFileFollowUp.class, "id", ids);
            return numberOfModifiedEntries > 0 ? "success" : "failed";
        }
        throw new CmisUnauthorizedException("Unauthorized Access");
    }

    @GetMapping(value = "reject")
    @ResponseBody
    public String reject(Integer[] ids, String remark, HttpSession session) {
        String urlList = Objects.toString(session.getAttribute("urlList"), "");
        if (urlList.contains("/legal/data-entry/litigation-file-follow-up/-checker")) {
            int numberOfModifiedEntries = makerCheckerService.reject(LitigationFileFollowUp.class, "id", ids, remark);
            return numberOfModifiedEntries > 0 ? "success" : "failed";
        }
        throw new CmisUnauthorizedException("Access Denied");
    }


}
