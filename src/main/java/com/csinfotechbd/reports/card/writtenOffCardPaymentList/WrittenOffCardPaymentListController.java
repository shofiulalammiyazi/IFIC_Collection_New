package com.csinfotechbd.reports.card.writtenOffCardPaymentList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/collection/report/card/writtenoffcardspaymentlist")
public class WrittenOffCardPaymentListController {
    @GetMapping("/view")
    public String view(Model model){
        return "collection/reports/card/writtenOffCardsPaymentList/view";
    }
}
