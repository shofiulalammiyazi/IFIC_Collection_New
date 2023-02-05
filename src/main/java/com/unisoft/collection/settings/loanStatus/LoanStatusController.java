package com.unisoft.collection.settings.loanStatus;


import com.unisoft.audittrail.AuditTrailService;
import com.unisoft.user.UserPrincipal;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.jws.WebParam;
import java.util.Date;

@Controller
@RequestMapping(value = "/collection/loanstatus/")
public class LoanStatusController {

    @Autowired
    private LoanStatusService loanStatusService;

    @Autowired
    private AuditTrailService auditTrailService;


    @GetMapping(value = "list")
    public String viewAll(Model model) {
        model.addAttribute("loanStatus", loanStatusService.getAll());
        return "collection/settings/loanStatus/list";
    }

    @GetMapping(value = "create")
    public String addPage(Model model) {
        model.addAttribute("loanStatus", new LoanStatusEntity());
        return "collection/settings/loanStatus/create";
    }


    @PostMapping(value = "create")
    public String save(LoanStatusEntity loanStatusEntity) {
        loanStatusService.saveNew(loanStatusEntity);
        return "redirect:/collection/loanstatus/list?id="+loanStatusEntity.getId();
    }

    @GetMapping("edit")
    public String edit(Model model, Long id) {
        LoanStatusEntity loanStatus = loanStatusService.geByLoanStatusId(id);
        model.addAttribute("loanStatus", loanStatus);
        return "collection/settings/loanStatus/create";
    }

}
