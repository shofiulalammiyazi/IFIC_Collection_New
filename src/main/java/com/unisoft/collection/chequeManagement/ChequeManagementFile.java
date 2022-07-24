package com.unisoft.collection.chequeManagement;

import com.unisoft.common.CommonEntity;
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
