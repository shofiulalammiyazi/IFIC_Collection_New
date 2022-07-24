package com.unisoft.collection.settings.ExchangeRate;

import com.unisoft.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/collection/exchangerate")
public class ExchangeRateController {

    @Autowired
    private ExchangeRateService exchangeRateService;

    @GetMapping(value = "/create")
    public String create(Model model){
        model.addAttribute("exchangeRate", new ExchangeRateEntity());
        return "collection/settings/exchangeRate/create";
    }

    @GetMapping(value = "/list")
    public String viewList(Model model){
        //List<ExchangeRateEntity> exchangeRateList = exchangeRateService.getExchangeRateList();
        ExchangeRateEntity exchangeRateList = exchangeRateService.findExchaneRate();
        model.addAttribute("exchangeRateList",exchangeRateList);
        return "collection/settings/exchangeRate/list";
    }

    @PostMapping(value = "/create", consumes = (MediaType.APPLICATION_FORM_URLENCODED_VALUE))
    public String createPage(ExchangeRateEntity exchangeRateEntity, Model model){
        String output = exchangeRateService.save(exchangeRateEntity);
        switch (output) {
            case "1":
                return "redirect:/collection/exchangerate/list";
            default:
                model.addAttribute("error", output);
        }
        model.addAttribute("exchangeRate",exchangeRateEntity);
        return "collection/settings/exchangeRate/create";
    }

    @GetMapping(value = "/edit")
    public String edit(@RequestParam(value = "id") Long id, Model model){
        ExchangeRateEntity exchangeRateEntity = exchangeRateService.getById(id);
        if(exchangeRateEntity == null)
            throw new NotFoundException(ExchangeRateEntity.class);
        model.addAttribute("exchangeRate",exchangeRateEntity);
        return "collection/settings/exchangeRate/create";
    }

    @GetMapping("view")
    public String viewLoc(@RequestParam(value = "id") Long id, Model model) {
        model.addAttribute("exchangeRate", exchangeRateService.getById(id));
        return "collection/settings/exchangeRate/view";
    }

}
