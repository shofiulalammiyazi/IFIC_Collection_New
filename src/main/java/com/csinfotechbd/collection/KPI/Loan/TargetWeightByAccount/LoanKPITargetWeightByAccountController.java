package com.csinfotechbd.collection.KPI.Loan.TargetWeightByAccount;
/*
  Created by Md. Monirul Islam on 9/3/2019
*/

import com.csinfotechbd.audittrail.AuditTrailService;
import com.csinfotechbd.collection.settings.dpdBucket.DPDBucketEntity;
import com.csinfotechbd.collection.settings.dpdBucket.DPDBucketService;
import com.csinfotechbd.collection.settings.location.LocationEntity;
import com.csinfotechbd.collection.settings.location.LocationService;
import com.csinfotechbd.collection.settings.productType.ProductTypeEntity;
import com.csinfotechbd.collection.settings.productType.ProductTypeRepository;
import com.csinfotechbd.collection.settings.productType.ProductTypeService;
import com.csinfotechbd.collection.settings.sectorGroup.SectorGroupEntity;
import com.csinfotechbd.collection.settings.sectorGroup.SectorGroupService;
import com.csinfotechbd.collection.settings.zone.ZoneEntity;
import com.csinfotechbd.collection.settings.zone.ZoneService;
import com.csinfotechbd.user.UserPrincipal;
import com.csinfotechbd.user.UserService;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping(value = "/collection/kpi/loan/weightByAccount/")
public class LoanKPITargetWeightByAccountController {

    @Autowired
    private LoanKPITargetWeightByAccountService loanKPITargetWeightByAccountService;
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

        List<LoanKPITargetWeightByAccountEntity> loanKPITargetWeightByAccountEntities= loanKPITargetWeightByAccountService.getAllData();
        model.addAttribute("loanKPITargetWeightList",loanKPITargetWeightByAccountEntities);

        return "collection/kpi/loan/weightByAccount/listView";

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

