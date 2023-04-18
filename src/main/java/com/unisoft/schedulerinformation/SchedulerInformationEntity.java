package com.unisoft.schedulerinformation;


import com.unisoft.base.BaseInfo;
import com.unisoft.common.CommonEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class SchedulerInformationEntity extends CommonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String schedulerName;
    //private String time;
}
