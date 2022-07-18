package com.csinfotechbd.collection.automaticDistribution.postInterimException;
/*
Created by Monirul Islam at 8/21/2019
*/

import com.csinfotechbd.base.BaseInfo;
import com.csinfotechbd.collection.settings.employee.EmployeeInfoEntity;
import com.csinfotechbd.customerbasicinfo.CustomerBasicInfoEntity;
import com.google.gson.annotations.Expose;
import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class PostInterimException extends BaseInfo {

    @Expose
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String samIgnore;

    private String writeOffIgnore;

    private String VVIP;

    // Here we used ageCode but it means plasticCD for some weired reason
    private String[] ageCode;

    private String secureCard;

    @LazyCollection(value = LazyCollectionOption.FALSE)
    @OneToMany
    private List<CustomerBasicInfoEntity> customerBasicInfoEntityList = new ArrayList<>();

    @Expose
    @LazyCollection(value = LazyCollectionOption.FALSE)
    @OneToMany
    private List<EmployeeInfoEntity> employeeInfoEntities = new ArrayList<>();

    @Transient
    private List<String> customerIds = new ArrayList<>();

    @Transient
    private List<String> employeeIds = new ArrayList<>();
}
