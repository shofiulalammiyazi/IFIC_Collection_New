package com.csinfotechbd.collection.distribution.emiDetails;

import com.csinfotechbd.base.BaseInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmiDetails extends BaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String cardNo;

    private String numOfInst;

    private String emiSize;

    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Temporal(TemporalType.DATE)
    private Date endDate;

    private String initiator;

    private String approval;

    private String completed;

    private String remark;
}
