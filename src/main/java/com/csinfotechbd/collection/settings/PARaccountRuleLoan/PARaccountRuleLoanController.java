package com.csinfotechbd.collection.settings.PARaccountRuleLoan;
/*
Created by Monirul Islam on 7/10/2019 
*/

import com.csinfotechbd.audittrail.AuditTrailService;
import com.csinfotechbd.collection.settings.dpdBucket.DPDBucketEntity;
import com.csinfotechbd.collection.settings.dpdBucket.DPDBucketEntityDto1;
import com.csinfotechbd.collection.settings.dpdBucket.DPDBucketService;
import com.csinfotechbd.user.UserPrincipal;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Controller
@RequestMapping(value = "/collection/par_account_rule_loan/")
public class PARaccountRuleLoanController {

    private PARaccountRuleLoanService paRaccountRuleLoanService;
    private DPDBucketService dpdBucketService;
    private final AuditTrailService auditTrailService;


    @GetMapping(value = "list")
    public String viewAll(Model model) {
        model.addAttribute("parList", paRaccountRuleLoanService.getAll());
        return "collection/settings/parAccountRuleLoan/parRule";
    }

    @PostMapping(value = "create")
    public String create(@Valid PARaccountRuleLoanEntity paRaccountRuleLoan, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            List<DPDBucketEntityDto1> dpdBucketEntityList = dpdBucketService.getCustomActiveList();
            Gson gson = new Gson();
            model.addAttribute("dpdBucketList", gson.toJson(dpdBucketEntityList));
            model.addAttribute("parRuleLoan", paRaccountRuleLoan);
            model.addAttribute("result", result.getFieldError());
            return "collection/settings/parAccountRuleLoan/create";
        }

        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (paRaccountRuleLoan.getId() == null) {
            paRaccountRuleLoan.setCreatedBy(user.getUsername());
            paRaccountRuleLoan.setCreatedDate(new Date());
            boolean save = paRaccountRuleLoanService.saveNew(paRaccountRuleLoan);
            auditTrailService.saveCreatedData("PAR Account Rules-Loan", paRaccountRuleLoan);
        } else {
            PARaccountRuleLoanEntity oldEntity = paRaccountRuleLoanService.getById(paRaccountRuleLoan.getId());
            PARaccountRuleLoanEntity previousEntity = new PARaccountRuleLoanEntity();
            BeanUtils.copyProperties(oldEntity, previousEntity);

            paRaccountRuleLoan.setModifiedBy(user.getUsername());
            paRaccountRuleLoan.setModifiedDate(new Date());
            boolean update = paRaccountRuleLoanService.updatePAR(paRaccountRuleLoan);
            auditTrailService.saveUpdatedData("PAR Account Rules-Loan", previousEntity, paRaccountRuleLoan);
        }
        redirectAttributes.addFlashAttribute("savedStatus", "Saved Successfully");
        return "redirect:/collection/par_account_rule_loan/list";
    }

    @GetMapping(value = "create")
    public String addPage(Model model) {
        List<DPDBucketEntityDto1> dpdBucketEntityList = dpdBucketService.getCustomActiveList();
        Gson gson = new Gson();
        model.addAttribute("dpdBucketList", gson.toJson(dpdBucketEntityList));
        model.addAttribute("parRuleLoan", new PARaccountRuleLoanEntity());
        return "collection/settings/parAccountRuleLoan/create";
    }

    @GetMapping(value = "edit")
    public String editPage(Model model, @RequestParam(value = "id") Long id) {
        PARaccountRuleLoanEntity paRaccountRuleLoanEntity = paRaccountRuleLoanService.getById(id);
        //List<DPDBucketEntity> dpdBucketEntityList = dpdBucketService.getActiveList();
        List<DPDBucketEntityDto1> dpdBucketEntityList = dpdBucketService.getCustomActiveList();
        Gson gson = new Gson();
        model.addAttribute("dpdBucketList", gson.toJson(dpdBucketEntityList));
        model.addAttribute("parRuleLoan", paRaccountRuleLoanEntity);
        model.addAttribute("selectedDpdBucketList", gson.toJson(paRaccountRuleLoanEntity.getDpdBucketEntityList()));
        return "collection/settings/parAccountRuleLoan/create";
    }
}
