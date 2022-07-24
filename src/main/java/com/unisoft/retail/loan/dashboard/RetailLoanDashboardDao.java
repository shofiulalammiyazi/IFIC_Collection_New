package com.unisoft.retail.loan.dashboard;

import com.unisoft.customerbasicinfo.CustomerBasicInfoEntity;
import com.unisoft.customerloanprofile.followup.FollowUpEntity;
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
public class RetailLoanDashboardDao {

    private final EntityManager entityManager;
    private final DateUtils dateUtils;

    public List<FollowUpEntity> getLoanFollowUpByCusBasicInfo(Long cusId, String userId) {
        List<FollowUpEntity> followUpList = new ArrayList<>();

        CustomerBasicInfoEntity customerBasicInfo = new CustomerBasicInfoEntity();
        customerBasicInfo.setId(cusId);
        try {
            Session session = entityManager.unwrap(Session.class);

            Criteria crt = session.createCriteria(FollowUpEntity.class);
            crt.add(Restrictions.between("followUpDate", dateUtils.getMonthStartDate(), dateUtils.getMonthEndDate()));
            crt.add(Restrictions.eq("customerBasicInfo", customerBasicInfo));
            //crt.add(Restrictions.eq("createdBy",userId));
            crt.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
            followUpList = crt.list();

            //System.err.println("DATA :"+followUpList);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return followUpList;
    }


}
