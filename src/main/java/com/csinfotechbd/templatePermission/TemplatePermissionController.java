package com.csinfotechbd.templatePermission;


import com.csinfotechbd.audittrail.AuditTrailService;
import com.csinfotechbd.role.Role;
import com.csinfotechbd.role.RoleService;
import com.csinfotechbd.user.User;
import com.csinfotechbd.user.UserPrincipal;
import com.csinfotechbd.user.UserRepository;
import com.csinfotechbd.workflow.propertyBasedMakerChecker.PropertyBasedMakerCheckerService;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import com.csinfotechbd.utillity.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/user-role/template/urltolist/")
public class TemplatePermissionController {

    private final RoleService roleService;

    private final TextToUrlBaseRepository textToUrlBaseRepository;

    private final RoleToUrlRepository roleToUrlRepository;

    private final HttpSession permissionSession;

    private final UserRepository userRepository;

    private final AuditTrailService auditTrailService;
    private final PropertyBasedMakerCheckerService<Role> makerCheckerService;

    @GetMapping("edit")
    public String textToUrlList(@RequestParam(value = "id") String id, Model model) {
        Gson gson = new Gson();
//        List<TextToUrlBase> textToUrlBases = textToUrlBaseRepository.findAll();
        String selectedUrlList = "/";
        Role role = roleService.findById(Integer.parseInt(id));
        RoleToUrl roleToUrl = roleToUrlRepository.findByRole(role);


        if (roleToUrl == null) {
//            model.addAttribute("selectedUrlList", new ArrayList<String>());
//            model.addAttribute("roleToUrl", new RoleToUrl());
            model.addAttribute("selectedUrlList", selectedUrlList);
            model.addAttribute("roleToUrl", new RoleToUrl());
        } else {
//            String[] urls = gson.fromJson(roleToUrl.getUrl(), String[].class);
//            roleToUrl.setUrlList(urls);
//            model.addAttribute("selectedUrlList", roleToUrl.getUrlList());
            selectedUrlList = StringUtils.hasText(roleToUrl.getUrl()) ? roleToUrl.getUrl() : "/";
            model.addAttribute("roleToUrl", roleToUrl);
            model.addAttribute("selectedUrlList", selectedUrlList);
        }
//        model.addAttribute("list", textToUrlBases);
        model.addAttribute("role", role);
        return "common/templatePermission/urltotext";
    }

    @GetMapping("list")
    public String urlToListRoleList(Model model) {
        List<Role> allRoles = roleService.getActiveList();
        model.addAttribute("rolelist", allRoles);
        return "common/templatePermission/rolelist";
    }

    @PostMapping("create")
    public String urlToSave(RoleToUrl roleToUrl, HttpSession session) {
        Gson gson = new Gson();
        roleToUrl.setUrl(gson.toJson(roleToUrl.getUrlList()));
        roleToUrlRepository.save(roleToUrl);
//        updatePerMissionList(session); // Users supposed to get permissions only after checker approval
        auditTrailService.saveCreatedData("Role Permission", roleToUrl);


        makerCheckerService.makePending(Role.class, "roleId", roleToUrl.getRole().getRoleId());

        return "redirect:/user-role/template/urltolist/list";
    }

    public void updatePerMissionList(HttpSession session) {
        Gson gson = new Gson();
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User userEntity = userRepository.findById(userPrincipal.getId()).get();
        List<Role> roles = userEntity.getRoles();
        List<String> urlList = new ArrayList<>();

        roles.forEach(role -> {
            Arrays.stream(gson.fromJson(roleToUrlRepository.findByRole(role).getUrl(), String[].class)).forEach(urlList::add);
        });

        session.setAttribute("urlList", urlList);
    }
}
