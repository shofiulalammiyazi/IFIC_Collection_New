package com.unisoft.collection.automaticDistribution.distributionExceptionCard;

import com.unisoft.collection.settings.ageCode.AgeCodeService;
import com.unisoft.collection.settings.employee.EmployeeService;
import com.unisoft.collection.settings.productType.ProductTypeRepository;
import com.unisoft.customerbasicinfo.CustomerBasicInfoService;

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
