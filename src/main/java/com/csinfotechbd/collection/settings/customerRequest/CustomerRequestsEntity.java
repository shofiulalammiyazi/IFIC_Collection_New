package com.csinfotechbd.collection.settings.customerRequest;

import com.csinfotechbd.base.BaseInfo;
import com.csinfotechbd.customerbasicinfo.CustomerBasicInfoEntity;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;

@Entity
@Table(name = "customer_requests_entity")
@Data
public class CustomerRequestsEntity extends BaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String custDate;
    private String custTime;
    private String reqThough;
    private String reqDetails;
    private String mobileNo;
    private String reqTime;

    @Enumerated(EnumType.STRING)
    private CustomerRequestsStatus status;
    private String dealerPin;

    @Transient
    private MultipartFile file;


    public CustomerRequestsEntity() {
    }

    public CustomerRequestsEntity(Long id) {
        this.id = id;
    }

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "CUSTOMER_ID")
    private CustomerBasicInfoEntity customerBasicInfo;


}
