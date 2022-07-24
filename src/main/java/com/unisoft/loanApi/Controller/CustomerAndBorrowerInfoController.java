package com.unisoft.loanApi.Controller;

import com.unisoft.collection.settings.employee.EmployeeService;
import com.unisoft.loanApi.model.*;
import com.unisoft.loanApi.service.CustomerOtherInfoService;
import com.unisoft.user.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by
 * Created at May 30, 2021
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/loan/api/customer-other-info")
public class CustomerAndBorrowerInfoController {

    private final CustomerOtherInfoService service;
    private final EmployeeService employeeService;


    @PostMapping("/save")
    public Map<String, Object> addNew(@RequestBody CustomerAndBorrowerInfo entity) {
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        entity.setDealerPin(userPrincipal.getUsername());
        entity.setStatus(CustomerAndBorrowerInfoStatus.PENDING);
        Map<String, Object> result = service.storeData(entity);
        result.put("id", entity.getId());
        result.put("status", entity.getStatus());
        return result;
    }

    @GetMapping(value = "/view")
    public CustomerAndBorrowerInfo viewPage(Long customerId) {

        CustomerAndBorrowerInfo customerAndBorrowerInfo = service.findByCustomerId(customerId);
        return customerAndBorrowerInfo;
    }


    @GetMapping(value = "/edit")
    @ResponseBody
    public CustomerAndBorrowerInfoDto edit(@RequestParam(value = "id") Long id){
        CustomerAndBorrowerInfoDto customerAndBorrowerInfoDto = service.findById(id);
        System.out.println(customerAndBorrowerInfoDto.getId()+":"+customerAndBorrowerInfoDto.getAccountName());
        return customerAndBorrowerInfoDto;
    }

    @GetMapping(value = "/find")
    @ResponseBody
    public CustomerAndBorrowerInfo findCustomerAndBorrowerInfo(@RequestParam Long customerId){
        CustomerAndBorrowerInfo customerAndBorrowerInfo = service.findByCustomerId(customerId);
        return customerAndBorrowerInfo;
    }


    @GetMapping(value = "/approve")
    @ResponseBody
    public void approveMethod(@RequestParam Long id){
        CustomerAndBorrowerInfo customerAndBorrowerInfo = service.findCustomerBorrowerById(id);
        customerAndBorrowerInfo.setStatus(CustomerAndBorrowerInfoStatus.CONFIRM);
        CustomerAndBorrowerInfo referenceInfoEntity1 = service.save(customerAndBorrowerInfo);
        System.out.println(referenceInfoEntity1.getStatus());
    }


    @GetMapping(value = "/reject")
    @ResponseBody
    public void rejectMethod(@RequestParam Long id){
        CustomerAndBorrowerInfo referenceInfoEntity = service.findCustomerBorrowerById(id);
        referenceInfoEntity.setStatus(CustomerAndBorrowerInfoStatus.REJECT);
        CustomerAndBorrowerInfo referenceInfoEntity1 = service.save(referenceInfoEntity);
        System.out.println(referenceInfoEntity1.getStatus());
    }


}
