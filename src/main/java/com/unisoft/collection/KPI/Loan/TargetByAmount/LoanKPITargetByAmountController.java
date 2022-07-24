package com.unisoft.collection.KPI.Loan.TargetByAmount;

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
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
@RequestMapping(value = "/collection/kpi/loan/byOutstanding/")
public class LoanKPITargetByAmountController {

    private final LoanKPITargetByAmountService loanKPITargetByAmountService;
    private final ProductTypeService productTypeService;
    private final SectorGroupService sectorGroupService;
    private final LocationService locationService;
    private final ZoneService zoneService;
    private final DPDBucketService dpdBucketService;
    private final UserService userService;
    private final ProductTypeRepository productTypeRepository;
    private final AuditTrailService auditTrailService;

    @GetMapping("list")
    public String viewList(Model model) {
        List<LoanKPITargetByAmountEntity> loanKPITargetByAmountEntities = loanKPITargetByAmountService.getAllData();
        model.addAttribute("loanKPIByAmountList", loanKPITargetByAmountEntities);
        return "collection/kpi/loan/targetbyamount/listView";
    }

    @GetMapping("create")
    public String addNewPage(Model model) {
        Gson gson = new Gson();
        model.addAttribute("productTypeList", gson.toJson(productTypeRepository.findByProductGroupEntityCardAccount("Loan")));
        model.addAttribute("sectorGroupList", gson.toJson(sectorGroupService.getAll()));
        model.addAttribute("zoneList", gson.toJson(zoneService.getAll()));
        model.addAttribute("locationList", gson.toJson(locationService.getLocList()));
        model.addAttribute("dpdBucketList", gson.toJson(dpdBucketService.getAll()));

        model.addAttribute("loanKPIByAmount", new LoanKPITargetByAmountEntity());
        return "collection/kpi/loan/targetbyamount/create";
    }

