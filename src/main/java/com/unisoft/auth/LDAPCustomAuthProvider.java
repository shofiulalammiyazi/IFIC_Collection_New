package com.unisoft.auth;

import com.unisoft.role.Role;
import com.unisoft.user.User;
import com.unisoft.user.UserDao;
import com.unisoft.user.UserPrincipal;
import com.unisoft.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.ldap.userdetails.LdapAuthoritiesPopulator;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

//@Component
@Configurable
public class LDAPCustomAuthProvider implements LdapAuthoritiesPopulator {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Collection<? extends GrantedAuthority> getGrantedAuthorities(DirContextOperations dirContextOperations, String userName) {
        UserRepository userRepository = (UserRepository) SpringApplicationContext.getBean("userRepository");
        UserDao userDao = (UserDao) SpringApplicationContext.getBean("userDao");
        //User user = userRepository.findUserByUsername(userName);
        User user = userDao.findUserAndRolesByUsername(userName);

        List<Role> roles = user.getRoles().stream().filter(Role::isEnabled).collect(Collectors.toList());
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        roles.forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName())));


        return authorities;
    }
}
