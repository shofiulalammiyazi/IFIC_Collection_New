package com.csinfotechbd.legal.setup.caseFiledSubType;

import com.csinfotechbd.legal.setup.caseFiledType.CaseFiledTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/legal/setup/casefiledsubtype/")
public class CaseFiledSubTypeController {

    private final CaseFiledSubTypeService caseFiledSubTypeService;
    private final CaseFiledTypeService caseFiledTypeService;

    @GetMapping("list")
    public String list(Model model) {
        model.addAttribute("casefiledsubtypelist", caseFiledSubTypeService.findAll());
        return "legal/setup/casefiledsubtype/list";
    }

    @GetMapping("create")
    public String viewPage(Model model) {
        model.addAttribute("caseFiledSubType", new CaseFiledSubType());
        model.addAttribute("caseFiledList", caseFiledTypeService.findAll());

        return "legal/setup/casefiledsubtype/create";
    }

    @PostMapping("create")
    public String saveLitigation(@Valid CaseFiledSubType casefiledsubtype, BindingResult result, Model model) {
        if (result.hasErrors()) return "legal/setup/casefiledsubtype/create";
        String output = caseFiledSubTypeService.save(casefiledsubtype);
        switch (output) {
            case "0":
                return "redirect:/legal/setup/casefiledsubtype/list";
            default:
                model.addAttribute("error", output);
        }

        model.addAttribute("caseFiledSubType", casefiledsubtype);
        model.addAttribute("caseFiledList", caseFiledTypeService.findAll());

        return "legal/setup/casefiledsubtype/create";

    }

//    @GetMapping("edit")
//    public String edit(@RequestParam("id") Long id, Model model) {
//        model.addAttribute("casefiledsubtype", casefiledsubtypeService.findById(id));
//        return "legal/setup/casefiledsubtype/create";
//    }
//
//    @GetMapping("view")
//    public String view(@RequestParam(value = "id") Long id, Model model) {
//        model.addAttribute("casefiledsubtype", casefiledsubtypeService.findById(id));
//        return "legal/setup/casefiledsubtype/view";
//    }

//    @GetMapping("remove")
//    public String delete(@RequestParam("id") Long id) {
//        casefiledsubtypeService.deleteById(id);
//        return "redirect:/legal/setup/casefiledsubtype/list";
//    }

//    @RequestMapping("exists")
//    @ResponseBody
//    public String view(@RequestParam("id") String name) {
//        boolean exists = casefiledsubtypeService.existsByName(name);
//        return String.valueOf(exists);
//    }


}
