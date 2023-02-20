package com.unisoft.user.ad;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.ldap.userdetails.LdapUserDetailsImpl;

import java.util.Collection;

@Data
public class UserADPrinciple extends LdapUserDetailsImpl{

//    private static final long serialVersionUID = 500L;
//    private String dn;
//    private String password;
//    private String username;
//    private Collection<GrantedAuthority> authorities;
//    private boolean accountNonExpired;
//    private boolean accountNonLocked;
//    private boolean credentialsNonExpired;
//    private boolean enabled;
//    private int timeBeforeExpiration;
//    private int graceLoginsRemaining;
}
