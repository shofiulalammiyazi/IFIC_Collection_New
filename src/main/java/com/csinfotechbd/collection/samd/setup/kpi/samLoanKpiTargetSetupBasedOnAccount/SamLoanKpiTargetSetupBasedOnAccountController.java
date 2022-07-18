package com.csinfotechbd.collection.samd.setup.kpi.samLoanKpiTargetSetupBasedOnAccount;

import com.csinfotechbd.collection.settings.dpdBucket.DPDBucketEntity;
import com.csinfotechbd.collection.settings.dpdBucket.DPDBucketService;
import com.csinfotechbd.collection.settings.location.LocationEntity;
import com.csinfotechbd.collection.settings.location.LocationService;
import com.csinfotechbd.collection.settings.productType.ProductTypeEntity;
import com.csinfotechbd.collection.settings.productType.ProductTypeService;
import com.csinfotechbd.collection.settings.sectorGroup.SectorGroupEntity;
import com.csinfotechbd.collection.settings.sectorGroup.SectorGroupService;
import com.csinfotechbd.collection.settings.zone.ZoneEntity;
import com.csinfotechbd.collection.settings.zone.ZoneService;
import com.csinfotechbd.common.CommonController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/collection/samd/kpi/sam-loan-kpi-target-setup-based-on-account")
public class SamLoanKpiTargetSetupBasedOnAccountController implements CommonController<SamLoanKpiTargetSetupBasedOnAccount> {

    @Autowired
    private ZoneService zoneService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private DPDBucketService dpdBucketService;

    @Autowired
    private ProductTypeService productTypeService;

    @Autowired
    private SectorGroupService sectorGroupService;

    @Autowired
    private SamLoanKpiTargetSetupBasedOnAccountService samLoanKpiTargetSetupBasedOnAccountService;

    @Override
    @GetMapping("/create")
    public String create(Model model) {
        List<ProductTypeEntity> productTypeList = productTypeService.getByProductGroup("Loan");
        List<LocationEntity> locationList = locationService.getLocList();
        List<ZoneEntity> zoneList = zoneService.getAll();
        List<DPDBucketEntity> dpdBucketList = dpdBucketService.getAll();
        List<SectorGroupEntity> sectorGroupList = sectorGroupService.getAll();

        model.addAttribute("productTypeList", productTypeList);
        model.addAttribute("locationList", locationList);
        model.addAttribute("zoneList", zoneList);
        model.addAttribute("dpdBucketList", dpdBucketList);
        model.addAttribute("sectorGroupList", sectorGroupList);

        return "samd/setup/kpi/samLoanKpiTargetSetupBasedOnAccount/create";
    }

    @Override
    @PostMapping("/create")
    public String create(Model model, SamLoanKpiTargetSetupBasedOnAccount entity) {
        samLoanKpiTargetSetupBasedOnAccountService.storeData(entity);
        return "redirect:/collection/samd/kpi/sam-loan-kpi-target-setup-based-on-account/list";
    }

    @Override
    public String view(Model model, Long id) {
        return null;
    }

    @GetMapping("/edit")
    public String edit(Model model, @RequestParam(value = "id", required = true) Long id) {
        SamLoanKpiTargetSetupBasedOnAccount targetSetupBasedOnAccount = samLoanKpiTargetSetupBasedOnAccountService.findDataById(id);

        List<ProductTypeEntity> productTypeList = productTypeService.getByProductGroup("Loan");
        List<LocationEntity> locationList = locationService.getLocList();
        List<ZoneEntity> zoneList = zoneService.getAll();
        List<DPDBucketEntity> dpdBucketList = dpdBucketService.getAll();
        List<SectorGroupEntity> sectorGroupList = sectorGroupService.getAll();

        model.addAttribute("targetSetupBasedOnAccount", targetSetupBasedOnAccount);
        model.addAttribute("productTypeList", productTypeList);
        model.addAttribute("locationList", locationList);
        model.addAttribute("zoneList", zoneList);
        model.addAttribute("dpdBucketList", dpdBucketList);
        model.addAttribute("sectorGroupList", sectorGroupList);

        return "samd/setup/kpi/samLoanKpiTargetSetupBasedOnAccount/edit";
    }

    @PostMapping("/edit")
    public String edit(Model model, SamLoanKpiTargetSetupBasedOnAccount entity) {
        samLoanKpiTargetSetupBasedOnAccountService.updateData(entity);
        return "redirect:/collection/samd/kpi/sam-loan-kpi-target-setup-based-on-account/list";
    }

    @Override
    @GetMapping("/list")
    public String list(Model model) {
        List<SamLoanKpiTargetSetupBasedOnAccount> allData = samLoanKpiTargetSetupBasedOnAccountService.findAllData();
        model.addAttribute("allData", allData);
        return "samd/setup/kpi/samLoanKpiTargetSetupBasedOnAccount/list";
    }

    @Override
    public String delete() {
        return null;
    }
}
