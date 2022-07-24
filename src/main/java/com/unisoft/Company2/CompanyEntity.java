package com.unisoft.Company2;

import com.unisoft.base.BaseInfo;
import lombok.Data;

import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;

@Data
@Entity
@Table(name = "los_tb_s_company")
public class CompanyEntity extends BaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer companyId;

    //@NotEmpty(message = "Not Empty")
    private String name;

    //@NotEmpty(message = "Not Empty")
    private String shortName;
    @Transient // will not save or store into DB. used to get data from front
    // end only

    private MultipartFile file;

    private String logo;

    //@NotEmpty(message = "Not Empty")
    private String webAddress;
    //@NotEmpty(message = "Not Empty")

    private String tagLine;

    //@NotEmpty(message = "Not Empty")
    private String email;

    //@NotEmpty(message = "Not Empty")
    private String contactNo1;

    private String contactNo2;

    private String logoUrl;

//    @Transient
//    private String documentUrl;
//    @Transient
//    private String documentName;
//    @Transient
//    private String documentType;
}
