package com.csinfotechbd.collection.samd.setup.categoryForDelinquency;


import com.csinfotechbd.audittrail.AuditTrailService;
import com.csinfotechbd.user.UserPrincipal;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/collection/samd/setup/category-for-delinquency")
public class CategoryForDelinquencyController {


    @Autowired
    private CategoryForDelinquencyService categoryForDelinquencyService;

    @Autowired
    private CategoryForDelinquencyRepository categoryForDelinquencyRepository;

    @Autowired
    private AuditTrailService auditTrailService;



    @GetMapping("/list")
    public String  list(Model model){
        List<CategoryDelinquency> categoryForDelinquencyList = categoryForDelinquencyService.findAll();
        model.addAttribute("categoryForDelinquencyList", categoryForDelinquencyList);
        return "samd/setup/categoryForDelinquency/list";
    }



    @GetMapping("/create")
    public String createCategoryForDelinquencyView(Model model){
        return prepareEditViewModel(model, new CategoryDelinquency());
    }

    @PostMapping("/create")
    public String createCategoryForDelinquency(Model model, @Valid CategoryDelinquency categoryForDelinquency, BindingResult result){
//        if (!result.hasErrors()){
//            categoryForDelinquencyService.save(categoryForDelinquency);
//            model.addAttribute("success", true);
//            return "redirect:/collection/samd/setup/category-for-delinquency/list";
//        }
//
//        model.addAttribute("errro", true);
//        return prepareEditViewModel(model, categoryForDelinquency);

        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(categoryForDelinquency.getId()==null){
            categoryForDelinquency.setCreatedBy(user.getUsername());
            categoryForDelinquency.setCreatedDate(new Date());
            categoryForDelinquencyService.save(categoryForDelinquency);
            auditTrailService.saveCreatedData("Category for delinquency", categoryForDelinquency);
        }else{
            CategoryDelinquency oldEntity = categoryForDelinquencyRepository.getOne(categoryForDelinquency.getId());
            CategoryDelinquency previousEntity = new CategoryDelinquency();
            BeanUtils.copyProperties(oldEntity,previousEntity);

            categoryForDelinquency.setModifiedBy(user.getUsername());
            categoryForDelinquency.setModifiedDate(new Date());
            categoryForDelinquencyRepository.save(categoryForDelinquency);
            auditTrailService.saveUpdatedData("Category for delinquency",previousEntity,categoryForDelinquency);
        }
            return "redirect:/collection/samd/setup/category-for-delinquency/list";

    }



    @GetMapping("/edit")
    public String edit(Model model, @RequestParam Long id){
        CategoryDelinquency categoryForDelinquency = categoryForDelinquencyService.findCategoryForDelinquencyById(id);
        return prepareEditViewModel(model, categoryForDelinquency);
    }



    private String prepareEditViewModel(Model model, CategoryDelinquency categoryForDelinquency) {
        model.addAttribute("categoryForDelinquency", categoryForDelinquency);
        return "samd/setup/categoryForDelinquency/create";
    }
}
