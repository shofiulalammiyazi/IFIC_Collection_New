package com.csinfotechbd.collection.KPI.esau;
/*
Created by Monirul Islam at 9/23/2019 
*/

import com.csinfotechbd.audittrail.AuditTrailService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.EntityManager;

@Controller
@AllArgsConstructor
@RequestMapping(value = "/collection/kpi/esau")
public class EsauDefaultRatingController {

    private EsauDefaultRatingSetupRepository esauDefaultRatingSetupRepository;
    private AuditTrailService auditTrailService;

    @GetMapping(value = "/create")
    public String getCreatePage(Model model){

        EsauDefaultRatingSetup esauDefaultRatingSetup = esauDefaultRatingSetupRepository.findFirstByOrderByIdDesc();

        if(esauDefaultRatingSetup == null) esauDefaultRatingSetup = new EsauDefaultRatingSetup();

        model.addAttribute("agency", esauDefaultRatingSetup);
        return "collection/kpi/esau/create";
    }

    @PostMapping(value = "/create")
    public String saveEsau(EsauDefaultRatingSetup esauDefaultRatingSetup){
        boolean isNewEntity = false;
        EsauDefaultRatingSetup previousEntity = new EsauDefaultRatingSetup();
        if (esauDefaultRatingSetup.getId() == null)
            isNewEntity = true;
        else{
            EsauDefaultRatingSetup oldEntity = esauDefaultRatingSetupRepository.getOne(esauDefaultRatingSetup.getId());
            BeanUtils.copyProperties(oldEntity, previousEntity);
        }

        esauDefaultRatingSetupRepository.save(esauDefaultRatingSetup);

        if (isNewEntity)
            auditTrailService.saveCreatedData("EASU Rating Default Setup", esauDefaultRatingSetup);
        else
            auditTrailService.saveUpdatedData("EASU Rating Default Setup", previousEntity, esauDefaultRatingSetup);
        return "redirect:/collection/kpi/esau/create";
    }
}
