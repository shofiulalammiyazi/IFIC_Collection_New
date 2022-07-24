package com.unisoft.retail.card.dashboard;

import com.unisoft.customerbasicinfo.CustomerBasicInfoEntity;
import com.unisoft.retail.card.dataEntry.followup.CardFollowUp;
import com.unisoft.utillity.DateUtils;
import lombok.RequiredArgsConstructor;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yasir Araphat
 * Created at 01 April, 2021
 */

@Repository
@Transactional
@RequiredArgsConstructor
public class RetailCardDashboardDao {

    private final EntityManager entityManager;
    private final DateUtils dateUtils;


    public List<CardFollowUp> getCardFollowUpByCusBasicInfo(Long cusId, String userId) {
        List<CardFollowUp> followUpList = new ArrayList<>();

        CustomerBasicInfoEntity customerBasicInfo = new CustomerBasicInfoEntity();
        customerBasicInfo.setId(cusId);
        try {
            Session session = entityManager.unwrap(Session.class);

            Criteria crt = session.createCriteria(CardFollowUp.class);
            crt.add(Restrictions.between("followUpDate", dateUtils.getMonthStartDate(), dateUtils.getMonthEndDate()));
            crt.add(Restrictions.eq("customerBasicInfo", customerBasicInfo));
            //crt.add(Restrictions.eq("createdBy",userId));
            crt.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
            followUpList = crt.list();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return followUpList;
    }


}
