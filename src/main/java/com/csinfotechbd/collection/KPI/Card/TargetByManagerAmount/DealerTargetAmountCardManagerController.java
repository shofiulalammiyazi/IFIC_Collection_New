package com.csinfotechbd.collection.KPI.Card.TargetByManagerAmount;


import com.csinfotechbd.collection.settings.ageCode.AgeCodeEntity;
import com.csinfotechbd.collection.settings.ageCode.AgeCodeService;
import com.csinfotechbd.collection.settings.employee.EmployeeInfoEntity;
import com.csinfotechbd.collection.settings.employee.EmployeeService;
import com.csinfotechbd.collection.settings.location.LocationEntity;
import com.csinfotechbd.collection.settings.location.LocationService;
import com.csinfotechbd.collection.settings.productType.ProductTypeEntity;
import com.csinfotechbd.collection.settings.productType.ProductTypeRepository;
import com.csinfotechbd.collection.settings.productType.ProductTypeService;
import com.csinfotechbd.collection.settings.statuscdmanagement.StatusCdManagementService;
import com.csinfotechbd.user.UserPrincipal;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping(value = "/collection/dealer/card/bytarget/manager/amount/")
public class DealerTargetAmountCardManagerController {

    private DealerTargetAmountCardManagerRepository dealerTargetAmountCardManagerRepository;
    private ProductTypeService productTypeService;
    private LocationService locationService;
    private AgeCodeService ageCodeService;
    private EmployeeService employeeService;
    private StatusCdManagementService statusCdManagementService;
    private ProductTypeRepository productTypeRepository;

    @GetMapping(value = "list")
    public String viewAll(Model model) {
        model.addAttribute("agencyList",dealerTargetAmountCardManagerRepository.findAll());
        return "collection/kpi/card/targetByManagerAmount/list";
    }

    @GetMapping(value = "create")
    public String addpage(Model model) {
        Gson gson = new Gson();
        Gson gson1 = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        List<String> samList = new ArrayList<String>() {{ add("Yes");add("No"); }};
        model.addAttribute("samValues", samList);
        model.addAttribute("productTypeList", gson.toJson(productTypeRepository.findByProductGroupEntityCardAccount("Card")));
        model.addAttribute("locationList", gson.toJson(locationService.getLocList()));
        model.addAttribute("employeeList", gson1.toJson(employeeService.getDealerList()));
        model.addAttribute("ageCodeList", gson.toJson(ageCodeService.getAll()));
        model.addAttribute("statusCdList", gson.toJson(statusCdManagementService.list()));
        model.addAttribute("agency",new DealerTargetAmountCardManager());
        return "collection/kpi/card/targetByManagerAmount/create";
    }

    @PostMapping(value = "create")
    public String addnew(Model model, DealerTargetAmountCardManager agency) {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<LocationEntity> locationEntityList = new ArrayList<>();
        List<AgeCodeEntity> ageCodeEntityList= new ArrayList<>();
        List<EmployeeInfoEntity> employeeInfoEntityList = new ArrayList<>();
        List<ProductTypeEntity> productTypeEntities = new ArrayList<>();

        agency.getLocationIds().forEach(s -> {
            LocationEntity locationEntity = locationService.getById(Long.parseLong(s));
            locationEntityList.add(locationEntity);
        });

        agency.getAgeCodeIds().forEach(s -> {
            AgeCodeEntity ageCodeEntity= ageCodeService.getById(Long.parseLong(s));
            ageCodeEntityList.add(ageCodeEntity);
        });

        agency.getEmployeeIds().forEach(s -> {
            EmployeeInfoEntity employeeInfoEntity = employeeService.getById(Long.parseLong(s));
            employeeInfoEntityList.add(employeeInfoEntity);
        });

        agency.getProductTypeIds().forEach(s -> {
            ProductTypeEntity productTypeEntity = productTypeService.getById(Long.parseLong(s));
            productTypeEntities.add(productTypeEntity);
        });

        agency.setLocationEntityList(locationEntityList);
        agency.setAgeCodeEntityList(ageCodeEntityList);
        agency.setEmployeeInfoEntityList(employeeInfoEntityList);
        agency.setProductType(productTypeEntities);

        if(agency.getId()==null) {
            agency.setCreatedBy(user.getUsername());
            agency.setCreatedDate(new Date());
            dealerTargetAmountCardManagerRepository.save(agency);
        }else {
            agency.setModifiedBy(user.getUsername());
            agency.setModifiedDate(new Date());
            dealerTargetAmountCardManagerRepository.save(agency);
        }
        return "redirect:/collection/dealer/card/bytarget/manager/amount/list";
    }

    @GetMapping(value = "edit")
    public String editPage(@RequestParam(value = "id")Long id, Model model) {
        Gson gson = new Gson();
        Gson gson1 = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        DealerTargetAmountCardManager agencyKpiTargetAmountSetup = dealerTargetAmountCardManagerRepository.findById(id).get();

        model.addAttribute("locationList", gson.toJson(locationService.getLocList()));
        model.addAttribute("employeeList", gson1.toJson(employeeService.getDealerList()));
        model.addAttribute("ageCodeList", gson.toJson(ageCodeService.getAll()));
        model.addAttribute("productTypeList", gson.toJson(productTypeRepository.findByProductGroupEntityCardAccount("Card")));



        model.addAttribute("selectedLocationList", gson.toJson(agencyKpiTargetAmountSetup.getLocationEntityList()));
        model.addAttribute("selectedAgeCodeList", gson.toJson(agencyKpiTargetAmountSetup.getAgeCodeEntityList()));
        model.addAttribute("selectedEmployeeList", gson1.toJson(agencyKpiTargetAmountSetup.getEmployeeInfoEntityList()));
        model.addAttribute("selectedProuductTypeList", gson.toJson(agencyKpiTargetAmountSetup.getProductType()));


        model.addAttribute("agency", agencyKpiTargetAmountSetup);
        return "collection/kpi/card/targetByManagerAmount/create";
    }

    @GetMapping(value = "view")
    public String viewPage(@RequestParam(value = "id")Long id, Model model) {
        model.addAttribute("agency",dealerTargetAmountCardManagerRepository.findById(id).get());
        return "collection/kpi/card/targetByManagerAmount/view";
    }
}
