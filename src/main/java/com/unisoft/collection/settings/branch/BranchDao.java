package com.unisoft.collection.settings.branch;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
@Transactional
public class BranchDao {

    @Autowired
    private EntityManager manager;

    public void save(Branch branch) {
        manager.persist(branch);
    }

    public Branch findBranchByBranchCode(String branchCode) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Branch> criteria = builder.createQuery(Branch.class);
        Root<Branch> root = criteria.from(Branch.class);
        criteria.select(root);
        criteria.where(builder.equal(root.get(Branch_.branchCode), branchCode));
        return manager.createQuery(criteria).getSingleResult();
    }

    //    Changes made for lawyer table
    public List getByDistrict(Long districtId) {
        List objList = new ArrayList<>();
        try {
            Session session = manager.unwrap(Session.class);
            Criteria crt=session.createCriteria(Branch.class)
					.setProjection(Projections.projectionList()
							.add(Projections.property("branchId"), "branchId")
							.add(Projections.property("branchName"), "branchName"))
					.setResultTransformer(Transformers.aliasToBean(HashMap.class));
			crt.add(Restrictions.eq("district.id", districtId));
			objList= crt.list();
            session.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return objList;
    }


    public List<Branch> getList() {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Branch> criteria = builder.createQuery(Branch.class);
        Root<Branch> root = criteria.from(Branch.class);
        criteria.select(root);
        return manager.createQuery(criteria).getResultList();
    }

    public Branch findById(int branchId) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Branch> criteria = builder.createQuery(Branch.class);
        Root<Branch> root = criteria.from(Branch.class);
        criteria.select(root);
        criteria.where(builder.equal(root.get(Branch_.branchId), branchId));
        return manager.createQuery(criteria).getSingleResult();
    }

    public void update(Branch branch) {
        manager.merge(branch);
    }

//	public Branch findBranchByUsername(String username) {
//		CriteriaBuilder builder = manager.getCriteriaBuilder();
//		CriteriaQuery<User> criteria = builder.createQuery(User.class);
//		Root<User> root = criteria.from(User.class);
//		criteria.select(root);
//		criteria.where(builder.equal(root.get(User_.username), username));
//		User user = manager.createQuery(criteria).getSingleResult();
//		return user.getBranch();
//	}
//
//	public Branch findBranchByUserId(String userId) {
//		CriteriaBuilder builder = manager.getCriteriaBuilder();
//		CriteriaQuery<User> criteria = builder.createQuery(User.class);
//		Root<User> root = criteria.from(User.class);
//		criteria.select(root);
//		criteria.where(builder.equal(root.get(User_.userId), userId));
//		User user = manager.createQuery(criteria).getSingleResult();
//		return user.getBranch();
//	}

    public Branch getBranchById(String id) {
        Session session = manager.unwrap(Session.class);
        Branch branch = new Branch();
        try {
            Criteria crt = session.createCriteria(Branch.class);
            crt.add(Restrictions.eq("branchId", Integer.parseInt(id)));
            branch = (Branch) crt.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return branch;
    }

    public List<Branch> getActiveList() {
        Session session = manager.unwrap(Session.class);
        List<Branch> branchList = new ArrayList();
        try {
            Criteria crt = session.createCriteria(Branch.class);
            crt.add(Restrictions.eq("enabled", true));
            branchList = crt.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return branchList;
    }

}
