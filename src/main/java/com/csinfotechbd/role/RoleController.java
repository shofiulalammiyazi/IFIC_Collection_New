package com.csinfotechbd.role;

import com.csinfotechbd.workflow.propertyBasedMakerChecker.PropertyBasedMakerCheckerService;
import lombok.RequiredArgsConstructor;
import org.apache.chemistry.opencmis.commons.exceptions.CmisUnauthorizedException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Objects;


/**
 * Created by imran on 10/07/2018.
 */

@Controller
@RequiredArgsConstructor
public class RoleController {

    private final RoleService rolesService;

    private final PropertyBasedMakerCheckerService<Role> makerCheckerService;

    @GetMapping("/role/list")
    public String index(Model map) {
        List<Role> rolelist = rolesService.getAllRoles();
        map.addAttribute("rolelist", rolelist);
        return "card/contents/settings/role/role";
    }

    @GetMapping("/role/create")
    public String createView(Model map) {
        map.addAttribute("role", new Role());
        return "card/contents/settings/role/create";
    }

    @PostMapping(value = "/role/create")
    public String create(@Valid Role role, BindingResult result) {
        if (!result.hasErrors()) {
            rolesService.saveOrUpdate(role);
        } else {
            return "card/contents/settings/role/create";
        }
        return "redirect:/role/list";

    }

    @GetMapping("/role/view")
    public String singleView(Model model, @RequestParam String id) {
        int roleId = Integer.parseInt(id);
        Role role = rolesService.findById(roleId);
        System.err.println("ROLES : " + role);
        model.addAttribute("role", role);
        return "card/contents/settings/role/view";
    }

    @GetMapping("/role/edit")
    public String editView(Model model, @RequestParam String id) {
        int roleId = Integer.parseInt(id);
        Role role = rolesService.findById(roleId);
        model.addAttribute("role", role);
        return "card/contents/settings/role/create";
    }

    @GetMapping(value = "/role/remove")
    public String remove(@RequestParam String id) {
        int roleId = Integer.parseInt(id);
        Role role = rolesService.findById(roleId);
        role.setDeleted(true);
        rolesService.saveOrUpdate(role);
        return "redirect:/role/list";
    }

    @GetMapping("/role/list/approval")
    public String approve(Model model) {
        List<Role> list = rolesService.getAllRoles();
        model.addAttribute("list", list);
        return "card/contents/settings/role/approval";
    }

    @GetMapping(value = "/role/approve")
    @ResponseBody
    public String approve(Integer[] ids, HttpSession session) {
        String urlList = Objects.toString(session.getAttribute("urlList"), "");
        if (urlList.contains("/role-checker")) {
            int numberOfModifiedEntries = makerCheckerService.approve(Role.class, "roleId", ids);
            return numberOfModifiedEntries > 0 ? "success" : "failed";
        }
        throw new CmisUnauthorizedException("Unauthorized Access");
    }

    @GetMapping(value = "/role/reject")
    @ResponseBody
    public String reject(Integer[] ids, String remark, HttpSession session) {
        String urlList = Objects.toString(session.getAttribute("urlList"), "");
        if (urlList.contains("/role-checker")) {
            int numberOfModifiedEntries = makerCheckerService.reject(Role.class, "roleId", ids, remark);
            return numberOfModifiedEntries > 0 ? "success" : "failed";
        }
        throw new CmisUnauthorizedException("Access Denied");
    }


}
