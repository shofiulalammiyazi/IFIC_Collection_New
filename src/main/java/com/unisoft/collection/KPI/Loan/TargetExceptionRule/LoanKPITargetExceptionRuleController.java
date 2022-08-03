package com.unisoft.collection.KPI.Loan.TargetExceptionRule;
/*
  Created by Md.   Islam on 9/4/2019
*/

import com.unisoft.audittrail.AuditTrailService;
import com.unisoft.collection.settings.location.LocationEntity;
import com.unisoft.collection.settings.location.LocationService;
import com.unisoft.collection.settings.productType.ProductTypeEntity;
import com.unisoft.collection.settings.productType.ProductTypeRepository;
import com.unisoft.collection.settings.productType.ProductTypeService;
import com.unisoft.collection.settings.sectorGroup.SectorGroupEntity;
import com.unisoft.collection.settings.sectorGroup.SectorGroupService;
import com.unisoft.collection.settings.zone.ZoneEntity;
import com.unisoft.collection.settings.zone.ZoneService;
import com.unisoft.user.UserPrincipal;
import com.unisoft.user.UserService;
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
@RequestMapping(value = "/collection/kpi/loan/exceptionRule/")
public class LoanKPITargetExceptionRuleController {


    private LoanKPITargetExceptionRuleService loanKPITargetExceptionRuleService;
    private ProductTypeService productTypeService;
    private SectorGroupService sectorGroupService;
    private LocationService locationService;
    private ZoneService zoneService;
    private UserService userService;
    private ProductTypeRepository productTypeRepository;
    private final AuditTrailService auditTrailService;

    @GetMapping("list")

    public String viewList(Model model){

        List<LoanKPITargetExceptionRuleEntity> loanKPITargetExceptionRuleEntities= loanKPITargetExceptionRuleService.getAllData();
        model.addAttribute("exceptionRuleLoanList",loanKPITargetExceptionRuleEntities);

        return "collection/kpi/loan/exceptionRule/listView";

    }

    @GetMapping("create")
    public String addNewPage(Model model) {
        Gson gson = new Gson();
        model.addAttribute("productTypeList", gson.toJson(productTypeRepository.findByProductGroupEntityCardAccount("Loan")));
        model.addAttribute("sectorGroupList", gson.toJson(sectorGroupService.getAll()));
        model.addAttribute("zoneList", gson.toJson(zoneService.getAll()));
        model.addAttribute("locationList", gson.toJson(locationService.getLocList()));

        model.addAttribute("exceptionRuleLoan",new LoanKPITargetExceptionRuleEntity());
        return "collection/kpi/loan/exceptionRule/create";
    }


