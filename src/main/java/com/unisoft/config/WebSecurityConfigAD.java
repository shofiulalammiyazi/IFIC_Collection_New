package com.unisoft.config;

import com.unisoft.auth.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@Order(2)
public class WebSecurityConfigAD extends WebSecurityConfigurerAdapter {


    @Autowired
    private CustomAccessDeniedHandler customAccessDeniedHandler;

    @Autowired
    private CustomAuthenticationProvider customAuthenticationProvider;

    @Autowired
    private CustomLoginSuccessHandler customLoginSuccessHandler;

    @Autowired
    private ADCustomLoginSuccessHandler adCustomLoginSuccessHandler;

    @Autowired
    private CustomWebAuthenticationDetailsSource authenticationDetailsSource;

    //@Value("ificbankbd.com")
    private String AD_DOMAIN = "{cn=ificbankbd.com,ou=users,ou=system}";

    //@Value("192.168.1.5")
    private String AD_URL = "ldap://localhost:10389";//"192.168.1.5";

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests().anyRequest().authenticated().and().httpBasic();
//    }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder authManagerBuilder) throws Exception {
//        authManagerBuilder.authenticationProvider(activeDirectoryLdapAuthenticationProvider()).userDetailsService(userDetailsService());
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager() {
//        return new ProviderManager(Arrays.asList(activeDirectoryLdapAuthenticationProvider()));
//    }
//    @Bean
//    public AuthenticationProvider activeDirectoryLdapAuthenticationProvider() {
//        ActiveDirectoryLdapAuthenticationProvider provider = new ActiveDirectoryLdapAuthenticationProvider(AD_DOMAIN, AD_URL);
//        provider.setConvertSubErrorCodesToExceptions(true);
//        provider.setUseAuthenticationRequestCredentials(true);
//
//        return provider;
//    }

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler1() {
        return new CustomLogoutSuccessHandler();
    }


//    @Bean
//    public AuthenticationFailureHandler authenticationFailureHandler() {
//        return new ADCustomAuthenticationFailureHandler();
//    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers("/**/*.css", "/**/*.png", "/**/*.map",
                        "/**/*.jpg", "/**/*.ico", "/**/*.woff2",
                        "/auth/password-reset/**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .authenticationDetailsSource(authenticationDetailsSource)
                .failureUrl("/loginError")
                .successHandler(adCustomLoginSuccessHandler)
                .loginPage("/")
                .permitAll()
                .failureHandler(new ADCustomAuthenticationFailureHandler())
                .and()
                .exceptionHandling().accessDeniedHandler(customAccessDeniedHandler)
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .logoutSuccessHandler(logoutSuccessHandler1())
                .permitAll();

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.ldapAuthentication()
                .userSearchFilter("(sAMAccountName={0})")
                .userSearchBase("DC=ificbankbd,DC=com")
//                .groupSearchBase("ou=groups,dc=some,dc=domain,dc=com")
//                .groupSearchFilter("member={0}")
                .contextSource()
                .url("ldap://192.168.1.5")
                //.port(639)
                .managerDn("cn=binduser,ou=users,dc=ificbankbd,dc=ificbankbd,dc=com")
                .managerPassword("14Aug14##003");
    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.ldapAuthentication()
//                .userDnPatterns("uid={0},ou=people")
//                .groupSearchBase("ou=groups")
//                .contextSource()
//                .url("ldap://localhost:8380/dc=springframework,dc=org")
//                .and()
//                .passwordCompare()
//                .passwordEncoder(new BCryptPasswordEncoder())
//                .passwordAttribute("userPassword").and().ldapAuthoritiesPopulator(new LDAPCustomAuthProvider());
//    }

//    @Bean
//    public AuthenticationManager configureManager(HttpSecurity httpSecurity)throws Exception{
//        AuthenticationManagerBuilder authenticationManagerBuilder =
//                httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);
//        authenticationManagerBuilder.ldapAuthentication()
//                .userDnPatterns("uid={0},ou=people").groupSearchBase("ou=groups")
//                .contextSource().url("ldap://localhost:8380/dc=springframework,dc=org")
//                .and()
//                .passwordCompare().passwordEncoder(new BCryptPasswordEncoder()).passwordAttribute("userPassword");
//        return authenticationManagerBuilder.build();
//    }

//    @Override
//    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
//        authenticationManagerBuilder
//                .ldapAuthentication()
//                .userDnPatterns("uid={0},ou=people").groupSearchBase("ou=groups")
//                .contextSource().url("ldap://localhost:8380/dc=springframework,dc=org")
//                .and()
//                .passwordCompare().passwordEncoder(new BCryptPasswordEncoder()).passwordAttribute("userPassword");
//
//        //authenticationManagerBuilder.build();
//
//        //                .contextSource().url("ldap://localhost:10389/")
////                .managerDn("uid=admin,ou=system").managerPassword("secret")
////                .and()
////                .userSearchBase("ou=system")
////                .userSearchFilter("(uid={0})");
//    }

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity httpSecurity)throws Exception{
//        httpSecurity
//                .httpBasic()
//                .and()
//                .authorizeRequests()
//                .antMatchers("/**/*.css", "/**/*.png", "/**/*.map",
//                        "/**/*.jpg", "/**/*.ico", "/**/*.woff2",
//                        "/auth/password-reset/**")
//                .permitAll()
//                .anyRequest()
//                .authenticated()
//                .and()
//                .formLogin()
//                .authenticationDetailsSource(authenticationDetailsSource)
//                .failureUrl("/loginError")
//                .successHandler(customLoginSuccessHandler)
//                .loginPage("/")
//                .permitAll()
//                .and()
//                .exceptionHandling().accessDeniedHandler(customAccessDeniedHandler)
//                .and()
//                .logout()
//                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//                .logoutSuccessUrl("/")
//                .deleteCookies("JSESSIONID")
//                .invalidateHttpSession(true)
//                .logoutSuccessHandler(logoutSuccessHandler1())
//                .permitAll();
//
//        return httpSecurity.build();
//    }
//
//    @Bean
//    public AuthenticationManager configureManager(HttpSecurity httpSecurity)throws Exception{
//        AuthenticationManagerBuilder authenticationManagerBuilder =
//                httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);
//        authenticationManagerBuilder.ldapAuthentication()
//                .userDnPatterns("uid={0},ou=people").groupSearchBase("ou=groups")
//                .contextSource().url("ldap://localhost:8389/dc=springframework,dc=org")
//                .and()
//                .passwordCompare().passwordEncoder(new BCryptPasswordEncoder()).passwordAttribute("userPassword");
//        return authenticationManagerBuilder.build();
//    }
}