package com.unisoft.schedulermonitoringstatus;

import com.unisoft.common.CommonEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Time;
import java.util.Date;

@Data
@Entity
public class SchedulerMonitoringStatus extends CommonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String schedulerName;

    private Date executionDate;

    private String Status;
}
