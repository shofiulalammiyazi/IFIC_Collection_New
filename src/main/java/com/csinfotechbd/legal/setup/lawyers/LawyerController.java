package com.csinfotechbd.legal.setup.lawyers;

import com.csinfotechbd.collection.settings.branch.BranchService;
import com.csinfotechbd.collection.settings.district.DistrictService;
import com.csinfotechbd.collection.settings.division.DivisionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/legal/setup/lawyer/")
public class LawyerController {

    private final LawyerService lawyerService;
    private final BranchService branchService;
    private final DivisionService divisionService;

    @GetMapping("list")
    public String list(Model model) {
        model.addAttribute("lawyerlist", lawyerService.findByEnabled(true));
        return "legal/setup/lawyer/lawyer-list";
    }

    @GetMapping("create")
    public String save(Model model) {
        prepareFormModal(model, new Lawyer());
        return "legal/setup/lawyer/create";
    }

    @PostMapping("create")
    public String save(@Valid Lawyer lawyer, BindingResult result, Model model) {
        if (result.hasErrors()) return "legal/setup/lawyer/create";
        String output = lawyerService.save(lawyer);
        switch (output) {
            case "01":
                model.addAttribute("error", "Invalid phone number");
                break;
            case "02":
                model.addAttribute("error", "Invalid mobile number");
                break;
            case "03":
                model.addAttribute("error", "Phone number already exists");
                break;
            case "04":
                model.addAttribute("error", "Email already exists");
                break;
            case "05":
                model.addAttribute("error", "Mobile number already exists");
                break;
            case "06":
                model.addAttribute("error", "Lawyer Id already exists");
                break;
            default:
                return "redirect:/legal/setup/lawyer/list";
        }
        model.addAttribute("lawyer", lawyer);
        model.addAttribute("divisionList", divisionService.getActiveList());
        return "legal/setup/lawyer/create";
    }

    @GetMapping("edit")
    public String edit(@RequestParam("id") Long id, Model model) {
        prepareFormModal(model, lawyerService.findById(id));
        return "legal/setup/lawyer/create";
    }

    @GetMapping("view")
    public String view(@RequestParam(value = "id") Long id, Model model) {
        model.addAttribute("lawyer", lawyerService.findById(id));
        return "legal/setup/lawyer/view";
    }

    @GetMapping("upload-excel")
    public String bulkUpload() {
        return "legal/setup/lawyer/upload";
    }

    @PostMapping("upload-excel")
    public String bulkUpload(@RequestParam("file") MultipartFile multipartFile, HttpSession session) {
        List errors = lawyerService.saveFromExcel(multipartFile);
        session.setAttribute("errors", errors);
        return "redirect:list";
    }

    private void prepareFormModal(Model model, Lawyer lawyer) {
        model.addAttribute("lawyer", lawyer);
        model.addAttribute("branches", branchService.getActiveList());
//        model.addAttribute("districtList", districtService.getActiveOnly());
//        model.addAttribute("divisionList", divisionService.getActiveList());
    }

//    @GetMapping("remove")
//    public String delete(@RequestParam("id") Long id) {
//        lawyerService.deleteById(id);
//        return "redirect:/legal/setup/lawyer/list";
//    }

//    @RequestMapping("exists")
//    @ResponseBody
//    public String view(@RequestParam("id") String name) {
//        boolean exists = lawyerService.existsByName(name);
//        return String.valueOf(exists);
//    }


}
