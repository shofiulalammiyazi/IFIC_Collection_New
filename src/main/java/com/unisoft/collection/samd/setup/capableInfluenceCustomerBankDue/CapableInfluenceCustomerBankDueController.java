package com.unisoft.collection.samd.setup.capableInfluenceCustomerBankDue;


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
@RequestMapping("/collection/samd/setup/capable-influence-customer-bank-due")
public class CapableInfluenceCustomerBankDueController {


    @Autowired
    private CapableInfluenceCustomerBankDueService capableInfluenceCustomerBankDueService;

    @Autowired
    private AuditTrailService auditTrailService;

    @Autowired
    private CapableInfluenceCustomerBankDueRepository capableInfluenceCustomerBankDueRepository;



    @GetMapping("/list")
    public String  list(Model model){
        List<CapableInfluenceCustomerBankDue> capableInfluenceCustomerBankDueList = capableInfluenceCustomerBankDueService.findAll();
        model.addAttribute("capableInfluenceCustomerBankDueList", capableInfluenceCustomerBankDueList);
        return "samd/setup/capableInfluenceCustomerBankDue/list";
    }



    @GetMapping("/create")
    public String createCategoryForDelinquencyView(Model model){
        return prepareEditViewModel(model, new CapableInfluenceCustomerBankDue());
    }

    @PostMapping("/create")
    public String createCategoryForDelinquency(Model model, @Valid CapableInfluenceCustomerBankDue capableInfluenceCustomerBankDue, BindingResult result){
//        if (!result.hasErrors()){
//            capableInfluenceCustomerBankDueService.save(capableInfluenceCustomerBankDue);
//            model.addAttribute("success", true);
//            return "redirect:/collection/samd/setup/capable-influence-customer-bank-due/list";
//        }
//
//        model.addAttribute("errro", true);
//        return prepareEditViewModel(model, capableInfluenceCustomerBankDue);

        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(capableInfluenceCustomerBankDue.getId()==null){
            capableInfluenceCustomerBankDue.setCreatedBy(user.getUsername());
            capableInfluenceCustomerBankDue.setCreatedDate(new Date());
            capableInfluenceCustomerBankDueService.save(capableInfluenceCustomerBankDue);
            auditTrailService.saveCreatedData("Customer for making payment of bank due", capableInfluenceCustomerBankDue);
        }else{
            CapableInfluenceCustomerBankDue oldEntity = capableInfluenceCustomerBankDueRepository.getOne(capableInfluenceCustomerBankDue.getId());
            CapableInfluenceCustomerBankDue previouseEntity = new CapableInfluenceCustomerBankDue();
            BeanUtils.copyProperties(oldEntity,previouseEntity);

            capableInfluenceCustomerBankDue.setModifiedBy(user.getUsername());
            capableInfluenceCustomerBankDue.setModifiedDate(new Date());
            capableInfluenceCustomerBankDueRepository.save(capableInfluenceCustomerBankDue);
            auditTrailService.saveUpdatedData("Customer for making payment of bank due",previouseEntity,capableInfluenceCustomerBankDue);
        }
         return "redirect:/collection/samd/setup/capable-influence-customer-bank-due/list";

    }



    @GetMapping("/edit")
    public String edit(Model model, @RequestParam Long id){
        CapableInfluenceCustomerBankDue capableInfluenceCustomerBankDue = capableInfluenceCustomerBankDueService.findCapableInfluenceCustomerBankDueById(id);
        return prepareEditViewModel(model, capableInfluenceCustomerBankDue);
    }



    private String prepareEditViewModel(Model model, CapableInfluenceCustomerBankDue capableInfluenceCustomerBankDue) {
        model.addAttribute("capableInfluenceCustomerBankDue", capableInfluenceCustomerBankDue);
        return "samd/setup/capableInfluenceCustomerBankDue/create";
    }
}
