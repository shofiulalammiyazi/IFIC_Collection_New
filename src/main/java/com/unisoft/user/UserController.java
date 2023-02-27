package com.unisoft.user;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.unisoft.beans.Validation;
import com.unisoft.collection.settings.branch.Branch;
import com.unisoft.collection.settings.branch.BranchService;
import com.unisoft.collection.settings.loginPolicy.LoginPolicyEntity;
import com.unisoft.collection.settings.loginPolicy.LoginPolicyService;
import com.unisoft.workflow.propertyBasedMakerChecker.PropertyBasedMakerCheckerService;
import lombok.RequiredArgsConstructor;
import org.apache.chemistry.opencmis.commons.exceptions.CmisUnauthorizedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final BranchService branchService;

    private final UserService userService;

    private final PropertyBasedMakerCheckerService<User> makerCheckerService;

    private final LoginPolicyService loginPolicyService;

    @GetMapping("/user/list")
    public String index(ModelMap map, Model model) {
        List<User> userlist = userService.getAll();
//		map.addAttribute("userlist", userlist);
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        model.addAttribute("userlsit", gson.toJson(userlist));
        return "card/contents/settings/user/user";
    }

    @GetMapping("/user/create")
    public String createView(ModelMap map, HttpSession session, Principal principal) {
        map.addAttribute("user", new User());
        List<Branch> branchelist = branchService.getList();
        map.addAttribute("branchelist", branchelist);
        return "card/contents/settings/user/create";
    }

    @PostMapping(value = "/user/create")
    public String create(Model model, @Valid User user, BindingResult result, HttpSession session, Principal principal) {

//			userService.saveOrUpdate(user, String.valueOf(((UserPrincipal)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId()));
        return "redirect:/user/list";
    }

    @GetMapping("/user/list/approval")
    public String approve(Model model) {
        List<UserApprovalDto> userlist = userService.getAll().stream()
                .map(UserApprovalDto::new).collect(Collectors.toList());
        model.addAttribute("userlsit", userlist);
        return "card/contents/settings/user/approval";
    }

    @GetMapping(value = "/user/approve")
    @ResponseBody
    public String approve(String[] usernames, HttpSession session) {
        String urlList = Objects.toString(session.getAttribute("urlList"), "");
        if (urlList.contains("/role-checker")) {
            int numberOfModifiedEntries = makerCheckerService.approve(User.class, "username", usernames);
            return numberOfModifiedEntries > 0 ? "success" : "failed";
        }
        throw new CmisUnauthorizedException("Unauthorized Access");
    }

    @GetMapping(value = "/user/reject")
    @ResponseBody
    public String reject(String[] usernames, String remark, HttpSession session) {
        String urlList = Objects.toString(session.getAttribute("urlList"), "");
        if (urlList.contains("/role-checker")) {
            int numberOfModifiedEntries = makerCheckerService.reject(User.class, "username", usernames, remark);
            return numberOfModifiedEntries > 0 ? "success" : "failed";
        }
        throw new CmisUnauthorizedException("Access Denied");
    }

    @GetMapping("/user/view")
    public String singleView(Model model, @RequestParam String id, HttpSession session, Principal principal) {
        long userId = Long.parseLong(id);
        User user = userService.findById(userId);
        model.addAttribute("user", user);
        List<Branch> branchelist = branchService.getList();
        model.addAttribute("branchelist", branchelist);
        model.addAttribute("roleList", userService.getRoleList(id));
        return "card/contents/settings/user/view";
    }

    @GetMapping("/user/edit")
    public String editView(Model model, @RequestParam String id, HttpSession session, Principal principal) {
        long userId = Long.parseLong(id);
        User user = userService.findById(userId);
        model.addAttribute("user", user);
        List<Branch> branchelist = branchService.getList();
        model.addAttribute("branchelist", branchelist);
        return "card/contents/settings/user/create";
    }

    @GetMapping(value = "/user/remove")
    public String remove(@RequestParam String id, HttpSession session, Principal principal) {
        long userId = Long.parseLong(id);
        User user = userService.findById(userId);
        user.setDeleted(true);
//		userService.saveOrUpdate(user, "admin");
        return "redirect:/user/list";
    }

    @GetMapping(value = "/user/changePassword")
    public String changePass(Model model) {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        LoginPolicyEntity loginPolicy = loginPolicyService.getPolicy();
        model.addAttribute("loginPolicy", loginPolicy);
        model.addAttribute("user", user);
        return "card/contents/settings/user/changePassword";
    }

    @PostMapping(value = "/user/changePassword")
    public String savePass(Model model, @RequestParam Map<String, String> user) {
        User updatableUser = userService.findById(Long.parseLong(user.get("userId")));

        if (Validation.isContainsSpaces(user.get("password"))) {
            model.addAttribute("errors", "Space is not allowed");
        } else if (Validation.isContainsRepetitiveCharacters(user.get("password"))) {
            model.addAttribute("errors", "No repetitive characters allowed");
        } else if (new BCryptPasswordEncoder().matches(user.get("oldPassword"), updatableUser.getPassword())) {
            updatableUser.setPassword(user.get("password"));
            userService.updatePassword(updatableUser, String.valueOf(((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId()));
            return "redirect:/";
        }
        return "redirect:/user/changePassword";
    }

    @GetMapping(value = "/user/api/changepassword")
    @ResponseBody
    public boolean updatePassword(@RequestParam(value = "id") String id) {
        User updateableuser = userService.findById(new Long(id));
        updateableuser.setPassword("A123");
        userService.updatePassword(updateableuser, updateableuser.getUsername());
        return true;
    }


    @GetMapping("user/checkUsername")
    @ResponseBody
    public boolean checkUserName(@RequestParam(value = "userName") String userName) {
        return userService.checkUserName(userName);
    }

    @ResponseBody
    @GetMapping("/user/release-lock")
    public boolean releaseLock(@RequestParam(value = "username") String username) {
        boolean isSuccess = userService.releaseLock(username);
        return isSuccess;
    }

    @GetMapping("/disabled/user/list")
    public String disabledUserList(ModelMap map, Model model) {
        List<User> userlist = userService.getByEnabled(false);
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        model.addAttribute("userlsit", gson.toJson(userlist));
        return "auth/passwordReset/enable_user";
    }

}
