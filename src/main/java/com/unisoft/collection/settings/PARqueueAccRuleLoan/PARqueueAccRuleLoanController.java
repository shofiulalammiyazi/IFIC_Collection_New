package com.unisoft.collection.settings.PARqueueAccRuleLoan;
/*
Created by   Islam on 7/10/2019
*/

import com.unisoft.audittrail.AuditTrailService;

import com.unisoft.collection.settings.dpdBucket.DPDBucketEntityDto;
import com.unisoft.collection.settings.dpdBucket.DPDBucketService;
import com.unisoft.collection.settings.productType.ProductTypeDto;
import com.unisoft.collection.settings.productType.ProductTypeService;
import com.unisoft.user.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/collection/par_queue_acc_rule_loan/")
public class PARqueueAccRuleLoanController {

    private final PARqueueAccRuleLoanService paRqueueAccRuleLoanService;
    private final ProductTypeService productTypeService;
    private final DPDBucketService dpdBucketService;
    private final AuditTrailService auditTrailService;

    @GetMapping(value = "list")
    public String viewALL(Model model) {
        model.addAttribute("parQueueList", paRqueueAccRuleLoanService.getAll());
        return "collection/settings/PARqueueAccRuleLoan/parQueueLoan";
    }

    @GetMapping(value = "create")
    public String addPage(Model model) {
//        List<String> selProdIdList = new ArrayList<>();
//        selProdIdList.add("");
//        List<String> selDpdBucketIdList = new ArrayList<>();
//
//        model.addAttribute("typeList", productTypeService.getByProductGroup("Loan"));
//        model.addAttribute("dpdBucketList", dpdBucketService.getActiveList());
//        model.addAttribute("selProdIdList", selProdIdList);
//        model.addAttribute("selDpdIdList", selDpdBucketIdList);

        return prepareEditViewModel(model, new PARqueueAccRuleLoanEntity());
    }

    @PostMapping(value = "create")
    public String create(Model model,PARqueueAccRuleLoanEntity paRqueueAccRuleLoan, BindingResult result) {

                if (result.hasErrors()){
                    return prepareEditViewModel(model, paRqueueAccRuleLoan);
                }

                UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

                if (paRqueueAccRuleLoan.getId() == null) {
                    paRqueueAccRuleLoan.setCreatedBy(user.getUsername());
                    paRqueueAccRuleLoan.setCreatedDate(new Date());
                    boolean save = paRqueueAccRuleLoanService.saveNew(paRqueueAccRuleLoan);
                    auditTrailService.saveCreatedData("PAR Queue Account Rules-Loan", paRqueueAccRuleLoan);
                } else {
                    PARqueueAccRuleLoanEntity oldEntity = paRqueueAccRuleLoanService.getById(paRqueueAccRuleLoan.getId());
                    PARqueueAccRuleLoanEntity previousEntity = new PARqueueAccRuleLoanEntity();
                    BeanUtils.copyProperties(oldEntity, previousEntity);

                    paRqueueAccRuleLoan.setModifiedBy(user.getUsername());
                    paRqueueAccRuleLoan.setModifiedDate(new Date());
                    boolean update = paRqueueAccRuleLoanService.updateRule(paRqueueAccRuleLoan);
                    auditTrailService.saveUpdatedData("PAR Queue Account Rules-Loan", previousEntity, paRqueueAccRuleLoan);
                }
                 paRqueueAccRuleLoanService.save(paRqueueAccRuleLoan);
             return "redirect:/collection/par_queue_acc_rule_loan/list";
    }

    @GetMapping(value = "edit")
    public String editPage(@RequestParam(value = "id") Long id, Model model) {
        PARqueueAccRuleLoanEntity parQueue = paRqueueAccRuleLoanService.getById(id);
        return prepareEditViewModel(model, parQueue);
    }

    private String prepareEditViewModel(Model model, PARqueueAccRuleLoanEntity parQueue) {

        List<DPDBucketEntityDto>selectedDpd = parQueue.getDpdBucketEntityList().stream().map(DPDBucketEntityDto::new).collect(Collectors.toList());
        List<ProductTypeDto>selectedProductType = parQueue.getProductTypeList().stream().map(ProductTypeDto::new).collect(Collectors.toList());

        model.addAttribute("typeList", productTypeService.getByProductGroup("Loan"));
        model.addAttribute("dpdBucketList", dpdBucketService.getCustomActiveList());
        model.addAttribute("parQueue", parQueue);
        model.addAttribute("selectedProductType", selectedProductType);
        model.addAttribute("selectedDpd", selectedDpd);
        return "collection/settings/PARqueueAccRuleLoan/create";
    }
}
