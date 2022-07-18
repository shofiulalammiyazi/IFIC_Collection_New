package com.csinfotechbd.collection.automaticDistribution.samDistributionRule;
/*
Created by Monirul Islam at 8/8/2019
*/

import com.csinfotechbd.collection.distribution.samAccountHandover.SamAccountHandoverInfo;
import com.csinfotechbd.collection.settings.ageCode.AgeCodeService;
import com.csinfotechbd.collection.settings.dpdBucket.DPDBucketEntity;
import com.csinfotechbd.collection.settings.dpdBucket.DPDBucketService;
import com.csinfotechbd.collection.settings.productGroup.ProductGroupEntity;
import com.csinfotechbd.collection.settings.productGroup.ProductGroupService;
import com.csinfotechbd.collection.settings.productType.ProductTypeEntity;
import com.csinfotechbd.collection.settings.productType.ProductTypeRepository;
import com.csinfotechbd.collection.settings.productType.ProductTypeService;
import com.csinfotechbd.user.User;
import com.csinfotechbd.user.UserDao;
import com.csinfotechbd.user.UserPrincipal;
import com.google.gson.Gson;
import com.sun.istack.Nullable;
import lombok.RequiredArgsConstructor;
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
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/collection/automaticdistribution/sam/")
public class SamDistributionController {

    private final SamDistributionService samDistributionService;

    private final ProductTypeRepository productTypeRepository;

    private final DPDBucketService dpdBucketService;

    private final UserDao userDao;

    @GetMapping(value = "list")
    public String viewAll(Model model) {
        List<SamDistributionRuleInfo> list = samDistributionService.getAll();
        User user = getUserFromSamRuleList(list);
        model.addAttribute("userId", user.getEmployeeId());
        model.addAttribute("userName", user.getFirstName());
        model.addAttribute("samAccountList", list);
        return "collection/automaticDistribution/samDistributionRule/samRule";
    }

    @GetMapping(value = "create")
    public String addpage(Model model) {

        SamDistributionRuleInfo samDistributionRuleInfo = samDistributionService.getById(null);

        model.addAttribute("selectedProductList", samDistributionRuleInfo.getProductTypeEntityList());
        model.addAttribute("selectedDpdBucketList", samDistributionRuleInfo.getDpdBucketEntities());
        model.addAttribute("productList", productTypeRepository.findByProductGroupEntityCardAccount("Loan"));
        model.addAttribute("dpdBucketList", dpdBucketService.getAll());
        model.addAttribute("sam", samDistributionRuleInfo);
        return "collection/automaticDistribution/samDistributionRule/create";
    }

    @PostMapping(value = "create")
    public String create(Model model, SamDistributionRuleInfo samDistributionRuleInfo) {
        String output = samDistributionService.save(samDistributionRuleInfo);
        switch (output) {
            case "1":
                break;
            default:
                model.addAttribute("error", output);
        }
        return "redirect:/collection/automaticdistribution/sam/create";
    }

    private User getUserFromSamRuleList(List<SamDistributionRuleInfo> list) {
        Long userId = null;
        if (!list.isEmpty()) {
            SamDistributionRuleInfo samRule = list.get(0);
            String user = samRule.getCreatedBy() == null ? samRule.getModifiedBy() : samRule.getCreatedBy();
            userId = (list.isEmpty()) ? null : Long.valueOf(user);
        }
        return (userId == null) ? new User() : userDao.findById(userId);

    }
}
