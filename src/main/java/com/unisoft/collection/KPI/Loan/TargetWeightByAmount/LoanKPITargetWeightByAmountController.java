package com.unisoft.collection.KPI.Loan.TargetWeightByAmount;
/*
  Created by Md.   Islam on 9/3/2019
*/

import com.unisoft.audittrail.AuditTrailService;
import com.unisoft.collection.settings.dpdBucket.DPDBucketEntity;
import com.unisoft.collection.settings.dpdBucket.DPDBucketService;
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
@RequestMapping(value = "/collection/kpi/loan/weightByAmount/")
public class LoanKPITargetWeightByAmountController {

    private LoanKPITargetWeightByAmountService loanKPITargetWeightByAmountService;
    private ProductTypeService productTypeService;
    private SectorGroupService sectorGroupService;
    private LocationService locationService;
    private ZoneService zoneService;
    private DPDBucketService dpdBucketService;
    private UserService userService;
    private ProductTypeRepository productTypeRepository;
    private AuditTrailService auditTrailService;

    @GetMapping("list")

    public String viewList(Model model){

        List<LoanKPITargetWeightByAmountEntity> loanKPITargetWeightByAmountEntities= loanKPITargetWeightByAmountService.getAllActiveData(new Date());
        model.addAttribute("loanKPITargetWeightAmountList",loanKPITargetWeightByAmountEntities);

        return "collection/kpi/loan/weightByAmount/listView";

    }

    @GetMapping("create")
    public String addNewPage(Model model)
    {
        Gson gson = new Gson();
        model.addAttribute("productTypeList", gson.toJson(productTypeRepository.findByProductGroupEntityCardAccount("Loan")));
        model.addAttribute("sectorGroupList", gson.toJson(sectorGroupService.getAll()));
        model.addAttribute("zoneList", gson.toJson(zoneService.getAll()));
        model.addAttribute("locationList", gson.toJson(locationService.getLocList()));
        model.addAttribute("dpdBucketList", gson.toJson(dpdBucketService.getAll()));

        model.addAttribute("loanKPITargetWeightAmount",new LoanKPITargetWeightByAmountEntity());
        return "collection/kpi/loan/weightByAmount/create";
    }


