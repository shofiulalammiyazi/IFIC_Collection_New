package com.csinfotechbd.collection.settings.employeeStatusManagement;
/*
Created by Monirul Islam on 7/11/2019 
*/

import com.csinfotechbd.collection.settings.employee.EmployeeInfoEntity;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class EmployeeStatusManagerDao {

    @Autowired
    private EntityManager entityManager;

    public List<EmployeeStatusManagerEntity> getList()
    {

        List<EmployeeStatusManagerEntity> objList=new ArrayList<>();
        try{
            Session session= entityManager.unwrap(Session.class);
            Criteria crt=session.createCriteria(EmployeeStatusManagerEntity.class);
            //crt.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

            objList=crt.list();
            return  objList;
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public boolean saveNew(EmployeeStatusManagerEntity obj)
    {
        boolean save=false;

        try{
            Session session= entityManager.unwrap(Session.class);
            session.save(obj);
            session.flush();
            save=true;
            session.clear();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return save;
    }

    public EmployeeStatusManagerEntity getById(Long id)
    {
        EmployeeStatusManagerEntity obj=new EmployeeStatusManagerEntity();
        try{
            Session session=entityManager.unwrap(Session.class);
            Criteria crt=session.createCriteria(EmployeeStatusManagerEntity.class);
            crt.add(Restrictions.eq("id",id));
            obj=(EmployeeStatusManagerEntity) crt.uniqueResult();
            session.clear();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return obj;
    }

    public boolean updateObj(EmployeeStatusManagerEntity obj)
    {
        EmployeeStatusManagerEntity tempObj=getById(obj.getId());
        boolean update=false;
        try{
            obj.setCreatedDate(tempObj.getCreatedDate());
            obj.setCreatedBy(tempObj.getCreatedBy());

            Session session=entityManager.unwrap(Session.class);
            session.merge(obj);
            session.flush();
            session.clear();
            update=true;
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return update;
    }

    public List<EmployeeStatusManagerEntity> getActiveOnly()
    {
        List<EmployeeStatusManagerEntity> objList=new ArrayList<>();

        try{
            Session session=entityManager.unwrap(Session.class);
            Criteria crt=session.createCriteria(EmployeeStatusManagerEntity.class);
            crt.add(Restrictions.eq("enabled",true));
            //crt.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
            objList=crt.list();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return objList;
    }

    public EmployeeStatusManagerEntity getEmpLastStatById(Long id)
    {
        EmployeeStatusManagerEntity emp=new EmployeeStatusManagerEntity();
        EmployeeInfoEntity employeeInfo=new EmployeeInfoEntity();
        employeeInfo.setId(id);
        try{
            Session session=entityManager.unwrap(Session.class);
            Criteria crt=session.createCriteria(EmployeeStatusManagerEntity.class);
            crt.add(Restrictions.eq("employeeInfo",employeeInfo));
            //crt.setProjection(Projections.max("id"));
            crt.addOrder(Order.desc("id"));
            crt.setMaxResults(1);
            emp=(EmployeeStatusManagerEntity) crt.uniqueResult();
            session.clear();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return emp;
    }

    public EmployeeStatusManagerEntity getByUserId(Long userId)
    {
        EmployeeStatusManagerEntity emp=new EmployeeStatusManagerEntity();
        try{
            Session session=entityManager.unwrap(Session.class);
            Criteria crt=session.createCriteria(EmployeeStatusManagerEntity.class);
            crt.add(Restrictions.eq("userId",userId));
            crt.addOrder(Order.desc("id"));
            crt.setMaxResults(1);

            emp=(EmployeeStatusManagerEntity) crt.uniqueResult();
            session.clear();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return emp;
    }
}
