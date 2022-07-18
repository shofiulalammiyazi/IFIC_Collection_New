package com.csinfotechbd.collection.dashboard;
/*
  Created by Md. Monirul Islam on 2/1/2020
*/

import com.csinfotechbd.utillity.DateUtils;
import lombok.RequiredArgsConstructor;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Transactional
public class DealerPerformanceDataDao {

    private final EntityManager entityManager;
    private final DateUtils dateUtils;

    public DealerPerformanceDataEntity getCurrentMonthPerformanceByUserPin(String userPin, String unit) {
        Date startDate = dateUtils.getMonthStartDate();
        Date endDate = dateUtils.getMonthEndDate();
        return getPerformanceDataWithinDateRange(userPin, unit, startDate, endDate);
    }

    public DealerPerformanceDataEntity getPerformanceDataWithinDateRange(String userPin, String unit, Date startDate, Date endDate) {
        DealerPerformanceDataEntity dataEntity = null;
        try {
            Session session = entityManager.unwrap(Session.class);

            Criteria crt = session.createCriteria(DealerPerformanceDataEntity.class);
            crt.add(Restrictions.eq("dealerPin", userPin));
            crt.add(Restrictions.like("unit", unit, MatchMode.ANYWHERE).ignoreCase());
            crt.add(Restrictions.between("performanceDate", startDate, endDate));
            crt.addOrder(Order.desc("performanceDate"));
            crt.setMaxResults(1);

            dataEntity = (DealerPerformanceDataEntity) crt.uniqueResult();
            session.clear();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());

        }

        return Optional.ofNullable(dataEntity).orElse(new DealerPerformanceDataEntity());
    }

    public boolean saveOrUpdateData(DealerPerformanceDataEntity dataEntity) {
        boolean save = false;
        try {
            Session session = entityManager.unwrap(Session.class);

            session.saveOrUpdate(dataEntity);
            session.flush();
            session.clear();

            save = true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            save = false;
        }
        return save;
    }
}
