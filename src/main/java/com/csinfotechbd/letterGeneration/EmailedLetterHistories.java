package com.csinfotechbd.letterGeneration;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity @Getter @Setter
public class EmailedLetterHistories {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long customerId;

    @Transient
    private String accountNo;
    private String letterType;

    private String emailTo;
    private String emailCC;
    private String emailSubject;

    @Lob
    private String emailBody;

    @Expose
    @CreatedBy
    private String createdBy;

    @Expose
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern ="yyyy-MM-dd hh:mm:ss" )
    private Date createdDate;

}
