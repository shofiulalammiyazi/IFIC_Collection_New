package com.unisoft.retail.loan.setup.nplReleaseLoan;
/*
Created by   Islam on 7/10/2019

Modified by Yasir Araphat on 25 April, 2021
*/

import com.unisoft.collection.settings.dpdBucket.DPDBucketService;
import com.unisoft.collection.settings.productType.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/retail/loan/setup/npl-release-loan/")
public class NplReleaseLoanController {

    @Autowired
    private NplReleaseLoanService nplReleaseLoanService;

    @Autowired
    private DPDBucketService dpdBucketService;

    @Autowired
    private ProductTypeService productTypeService;

    @GetMapping(value = "list")
    public String create(Model model) {
        List<NplReleaseLoan> nplReleaseLoanList = nplReleaseLoanService.findAll();
        model.addAttribute("nplLoanList",nplReleaseLoanList);
        return "retail/loan/setup/nplReleaseLoan/list";
    }

    @GetMapping(value = "/create")
    public String createNplLoan(Model model){
        return editPageModel(model, new NplReleaseLoan());
    }


    @PostMapping(value = "create")
    public String createSamNplLoan(Model model, @ModelAttribute  NplReleaseLoan nplReleaseLoan, BindingResult result){
        if (!result.hasErrors()){
            NplReleaseLoan loan = nplReleaseLoanService.save(nplReleaseLoan);
            model.addAttribute("success", true);
            return "redirect:/retail/loan/setup/npl-release-loan/list";
        }
        model.addAttribute("error", true);
        return editPageModel(model, nplReleaseLoan);
    }

    @GetMapping(value = "edit")
    public String edit(Model model, @RequestParam(value = "id") Long id){
        NplReleaseLoan nplReleaseLoan = nplReleaseLoanService.findNplReleaseLoanById(id);
        return editPageModel(model, nplReleaseLoan);
    }

    private String editPageModel(Model model, NplReleaseLoan nplReleaseLoan) {
        model.addAttribute("dpdBucketList", dpdBucketService.getCustomActiveList());
        model.addAttribute("productTypeList", productTypeService.getAllActive());
        model.addAttribute("nplReleaseLoan", nplReleaseLoan);
        return "retail/loan/setup/nplReleaseLoan/create";
    }
}
