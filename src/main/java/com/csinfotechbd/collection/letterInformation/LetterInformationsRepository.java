package com.csinfotechbd.collection.letterInformation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.List;

@Repository
public interface LetterInformationsRepository  extends JpaRepository<LetterInformations, Long> {

    @Query(value = "SELECT LLCM.ID           AS id, " +

            "       LLCM.LAST_LETTER_TYPE AS lastLetterType, " +
            "       LLCM.LETTER_REF_NO AS letterRefNo, " +
            "       LLCM.RECEIVED_BY AS receivedBy, " +
            "       LLCM.LETTER_SEND_DATE AS letterSendDate, " +
            "       LLCM.LETTER_RETURN_DATE AS letterReturnDate, " +
            "       LLCM.RETURN_REASON AS returnReason, " +
            "       CMF.FILE_NAME     AS fileName, " +
            "       CMF.DMS_FILE_ID   AS dmsId, " +
            "       CMF.DMS_FILE_TYPE AS fileType " +
            "FROM LMS_LOAN_LETTER_INFORMATION LLCM " +
            "       LEFT JOIN LETTER_INFORMATIONS_FILE CMF ON CMF.LETTER_INFORMATIONS_ID = LLCM.ID " +
            "WHERE LLCM.CUSTOMER_ID = ? " +
            "ORDER BY LLCM.CREATED_DATE DESC", nativeQuery = true)
    List<Tuple> findAllByCustomerId(String customerId);


}
