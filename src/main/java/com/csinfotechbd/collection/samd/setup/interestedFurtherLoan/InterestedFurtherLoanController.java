package com.csinfotechbd.collection.samd.setup.interestedFurtherLoan;

import com.csinfotechbd.audittrail.AuditTrailService;
import com.csinfotechbd.user.UserPrincipal;
import org.bouncycastle.math.raw.Mod;
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
@RequestMapping("/collection/samd/setup/interested-further-Loan")
public class InterestedFurtherLoanController {

    @Autowired
    private InterestedFurtherLoanService interestedFurtherLoanService;

    @Autowired
    private InterestedFurtherLoanRepository interestedFurtherLoanRepository;

    @Autowired
    private AuditTrailService auditTrailService;


    @GetMapping("/list")
    public String showList(Model model){
        List<InterestedFurtherLoan>interestedFurtherLoans = interestedFurtherLoanService.findAllActive();
        model.addAttribute("interestFurtherLoanList", interestedFurtherLoans);
        return "samd/setup/interestedFurtherLoan/list";
    }

    @GetMapping("/create")
    public String create(Model model){
        return previewEditModel(model, new InterestedFurtherLoan());
    }


    @PostMapping("/create")
    public String create(Model model, @Valid InterestedFurtherLoan interestedFurtherLoan, BindingResult result){
//        if (!result.hasErrors()){
//            interestedFurtherLoanService.save(interestedFurtherLoan);
//            model.addAttribute("success", true);
//            return "redirect:/collection/samd/setup/interested-further-Loan/list";
//        }
//        return previewEditModel(model, interestedFurtherLoan);

        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(interestedFurtherLoan.getId()==null){
            interestedFurtherLoan.setCreatedBy(user.getUsername());
            interestedFurtherLoan.setCreatedDate(new Date());
            interestedFurtherLoanService.save(interestedFurtherLoan);
            auditTrailService.saveCreatedData("Interest Further Loan", interestedFurtherLoan);
        }else{
            InterestedFurtherLoan oldEntity = interestedFurtherLoanRepository.getOne(interestedFurtherLoan.getId());
            InterestedFurtherLoan previousEntity = new InterestedFurtherLoan();
            BeanUtils.copyProperties(oldEntity,previousEntity);

            interestedFurtherLoan.setModifiedBy(user.getUsername());
            interestedFurtherLoan.setModifiedDate(new Date());
            interestedFurtherLoanRepository.save(interestedFurtherLoan);
            auditTrailService.saveUpdatedData("Interest Further Loan",previousEntity,interestedFurtherLoan);
        }

        return "redirect:/collection/samd/setup/interested-further-Loan/list";
    }


    @GetMapping("/edit")
    public String edit(Model model, @RequestParam Long id){
        InterestedFurtherLoan interestedFurtherLoan = interestedFurtherLoanService.findInterestedFurtherLoanById(id);
        return previewEditModel(model, interestedFurtherLoan);
    }

    private String previewEditModel(Model model, InterestedFurtherLoan interestedFurtherLoan) {
        model.addAttribute("interestedFurtherLoan", interestedFurtherLoan);
        return "samd/setup/interestedFurtherLoan/create";
    }
}
