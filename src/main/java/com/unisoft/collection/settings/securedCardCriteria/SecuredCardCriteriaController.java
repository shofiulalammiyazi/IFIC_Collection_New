package com.unisoft.collection.settings.securedCardCriteria;


import com.unisoft.user.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

@Controller
@RequestMapping(value = "/collection/securedCardCriteria/")
public class SecuredCardCriteriaController {

    @Autowired
    private SecuredCardCriteriaService securedCardCriteriaService;

    @GetMapping(value = "list")
    public String viewList(Model model) {
        model.addAttribute("secCardList", securedCardCriteriaService.getAll());
        return "collection/settings/securedcardcriteria/securedcardcriteria";
    }

    @GetMapping(value = "create")
    public String addPage(Model model) {
        SecuredCardCriteriaEntity cardCriteriaEntity = securedCardCriteriaService.getSecuredCardLatest();
        if (cardCriteriaEntity == null) cardCriteriaEntity = new SecuredCardCriteriaEntity();

        model.addAttribute("secCardNew", cardCriteriaEntity);
        return "collection/settings/securedcardcriteria/create";
    }

    @PostMapping(value = "create", consumes = (MediaType.APPLICATION_FORM_URLENCODED_VALUE))
    public String createDiv(Model model, SecuredCardCriteriaEntity securedCardCriteriaEntity) {

        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (securedCardCriteriaEntity.getId() == null) {
            securedCardCriteriaEntity.setCreatedBy(user.getUsername());
            securedCardCriteriaEntity.setCreatedDate(new Date());
            securedCardCriteriaService.saveNew(securedCardCriteriaEntity);
        } else {
            securedCardCriteriaEntity.setModifiedBy(user.getUsername());
            securedCardCriteriaEntity.setModifiedDate(new Date());
            securedCardCriteriaService.updateSecured(securedCardCriteriaEntity);
        }
        return "redirect:/collection/securedCardCriteria/list";
    }
}
