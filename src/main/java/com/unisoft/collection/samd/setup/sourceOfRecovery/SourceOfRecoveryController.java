package com.unisoft.collection.samd.setup.sourceOfRecovery;


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
import java.util.List;

@Controller
@RequestMapping("/collection/samd/setup/source-of-recovery")
public class SourceOfRecoveryController {


    @Autowired
    private AuditTrailService auditTrailService;

    @Autowired
    private SourceOfRecoveryRepository sourceOfRecoveryRepository;

    @Autowired
    private SourceOfRecoveryService sourceOfRecoveryService;

    @GetMapping("/list")
    public String sourceOfRecoveryList(Model model){
        List<SourceOfRecovery>sourceOfRecoveryList = sourceOfRecoveryService.findAll();
        model.addAttribute("sourceOfRecoveryList", sourceOfRecoveryList);
        return "samd/setup/sourceOfRecovery/list";
    }


    @GetMapping("/create")
    public String createSourceOfRecoveryView(Model model){
        return prepareEditViewModel(model, new SourceOfRecovery());
    }

    @PostMapping("/create")
    public String createSourceOfRecovery(Model model, @Valid SourceOfRecovery sourceOfRecovery, BindingResult result){
//        if (!result.hasErrors()){
//            sourceOfRecoveryService.save(sourceOfRecovery);
//            model.addAttribute("success", true);
//            return "redirect:/collection/samd/setup/source-of-recovery/list";
//        }
//        return prepareEditViewModel(model, sourceOfRecovery);

        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(sourceOfRecovery.getId()==null){
            sourceOfRecovery.setCreatedBy(user.getUsername());
            sourceOfRecovery.setCreatedDate(new Date());
            sourceOfRecoveryService.save(sourceOfRecovery);
            auditTrailService.saveCreatedData("Source of recovery",sourceOfRecovery);
        }
        else {
            SourceOfRecovery oldEntity = sourceOfRecoveryRepository.getOne(sourceOfRecovery.getId());
            SourceOfRecovery previouseEntity = new SourceOfRecovery();
            BeanUtils.copyProperties(oldEntity,previouseEntity);

            sourceOfRecovery.setModifiedBy(user.getUsername());
            sourceOfRecovery.setModifiedDate(new Date());
            sourceOfRecoveryRepository.save(sourceOfRecovery);
            auditTrailService.saveUpdatedData("Source of recovery",previouseEntity,sourceOfRecovery);
        }

        return "redirect:/collection/samd/setup/source-of-recovery/list";

    }

    @GetMapping("/edit")
    public String edit(Model model, @RequestParam Long id){
        SourceOfRecovery sourceOfRecovery = sourceOfRecoveryService.findSourceOfRecoveryById(id);
        return prepareEditViewModel(model, sourceOfRecovery);
    }

    private String prepareEditViewModel(Model model, SourceOfRecovery sourceOfRecovery) {
        model.addAttribute("sourceOfRecovery", sourceOfRecovery);
        return "samd/setup/sourceOfRecovery/create";
    }
}
