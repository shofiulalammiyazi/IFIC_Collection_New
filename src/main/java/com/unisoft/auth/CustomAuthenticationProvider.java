package com.unisoft.auth;

import com.unisoft.collection.agency.agencyStatusManager.AgencyStatusManagerEntity;
import com.unisoft.collection.agency.agencyStatusManager.AgencyStatusManagerService;
import com.unisoft.collection.settings.employee.EmployeeService;
import com.unisoft.collection.settings.employeeStatusManagement.EmployeeStatusManagerEntity;
import com.unisoft.collection.settings.employeeStatusManagement.EmployeeStatusManagerRepository;
import com.unisoft.collection.settings.lateReason.LateReasonEntity;
import com.unisoft.collection.settings.lateReason.LateReasonRepository;
import com.unisoft.collection.settings.lateReason.LateReasonService;
import com.unisoft.collection.settings.lateReasonExplain.LateReasonExplainInfo;
import com.unisoft.collection.settings.lateReasonExplain.LateReasonExplainRepository;
import com.unisoft.collection.settings.lateReasonExplain.LateReasonExplainService;
import com.unisoft.role.Role;
import com.unisoft.user.User;
import com.unisoft.user.UserDao;
import com.unisoft.user.UserPrincipal;
import com.unisoft.user.UserRepository;
import com.unisoft.utillity.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserDao userDao;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private LateReasonExplainService lateReasonExplainService;
    @Autowired
    private LateReasonService lateReasonService;
    @Autowired
    private LateReasonRepository lateReasonRepository;
    @Autowired
    private EmployeeStatusManagerRepository empoyeeStatusMangerRepository;
    @Autowired
    private HttpSession session;
    @Autowired
    private LateReasonExplainRepository lateReasonExplainRepository;

    @Autowired
    private AgencyStatusManagerService agencyStatusManagerService;
    @Autowired
    private UserRepository userRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        session.setAttribute("logout", false);
        session.setAttribute("isLate", false);
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        User user = userDao.findUserAndRolesByUsername(username);
//        User user = userRepository.findUserByUsername(username);

        if (user.getIsAgency()) {
            String module = ((CustomWebAuthenticationDetails) authentication.getDetails()).getModudleName();
            if (user == null) {
                session.setAttribute("loginError", true);
                session.setAttribute("loginUnable", false);
                return null;
            }

            AgencyStatusManagerEntity agencyStatusManagerEntity = agencyStatusManagerService.findFirstByUserIdOrderByIdDesc(user.getUserId());
            boolean isPermanentLocked = false;
            boolean isTemporaryLocked = false;
            int tryAgainAfterMinutes = 30;    // Locked time minutes

            if (!agencyStatusManagerEntity.getAgencyStatusEntity().isLoginDisable()) {
                boolean endDateNull = agencyStatusManagerEntity.getEndDate() == null || agencyStatusManagerEntity.getStartDate().compareTo(new Date()) <= 0;
                boolean endDateNotNull = agencyStatusManagerEntity.getEndDate() != null && agencyStatusManagerEntity.getEndDate().compareTo(new Date()) < 0;

                if (endDateNull || endDateNotNull) {
                    isPermanentLocked = user.getLoginLockedAttempts() == 3;
                    isTemporaryLocked = user.getLoginFailureAttempts() != 0 && user.getLoginFailureAttempts() % 3 == 0;

                    if (isTemporaryLocked && !isPermanentLocked) {
                        long lockedTime = user.getLockedTime().getTime();
                        long currentTime = System.currentTimeMillis();
                        long minuteGoesAfterLockedTime = ((currentTime - lockedTime) / 1000) / 60;
                        tryAgainAfterMinutes -= (int) minuteGoesAfterLockedTime;
                    }

                    if (tryAgainAfterMinutes <= 0) {
                        isTemporaryLocked = false;
                        user.setLoginFailureAttempts(0);
                        session.setAttribute("errorMsg", false);
                    }

                    if (!isTemporaryLocked && !isPermanentLocked) {   // Check If the user is not locked for failure login & user not permanent locked
                        if (new BCryptPasswordEncoder().matches(password, user.getPassword())) {

                            // Authorize active roles only
                            List<Role> roles = user.getRoles().stream().filter(Role::isEnabled).collect(Collectors.toList());
                            //check role->  super admin and unit-> it security it is both
                            boolean isSuperAdmin = false;
                            boolean hasItSecurity = false;
                            for (Role role : roles) {
                                if (role.getName().toUpperCase().equals("SUPER_ADMIN") || role.getName().toUpperCase().equals("SUPERADMIN")) {
                                    isSuperAdmin = true;
                                    break;
                                }
                            }

                            List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
                            roles.forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName())));

                            String reset = !user.isPassReset() ? "false" : "true";

                            UserPrincipal userPrincipal = new UserPrincipal(user.getUserId(), user.getUsername(), user.getFirstName(), user.getLastName(), user.getAgencyId(), "null", module);

                            Date loginTime = null;
                            try {
                                loginTime = new SimpleDateFormat("yyyy-MM-dd hh:mm a").parse(LocalDate.now().toString() + " 06:00 AM");
                            } catch (ParseException e) {
                                System.out.println(e.getMessage());
                            }


                            LateReasonExplainInfo lateReasonExplainInfo = lateReasonExplainRepository.findByUserAndCreatedDateGreaterThan(user, loginTime);
                            boolean after = new Date().after(loginTime);

                            if (lateReasonExplainInfo == null && after && StringUtils.hasText(module)) {
                                LateReasonEntity lateReasonServiceById = lateReasonService.getById(Long.parseLong(module));
                                LateReasonExplainInfo lateReasonExplainInfo1 = new LateReasonExplainInfo();
                                lateReasonExplainInfo1.setUser(user);
                                lateReasonExplainInfo1.setLateReasonEntity(lateReasonServiceById);
                                lateReasonExplainInfo1.setCreatedBy(user.getUsername());
                                lateReasonExplainInfo1.setCreatedDate(new Date());
                                lateReasonExplainInfo1.setEnabled(true);

                                lateReasonExplainService.saveNew(lateReasonExplainInfo1);
                            }

                            /* For successful login, clear all failure errors */
                            user.setLoginFailureAttempts(0);
                            user.setLoginLockedAttempts(0);
                            user.setLockedTime(null);
                            userDao.update(user);

                            return new UsernamePasswordAuthenticationToken(userPrincipal, reset, authorities);
                        } else {
                            /* Mechanism to check login failure errors */
                            int failureAttempts = user.getLoginFailureAttempts() + 1;
                            user.setLoginFailureAttempts(failureAttempts);

                            if (failureAttempts % 3 == 0) {
                                SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");

                                Timestamp lockedTime = user.getLockedTime() == null ? new Timestamp(System.currentTimeMillis()) : user.getLockedTime();
                                String lastFailureDate = formatter.format(lockedTime);
                                String currentDate = formatter.format(new Date());

                                int lockedAttempts = lastFailureDate.equals(currentDate) ? user.getLoginLockedAttempts() + 1 : 1;
                                user.setLoginLockedAttempts(lockedAttempts);
                                user.setLockedTime(new Timestamp(System.currentTimeMillis()));
                                isTemporaryLocked = true;

                                isPermanentLocked = lockedAttempts == 3;
                            }

                            userDao.update(user);
                        }
                    }
                }
            }

            else System.err.println("Login error");

            session.setAttribute("loginError", true);
            session.setAttribute("errorMsg", false);
            session.setAttribute("loginUnable", false);

            if (isPermanentLocked)
                session.setAttribute("errorMsg", "User locked out due to invalid attempts.");
            else if (isTemporaryLocked)
                session.setAttribute("errorMsg", "Please try again after " + tryAgainAfterMinutes + " minutes.");

            if (isTemporaryLocked || isPermanentLocked) {
                session.setAttribute("loginError", false);
                session.setAttribute("loginUnable", false);
            }
            return null;

        } else {
            String module = ((CustomWebAuthenticationDetails) authentication.getDetails()).getModudleName();
            if (user == null) {
                session.setAttribute("loginError", true);
                session.setAttribute("loginUnable", false);
                return null;
            }

            EmployeeStatusManagerEntity employeeStatusManager = empoyeeStatusMangerRepository.findFirstByUserIdOrderByIdDesc(user.getUserId());
            boolean isPermanentLocked = false;
            boolean isTemporaryLocked = false;
            int tryAgainAfterMinutes = 30;    // Locked time minutes

            if (!employeeStatusManager.getEmployeeStatus().isLoginDisable()) {
                //Todo: modified endDateNull condition from '&&'  to '||'g
                boolean endDateNull = employeeStatusManager.getEndDate() == null || employeeStatusManager.getStartDate().compareTo(new Date()) <= 0;
                boolean endDateNotNull = employeeStatusManager.getEndDate() != null && employeeStatusManager.getEndDate().compareTo(new Date()) < 0;

                if (endDateNull || endDateNotNull) {
                    isPermanentLocked = user.getLoginLockedAttempts() == 3;
                    isTemporaryLocked = user.getLoginFailureAttempts() != 0 && user.getLoginFailureAttempts() % 3 == 0;

                    if (isTemporaryLocked && !isPermanentLocked) {
                        long lockedTime = user.getLockedTime().getTime();
                        long currentTime = System.currentTimeMillis();
                        long minuteGoesAfterLockedTime = ((currentTime - lockedTime) / 1000) / 60;
                        tryAgainAfterMinutes -= (int) minuteGoesAfterLockedTime;
                    }

                    if (tryAgainAfterMinutes <= 0) {
                        isTemporaryLocked = false;
                        user.setLoginFailureAttempts(0);
                        session.setAttribute("errorMsg", false);
                    }

                    if (!isTemporaryLocked && !isPermanentLocked) {   // Check If the user is not locked for failure login & user not permanent locked
                        if (new BCryptPasswordEncoder().matches(password, user.getPassword())) {

                            // Authorize active roles only
                            List<Role> roles = user.getRoles().stream().filter(Role::isEnabled).collect(Collectors.toList());
                            //check role->  super admin and unit-> it security it is both
                            boolean isSuperAdmin = false;
                            boolean hasItSecurity = false;
                            for (Role role : roles) {
                                if (role.getName().toUpperCase().equals("SUPER_ADMIN") || role.getName().toUpperCase().equals("SUPERADMIN")) {
                                    isSuperAdmin = true;
                                    break;
                                }
                            }
                            if (employeeService.getByPin(user.getUsername()).getUnit().toUpperCase().contains("IT SECURITY")) {
                                hasItSecurity = true;
                            }
                            if (isSuperAdmin && hasItSecurity) {
                                session.setAttribute("showSearchOption", true);

                            } else {
                                session.setAttribute("showSearchOption", false);
                            }
                            //check role->  super admin and unit-> it security it is both end

                            List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
                            roles.forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName())));

                            String reset = !user.isPassReset() ? "false" : "true";

                            UserPrincipal userPrincipal = new UserPrincipal(user.getUserId(), user.getUsername(), user.getFirstName(), user.getLastName(), user.getEmployeeId(), "null", module);

                            Date loginTime = null;
                            try {
                                loginTime = new SimpleDateFormat("yyyy-MM-dd hh:mm a").parse(LocalDate.now().toString() + " 06:00 AM");
                            } catch (ParseException e) {
                                System.out.println(e.getMessage());
                            }
                            LateReasonExplainInfo lateReasonExplainInfo = lateReasonExplainRepository.findByUserAndCreatedDateGreaterThan(user, loginTime);
                            List<LateReasonExplainInfo> lateReasonExplainInfos = lateReasonExplainRepository.findByUserIdAndDate(userPrincipal.getId());
                            boolean after = new Date().after(loginTime);

                            if (!isSuperAdmin && !hasItSecurity && after && !StringUtils.hasText(module) && lateReasonExplainInfos.size() == 0) {
                                session.setAttribute("isLate", true);
                                return null;
                            }

                            if((isSuperAdmin || hasItSecurity) && lateReasonExplainInfo == null){
                                LateReasonEntity regularId = lateReasonRepository.findByReasonTitle("Regular");
                                LateReasonExplainInfo lateReasonExplainInfo1 = new LateReasonExplainInfo();
                                lateReasonExplainInfo1.setUser(user);
                                lateReasonExplainInfo1.setLateReasonEntity(regularId);
                                lateReasonExplainInfo1.setCreatedBy(user.getUsername());
                                lateReasonExplainInfo1.setCreatedDate(new Date());
                                lateReasonExplainInfo1.setEnabled(true);
                                lateReasonExplainService.saveNew(lateReasonExplainInfo1);
                            } else if (lateReasonExplainInfo == null && after && StringUtils.hasText(module)) {
                                LateReasonEntity lateReasonServiceById = lateReasonService.getById(Long.parseLong(module));
                                LateReasonExplainInfo lateReasonExplainInfo1 = new LateReasonExplainInfo();
                                lateReasonExplainInfo1.setUser(user);
                                lateReasonExplainInfo1.setLateReasonEntity(lateReasonServiceById);
                                lateReasonExplainInfo1.setCreatedBy(user.getUsername());
                                lateReasonExplainInfo1.setCreatedDate(new Date());
                                lateReasonExplainInfo1.setEnabled(true);

                                lateReasonExplainService.saveNew(lateReasonExplainInfo1);
                            }
                            /* For successful login, clear all failure errors */
                            user.setLoginFailureAttempts(0);
                            user.setLoginLockedAttempts(0);
                            user.setLockedTime(null);
                            userDao.update(user);

                            return new UsernamePasswordAuthenticationToken(userPrincipal, reset, authorities);
                        } else {
                            /* Mechanism to check login failure errors */
                            int failureAttempts = user.getLoginFailureAttempts() + 1;
                            user.setLoginFailureAttempts(failureAttempts);

                            if (failureAttempts % 3 == 0) {
                                SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");

                                Timestamp lockedTime = user.getLockedTime() == null ? new Timestamp(System.currentTimeMillis()) : user.getLockedTime();
                                String lastFailureDate = formatter.format(lockedTime);
                                String currentDate = formatter.format(new Date());

                                int lockedAttempts = lastFailureDate.equals(currentDate) ? user.getLoginLockedAttempts() + 1 : 1;
                                user.setLoginLockedAttempts(lockedAttempts);
                                user.setLockedTime(new Timestamp(System.currentTimeMillis()));
                                isTemporaryLocked = true;

                                isPermanentLocked = lockedAttempts == 3;
                            }

                            userDao.update(user);
                        }
                    }
                } else return null;

            } else System.err.println("Login error");

            session.setAttribute("loginError", true);
            session.setAttribute("errorMsg", false);
            session.setAttribute("loginUnable", false);

            if (isPermanentLocked)
                session.setAttribute("errorMsg", "User locked out due to invalid attempts.");
            else if (isTemporaryLocked)
                session.setAttribute("errorMsg", "Please try again after " + tryAgainAfterMinutes + " minutes.");

            if (isTemporaryLocked || isPermanentLocked) {
                session.setAttribute("loginError", false);
                session.setAttribute("loginUnable", false);
            }
            return null;
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
