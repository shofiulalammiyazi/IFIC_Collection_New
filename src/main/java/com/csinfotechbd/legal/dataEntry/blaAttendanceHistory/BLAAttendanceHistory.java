package com.csinfotechbd.legal.dataEntry.blaAttendanceHistory;

import com.csinfotechbd.base.BaseInfo;
import com.csinfotechbd.legal.dataEntry.caseEntry.LitigationCaseInfo;
import com.csinfotechbd.legal.setup.courseOfAction.CourseOfAction;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnore;


import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "LITIGATION_CASE_INFO_BLA_ATTENDANCE_HISTORY_DETAILS")
public class BLAAttendanceHistory extends BaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long litigationCaseInfoId;
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "bla_litigation_case_info_id")
    @JsonBackReference
    @JsonIgnore
    private LitigationCaseInfo litigationCaseInfo;

    private boolean nextDateFixed;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MMM/yyyy")
    private Date nextDate;
    private String courseOfActionContestedType;
    private Long courseOfActionId;
    private String courseOfActionName;
    private boolean blaAttendance;

    private boolean possessionInFavourOfBank5;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MMM/yyyy")
    private Date registrationDate;
    private String deedNo;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MMM/yyyy")
    private Date deedDate;
    private boolean applicationForMutation;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MMM/yyyy")
    private Date mutationDate;
    private boolean possessionInFavourOfBank7;
    private String propertyDisputed;
}