package com.csinfotechbd.userrole;

import com.csinfotechbd.role.Role;
import com.csinfotechbd.role.RoleService;
import com.csinfotechbd.user.User;
import com.csinfotechbd.user.UserDto;
import com.csinfotechbd.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user-role/authentication")
public class UserRoleController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService rolesService;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private UserRoleDao userRoleDao;

    @GetMapping("/create")
    public String user_role_insert_page(Model model) {
        /*List<User> userlist = userService.getAll();
        model.addAttribute("userlist", userlist);

        List<Role> roleList=rolesService.getAllRoles();
        model.addAttribute("roleList",roleList);*/

        List<User> userlist = userService.getByEnabled(true);
        List<Role> roleList = rolesService.getActiveList();
        List<UserRoleDto> userRoleDtoList = userlist.stream().map(UserRoleDto::new).collect(Collectors.toList());
        model.addAttribute("userList", userRoleDtoList);
        model.addAttribute("roleList", roleList);
        model.addAttribute("user", new UserDto());
        return "card/contents/settings/userRole/create";
    }


    @PostMapping(value = "/create")
    public String save(@RequestParam(value = "userId") Long userId,
                       @RequestParam(value = "roleId") List<Integer> roleIdList) {
        boolean done = userRoleDao.insert(userId, roleIdList);
        return "redirect:/user-role/authentication/list";
    }

    @PostMapping(value = "/rest/create")
    @ResponseBody
    public String saveRest(@RequestBody User user) {
        List<Integer> roleIds = user.getRoles().stream().map(Role::getRoleId).collect(Collectors.toList());
        boolean done = userRoleDao.insert(user.getUserId(), roleIds);
        return "succeed";
    }

    @GetMapping(value = "/user/role-names")
    @ResponseBody
    public String checkAdmin(String pin) {
        String roleNames = userRoleDao.getRoleNamesByEmployeePin(pin).toLowerCase();
        return roleNames.contains("admin") ? "admin" : "others";
    }

    @GetMapping("/list")
    public String List(Model model) {
        List<UserRole> userRoleList = userRoleDao.getUserRoleList();
        model.addAttribute("userRoleList", userRoleList);
        return "card/contents/settings/userRole/user-role";
    }

    @RequestMapping(value = "/getRolesByid", method = RequestMethod.GET)
    @ResponseBody
    public List<UserRole> getRoleByUid(@RequestParam(value = "userId") Long userId) throws Exception {
        return userRoleDao.getUserByUid(userId);
    }

    @GetMapping(value = "addRole")
    public String addRoleByUid(@RequestParam(value = "id") Long userId, Model model) {
        List<User> userlist = userService.getByEnabled(true);
        List<Role> roleList = rolesService.getActiveList();
        List<UserRoleDto> userRoleDtoList = userlist.stream().map(UserRoleDto::new).collect(Collectors.toList());
        User user = userService.findById(userId);
        model.addAttribute("userList", userRoleDtoList);
        model.addAttribute("roleList", roleList);
        model.addAttribute("user", new UserDto(user));
        return "card/contents/settings/userRole/create";
    }
}
