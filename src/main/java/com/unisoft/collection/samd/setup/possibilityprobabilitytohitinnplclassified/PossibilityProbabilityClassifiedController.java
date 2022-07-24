package com.unisoft.collection.samd.setup.possibilityprobabilitytohitinnplclassified;


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
@RequestMapping("/collection/samd/setup/possibility-probability-classified")
public class PossibilityProbabilityClassifiedController {
    @Autowired
    private PossibilityProbabilityClassifiedService service ;

    @Autowired
    private AuditTrailService auditTrailService;

    @Autowired
    private PossibilityProbabilityClassifiedRepository possibilityProbabilityClassifiedRepository;

    @GetMapping(value="/view")
    public String view(Model model, @RequestParam(value = "id", required = true) String id){
        return "samd/setup/possibilityprobabilitytohitinnplclassified/view";
    }

    @GetMapping(value="/create")
    public String create(){
        return "samd/setup/possibilityprobabilitytohitinnplclassified/create";
    }

    @PostMapping(value = "/create")
    public String save(Model model, @Valid PossibilityProbabilityClassified obj, BindingResult result) {
//        if (!result.hasErrors()) {
//            service.saveOrUpdate(obj);
//        } else {
//            return "samd/setup/possibilityprobabilitytohitinnplclassified/create";
//        }

        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(obj.getId()==null){
            obj.setCreatedBy(user.getUsername());
            obj.setCreatedDate(new Date());
            service.saveOrUpdate(obj);
            auditTrailService.saveCreatedData("Possibility/Probability to hit in NPL",obj);
        }
        else {
            PossibilityProbabilityClassified oldEntity = possibilityProbabilityClassifiedRepository.getOne(obj.getId());
            PossibilityProbabilityClassified previousEntity = new PossibilityProbabilityClassified();
            BeanUtils.copyProperties(oldEntity,previousEntity);

            obj.setModifiedBy(user.getUsername());
            obj.setModifiedDate(new Date());
            possibilityProbabilityClassifiedRepository.save(obj);
            auditTrailService.saveUpdatedData("Possibility/Probability to hit in NPL",previousEntity,obj);
        }

        return "redirect:/collection/samd/setup/possibility-probability-classified/list";
    }

    @GetMapping(value="/list")
    public String objectsList(Model model){
        List<PossibilityProbabilityClassified> list=service.objectList();
        model.addAttribute("possibilityProbabilityClassifiedList" ,list);
        return "samd/setup/possibilityprobabilitytohitinnplclassified/list";
    }


    @GetMapping(value="/edit")
    public String edit(Model model, @RequestParam(value = "id", required = true) String id){
        model.addAttribute("possibilityProbabilityClassified",service.findByid(id));
        return "samd/setup/possibilityprobabilitytohitinnplclassified/create";
    }

    @GetMapping(value="/remove")
    public String remove( @RequestParam(value = "id", required = true) String id){
        service.remove(id);
        return "redirect:/collection/samd/setup/possibility-probability-classified/list";
    }



}
