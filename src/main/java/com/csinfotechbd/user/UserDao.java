package com.csinfotechbd.user;

import java.util.*;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;

import com.csinfotechbd.collection.settings.branch.Branch;
import com.csinfotechbd.collection.settings.branch.Branch_;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.csinfotechbd.role.Role;

@Repository
@Transactional
public class UserDao {

    @Autowired
    private EntityManager manager;

    // User section save user
    public void save(User user) {
        manager.persist(user);
    }

    // update user
    public void update(User user) {
        manager.merge(user);
    }

    // delete
    public void deleteUser(long id) {
        User u = manager.getReference(User.class, id);
        manager.remove(u);
    }

    public void disableUser(long id) {
        User u = manager.find(User.class, id);
        u.setEnabled(false);
    }

    public User findUserByUsername(String username) {
        try {
            CriteriaBuilder builder = manager.getCriteriaBuilder();
            CriteriaQuery<User> criteria = builder.createQuery(User.class);
            Root<User> root = criteria.from(User.class);
            criteria.select(root);
            criteria.where(builder.equal(root.get(User_.username), username));
            return manager.createQuery(criteria).getSingleResult();
        } catch (Exception e) {

        }
        return new User();
    }

    public User findById(long id) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<User> criteria = builder.createQuery(User.class);
        Root<User> root = criteria.from(User.class);
        criteria.select(root);
        criteria.where(builder.equal(root.get(User_.userId), id));
        return manager.createQuery(criteria).getSingleResult();
    }

    // get All User
    public List<User> findAll() {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<User> criteria = builder.createQuery(User.class);
        Root<User> root = criteria.from(User.class);
        criteria.select(root).where(builder.equal(root.get("deleted"), false));
        return manager.createQuery(criteria).getResultList();
    }

    public User findUserAndRolesByUsername(String username) {
        try {
            CriteriaBuilder builder = manager.getCriteriaBuilder();
            CriteriaQuery<User> criteria = builder.createQuery(User.class);
            Root<User> root = criteria.from(User.class);
            //Fetch<User,Branch> branchFetch = root.fetch(User_.branch,JoinType.INNER);
            //Fetch<User, Role> roleFetch = root.fetch(User_.roles, JoinType.INNER);
            criteria.where(builder.equal(root.get(User_.username), username));
            List<User> users = manager.createQuery(criteria).getResultList();
            User user = (users.isEmpty()) ? null : users.get(0);
            return user != null && user.isEnabled() ? user : null;
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    public void removeRolesFromUser(long userId) {
        User u = manager.find(User.class, userId);
        u.removeAllRoleFromUser();
    }

    public void addRolesWithUser(long userId, List<Integer> roleIds) {
        User u = manager.find(User.class, userId);
        u.removeAllRoleFromUser();
        roleIds.parallelStream().forEach(id -> u.addRole(manager.getReference(Role.class, id)));
    }

    public List<String> getRolesByUID(String userId) {
        Map<String, String> stringMap = new HashMap<>();
        stringMap.put("userId", userId);
        Session session = manager.unwrap(Session.class);
        List<String> roleList = new ArrayList<>();
        String sql = "SELECT los_tb_s_roles.name FROM los_tb_j_users_roles INNER JOIN los_tb_s_roles ON los_tb_j_users_roles.role_id=los_tb_s_roles.role_id WHERE los_tb_j_users_roles.user_id=:userId";
        try {
            SQLQuery query = session.createSQLQuery(sql);
            for (Map.Entry<String, String> entry : stringMap.entrySet()) {
                query.setParameter(entry.getKey(), entry.getValue());
            }
            roleList = query.list();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return roleList;
    }

    public boolean checkUserName(String userName) {
        boolean found = false;
        Session session = manager.unwrap(Session.class);

        try {
            Criteria crt = session.createCriteria(User.class);
            crt.add(Restrictions.eq("username", userName));
            if (crt.uniqueResult() != null) {
                found = true;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return found;
    }

}
