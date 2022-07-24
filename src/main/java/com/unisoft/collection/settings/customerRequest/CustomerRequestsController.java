package com.unisoft.collection.settings.customerRequest;


import com.unisoft.customerbasicinfo.CustomerBasicInfoEntityRepository;
import com.unisoft.dms.DmsDocumentProperty;
import com.unisoft.dms.DmsFileSaver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Controller
@RequestMapping("/collection/setting/customerRequests")
public class CustomerRequestsController {

    @Autowired
    private CustomerRequestsService customerRequestsService;
    @Autowired
    private CustomerRequestsRepository customerRequestsRepository;
    @Autowired
    private CustomerBasicInfoEntityRepository customerBasicInfoEntityRepository;

    @Autowired
    private DmsFileSaver dmsFileSaver;

    @GetMapping(value = "/list")
    @ResponseBody
    public List<CustomerRequestDto> viewList(@RequestParam String customerId){
        List<CustomerRequestDto> customerRequestDtos = customerRequestsService.findCustomerRequestByCustomerId(customerId);
       return customerRequestDtos;
    }

//    @GetMapping(value = "/list")
//    @ResponseBody
//    public List<CustomerRequestsEntity> viewList(@RequestParam(value = "id") String id){
//        CustomerBasicInfoEntity customerBasicInfoEntity = customerBasicInfoEntityRepository.findById(new Long(id)).get();
//        List<CustomerRequestsEntity> customerRequestsEntities = customerRequestsRepository.findByCustomerBasicInfo(customerBasicInfoEntity);
//        return customerRequestsEntities;
//    }

    @PostMapping(value = "/save")
    @ResponseBody
    public List<CustomerRequestsEntity> save(CustomerRequestsEntity customerRequestsEntity){
        customerRequestsService.saveCustomerReqInfo(customerRequestsEntity);
        return customerRequestsRepository.findByCustomerBasicInfo(customerRequestsEntity.getCustomerBasicInfo());
    }

    @GetMapping(value = "/findByCustomerId")
    @ResponseBody
    public CustomerRequestsEntity findById(@RequestParam Long id){
        CustomerRequestsEntity customerRequestsEntity =  customerRequestsService.findByid(id);
        System.out.println("test");
        return customerRequestsEntity;
    }


    @GetMapping(value = "/approveCustomer")
    @ResponseBody
    public void approveCustomer(@RequestParam Long id){
        CustomerRequestsEntity customerRequestsEntity = customerRequestsService.findByid(id);
        customerRequestsEntity.setStatus(CustomerRequestsStatus.CONFIRM);
        customerRequestsService.save(customerRequestsEntity);
    }

    @GetMapping(value = "/rejectCustomer")
    @ResponseBody
    public void rejectCustomer(@RequestParam Long id){
        CustomerRequestsEntity customerRequestsEntity = customerRequestsService.findByid(id);
        customerRequestsEntity.setStatus(CustomerRequestsStatus.REJECT);
        customerRequestsService.save(customerRequestsEntity);
    }

    @GetMapping("/fileUrl")
    @ResponseBody
    public String getFileUrl(@RequestParam(value = "dmsFileId") String dmsFileId) {
        String fileUrl = customerRequestsService.getDmsFileUrl(dmsFileId);
        System.out.println("file url: " + fileUrl);
        return fileUrl;
    }


    @GetMapping("/fileView")
    @ResponseBody
    public DmsDocumentProperty requestFileView(
            @RequestParam(value = "id") String id,
            @RequestParam(value = "name") String documentName,
            @RequestParam(value = "type") String documentType){
        String requestFileUrl = dmsFileSaver.getDocumentURL(id);
        return new DmsDocumentProperty(id, requestFileUrl, documentType, documentName);
    }

}
