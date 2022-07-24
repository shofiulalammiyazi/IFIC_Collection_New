package com.unisoft.userrole;
/*
Created by   Islam at 3/18/2019
*/

import com.unisoft.audittrail.AuditTrailService;
import com.unisoft.user.User;
import com.unisoft.workflow.propertyBasedMakerChecker.PropertyBasedMakerCheckerService;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserRoleDao {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private AuditTrailService auditTrailService;

    @Autowired
    private PropertyBasedMakerCheckerService<User> makerCheckerService;

    public List<UserRole> getUserRoleList() {
        List<UserRole> userRoles = new ArrayList<>();
        Session session = entityManager.unwrap(Session.class);
        try {
            String sql = "SELECT los_tb_j_users_roles.id,los_tb_j_users_roles.user_id,los_tb_j_users_roles.role_id," +
                    "los_tb_m_users.username,los_tb_s_roles.name" +
                    " FROM los_tb_j_users_roles INNER JOIN los_tb_m_users ON l" +
                    "os_tb_j_users_roles.user_id=los_tb_m_users.user_id " +
                    " INNER JOIN los_tb_s_roles ON los_tb_j_users_roles.role_id=los_tb_s_roles.role_id " +
                    "ORDER BY los_tb_j_users_roles.user_id ASC";
            SQLQuery query = session.createSQLQuery(sql);

            List<Object[]> rows = query.list();

            for (Object[] row : rows) {
                UserRole userRole = new UserRole();

                userRole.setId(Integer.parseInt(row[0].toString()));
                userRole.setUserId(Long.parseLong(row[1].toString()));
                userRole.setRoleId(Integer.parseInt(row[2].toString()));
                userRole.setUserName(row[3].toString());
                userRole.setRoleName(row[4].toString());

                userRoles.add(userRole);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return userRoles;
    }

    public boolean deleteAllByUserId(Long userId) {
        boolean delete = false;
        Session session = entityManager.unwrap(Session.class);
        try {
            String sql = "DELETE FROM los_tb_j_users_roles WHERE user_id= :userId";
            SQLQuery query = session.createSQLQuery(sql);
            query.setParameter("userId", userId);
            query.executeUpdate();
            session.flush();
            delete = true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return delete;
    }

    public List<UserRole> getUserByUid(Long userId) {
        Session session = entityManager.unwrap(Session.class);
        List<UserRole> userRoles = new ArrayList<>();
        try {
            String sql = "Select * from los_tb_j_users_roles where user_id= :userId";
            SQLQuery query = session.createSQLQuery(sql);
            query.setParameter("userId", userId);
//            Criteria crt=session.createCriteria(UserRole.class);
//            crt.add(Restrictions.eq("userId",userId));
//            userRoles=crt.list();
            List<Object[]> rows = query.list();

            for (Object[] row : rows) {
                UserRole userRole = new UserRole();

                userRole.setId(Integer.parseInt(row[0].toString()));
                userRole.setUserId(Long.parseLong(row[2].toString()));
                userRole.setRoleId(Integer.parseInt(row[1].toString()));

                userRoles.add(userRole);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return userRoles;
    }

    public String getRoleNamesByEmployeePin(String pin) {
        Session session = entityManager.unwrap(Session.class);
        String roles = "";
        try {
            String sql = "" +
                    "SELECT LISTAGG(R.NAME,',') WITHIN GROUP(ORDER BY R.NAME) AS ROLES " +
                    "FROM LOS_TB_J_USERS_ROLES UR " +
                    "       JOIN LOS_TB_S_ROLES R ON R.ROLE_ID = UR.ROLE_ID " +
                    "       JOIN LOS_TB_M_USERS U ON UR.USER_ID = U.USER_ID AND U.EMPLOYEE_ID = :pin";
            Query query = session.createNativeQuery(sql);
            query.setParameter("pin", pin);
            roles = (String) query.getSingleResult();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return roles;
    }

    public boolean insert(Long userId, List<Integer> roleIdList) {
        boolean save = false;
        Session session = entityManager.unwrap(Session.class);
        deleteAllByUserId(userId);
        try {
            int sz = roleIdList.size();
            for (int i = 0; i < sz; i++) {
//                String sql="INSERT INTO los_tb_j_users_roles(user_id,role_id) VALUES('"+userId+"','"+roleIdList.get(i)+"')";
//                SQLQuery query=session.createSQLQuery(sql);
//                query.executeUpdate();
//                session.flush();

                UserRole userRole = new UserRole();
                userRole.setUserId(userId);
                userRole.setRoleId(roleIdList.get(i));

                session.save(userRole);
                auditTrailService.saveCreatedData("User Role Modification", userRole);
                session.flush();
            }
            makerCheckerService.makePending(User.class, "userId", userId);
            save = true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return save;
    }
}