    @PostMapping(value = "create")
    public String addNewPage(LoanKPITargetByAmountEntity loanKPITargetByAmountEntity) {
        //loanKPITargetByAmountEntity.setId(null);
        boolean isNewEntity = false;
        LoanKPITargetByAmountEntity previousEntity = new LoanKPITargetByAmountEntity();
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String empId = userService.findById(user.getId()).getEmployeeId();

        if (loanKPITargetByAmountEntity.getId() == null)
            isNewEntity = true;
        else {
            LoanKPITargetByAmountEntity oldEntity = loanKPITargetByAmountService.getById(loanKPITargetByAmountEntity.getId());
            BeanUtils.copyProperties(oldEntity, previousEntity);
        }

//        if(loanKPITargetByAmountEntity.getId() == null)
//        {
        List<ProductTypeEntity> productTypeEntities = new ArrayList<>();
        List<SectorGroupEntity> sectorGroupEntities = new ArrayList<>();
        List<ZoneEntity> zoneEntities = new ArrayList<>();
        List<LocationEntity> locationEntities = new ArrayList<>();
        List<DPDBucketEntity> dpdBucketEntities = new ArrayList<>();


        for (String s : loanKPITargetByAmountEntity.getProductTypeIds()) {
            ProductTypeEntity productTypeEntity = productTypeService.getById(new Long(s));
            productTypeEntities.add(productTypeEntity);
        }

        for (String s : loanKPITargetByAmountEntity.getSectorGroupIds()) {
            SectorGroupEntity sectorGroupEntity = sectorGroupService.getById(new Long(s));
            sectorGroupEntities.add(sectorGroupEntity);
        }

        for (String s : loanKPITargetByAmountEntity.getZoneIds()) {
            ZoneEntity zoneEntity = zoneService.getById(new Long(s));
            zoneEntities.add(zoneEntity);
        }

        for (String s : loanKPITargetByAmountEntity.getLocationIds()) {
            LocationEntity locationEntity = locationService.getById(new Long(s));
            locationEntities.add(locationEntity);
        }

        for (String s : loanKPITargetByAmountEntity.getDpdBucketIds()) {
            DPDBucketEntity dpdBucketEntity = dpdBucketService.getById(new Long(s));
            dpdBucketEntities.add(dpdBucketEntity);
        }

        loanKPITargetByAmountEntity.setProductType(productTypeEntities);
        loanKPITargetByAmountEntity.setSectorGroup(sectorGroupEntities);
        loanKPITargetByAmountEntity.setZone(zoneEntities);
        loanKPITargetByAmountEntity.setLocation(locationEntities);
        loanKPITargetByAmountEntity.setDpdBucket(dpdBucketEntities);

        loanKPITargetByAmountEntity.setCreatedBy(empId);
        loanKPITargetByAmountEntity.setCreatedDate(new Date());
        boolean save = loanKPITargetByAmountService.addNew(loanKPITargetByAmountEntity);

        if (isNewEntity)
            auditTrailService.saveCreatedData("KPI Target Setup based on Amount/Outstanding-Loan", loanKPITargetByAmountEntity);
        else
            auditTrailService.saveUpdatedData("KPI Target Setup based on Amount/Outstanding-Loan", previousEntity, loanKPITargetByAmountEntity);
//        }else {
//            List<ProductTypeEntity> productTypeEntities = new ArrayList<>();
//            List<SectorGroupEntity> sectorGroupEntities = new ArrayList<>();
//            List<ZoneEntity> zoneEntities = new ArrayList<>();
//            List<LocationEntity> locationEntities = new ArrayList<>();
//            List<DPDBucketEntity> dpdBucketEntities= new ArrayList<>();
//
//            for(String s: loanKPITargetByAmountEntity.getProductTypeIds()){
//                ProductTypeEntity productTypeEntity= productTypeService.getById(new Long(s));
//                productTypeEntities.add(productTypeEntity);
//            }
//
//            for(String s: loanKPITargetByAmountEntity.getSectorGroupIds()){
//                SectorGroupEntity sectorGroupEntity= sectorGroupService.getById(new Long(s));
//                sectorGroupEntities.add(sectorGroupEntity);
//            }
//
//            for(String s: loanKPITargetByAmountEntity.getZoneIds()){
//                ZoneEntity zoneEntity= zoneService.getById(new Long(s));
//                zoneEntities.add(zoneEntity);
//            }
//
//            for(String s: loanKPITargetByAmountEntity.getLocationIds()){
//                LocationEntity locationEntity= locationService.getById(new Long(s));
//                locationEntities.add(locationEntity);
//            }
//
//            for(String s: loanKPITargetByAmountEntity.getDpdBucketIds()){
//                DPDBucketEntity dpdBucketEntity= dpdBucketService.getById(new Long(s));
//                dpdBucketEntities.add(dpdBucketEntity);
//            }
//
//            loanKPITargetByAmountEntity.setProductType(productTypeEntities);
//            loanKPITargetByAmountEntity.setSectorGroup(sectorGroupEntities);
//            loanKPITargetByAmountEntity.setZone(zoneEntities);
//            loanKPITargetByAmountEntity.setLocation(locationEntities);
//            loanKPITargetByAmountEntity.setDpdBucket(dpdBucketEntities);
//
//            loanKPITargetByAmountEntity.setModifiedBy(empId);
//            loanKPITargetByAmountEntity.setModifiedDate(new Date());
//            boolean update=loanKPITargetByAmountService.updateData(loanKPITargetByAmountEntity);
//        }
        return "redirect:/collection/kpi/loan/byOutstanding/list";
    }

    @GetMapping("edit")
    public String editPage(@RequestParam(value = "id") Long findId, Model model) {
        LoanKPITargetByAmountEntity loanKPITargetByAmountEntity = loanKPITargetByAmountService.getById(findId);
        Gson gson = new Gson();

        model.addAttribute("productTypeList", gson.toJson(productTypeRepository.findByProductGroupEntityCardAccount("Loan")));
        model.addAttribute("sectorGroupList", gson.toJson(sectorGroupService.getAll()));
        model.addAttribute("zoneList", gson.toJson(zoneService.getAll()));
        model.addAttribute("locationList", gson.toJson(locationService.getLocList()));
        model.addAttribute("dpdBucketList", gson.toJson(dpdBucketService.getAll()));
        model.addAttribute("selectedProductTypeList", gson.toJson(loanKPITargetByAmountEntity.getProductType()));
        model.addAttribute("selectedSectorGroupList", gson.toJson(loanKPITargetByAmountEntity.getSectorGroup()));
        model.addAttribute("selectedZoneList", gson.toJson(loanKPITargetByAmountEntity.getZone()));
        model.addAttribute("selectedLocationList", gson.toJson(loanKPITargetByAmountEntity.getLocation()));
        model.addAttribute("selectedDpdBucketList", gson.toJson(loanKPITargetByAmountEntity.getDpdBucket()));

        model.addAttribute("loanKPIByAmount", loanKPITargetByAmountEntity);
        return "collection/kpi/loan/targetbyamount/create";
    }

