package com.unisoft.user;

import com.unisoft.audittrail.AuditTrailService;
import com.unisoft.collection.agency.agencyStatus.AgencyStatusEntity;
import com.unisoft.collection.agency.agencyStatus.AgencyStatusService;
import com.unisoft.collection.agency.agencyStatusManager.AgencyStatusManagerEntity;
import com.unisoft.collection.agency.agencyStatusManager.AgencyStatusManagerService;
import com.unisoft.collection.settings.agency.AgencyDto;
import com.unisoft.collection.settings.agency.AgencyEntity;
import com.unisoft.collection.settings.employee.EmployeeInfoEntity;
import com.unisoft.collection.settings.employeeStatus.EmployeeStatusEntity;
import com.unisoft.collection.settings.employeeStatus.EmployeeStatusService;
import com.unisoft.utillity.StringUtils;
import com.unisoft.workflow.propertyBasedMakerChecker.PropertyBasedMakerCheckerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDao userDao;

    private final EmployeeStatusService employeeStatusService;

    private final UserRepository repository;

    private final PropertyBasedMakerCheckerService<User> makerCheckerService;

    private final AgencyStatusManagerService agencyStatusManagerService;

    private final AgencyStatusService agencyStatusService;

    private final AuditTrailService auditTrailService;

    public List<User> getAll() {
        return userDao.findAll();
    }

    public List<User> getByEnabled(boolean enabled) {
        return repository.findByEnabled(enabled);
    }

    public User getUserByUsername(String username) {
        return repository.findUserByUsername(username);
    }

    public static String getCurrentUserFullName() {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user == null ? "System" : user.getFirstName() + " " + user.getLastName();
    }

    public static String getSessionUsername() {
        try {
            UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return user != null ? user.getUsername() : "System";
        } catch (Exception ignored) {
        }
        return "System";
    }

    @Transactional
    public void saveOrUpdate(User user, EmployeeInfoEntity employee) {

        if (user.getUserId() == null) {
            user.setCreatedBy(employee.getCreatedBy());
            user.setCreatedDate(new Date());
            //user.setUsername(employee.getPin());
            user.setUsername(employee.getEmail());
            //user.setEmployeeId(employee.getPin());
            user.setIsAgency(false);
            user.setStatus(true);
            user.setEnabled(false);
            user.setPassword(new BCryptPasswordEncoder().encode("A123"));
            userDao.save(user);
        } else {
            User oldEntity = userDao.findById(user.getUserId());
            EmployeeStatusEntity empStatus = employeeStatusService.getById(employee.getEmployeeStatus().getId());

            if (empStatus.isLoginDisable() || StringUtils.hasText(oldEntity.getRemark()))
                oldEntity.setEnabled(false);
            else
                oldEntity.setEnabled(true);

            oldEntity.setUsername(employee.getEmail());
            oldEntity.setEmployeeId(employee.getPin());
            oldEntity.setStatus(true);
            // oldEntity.setBranch(user.getBranch());
            oldEntity.setLastName(user.getLastName());
            oldEntity.setFirstName(user.getFirstName());
            oldEntity.setModifiedBy(employee.getModifiedBy());
            oldEntity.setModifiedDate(new Date());

            userDao.update(oldEntity);
        }
        makerCheckerService.makePending(User.class, "userId", user.getUserId());
    }

    public void updatePassword(User user, String userName) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userDao.update(user);
    }

    public User findById(long userId) {
        return userDao.findById(userId);

    }

    public void attachRolesWithUser(long userId, List<Integer> roleIds) {
        userDao.addRolesWithUser(userId, roleIds);
    }

    public List<String> getRoleList(String userId) {
        return userDao.getRolesByUID(userId);
    }

    public boolean checkUserName(String userName) {
        return userDao.checkUserName(userName);
    }

    public boolean releaseLock(String username) {
        User user = userDao.findUserByUsername(username);

        if (user.getLoginLockedAttempts() == 1) {
            user.setLoginFailureAttempts(0);
            user.setLoginLockedAttempts(0);
            user.setLockedTime(null);

            userDao.update(user);
            return true;
        } else
            return false;
    }

    public void saveAgency(AgencyDto agencyDto, AgencyEntity agencyEntity1) {
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = new User();
//        user.setFirstName(agencyDto.getFirstName());
//        user.setLastName(agencyDto.getLastName());
        user.setCreatedBy(userPrincipal.getUsername());
        user.setCreatedDate(new Date());
        user.setUsername(agencyDto.getPin());
        user.setPassword(new BCryptPasswordEncoder().encode(agencyDto.getPassword()));
        user.setIsAgency(true);
        user.setAgencyId(String.valueOf(agencyEntity1.getId()));
        user.setStatus(true);
        User user1 = repository.save(user);
        AgencyStatusEntity agencyStatusEntity = new AgencyStatusEntity();
        agencyStatusEntity.setLoginDisable(false);
        agencyStatusEntity.setDescription("login for agency");
        agencyStatusEntity.setCode(agencyDto.getPin());
        AgencyStatusEntity agencyStatusEntity1 = agencyStatusService.saveAgencyStatusEntity(agencyStatusEntity);
        auditTrailService.saveCreatedData("Agency Status",agencyStatusEntity1);

        AgencyStatusManagerEntity agencyStatusManagerEntity = new AgencyStatusManagerEntity();
        agencyStatusManagerEntity.setUserId(user1.getUserId());
        agencyStatusManagerEntity.setAgencyEntity(agencyEntity1);
        agencyStatusManagerEntity.setAgencyStatusEntity(agencyStatusEntity1);
        AgencyStatusManagerEntity agencyStatusManagerEntity1 = agencyStatusManagerService.save(agencyStatusManagerEntity);
        auditTrailService.saveCreatedData("Agency Status Manager Entiry", agencyStatusManagerEntity1);
    }
}
