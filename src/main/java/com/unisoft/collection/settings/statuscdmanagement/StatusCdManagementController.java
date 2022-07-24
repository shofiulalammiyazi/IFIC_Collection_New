package com.unisoft.collection.settings.statuscdmanagement;

import com.unisoft.user.UserPrincipal;
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
@RequestMapping("/collection/status-cd")
public class StatusCdManagementController {
    @Autowired
    StatusCdManagementService service;

    @GetMapping("/create")
    public String create() {
        return "collection/settings/statuscd/create";

    }

    @GetMapping("/list")
    public String statuscdlist(Model model) {
        model.addAttribute("statuscdList", service.list());
        return "collection/settings/statuscd/status-cd";
    }

    @GetMapping("/view")
    public String view(@RequestParam(value = "id") Long id, Model model) {
        model.addAttribute("statuscd", service.findbyId(id));
        return "collection/settings/statuscd/view";
    }

    @PostMapping("/create")
    public String save(Model model, StatusCd statusCd) {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (statusCd.getId() == null) {
            statusCd.setCreatedBy(user.getUsername());
            statusCd.setCreatedDate(new Date());
            service.save(statusCd);

        } else {
            statusCd.setModifiedBy(user.getUsername());
            statusCd.setModifiedDate(new Date());
            service.save(statusCd);

        }
        return "redirect:/collection/status-cd/list";
    }

    @GetMapping("/edit")
    public String edit(Model model, @RequestParam(value = "id") Long id) {
        model.addAttribute("statusCd", service.findbyId(id));
        return "collection/settings/statuscd/create";

    }

}
