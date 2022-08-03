package com.unisoft.CustomerSearch;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/customersearch/")
public class AdvancedCustomerSearchController {

    @GetMapping("loan")
    public String advancedCustomerSearchLoan(Model model) {
        model.addAttribute("searchloan", "show1");
        return "collection/search/advanceCustomerSearchLoan";
    }

    @GetMapping("card")
    public String advancedCustomerSearchCard(Model model) {
        model.addAttribute("searchcard", "show2");
        return "collection/search/advanceCustomerSearchCard";
    }
}
