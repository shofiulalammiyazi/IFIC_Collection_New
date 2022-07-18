package com.csinfotechbd.collection.settings.officeTimeSetup;


import com.csinfotechbd.base.BaseInfo;
import com.csinfotechbd.common.CommonEntity;
import lombok.Data;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
@JsonSerialize
public class OfficeTimeSetupInfo extends CommonEntity {

    @Temporal(TemporalType.TIME)
    @DateTimeFormat(pattern = "HH:mm")
    private Date officeHour;

    @Temporal(TemporalType.TIME)
    @DateTimeFormat(pattern = "HH:mm")
    private Date logoutHour;

    public Date getOfficeHour() {
        return officeHour;
    }

    public void setOfficeHour(Date officeHour) {
        this.officeHour = officeHour;
    }

    public Date getLogoutHour() {
        return logoutHour;
    }

    public void setLogoutHour(Date logoutHour) {
        this.logoutHour = logoutHour;
    }
}
