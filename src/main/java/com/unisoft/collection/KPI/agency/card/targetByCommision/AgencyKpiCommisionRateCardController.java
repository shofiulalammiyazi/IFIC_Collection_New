package com.unisoft.collection.KPI.agency.card.targetByCommision;


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
@RequestMapping(value = "/collection/kpi/agency/card/bycommision/")
public class AgencyKpiCommisionRateCardController {
    private AgencyKpiCommisionRateCardSetupRepository agencyKpiCommisionRateCardSetupRepository;
    private AgeCodeService ageCodeService;

    @GetMapping(value = "list")
    public String viewAll(Model model) {
        model.addAttribute("agencyList",agencyKpiCommisionRateCardSetupRepository.findAll());
        return "collection/kpi/agency/card/targetByCommision/list";
    }

    @GetMapping(value = "create")
    public String addpage(Model model) {
        Gson gson = new Gson();
        model.addAttribute("dpdBucketList", gson.toJson(ageCodeService.getAll()));
        model.addAttribute("agency",new AgencyKpiCommisionRateCardSetup());
        return "collection/kpi/agency/card/targetByCommision/create";
    }

    @PostMapping(value = "create")
    public String addnew(Model model, AgencyKpiCommisionRateCardSetup agency) {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<AgeCodeEntity>  dpdBucketEntityList = new ArrayList<>();


        agency.getDpdBucketIds().forEach(s -> {
            AgeCodeEntity dpdBucketEntity = ageCodeService.getById(Long.parseLong(s));
            dpdBucketEntityList.add(dpdBucketEntity);
        });

        agency.setAgeCodeEntityList(dpdBucketEntityList);

        if(agency.getId()==null) {
            agency.setCreatedBy(user.getUsername());
            agency.setCreatedDate(new Date());
            agencyKpiCommisionRateCardSetupRepository.save(agency);
        }else {
            agency.setModifiedBy(user.getUsername());
            agency.setModifiedDate(new Date());
            agencyKpiCommisionRateCardSetupRepository.save(agency);
        }
        return "redirect:/collection/kpi/agency/card/bycommision/list";
    }

    @GetMapping(value = "edit")
    public String editPage(@RequestParam(value = "id")Long id, Model model) {
        Gson gson = new Gson();
        AgencyKpiCommisionRateCardSetup agencyKpiTargetAmountSetup = agencyKpiCommisionRateCardSetupRepository.findById(id).get();

        model.addAttribute("dpdBucketList", gson.toJson(ageCodeService.getAll()));
        model.addAttribute("selectedDpdBucketList", gson.toJson(agencyKpiTargetAmountSetup.getAgeCodeEntityList()));

        model.addAttribute("agency", agencyKpiTargetAmountSetup);
        return "collection/kpi/agency/card/targetByCommision/create";
    }

    @GetMapping(value = "view")
    public String viewPage(@RequestParam(value = "id")Long id, Model model) {
        model.addAttribute("agency",agencyKpiCommisionRateCardSetupRepository.findById(id).get());
        return "collection/kpi/agency/card/targetByCommision/view";
    }
}
