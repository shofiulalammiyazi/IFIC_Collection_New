package com.unisoft.collection.samd.dashboard;

import com.unisoft.collection.samd.dataEntry.loanAccountDistribution.SamLoanAccountDistribution;
import com.unisoft.utillity.DateUtils;
import lombok.RequiredArgsConstructor;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
@RequiredArgsConstructor
public class SamdDashboardDao {

    private final EntityManager entityManager;
    private final DateUtils dateUtils;

    @Transactional(noRollbackFor = Exception.class)
    public List<SamLoanAccountDistribution> getSamAccountListByUser(String userPin) {
        List<SamLoanAccountDistribution> infos = new ArrayList<>();

        try {
            Session session = entityManager.unwrap(Session.class);

            Criteria crt = session.createCriteria(SamLoanAccountDistribution.class);
            crt.add(Restrictions.between("createdDate", dateUtils.getMonthStartDate(), dateUtils.getMonthEndDate()));
            crt.add(Restrictions.eq("dealerPin", userPin));
            crt.add(Restrictions.eq("latest", true));
            crt.add(Restrictions.eq("isDealerAccount", false));
//            crt.add(Restrictions.eq("writeOffAccount", "0"));
//            crt.add(Restrictions.ge("createdDate",startDate));
//            crt.add(Restrictions.le("createdDate",endDate));
            crt.addOrder(Order.desc("outStanding"));
            crt.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
            infos = crt.list();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return infos;
    }
}
