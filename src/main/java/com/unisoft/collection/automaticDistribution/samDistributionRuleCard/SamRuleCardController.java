package com.unisoft.collection.automaticDistribution.samDistributionRuleCard;

import com.unisoft.collection.settings.ageCode.AgeCodeEntity;
import com.unisoft.collection.settings.ageCode.AgeCodeService;
import com.unisoft.user.UserPrincipal;
import com.google.gson.Gson;
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
@RequestMapping(value = "/collcection/distribution/sam/card/")
public class SamRuleCardController {

    @Autowired
    private SamRuleCardRepository samRuleCardRepository;

    @Autowired
    private AgeCodeService ageCodeService;

    @GetMapping(value = "list")
    public String viewAll(Model model) {
        model.addAttribute("samRuleList", samRuleCardRepository.findAll());
        return "collection/automaticDistribution/samRuleCard/samRuleCard";
    }

    @GetMapping(value = "create")
    public String addpage(Model model) {
        List<SamRuleCardInfo> samDistributionRuleInfos = samRuleCardRepository.findAll();
        SamRuleCardInfo samRuleCardInfo = new SamRuleCardInfo();
        Gson gson = new Gson();

        if (samDistributionRuleInfos.size() > 0) {
            samRuleCardInfo = samDistributionRuleInfos.get(0);

            model.addAttribute("selectedAgeCodeList",
                    gson.toJson(samRuleCardInfo.getAgeCodeEntityList()));
        }

        model.addAttribute("ageCodeList", gson.toJson(ageCodeService.getAll()));
        model.addAttribute("samcard", samRuleCardInfo);
        return "collection/automaticDistribution/samRuleCard/create";
    }

    @PostMapping(value = "create")
    public String create(Model model, SamRuleCardInfo agency) {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<AgeCodeEntity> ageCodeEntities = new ArrayList<>();

        if (agency.getId() == null) {
            for (String s : agency.getAgeCodeIds()) {
                AgeCodeEntity byId = ageCodeService.getById(new Long(s));
                ageCodeEntities.add(byId);
            }
            agency.setEnabled(true);
            agency.setAgeCodeEntityList(ageCodeEntities);
            agency.setCreatedBy(user.getUsername());
            agency.setCreatedDate(new Date());
            samRuleCardRepository.save(agency);
        } else {
            for (String s : agency.getAgeCodeIds()) {
                AgeCodeEntity byId = ageCodeService.getById(new Long(s));
                ageCodeEntities.add(byId);
            }
            agency.setEnabled(true);
            agency.setAgeCodeEntityList(ageCodeEntities);
            agency.setModifiedBy(user.getUsername());
            agency.setModifiedDate(new Date());
            samRuleCardRepository.save(agency);
        }
        return "redirect:/collcection/distribution/sam/card/create";
    }

    @GetMapping(value = "edit")
    public String editPage(@RequestParam(value = "id") Long id, Model model) {
        SamRuleCardInfo samRuleCardInfo = samRuleCardRepository.findById(id).get();
        Gson gson = new Gson();
        model.addAttribute("ageCodeList", gson.toJson(ageCodeService.getAll()));
        model.addAttribute("selectedAgeCodeList", gson.toJson(samRuleCardInfo.getAgeCodeEntityList()));
        model.addAttribute("samcard", samRuleCardInfo);
        return "collection/automaticDistribution/samRuleCard/create";
    }

    @GetMapping(value = "view")
    public String viewPage(@RequestParam(value = "id") Long id, Model model) {
        model.addAttribute("agency", samRuleCardRepository.findById(id).get());
        return "collection/settings/agency/create";
    }
}
