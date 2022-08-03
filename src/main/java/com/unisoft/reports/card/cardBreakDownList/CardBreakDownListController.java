package com.unisoft.reports.card.cardBreakDownList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/collection/report/card/cardbreakdownlist")
public class CardBreakDownListController {
    @GetMapping("view")
    public String view(Model model){
        return "collection/reports/card/cardBreakDownList/view";
    }
}
