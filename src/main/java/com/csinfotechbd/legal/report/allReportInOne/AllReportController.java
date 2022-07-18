package com.csinfotechbd.legal.report.allReportInOne;


import com.csinfotechbd.collection.settings.branch.Branch;
import com.csinfotechbd.collection.settings.branch.BranchService;
import com.csinfotechbd.collection.settings.district.DistrictEntity;
import com.csinfotechbd.collection.settings.district.DistrictService;
import com.csinfotechbd.collection.settings.zone.ZoneEntity;
import com.csinfotechbd.collection.settings.zone.ZoneService;
import com.csinfotechbd.legal.dataEntry.caseEntry.LitigationCaseInfo;
import com.csinfotechbd.legal.dataEntry.caseEntry.LitigationCaseInfoService;
import com.csinfotechbd.legal.setup.caseFiledType.CaseFiledType;
import com.csinfotechbd.legal.setup.caseFiledType.CaseFiledTypeService;
import com.csinfotechbd.legal.setup.caseType.CaseType;
import com.csinfotechbd.legal.setup.caseType.CaseTypeService;
import com.csinfotechbd.legal.setup.lawyers.Lawyer;
import com.csinfotechbd.legal.setup.lawyers.LawyerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/legal/report/all-report-in-one")
public class AllReportController {

    @Autowired
    private BranchService branchService;
    @Autowired
    private CaseFiledTypeService caseFiledTypeService;
    @Autowired
    private CaseTypeService caseTypeService;
    @Autowired
    private LawyerService lawyerService;
    @Autowired
    private ZoneService zoneService;
    @Autowired
    private DistrictService districtService;
    @Autowired
    private LitigationCaseInfoService litigationCaseInfoService;


    @GetMapping("/view")
    public String viewPage(Model model){
        List<Branch> branchList = branchService.getList();
        List<CaseFiledType> caseFiledTypes = caseFiledTypeService.findAll();
        List<CaseType> caseTypes = caseTypeService.findAllCaseType();
        List<Lawyer>lawyerList = lawyerService.findAll();
        List<ZoneEntity> zoneEntities = zoneService.getAll();
        List<DistrictEntity> districtEntities = districtService.getAll();
        List<LitigationCaseInfo> litigationCaseInfos = litigationCaseInfoService.findAllLitigationCaseInfo();
        model.addAttribute("branchList",branchList);
        model.addAttribute("caseFiledTypes",caseFiledTypes);
        model.addAttribute("caseTypes",caseTypes);
        model.addAttribute("lawyerList",lawyerList);
        model.addAttribute("zoneEntities",zoneEntities);
        model.addAttribute("districtEntities",districtEntities);
        model.addAttribute("litigationCaseInfos",litigationCaseInfos);
        return "legal/report/reportAllInOne/allReportTab";
    }


    @GetMapping("/branch-list")
    @ResponseBody
    public List<Branch> geBranchList(){
        List<Branch> branchList = branchService.getList();
        return branchList;
    }
}
