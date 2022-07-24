package com.unisoft.collection.settings.dunningLetterRulesCard;

import com.unisoft.collection.settings.ageCode.AgeCodeEntity;
import com.unisoft.collection.settings.ageCode.AgeCodeService;
import com.unisoft.collection.settings.location.LocationEntity;
import com.unisoft.collection.settings.location.LocationService;
import com.unisoft.collection.settings.manualLetterSetup.ManualLetterSetupInfo;
import com.unisoft.collection.settings.manualLetterSetup.ManualLetterSetupService;
import com.unisoft.collection.settings.productType.ProductTypeEntity;
import com.unisoft.collection.settings.productType.ProductTypeRepository;
import com.unisoft.collection.settings.productType.ProductTypeService;
import com.unisoft.user.UserPrincipal;
import com.google.gson.Gson;
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
@RequestMapping("/collection/settings/dunningLetterRulesCard/")
public class DunningLetterRulesCardController {

    private DunningLetterRulesCardService dunningLetterRulesCardService;
    private ProductTypeService productTypeService;
    private ProductTypeRepository productTypeRepository;
    private AgeCodeService ageCodeService;
    private ManualLetterSetupService manualLetterSetupService;
    private LocationService locationService;

    @GetMapping("list")

    public String viewDunningLetterRulesCardList(Model model){

        List<DunningLetterRulesCardInfo> dunningLetterRulesCardInfoList = dunningLetterRulesCardService.getDunningLetterRulesCardInfoList();
        model.addAttribute("dlrclist",dunningLetterRulesCardInfoList);

        return "collection/settings/dunningletterrulescard/dunningletterrulescard";

    }

    @GetMapping("create")
    public String addNewPage(Model model)
    {
        Gson gson = new Gson();
        model.addAttribute("productTypeCardList", gson.toJson(productTypeRepository.findAll()));
        model.addAttribute("ageCodeList", gson.toJson(ageCodeService.getAll()));
        model.addAttribute("letterTypeList", gson.toJson(manualLetterSetupService.getManualLetterSetupList()));
        model.addAttribute("locationList", gson.toJson(locationService.getLocList()));

        model.addAttribute("dlrc",new DunningLetterRulesCardInfo());
        return "collection/settings/dunningletterrulescard/create";
    }

