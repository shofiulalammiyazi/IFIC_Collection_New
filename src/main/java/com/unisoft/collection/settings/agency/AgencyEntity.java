package com.unisoft.collection.settings.agency;
/*
Created by   Islam on 7/7/2019
*/

import com.google.gson.annotations.Expose;
import com.unisoft.base.BaseInfo;
import com.unisoft.user.User;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.Valid;

@Entity
@Data
public class AgencyEntity extends BaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String pin;

    private String contactPerson;

    private String contactNo;

    private String registeredAddress;

    private String agreementWithAgency;


    @Expose
    @OneToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "user_id")
    @Valid
    private User user;

    @Transient
    private MultipartFile file;
}
