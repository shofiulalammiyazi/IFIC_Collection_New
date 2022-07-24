package com.unisoft.collection.settings.dlgExceptionRulesLoan;


import com.unisoft.audittrail.AuditTrailService;
import com.unisoft.collection.settings.branch.Branch;
import com.unisoft.collection.settings.branch.BranchService;
import com.unisoft.collection.settings.productType.ProductTypeEntity;
import com.unisoft.collection.settings.productType.ProductTypeService;
import com.unisoft.collection.settings.vipStatus.VipStatus;
import com.unisoft.collection.settings.vipStatus.VipStatusService;
import com.unisoft.customerbasicinfo.CustomerBasicInfoEntity;
import com.unisoft.customerbasicinfo.CustomerBasicInfoService;
import com.unisoft.user.UserPrincipal;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/collection/settings/dlgexceptionrulesloan/")
public class DlgExceptionRulesLoanController {

    @Autowired
    private DlgExceptionRulesLoanService dlgExceptionRulesLoanService;
    @Autowired
    private ProductTypeService productTypeService;
    @Autowired
    private CustomerBasicInfoService customerBasicInfoService;
    @Autowired
    private BranchService branchService;

    @Autowired
    private AuditTrailService auditTrailService;

    @Autowired
    private VipStatusService vipStatusService;

    @GetMapping("create")
    public String addDlgExceptionRulesLoan(Model model) {
//        Gson gson = new Gson();
        DlgExceptionRulesLoanInfo dlgExceptionRulesLoanInfo = dlgExceptionRulesLoanService.getDlgExceptionRulesLoanInfo();
        if (dlgExceptionRulesLoanInfo.getCustomerCifNo() == null)
            dlgExceptionRulesLoanInfo.setCustomerCifNo("");
        model.addAttribute("selectedCifNumbers", dlgExceptionRulesLoanInfo.getCustomerCifNo().split(","));
        model.addAttribute("productTypeList", productTypeService.getAllActive());
        model.addAttribute("selectedProductTypeList", dlgExceptionRulesLoanInfo.getProductType());
        model.addAttribute("branches", branchService.getActiveList());
        model.addAttribute("selectedBranches", dlgExceptionRulesLoanInfo.getBranches());

        model.addAttribute("vipStatusList", vipStatusService.getActiveList());
        model.addAttribute("selectedVipStatus", dlgExceptionRulesLoanInfo.getVipStatuses());

        model.addAttribute("dlgerl", dlgExceptionRulesLoanInfo);
        return "collection/settings/dlgexceptionrulesloan/dlgexceptionrulesloan";
    }

    @PostMapping("create")
    public String addNewDiv(DlgExceptionRulesLoanInfo dlgExceptionRulesLoanInfo) {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<ProductTypeEntity> productTypeEntities = dlgExceptionRulesLoanInfo.getProductTypeIds().stream()
                .map(ProductTypeEntity::new).collect(Collectors.toList());

        List<Branch> branches = dlgExceptionRulesLoanInfo.getBranchIds().stream()
                .map(Branch::new).collect(Collectors.toList());

        List<VipStatus> vipStatuses = dlgExceptionRulesLoanInfo.getVipStatusIds().stream().map(VipStatus::new).collect(Collectors.toList());


        dlgExceptionRulesLoanInfo.setProductType(productTypeEntities);

        dlgExceptionRulesLoanInfo.setBranches(branches);
        dlgExceptionRulesLoanInfo.setVipStatuses(vipStatuses);

        if (dlgExceptionRulesLoanInfo.getId() == null) {
            dlgExceptionRulesLoanInfo.setCreatedBy(user.getUsername());
            dlgExceptionRulesLoanInfo.setCreatedDate(new Date());
            dlgExceptionRulesLoanService.saveDlgExceptionRulesLoan(dlgExceptionRulesLoanInfo);
            auditTrailService.saveCreatedData("Dunning Letter Generate Exception Rule – Loan", dlgExceptionRulesLoanInfo);
        } else {
            DlgExceptionRulesLoanInfo oldEntity = dlgExceptionRulesLoanService.getDlgExceptionRulesLoanInfo();
            DlgExceptionRulesLoanInfo previousEntity = new DlgExceptionRulesLoanInfo();
            BeanUtils.copyProperties(oldEntity, previousEntity);

            dlgExceptionRulesLoanInfo.setModifiedBy(user.getUsername());
            dlgExceptionRulesLoanInfo.setModifiedDate(new Date());
            dlgExceptionRulesLoanService.updateDlgExceptionRulesLoan(dlgExceptionRulesLoanInfo);
            auditTrailService.saveUpdatedData("Dunning Letter Generate Exception Rule – Loan", previousEntity, dlgExceptionRulesLoanInfo);
        }
        return "redirect:/collection/settings/dlgexceptionrulesloan/create";
    }

    //    TODO: Temporary api implementation for customer CIF numbers. Deactivate this method when UCBL API is abvailable.
    @ResponseBody
    @GetMapping("dummy-cif")
    public List<HashMap<String, String>> getDummyCifNumbers() {
        List<CustomerBasicInfoEntity> list = customerBasicInfoService.getCustomerBasicInfoList();
        List<HashMap<String, String>> cifNumbers = new ArrayList<>();
        HashMap<String, String> cif = null;
        for (CustomerBasicInfoEntity customer : list) {
            if (customer.getAccountNo() == null) continue;
            cif = new HashMap<>();
            cif.put("cif", customer.getAccountNo());
            cifNumbers.add(cif);
        }
        return cifNumbers;
    }

}