        model.addAttribute("loanKPITargetWeight",new LoanKPITargetWeightByAccountEntity());
        return "collection/kpi/loan/weightByAccount/create";
    }


    @PostMapping(value = "create")
    public String addNewPage( LoanKPITargetWeightByAccountEntity loanKPITargetWeightByAccountEntity)
    {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String empId=userService.findById(user.getId()).getEmployeeId();

        if(loanKPITargetWeightByAccountEntity.getId() == null)
        {
            List<ProductTypeEntity> productTypeEntities = new ArrayList<>();
            List<SectorGroupEntity> sectorGroupEntities = new ArrayList<>();
            List<ZoneEntity> zoneEntities = new ArrayList<>();
            List<LocationEntity> locationEntities = new ArrayList<>();
            List<DPDBucketEntity> dpdBucketEntities= new ArrayList<>();


            for(String s: loanKPITargetWeightByAccountEntity.getProductTypeIds()){
                ProductTypeEntity productTypeEntity= productTypeService.getById(new Long(s));
                productTypeEntities.add(productTypeEntity);
            }

            for(String s: loanKPITargetWeightByAccountEntity.getSectorGroupIds()){
                SectorGroupEntity sectorGroupEntity= sectorGroupService.getById(new Long(s));
                sectorGroupEntities.add(sectorGroupEntity);
            }

            for(String s: loanKPITargetWeightByAccountEntity.getZoneIds()){
                ZoneEntity zoneEntity= zoneService.getById(new Long(s));
                zoneEntities.add(zoneEntity);
            }

            for(String s: loanKPITargetWeightByAccountEntity.getLocationIds()){
                LocationEntity locationEntity= locationService.getById(new Long(s));
                locationEntities.add(locationEntity);
            }

            for(String s: loanKPITargetWeightByAccountEntity.getDpdBucketIds()){
                DPDBucketEntity dpdBucketEntity= dpdBucketService.getById(new Long(s));
                dpdBucketEntities.add(dpdBucketEntity);
            }

            loanKPITargetWeightByAccountEntity.setProductType(productTypeEntities);
            loanKPITargetWeightByAccountEntity.setSectorGroup(sectorGroupEntities);
            loanKPITargetWeightByAccountEntity.setZone(zoneEntities);
            loanKPITargetWeightByAccountEntity.setLocation(locationEntities);
            loanKPITargetWeightByAccountEntity.setDpdBucket(dpdBucketEntities);

            loanKPITargetWeightByAccountEntity.setCreatedBy(empId);
            loanKPITargetWeightByAccountEntity.setCreatedDate(new Date());
            boolean save = loanKPITargetWeightByAccountService.addNew(loanKPITargetWeightByAccountEntity);
            auditTrailService.saveCreatedData("KPI Target Setup (Weight) based on Account-Loan", loanKPITargetWeightByAccountEntity);
        }else {
            LoanKPITargetWeightByAccountEntity oldEntity = loanKPITargetWeightByAccountService.getById(loanKPITargetWeightByAccountEntity.getId());
            LoanKPITargetWeightByAccountEntity previousEntity = new LoanKPITargetWeightByAccountEntity();
            BeanUtils.copyProperties(oldEntity, previousEntity);

            List<ProductTypeEntity> productTypeEntities = new ArrayList<>();
            List<SectorGroupEntity> sectorGroupEntities = new ArrayList<>();
            List<ZoneEntity> zoneEntities = new ArrayList<>();
            List<LocationEntity> locationEntities = new ArrayList<>();
            List<DPDBucketEntity> dpdBucketEntities= new ArrayList<>();

            for(String s: loanKPITargetWeightByAccountEntity.getProductTypeIds()){
                ProductTypeEntity productTypeEntity= productTypeService.getById(new Long(s));
                productTypeEntities.add(productTypeEntity);
            }

            for(String s: loanKPITargetWeightByAccountEntity.getSectorGroupIds()){
                SectorGroupEntity sectorGroupEntity= sectorGroupService.getById(new Long(s));
                sectorGroupEntities.add(sectorGroupEntity);
            }

            for(String s: loanKPITargetWeightByAccountEntity.getZoneIds()){
                ZoneEntity zoneEntity= zoneService.getById(new Long(s));
                zoneEntities.add(zoneEntity);
            }

            for(String s: loanKPITargetWeightByAccountEntity.getLocationIds()){
                LocationEntity locationEntity= locationService.getById(new Long(s));
                locationEntities.add(locationEntity);
            }

            for(String s: loanKPITargetWeightByAccountEntity.getDpdBucketIds()){
                DPDBucketEntity dpdBucketEntity= dpdBucketService.getById(new Long(s));
                dpdBucketEntities.add(dpdBucketEntity);
            }

            loanKPITargetWeightByAccountEntity.setProductType(productTypeEntities);
            loanKPITargetWeightByAccountEntity.setSectorGroup(sectorGroupEntities);
            loanKPITargetWeightByAccountEntity.setZone(zoneEntities);
            loanKPITargetWeightByAccountEntity.setLocation(locationEntities);
            loanKPITargetWeightByAccountEntity.setDpdBucket(dpdBucketEntities);

            loanKPITargetWeightByAccountEntity.setModifiedBy(empId);
            loanKPITargetWeightByAccountEntity.setModifiedDate(new Date());
            boolean update=loanKPITargetWeightByAccountService.updateData(loanKPITargetWeightByAccountEntity);
            auditTrailService.saveUpdatedData("KPI Target Setup (Weight) based on Account-Loan", previousEntity, loanKPITargetWeightByAccountEntity);
        }
        return "redirect:/collection/kpi/loan/weightByAccount/list";
    }

    @GetMapping("edit")
    public String editPage(@RequestParam(value = "id") Long findId, Model model)
    {
        LoanKPITargetWeightByAccountEntity loanKPITargetWeightByAccountEntity= loanKPITargetWeightByAccountService.getById(findId);
        Gson gson = new Gson();

        model.addAttribute("productTypeList", gson.toJson(productTypeRepository.findByProductGroupEntityCardAccount("Loan")));
        model.addAttribute("sectorGroupList", gson.toJson(sectorGroupService.getAll()));
        model.addAttribute("zoneList", gson.toJson(zoneService.getAll()));
        model.addAttribute("locationList", gson.toJson(locationService.getLocList()));
        model.addAttribute("dpdBucketList", gson.toJson(dpdBucketService.getAll()));
        model.addAttribute("selectedProductTypeList", gson.toJson(loanKPITargetWeightByAccountEntity.getProductType()));
        model.addAttribute("selectedSectorGroupList", gson.toJson(loanKPITargetWeightByAccountEntity.getSectorGroup()));
        model.addAttribute("selectedZoneList", gson.toJson(loanKPITargetWeightByAccountEntity.getZone()));
        model.addAttribute("selectedLocationList", gson.toJson(loanKPITargetWeightByAccountEntity.getLocation()));
        model.addAttribute("selectedDpdBucketList", gson.toJson(loanKPITargetWeightByAccountEntity.getDpdBucket()));

        model.addAttribute("loanKPITargetWeight", loanKPITargetWeightByAccountEntity);
        return "collection/kpi/loan/weightByAccount/create";
    }

    @GetMapping("view")
    public String viewPage(@RequestParam(value = "id") Long findId,Model model)
    {
        model.addAttribute("loanKPITargetWeight",loanKPITargetWeightByAccountService.getById(findId));
        return "collection/kpi/loan/weightByAccount/view";
    }


}
