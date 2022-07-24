package com.unisoft.collection.KPI.Loan.AccountTargetByManager;
/*
Created by   Islam at 9/23/2019
*/

import com.unisoft.audittrail.AuditTrailService;
import com.unisoft.collection.settings.dpdBucket.DPDBucketEntity;
import com.unisoft.collection.settings.dpdBucket.DPDBucketService;
import com.unisoft.collection.settings.location.LocationEntity;
import com.unisoft.collection.settings.location.LocationService;
import com.unisoft.collection.settings.productType.ProductTypeEntity;
import com.unisoft.collection.settings.productType.ProductTypeRepository;
import com.unisoft.collection.settings.productType.ProductTypeService;
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
@RequestMapping(value = "/collection/dealer/loan/bytarget/manager")
public class DealerAccountTargetByManagerController {
    private DealerAccountTargetByManagerRepository dealerAccountTargetByManagerRepository;
    private ProductTypeService productTypeService;
    private LocationService locationService;
    private DPDBucketService dpdBucketService;
    private ProductTypeRepository productTypeRepository;
    private AuditTrailService auditTrailService;

    @GetMapping(value = "list")
    public String viewAll(Model model) {

        model.addAttribute("agencyList",dealerAccountTargetByManagerRepository.findAll());
        return "collection/kpi/loan/targetByManager/list";
    }

    @GetMapping(value = "create")
    public String addpage(Model model) {
        Gson gson = new Gson();
        model.addAttribute("productTypeList", gson.toJson(productTypeRepository.findByProductGroupEntityCardAccount("Loan")));
        model.addAttribute("locationList", gson.toJson(locationService.getLocList()));
        model.addAttribute("dpdBucketList", gson.toJson(dpdBucketService.getAll()));
        model.addAttribute("agency",new DealerAccountTargetByManager());
        return "collection/kpi/loan/targetByManager/create";
    }

    @PostMapping(value = "create")
    public String addnew(Model model, DealerAccountTargetByManager agency) {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<ProductTypeEntity> productTypeEntityList = new ArrayList<>();
        List<LocationEntity> locationEntityList = new ArrayList<>();
        List<DPDBucketEntity> dpdBucketEntityList= new ArrayList<>();

        agency.getProductIds().forEach(s -> {
            ProductTypeEntity productTypeEntity = productTypeService.getById(Long.parseLong(s));
            productTypeEntityList.add(productTypeEntity);
        });

        agency.getLocationIds().forEach(s -> {
            LocationEntity locationEntity = locationService.getById(Long.parseLong(s));
            locationEntityList.add(locationEntity);
        });

        agency.getDpdBucketIds().forEach(s -> {
            DPDBucketEntity dpdBucketEntity= dpdBucketService.getById(Long.parseLong(s));
            dpdBucketEntityList.add(dpdBucketEntity);
        });

        agency.setProductTypeEntityList(productTypeEntityList);
        agency.setLocationEntityList(locationEntityList);
        agency.setDpdBucketEntityList(dpdBucketEntityList);

        if(agency.getId()==null) {
            agency.setCreatedBy(user.getUsername());
            agency.setCreatedDate(new Date());
            dealerAccountTargetByManagerRepository.save(agency);
            auditTrailService.saveCreatedData("Dealer Account Target Setup by Manager – Loan", agency);
        }else {
            DealerAccountTargetByManager oldEntity = dealerAccountTargetByManagerRepository.getOne(agency.getId());
            DealerAccountTargetByManager previousEntity = new DealerAccountTargetByManager();
            BeanUtils.copyProperties(oldEntity, previousEntity);

            agency.setModifiedBy(user.getUsername());
            agency.setModifiedDate(new Date());
            dealerAccountTargetByManagerRepository.save(agency);
            auditTrailService.saveUpdatedData("Dealer Account Target Setup by Manager – Loan", previousEntity, agency);
        }
        return "redirect:/collection/dealer/loan/bytarget/manager/list";
    }

    @GetMapping(value = "edit")
    public String editPage(@RequestParam(value = "id")Long id, Model model) {
        Gson gson = new Gson();
        DealerAccountTargetByManager agencyKpiTargetAmountSetup = dealerAccountTargetByManagerRepository.findById(id).get();

        model.addAttribute("productTypeList", gson.toJson(productTypeRepository.findByProductGroupEntityCardAccount("Loan")));
        model.addAttribute("locationList", gson.toJson(locationService.getLocList()));
        model.addAttribute("dpdBucketList", gson.toJson(dpdBucketService.getAll()));

        model.addAttribute("selectedProuductTypeList", gson.toJson(agencyKpiTargetAmountSetup.getProductTypeEntityList()));
        model.addAttribute("selectedLocationList", gson.toJson(agencyKpiTargetAmountSetup.getLocationEntityList()));
        model.addAttribute("selectedDpdBucketList", gson.toJson(agencyKpiTargetAmountSetup.getDpdBucketEntityList()));


        model.addAttribute("agency", agencyKpiTargetAmountSetup);
        return "collection/kpi/loan/targetByManager/create";
    }

    @GetMapping(value = "view")
    public String viewPage(@RequestParam(value = "id")Long id, Model model) {
        DealerAccountTargetByManager dealerAccountTargetByManager = dealerAccountTargetByManagerRepository.findById(id).get();
        model.addAttribute("agency", dealerAccountTargetByManager);

        System.err.println("THIS is an error"+dealerAccountTargetByManagerRepository.findById(id).toString());
        return "collection/kpi/loan/targetByManager/view";
    }
}
