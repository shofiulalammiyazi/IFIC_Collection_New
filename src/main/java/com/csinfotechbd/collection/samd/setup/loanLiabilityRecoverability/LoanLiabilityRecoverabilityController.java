package com.csinfotechbd.collection.samd.setup.loanLiabilityRecoverability;


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
@RequestMapping("/collection/samd/setup/loan-liability-recoverability")
public class LoanLiabilityRecoverabilityController {


    @Autowired
    private LoanLiabilityRecoverabilityService loanLiabilityRecoverabilityService;

    @Autowired
    private LoanLiabilityRecoverabilityRepository loanLiabilityRecoverabilityRepository;

    @Autowired
    private AuditTrailService auditTrailService;



    @GetMapping("/list")
    public String  list(Model model){
        List<LoanLiabilityRecoverability> loanLiabilityRecoverabilityList = loanLiabilityRecoverabilityService.findAll();
        model.addAttribute("loanLiabilityRecoverabilityList", loanLiabilityRecoverabilityList);
        return "samd/setup/loanLiabilityRecoverability/list";
    }



    @GetMapping("/create")
    public String createLoanLiabilityRecoverabilityView(Model model){
        return prepareEditViewModel(model, new LoanLiabilityRecoverability());
    }

    @PostMapping("/create")
    public String createLoanLiabilityRecoverability(Model model, @Valid LoanLiabilityRecoverability loanLiabilityRecoverability, BindingResult result){
//        if (!result.hasErrors()){
//            loanLiabilityRecoverabilityService.save(loanLiabilityRecoverability);
//            model.addAttribute("success", true);
//            return "redirect:/collection/samd/setup/loan-liability-recoverability/list";
//        }
//
//        model.addAttribute("errro", true);
//        return prepareEditViewModel(model, loanLiabilityRecoverability);

        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(loanLiabilityRecoverability.getId()==null){
            loanLiabilityRecoverability.setCreatedBy(user.getUsername());
            loanLiabilityRecoverability.setCreatedDate(new Date());
            loanLiabilityRecoverabilityService.save(loanLiabilityRecoverability);
            auditTrailService.saveCreatedData("Loan Liability Recovery", loanLiabilityRecoverability);
        }else{
            LoanLiabilityRecoverability oldEntity = loanLiabilityRecoverabilityRepository.getOne(loanLiabilityRecoverability.getId());
            LoanLiabilityRecoverability previousEntity = new LoanLiabilityRecoverability();
            BeanUtils.copyProperties(oldEntity,previousEntity);

            loanLiabilityRecoverability.setModifiedBy(user.getUsername());
            loanLiabilityRecoverability.setModifiedDate(new Date());
            loanLiabilityRecoverabilityRepository.save(loanLiabilityRecoverability);
            auditTrailService.saveUpdatedData("Loan Liability Recovery",previousEntity,loanLiabilityRecoverability);
        }
        
        return "redirect:/collection/samd/setup/loan-liability-recoverability/list";

    }



    @GetMapping("/edit")
    public String edit(Model model, @RequestParam Long id){
        LoanLiabilityRecoverability loanLiabilityRecoverability = loanLiabilityRecoverabilityService.findLoanLiabilityRecoverabilityById(id);
        return prepareEditViewModel(model, loanLiabilityRecoverability);
    }



    private String prepareEditViewModel(Model model, LoanLiabilityRecoverability loanLiabilityRecoverability) {
        model.addAttribute("loanLiabilityRecoverability", loanLiabilityRecoverability);
        return "samd/setup/loanLiabilityRecoverability/create";
    }
}
