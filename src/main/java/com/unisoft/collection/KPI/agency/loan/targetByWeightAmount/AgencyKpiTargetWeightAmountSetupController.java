package com.unisoft.collection.KPI.agency.loan.targetByWeightAmount;
/*
Created by   Islam at 9/19/2019
*/

import com.unisoft.audittrail.AuditTrailService;
import com.unisoft.collection.settings.dpdBucket.DPDBucketEntity;
import com.unisoft.collection.settings.dpdBucket.DPDBucketService;
import com.unisoft.collection.settings.location.LocationEntity;
import com.unisoft.collection.settings.location.LocationService;
import com.unisoft.collection.settings.productType.ProductTypeEntity;
import com.unisoft.collection.settings.productType.ProductTypeRepository;
import com.unisoft.collection.settings.productType.ProductTypeService;
import com.unisoft.collection.settings.zone.ZoneEntity;
import com.unisoft.collection.settings.zone.ZoneService;
import com.unisoft.user.UserPrincipal;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
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
@RequestMapping(value = "/collection/kpi/agency/loan/bytarget/wight/amount/")
@AllArgsConstructor
public class AgencyKpiTargetWeightAmountSetupController {
    private AgencyKpiTargetWeightAmountSetupRepository agencyKpiTargetWeightAmountSetupRepository;
    private ProductTypeService productTypeService;
    private LocationService locationService;
    private ZoneService zoneService;
    private DPDBucketService dpdBucketService;
    private ProductTypeRepository productTypeRepository;
    private AuditTrailService auditTrailService;

    @GetMapping(value = "list")
    public String viewAll(Model model) {

        model.addAttribute("agencyList",agencyKpiTargetWeightAmountSetupRepository.findAll());
        return "collection/kpi/agency/loan/targetByAmountWeight/list";
    }

    @GetMapping(value = "create")
    public String addpage(Model model) {
        Gson gson = new Gson();
        model.addAttribute("productTypeList", gson.toJson(productTypeRepository.findByProductGroupEntityCardAccount("Loan")));
        model.addAttribute("locationList", gson.toJson(locationService.getLocList()));
        model.addAttribute("zoneList", gson.toJson(zoneService.getAll()));
        model.addAttribute("dpdBucketList", gson.toJson(dpdBucketService.getAll()));
        model.addAttribute("agency",new AgencyKpiTargetWeightAmountSetup());
        return "collection/kpi/agency/loan/targetByAmountWeight/create";
    }

    @PostMapping(value = "create")
    public String addnew(Model model, AgencyKpiTargetWeightAmountSetup agency) {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<ProductTypeEntity> productTypeEntityList = new ArrayList<>();
        List<LocationEntity> locationEntityList = new ArrayList<>();
        List<ZoneEntity> zoneEntityList = new ArrayList<>();
        List<DPDBucketEntity> dpdBucketEntityList= new ArrayList<>();

        agency.getProductIds().forEach(s -> {
            ProductTypeEntity productTypeEntity = productTypeService.getById(Long.parseLong(s));
            productTypeEntityList.add(productTypeEntity);
        });

        agency.getLocationIds().forEach(s -> {
            LocationEntity locationEntity = locationService.getById(Long.parseLong(s));
            locationEntityList.add(locationEntity);
        });

        agency.getZoneIds().forEach(s -> {
            ZoneEntity zoneEntity = zoneService.getById(Long.parseLong(s));
            zoneEntityList.add(zoneEntity);
        });

        agency.getDpdBucketIds().forEach(s -> {
            DPDBucketEntity dpdBucketEntity= dpdBucketService.getById(Long.parseLong(s));
            dpdBucketEntityList.add(dpdBucketEntity);
        });

        agency.setProductTypeEntityList(productTypeEntityList);
        agency.setLocationEntityList(locationEntityList);
        agency.setZoneEntityList(zoneEntityList);
        agency.setDpdBucketEntityList(dpdBucketEntityList);

        if(agency.getId()==null) {
            agency.setCreatedBy(user.getUsername());
            agency.setCreatedDate(new Date());
            agencyKpiTargetWeightAmountSetupRepository.save(agency);
            auditTrailService.saveCreatedData("Agency KPI Target Setup (Weight) based on Amount – Loan", agency);
        }else {
            AgencyKpiTargetWeightAmountSetup oldEntity = agencyKpiTargetWeightAmountSetupRepository.getOne(agency.getId());
            AgencyKpiTargetWeightAmountSetup previousEntity = new AgencyKpiTargetWeightAmountSetup();
            BeanUtils.copyProperties(oldEntity, previousEntity);

            agency.setModifiedBy(user.getUsername());
            agency.setModifiedDate(new Date());
            agencyKpiTargetWeightAmountSetupRepository.save(agency);
            auditTrailService.saveUpdatedData("Agency KPI Target Setup (Weight) based on Amount – Loan", previousEntity, agency);
        }
        return "redirect:/collection/kpi/agency/loan/bytarget/wight/amount/list";
    }

    @GetMapping(value = "edit")
    public String editPage(@RequestParam(value = "id")Long id, Model model) {
        Gson gson = new Gson();
        AgencyKpiTargetWeightAmountSetup agencyKpiTargetAmountSetup = agencyKpiTargetWeightAmountSetupRepository.findById(id).get();

        model.addAttribute("productTypeList", gson.toJson(productTypeRepository.findByProductGroupEntityCardAccount("Loan")));
        model.addAttribute("locationList", gson.toJson(locationService.getLocList()));
        model.addAttribute("zoneList", gson.toJson(zoneService.getAll()));
        model.addAttribute("dpdBucketList", gson.toJson(dpdBucketService.getAll()));

        model.addAttribute("selectedProuductTypeList", gson.toJson(agencyKpiTargetAmountSetup.getProductTypeEntityList()));
        model.addAttribute("selectedLocationList", gson.toJson(agencyKpiTargetAmountSetup.getLocationEntityList()));
        model.addAttribute("selectedZoneList", gson.toJson(agencyKpiTargetAmountSetup.getZoneEntityList()));
        model.addAttribute("selectedDpdBucketList", gson.toJson(agencyKpiTargetAmountSetup.getDpdBucketEntityList()));


        model.addAttribute("agency", agencyKpiTargetAmountSetup);
        return "collection/kpi/agency/loan/targetByAmountWeight/create";
    }

    @GetMapping(value = "view")
    public String viewPage(@RequestParam(value = "id")Long id, Model model) {
        model.addAttribute("agency",agencyKpiTargetWeightAmountSetupRepository.findById(id).get());
        return "collection/kpi/agency/loan/targetByAmountWeight/view";
    }
}
