package com.csinfotechbd.collection.settings.assetClassificationLoan;
/*
Created by Monirul Islam on 7/11/2019 
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
public class AssetClassificationLoanDao {

    @Autowired
    private EntityManager entityManager;

    public List<AssetClassificationLoanEntity> getList()
    {

        List<AssetClassificationLoanEntity> objList=new ArrayList<>();
        try{
            Session session= entityManager.unwrap(Session.class);
            Criteria crt=session.createCriteria(AssetClassificationLoanEntity.class);
            //crt.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
            objList=crt.list();
            return  objList;
        }catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }

    }

    public boolean saveNew(AssetClassificationLoanEntity obj)
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
            e.printStackTrace();
        }
        return save;
    }

    public AssetClassificationLoanEntity getById(Long id)
    {
        AssetClassificationLoanEntity obj=new AssetClassificationLoanEntity();
        try{
            Session session=entityManager.unwrap(Session.class);
            Criteria crt=session.createCriteria(AssetClassificationLoanEntity.class);
            crt.add(Restrictions.eq("id",id));
            obj=(AssetClassificationLoanEntity) crt.uniqueResult();
            session.clear();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return obj;
    }

    public boolean updateObj(AssetClassificationLoanEntity obj)
    {
        AssetClassificationLoanEntity tempObj=getById(obj.getId());
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
            e.printStackTrace();
        }
        return update;
    }

    public List<AssetClassificationLoanEntity> getActiveOnly()
    {
        List<AssetClassificationLoanEntity> objList=new ArrayList<>();

        try{
            Session session=entityManager.unwrap(Session.class);
            Criteria crt=session.createCriteria(AssetClassificationLoanEntity.class);
            crt.add(Restrictions.eq("enabled",true));
            //crt.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
            objList=crt.list();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return objList;
    }
}
