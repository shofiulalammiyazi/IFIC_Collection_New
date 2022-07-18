package com.csinfotechbd.collection.settings.lateReasonExplain;

import com.csinfotechbd.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface LateReasonExplainRepository extends JpaRepository<LateReasonExplainInfo, Long> {
    LateReasonExplainInfo findByUserAndCreatedDateGreaterThan(User user, Date date);
    LateReasonExplainInfo findByUser(User user);
    List<LateReasonExplainInfo> findByCreatedDateIsBetweenAndUser(Date startDate, Date endDate, User user);
    List<LateReasonExplainInfo> findByCreatedDateIsBetweenAndUserOrderByCreatedDateDesc(Date startDate, Date endDate, User user);

    @Query(value = "SELECT * " +
            "FROM LATE_REASON_EXPLAIN_INFO LREI " +
            "WHERE LREI.USER_ID = ? " +
            "  AND to_char(LREI.CREATED_DATE, 'dd-Mon-yyyy') = (select to_char(current_timestamp, 'dd-Mon-yyyy') from dual) " +
            "ORDER BY LREI.CREATED_DATE DESC ", nativeQuery = true)
    List<LateReasonExplainInfo> findByUserIdAndDate(Long userId);
}
