package com.csinfotechbd.legal.dataEntry.caseEntry;

import com.csinfotechbd.collection.settings.branch.BranchService;
import com.csinfotechbd.collection.settings.employee.EmployeeInfoEntity;
import com.csinfotechbd.collection.settings.employee.EmployeeService;
import com.csinfotechbd.legal.dataEntry.blaAttendanceHistory.BLAAttendanceHistory;
import com.csinfotechbd.legal.setup.caseFiledType.CaseFiledType;
import com.csinfotechbd.legal.setup.caseFiledType.CaseFiledTypeDto;
import com.csinfotechbd.legal.setup.caseFiledType.CaseFiledTypeService;
import com.csinfotechbd.legal.setup.legalExpense.LegalExpenseEntity;
import com.csinfotechbd.legal.setup.legalExpense.LegalExpenseService;
import com.csinfotechbd.legal.setup.legalExpense.LegalExpenseRepository;
import com.csinfotechbd.legal.setup.legalExpense.LegalExpenseService;
import com.csinfotechbd.user.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/legal/data-entry/litigation-case-info")
public class LitigationCaseInfoController {

    private final LitigationCaseInfoService litigationCaseInfoService;
    private final CaseFiledTypeService caseFiledTypeService;
    private final LegalExpenseService legalExpenseRepository;

    private final EmployeeService employeeService;
    private final BranchService branchService;

    @GetMapping("list")
    public String list(Model model, HttpSession session) {

        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = user.getUsername();

        EmployeeInfoEntity getBranch = employeeService.getByPin(username);

        String branchCode = getBranch.getBranch() != null ? getBranch.getBranch().getBranchCode() : "";


        if (branchCode.equals("000")){
            List<LitigationCaseInfoDto> litigationCaseInfos = litigationCaseInfoService.findAllActiveHeadOffice();
            model.addAttribute("litigationCaseInfoList", litigationCaseInfos);
        }
        else {
            List<LitigationCaseInfoDto> litigationCaseInfos = litigationCaseInfoService.findActiveSuitsWithCommonColumns(branchCode);
            model.addAttribute("litigationCaseInfoList", litigationCaseInfos);
        }

        List errors = (List) session.getAttribute("errors");
        if (errors != null && !errors.isEmpty()) {
            model.addAttribute("errors", errors);
            session.removeAttribute("errors");
        }

        if (branchCode.equals("000")) {
            model.addAttribute("branchCode", branchCode);
        }else {
            model.addAttribute("branchCode", null);
        }

//        model.addAttribute("view", false);
        return "legal/dataEntry/litigationCaseInfo/list";
    }

    @GetMapping("/create")
    public String create(Model model) {
        return populateLitigationFormModelAndReturnPageSource(model, new LitigationCaseInfo(), false);
    }

    @GetMapping("edit")
    public String edit(@RequestParam("id") Long id, Model model) {
        LitigationCaseInfo caseInfo = litigationCaseInfoService.getLitigationCaseInfo(id);

        List<BLAAttendanceHistory> blaAttendanceHistoryList = caseInfo.getBlaAttendanceHistoryList();
       /* List<BLAAttendanceHistory> newList = new ArrayList<>();

        for (BLAAttendanceHistory item : blaAttendanceHistoryList) {
            Date date = new Date();
            if ( item.getNextDate() == null || item.getNextDate().after(date)){
                newList.add(item);
            }
        }*/

        caseInfo.setBlaAttendanceHistoryList(blaAttendanceHistoryList);

        return populateLitigationFormModelAndReturnPageSource(model, caseInfo, true);
    }

    private String populateLitigationFormModelAndReturnPageSource(Model model, LitigationCaseInfo caseInfo, boolean isEditPurpose) {
        List<CaseFiledType> caseFiledTypes = caseFiledTypeService.findByEnabled(true);

        List<LegalExpenseEntity> legalExpenseEntityList = legalExpenseRepository.findByEnabled(true);

        List<CaseFiledTypeDto> caseFiledTypeList = caseFiledTypes.stream()
                .map(CaseFiledTypeDto::new).collect(Collectors.toList());


        //caseInfo.getCollateralSecurityList().get(0).getId();

        model.addAttribute("legalExpense", legalExpenseEntityList);
        model.addAttribute("litigationCaseInfo", caseInfo);
        model.addAttribute("caseFiledTypeList", caseFiledTypeList);
        model.addAttribute("edit", isEditPurpose);
        return "legal/dataEntry/litigationCaseInfo/create";
    }

    @GetMapping("upload-excel")
    public String bulkUpload() {
        return "legal/dataEntry/litigationCaseInfo/upload";
    }

    @PostMapping("upload-excel")
    public String bulkUpload(@RequestParam("file") MultipartFile multipartFile, HttpSession session) {
        List errors = litigationCaseInfoService.saveCaseInfoFromExcel(multipartFile);
        session.setAttribute("errors", errors);
        return "redirect:list";
    }

    @GetMapping("view")
    public String view(@RequestParam(value = "id") Long id, Model model) {
        List<CaseFiledType> caseFiledTypes = caseFiledTypeService.findByEnabled(true);
        List<LegalExpenseEntity> legalExpenseEntityList = legalExpenseRepository.findByEnabled(true);
        List<CaseFiledTypeDto> caseFiledTypeList = caseFiledTypes.stream()
                .map(CaseFiledTypeDto::new).collect(Collectors.toList());

        model.addAttribute("litigationCaseInfo", litigationCaseInfoService.getLitigationCaseInfo(id));
        model.addAttribute("legalExpense", legalExpenseEntityList);
        model.addAttribute("caseFiledTypeList", caseFiledTypeList);

        model.addAttribute("view", true);

        return "legal/dataEntry/litigationCaseInfo/create";
    }

}
