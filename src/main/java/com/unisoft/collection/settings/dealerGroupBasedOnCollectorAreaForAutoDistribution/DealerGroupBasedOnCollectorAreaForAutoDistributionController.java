package com.unisoft.collection.settings.dealerGroupBasedOnCollectorAreaForAutoDistribution;


import com.unisoft.collection.settings.ageCode.AgeCodeService;
import com.unisoft.collection.settings.dpdBucket.DPDBucketService;
import com.unisoft.collection.settings.employee.EmployeeInfoDto;
import com.unisoft.collection.settings.employee.EmployeeInfoEntity;
import com.unisoft.collection.settings.employee.EmployeeService;
import com.unisoft.collection.settings.location.LocationEntity;
import com.unisoft.collection.settings.location.LocationService;
import com.unisoft.collection.settings.productGroup.ProductGroupEntity;
import com.unisoft.collection.settings.productGroup.ProductGroupService;
import com.unisoft.collection.settings.productType.ProductTypeEntity;
import com.unisoft.collection.settings.productType.ProductTypeService;
import com.unisoft.collection.settings.unit.UnitEntity;
import com.unisoft.collection.settings.unit.UnitService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/collection/settings/dealer-group-based-on-collector-area-for-auto-distribution")
public class DealerGroupBasedOnCollectorAreaForAutoDistributionController {

    private final DPDBucketService dpdBucketService;
    private final AgeCodeService ageCodeService;
    private final UnitService unitService;
    private final EmployeeService employeeService;
    private final LocationService locationService;
    private final ProductGroupService productGroupService;
    private final ProductTypeService productTypeService;
    private final DealerGroupBasedOnCollectorAreaForAutoDistributionService service;

    @GetMapping("/list")
    public String collectorDistributionlist(Model model) {
        model.addAttribute("collectorDistributionList", service.findAllData());
        return "collection/settings/dealer_group_based_on_collector_area_for_auto_distribution/list";
    }


    @GetMapping("/create")
    public String crateGroupBasedDistributionPage(Model model) {
        return populateDateFormModel(model, new DealerGroupBasedOnCollectorAreaForAutoDistribution());
    }

    @PostMapping("/create")
    public String crateGroupBasedDistribution(Model model,
                                              @ModelAttribute("entity") @Valid DealerGroupBasedOnCollectorAreaForAutoDistribution entity,
                                              BindingResult result) {
        if (!result.hasErrors()) {
            Map<String, Object> messages = service.storeData(entity);
            boolean isSuccessfullySaved = (boolean) messages.getOrDefault("success", false);
            if (isSuccessfullySaved) {
                return "redirect:list";
            } else {
                model.addAttribute("errors", messages.get("errors"));
            }
        }
        return populateDateFormModel(model, entity);
    }

    @GetMapping("/edit")
    public String updatecollectorDistribution(@RequestParam(value = "id") Long id, Model model) {
        DealerGroupBasedOnCollectorAreaForAutoDistribution collectorDistributionEntity = service.findDataById(id);
        return populateDateFormModel(model, collectorDistributionEntity);
    }


    private String populateDateFormModel(Model model, DealerGroupBasedOnCollectorAreaForAutoDistribution entity) {

        DealerGroupBasedOnCollectorAreaForAutoDistributionDto entityModel = new DealerGroupBasedOnCollectorAreaForAutoDistributionDto(entity);

        List<EmployeeInfoEntity> employeeDealerList = employeeService.getDealerList().stream().filter(employee -> {
            String unit = employee.getUnit().toLowerCase();
            return unit.contains("loan") || unit.contains("card");
        }).collect(Collectors.toList());
        List<EmployeeInfoDto> dealerList = employeeDealerList
                .stream().map(EmployeeInfoDto::new).collect(Collectors.toList());

        List<LocationEntity> locationList = locationService.getActiveList();

        List<ProductGroupEntity> productGroupList = productGroupService.getAllActive();

        List<ProductTypeEntity> productTypeList = productTypeService.getAllActive();

        List<UnitEntity> unitEntityList = unitService.getByName("Loan", "Card");

        model.addAttribute("unitList", unitEntityList);
        model.addAttribute("dpdList", dpdBucketService.getActiveList());
        model.addAttribute("ageCodeList", ageCodeService.getActiveList());
        model.addAttribute("dealerList", dealerList);
        model.addAttribute("locationList", locationList);
        model.addAttribute("productGroupList", productGroupList);
        model.addAttribute("productTypeList", productTypeList);
        model.addAttribute("entity", entityModel);
        return "collection/settings/dealer_group_based_on_collector_area_for_auto_distribution/create";
    }
}
