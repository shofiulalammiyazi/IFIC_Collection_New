package com.unisoft.collection.samd.setup.samNplRelease.samNplReleaseLoan;


import com.unisoft.collection.settings.assetClassificationLoan.AssetClassificationLoanEntityDto;
import com.unisoft.collection.settings.assetClassificationLoan.AssetClassificationLoanService;
import com.unisoft.collection.settings.dpdBucket.DPDBucketEntityDto;
import com.unisoft.collection.settings.dpdBucket.DPDBucketService;
import com.unisoft.collection.settings.productType.ProductTypeDto;
import com.unisoft.collection.settings.productType.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/collection/samd/setup/sam-npl-release/sam-npl-release-loan")
public class SamNplReleaseLoanController {


    @Autowired
    private SamNplReleaseLoanService samNplReleaseLoanService;

    @Autowired
    private DPDBucketService dpdBucketService;
    @Autowired
    private AssetClassificationLoanService assetClassificationLoanService;
    @Autowired
    private ProductTypeService productTypeService;


    @GetMapping("/list")
    public String samNplReleaseLoan(Model model){
        List<SamNplReleaseLoan>samNplReleaseList = samNplReleaseLoanService.findAll();
        model.addAttribute("samNplReleaseList", samNplReleaseList);
        return "samd/setup/samNplRelease/samNplReleaseLoan/samNplReleaseList";
    }


    @GetMapping("/create")
    public String createSamNplLoanView(Model model){
        return prepareEditPageModel(model, new SamNplReleaseLoan());
    }

    @PostMapping("/create")
    public String createSamNplLoan(Model model, @Valid SamNplReleaseLoan samNplReleaseLoan, BindingResult result){
        if (!result.hasErrors()){
            SamNplReleaseLoan loan = samNplReleaseLoanService.save(samNplReleaseLoan);
            model.addAttribute("success", true);
            return "redirect:/collection/samd/setup/sam-npl-release/sam-npl-release-loan/list";
        }
        model.addAttribute("error", true);
        return prepareEditPageModel(model, samNplReleaseLoan);

    }


    @GetMapping("/edit")
    public String editSamdNplRelease(Model model, @RequestParam(value = "id") Long id){
        SamNplReleaseLoan samNplReleaseLoan = samNplReleaseLoanService.findSamNplReleaseLoanById(id);
        return prepareEditPageModel(model, samNplReleaseLoan);
    }


    private String prepareEditPageModel(Model model, SamNplReleaseLoan samNplReleaseLoan){

        List<DPDBucketEntityDto>selectedDpdBucket = samNplReleaseLoan.getDpdBucketEntities().stream().map(DPDBucketEntityDto::new).collect(Collectors.toList());
        List<AssetClassificationLoanEntityDto> selectedAssetClassificationLoanEntities = samNplReleaseLoan.getAssetClassificationLoanEntities().stream().map(AssetClassificationLoanEntityDto::new).collect(Collectors.toList());
        List<ProductTypeDto>selectedProductTypes = samNplReleaseLoan.getProductTypeEntities().stream().map(ProductTypeDto::new).collect(Collectors.toList());

        model.addAttribute("dpdBucketEntities",dpdBucketService.getAll());
        model.addAttribute("assetClassificationLoanEntities",assetClassificationLoanService.getAll());
        model.addAttribute("productTypeEntities",productTypeService.getAllActive());
        model.addAttribute("samNplReleaseLoan",samNplReleaseLoan);
        model.addAttribute("selectedDpdBucket",selectedDpdBucket);
        model.addAttribute("selectedAssetClassificationLoanEntities",selectedAssetClassificationLoanEntities);
        model.addAttribute("selectedProductTypes",selectedProductTypes);
        return "samd/setup/samNplRelease/samNplReleaseLoan/create";
    }

}
