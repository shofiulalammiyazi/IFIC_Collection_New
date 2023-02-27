package com.unisoft.collection.automaticDistribution.postInterimPeopleLoan;


import com.google.gson.Gson;
import com.unisoft.collection.settings.productType.ProductTypeEntity;
import com.unisoft.collection.settings.productType.ProductTypeRepository;
import com.unisoft.user.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "/collection/postpeople/loan/")
public class PostPeopleLoanController {

    @Autowired
    private PostPeopleLoanRepository postPeopleLoanRepository;

    @Autowired
    private ProductTypeRepository productTypeRepository;

    @GetMapping(value = "create")
    public String addpage(Model model) {
        List<PostPeopleLoan> postPeopleLoans = postPeopleLoanRepository.findAll();
        PostPeopleLoan postPeopleLoan = new PostPeopleLoan();

        if (postPeopleLoans.size() > 0) {
            postPeopleLoan = postPeopleLoans.get(0);
        }
        model.addAttribute("postpeople", postPeopleLoan);

        Gson gson = new Gson();
        List<String> samList = new ArrayList<String>() {{
            add("Yes");
            add("No");
        }};

        List<String> writeOffList = new ArrayList<String>() {{
            add("Yes");
            add("No");
        }};

        model.addAttribute("samValues", samList);
        model.addAttribute("writeOffValues", writeOffList);

        model.addAttribute("productOptons", gson.toJson(productTypeRepository.findByProductGroupEntityCardAccount("Loan")));
        if (postPeopleLoan.getId() != null)
            model.addAttribute("selectedProduct", gson.toJson(postPeopleLoan.getProductGroupEntityList()));

        return "collection/automaticDistribution/postPeopleLoan/create";
    }

    @PostMapping(value = "create")
    public String create(Model model, PostPeopleLoan postPeopleLoan) {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<ProductTypeEntity> productGroupEntites = new ArrayList<>();

        if (postPeopleLoan.getId() == null) {
            for (String id : postPeopleLoan.getProductGroupId()) {
                ProductTypeEntity productGroup = productTypeRepository.findById(Long.parseLong(id)).get();
                productGroupEntites.add(productGroup);
            }

            postPeopleLoan.setProductGroupEntityList(productGroupEntites);
            postPeopleLoan.setCreatedBy(user.getUsername());
            postPeopleLoan.setCreatedDate(new Date());
            postPeopleLoanRepository.save(postPeopleLoan);
        } else {
            for (String id : postPeopleLoan.getProductGroupId()) {
                ProductTypeEntity productGroup = productTypeRepository.findById(Long.parseLong(id)).get();
                productGroupEntites.add(productGroup);
            }

            postPeopleLoan.setProductGroupEntityList(productGroupEntites);
            postPeopleLoan.setModifiedBy(user.getUsername());
            postPeopleLoan.setModifiedDate(new Date());
            postPeopleLoanRepository.save(postPeopleLoan);
        }
        return "redirect:/collection/postpeople/loan/create";
    }

}
