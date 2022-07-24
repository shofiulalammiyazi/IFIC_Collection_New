package com.unisoft.customerloanprofile.vehicleRepossession;

import com.unisoft.common.CommonEntity;
import lombok.Data;

import javax.persistence.*;


@Entity
@Data
public class VehicleRepossessionFile extends CommonEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String fileName;
    private String dmsFileId;
    private String dmsFileType;


    @OneToOne(cascade = CascadeType.REFRESH)
    private VehicleRepossession vehicleRepossession;
}
