package com.unisoft.collection.samd.setup.borrowerbusinessstatus;


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
@RequestMapping("/collection/samd/setup/borrower-business-status")
public class BorrowerBusinessStatusController {
    @Autowired
    private BorrowerBusinessStatusService service ;

    @Autowired
    private BorrowerBusinessStatusRepository borrowerBusinessStatusRepository;

    @Autowired
    private AuditTrailService auditTrailService;

    @GetMapping(value="/view")
    public String view(Model model, @RequestParam(value = "id", required = true) String id){
        return "samd/setup/borrowerbusinessstatus/view";
    }

    @GetMapping(value="/create")
    public String create(){
        return "samd/setup/borrowerbusinessstatus/create";
    }

    @PostMapping(value = "/create")
    public String save(Model model, @Valid BorrowerBusinessStatus obj, BindingResult result) {
//        if (!result.hasErrors()) {
//            service.saveOrUpdate(obj);
//        } else {
//            return "samd/setup/borrowerbusinessstatus/create";
//        }
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(obj.getId()==null) {
            obj.setCreatedBy(user.getUsername());
            obj.setCreatedDate(new Date());
            service.saveOrUpdate(obj);
            auditTrailService.saveCreatedData("Borrower's Business Status", obj);
        }else {
            BorrowerBusinessStatus oldEntity = borrowerBusinessStatusRepository.getOne(obj.getId());
            BorrowerBusinessStatus previousEntity = new BorrowerBusinessStatus();
            BeanUtils.copyProperties(oldEntity, previousEntity);

            obj.setModifiedBy(user.getUsername());
            obj.setModifiedDate(new Date());
            borrowerBusinessStatusRepository.save(obj);
            auditTrailService.saveUpdatedData("Borrower's Business Status", previousEntity, obj);
        }

        return "redirect:/collection/samd/setup/borrower-business-status/list";
    }

    @GetMapping(value="/list")
    public String proposalList(Model model){
        List<BorrowerBusinessStatus> list=service.findAllBorrowerBusinessStatus();
        model.addAttribute("borrowerBusinessStatusList" ,list);
        return "samd/setup/borrowerbusinessstatus/list";
    }


    @GetMapping(value="/edit")
    public String edit(Model model, @RequestParam(value = "id", required = true) String id){
        model.addAttribute("borrowerBusinessStatus",service.findByid(id));
        return "samd/setup/borrowerbusinessstatus/create";
    }

    @GetMapping(value="/remove")
    public String remove( @RequestParam(value = "id", required = true) String id){
        service.remove(id);
        return "redirect:/collection/samd/setup/borrower-business-status/list";
    }



}
