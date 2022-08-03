package com.csinfotechbd.collection.automaticDistribution.distributionExceptionCard;
/*
Created by Monirul Islam at 8/22/2019
*/

import com.csinfotechbd.collection.automaticDistribution.distributionExceptionCardModel.ProductGroupAgeCode;

import com.csinfotechbd.collection.settings.ageCode.AgeCodeService;

import com.csinfotechbd.collection.settings.employee.EmployeeService;

import com.csinfotechbd.collection.settings.productType.ProductTypeRepository;

import com.csinfotechbd.customerbasicinfo.CustomerBasicInfoService;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;


import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping(value = "/collection/distribution/exception/")
public class DistributionExceptionController {

    @Autowired
    private DistributionExceptionRepository distributionExceptionRepository;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private CustomerBasicInfoService customerBasicInfoService;

    @Autowired
    private AgeCodeService ageCodeService;

    @Autowired
    private ProductTypeRepository productTypeRepository;






}
