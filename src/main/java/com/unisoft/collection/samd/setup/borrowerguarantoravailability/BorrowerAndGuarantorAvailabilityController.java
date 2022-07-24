package com.unisoft.collection.samd.setup.borrowerguarantoravailability;


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
@RequestMapping("/collection/samd/setup/borrower-guarantor-availability")
public class BorrowerAndGuarantorAvailabilityController {
    @Autowired
    private BorrowerAndGuarantorAvailabilityService service ;

    @Autowired
    private BorrowerAndGuarantorAvailabilityRepository borrowerAndGuarantorAvailabilityRepository;

    @Autowired
    private AuditTrailService auditTrailService;

    @GetMapping(value="/view")
    public String view(Model model, @RequestParam(value = "id", required = true) String id){
        return "samd/setup/borrowerguarantoravailability/view";
    }

    @GetMapping(value="/create")
    public String create(){
        return "samd/setup/borrowerguarantoravailability/create";
    }

    @PostMapping(value = "/create")
    public String save(Model model, @Valid BorrowerAndGuarantorAvailability obj, BindingResult result) {
//        if (!result.hasErrors()) {
//            service.saveOrUpdate(obj);
//        } else {
//            return "samd/setup/borrowerguarantoravailability/create";
//        }

        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(obj.getId()==null){
            obj.setCreatedBy(user.getUsername());
            obj.setCreatedDate(new Date());
            service.saveOrUpdate(obj);
            auditTrailService.saveCreatedData("Borrower and guarantor available", obj);
        }else {
            BorrowerAndGuarantorAvailability oldEntity = borrowerAndGuarantorAvailabilityRepository.getOne(obj.getId());
            BorrowerAndGuarantorAvailability previousEntity = new BorrowerAndGuarantorAvailability();
            BeanUtils.copyProperties(oldEntity,previousEntity);

            obj.setModifiedBy(user.getUsername());
            obj.setModifiedDate(new Date());
            borrowerAndGuarantorAvailabilityRepository.save(obj);
            auditTrailService.saveUpdatedData("Borrower and guarantor available",previousEntity,obj);
        }

        return "redirect:/collection/samd/setup/borrower-guarantor-availability/list";
    }

    @GetMapping(value="/list")
    public String proposalList(Model model){
        List<BorrowerAndGuarantorAvailability> list=service.findAllBorrowerAndGuarantorAvailability();
        model.addAttribute("borrowerGuarantorAvailabilityList" ,list);
        return "samd/setup/borrowerguarantoravailability/list";
    }


    @GetMapping(value="/edit")
    public String edit(Model model, @RequestParam(value = "id", required = true) String id){
        model.addAttribute("borrowerGuarantorAvailability",service.findByid(id));
        return "samd/setup/borrowerguarantoravailability/create";
    }

    @GetMapping(value="/remove")
    public String remove( @RequestParam(value = "id", required = true) String id){
        service.remove(id);
        return "redirect:/collection/samd/setup/borrower-guarantor-availability/list";
    }



}
