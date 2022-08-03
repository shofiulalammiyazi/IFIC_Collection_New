package com.unisoft.reports.card.nonStarterCardDetails;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/collection/report/card/nonstartercarddetails")
public class NonStarterCardDetailsController {
    @GetMapping("/view")
    public String view(Model model){
        return "collection/reports/card/nonStarterCardDetails/view";
    }
}
