package com.csinfotechbd.trackUserWorking;


import com.csinfotechbd.common.CommonEntity;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import java.util.Date;

@Entity
@Data
public class TrackUserWorking extends CommonEntity {

    private Long currentWorkingTime;
    private Long previousWorkingTime;
    private String currentDate;
    private Long timeDuration;
    private String dealerPin;

}
