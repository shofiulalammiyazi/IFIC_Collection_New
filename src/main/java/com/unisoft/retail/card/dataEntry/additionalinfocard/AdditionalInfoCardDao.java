package com.unisoft.retail.card.dataEntry.additionalinfocard;

import com.unisoft.customerbasicinfo.CustomerBasicInfoEntity;
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
public class AdditionalInfoCardDao {

    @Autowired
    private EntityManager entityManager;

    public List<AdditionalInfoCard> getList(){
        List<AdditionalInfoCard> additionalInfoList = new ArrayList<>();

        try{
            Session session = entityManager.unwrap(Session.class);
            additionalInfoList = session.createCriteria(AdditionalInfoCard.class).list();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

        return additionalInfoList;
    }

    public List<AdditionalInfoCard> findByCustomer(CustomerBasicInfoEntity customerBasicInfoEntity){
        List<AdditionalInfoCard> additionalInfoList = new ArrayList<>();

        try{
            Session session = entityManager.unwrap(Session.class);
            additionalInfoList = session.createCriteria(AdditionalInfoCard.class)
                    .add(Restrictions.eq("customerBasicInfo", customerBasicInfoEntity))
                    .list();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return additionalInfoList;
    }

    public boolean save(AdditionalInfoCard additionalInfo){
        boolean save = false;

        try{
            Session session = entityManager.unwrap(Session.class);
            session.saveOrUpdate(additionalInfo);
            session.flush();
            save = true;
            session.clear();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        return save;
    }


    public AdditionalInfoCard findById(Long id){
        AdditionalInfoCard additionalInfo = new AdditionalInfoCard();
        try {
            Session session = entityManager.unwrap(Session.class);
            additionalInfo = session.byId(AdditionalInfoCard.class).getReference(id);
            session.flush();
            session.clear();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return additionalInfo;
    }


}
