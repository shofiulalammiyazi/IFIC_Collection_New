package com.unisoft.collection.settings.department;
/*
Created by   Islam at 6/24/2019
*/

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class DepartmentDao {

    @Autowired
    private EntityManager entityManager;

    public List<DepartmentEntity> getDivList()
    {

        List<DepartmentEntity> depList=new ArrayList<>();
        try{
            Session session= entityManager.unwrap(Session.class);
            depList=session.createCriteria(DepartmentEntity.class).list();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return  depList;
    }

    public boolean saveNewDept(DepartmentEntity dept)
    {
        boolean save=false;

        try{
            Session session= entityManager.unwrap(Session.class);
            session.save(dept);
            session.flush();
            save=true;
            session.clear();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return save;
    }

    public DepartmentEntity getById(Long id)
    {
        DepartmentEntity dept=new DepartmentEntity();
        try{
            Session session=entityManager.unwrap(Session.class);
            Criteria crt=session.createCriteria(DepartmentEntity.class);
            crt.add(Restrictions.eq("id",id));
            dept=(DepartmentEntity) crt.uniqueResult();
            session.clear();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return dept;
    }

    public boolean updateDept(DepartmentEntity dept)
    {
        DepartmentEntity tempDept=getById(dept.getId());
        boolean update=false;
        try{
            dept.setCreatedDate(tempDept.getCreatedDate());
            dept.setCreatedBy(tempDept.getCreatedBy());
            Session session=entityManager.unwrap(Session.class);
            session.merge(dept);
            session.flush();
            session.clear();
            update=true;
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return update;
    }

    public List<DepartmentEntity> getActiveOnly()
    {
        List<DepartmentEntity> depList=new ArrayList<>();

        try{
            Session session=entityManager.unwrap(Session.class);
            Criteria crt=session.createCriteria(DepartmentEntity.class);
            crt.add(Restrictions.eq("enabled",true));
            depList=crt.list();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return depList;
    }
}
