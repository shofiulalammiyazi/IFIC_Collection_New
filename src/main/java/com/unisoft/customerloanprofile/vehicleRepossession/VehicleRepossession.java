package com.unisoft.customerloanprofile.vehicleRepossession;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.unisoft.collection.settings.clstatus.CLStatus;
import com.unisoft.common.CommonEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Setter
@Getter
public class VehicleRepossession extends CommonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String vehicleType;
    private String vehicleModel;
    private String vehicleRegistrationNo;
    private String vehicleChassisNo;
    private String vehicleEngineNo;
    private String yearManufacturing;
    private String vehicleCC;
    private String presentStatus;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date agencyHandOverDate;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date repossessionDate;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date clStatusRepossessionDate;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date vehicleReleaseDate;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date samHandoverDate;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date auctionDate;
    private String agencyName;
    private String repossessBy;
    private String vehicleColour;
    private String brandName;

//    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
//    @JsonIgnore
//    private AssetClassificationLoanEntity cLStatus;

    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JsonIgnore
    private CLStatus cLStatus;

    @Transient
    @JsonIgnore
    private MultipartFile file;

    @Transient
    private Long clStatusId;

    private String customerId;

//    public AssetClassificationLoanEntity getcLStatus() {
//        return cLStatus = (cLStatus != null) ? cLStatus: new AssetClassificationLoanEntity();
//    }
//
//    public void setClStatusId(Long clStatusId) {
//        this.cLStatus = new AssetClassificationLoanEntity(clStatusId);
//    }

    /*@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "CUSTOMER_ID")
    @JsonIgnore
    private CustomerBasicInfoEntity customerBasicInfo;


    public void setCustomerBasicInfo(CustomerBasicInfoEntity customerBasicInfo) {
        this.customerBasicInfo = (customerBasicInfo != null) ? customerBasicInfo: new CustomerBasicInfoEntity();
    }

    public void setClientId(String customerId) {
        this.customerBasicInfo = new CustomerBasicInfoEntity(customerId);
    }*/
}
