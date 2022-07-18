package com.csinfotechbd.collection.settings.customerRequest;

import com.csinfotechbd.base.BaseInfo;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Data
@Getter
@Setter
public class CustomerRequestFile extends BaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Long id;

    private String fileName;
    private String dmsFileId;
    private String dmsFileType;

    @OneToOne(cascade = CascadeType.REFRESH)
    private CustomerRequestsEntity customerRequestsEntity;

    @Transient
    private Long customerRequestEntityId;

    public CustomerRequestsEntity getCustomerRequestEntity() {
        return customerRequestsEntity = (customerRequestsEntity!= null) ? customerRequestsEntity: new CustomerRequestsEntity();
    }

    public void setCustomerRequestEntityId(Long customerRequestEntityId) {
        this.customerRequestsEntity = new CustomerRequestsEntity(customerRequestEntityId);
    }

}
