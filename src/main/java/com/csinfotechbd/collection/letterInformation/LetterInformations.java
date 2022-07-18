package com.csinfotechbd.collection.letterInformation;

import com.csinfotechbd.common.CommonEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "lms_loan_letter_information")
public class LetterInformations extends CommonEntity {

    private String customerId;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date receiveDate;

    private String receivedBy;
    private String lastLetterType;
    private String letterRefNo;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date letterSendDate;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date letterReturnDate;
    private String returnReason;

}
