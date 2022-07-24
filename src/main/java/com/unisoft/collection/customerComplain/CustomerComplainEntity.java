package com.unisoft.collection.customerComplain;

import com.unisoft.common.CommonEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;

@Data
@Entity
@Setter
@Getter
public class CustomerComplainEntity extends CommonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Long id;
    private String custDate;
    private String custTime;
    private String reqThough;
    private String reqDetails;
    private String mobileNo;
    private String reqTime;

    private Long customerId;

    private String dealerPin;

    @Enumerated(EnumType.STRING)
    private CustomerComplainStatus status;

    @Transient
    private MultipartFile file;

    private String unit;
    private String assignee;
    private String assigneePin;
    private String assigneeNote;
    private String assignedBy;

    public CustomerComplainEntity() {
    }

    public CustomerComplainEntity(Long id) {
        this.id = id;
    }

}
