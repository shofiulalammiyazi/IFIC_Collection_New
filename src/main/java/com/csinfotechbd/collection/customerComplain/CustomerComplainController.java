package com.csinfotechbd.collection.customerComplain;


import com.csinfotechbd.collection.settings.employee.EmployeeInfoDto;
import com.csinfotechbd.collection.settings.employee.EmployeeInfoEntity;
import com.csinfotechbd.collection.settings.employee.EmployeeService;
import com.csinfotechbd.customerbasicinfo.CustomerBasicInfoEntity;
import com.csinfotechbd.customerbasicinfo.CustomerBasicInfoService;
import com.csinfotechbd.dms.DmsDocumentProperty;
import com.csinfotechbd.dms.DmsFileSaver;
import com.csinfotechbd.user.UserPrincipal;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/collection/customer-complain")
public class CustomerComplainController {

    @Autowired
    private CustomerComplainService customerComplainService;

    @Autowired
    private DmsFileSaver dmsFileSaver;

    @Autowired
    private CustomerBasicInfoService customerBasicInfoService;

    @Autowired
    private EmployeeService employeeService;


    @GetMapping("/complain-list")
    @ResponseBody
    public List<CustomerComplainEntity> getAllComplain() {
        List<CustomerComplainEntity> complains = customerComplainService.getAllComplain();
        return complains;
    }

    @PostMapping("/create")
    @ResponseBody
    public CustomerComplainEntity createComplain(CustomerComplainEntity customerComplainEntity) {
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        customerComplainEntity.setDealerPin(userPrincipal.getUsername());
        customerComplainEntity.setStatus(CustomerComplainStatus.PENDING);
        return customerComplainService.saveComplain(customerComplainEntity);
    }

    @GetMapping("/list")
    @ResponseBody
    public List<CustomerComplainDto> getComplainByCustomerId(@RequestParam(value = "customerId") Long customerId) {
        return customerComplainService.getComplainByCustomerId(customerId);
    }

    @GetMapping(value = "/findByCustomerId")
    @ResponseBody
    public CustomerComplainDto findById(@RequestParam Long id) {
        return customerComplainService.getCustomerCompainFileById(id).get(0);
    }

    @GetMapping(value = "/approveCustomerComplain")
    @ResponseBody
    public void approveCustomer(@RequestParam Long id) {
        CustomerComplainEntity customerComplainEntity = customerComplainService.findByid(id);
        customerComplainEntity.setStatus(CustomerComplainStatus.RESOLVED);
        customerComplainService.save(customerComplainEntity);
    }

    @GetMapping(value = "/rejectCustomerComplain")
    @ResponseBody
    public void rejectCustomer(@RequestParam Long id) {
        CustomerComplainEntity customerComplainEntity = customerComplainService.findByid(id);
        customerComplainEntity.setStatus(CustomerComplainStatus.REJECT);
        customerComplainService.save(customerComplainEntity);
    }

    @GetMapping("/file-url")
    @ResponseBody
    public String getFileUrl(@RequestParam(value = "dmsFileId") String dmsFileId) {
        return customerComplainService.getDmsFileUrl(dmsFileId);
    }


    @GetMapping("/view")
    @ResponseBody
    public DmsDocumentProperty complainFileView(
            @RequestParam(value = "id") String id,
            @RequestParam(value = "name") String documentName,
            @RequestParam(value = "type") String documentType) {
        String complainFileUrl = dmsFileSaver.getDocumentURL(id);
        return new DmsDocumentProperty(id, complainFileUrl, documentType, documentName);
    }

    @GetMapping(value = "/assignCustomerComplain")
    @ResponseBody
    public void assignCustomerComplain(@RequestParam("id") String id,
                                       @RequestParam("pin") String pin,
                                       @RequestParam("note") String note) {
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        CustomerComplainEntity customerComplainEntity = customerComplainService.findByid(Long.valueOf(id));
        CustomerBasicInfoEntity customerBasicInfoEntity = customerBasicInfoService.findById(customerComplainEntity.getCustomerId());
        EmployeeInfoEntity employeeInfoEntity = employeeService.getByPin(pin);

        CustomerComplainEntity customerComplainEntity1 = new CustomerComplainEntity();

        BeanUtils.copyProperties(customerComplainEntity, customerComplainEntity1);
        customerComplainEntity1.setAssignedBy(userPrincipal.getUsername());
        customerComplainEntity1.setAssigneePin(pin);
        customerComplainEntity1.setAssignee(employeeInfoEntity.getUser().getFirstName()+" "+employeeInfoEntity.getUser().getLastName());
        customerComplainEntity1.setCustDate(customerComplainEntity.getCustDate());
        customerComplainEntity1.setCustTime(customerComplainEntity.getCustTime());
        customerComplainEntity1.setReqDetails(customerComplainEntity.getReqDetails());
        customerComplainEntity1.setReqThough(customerComplainEntity.getReqThough());
        customerComplainEntity1.setReqTime(customerComplainEntity.getReqTime());
        customerComplainEntity1.setUnit(employeeInfoEntity.getUnit());
        customerComplainEntity1.setCustomerId(Long.valueOf(customerBasicInfoEntity.getId()));
        customerComplainEntity1.setAssigneeNote(note);

        customerComplainService.save(customerComplainEntity1);
    }


    @GetMapping("/get-list")
    @ResponseBody
    public List<CustomerComplainEntity> getCustomerComplainList(String customerId){
        Long custId = null;
        try{
            custId = Long.valueOf(customerId);
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        return customerComplainService.getCustomerComplainList(custId);
    }
}
