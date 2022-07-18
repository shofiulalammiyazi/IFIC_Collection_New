package com.csinfotechbd.legal.report.bengali;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping("/report/bengali")
public class BengaliReportController {


    @GetMapping("/view")
    public String view(Model model) {
        model.addAttribute("reportTitle", "Branch wise Legal Expenses");
        return "legal/report/bengali/mamlaView";
    }

    @GetMapping("/rit-view")
    public String ritview(Model model) {
        model.addAttribute("reportTitle", "ব্যাংক  , বীমা ও আর্থিক প্রতিষ্ঠানের মামলা  সংক্রান্ত   তথ্য প্রদান প্রসঙ্গে");
        return "legal/report/bengali/cibRitView";
    }
}