    @GetMapping("view")
    public String viewPage(@RequestParam(value = "id") Long findId, Model model) {
        model.addAttribute("loanKPIByAmount", loanKPITargetByAmountService.getById(findId));
        return "collection/kpi/loan/targetbyamount/view";
    }

    /*@GetMapping(value = "edit")
    public String viewData(Model model,@RequestParam(value = "id")Long id)
    {
        int i;
        LoanKPITargetByAmountEntity loanKPITargetByAmountEntity = loanKPITargetByAmountService.getById(id);
        //System.err.println("KPI "+ loanKPITargetByAmountEntity);

        List<Long> bucketIdList=new ArrayList<>();
        List<Long> prodIdList=new ArrayList<>();
        List<Long> sectorIdList=new ArrayList<>();
        List<Long> locIdList=new ArrayList<>();
        List<Long> zoneIdList=new ArrayList<>();

        if(loanKPITargetByAmountEntity !=null)
        {
            for(i=0; i< loanKPITargetByAmountEntity.getProductList().size(); i++)
            {
                prodIdList.add(loanKPITargetByAmountEntity.getProductList().get(i).getId());
            }

            for(i=0; i< loanKPITargetByAmountEntity.getSectorGroupList().size(); i++)
            {
                sectorIdList.add(loanKPITargetByAmountEntity.getSectorGroupList().get(i).getId());
            }

            for(i=0; i< loanKPITargetByAmountEntity.getLocationList().size(); i++)
            {
                locIdList.add(loanKPITargetByAmountEntity.getLocationList().get(i).getId());
            }

            for(i=0; i< loanKPITargetByAmountEntity.getZoneList().size(); i++)
            {
                zoneIdList.add(loanKPITargetByAmountEntity.getZoneList().get(i).getId());
            }

            for(i=0; i< loanKPITargetByAmountEntity.getBucketList().size(); i++)
            {
                bucketIdList.add(loanKPITargetByAmountEntity.getBucketList().get(i).getId());
            }

            model.addAttribute("loanKPIByAmount", loanKPITargetByAmountEntity);
        }else {
            model.addAttribute("loanKPIByAmount",new LoanKPITargetByAmountEntity());
        }
        model.addAttribute("productList",productTypeService.getAllActive());
        model.addAttribute("sectorList",sectorGroupService.getActiveList());
        model.addAttribute("locationList",locationService.getActiveList());
        model.addAttribute("zoneList",zoneService.getActiveList());
        model.addAttribute("bucketList",dpdBucketService.getActiveList());

        model.addAttribute("prodIdList",prodIdList);
        model.addAttribute("sectorIdList",sectorIdList);
        model.addAttribute("locIdList",locIdList);
        model.addAttribute("zoneIdList",zoneIdList);
        model.addAttribute("bucketIdList",bucketIdList);


        return "collection/kpi/loan/targetbyamount/targetByAmount";
    }*/


