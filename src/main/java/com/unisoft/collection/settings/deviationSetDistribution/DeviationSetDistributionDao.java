package com.unisoft.collection.settings.deviationSetDistribution;

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
public class DeviationSetDistributionDao {

    @Autowired
    private EntityManager entityManager;

    public List<DeviationSetDistributionInfo> getList()
    {
        List<DeviationSetDistributionInfo> deviationSetDistributionInfoList=new ArrayList<>();

        try{
            Session session= entityManager.unwrap(Session.class);
            deviationSetDistributionInfoList=session.createCriteria(DeviationSetDistributionInfo.class).list();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return deviationSetDistributionInfoList;
    }

    public boolean save(DeviationSetDistributionInfo deviationSetDistributionInfo)
    {
        boolean save=false;

        try{
            Session session=entityManager.unwrap(Session.class);
            session.save(deviationSetDistributionInfo);
            session.flush();
            save=true;
            session.clear();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        return save;
    }

    public DeviationSetDistributionInfo  getById(Long id)
    {
        DeviationSetDistributionInfo deviationSetDistributionInfo=new DeviationSetDistributionInfo();
        try{
            Session session=entityManager.unwrap(Session.class);
            Criteria criteria=session.createCriteria(DeviationSetDistributionInfo.class);
            criteria.add(Restrictions.eq("id",id));
            deviationSetDistributionInfo=(DeviationSetDistributionInfo) criteria.uniqueResult();
            session.clear();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return deviationSetDistributionInfo;
    }

    public boolean update(DeviationSetDistributionInfo deviationSetDistributionInfo)
    {
        DeviationSetDistributionInfo temp=getById(deviationSetDistributionInfo.getId());
        boolean update=false;
        try{
            deviationSetDistributionInfo.setCreatedBy(deviationSetDistributionInfo.getCreatedBy());
            deviationSetDistributionInfo.setCreatedDate(deviationSetDistributionInfo.getCreatedDate());
            Session session=entityManager.unwrap(Session.class);
            session.merge(deviationSetDistributionInfo);
            session.flush();
            update=true;
            session.clear();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return update;
    }

    public List<DeviationSetDistributionInfo> getActiveOnly()
    {
        List<DeviationSetDistributionInfo> deviationSetDistributionInfoList=new ArrayList<>();

        try{
            Session session=entityManager.unwrap(Session.class);
            Criteria crt=session.createCriteria(DeviationSetDistributionInfo.class);
            crt.add(Restrictions.eq("enabled",true));
            deviationSetDistributionInfoList=crt.list();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return deviationSetDistributionInfoList;
    }
}
