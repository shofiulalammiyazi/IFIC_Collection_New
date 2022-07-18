package com.csinfotechbd.legal.report.datasheets.accountWiseCaseReportUnderSupremeCourt;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/collection/report/legal/account-wise-case-report-under-supreme-court")
public class AccountWiseCaseReportUnderSupremeCourtController {

    @Autowired
    private AccountWiseCaseReportUnderSupremeCourtService service;

    @RequestMapping("/view")
    public String view(Model model) {
        model.addAttribute("reportTitle", "Account Wise Case Report Under The Supreme Court of Bangladesh");
        return "legal/report/accountWiseCaseReportUnderSupremeCourtReport/view";
    }

    @RequestMapping("/report")
    @ResponseBody
    public JSONObject getReportData() {

        JSONObject jsonObject = new JSONObject();

        List<AccountWiseCaseReportUnderSupremeCourtDto> reportData = service.searchData();
//        Object[][] data = service.generateReport();

        jsonObject.put("data", reportData);
//        jsonObject.put("data", data);

        return jsonObject;
    }
}
