package com.unisoft.collection.automaticDistribution.samDistributionRule;
/*
Created by   Islam at 8/8/2019
*/

import com.unisoft.collection.settings.dpdBucket.DPDBucketService;
import com.unisoft.collection.settings.productType.ProductTypeRepository;
import com.unisoft.user.User;
import com.unisoft.user.UserDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

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
