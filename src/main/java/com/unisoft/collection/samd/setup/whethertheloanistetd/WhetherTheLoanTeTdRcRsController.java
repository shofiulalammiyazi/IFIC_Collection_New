package com.unisoft.collection.samd.setup.whethertheloanistetd;


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
@RequestMapping("/collection/samd/setup/whether-the-loan/te-td-r-c-rs")
public class WhetherTheLoanTeTdRcRsController {
    @Autowired
    private WhetherTheLoanTeTdRcRsService service ;

    @Autowired
    private WhetherTheLoanTeTdRcRsRepository whetherTheLoanTeTdRcRsRepository;

    @Autowired
    private AuditTrailService auditTrailService;

    @GetMapping(value="/view")
    public String view(Model model, @RequestParam(value = "id", required = true) String id){
        return "samd/setup/whethertheloanistetd/view";
    }

    @GetMapping(value="/create")
    public String create(){
        return "samd/setup/whethertheloanistetd/create";
    }

    @PostMapping(value = "/create")
    public String save(Model model, @Valid WhetherTheLoanTeTdRcRs obj, BindingResult result) {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//        if (!result.hasErrors()) {
//            service.saveOrUpdate(obj);
//        } else {
//            return "samd/setup/whethertheloanistetd/create";
//        }

        if (obj.getId()==null) {
            obj.setCreatedBy(user.getUsername());
            obj.setCreatedDate(new Date());
            service.saveOrUpdate(obj);
            auditTrailService.saveCreatedData("Whether the Loan is TE/TD/R/C/RS", obj);
        }else {
            WhetherTheLoanTeTdRcRs oldEntity = whetherTheLoanTeTdRcRsRepository.getOne(obj.getId());
            WhetherTheLoanTeTdRcRs previousEntity = new WhetherTheLoanTeTdRcRs();
            BeanUtils.copyProperties(oldEntity, previousEntity);

            obj.setModifiedBy(user.getUsername());
            obj.setModifiedDate(new Date());
            whetherTheLoanTeTdRcRsRepository.save(obj);
            auditTrailService.saveUpdatedData("Whether the Loan is TE/TD/R/C/RS", previousEntity, obj);
        }

        return "redirect:/collection/samd/setup/whether-the-loan/te-td-r-c-rs/list";
    }

    @GetMapping(value="/list")
    public String proposalList(Model model){
        List<WhetherTheLoanTeTdRcRs> list=service.whetherTheLoanTeTdRcRsList();
        model.addAttribute("whetherTheLoanList" ,list);
        return "samd/setup/whethertheloanistetd/list";
    }


    @GetMapping(value="/edit")
    public String edit(Model model, @RequestParam(value = "id", required = true) String id){
        model.addAttribute("whetherTheLoan",service.findByid(id));
        return "samd/setup/whethertheloanistetd/create";
    }

    @GetMapping(value="/remove")
    public String remove( @RequestParam(value = "id", required = true) String id){
        service.remove(id);
        return "redirect:/collection/samd/setup/whether-the-loan/te-td-r-c-rs/list";
    }



}
