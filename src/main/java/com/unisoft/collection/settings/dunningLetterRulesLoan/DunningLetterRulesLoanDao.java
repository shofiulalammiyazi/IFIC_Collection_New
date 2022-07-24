package com.unisoft.collection.settings.dunningLetterRulesLoan;

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
public class DunningLetterRulesLoanDao {

    @Autowired
    private EntityManager entityManager;

    public boolean save(DunningLetterRulesLoanInfo entity){
        boolean save = false;
        try{
            Session session = entityManager.unwrap(Session.class);
            entity.getConditions().forEach(dlc->dlc.setDunningLetterRulesLoanInfo(entity));
            entity.getConditions().forEach(dlc->dlc.getDunningLetterRulesDpdLetterType().forEach(dldpdletter->dldpdletter.setDunningLetterRulesLoanCondition(dlc)));
            session.save(entity);
            session.flush();
            save=true;
            session.clear();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return save;
    }

    public List<DunningLetterRulesLoanInfo> getList()
    {
        List<DunningLetterRulesLoanInfo> dunningLetterRulesLoanInfoList=new ArrayList<>();

        try{
            Session session= entityManager.unwrap(Session.class);
            dunningLetterRulesLoanInfoList=session.createCriteria(DunningLetterRulesLoanInfo.class).list();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return dunningLetterRulesLoanInfoList;
    }

    public DunningLetterRulesLoanInfo getById(Long id){
        DunningLetterRulesLoanInfo dunningLetterRulesLoanInfo = null;
        try{
            Session session= entityManager.unwrap(Session.class);
            Criteria criteria = session.createCriteria(DunningLetterRulesLoanInfo.class);
            criteria.add(Restrictions.eq("id",id));
            dunningLetterRulesLoanInfo = (DunningLetterRulesLoanInfo) criteria.uniqueResult();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return dunningLetterRulesLoanInfo;
    }

    public boolean update(DunningLetterRulesLoanInfo entity){
        boolean save = false;
        DunningLetterRulesLoanInfo previousEntity = getById(entity.getId());
        try{
            Session session = entityManager.unwrap(Session.class);
            entity.getConditions().forEach(dlc->dlc.setDunningLetterRulesLoanInfo(entity));
            entity.getConditions().forEach(dlc->dlc.getDunningLetterRulesDpdLetterType().forEach(dldpdletter->dldpdletter.setDunningLetterRulesLoanCondition(dlc)));
            entity.setCreatedDate(previousEntity.getCreatedDate());
            entity.setCreatedBy(previousEntity.getCreatedBy());
            session.merge(entity);
            session.flush();
            save = true;
            session.clear();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }

        return save;
    }
}
