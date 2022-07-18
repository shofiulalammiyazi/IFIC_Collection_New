package com.csinfotechbd.collection.settings.employee;
/*
Created by Monirul Islam at 7/7/2019 
*/

import com.csinfotechbd.collection.settings.designation.DesignationEntity;
import com.csinfotechbd.collection.settings.designation.DesignationService;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class EmployeeDao {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private DesignationService designationService;

    public List<EmployeeInfoEntity> getList()
    {

        List<EmployeeInfoEntity> objList=new ArrayList<>();
        try{
            Session session= entityManager.unwrap(Session.class);
            Criteria crt=session.createCriteria(EmployeeInfoEntity.class);
            crt.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
            objList=crt.list();
            return  objList;
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
            return null;
        }

    }

    public boolean saveNew(EmployeeInfoEntity obj)
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

    public EmployeeInfoEntity getById(Long id)
    {
        EmployeeInfoEntity obj=new EmployeeInfoEntity();
        try{
            Session session=entityManager.unwrap(Session.class);
            Criteria crt=session.createCriteria(EmployeeInfoEntity.class);
            crt.add(Restrictions.eq("id",id));
            obj=(EmployeeInfoEntity) crt.uniqueResult();
            session.clear();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return obj;
    }
    
    

    public boolean updateObj(EmployeeInfoEntity obj)
    {
        EmployeeInfoEntity tempObj=getById(obj.getId());
        boolean update=false;
        try{
            obj.setCreatedDate(tempObj.getCreatedDate());
            obj.setCreatedBy(tempObj.getCreatedBy());
            obj.getUser().setCreatedDate(tempObj.getUser().getCreatedDate());
            obj.getUser().setCreatedBy(tempObj.getUser().getCreatedBy());

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

    public List<EmployeeInfoEntity> getActiveOnly()
    {
        List<EmployeeInfoEntity> objList=new ArrayList<>();

        try{
            Session session=entityManager.unwrap(Session.class);
            Criteria crt=session.createCriteria(EmployeeInfoEntity.class);
            crt.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
            crt.add(Restrictions.eq("enabled",true));
            objList=crt.list();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return objList;
    }

    public List<EmployeeInfoEntity> getDealerList() {
        List<EmployeeInfoEntity> objList=new ArrayList<>();
        DesignationEntity dealer = designationService.findByName("Dealer");
        try{
            Session session=entityManager.unwrap(Session.class);
            Criteria crt=session.createCriteria(EmployeeInfoEntity.class);
            crt.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
            crt.add(Restrictions.eq("designation", dealer));
            crt.add(Restrictions.eq("enabled",true));
            crt.createAlias("user", "users");
            crt.addOrder(Order.asc("users.firstName"));
            objList=crt.list();
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return objList;
    }

    public List<EmployeeInfoEntity> getManagerList() {
        List<EmployeeInfoEntity> objList=new ArrayList<>();
        DesignationEntity dealer = designationService.findByName("Manager");
        try{
            Session session=entityManager.unwrap(Session.class);
            Criteria crt=session.createCriteria(EmployeeInfoEntity.class);
            crt.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
            crt.add(Restrictions.eq("designation", dealer));
            crt.add(Restrictions.eq("enabled",true));
            crt.createAlias("user", "users");
            crt.addOrder(Order.asc("users.firstName"));
            objList=crt.list();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return objList;
    }

    public List<EmployeeInfoEntity> getTeamLeaderList() {
        List<EmployeeInfoEntity> objList=new ArrayList<>();
        DesignationEntity dealer = designationService.findByName("Team Leader");
        try{
            Session session=entityManager.unwrap(Session.class);
            Criteria crt=session.createCriteria(EmployeeInfoEntity.class);
            crt.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
            crt.add(Restrictions.eq("designation", dealer));
            crt.add(Restrictions.eq("enabled",true));
            crt.createAlias("user", "users");
            crt.addOrder(Order.asc("users.firstName"));
            objList=crt.list();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return objList;
    }

    public List<EmployeeInfoEntity> getSuprvisorList() {
        List<EmployeeInfoEntity> objList=new ArrayList<>();
        DesignationEntity dealer = designationService.findByName("Supervisor");
        try{
            Session session=entityManager.unwrap(Session.class);
            Criteria crt=session.createCriteria(EmployeeInfoEntity.class);
            crt.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
            crt.add(Restrictions.eq("designation", dealer));
            crt.add(Restrictions.eq("enabled",true));
            crt.createAlias("user", "users");
            crt.addOrder(Order.asc("users.firstName"));
            objList=crt.list();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return objList;
    }

    public List<Long> getAllEmpIdList()
    {
        List<Long> idList=new ArrayList<>();

        try{
            Session session=entityManager.unwrap(Session.class);
            String sql="select id from EMPLOYEE_INFO_ENTITY";
            SQLQuery query=session.createSQLQuery(sql);
            List<Object> objList=query.list();

            for(Object obj: objList)
            {
                Long id=Long.parseLong(obj.toString());
                idList.add(id);
            }
//            idList=query.list();
            System.err.println("EMPID :"+objList);
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        return idList;
    }

}
