package com.unisoft.templatePermission;

import com.google.gson.Gson;
import com.unisoft.role.Role;
import com.unisoft.role.RoleService;
import com.unisoft.user.User;
import com.unisoft.user.UserPrincipal;
import com.unisoft.user.UserRepository;
import com.unisoft.utillity.HttpSessionUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class InterceptorConfig extends WebMvcConfigurerAdapter {

    private final PermissionInterceptor permissionInterceptor;
    private final RoleService roleService;
    private final UserRepository userRepository;
    private final RoleToUrlRepository roleToUrlRepository;

    private HttpSession permissionSession;
    public boolean check = false;

    @Autowired
    private HttpSessionUtils httpSessionUtils;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(permissionInterceptor);
    }

    public void BeginPermissionFilter(HttpServletRequest request) {
        Gson gson = new Gson();
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User userEntity = userRepository.findById(userPrincipal.getId()).orElse(null);
        if (userEntity == null) return;

        // Authorize active roles only
        List<Role> roles = userEntity.getRoles().stream().filter(Role::isEnabled).collect(Collectors.toList());

        // Get url list assigned to the user
        Set<String> urlList = new HashSet<>();
        for (Role role : roles) {
            RoleToUrl toUrl = roleToUrlRepository.findByRole(role);
            if (toUrl == null) continue;
            String jsonUrls = Optional.ofNullable(toUrl.getUrl()).orElse("['/']");
            String[] urlArray = gson.fromJson(jsonUrls, String[].class);
            List<String> urls = Arrays.asList(urlArray);
            urlList.addAll(urls);
        }
//        roles.forEach(role -> {
//            Arrays.stream(gson.fromJson(roleToUrlRepository.findByRole(role).getUrl(), String[].class)).forEach(urlList::add);
//        });

        if (roles.size() > 0) this.check = true;

        permissionSession = request.getSession(false);
        if (permissionSession == null) return;

        permissionSession.setAttribute("check", this.check);
        permissionSession.setAttribute("urlList", urlList);
    }


    public boolean isCheck() {
        return check;
    }


}
