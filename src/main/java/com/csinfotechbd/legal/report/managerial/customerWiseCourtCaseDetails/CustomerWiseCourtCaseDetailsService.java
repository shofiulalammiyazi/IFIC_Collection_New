package com.csinfotechbd.legal.report.managerial.customerWiseCourtCaseDetails;


import com.csinfotechbd.legal.dataEntry.caseEntry.LitigationCaseInfo;
import com.csinfotechbd.legal.dataEntry.caseEntry.LitigationCaseInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerWiseCourtCaseDetailsService {


    @Autowired
    private CustomerWiseCourtCaseDetailsRepository repository;

    public List<CustomerWiseCourtCaseDetailsDto> getCustomerWiseCaseList(String accountName, List<Long> caseFiledTypes, List<Long> caseTypes, List<String> branches) {
        List<Tuple> tupleList = repository.getCustomerWiseCaseList(accountName, caseFiledTypes, caseTypes, branches);
        return tupleList.stream().map(CustomerWiseCourtCaseDetailsDto::new).collect(Collectors.toList());
    }

//    public List<CustomerWiseCourtCaseDetailsDto> getCustomerWiseCaseList(String accountNo, List<Long> caseFiledTypes, List<Long> caseTypes, List<String> branches) {
////        List<String> accountNos = Arrays.asList(accountNo.split(","));
//        List<Tuple> tupleList = repository.getCustomerWiseCaseList(accountNo, caseFiledTypes, caseTypes, branches);
//        return tupleList.stream().map(CustomerWiseCourtCaseDetailsDto::new).collect(Collectors.toList());
//    }
//
//    public List<LitigationCaseInfoDto> getAccountInfos() {
//        List<LitigationCaseInfoDto> list = new LinkedList<>();
//        List<LitigationCaseInfo> caseInfos = repository.findAll();
//
//        for (LitigationCaseInfo caseInfo : caseInfos) {
//            LitigationCaseInfoDto dto = new LitigationCaseInfoDto();
//            dto.setNameOfAcc(caseInfo.getNameOfAcc());
//            dto.setCustomerAccNum(caseInfo.getCustomerAccNum());
//            list.add(dto);
//        }
//        return list;
//    }


}
