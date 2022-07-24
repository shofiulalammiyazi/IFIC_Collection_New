package com.unisoft.collection.samd.setup.bbApprovalRequirement;


import com.unisoft.audittrail.AuditTrailService;
import com.unisoft.user.UserPrincipal;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Date;

@Controller
@RequestMapping("/collection/samd/setup/bbApproval-requirement")
public class BBApprovalRequirementController {

    @Autowired
    private BBApprovalRequirementService bbApprovalRequirementService;

    @Autowired
    private BBApprovalRequirementRepository bbApprovalRequirementRepository;

    @Autowired
    private AuditTrailService auditTrailService;


    @GetMapping("/list")
    public String bBApprovalRequirementList(Model model){
        model.addAttribute("bBApprovalRequirementList",bbApprovalRequirementService.findAll());
        return "samd/setup/bBApprovalRequirement/list";
    }


    @GetMapping("/save")
    public String bBApprovalRequirementView(Model model){
        return prepareEditAndModelView(new BBApprovalRequirement(), model);
    }


    @PostMapping("/save")
    public String saveBbApprovalRequirement(Model model, @Valid BBApprovalRequirement bbApprovalRequirement, BindingResult result){
//        if (!result.hasErrors()){
////            BBApprovalRequirement bbApprovalRequirement1 = bbApprovalRequirementService.save(bbApprovalRequirement);
////            model.addAttribute("success", true);
////            return "redirect:/collection/samd/setup/bbApproval-requirement/list";
////        }else {
////            model.addAttribute("error", true);
////        }

        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(bbApprovalRequirement.getId()==null){
            bbApprovalRequirement.setCreatedBy(user.getUsername());
            bbApprovalRequirement.setCreatedDate(new Date());
            bbApprovalRequirementService.save(bbApprovalRequirement);
            auditTrailService.saveCreatedData("BB Approval Requirement", bbApprovalRequirement);
        }else {
            BBApprovalRequirement oldEntity = bbApprovalRequirementRepository.getOne(bbApprovalRequirement.getId());
            BBApprovalRequirement previewEntity = new BBApprovalRequirement();
            BeanUtils.copyProperties(oldEntity,previewEntity);

            bbApprovalRequirement.setModifiedBy(user.getUsername());
            bbApprovalRequirement.setModifiedDate(new Date());
            bbApprovalRequirementRepository.save(bbApprovalRequirement);
            auditTrailService.saveUpdatedData("BB Approval Requirement", previewEntity, bbApprovalRequirement);
        }

            return "redirect:/collection/samd/setup/bbApproval-requirement/list";

//        return prepareEditAndModelView(bbApprovalRequirement, model);
    }


    @GetMapping("/edit")
    public String editBBApprovalREquirement(@RequestParam Long id, Model model){
        BBApprovalRequirement bbApprovalRequirement = bbApprovalRequirementService.findBBApprovalRequirementById(id);
        return prepareEditAndModelView(bbApprovalRequirement,model);
    }


    private String prepareEditAndModelView(BBApprovalRequirement bbApprovalRequirement, Model model) {
        model.addAttribute("bbApprovalRequirement", bbApprovalRequirement);
        return "samd/setup/bBApprovalRequirement/create";
    }


}