    @PostMapping(value = "create")
    public String addNewPage( LoanKPITargetWeightByAmountEntity loanKPITargetWeightByAmountEntity)
    {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String empId=userService.findById(user.getId()).getEmployeeId();

        if(loanKPITargetWeightByAmountEntity.getId() == null)
        {
            List<ProductTypeEntity> productTypeEntities = new ArrayList<>();
            List<SectorGroupEntity> sectorGroupEntities = new ArrayList<>();
            List<ZoneEntity> zoneEntities = new ArrayList<>();
            List<LocationEntity> locationEntities = new ArrayList<>();
            List<DPDBucketEntity> dpdBucketEntities= new ArrayList<>();


            for(String s: loanKPITargetWeightByAmountEntity.getProductTypeIds()){
                ProductTypeEntity productTypeEntity= productTypeService.getById(new Long(s));
                productTypeEntities.add(productTypeEntity);
            }

            for(String s: loanKPITargetWeightByAmountEntity.getSectorGroupIds()){
                SectorGroupEntity sectorGroupEntity= sectorGroupService.getById(new Long(s));
                sectorGroupEntities.add(sectorGroupEntity);
            }

            for(String s: loanKPITargetWeightByAmountEntity.getZoneIds()){
                ZoneEntity zoneEntity= zoneService.getById(new Long(s));
                zoneEntities.add(zoneEntity);
            }

            for(String s: loanKPITargetWeightByAmountEntity.getLocationIds()){
                LocationEntity locationEntity= locationService.getById(new Long(s));
                locationEntities.add(locationEntity);
            }

            for(String s: loanKPITargetWeightByAmountEntity.getDpdBucketIds()){
                DPDBucketEntity dpdBucketEntity= dpdBucketService.getById(new Long(s));
                dpdBucketEntities.add(dpdBucketEntity);
            }

            loanKPITargetWeightByAmountEntity.setProductType(productTypeEntities);
            loanKPITargetWeightByAmountEntity.setSectorGroup(sectorGroupEntities);
            loanKPITargetWeightByAmountEntity.setZone(zoneEntities);
            loanKPITargetWeightByAmountEntity.setLocation(locationEntities);
            loanKPITargetWeightByAmountEntity.setDpdBucket(dpdBucketEntities);

            loanKPITargetWeightByAmountEntity.setCreatedBy(empId);
            loanKPITargetWeightByAmountEntity.setCreatedDate(new Date());
            boolean save = loanKPITargetWeightByAmountService.addNew(loanKPITargetWeightByAmountEntity);
            auditTrailService.saveCreatedData("KPI Target Setup (Weight) based on Amount-Loan", loanKPITargetWeightByAmountEntity);
        }else {
            LoanKPITargetWeightByAmountEntity oldEntity = loanKPITargetWeightByAmountService.getById(loanKPITargetWeightByAmountEntity.getId());
            LoanKPITargetWeightByAmountEntity previousEntity = new LoanKPITargetWeightByAmountEntity();
            BeanUtils.copyProperties(oldEntity, previousEntity);

            List<ProductTypeEntity> productTypeEntities = new ArrayList<>();
            List<SectorGroupEntity> sectorGroupEntities = new ArrayList<>();
            List<ZoneEntity> zoneEntities = new ArrayList<>();
            List<LocationEntity> locationEntities = new ArrayList<>();
            List<DPDBucketEntity> dpdBucketEntities= new ArrayList<>();

            for(String s: loanKPITargetWeightByAmountEntity.getProductTypeIds()){
                ProductTypeEntity productTypeEntity= productTypeService.getById(new Long(s));
                productTypeEntities.add(productTypeEntity);
            }

            for(String s: loanKPITargetWeightByAmountEntity.getSectorGroupIds()){
                SectorGroupEntity sectorGroupEntity= sectorGroupService.getById(new Long(s));
                sectorGroupEntities.add(sectorGroupEntity);
            }

            for(String s: loanKPITargetWeightByAmountEntity.getZoneIds()){
                ZoneEntity zoneEntity= zoneService.getById(new Long(s));
                zoneEntities.add(zoneEntity);
            }

            for(String s: loanKPITargetWeightByAmountEntity.getLocationIds()){
                LocationEntity locationEntity= locationService.getById(new Long(s));
                locationEntities.add(locationEntity);
            }

            for(String s: loanKPITargetWeightByAmountEntity.getDpdBucketIds()){
                DPDBucketEntity dpdBucketEntity= dpdBucketService.getById(new Long(s));
                dpdBucketEntities.add(dpdBucketEntity);
            }

            loanKPITargetWeightByAmountEntity.setProductType(productTypeEntities);
            loanKPITargetWeightByAmountEntity.setSectorGroup(sectorGroupEntities);
            loanKPITargetWeightByAmountEntity.setZone(zoneEntities);
            loanKPITargetWeightByAmountEntity.setLocation(locationEntities);
            loanKPITargetWeightByAmountEntity.setDpdBucket(dpdBucketEntities);

            loanKPITargetWeightByAmountEntity.setModifiedBy(empId);
            loanKPITargetWeightByAmountEntity.setModifiedDate(new Date());
            boolean update=loanKPITargetWeightByAmountService.updateData(loanKPITargetWeightByAmountEntity);
            auditTrailService.saveUpdatedData("KPI Target Setup (Weight) based on Amount-Loan", previousEntity, loanKPITargetWeightByAmountEntity);
        }
        return "redirect:/collection/kpi/loan/weightByAmount/list";
    }


    @GetMapping("edit")
    public String editPage(@RequestParam(value = "id") Long findId, Model model)
    {
        LoanKPITargetWeightByAmountEntity loanKPITargetWeightByAmountEntity= loanKPITargetWeightByAmountService.getById(findId);
        Gson gson = new Gson();

        model.addAttribute("productTypeList", gson.toJson(productTypeRepository.findByProductGroupEntityCardAccount("Loan")));
        model.addAttribute("sectorGroupList", gson.toJson(sectorGroupService.getAll()));
        model.addAttribute("zoneList", gson.toJson(zoneService.getAll()));
        model.addAttribute("locationList", gson.toJson(locationService.getLocList()));
        model.addAttribute("dpdBucketList", gson.toJson(dpdBucketService.getAll()));
        model.addAttribute("selectedProductTypeList", gson.toJson(loanKPITargetWeightByAmountEntity.getProductType()));
        model.addAttribute("selectedSectorGroupList", gson.toJson(loanKPITargetWeightByAmountEntity.getSectorGroup()));
        model.addAttribute("selectedZoneList", gson.toJson(loanKPITargetWeightByAmountEntity.getZone()));
        model.addAttribute("selectedLocationList", gson.toJson(loanKPITargetWeightByAmountEntity.getLocation()));
        model.addAttribute("selectedDpdBucketList", gson.toJson(loanKPITargetWeightByAmountEntity.getDpdBucket()));

        model.addAttribute("loanKPITargetWeightAmount", loanKPITargetWeightByAmountEntity);
        return "collection/kpi/loan/weightByAmount/create";
    }

    @GetMapping("view")
    public String viewPage(@RequestParam(value = "id") Long findId,Model model)
    {
        model.addAttribute("loanKPITargetWeightAmount",loanKPITargetWeightByAmountService.getById(findId));
        return "collection/kpi/loan/weightByAmount/view";
    }


}
