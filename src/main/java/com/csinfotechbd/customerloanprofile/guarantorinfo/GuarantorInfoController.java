package com.csinfotechbd.customerloanprofile.guarantorinfo;

import com.csinfotechbd.customerbasicinfo.CustomerBasicInfoEntity;
import com.csinfotechbd.customerbasicinfo.CustomerBasicInfoEntityRepository;
import com.csinfotechbd.user.UserPrincipal;
import com.csinfotechbd.utillity.JsonHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/customerloanprofile/guarantorinfo/")
public class GuarantorInfoController {
    @Autowired
    private JsonHandler jsonHandler;
    @Autowired
    private GuarantorInfoService guarantorInfoService;
    @Autowired
    private GurantorInfoRepository gurantorInfoRepository;
    @Autowired
    private CustomerBasicInfoEntityRepository customerBasicInfoEntityRepository;

    @GetMapping("list")
    @ResponseBody
    public List<GuarantorInfoEntity> viewGuarantorList(@RequestParam(value = "id") String id){
        CustomerBasicInfoEntity customerBasicInfoEntity = customerBasicInfoEntityRepository.findById(new Long(id)).get();
        List<GuarantorInfoEntity> guarantorInfoEntities = gurantorInfoRepository.findByCustomerBasicInfo(customerBasicInfoEntity);
        return guarantorInfoEntities;
    }

//    @PostMapping(value = "save")
//    @ResponseBody
//    public List<GuarantorInfoEntity> save(GuarantorInfoEntity guarantorInfoEntity){
//        guarantorInfoService.saveGuarantorInfo(guarantorInfoEntity);
//        return gurantorInfoRepository.findByCustomerBasicInfo(guarantorInfoEntity.getCustomerBasicInfo());
//    }





    @PostMapping(value="/save", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Map<String, Object>> saveReference(@RequestBody GuarantorInfoEntity guarantorInfo ){
        Map<String,Object> resultMap=new HashMap<String,Object>();
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        guarantorInfo.setCreatedBy(Long.toString(user.getId()));
        guarantorInfo.setCreatedDate(new Date());
        guarantorInfo.setDealerPin(user.getEmpId());
        HttpHeaders header = jsonHandler.setHeader(Collections.singletonMap("user-id", String.valueOf(user.getId())));
        HttpEntity<String> req = new HttpEntity<>(jsonHandler.convertToJSON(guarantorInfo),header);
        resultMap.put("guarantorInfo", guarantorInfoService.guarantorInfoSave(guarantorInfo));
        resultMap.put("successMsg", "Guarantor info Successfully Saved.");
        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);}

    @GetMapping("find-by")
    @ResponseBody
    public GuarantorInfoEntity findById(@RequestParam Long id){
        GuarantorInfoEntity guarantorInfoEntity =  guarantorInfoService.findByid(id);
        System.out.println("test");
        return guarantorInfoEntity;
    }


    @GetMapping("approve-guarantor")
    @ResponseBody
    public void appromveGuarantor(@RequestParam Long id){
        GuarantorInfoEntity guarantorInfoEntity = guarantorInfoService.findByid(id);
        guarantorInfoEntity.setStatus(GuarantorInfoStatus.CONFIRM);
        guarantorInfoService.save(guarantorInfoEntity);
    }

    @GetMapping("reject-guarantor")
    @ResponseBody
    public void rejectGuarantor(@RequestParam Long id){
        GuarantorInfoEntity guarantorInfoEntity = guarantorInfoService.findByid(id);
        guarantorInfoEntity.setStatus(GuarantorInfoStatus.REJECT);
        guarantorInfoService.save(guarantorInfoEntity);
    }




    @GetMapping("view")
    public ResponseEntity<Map<String, Object>> view(@RequestParam(value = "id", required = true) String loanAccountNo) throws IOException {
        List<GuarantorInfoEntity> list=guarantorInfoService.guarantorInfoEntityList(loanAccountNo);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("guarantorInfo", list);
        resultMap.put("successMsg", "Guarantor Info viewed!");
        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }
}
