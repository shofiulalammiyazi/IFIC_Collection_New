package com.unisoft.collection.samd.setup.agencySamd;

import com.unisoft.base.BaseInfo;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;

@Entity
@Data
public class AgencySamdEntity extends BaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String contactPerson;
    private String contactNo;
    private String remarks;


    @Transient
    private MultipartFile file;

}
