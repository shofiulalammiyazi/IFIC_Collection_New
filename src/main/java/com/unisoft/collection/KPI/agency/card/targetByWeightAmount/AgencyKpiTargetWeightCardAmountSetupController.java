package com.unisoft.collection.KPI.agency.card.targetByWeightAmount;


import com.unisoft.collection.settings.ageCode.AgeCodeEntity;
import com.unisoft.collection.settings.ageCode.AgeCodeService;
import com.unisoft.collection.settings.agency.AgencyEntity;
import com.unisoft.collection.settings.agency.AgencyService;
import com.unisoft.collection.settings.location.LocationEntity;
import com.unisoft.collection.settings.location.LocationService;
import com.unisoft.collection.settings.productType.ProductTypeEntity;
import com.unisoft.collection.settings.productType.ProductTypeRepository;
import com.unisoft.collection.settings.productType.ProductTypeService;
import com.unisoft.collection.settings.statuscdmanagement.StatusCd;
import com.unisoft.collection.settings.statuscdmanagement.StatusCdManagementService;
import com.unisoft.user.UserPrincipal;
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
@RequestMapping(value = "/collection/kpi/agency/card/bytarget/weight/amount")
public class AgencyKpiTargetWeightCardAmountSetupController {
    private AgencyKpiTargetWeightCardAmountSetupRepository agencyKpiTargetWeightCardAmountSetupRepository;
    private ProductTypeService productTypeService;
    private LocationService locationService;
    private AgeCodeService ageCodeService;
    private AgencyService agencyService;
    private StatusCdManagementService statusCdManagementService;
    private ProductTypeRepository productTypeRepository;

    @GetMapping(value = "list")
    public String viewAll(Model model) {
        model.addAttribute("agencyList",agencyKpiTargetWeightCardAmountSetupRepository.findAll());
        return "collection/kpi/agency/card/targetByAmountWeight/list";
    }

    @GetMapping(value = "create")
    public String addpage(Model model) {
        Gson gson = new Gson();
        Gson gson1 = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        List<String> samList = new ArrayList<String>() {{ add("Yes");add("No"); }};
        model.addAttribute("samValues", samList);
        model.addAttribute("productTypeList", gson.toJson(productTypeRepository.findByProductGroupEntityCardAccount("Card")));
        model.addAttribute("locationList", gson.toJson(locationService.getLocList()));
        model.addAttribute("agencyList", gson.toJson(agencyService.getActiveList()));
        model.addAttribute("ageCodeList", gson.toJson(ageCodeService.getAll()));
        model.addAttribute("statusCdList", gson.toJson(statusCdManagementService.list()));
        model.addAttribute("agency",new AgencyKpiTargetWeightCardAmountSetup());
        return "collection/kpi/agency/card/targetByAmountWeight/create";
    }

    @PostMapping(value = "create")
    public String addnew(Model model, AgencyKpiTargetWeightCardAmountSetup agency) {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<ProductTypeEntity> productTypeEntityList = new ArrayList<>();
        List<LocationEntity> locationEntityList = new ArrayList<>();
        List<AgeCodeEntity> ageCodeEntityList= new ArrayList<>();
        List<AgencyEntity> agencyEntityList = new ArrayList<>();
        List<StatusCd> statusCdList = new ArrayList<>();


        agency.getProductIds().forEach(s -> {
            ProductTypeEntity productTypeEntity = productTypeService.getById(Long.parseLong(s));
            productTypeEntityList.add(productTypeEntity);
        });

        agency.getLocationIds().forEach(s -> {
            LocationEntity locationEntity = locationService.getById(Long.parseLong(s));
            locationEntityList.add(locationEntity);
        });

        agency.getAgeCodeIds().forEach(s -> {
            AgeCodeEntity ageCodeEntity= ageCodeService.getById(Long.parseLong(s));
            ageCodeEntityList.add(ageCodeEntity);
        });

        agency.getAgencyIds().forEach(s -> {
            AgencyEntity agencyEntity = agencyService.getById(Long.parseLong(s));
            agencyEntityList.add(agencyEntity);
        });

        agency.getStatusCdIds().forEach(s -> {
            StatusCd statusCd = statusCdManagementService.findbyId(Long.parseLong(s));
            statusCdList.add(statusCd);
        });

        agency.setProductTypeEntityList(productTypeEntityList);
        agency.setLocationEntityList(locationEntityList);
        agency.setAgeCodeEntityList(ageCodeEntityList);
        agency.setAgencyEntityList(agencyEntityList);
        agency.setStatusCdList(statusCdList);

        if(agency.getId()==null) {
            agency.setCreatedBy(user.getUsername());
            agency.setCreatedDate(new Date());
            agencyKpiTargetWeightCardAmountSetupRepository.save(agency);
        }else {
            agency.setModifiedBy(user.getUsername());
            agency.setModifiedDate(new Date());
            agencyKpiTargetWeightCardAmountSetupRepository.save(agency);
        }
        return "redirect:/collection/kpi/agency/card/bytarget/weight/amount/list";
    }

    @GetMapping(value = "edit")
    public String editPage(@RequestParam(value = "id")Long id, Model model) {
        Gson gson = new Gson();
        Gson gson1 = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        AgencyKpiTargetWeightCardAmountSetup agencyKpiTargetAmountSetup = agencyKpiTargetWeightCardAmountSetupRepository.findById(id).get();

        List<String> samList = new ArrayList<String>() {{ add("Yes");add("No"); }};
        model.addAttribute("samValues", samList);
        model.addAttribute("productTypeList", gson.toJson(productTypeRepository.findByProductGroupEntityCardAccount("Card")));
        model.addAttribute("locationList", gson.toJson(locationService.getLocList()));
        model.addAttribute("agencyList", gson.toJson(agencyService.getActiveList()));
        model.addAttribute("ageCodeList", gson.toJson(ageCodeService.getAll()));
        model.addAttribute("statusCdList", gson.toJson(statusCdManagementService.list()));

        model.addAttribute("selectedProuductTypeList", gson.toJson(agencyKpiTargetAmountSetup.getProductTypeEntityList()));
        model.addAttribute("selectedLocationList", gson.toJson(agencyKpiTargetAmountSetup.getLocationEntityList()));
        model.addAttribute("selectedAgeCodeList", gson.toJson(agencyKpiTargetAmountSetup.getAgeCodeEntityList()));
        model.addAttribute("selectedAgencyList", gson.toJson(agencyKpiTargetAmountSetup.getAgencyEntityList()));
        model.addAttribute("selectedStatusCdList", gson.toJson(agencyKpiTargetAmountSetup.getStatusCdList()));


        model.addAttribute("agency", agencyKpiTargetAmountSetup);
        return "collection/kpi/agency/card/targetByAmountWeight/create";
    }

    @GetMapping(value = "view")
    public String viewPage(@RequestParam(value = "id")Long id, Model model) {
        model.addAttribute("agency",agencyKpiTargetWeightCardAmountSetupRepository.findById(id).get());
        return "collection/kpi/agency/card/targetByAmountWeight/view";
    }
}
