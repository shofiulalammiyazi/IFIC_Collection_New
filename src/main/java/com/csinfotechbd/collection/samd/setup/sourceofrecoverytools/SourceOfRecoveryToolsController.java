package com.csinfotechbd.collection.samd.setup.sourceofrecoverytools;


import com.csinfotechbd.audittrail.AuditTrailService;
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
@RequestMapping("/collection/samd/setup/source-of-recovery-tools")
public class SourceOfRecoveryToolsController {
    @Autowired
    private SourceOfRecoveryToolsService service ;

    @Autowired
    private SourceOfRecoveryToolsRepository sourceOfRecoveryToolsRepository;

    @Autowired
    private AuditTrailService auditTrailService;

    @GetMapping(value="/view")
    public String view(Model model, @RequestParam(value = "id", required = true) String id){
        return "samd/setup/sourceofrecoverytools/view";
    }

    @GetMapping(value="/create")
    public String create(){
        return "samd/setup/sourceofrecoverytools/create";
    }

    @PostMapping(value = "/create")
    public String save(Model model, @Valid SourceOfRecoveryTools obj, BindingResult result) {
//        if (!result.hasErrors()) {
//            service.saveOrUpdate(obj);
//        } else {
//            return "samd/setup/sourceofrecoverytools/create";
//        }
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(obj.getId()==null) {
            obj.setCreatedBy(user.getUsername());
            obj.setCreatedDate(new Date());
            sourceOfRecoveryToolsRepository.save(obj);
            auditTrailService.saveCreatedData("Source of recovery tools", obj);
        }else {
            SourceOfRecoveryTools oldEntity = sourceOfRecoveryToolsRepository.getOne(obj.getId());
            SourceOfRecoveryTools previousEntity = new SourceOfRecoveryTools();
            BeanUtils.copyProperties(oldEntity, previousEntity);

            obj.setModifiedBy(user.getUsername());
            obj.setModifiedDate(new Date());
            sourceOfRecoveryToolsRepository.save(obj);
            auditTrailService.saveUpdatedData("Source of recovery tools", previousEntity, obj);
        }

        return "redirect:/collection/samd/setup/source-of-recovery-tools/list";
    }

    @GetMapping(value="/list")
    public String proposalList(Model model){
        List<SourceOfRecoveryTools> list=service.findSourceOfRecoveryTools();
        model.addAttribute("sourceOfRecoveryList" ,list);
        return "samd/setup/sourceofrecoverytools/list";
    }


    @GetMapping(value="/edit")
    public String edit(Model model, @RequestParam(value = "id", required = true) String id){
        model.addAttribute("sourceOfRecovery",service.findByid(id));
        return "samd/setup/sourceofrecoverytools/create";
    }

    @GetMapping(value="/remove")
    public String remove( @RequestParam(value = "id", required = true) String id){
        service.remove(id);
        return "redirect:/collection/samd/setup/source-of-recovery-tools/list";
    }



}
