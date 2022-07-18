package com.csinfotechbd.collection.settings.esau.card;
/*
  Created by Md. Monirul Islam on 9/11/2019
*/

import com.csinfotechbd.collection.settings.ageCode.AgeCodeEntity;
import com.csinfotechbd.collection.settings.ageCode.AgeCodeService;
import com.csinfotechbd.user.UserPrincipal;
import com.csinfotechbd.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Controller
@RequestMapping(value = "/collection/card/esau/")
public class ESAUCardController {

    @Autowired
    private ESAUCardService esauCardService;

    @Autowired
    private UserService userService;

    @Autowired
    private AgeCodeService ageCodeService;

    @GetMapping(value = "create")
    private String viewAddPage(Model model)
    {

        List<Long> bucketIdList =new ArrayList<>();
        model.addAttribute("bucketIdList",bucketIdList);
        model.addAttribute("bucketList",ageCodeService.getActiveList());
        model.addAttribute("esauLoan",new ESAUCardEntity());
        return "collection/settings/esau/card/esauCard";
    }

    @GetMapping(value = "view")
    public String viewPage(Model  model, @RequestParam(value = "id")Long id)
    {
        model.addAttribute("esauLoan",esauCardService.getById(id));
        return "collection/settings/esau/card/view";
    }

    @PostMapping(value = "create")
    public String create(ESAUCardEntity esauLoanEntity,@RequestParam(value = "bucketIdList") List<Long> bucketIdList)
    {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String empId=userService.findById(user.getId()).getEmployeeId();

        esauLoanEntity.setModifiedBy(empId);
        esauLoanEntity.setModifiedDate(new Date());

        Set<AgeCodeEntity> ageCodeEntities=new HashSet<>();
        for(Long id:bucketIdList)
        {
            AgeCodeEntity ageCode=new AgeCodeEntity();
            ageCode.setId(id);
            ageCodeEntities.add(ageCode);
        }

        esauLoanEntity.setAgeCodeEntities(ageCodeEntities);
        if(esauLoanEntity.getId() == null)
        {
            esauLoanEntity.setCreatedBy(empId);
            esauLoanEntity.setCreatedDate(new Date());
            boolean save=esauCardService.saveNew(esauLoanEntity);
        }else {
            boolean update=esauCardService.updateData(esauLoanEntity);
        }
        return "redirect:/collection/card/esau/list";
    }

    @GetMapping(value = "list")
    public String listView(Model model)
    {
        model.addAttribute("esauCardList",esauCardService.getAll());
        return "collection/settings/esau/card/listView";
    }

    @GetMapping(value = "edit")
    public String editPage(Model model,@RequestParam(value = "id")Long id)
    {

        ESAUCardEntity esauLoanEntity=esauCardService.getById(id);
        //System.err.println("DPD :"+esauLoanEntity);
        List<Long> bucketIdList =new ArrayList<>();
        for(AgeCodeEntity ageCode: esauLoanEntity.getAgeCodeEntities())
        {
            bucketIdList.add(ageCode.getId());
            //System.err.println("DPD :"+dpdBucketEntity.getId());
        }

        model.addAttribute("bucketIdList",bucketIdList);
        model.addAttribute("bucketList",ageCodeService.getActiveList());
        model.addAttribute("esauLoan",esauLoanEntity);
        return "collection/settings/esau/card/esauCard";
    }
}
