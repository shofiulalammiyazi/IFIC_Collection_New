package com.csinfotechbd.collection.chequeManagement;

import com.csinfotechbd.collection.customerComplain.CustomerComplainEntity;
import com.csinfotechbd.common.CommonEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Data
@Entity
@Setter
@Getter
public class ChequeManagementFile extends CommonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String fileName;

    private String dmsFileId;

    private String dmsFileType;

    @OneToOne(cascade = CascadeType.REFRESH)
    private ChequeManagement chequeManagement;


}
