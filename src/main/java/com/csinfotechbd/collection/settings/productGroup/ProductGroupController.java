package com.csinfotechbd.collection.settings.productGroup;
/*
Created by Monirul Islam at 6/25/2019
*/

import com.csinfotechbd.audittrail.AuditTrailService;
import com.csinfotechbd.user.UserPrincipal;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

@Controller
@RequestMapping(value = "/collection/product_group/")
public class ProductGroupController {

    @Autowired
    private ProductGroupService productGroupService;

    @Autowired
    private AuditTrailService auditTrailService;

    @GetMapping(value = "list")
    public String viewAll(Model model) {
        model.addAttribute("groupList", productGroupService.getList());
        return "collection/settings/productGroup/product_group";
    }

    @GetMapping(value = "create")
    public String addPage(Model model) {
        model.addAttribute("group", new ProductGroupEntity());
        return "collection/settings/productGroup/create";
    }

    @PostMapping(value = "create")
    public String createGrp(ProductGroupEntity group) {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (group.getId() == null) {
            group.setCreatedBy(user.getUsername());
            group.setCreatedDate(new Date());
            productGroupService.saveGrp(group);
            auditTrailService.saveCreatedData("Product Group", group);
        } else {
            ProductGroupEntity oldEntity = productGroupService.getById(group.getId());
            ProductGroupEntity previousEntity = new ProductGroupEntity();
            BeanUtils.copyProperties(oldEntity, previousEntity);

            group.setModifiedBy(user.getUsername());
            group.setModifiedDate(new Date());
            productGroupService.updateGrp(group);
            auditTrailService.saveUpdatedData("Product Group", previousEntity, group);
        }
        return "redirect:/collection/product_group/list";
    }

    @GetMapping(value = "view")
    public String viewPage(Model model, @RequestParam(value = "id") Long Id) {
        model.addAttribute("group", productGroupService.getById(Id));
        return "collection/settings/productGroup/view";
    }

    @GetMapping(value = "edit")
    public String editPage(@RequestParam(value = "id") Long Id, Model model) {
        model.addAttribute("group", productGroupService.getById(Id));
        return "collection/settings/productGroup/create";
    }
}
