package com.unisoft.collection.samd.setup.borrowerstayingat;


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
@RequestMapping("/collection/samd/setup/borrower-staying-at")
public class BorrowerStayingController {
    @Autowired
    private BorrowerStayingService service ;

    @Autowired
    private BorrowerStayingRepository borrowerStayingRepository;

    @Autowired
    private AuditTrailService auditTrailService;

    @GetMapping(value="/view")
    public String view(Model model, @RequestParam(value = "id", required = true) String id){
        return "samd/setup/borrowerstayingat/view";
    }

    @GetMapping(value="/create")
    public String create(){
        return "samd/setup/borrowerstayingat/create";
    }

    @PostMapping(value = "/create")
    public String save(Model model, @Valid BorrowerStaying obj, BindingResult result) {
//        if (!result.hasErrors()) {
//            service.saveOrUpdate(obj);
//        } else {
//            return "samd/setup/borrowerstayingat/create";
//        }

        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(obj.getId()==null) {
            obj.setCreatedBy(user.getUsername());
            obj.setCreatedDate(new Date());
            service.saveOrUpdate(obj);
            auditTrailService.saveCreatedData("Borrower staying at", obj);
        }else {
            BorrowerStaying oldEntity = borrowerStayingRepository.getOne(obj.getId());
            BorrowerStaying previousEntity = new BorrowerStaying();
            BeanUtils.copyProperties(oldEntity, previousEntity);

            obj.setModifiedBy(user.getUsername());
            obj.setModifiedDate(new Date());
            borrowerStayingRepository.save(obj);
            auditTrailService.saveUpdatedData("Borrower staying at", previousEntity, obj);
        }

        return "redirect:/collection/samd/setup/borrower-staying-at/list";
    }

    @GetMapping(value="/list")
    public String proposalList(Model model){
        List<BorrowerStaying> list=service.findBorrowerStaingAll();
        model.addAttribute("borrowerStayingList" ,list);
        return "samd/setup/borrowerstayingat/list";
    }


    @GetMapping(value="/edit")
    public String edit(Model model, @RequestParam(value = "id", required = true) String id){
        model.addAttribute("borrowerStaying",service.findByid(id));
        return "samd/setup/borrowerstayingat/create";
    }

    @GetMapping(value="/remove")
    public String remove( @RequestParam(value = "id", required = true) String id){
        service.remove(id);
        return "redirect:/collection/samd/setup/borrower-staying-at/list";
    }



}