    @PostMapping(value = "create")
    public String addNewManualLetterSetup( DunningLetterRulesCardInfo dunningLetterRulesCardInfo)
    {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(dunningLetterRulesCardInfo.getId() == null)
        {
            List<ProductTypeEntity> productTypeEntities = new ArrayList<>();
            List<AgeCodeEntity> ageCodeEntities = new ArrayList<>();
            List<ManualLetterSetupInfo> manualLetterSetupInfos = new ArrayList<>();
            List<LocationEntity> locationEntities = new ArrayList<>();

            for(String s: dunningLetterRulesCardInfo.getProductTypeCardIds()){
                ProductTypeEntity productTypeEntity= productTypeService.getById(new Long(s));
                productTypeEntities.add(productTypeEntity);
            }

            for(String s: dunningLetterRulesCardInfo.getAgeCodeIds()){
                AgeCodeEntity ageCodeEntity= ageCodeService.getById(new Long(s));
                ageCodeEntities.add(ageCodeEntity);
            }

            for(String s: dunningLetterRulesCardInfo.getLetterTypeIds()){
                ManualLetterSetupInfo manualLetterSetupInfo= manualLetterSetupService.getById(new Long(s));
                manualLetterSetupInfos.add(manualLetterSetupInfo);
            }

            for(String s: dunningLetterRulesCardInfo.getLocationIds()){
                LocationEntity locationEntity= locationService.getById(new Long(s));
                locationEntities.add(locationEntity);
            }

            dunningLetterRulesCardInfo.setProductTypeCard(productTypeEntities);
            dunningLetterRulesCardInfo.setAgeCode(ageCodeEntities);
            dunningLetterRulesCardInfo.setLetterType(manualLetterSetupInfos);
            dunningLetterRulesCardInfo.setLocation(locationEntities);

            dunningLetterRulesCardInfo.setCreatedBy(user.getUsername());
            dunningLetterRulesCardInfo.setCreatedDate(new Date());
            boolean save=dunningLetterRulesCardService.saveDunningLetterRulesCard(dunningLetterRulesCardInfo);
        }else {
            List<ProductTypeEntity> productTypeEntities = new ArrayList<>();
            List<AgeCodeEntity> ageCodeEntities = new ArrayList<>();
            List<ManualLetterSetupInfo> manualLetterSetupInfos = new ArrayList<>();
            List<LocationEntity> locationEntities = new ArrayList<>();

            for(String s: dunningLetterRulesCardInfo.getProductTypeCardIds()){
                ProductTypeEntity productTypeEntity= productTypeService.getById(new Long(s));
                productTypeEntities.add(productTypeEntity);
            }

            for(String s: dunningLetterRulesCardInfo.getAgeCodeIds()){
                AgeCodeEntity ageCodeEntity= ageCodeService.getById(new Long(s));
                ageCodeEntities.add(ageCodeEntity);
            }

            for(String s: dunningLetterRulesCardInfo.getLetterTypeIds()){
                ManualLetterSetupInfo manualLetterSetupInfo= manualLetterSetupService.getById(new Long(s));
                manualLetterSetupInfos.add(manualLetterSetupInfo);
            }

            for(String s: dunningLetterRulesCardInfo.getLocationIds()){
                LocationEntity locationEntity= locationService.getById(new Long(s));
                locationEntities.add(locationEntity);
            }

            dunningLetterRulesCardInfo.setProductTypeCard(productTypeEntities);
            dunningLetterRulesCardInfo.setAgeCode(ageCodeEntities);
            dunningLetterRulesCardInfo.setLetterType(manualLetterSetupInfos);
            dunningLetterRulesCardInfo.setLocation(locationEntities);
            dunningLetterRulesCardInfo.setModifiedBy(user.getUsername());
            dunningLetterRulesCardInfo.setModifiedDate(new Date());
            boolean update=dunningLetterRulesCardService.updateDunningLetterRulesCard(dunningLetterRulesCardInfo);
        }
        return "redirect:/collection/settings/dunningLetterRulesCard/list";
    }

    @GetMapping("edit")
    public String editPage(@RequestParam(value = "id") Long dlrId, Model model)
    {
        DunningLetterRulesCardInfo dunningLetterRulesCardInfo= dunningLetterRulesCardService.getById(dlrId);
        Gson gson = new Gson();

        model.addAttribute("productTypeList", gson.toJson(productTypeRepository.findAll()));
        model.addAttribute("ageCodeList", gson.toJson(ageCodeService.getAll()));
        model.addAttribute("letterTypeList", gson.toJson(manualLetterSetupService.getManualLetterSetupList()));
        model.addAttribute("locationList", gson.toJson(locationService.getLocList()));
        model.addAttribute("selectedProductTypeCardList", gson.toJson(dunningLetterRulesCardInfo.getProductTypeCard()));
        model.addAttribute("selectedAgeCodeList", gson.toJson(dunningLetterRulesCardInfo.getAgeCode()));
        model.addAttribute("selectedLetterTypeList", gson.toJson(dunningLetterRulesCardInfo.getLetterType()));
        model.addAttribute("selectedLocationList", gson.toJson(dunningLetterRulesCardInfo.getLocation()));

        model.addAttribute("dlrc", dunningLetterRulesCardInfo);
        return "collection/settings/dunningletterrulescard/create";
    }

    @GetMapping("view")
    public String viewPage(@RequestParam(value = "id") Long dlrId,Model model)
    {
        model.addAttribute("dlrcv",dunningLetterRulesCardService.getById(dlrId));
        return "collection/settings/dunningletterrulescard/view";
    }
}
