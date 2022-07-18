package com.csinfotechbd.cardprofile.cardOtherAccountInfo;

import com.csinfotechbd.customerbasicinfo.CustomerBasicInfoService;
import com.csinfotechbd.user.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/cardOtherAccountInfo/")

public class CardOtherAccountInfoController {

    @Autowired
    private CardOtherAccountInfoService cardOtherAccountInfoService;

    @Autowired
    private CustomerBasicInfoService customerBasicInfoService;

    @GetMapping("list")
    public String viewList(Model model) {
        List<CardOtherAccountInfo> all = cardOtherAccountInfoService.getAll();
        model.addAttribute("cardotheraccountlist", all);
        return "collection/cardOtherAccountInfo/cardOtherAccountInfo";
    }

    @GetMapping("create")
    public String addpage(Model model) {
        model.addAttribute("customerList", customerBasicInfoService.getCustomerBasicInfoList());
        model.addAttribute("cardOther", new CardOtherAccountInfo());
        return "collection/cardOtherAccountInfo/create";
    }

    @PostMapping(value = "create")
    public String create(Model model, CardOtherAccountInfo cardOtherAccountInfo) {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (cardOtherAccountInfo.getId() == null) {
            cardOtherAccountInfo.setCreatedBy(user.getUsername());
            cardOtherAccountInfo.setCreatedDate(new Date());
            cardOtherAccountInfoService.saveNew(cardOtherAccountInfo);
        } else {
            cardOtherAccountInfo.setModifiedBy(user.getUsername());
            cardOtherAccountInfo.setModifiedDate(new Date());
            cardOtherAccountInfoService.updateAgency(cardOtherAccountInfo);
        }
        return "redirect:/cardOtherAccountInfo/list";
    }

    @GetMapping(value = "edit")
    public String editPage(@RequestParam(value = "id") Long id, Model model) {
        model.addAttribute("agency", cardOtherAccountInfoService.getById(id));
        return "collection/settings/agency/create";
    }

    @GetMapping(value = "view")
    public String viewPage(@RequestParam(value = "id") Long id, Model model) {
        model.addAttribute("agency", cardOtherAccountInfoService.getById(id));
        return "collection/settings/agency/create";
    }

    @GetMapping(value = "delete")
    public String delete(@RequestParam(value = "id") Long id) {
        cardOtherAccountInfoService.delete(id);
        return "redirect:/cardOtherAccountInfo/list";
    }
}
