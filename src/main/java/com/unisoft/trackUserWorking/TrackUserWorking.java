package com.unisoft.trackUserWorking;


import com.unisoft.common.CommonEntity;
import lombok.Data;

import javax.persistence.Entity;

@Entity
@Data
public class TrackUserWorking extends CommonEntity {

    private Long currentWorkingTime;
    private Long previousWorkingTime;
    private String currentDate;
    private Long timeDuration;
    private String dealerPin;

}
