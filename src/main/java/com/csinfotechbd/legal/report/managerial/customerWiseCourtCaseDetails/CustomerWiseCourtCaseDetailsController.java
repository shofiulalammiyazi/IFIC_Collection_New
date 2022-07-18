package com.csinfotechbd.legal.report.managerial.customerWiseCourtCaseDetails;


import com.csinfotechbd.collection.settings.branch.Branch;
import com.csinfotechbd.collection.settings.branch.BranchService;
import com.csinfotechbd.legal.setup.caseFiledType.CaseFiledType;
import com.csinfotechbd.legal.setup.caseFiledType.CaseFiledTypeService;
import com.csinfotechbd.legal.setup.caseType.CaseType;
import com.csinfotechbd.legal.setup.caseType.CaseTypeService;
import com.csinfotechbd.utillity.JasperReportManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/legal/report/managerial/customer-wise-court-case-details")
public class CustomerWiseCourtCaseDetailsController {

    private final CaseFiledTypeService caseFiledTypeService;
    private final CaseTypeService caseTypeService;
    private final BranchService branchService;
    private final CustomerWiseCourtCaseDetailsService service;
    private final JasperReportManager jasperReportManager;
    
    @Value("")
    private String jasperFile;
    
    @Value("Customer wise Court Case details")
    private String reportTitle;

    @GetMapping("/view")
    public String viewReport(Model model) {
        List<CaseFiledType> caseFiledTypes = caseFiledTypeService.findByEnabled(true);
        List<CaseType> caseTypes = caseTypeService.findAllCaseType();
        List<Branch> branches = branchService.getActiveList();
        model.addAttribute("reportTitle", "Customer wise Court Case details");
        model.addAttribute("caseFiledTypes", caseFiledTypes);
        model.addAttribute("caseTypes", caseTypes);
        model.addAttribute("branches", branches);
        return "legal/report/managerial/customerWiseCourtCaseDetails/view";
    }

    @GetMapping("/report")
    @ResponseBody
    public List<CustomerWiseCourtCaseDetailsDto> getCustomerWiseCourtCaseDetails(String accountName, Long[] caseFiledTypes, Long[] caseTypes, String[] branches) {
        accountName = accountName.replace("&amp;", "&");
        return service.getCustomerWiseCaseList(accountName, Arrays.asList(caseFiledTypes), Arrays.asList(caseTypes), Arrays.asList(branches));
    }

//    @GetMapping("/view")
//    public String viewReport(Model model) {
//
//        List<CaseFiledType> caseFiledTypes = caseFiledTypeService.findByEnabled(true);
//        List<CaseType> caseTypes = caseTypeService.findAllCaseType();
//        List<Branch> branches = branchService.getActiveList();
//        model.addAttribute("reportTitle", "Customer wise Court Case details");
//        model.addAttribute("accountInfos", service.getAccountInfos());
//        model.addAttribute("caseFiledTypes", caseFiledTypes);
//        model.addAttribute("caseTypes", caseTypes);
//        model.addAttribute("branches", branches);
//        return "legal/report/managerial/customerWiseCourtCaseDetails/view";
//    }
//
//    @GetMapping("/report")
//    @ResponseBody
//    public List<CustomerWiseCourtCaseDetailsDto> getCustomerWiseCourtCaseDetails(String accountNo, Long[] caseFiledTypes, Long[] caseTypes, String[] branches) {
//        return service.getCustomerWiseCaseList(accountNo, Arrays.asList(caseFiledTypes), Arrays.asList(caseTypes), Arrays.asList(branches));
//    }

    @GetMapping(value = "/report/excel")
    public void generateExcelReport(HttpServletResponse response, @RequestParam(value = "accountNo") String accountName,
                                    @RequestParam(value = "caseFiledTypes") List<Long> caseFileTypes,@RequestParam(value = "caseTypes") List<Long> caseTypes,
                                    @RequestParam(value = "branches") List<String> branches){
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("accountName",accountName);
        parameters.put("caseFileTypes",caseFileTypes);
        parameters.put("branches",branches);
        parameters.put("caseTypes",caseTypes);
        jasperReportManager.exportToExcel(response,parameters,reportTitle,jasperFile);
    }
}
