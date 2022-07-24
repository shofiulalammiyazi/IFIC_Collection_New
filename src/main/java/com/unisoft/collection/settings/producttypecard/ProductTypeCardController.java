package com.unisoft.collection.settings.producttypecard;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/collection/product-type-card/")
public class ProductTypeCardController {

    private final ProductTypeCardService productTypeService;
//    private final ProductGroupService productGroupService;

    @GetMapping(value = "list")
    public String viewAll(Model model) {
        model.addAttribute("prTypeList", productTypeService.getTypeList());
        return "collection/settings/producttypecard/productTypeCard";
    }

    @GetMapping(value = "create")
    public String addPage(Model model) {
        model.addAttribute("productGroup", productTypeService.getPrductGroupCardList());
        model.addAttribute("productType", new ProductTypeCardEntity());
        return "collection/settings/producttypecard/create";
    }

    @PostMapping(value = "create")
    public String create(Model model, ProductTypeCardEntity productType) {
        String output = productTypeService.save(productType);
        switch (output) {
            case "1":
                return "redirect:/collection/product-type-card/list";
            default:
                model.addAttribute("error", "Something went wrong");
        }
        model.addAttribute("productGroup", productTypeService.getPrductGroupCardList());
        model.addAttribute("productType", productType);
        return "collection/settings/producttypecard/create";
    }

    @GetMapping(value = "view")
    public String viewPage(@RequestParam(value = "id") Long Id, Model model) {
        model.addAttribute("productType", productTypeService.getById(Id));
        return "collection/settings/producttypecard/view";
    }

    @GetMapping(value = "edit")
    public String editType(@RequestParam(value = "id") Long Id, Model model) {
        model.addAttribute("productGroup", productTypeService.getPrductGroupCardList());
        model.addAttribute("productType", productTypeService.getById(Id));
        return "collection/settings/producttypecard/create";
    }
}
