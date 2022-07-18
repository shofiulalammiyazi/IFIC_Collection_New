package com.csinfotechbd.workflow.propertyBasedMakerChecker;

import com.csinfotechbd.user.UserService;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

/**
 * Created by Yasir Araphat
 * Created at 30 March, 2021
 */

@Repository
@Transactional
@RequiredArgsConstructor
class PropertyBasedMakerCheckerDao {

    private final EntityManager em;

    public int makePending(String entityName, String identifierProperty, Object[] identifierPropertyValues) {
        String username = UserService.getSessionUsername();
        String sql = "UPDATE " + entityName + " e SET e.enabled = false " +
                "WHERE e." + identifierProperty + " IN(:values)";
        return executeUpdate(sql, identifierPropertyValues, null);
    }

    public int approve(String entityName, String identifierProperty, Object[] identifierPropertyValues) {
        String username = UserService.getSessionUsername();
        String sql = "UPDATE " + entityName + " e SET e.enabled = true, e.remark = NULL " +
                "WHERE e." + identifierProperty + " IN(:values) AND COALESCE(e.createdBy, '___') <> '" +
                username + "' AND COALESCE(e.modifiedBy, '___') <> '" + username + "'";
        return executeUpdate(sql, identifierPropertyValues, null);
    }

    public int reject(String entityName, String identifierProperty, Object[] identifierPropertyValues, String remark) {
        String username = UserService.getSessionUsername();
        String sql = "UPDATE " + entityName +
                " e SET e.remark = :remark, e.modifiedBy = NULL, e.modifiedDate = NULL " +
                " WHERE e." + identifierProperty + " IN(:values) AND COALESCE(e.createdBy, '___') <> '" +
                username + "' AND COALESCE(e.modifiedBy, '___') <> '" + username + "'";
        return executeUpdate(sql, identifierPropertyValues, remark);
    }

    @Modifying
    private int executeUpdate(String sql, Object[] identifierPropertyValues, String remark) {
        Session session = em.unwrap(Session.class);
        try {
            Query query = session.createQuery(sql);
            query.setParameterList("values", identifierPropertyValues);
            if (remark != null)
                query.setParameter("remark", remark);
            return query.executeUpdate();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return 0;
    }
}
