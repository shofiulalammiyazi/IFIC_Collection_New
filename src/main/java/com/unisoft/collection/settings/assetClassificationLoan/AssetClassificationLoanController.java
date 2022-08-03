package com.unisoft.collection.settings.assetClassificationLoan;

import com.unisoft.audittrail.AuditTrailService;
import com.unisoft.collection.settings.assetMainClassificationLoan.LoanMainClassification;
import com.unisoft.collection.settings.assetMainClassificationLoan.LoanMainClassificationDto;
import com.unisoft.collection.settings.assetMainClassificationLoan.LoanMainClassificationService;
import com.unisoft.collection.settings.dpdBucket.DPDBucketEntity;
import com.unisoft.collection.settings.dpdBucket.DPDBucketService;
import com.unisoft.collection.settings.productType.ProductTypeEntity;
import com.unisoft.collection.settings.productType.ProductTypeService;
import com.unisoft.user.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/collection/asset_classification_loan/")
public class AssetClassificationLoanController {

    private final AssetClassificationLoanService assetClassificationLoanService;
    private final LoanMainClassificationService loanMainClassificationService;
    private final DPDBucketService dpdBucketService;
    private final ProductTypeService productTypeService;
    private final AuditTrailService auditTrailService;

    @GetMapping("list")
    public String viewAll(Model model) {
        model.addAttribute("assetList", assetClassificationLoanService.getAll());
        return "collection/settings/assetClassification/assetClass";
    }

    @GetMapping("create")
    public String addPage(Model model) {
        List<String> assetClasstypes = Arrays.stream(AssetClassTypes.values())
                .map(AssetClassTypes::name)
                .collect(Collectors.toList());
        List<LoanMainClassification> assetClassificationLoanEntities = loanMainClassificationService.getAll();

        List<LoanMainClassificationDto> assetClassificationDropDownDtos = assetClassificationLoanEntities.stream()
                .map(LoanMainClassificationDto::new).collect(Collectors.toList());

        model.addAttribute("assetClasstypes", assetClassificationDropDownDtos);
        model.addAttribute("allowedBucketIdList", new Long[0]);
        model.addAttribute("allowedProdIdList", new Long[0]);
        model.addAttribute("dpdBucketList", dpdBucketService.getCustomActiveList());
        model.addAttribute("prodTypeList", productTypeService.getAllActive());
        model.addAttribute("asset", new AssetClassificationLoanEntity());
        return "collection/settings/assetClassification/create";
    }


    @PostMapping("create")
    public String create(AssetClassificationLoanEntity assetClassification,
                         @RequestParam("bucketIdList") List<Long> dpdBuckIdList,
                         @RequestParam("prodIdList") List<Long> prodTypeIdList) {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<DPDBucketEntity> bucketList = dpdBuckIdList.stream().map(DPDBucketEntity::new).collect(Collectors.toList());
        List<ProductTypeEntity> productList = prodTypeIdList.stream().map(ProductTypeEntity::new).collect(Collectors.toList());

        assetClassification.setBucketList(bucketList);
        assetClassification.setProductTypeList(productList);

        if (assetClassification.getId() == null) {
            assetClassification.setCreatedBy(user.getUsername());
            assetClassification.setCreatedDate(new Date());
            assetClassificationLoanService.saveNew(assetClassification);
            auditTrailService.saveCreatedData("Asset Classification - Loan", assetClassification);

        } else {
            AssetClassificationLoanEntity oldEntity = assetClassificationLoanService.getById(assetClassification.getId());
            AssetClassificationLoanEntity previousEntity = new AssetClassificationLoanEntity();
            BeanUtils.copyProperties(oldEntity, previousEntity);

            assetClassification.setModifiedBy(user.getUsername());
            assetClassification.setModifiedDate(new Date());
            assetClassificationLoanService.updateAsset(assetClassification);
            auditTrailService.saveUpdatedData("Asset Classification - Loan", previousEntity, assetClassification);
        }
        return "redirect:/collection/asset_classification_loan/list";
    }

    @GetMapping("edit")
    public String editPage(@RequestParam("id") Long id, Model model) {
        AssetClassificationLoanEntity assetClassification = assetClassificationLoanService.getById(id);

        List<Long> allowedBucketIdList = new ArrayList<>();
        List<Long> allowedProdIdList = new ArrayList<>();
        if (assetClassification != null){
            allowedBucketIdList = assetClassification.getBucketList().stream()
                    .map(DPDBucketEntity::getId).collect(Collectors.toList());
            allowedProdIdList = assetClassification.getProductTypeList().stream()
                    .map(ProductTypeEntity::getId).collect(Collectors.toList());
        }

        List<LoanMainClassification> assetClassificationLoanEntities = loanMainClassificationService.getAll();

        List<LoanMainClassificationDto> assetClassificationDropDownDtos = assetClassificationLoanEntities.stream()
                .map(LoanMainClassificationDto::new).collect(Collectors.toList());

        model.addAttribute("assetClasstypes", assetClassificationDropDownDtos);
        model.addAttribute("dpdBucketList", dpdBucketService.getCustomActiveList());
        model.addAttribute("prodTypeList", productTypeService.getAllActive());
        model.addAttribute("allowedBucketIdList", allowedBucketIdList);
        model.addAttribute("allowedProdIdList", allowedProdIdList);
        model.addAttribute("asset", assetClassificationLoanService.getById(id));
        return "collection/settings/assetClassification/create";
    }
}
