package com.csinfotechbd.collection.settings.productType;
/*
Created by Monirul Islam at 6/26/2019 
*/


import com.csinfotechbd.collection.settings.productGroup.ProductGroupService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/collection/product_type/")
public class ProductTypeController {

    private final ProductTypeService productTypeService;
//    private final ProductGroupService productGroupService;

    @GetMapping(value = "list")
    public String viewAll(Model model) {
        model.addAttribute("prTypeList", productTypeService.getTypeList());
        return "collection/settings/productType/product_type";
    }

    @GetMapping(value = "create")
    public String addPage(Model model) {
        model.addAttribute("productGroup", productTypeService.getPrductGroupLoanList());
        model.addAttribute("productType", new ProductTypeEntity());
        return "collection/settings/productType/create";
    }

    @PostMapping(value = "create")
    public String create(Model model, ProductTypeEntity productType) {
        String output = productTypeService.save(productType);
        switch (output) {
            case "1":
                return "redirect:/collection/product_type/list";
            default:
                model.addAttribute("error", "Something went wrong");
        }
        model.addAttribute("productGroup", productTypeService.getPrductGroupLoanList());
        model.addAttribute("productType", productType);
        return "collection/settings/productType/create";
    }

    @GetMapping(value = "view")
    public String viewPage(@RequestParam(value = "id") Long Id, Model model) {
        model.addAttribute("productType", productTypeService.getById(Id));
        return "collection/settings/productType/view";
    }

    @GetMapping(value = "edit")
    public String editType(@RequestParam(value = "id") Long Id, Model model) {
        model.addAttribute("productGroup", productTypeService.getPrductGroupLoanList());
        model.addAttribute("productType", productTypeService.getById(Id));
        return "collection/settings/productType/create";
    }
}
