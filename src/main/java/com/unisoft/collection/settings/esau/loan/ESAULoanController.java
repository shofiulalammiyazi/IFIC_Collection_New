package com.unisoft.collection.settings.esau.loan;
/*
  Created by Md.   Islam on 9/9/2019
*/

import com.unisoft.audittrail.AuditTrailService;
import com.unisoft.collection.settings.dpdBucket.DPDBucketService;
import com.unisoft.user.UserPrincipal;
import com.unisoft.user.UserService;
import org.springframework.beans.BeanUtils;
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
@RequestMapping(value = "/collection/loan/esau/")
public class ESAULoanController {

    @Autowired
    private ESAULoanService esauLoanService;

    @Autowired
    private DPDBucketService dpdBucketService;

    @Autowired
    private UserService userService;

    @Autowired
    private AuditTrailService auditTrailService;

    @GetMapping(value = "create")
    private String viewAddPage(Model model) {
        model.addAttribute("esauLoan", new ESAULoanEntity());
        return "collection/settings/esau/loan/esauLoan";
    }

    @GetMapping(value = "view")
    public String viewPage(Model model, @RequestParam(value = "id") Long id) {
        model.addAttribute("esauLoan", esauLoanService.getById(id));
        return "collection/settings/esau/loan/view";
    }

    @PostMapping(value = "create")
    public String create(ESAULoanEntity esauLoanEntity) {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String empId = userService.findById(user.getId()).getEmployeeId();

        esauLoanEntity.setModifiedBy(empId);
        esauLoanEntity.setModifiedDate(new Date());
        if (esauLoanEntity.getId() == null) {
            esauLoanEntity.setCreatedBy(empId);
            esauLoanEntity.setCreatedDate(new Date());
            boolean save = esauLoanService.saveNew(esauLoanEntity);
            auditTrailService.saveCreatedData("ESAU Rating Setup-Loan", esauLoanEntity);
        } else {
            ESAULoanEntity oldEntity = esauLoanService.getById(esauLoanEntity.getId());
            ESAULoanEntity previousEntity = new ESAULoanEntity();
            BeanUtils.copyProperties(oldEntity, previousEntity);

            boolean update = esauLoanService.updateData(esauLoanEntity);
            auditTrailService.saveUpdatedData("ESAU Rating Setup-Loan", previousEntity, esauLoanEntity);
        }
        return "redirect:/collection/loan/esau/list";
    }

    @GetMapping(value = "list")
    public String listView(Model model) {
        model.addAttribute("esauLoanList", esauLoanService.getAll());
        return "collection/settings/esau/loan/listView";
    }

    @GetMapping(value = "edit")
    public String editPage(Model model, @RequestParam(value = "id") Long id) {
        ESAULoanEntity esauLoanEntity = esauLoanService.getById(id);
        model.addAttribute("esauLoan", esauLoanEntity);
        return "collection/settings/esau/loan/esauLoan";
    }
}
