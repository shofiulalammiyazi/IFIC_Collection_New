package com.csinfotechbd.CustomerSearch;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/individualaccountsearch/")
public class IndividualAccountSearchCardController {

    @GetMapping("card")
    public String accountSearchCard(Model model) {
        model.addAttribute("searchicard", "show");
        return "collection/search/individualcardsearch";
    }
}