    /*@PostMapping(value = "addNew")
    public String addNew(@RequestParam(value = "prodIdList")List<Long> prodIdList,
                         @RequestParam(value = "secGrpIdList")List<Long> secGrpIdList,
                         @RequestParam(value = "zoneIdList")List<Long> zoneIdList,
                         @RequestParam(value = "locIdList")List<Long> locIdList,
                         @RequestParam(value = "bucketIdList")List<Long> bucketIdList,
                         LoanKPITargetByAmountEntity loanKPITargetByAmountEntity)
    {
        int i;

        List<ProductTypeEntity> productTypeList=new ArrayList<>();
        for(i=0;i<prodIdList.size();i++)
        {
            ProductTypeEntity productType=new ProductTypeEntity();
            productType.setId(prodIdList.get(i));
            productTypeList.add(productType);
        }

        List<SectorGroupEntity> sectorGroupList=new ArrayList<>();
        for(i=0;i<secGrpIdList.size();i++)
        {
            SectorGroupEntity sectorGroup=new SectorGroupEntity();
            sectorGroup.setId(secGrpIdList.get(i));
            sectorGroupList.add(sectorGroup);
        }

        List<ZoneEntity> zoneList=new ArrayList<>();
        for(i=0;i<zoneIdList.size();i++)
        {
            ZoneEntity zoneEntity=new ZoneEntity();
            zoneEntity.setId(zoneIdList.get(i));
            zoneList.add(zoneEntity);
        }

        List<LocationEntity> locationList=new ArrayList<>();
        for(i=0;i<locIdList.size();i++)
        {
            LocationEntity locationEntity=new LocationEntity();
            locationEntity.setId(locIdList.get(i));
            locationList.add(locationEntity);
        }

        List<DPDBucketEntity> bucketList=new ArrayList<>();
        for(i=0;i<bucketIdList.size();i++)
        {
            DPDBucketEntity dpdBucketEntity=new DPDBucketEntity();
            dpdBucketEntity.setId(bucketIdList.get(i));
            bucketList.add(dpdBucketEntity);
        }

        loanKPITargetByAmountEntity.setBucketList(bucketList);
        loanKPITargetByAmountEntity.setLocationList(locationList);
        loanKPITargetByAmountEntity.setZoneList(zoneList);
        loanKPITargetByAmountEntity.setSectorGroupList(sectorGroupList);
        loanKPITargetByAmountEntity.setProductList(productTypeList);

        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

//        if(userService.findById(user.getId()).getUsername().length()>0)
        String empId=userService.findById(user.getId()).getEmployeeId();

        loanKPITargetByAmountEntity.setModifiedDate(new Date());
        loanKPITargetByAmountEntity.setModifiedBy(empId);

        if(loanKPITargetByAmountEntity.getId() == null)
        {
            loanKPITargetByAmountEntity.setCreatedBy(empId);
            loanKPITargetByAmountEntity.setCreatedDate(new Date());
            boolean save= loanKPITargetByAmountService.addNew(loanKPITargetByAmountEntity);
        }else{
            boolean update =loanKPITargetByAmountService.updateData(loanKPITargetByAmountEntity);
        }

        return "redirect:/collection/kpi/loan/byOutstanding/list";
    }*/


    /*@GetMapping(value = "view")
    public String viewPage(Model model,@RequestParam(value = "id")Long id)
    {
        model.addAttribute("loanKPIByAmount",loanKPITargetByAmountService.getById(id));
        return "collection/kpi/loan/targetByAmount/view";
    }*/

    /*@GetMapping(value = "list")
    public String viewAll(Model model)
    {
        model.addAttribute("loanKPIByAmountList",loanKPITargetByAmountService.getAllData());
        return "collection/kpi/loan/targetbyamount/listView";
    }*/

    /*@GetMapping(value = "addNew")
    public String addPage(Model model)
    {
        List<Long> bucketIdList=new ArrayList<>();
        List<Long> prodIdList=new ArrayList<>();
        List<Long> sectorIdList=new ArrayList<>();
        List<Long> locIdList=new ArrayList<>();
        List<Long> zoneIdList=new ArrayList<>();

        model.addAttribute("productList",productTypeService.getAllActive());
        model.addAttribute("sectorList",sectorGroupService.getActiveList());
        model.addAttribute("locationList",locationService.getActiveList());
        model.addAttribute("zoneList",zoneService.getActiveList());
        model.addAttribute("bucketList",dpdBucketService.getActiveList());
        model.addAttribute("loanKPIByAmount",new LoanKPITargetByAmountEntity());
        model.addAttribute("prodIdList",prodIdList);
        model.addAttribute("sectorIdList",sectorIdList);
        model.addAttribute("locIdList",locIdList);
        model.addAttribute("zoneIdList",zoneIdList);
        model.addAttribute("bucketIdList",bucketIdList);

        return "collection/kpi/loan/targetByAmount/targetByAmount";
    }*/

}
