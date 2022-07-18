package com.csinfotechbd.collection.settings.vipStatus;

import com.csinfotechbd.common.CommonEntity;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;


@Data
@Entity
public class VipStatus extends CommonEntity {

    @NotBlank(message = "Please enter customer's vip status")
    @Column(unique = true)
    private String name;


    public VipStatus() {
    }

    public VipStatus(Long id) {
        this.setId(id);
    }


}
