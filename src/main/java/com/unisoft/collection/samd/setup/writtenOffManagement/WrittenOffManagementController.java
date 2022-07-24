package com.unisoft.collection.samd.setup.writtenOffManagement;


import com.unisoft.audittrail.AuditTrailService;
import com.unisoft.collection.settings.assetClassificationLoan.AssetClassificationLoanEntity;
import com.unisoft.collection.settings.assetClassificationLoan.AssetClassificationLoanService;
import com.unisoft.user.UserPrincipal;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/collection/samd/setup/written-off-management")
public class WrittenOffManagementController {

    @Autowired
    private WrittenOffManagementService writtenOffManagementService;

    @Autowired
    private AssetClassificationLoanService assetClassificationLoanService;

    @Autowired
    private AuditTrailService auditTrailService;

    @Autowired
    private WrittenOffManagementRepository writtenOffManagementRepository;


    @PostMapping("/save")
    @ResponseBody
    public WrittenOffManagement saveWrittenOffManagement(WrittenOffManagement writtenOffManagement){

        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (writtenOffManagement.getId()==null) {
            writtenOffManagement.setCreatedBy(user.getUsername());
            writtenOffManagement.setCreatedDate(new Date());
            writtenOffManagementService.save(writtenOffManagement);
            auditTrailService.saveCreatedData("Written off management - SAMD", writtenOffManagement);
        }else {
            WrittenOffManagement oldEntity = writtenOffManagementRepository.getOne(writtenOffManagement.getId());
            WrittenOffManagement previousEntity = new WrittenOffManagement();
            BeanUtils.copyProperties(oldEntity, previousEntity);

            writtenOffManagement.setModifiedBy(user.getUsername());
            writtenOffManagement.setModifiedDate(new Date());
            writtenOffManagementRepository.save(writtenOffManagement);
            auditTrailService.saveUpdatedData("Written off management - SAMD", previousEntity, writtenOffManagement);
        }


//        WrittenOffManagement writtenOffManagement1 = writtenOffManagementService.save(writtenOffManagement);
       return writtenOffManagement;
    }


    @GetMapping("/find")
    @ResponseBody
    public WrittenOffManagement getWrittenOffManagementByCustomerId(@RequestParam String customerId){
        WrittenOffManagement writtenOffManagement = writtenOffManagementService.findWrittenOffManagementByCustomerId(customerId);
        return writtenOffManagement;
    }

    @GetMapping("/edit")
    @ResponseBody
    public Map<String, Object> editWrittenOffManagementByCustomerId(@RequestParam String customerId){
        Map<String, Object> objectMap = new HashMap<>();
        WrittenOffManagement writtenOffManagement = writtenOffManagementService.findWrittenOffManagementByCustomerId(customerId);
        objectMap.put("writtenOffManagement", writtenOffManagement == null ? null : writtenOffManagement);
        if (writtenOffManagement.getClStatus() != null){
            objectMap.put("clStatus", writtenOffManagement.getClStatus());
        }
        return objectMap;
    }


    @GetMapping("/cl-status/list")
    @ResponseBody
    public List<AssetClassificationLoanEntity> getCLStatusList(){
        List<AssetClassificationLoanEntity> assetClassificationLoanEntityList = assetClassificationLoanService.getAll();
        return assetClassificationLoanEntityList;
    }


    public Map<String, Object>getWrittenOffManagementList(@RequestParam String customerId){
        Map map = new HashMap();
        WrittenOffManagement writtenOffManagement = writtenOffManagementService.findWrittenOffManagementByCustomerId(customerId);
        map.put("writtenOffManagement", writtenOffManagement);
        map.put("clStatusList", writtenOffManagement.getClStatus());
        return map;
    }
}
