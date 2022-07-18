package com.csinfotechbd.collection.customerComplain;

import com.csinfotechbd.common.CommonEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Data
@Entity
@Setter
@Getter
public class CustomerComplainFile extends CommonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String fileName;

    private String dmsFileId;

    private String dmsFileType;

    @OneToOne(cascade = CascadeType.REFRESH)
    private CustomerComplainEntity customerComplainEntity;

    @Transient
    private Long customerComplainEntityId;


    public CustomerComplainEntity getCustomerComplainEntity() {
        return customerComplainEntity = (customerComplainEntity != null) ? customerComplainEntity : new CustomerComplainEntity();
    }

    public void setCustomerComplainEntityId(Long customerComplainEntityId) {
        this.customerComplainEntity = new CustomerComplainEntity(customerComplainEntityId);
    }
}