    @PostMapping(value = "create")
    public String addNewPage( LoanKPITargetExceptionRuleEntity loanKPITargetExceptionRuleEntity)
    {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String empId=userService.findById(user.getId()).getEmployeeId();

        if(loanKPITargetExceptionRuleEntity.getId() == null)
        {
            List<ProductTypeEntity> productTypeEntities = new ArrayList<>();
            List<SectorGroupEntity> sectorGroupEntities = new ArrayList<>();
            List<ZoneEntity> zoneEntities = new ArrayList<>();
            List<LocationEntity> locationEntities = new ArrayList<>();


            for(String s: loanKPITargetExceptionRuleEntity.getProductTypeIds()){
                ProductTypeEntity productTypeEntity= productTypeService.getById(new Long(s));
                productTypeEntities.add(productTypeEntity);
            }

            for(String s: loanKPITargetExceptionRuleEntity.getSectorGroupIds()){
                SectorGroupEntity sectorGroupEntity= sectorGroupService.getById(new Long(s));
                sectorGroupEntities.add(sectorGroupEntity);
            }

            for(String s: loanKPITargetExceptionRuleEntity.getZoneIds()){
                ZoneEntity zoneEntity= zoneService.getById(new Long(s));
                zoneEntities.add(zoneEntity);
            }

            for(String s: loanKPITargetExceptionRuleEntity.getLocationIds()){
                LocationEntity locationEntity= locationService.getById(new Long(s));
                locationEntities.add(locationEntity);
            }


            loanKPITargetExceptionRuleEntity.setProductType(productTypeEntities);
            loanKPITargetExceptionRuleEntity.setSectorGroup(sectorGroupEntities);
            loanKPITargetExceptionRuleEntity.setZone(zoneEntities);
            loanKPITargetExceptionRuleEntity.setLocation(locationEntities);

            loanKPITargetExceptionRuleEntity.setCreatedBy(empId);
            loanKPITargetExceptionRuleEntity.setCreatedDate(new Date());
            boolean save = loanKPITargetExceptionRuleService.addNew(loanKPITargetExceptionRuleEntity);
            auditTrailService.saveCreatedData("KPI Target Exception Rules-Loan", loanKPITargetExceptionRuleEntity);
        }else {
            LoanKPITargetExceptionRuleEntity oldEntity = loanKPITargetExceptionRuleService.getById(loanKPITargetExceptionRuleEntity.getId());
            LoanKPITargetExceptionRuleEntity previousEntity = new LoanKPITargetExceptionRuleEntity();
            BeanUtils.copyProperties(oldEntity, previousEntity);

            List<ProductTypeEntity> productTypeEntities = new ArrayList<>();
            List<SectorGroupEntity> sectorGroupEntities = new ArrayList<>();
            List<ZoneEntity> zoneEntities = new ArrayList<>();
            List<LocationEntity> locationEntities = new ArrayList<>();

            for(String s: loanKPITargetExceptionRuleEntity.getProductTypeIds()){
                ProductTypeEntity productTypeEntity= productTypeService.getById(new Long(s));
                productTypeEntities.add(productTypeEntity);
            }

            for(String s: loanKPITargetExceptionRuleEntity.getSectorGroupIds()){
                SectorGroupEntity sectorGroupEntity= sectorGroupService.getById(new Long(s));
                sectorGroupEntities.add(sectorGroupEntity);
            }

            for(String s: loanKPITargetExceptionRuleEntity.getZoneIds()){
                ZoneEntity zoneEntity= zoneService.getById(new Long(s));
                zoneEntities.add(zoneEntity);
            }

            for(String s: loanKPITargetExceptionRuleEntity.getLocationIds()){
                LocationEntity locationEntity= locationService.getById(new Long(s));
                locationEntities.add(locationEntity);
            }


            loanKPITargetExceptionRuleEntity.setProductType(productTypeEntities);
            loanKPITargetExceptionRuleEntity.setSectorGroup(sectorGroupEntities);
            loanKPITargetExceptionRuleEntity.setZone(zoneEntities);
            loanKPITargetExceptionRuleEntity.setLocation(locationEntities);

            loanKPITargetExceptionRuleEntity.setModifiedBy(empId);
            loanKPITargetExceptionRuleEntity.setModifiedDate(new Date());
            boolean update=loanKPITargetExceptionRuleService.updateData(loanKPITargetExceptionRuleEntity);
            auditTrailService.saveUpdatedData("KPI Target Exception Rules-Loan", previousEntity, loanKPITargetExceptionRuleEntity);
        }
        return "redirect:/collection/kpi/loan/exceptionRule/list";
    }


    @GetMapping("edit")
    public String editPage(@RequestParam(value = "id") Long findId, Model model)
    {
        LoanKPITargetExceptionRuleEntity loanKPITargetExceptionRuleEntity= loanKPITargetExceptionRuleService.getById(findId);
        Gson gson = new Gson();

        model.addAttribute("productTypeList", gson.toJson(productTypeRepository.findByProductGroupEntityCardAccount("Loan")));
        model.addAttribute("sectorGroupList", gson.toJson(sectorGroupService.getAll()));
        model.addAttribute("zoneList", gson.toJson(zoneService.getAll()));
        model.addAttribute("locationList", gson.toJson(locationService.getLocList()));
        model.addAttribute("selectedProductTypeList", gson.toJson(loanKPITargetExceptionRuleEntity.getProductType()));
        model.addAttribute("selectedSectorGroupList", gson.toJson(loanKPITargetExceptionRuleEntity.getSectorGroup()));
        model.addAttribute("selectedZoneList", gson.toJson(loanKPITargetExceptionRuleEntity.getZone()));
        model.addAttribute("selectedLocationList", gson.toJson(loanKPITargetExceptionRuleEntity.getLocation()));

        model.addAttribute("exceptionRuleLoan", loanKPITargetExceptionRuleEntity);
        return "collection/kpi/loan/exceptionRule/create";
    }

    @GetMapping("view")
    public String viewPage(@RequestParam(value = "id") Long findId,Model model)
    {
        model.addAttribute("exceptionRuleLoan",loanKPITargetExceptionRuleService.getById(findId));
        return "collection/kpi/loan/exceptionRule/view";
    }
}
