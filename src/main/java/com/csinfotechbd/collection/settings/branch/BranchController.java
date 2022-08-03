package com.csinfotechbd.collection.settings.branch;

import com.csinfotechbd.collection.settings.district.DistrictService;
import com.csinfotechbd.user.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

/**
 * Created by imran on 09/07/2018.
 * Updated By Monirul Islam on 05/07/2019
 */


@Controller
public class BranchController {

    @Autowired
    private BranchService branchService;

    @Autowired
    private DistrictService districtService;

    @GetMapping("/branch/list")
    public String index(ModelMap map) {
        List<Branch> branchelist = branchService.getList();
        map.addAttribute("branchelist", branchelist);
        return "card/contents/settings/branch/branch";
    }

    @ResponseBody
    @GetMapping(value = "/branch/listbydistrict")
    public List listByDistrict(@RequestParam Long districtId) {
        return branchService.getByDistrict(districtId);
    }

    @GetMapping("/branch/create")
    public String createView(Model model) {
        model.addAttribute("disList", districtService.getActiveOnly());
        model.addAttribute("branch", new Branch());
        return "card/contents/settings/branch/create";
    }

    @PostMapping(value = "/branch/create")
    public String create(@Valid Branch branchEntity, BindingResult result) {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!result.hasErrors()) {
            branchService.saveOrUpdate(branchEntity, user.getUsername());
        } else {
            return "card/contents/settings/branch/create";
        }
        return "redirect:/branch/list";
    }

    @GetMapping("/branch/view")
    public String singleView(Model model, @RequestParam String id, HttpSession session, Principal principal) {
        int branchId = Integer.parseInt(id);
        Branch branch = branchService.findById(branchId);
        model.addAttribute("branch", branch);
        return "card/contents/settings/branch/view";
    }

    @GetMapping("/branch/edit")
    public String editView(Model model, @RequestParam String id, HttpSession session, Principal principal) {
        int branchId = Integer.parseInt(id);
        Branch branch = branchService.findById(branchId);
        model.addAttribute("disList", districtService.getActiveOnly());
        model.addAttribute("branch", branch);
        return "card/contents/settings/branch/create";
    }

    @GetMapping(value = "/branch/remove")
    public String remove(@RequestParam String id, HttpSession session, Principal principal) {
        int branchId = Integer.parseInt(id);
        Branch branch = branchService.findById(branchId);
        branch.setDeleted(true);
        branchService.saveOrUpdate(branch, "admin");
        return "redirect:/branch/list";
    }

    @GetMapping("/branch/upload-excel")
    public String bulkUpload() {
        return "card/contents/settings/branch/upload";
    }




}
