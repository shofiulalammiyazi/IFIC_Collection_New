package com.csinfotechbd.legal.setup.caseFiledType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/legal/setup/casefiledtype/")
public class CaseFiledTypeController {

    @Autowired
    private CaseFiledTypeService caseFiledTypeService;

    @GetMapping("list")
    public String list(Model model) {
        model.addAttribute("casefiledtypelist", caseFiledTypeService.findAll());
        return "legal/setup/casefiled/list";
    }

    @GetMapping("create")
    public String create(Model model) {
        model.addAttribute("casefiledtype", new CaseFiledType());
        return "legal/setup/casefiled/create";
    }

    @PostMapping("create")
    public String create(CaseFiledType caseFiledType, Model model) {
        String output = caseFiledTypeService.save(caseFiledType);
        switch (output) {
            case "1":
                return "redirect:/legal/setup/casefiledtype/list";
            default:
                model.addAttribute("message", output);
        }
        model.addAttribute("casefiledtype", caseFiledType);
        return "legal/setup/casefiled/create";
    }

    @GetMapping("edit")
    public String edit(@RequestParam("id") Long id, Model model) {
        model.addAttribute("casefiledtype", caseFiledTypeService.findById(id));
        return "legal/setup/casefiled/create";
    }

    @GetMapping("view")
    public String view(@RequestParam(value = "id") Long id, Model model) {
        model.addAttribute("casefiledtype", caseFiledTypeService.findById(id));
        return "legal/setup/casefiled/view";
    }

//    @GetMapping("delete")
//    public String del() {
//        caseFiledTypeRepository.deleteAll();
//        return "redirect:/legal/setup/casefiledtype/list";
//    }

    @RequestMapping("exists")
    @ResponseBody
    public String view(@RequestParam("id") String name) {
        boolean exists = caseFiledTypeService.existsByName(name);
        return String.valueOf(exists);
    }


}
