package com.unisoft.collection.KPI.agency.loan.targetByCommision;
/*
Created by   Islam at 9/19/2019
*/

import com.unisoft.audittrail.AuditTrailService;
import com.unisoft.collection.settings.assetClassificationLoan.AssetClassificationLoanEntity;
import com.unisoft.collection.settings.assetClassificationLoan.AssetClassificationLoanService;
import com.unisoft.collection.settings.dpdBucket.DPDBucketEntity;
import com.unisoft.collection.settings.dpdBucket.DPDBucketService;
import com.unisoft.collection.settings.location.LocationEntity;
import com.unisoft.collection.settings.location.LocationService;
import com.unisoft.collection.settings.productType.ProductTypeEntity;
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
@AllArgsConstructor
@RequestMapping(value = "/collection/kpi/agency/loan/bycommision/")
public class AgencyKpiCommisionRateLoanSetupController {
    private AgencyKpiCommisionRateLoanSetupRepository agencyKpiCommisionRateLoanSetupRepository;
    private LocationService locationService;
    private ZoneService zoneService;
    private DPDBucketService dpdBucketService;
    private AuditTrailService auditTrailService;
    private ProductTypeService productTypeService;
    private AssetClassificationLoanService assetClassificationLoanService;

    @GetMapping(value = "list")
    public String viewAll(Model model) {

        model.addAttribute("agencyList",agencyKpiCommisionRateLoanSetupRepository.findAll());
        return "collection/kpi/agency/loan/targetByCommision/list";
    }

    @GetMapping(value = "create")
    public String addpage(Model model) {
        Gson gson = new Gson();
        model.addAttribute("locationList", gson.toJson(locationService.getLocList()));
        model.addAttribute("zoneList", gson.toJson(zoneService.getAll()));
        model.addAttribute("dpdBucketList", gson.toJson(dpdBucketService.getAll()));
        model.addAttribute("productList", gson.toJson(productTypeService.getAllActive()));
        model.addAttribute("clStatusList", gson.toJson(assetClassificationLoanService.getAll()));
        model.addAttribute("agency",new AgencyKpiCommisionRateLoanSetup());
        return "collection/kpi/agency/loan/targetByCommision/create";
    }

    @PostMapping(value = "create")
    public String addnew(Model model, AgencyKpiCommisionRateLoanSetup agency) {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<LocationEntity> locationEntityList = new ArrayList<>();
        List<ZoneEntity> zoneEntityList = new ArrayList<>();
        List<DPDBucketEntity> dpdBucketEntityList= new ArrayList<>();

        List<ProductTypeEntity> productTypeEntityList = new ArrayList<>();
        List<AssetClassificationLoanEntity>clStatusList = new ArrayList<>();


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


        agency.getProductTypeEntityIds().forEach(s -> {
            ProductTypeEntity productTypeEntity = productTypeService.getById(Long.parseLong(s));
            productTypeEntityList.add(productTypeEntity);
        });

        agency.getClStatusIds().forEach(s -> {
            AssetClassificationLoanEntity assetClassificationLoanEntity = assetClassificationLoanService.getById(Long.parseLong(s));
            clStatusList.add(assetClassificationLoanEntity);
        });

        agency.setLocationEntityList(locationEntityList);
        agency.setZoneEntityList(zoneEntityList);
        agency.setDpdBucketEntityList(dpdBucketEntityList);

        agency.setProductTypeEntityList(productTypeEntityList);
        agency.setAssetClassificationLoanEntityList(clStatusList);

        if(agency.getId()==null) {
            agency.setCreatedBy(user.getUsername());
            agency.setCreatedDate(new Date());
            agencyKpiCommisionRateLoanSetupRepository.save(agency);
            auditTrailService.saveCreatedData("Agency Commission Rate- Loan", agency);
        }else {
            AgencyKpiCommisionRateLoanSetup oldEntity = agencyKpiCommisionRateLoanSetupRepository.getOne(agency.getId());
            AgencyKpiCommisionRateLoanSetup previousEntity = new AgencyKpiCommisionRateLoanSetup();
            BeanUtils.copyProperties(oldEntity, previousEntity);

            agency.setModifiedBy(user.getUsername());
            agency.setModifiedDate(new Date());
            agencyKpiCommisionRateLoanSetupRepository.save(agency);
            auditTrailService.saveUpdatedData("Agency Commission Rate- Loan", previousEntity, agency);
        }
        return "redirect:/collection/kpi/agency/loan/bycommision/list";
    }

    @GetMapping(value = "edit")
    public String editPage(@RequestParam(value = "id")Long id, Model model) {
        Gson gson = new Gson();
        AgencyKpiCommisionRateLoanSetup agencyKpiTargetAmountSetup = agencyKpiCommisionRateLoanSetupRepository.findById(id).get();

        model.addAttribute("locationList", gson.toJson(locationService.getLocList()));
        model.addAttribute("zoneList", gson.toJson(zoneService.getAll()));
        model.addAttribute("dpdBucketList", gson.toJson(dpdBucketService.getAll()));

        model.addAttribute("productList", gson.toJson(productTypeService.getAllActive()));
        model.addAttribute("clStatusList", gson.toJson(assetClassificationLoanService.getAll()));

        model.addAttribute("selectedLocationList", gson.toJson(agencyKpiTargetAmountSetup.getLocationEntityList()));
        model.addAttribute("selectedZoneList", gson.toJson(agencyKpiTargetAmountSetup.getZoneEntityList()));
        model.addAttribute("selectedDpdBucketList", gson.toJson(agencyKpiTargetAmountSetup.getDpdBucketEntityList()));

        model.addAttribute("selectedProductList", gson.toJson(agencyKpiTargetAmountSetup.getProductTypeEntityList()));
        model.addAttribute("selectedClStatusList", gson.toJson(agencyKpiTargetAmountSetup.getAssetClassificationLoanEntityList()));


        model.addAttribute("agency", agencyKpiTargetAmountSetup);
        return "collection/kpi/agency/loan/targetByCommision/create";
    }

    @GetMapping(value = "view")
    public String viewPage(@RequestParam(value = "id")Long id, Model model) {
        model.addAttribute("agency",agencyKpiCommisionRateLoanSetupRepository.findById(id).get());
        return "collection/kpi/agency/loan/targetByCommision/view";
    }
}
