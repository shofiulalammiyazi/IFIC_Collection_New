package com.csinfotechbd.retail.loan.setup.parReleaseLoan;

/*
 * Created by Yasir Araphat on 25 April, 2021
 */

import com.csinfotechbd.collection.settings.dpdBucket.DPDBucketService;
import com.csinfotechbd.collection.settings.productType.ProductTypeService;
import com.csinfotechbd.user.User;
import com.csinfotechbd.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/retail/loan/setup/par-release-loan/")
public class ParReleaseLoanController {

    @Autowired
    private ParReleaseLoanService parReleaseLoanService;

    @Autowired
    private DPDBucketService dpdBucketService;


    @GetMapping(value = "list")
    public String create(Model model) {
        List<ParReleaseLoan> parReleaseLoanList = parReleaseLoanService.findAll();
        model.addAttribute("parLoanList",parReleaseLoanList);
        return "retail/loan/setup/parReleaseLoan/view";
    }

    @GetMapping(value = "/create")
    public String createNplLoan(Model model){
        return editPageModel(model, new ParReleaseLoan());
    }


    @PostMapping(value = "create")
    public String createSamParLoan(Model model, @ModelAttribute   ParReleaseLoan parReleaseLoan, BindingResult result){
        if (!result.hasErrors()){
            ParReleaseLoan loan = parReleaseLoanService.save(parReleaseLoan);
            model.addAttribute("success", true);
            return "redirect:/retail/loan/setup/par-release-loan/list";
        }
        model.addAttribute("error", true);
        return editPageModel(model, parReleaseLoan);
    }

    @GetMapping(value = "edit")
    public String edit(Model model, @RequestParam(value = "id") Long id){
        ParReleaseLoan parReleaseLoan = parReleaseLoanService.findParReleaseLoanById(id);
        return editPageModel(model, parReleaseLoan);
    }

    private String editPageModel(Model model, ParReleaseLoan parReleaseLoan) {
        model.addAttribute("dpdBucketList", dpdBucketService.getCustomActiveList());
        model.addAttribute("parReleaseLoan",parReleaseLoan);
        return "retail/loan/setup/parReleaseLoan/create";
    }

}
