package com.csinfotechbd.legal.report.datasheets.accountWiseCaseReportUnderAraEx;


import com.csinfotechbd.legal.setup.caseType.CaseTypeService;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
@RequiredArgsConstructor
@RequestMapping("/legal/report/account-wise-case")
public class AccountWiseCaseReportUnderAraExController {

    private final CaseTypeService caseTypeService;

    private final AccountWiseCaseReportUnderAraExService service;


    @GetMapping("/view")
    public String accountWiseCaseReportView(Model model) {
        model.addAttribute("reportTitle", "Account wise Case Report Under ARA-EX");
        return "legal/report/accountWiseCaseReport/view";
    }


    @GetMapping("/report")
    @ResponseBody
    public JSONObject getReport(String caseType) {

        List<AccountWiseCaseReportUnderAraExDto> data = service.getReport(caseType);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("data", data);

        return jsonObject;
    }
}
