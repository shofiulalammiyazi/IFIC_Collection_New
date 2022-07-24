package com.unisoft.collection.settings.deviationSetDistribution;


import com.unisoft.collection.settings.ageCode.AgeCodeEntity;
import com.unisoft.collection.settings.ageCode.AgeCodeService;
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
@RequestMapping("/collection/settings/deviationsetdistribution/")

public class DeviationSetDistributionController {

    private DeviationSetDistributionService deviationSetDistributionService;
    private AgeCodeService ageCodeService;


    @GetMapping("list")
    private String viewList(Model model){
        List<DeviationSetDistributionInfo> deviationSetDistributionInfoList= deviationSetDistributionService.deviationSetDistributionInfoList();
        model.addAttribute("dsdlist", deviationSetDistributionInfoList);
        return "collection/settings/deviationsetdistribution/deviationsetdistribution";
    }

    @GetMapping("create")
    public String addNewPage(Model model)
    {
        Gson gson = new Gson();
        model.addAttribute("ageCodeList", gson.toJson(ageCodeService.getAll()));
        model.addAttribute("dsd",new DeviationSetDistributionInfo());
        return "collection/settings/deviationsetdistribution/create";
    }

    @PostMapping(value = "create")
    public String addDeviationSetDistribution( DeviationSetDistributionInfo deviationSetDistributionInfo)
    {


        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(deviationSetDistributionInfo.getId() == null)
        {
            List<AgeCodeEntity> ageCodeEntities= new ArrayList<>();

            for(String s: deviationSetDistributionInfo.getAgeCodeIds()){
                AgeCodeEntity ageCodeEntity= ageCodeService.getById(new Long(s));
                ageCodeEntities.add(ageCodeEntity);
            }

            deviationSetDistributionInfo.setAgeCode(ageCodeEntities);
            deviationSetDistributionInfo.setCreatedBy(user.getUsername());
            deviationSetDistributionInfo.setCreatedDate(new Date());
            boolean save=deviationSetDistributionService.saveDeviationSetDistribution(deviationSetDistributionInfo);
        }else {
            List<AgeCodeEntity> ageCodeEntities= new ArrayList<>();

            for(String s: deviationSetDistributionInfo.getAgeCodeIds()){
                AgeCodeEntity ageCodeEntity= ageCodeService.getById(new Long(s));
                ageCodeEntities.add(ageCodeEntity);
            }

            deviationSetDistributionInfo.setAgeCode(ageCodeEntities);
            deviationSetDistributionInfo.setModifiedBy(user.getUsername());
            deviationSetDistributionInfo.setModifiedDate(new Date());
            boolean update=deviationSetDistributionService.updateDeviationSetDistribution(deviationSetDistributionInfo);
        }
        return "redirect:/collection/settings/deviationsetdistribution/list";
    }

    @GetMapping("edit")
    public String editDsd(@RequestParam(value = "id") Long mlsId, Model model)
    {
        DeviationSetDistributionInfo deviationSetDistributionInfo= deviationSetDistributionService.getById(mlsId);
        Gson gson = new Gson();

        model.addAttribute("ageCodeList", gson.toJson(ageCodeService.getAll()));
        model.addAttribute("selectedAgeCodeList", gson.toJson(deviationSetDistributionInfo.getAgeCode()));

        model.addAttribute("dsd", deviationSetDistributionInfo);
        return "collection/settings/deviationsetdistribution/create";
    }

    @GetMapping("view")
    public String viewLoc(@RequestParam(value = "id") Long mlsId,Model model)
    {
        model.addAttribute("dsd",deviationSetDistributionService.getById(mlsId));
        return "collection/settings/deviationsetdistribution/view";
    }
}
