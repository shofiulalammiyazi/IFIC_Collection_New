package com.unisoft.collection.settings.smsAndAutoDistributionRules;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.List;

@Repository
public interface SmsAndAutoDistributionRulesRepository extends JpaRepository<SmsAndAutoDistributionRulesEntity, Long> {

    SmsAndAutoDistributionRulesEntity findByType(String type);

    @Query(value = "SELECT SAADRE.TYPE                       AS RULE_NAME, " +
            "       SAADRE.UNPAID_INSTALLMENT_NUMBER         AS NO_OF_INSTALLMENT_DUE, " +
            "       SAADRE.NO_OF_DAYS_BEFORE_EMI_DATE        AS NO_OF_DAYS_BEFORE_EMI_DATE, " +
            "       LSE.NAME                                 AS LOAN_STATUS, " +
            "       LTE.LOAN_TYPE                            AS LOAN_TYPE " +
            "FROM SMS_AND_AUTO_DISTRIBUTION_RULES_ENTITY SAADRE " +
            "       LEFT JOIN SMS_AND_AUTO_DISTRIBUTION_RULES_ENTITY_LOAN_TYPE_ENTITIES SAADRELTE " +
            "         ON SAADRE.ID = SAADRELTE.SMS_AND_AUTO_DISTRIBUTION_RULES_ENTITY_ID " +
            "       LEFT JOIN LOAN_TYPE_ENTITY LTE on SAADRELTE.LOAN_TYPE_ENTITIES_ID = LTE.ID " +
            "       LEFT JOIN SMS_AND_AUTO_DISTRIBUTION_RULES_ENTITY_LOAN_STATUS_ENTITY SAADRESE " +
            "         ON SAADRE.ID = SAADRESE.SMS_AND_AUTO_DISTRIBUTION_RULES_ENTITY_ID " +
            "       LEFT JOIN LOAN_STATUS_ENTITY LSE ON SAADRESE.LOAN_STATUS_ENTITY_ID = LSE.ID " +
            "WHERE LTE.LOAN_TYPE IN (?1) " +
            "  AND LSE.NAME IN (?2)", nativeQuery = true)
    List<Tuple> findByLoanStatusEntityAndLoanTypeEntity(String lt, String ls);
}
