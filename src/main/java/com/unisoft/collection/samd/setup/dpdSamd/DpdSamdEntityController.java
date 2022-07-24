package com.unisoft.collection.samd.setup.dpdSamd;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/collection/samd/dpd")
public class DpdSamdEntityController {
    
    @Autowired
    private DpdSamdEntityService samdEntityService;
    
    @GetMapping(value = "/list")
    public String getAll(Model model){
        model.addAttribute("dpdList",samdEntityService.getActiveList());
        return "samd/setup/dpd/dpdList";
    }
    
    @GetMapping(value = "/create")
    public String create(Model model){
        model.addAttribute("dpd",new DpdSamdEntity());
        return "samd/setup/dpd/create";
    }
    
    @PostMapping(value = "/create")
    public String add(DpdSamdEntity dpdSamdEntity,Model model){
        String output = samdEntityService.save(dpdSamdEntity);
        switch (output) {
            case "1":
                return "redirect:/collection/samd/dpd/list";
            default:
                model.addAttribute("error", output);
        }
        model.addAttribute("dpd",dpdSamdEntity);
        return "samd/setup/dpd/create";
    }
    
    @GetMapping(value = "edit")
    public String editPage(@RequestParam(value = "id") Long id, Model model) {
        model.addAttribute("dpd", samdEntityService.getById(id));
        return "samd/setup/dpd/create";
    }
}
