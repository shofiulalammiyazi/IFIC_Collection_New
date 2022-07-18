package com.csinfotechbd.legal.dataEntry.fileFollowUp;

import com.csinfotechbd.base.BaseInfo;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "FILE_FOLLOW_UP_LEGAL_NOTICE_SERVED_FOR_ARTHARIN")
public class FileFollowUpLegalNoticeServedForArtharinSuit extends BaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "file_followup_id")
    @JsonBackReference
    @JsonIgnore
    private LitigationFileFollowUp litigationFileFollowUp;

    private boolean legalNoticeServedForArtharinSuit;
    private String legalNoticeServedForArtharinSuitClaimAmount;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MMM/yyyy")
    private Date dateOfLegalNoticeServedForArtharinSuit;
}
