package com.unisoft.collection.automaticDistribution.postInterim;
/*
Created by   Islam at 8/20/2019
*/

import com.unisoft.collection.settings.ageCode.AgeCodeEntity;
import com.unisoft.collection.settings.ageCode.AgeCodeService;
import com.unisoft.collection.settings.productType.ProductTypeEntity;
import com.unisoft.collection.settings.productType.ProductTypeRepository;
import com.unisoft.collection.settings.producttypecard.ProductTypeCardService;
import com.unisoft.user.UserPrincipal;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/collection/postinterim/")
public class PostInterimController {

    @Autowired
    private PostInterimService postInterimService;

    @Autowired
    private ProductTypeRepository productTypeRepository;

    @Autowired
    private AgeCodeService ageCodeService;
    
    @Autowired
    private ProductTypeCardService productTypeCardService;

    @GetMapping(value = "create")
    public String addpage(Model model) {
        List<PostInterimCondition> postInterimConditions = postInterimService.getAll();
        PostInterimCondition attributeValue = new PostInterimCondition();

        if (postInterimConditions.size() > 0)
            attributeValue = postInterimConditions.get(0);

        model.addAttribute("postInterim", attributeValue);
        List<AgeCodeEntity> all = ageCodeService.getAll();

        List<AgeCodeEntity> x = all.stream().filter(d -> {
            char c = d.getName().charAt(0);
            return (!(c >= 'A' && c <= 'Z'));
        }).collect(Collectors.toList());

        all.removeAll(x);
        all.sort(Comparator.comparing(AgeCodeEntity::getName));
        x.sort(Comparator.comparingInt(o -> Integer.parseInt(o.getName())));
        all.addAll(x);

        Gson gson = new Gson();
        model.addAttribute("productOptons", gson.toJson(productTypeRepository.findByProductGroupEntityCardAccount("Card")));
        model.addAttribute("ageCodeOptons", gson.toJson(all));
        model.addAttribute("productTypeCardList",gson.toJson(productTypeCardService.getAllActive()));

        if (attributeValue.getId() != null) {
            model.addAttribute("selectedProduct", gson.toJson(attributeValue.getProductGroupEntities()));
            model.addAttribute("selectedAgeCode", gson.toJson(attributeValue.getAgeCodeEntities()));
            model.addAttribute("selectedProductTypeCardList",gson.toJson(productTypeCardService.getAllActive()));

        }
        return "collection/automaticDistribution/postInterim/create";
    }

    @PostMapping(value = "create")
    public String create(Model model, PostInterimCondition postInterim) {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<ProductTypeEntity> productGroupEntites = new ArrayList<>();
        List<AgeCodeEntity> ageCodeEntities = new ArrayList<>();

        if (postInterim.getId() == null) {
            for (String id : postInterim.getProductGroupId()) {
                ProductTypeEntity productGroup = productTypeRepository.findById(Long.parseLong(id)).get();
                productGroupEntites.add(productGroup);
            }

            for (String id : postInterim.getAgeCodeId()) {
                AgeCodeEntity ageCodeEntity = ageCodeService.getById(Long.parseLong(id));
                ageCodeEntities.add(ageCodeEntity);
            }

            postInterim.setAgeCodeEntities(ageCodeEntities);
            postInterim.setProductGroupEntities(productGroupEntites);
            postInterim.setCreatedBy(user.getUsername());
            postInterim.setCreatedDate(new Date());
            postInterimService.saveNew(postInterim);
        } else {
            for (String id : postInterim.getProductGroupId()) {
                ProductTypeEntity productGroup = productTypeRepository.findById(Long.parseLong(id)).get();
                productGroupEntites.add(productGroup);
            }

            for (String id : postInterim.getAgeCodeId()) {
                AgeCodeEntity ageCodeEntity = ageCodeService.getById(Long.parseLong(id));
                ageCodeEntities.add(ageCodeEntity);
            }

            postInterim.setAgeCodeEntities(ageCodeEntities);
            postInterim.setProductGroupEntities(productGroupEntites);
            postInterim.setModifiedBy(user.getUsername());
            postInterim.setModifiedDate(new Date());
            postInterimService.updateAgency(postInterim);
        }
        return "redirect:/collection/postinterim/create";
    }
}
