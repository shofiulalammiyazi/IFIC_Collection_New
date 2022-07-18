package com.csinfotechbd.legal.setup.caseType;


import com.csinfotechbd.legal.setup.caseFiledSubType.CaseFiledSubType;
import com.csinfotechbd.legal.setup.caseFiledSubType.CaseFiledSubTypeService;
import com.csinfotechbd.legal.setup.caseFiledType.CaseFiledType;
import com.csinfotechbd.legal.setup.caseFiledType.CaseFiledTypeService;
import com.csinfotechbd.user.UserPrincipal;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/legal/setup/caseType/")
public class CaseTypeController {


    private CaseTypeService caseTypeService;
    private CaseFiledTypeService caseFiledTypeService;
    private CaseFiledSubTypeService caseFiledSubTypeService;


    @GetMapping("list")
    public String list(Model model) {
        List<CaseFiledType> caseFiledTypeList = caseFiledTypeService.findAll();
        List<CaseTypeWrapper> caseTypeWrapperList = new LinkedList<>();

        for (CaseFiledType caseFiledType : caseFiledTypeList) {
            List<CaseType> caseTypeList = caseTypeService.findByCaseFiledType(caseFiledType.getId());
            caseFiledType.setCaseTypeList(caseTypeList);
            caseTypeWrapperList.add(new CaseTypeWrapper(null, caseFiledType, null));
        }

        model.addAttribute("caseTypeWrapperList", caseTypeWrapperList);


        return "legal/setup/casetype/list";
    }

    @GetMapping("create")
    public String viewPage(Model model) {

        CaseTypeWrapper caseTypeWrapper = new CaseTypeWrapper();
        caseTypeWrapper.setCaseTypeList(Arrays.asList(new CaseType()));
        model.addAttribute("caseTypeWrapper", caseTypeWrapper);
        model.addAttribute("CaseFiledTypeList", caseFiledTypeService.findAll());
        model.addAttribute("caseFiledSubTypeList", caseFiledSubTypeService.findAll());
        model.addAttribute("createEditStatus", "create");
        return "legal/setup/casetype/create";
    }

    @PostMapping("create")
    public String saveCaseType(CaseTypeWrapper caseTypeWrapper, @RequestParam("createEditStatus") String createEditStatus, RedirectAttributes redirectAttributes) {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();


        List<CaseType> caseTypeList = caseTypeWrapper.getCaseTypeList();


        if (createEditStatus.equals("create")) {
            caseTypeList.forEach(c -> {
                c.setCaseFiledType(caseTypeWrapper.getCaseFiledType());
                c.setCaseFiledSubType(caseTypeWrapper.getCaseFiledSubType());
                c.setCreatedDate(new Date());
                c.setCreatedBy(user.getUsername());
            });
            caseTypeService.saveAllCaseType(caseTypeList);
        } else if (createEditStatus.equals("edit")) {
            caseTypeList.forEach(c -> {
                c.setCaseFiledType(caseTypeWrapper.getCaseFiledType());
                c.setCaseFiledSubType(caseTypeWrapper.getCaseFiledSubType());
                c.setModifiedDate(new Date());
                c.setModifiedBy(user.getUsername());
            });
            caseTypeService.updateAll(caseTypeList);
        } else {

            return "legal/setup/casetype/create";
        }

        redirectAttributes.addFlashAttribute("saved", "Saved Successfully");

        return "redirect:/legal/setup/caseType/list";

    }

    @GetMapping("edit-all")
    public String editAll(@RequestParam(value = "id") Long id, @RequestParam(value = "cid", required = false) Long cid, Model model) {

        CaseFiledType caseFiledType = caseFiledTypeService.findById(id);
        CaseType caseType = caseTypeService.findCaseType(cid);
        CaseFiledSubType caseFiledSubType = caseType.getCaseFiledSubType();
        CaseTypeWrapper caseTypeWrapper = null;

        if (caseFiledSubType == null) {
            List<CaseType> caseTypes = caseTypeService.findByCaseFiledType(id);
            caseTypeWrapper = new CaseTypeWrapper(caseTypes, caseFiledType, caseFiledSubType);
        } else {
            caseTypeWrapper = new CaseTypeWrapper(Arrays.asList(caseType), caseFiledType, caseFiledSubType);
        }

        model.addAttribute("caseTypeWrapper", caseTypeWrapper);
        model.addAttribute("CaseFiledTypeList", caseFiledTypeService.findAll());
        model.addAttribute("caseFiledSubTypeList", caseFiledSubTypeService.findAll());
        model.addAttribute("createEditStatus", "edit");


        return "legal/setup/casetype/create";

    }

    @GetMapping("edit")
    public String editIndividualCaseType(@RequestParam(value = "id") Long id, Model model) {

        CaseType caseType = caseTypeService.findCaseType(id);
        model.addAttribute("caseType", caseType);
        model.addAttribute("caseFiledTypeList", caseFiledTypeService.findAll());
        model.addAttribute("caseFiledSubTypeList", caseFiledSubTypeService.findAll());

        return "legal/setup/casetype/edit";
    }

    @PostMapping("editSave")
    public String saveIndividualCaseType(CaseType caseType) {
        caseTypeService.edit(caseType);
        return "redirect:/legal/setup/caseType/list";
    }

    @GetMapping("view")
    public String view(@RequestParam(value = "id") Long id, Model model) {
        //model.addAttribute("casetype", caseTypeRepository.findById(id).get());

        return "collection/settings/casetype/view";

    }


}
