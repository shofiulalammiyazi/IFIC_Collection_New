package com.csinfotechbd.retail.card.dataEntry.additionalinfocard;

import com.csinfotechbd.audittrail.AuditTrailService;
import com.csinfotechbd.customerloanprofile.AdditionalInfo.AdditionalInfoStatus;
import com.csinfotechbd.user.UserPrincipal;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/collection/card/additionalInfo")
public class AdditionalInfoCardController {

    @Autowired
    private AdditionalInfoCardService additionalInfoService;

    @Autowired
    private AdditionalInfoCardRepository additionalInfoRepository;

    @Autowired
    private AuditTrailService auditTrailService;


    @GetMapping(value = "/list")
    @ResponseBody
    public List<AdditionalInfoCard> viewAdditionalInfoList(@RequestParam(value = "clientId", required=true) String clientId,
                                                           @RequestParam(value = "contractId", required=true) String contractId){
        List<AdditionalInfoCard> additionalInfoList = additionalInfoService.findAdditionalInfoByClientIdAndContactId(clientId,contractId);
        return additionalInfoList;
    }
//
//    @GetMapping(value = "/latest")
//    @ResponseBody
//    public AdditionalInfoCard viewLalestInfo(@RequestParam String accountNo){
//        AdditionalInfoCard additionalInfo = additionalInfoService.findByLastAccountNo(accountNo);
//        return additionalInfo;
//    }


    @PostMapping(value = "/save",consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public AdditionalInfoCard save(@RequestBody AdditionalInfoCard additionalInfo){

        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        additionalInfo.setDealerPin(user.getUsername());
        additionalInfo.setStatus(AdditionalInfoStatus.PENDING);

        if(additionalInfo.getId()== null) {
            additionalInfo.setCreatedBy(user.getUsername());
            additionalInfo.setCreatedDate(new Date());
            AdditionalInfoCard additionalInfo1 = additionalInfoService.save(additionalInfo);
            auditTrailService.saveCreatedData("Additional Info", additionalInfo);
            return additionalInfo1;
        }
        else {
            AdditionalInfoCard entity = additionalInfoRepository.getOne(additionalInfo.getId());
            AdditionalInfoCard oldEntity = new AdditionalInfoCard();
            BeanUtils.copyProperties(entity, oldEntity);

            additionalInfo.setModifiedBy(user.getUsername());
            additionalInfo.setModifiedDate(new Date());
            AdditionalInfoCard additionalInfo1 = additionalInfoService.save(additionalInfo);
            auditTrailService.saveUpdatedData("Additional Info", oldEntity, additionalInfo);
            return additionalInfo1;
        }
    }

    @GetMapping(value = "/edit")
    @ResponseBody
    public AdditionalInfoCard edit(@RequestParam(value = "id") Long id){
        AdditionalInfoCard additionalInfo = additionalInfoService.findById(id);
        return additionalInfo;
    }

    @GetMapping(value = "/approve")
    @ResponseBody
    public void approveMethod(@RequestParam Long id){
        AdditionalInfoCard additionalInfo = additionalInfoService.findAdditionalInfoById(id);
        additionalInfo.setStatus(AdditionalInfoStatus.CONFIRM);
        AdditionalInfoCard additionalInfo1 = additionalInfoService.save(additionalInfo);
        System.out.println(additionalInfo1.getStatus());
    }


    @GetMapping(value = "/reject")
    @ResponseBody
    public void rejectMethod(@RequestParam Long id){
        AdditionalInfoCard additionalInfo = additionalInfoService.findAdditionalInfoById(id);
        additionalInfo.setStatus(AdditionalInfoStatus.REJECT);
        AdditionalInfoCard additionalInfo1 = additionalInfoService.save(additionalInfo);
        System.out.println(additionalInfo1.getStatus());
    }
}
