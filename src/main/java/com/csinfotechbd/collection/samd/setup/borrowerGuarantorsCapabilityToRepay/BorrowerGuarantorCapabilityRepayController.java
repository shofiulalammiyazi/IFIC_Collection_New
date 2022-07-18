package com.csinfotechbd.collection.samd.setup.borrowerGuarantorsCapabilityToRepay;


import com.csinfotechbd.audittrail.AuditTrailService;
import com.csinfotechbd.collection.samd.setup.borrowerguarantoravailability.BorrowerAndGuarantorAvailabilityRepository;
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
@RequestMapping("/collection/samd/setup/borrower-guarantor-capability-repay")
public class BorrowerGuarantorCapabilityRepayController {
    @Autowired
    private BorrowerGuarantorCapabilityRepayService service ;

    @Autowired
    private AuditTrailService auditTrailService;

    @Autowired
    private BorrowerGuarantorCapabilityRepayRepository borrowerGuarantorCapabilityRepayRepository;

    @GetMapping(value="/view")
    public String view(Model model, @RequestParam(value = "id", required = true) String id){
        return "samd/setup/borrowerguarantorscapabilitytorepay/view";
    }

    @GetMapping(value="/create")
    public String create(){
        return "samd/setup/borrowerguarantorscapabilitytorepay/create";
    }

    @PostMapping(value = "/create")
    public String save(Model model, @Valid BorrowerGuarantorCapabilityRepay obj, BindingResult result) {
//        if (!result.hasErrors()) {
//            service.saveOrUpdate(obj);
//        } else {
//            return "samd/setup/borrowerguarantorscapabilitytorepay/create";
//        }

        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(obj.getId()==null){
            obj.setCreatedBy(user.getUsername());
            obj.setCreatedDate(new Date());
            service.saveOrUpdate(obj);
            auditTrailService.saveCreatedData("Borrower & Guarantor's Capability to Repay",obj);
        }
        else {
            BorrowerGuarantorCapabilityRepay oldEntity = borrowerGuarantorCapabilityRepayRepository.getOne(obj.getId());
            BorrowerGuarantorCapabilityRepay previousEntity = new BorrowerGuarantorCapabilityRepay();
            BeanUtils.copyProperties(oldEntity,previousEntity);

            obj.setModifiedBy(user.getUsername());
            obj.setModifiedDate(new Date());
            borrowerGuarantorCapabilityRepayRepository.save(obj);
            auditTrailService.saveUpdatedData("Borrower & Guarantor's Capability to Repay",previousEntity,obj);
        }


        return "redirect:/collection/samd/setup/borrower-guarantor-capability-repay/list";
    }

    @GetMapping(value="/list")
    public String objectsList(Model model){
        List<BorrowerGuarantorCapabilityRepay> list=service.objectList();
        model.addAttribute("borrowerGuarantorsCapabilityRepayList" ,list);
        return "samd/setup/borrowerguarantorscapabilitytorepay/list";
    }


    @GetMapping(value="/edit")
    public String edit(Model model, @RequestParam(value = "id", required = true) String id){
        model.addAttribute("borrowerGuarantorsCapabilityRepay",service.findByid(id));
        return "samd/setup/borrowerguarantorscapabilitytorepay/create";
    }

    @GetMapping(value="/remove")
    public String remove( @RequestParam(value = "id", required = true) String id){
        service.remove(id);
        return "redirect:/collection/samd/setup/borrower-guarantor-capability-repay/list";
    }



}
