package com.unisoft.collection.samd.setup.wayForwardReduceFromNPL;


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
@RequestMapping("/collection/samd/setup/way-forward-reduce-from-npl")
public class WayForwardReduceFromNPLController {

    @Autowired
    private WayForwardReduceFromNPLService wayForwardReduceFromNPLService;

    @Autowired
    private AuditTrailService auditTrailService;

    @Autowired
    private WayForwardReduceFromNPLRepository wayForwardReduceFromNPLRepository;


    @GetMapping("/list")
    public String getList(Model model){
        List<WayForwardReduceFromNPL> wayForwardReduceFromNPLList = wayForwardReduceFromNPLService.findAll();
        model.addAttribute("wayForwardReduceFromNPLList", wayForwardReduceFromNPLList);
        return "samd/setup/wayForwardReduceFromNPL/list";
    }



    @GetMapping("/create")
    public String createWayForwardReduceFromNPLView(Model model){
        return prepareEditViewModel(model, new WayForwardReduceFromNPL());
    }



    @PostMapping("/create")
    public String createWayForwardReduceFromNPL(Model model, @Valid WayForwardReduceFromNPL wayForwardReduceFromNPL, BindingResult result){
//        if (!result.hasErrors()){
//            wayForwardReduceFromNPLService.save(wayForwardReduceFromNPL);
//            model.addAttribute("success", true);
//            return "redirect:/collection/samd/setup/way-forward-reduce-from-npl/list";
//        }
//        model.addAttribute("error", true);
//        return prepareEditViewModel(model, wayForwardReduceFromNPL);
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(wayForwardReduceFromNPL.getId()==null){
            wayForwardReduceFromNPL.setCreatedBy(user.getUsername());
            wayForwardReduceFromNPL.setCreatedDate(new Date());
            wayForwardReduceFromNPLService.save(wayForwardReduceFromNPL);
            auditTrailService.saveCreatedData("Way Forward to reduce from NPL",wayForwardReduceFromNPL);
        }
        else {
            WayForwardReduceFromNPL oldEntity = wayForwardReduceFromNPLRepository.getOne(wayForwardReduceFromNPL.getId());
            WayForwardReduceFromNPL previouseEntity = new WayForwardReduceFromNPL();
            BeanUtils.copyProperties(oldEntity,previouseEntity);

            wayForwardReduceFromNPL.setModifiedBy(user.getUsername());
            wayForwardReduceFromNPL.setModifiedDate(new Date());
            wayForwardReduceFromNPLRepository.save(wayForwardReduceFromNPL);
            auditTrailService.saveUpdatedData("Way Forward to reduce from NPL",previouseEntity,wayForwardReduceFromNPL);
        }
            return "redirect:/collection/samd/setup/way-forward-reduce-from-npl/list";

    }



    @GetMapping("/edit")
    public String edit(Model model, @RequestParam Long id){
        WayForwardReduceFromNPL wayForwardReduceFromNPL = wayForwardReduceFromNPLService.findWayForwardReduceFromNPLById(id);
        return prepareEditViewModel(model, wayForwardReduceFromNPL);
    }



    private String prepareEditViewModel(Model model, WayForwardReduceFromNPL wayForwardReduceFromNPL) {
     model.addAttribute("wayForwardReduceFromNPL", wayForwardReduceFromNPL);
     return "samd/setup/wayForwardReduceFromNPL/create";
    }
}
