package com.csinfotechbd.role;

import com.csinfotechbd.userrole.UserRole;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shuvo on 08/01/2017.
 */
@Repository
@Transactional
public class RoleDao {

    @Autowired
    private EntityManager manager;


    // save and update Roles
    public void save(Role role) {
        manager.persist(role);
    }

    // update roles
    public void update(Role role) {
        manager.merge(role);
    }

    // delete role
    public void delete(Role role) {
        manager.remove(role);
    }

    public List<Role> getAllRoles() {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Role> criteria = builder.createQuery(Role.class);
        Root<Role> root = criteria.from(Role.class);
        criteria.select(root);
        return manager.createQuery(criteria).getResultList();
    }

    public List<Role> getActiveOnly() {
        List<Role> objList = new ArrayList<>();

        try {
            Session session = manager.unwrap(Session.class);
            Criteria crt = session.createCriteria(Role.class);
            crt.add(Restrictions.eq("enabled", true));
            objList = crt.list();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return objList;
    }

    public Role findById(int roleId) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Role> criteria = builder.createQuery(Role.class);
        Root<Role> root = criteria.from(Role.class);
        criteria.select(root);
        criteria.where(builder.equal(root.get(Role_.roleId), roleId));
        return manager.createQuery(criteria).getSingleResult();
    }

    // 	TODO: Unnecessary code. Delete whenever feel safe
//	public void detachUserFromRole(long userId, Integer roleId) {
//		Role role = manager.getReference(Role.class, roleId);
//		User user = manager.getReference(User.class, userId);
//		role.removeUser(user);
//	}
//
//	public void detachAllUserFromRole(Integer roleId) {
//		Role role = manager.find(Role.class, roleId);
//		role.removeUsers();
//	}
//
//	public void addUserToRole(long userId, int roleId) {
//		Role role = manager.find(Role.class, roleId);
//		role.addUser(manager.getReference(User.class, userId));
//	}

//	public List<Role> findRolesByUsername(String username) {
//		CriteriaBuilder builder = manager.getCriteriaBuilder();
//		CriteriaQuery<Role> criteria = builder.createQuery(Role.class);
//		Root<Role> root = criteria.from(Role.class);
//		Join<Role,User> join = root.join(Role_.users, JoinType.INNER);
//		criteria.select(root);
//		criteria.where(builder.equal(join.get(User_.username), username));
//		return manager.createQuery(criteria).getResultList();
//	}
//
//	//Retrieving user name using UID for commentbox
//
//	public String getUsernameByUID(String uid) {
//		String userName="";
//
//		Session session=manager.unwrap(Session.class);
//
//		try{
//			//session.beginTransaction();
//			User user=session.get(User.class,Long.parseLong(uid));
//			userName=user.getFirstName()+" "+user.getLastName();
//		}catch (Exception e)
//		{
//			System.out.println(e.getMessage());
//		}
//
//		return userName;
//	}

    public List<UserRole> getUserRoleByUId(Long uid) {
        List<UserRole> urole = new ArrayList();
        Session session = manager.unwrap(Session.class);

        try {
            //session.beginTransaction();
            Criteria crt = session.createCriteria(UserRole.class);
            crt.add(Restrictions.eq("userId", uid));
            urole = crt.list();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return urole;
    }
}
