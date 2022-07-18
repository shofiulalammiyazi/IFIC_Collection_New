package com.csinfotechbd.customerloanprofile.AdditionalInfo;

import com.csinfotechbd.audittrail.AuditTrailService;
import com.csinfotechbd.collection.settings.visitLedger.VisitLedgerEntity;
import com.csinfotechbd.user.UserPrincipal;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/collection/additionalInfo")
public class AdditionalInfoController {

    @Autowired
    private AdditionalInfoService additionalInfoService;

    @Autowired
    private AdditionalInfoRepository additionalInfoRepository;

    @Autowired
    private AuditTrailService auditTrailService;

//    @GetMapping(value = "/list")
//    @ResponseBody
//    public List<AdditionalInfo> viewAdditionalInfoList(@RequestParam String customerId){
//        List<AdditionalInfo> additionalInfoList = additionalInfoService.findAdditionalInfoByCustomerId(customerId);
//        return additionalInfoList;
//    }

    @GetMapping(value = "/list")
    @ResponseBody
    public List<AdditionalInfo> viewAdditionalInfoList(@RequestParam String accountNo){
        List<AdditionalInfo> additionalInfoList = additionalInfoService.findAdditionalInfoByLoanAcNo(accountNo);
        return additionalInfoList;
    }

    @GetMapping(value = "/latest")
    @ResponseBody
    public AdditionalInfo viewLalestInfo(@RequestParam String accountNo){
        AdditionalInfo additionalInfo = additionalInfoService.findByLastAccountNo(accountNo);
        return additionalInfo;
    }
//    public AdditionalInfo viewLalestInfo(@RequestParam String customerId){
//        AdditionalInfo additionalInfo = additionalInfoService.findByLastId(customerId);
//        return additionalInfo;
//    }

    @PostMapping(value = "/save")
    @ResponseBody
    public AdditionalInfo save(AdditionalInfo additionalInfo){

        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        additionalInfo.setDealerPin(user.getUsername());
        additionalInfo.setStatus(AdditionalInfoStatus.PENDING);

        if(additionalInfo.getId()== null) {
            additionalInfo.setCreatedBy(user.getUsername());
            additionalInfo.setCreatedDate(new Date());
            AdditionalInfo additionalInfo1 = additionalInfoService.save(additionalInfo);
            auditTrailService.saveCreatedData("Additional Info", additionalInfo);
            return additionalInfo1;
        }
        else {
            AdditionalInfo entity = additionalInfoRepository.getOne(additionalInfo.getId());
            AdditionalInfo oldEntity = new AdditionalInfo();
            BeanUtils.copyProperties(entity, oldEntity);

            additionalInfo.setModifiedBy(user.getUsername());
            additionalInfo.setModifiedDate(new Date());
            AdditionalInfo additionalInfo1 = additionalInfoService.save(additionalInfo);
            auditTrailService.saveUpdatedData("Additional Info", oldEntity, additionalInfo);
            return additionalInfo1;
        }
    }

    @GetMapping(value = "/edit")
    @ResponseBody
    public AdditionalInfo edit(@RequestParam(value = "id") Long id){
        AdditionalInfo additionalInfo = additionalInfoService.findById(id);
        return additionalInfo;
    }

    @GetMapping(value = "/approve")
    @ResponseBody
    public void approveMethod(@RequestParam Long id){
        AdditionalInfo additionalInfo = additionalInfoService.findAdditionalInfoById(id);
        additionalInfo.setStatus(AdditionalInfoStatus.CONFIRM);
        AdditionalInfo additionalInfo1 = additionalInfoService.save(additionalInfo);
        System.out.println(additionalInfo1.getStatus());
    }


    @GetMapping(value = "/reject")
    @ResponseBody
    public void rejectMethod(@RequestParam Long id){
        AdditionalInfo additionalInfo = additionalInfoService.findAdditionalInfoById(id);
        additionalInfo.setStatus(AdditionalInfoStatus.REJECT);
        AdditionalInfo additionalInfo1 = additionalInfoService.save(additionalInfo);
        System.out.println(additionalInfo1.getStatus());
    }
}
