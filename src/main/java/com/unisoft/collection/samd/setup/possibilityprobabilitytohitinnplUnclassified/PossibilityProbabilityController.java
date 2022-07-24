package com.unisoft.collection.samd.setup.possibilityprobabilitytohitinnplUnclassified;


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
@RequestMapping("/collection/samd/setup/possibility-probability")
public class PossibilityProbabilityController {
    @Autowired
    private PossibilityProbabilityService service ;

    @Autowired
    private AuditTrailService auditTrailService;

    @Autowired
    private PossibilityProbabilityRepository possibilityProbabilityRepository;

    @GetMapping(value="/view")
    public String view(Model model, @RequestParam(value = "id", required = true) String id){
        return "samd/setup/possibilityprobabilitytohitinnpl/view";
    }

    @GetMapping(value="/create")
    public String create(){
        return "samd/setup/possibilityprobabilitytohitinnpl/create";
    }

    @PostMapping(value = "/create")
    public String save(Model model, @Valid PossibilityProbability obj, BindingResult result) {
//        if (!result.hasErrors()) {
//            service.saveOrUpdate(obj);
//        } else {
//            return "samd/setup/possibilityprobabilitytohitinnpl/create";
//        }

        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(obj.getId()==null){
            obj.setCreatedBy(user.getUsername());
            obj.setCreatedDate(new Date());
            service.saveOrUpdate(obj);
            auditTrailService.saveCreatedData("Possibility/Probability to Hit in NPL Unclassified",obj);
        }
        else {
            PossibilityProbability oldEntity = possibilityProbabilityRepository.getOne(obj.getId());
            PossibilityProbability previousEntity = new PossibilityProbability();
            BeanUtils.copyProperties(oldEntity,previousEntity);

            obj.setModifiedBy(user.getUsername());
            obj.setModifiedDate(new Date());
            possibilityProbabilityRepository.save(obj);
            auditTrailService.saveUpdatedData("Possibility/Probability to Hit in NPL Unclassified",previousEntity,obj);
        }
        return "redirect:/collection/samd/setup/possibility-probability/list";
    }

    @GetMapping(value="/list")
    public String objectsList(Model model){
        List<PossibilityProbability> list=service.objectList();
        model.addAttribute("possibilityProbabilityList" ,list);
        return "samd/setup/possibilityprobabilitytohitinnpl/list";
    }


    @GetMapping(value="/edit")
    public String edit(Model model, @RequestParam(value = "id", required = true) String id){
        model.addAttribute("possibilityProbability",service.findByid(id));
        return "samd/setup/possibilityprobabilitytohitinnpl/create";
    }

    @GetMapping(value="/remove")
    public String remove( @RequestParam(value = "id", required = true) String id){
        service.remove(id);
        return "redirect:/collection/samd/setup/possibility-probability/list";
    }



}
