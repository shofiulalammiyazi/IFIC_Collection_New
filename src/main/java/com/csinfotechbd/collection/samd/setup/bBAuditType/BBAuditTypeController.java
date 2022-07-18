package com.csinfotechbd.collection.samd.setup.bBAuditType;


import com.csinfotechbd.audittrail.AuditTrailService;
import com.csinfotechbd.collection.samd.setup.bbApprovalRequirement.BBApprovalRequirementRepository;
import com.csinfotechbd.user.UserPrincipal;
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
import java.util.List;

@Controller
@RequestMapping("/collection/samd/setup/bb-audit-type")
public class BBAuditTypeController {


    @Autowired
    private BBAuditTypeService bbAuditTypeService;

    @Autowired
    private BBAuditTypeRepository bbAuditTypeRepository;

    @Autowired
    private AuditTrailService auditTrailService;



    @GetMapping("/list")
    public String  list(Model model){
        List<BBAuditType> bbAuditTypeList = bbAuditTypeService.findAll();
        model.addAttribute("bbAuditTypeList", bbAuditTypeList);
        return "samd/setup/bBAuditType/list";
    }



    @GetMapping("/create")
    public String createBBAuditTypeView(Model model){
        return prepareEditViewModel(model, new BBAuditType());
    }

    @PostMapping("/create")
    public String createBBAuditType(Model model, @Valid BBAuditType bbAuditType, BindingResult result){
//        if (!result.hasErrors()){
//            bbAuditTypeService.save(bbAuditType);
//            model.addAttribute("success", true);
//            return "redirect:/collection/samd/setup/bb-audit-type/list";
//        }
//
//        model.addAttribute("errro", true);
//        return prepareEditViewModel(model, bbAuditType);

        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(bbAuditType.getId()==null){
            bbAuditType.setCreatedBy(user.getUsername());
            bbAuditType.setCreatedDate(new Date());
            bbAuditTypeService.save(bbAuditType);
            auditTrailService.saveCreatedData("BB Audit type List", bbAuditType);
        }else{
            BBAuditType oldEntity = bbAuditTypeRepository.getOne(bbAuditType.getId());
            BBAuditType previouseEntity = new BBAuditType();
            BeanUtils.copyProperties(oldEntity,previouseEntity);

            bbAuditType.setModifiedBy(user.getUsername());
            bbAuditType.setModifiedDate(new Date());
            bbAuditTypeRepository.save(bbAuditType);
            auditTrailService.saveUpdatedData("BB Audit type List",previouseEntity,bbAuditType);
        }

        return "redirect:/collection/samd/setup/bb-audit-type/list";

    }



    @GetMapping("/edit")
    public String edit(Model model, @RequestParam Long id){
        BBAuditType bbAuditType = bbAuditTypeService.findBBAuditTypeById(id);
        return prepareEditViewModel(model, bbAuditType);
    }



    private String prepareEditViewModel(Model model, BBAuditType bbAuditType) {
        model.addAttribute("bbAuditType", bbAuditType);
        return "samd/setup/bBAuditType/create";
    }
}
