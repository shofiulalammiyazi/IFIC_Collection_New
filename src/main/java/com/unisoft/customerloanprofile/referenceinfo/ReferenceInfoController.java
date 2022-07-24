package com.unisoft.customerloanprofile.referenceinfo;

import com.unisoft.user.UserPrincipal;
import com.unisoft.utillity.JsonHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;


@RestController
@RequestMapping("/customerloanprofile/referenceinfo/")
public class ReferenceInfoController {
    @Autowired
    private ReferenceInfoService referenceInfoService;
    @Autowired
    private JsonHandler jsonHandler;
    @GetMapping("list")
    @ResponseBody
    public List<ReferenceInfoEntity> viewReferenceList(@RequestParam String customerId){
        List<ReferenceInfoEntity>referenceList = referenceInfoService.findReferenceInfoEntitiesByCustomerId(customerId);
        return referenceList;
    }

//    @PostMapping(value = "save",consumes = {MediaType.APPLICATION_JSON_VALUE},produces = {MediaType.APPLICATION_JSON_VALUE})
//    @ResponseBody
//    public boolean save( @RequestBody  ReferenceInfoEntity referenceInfoEntity){
//        referenceInfoService.saveReferenceInfo(referenceInfoEntity);
//        return true;
//    }
//
@PostMapping(value="/save", consumes = {MediaType.APPLICATION_JSON_VALUE})
public ResponseEntity<Map<String, Object>> saveReference(@RequestBody ReferenceInfoEntity referenceInfoEntity ){
    Map<String,Object> resultMap=new HashMap<String,Object>();
    UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    referenceInfoEntity.setCreatedBy(Long.toString(user.getId()));
    referenceInfoEntity.setCreatedDate(new Date());
    referenceInfoEntity.setDealerPin(user.getEmpId());
    HttpHeaders header = jsonHandler.setHeader(Collections.singletonMap("user-id", String.valueOf(user.getId())));
    HttpEntity<String> req = new HttpEntity<>(jsonHandler.convertToJSON(referenceInfoEntity),header);
    resultMap.put("referenceInfo", referenceInfoService.saveReferenceInfo(referenceInfoEntity));
    resultMap.put("successMsg", "Reference info Successfully Saved.");
    return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);}

    @GetMapping("edit")
    @ResponseBody
    public ReferenceInfoEntityDto edit(@RequestParam(value = "id") Long id){
        ReferenceInfoEntityDto referenceInfoEntity = referenceInfoService.findById(id);
        System.out.println(referenceInfoEntity.getId()+":"+referenceInfoEntity.getAccountNo());
        return referenceInfoEntity;
    }


    @GetMapping("approve")
    @ResponseBody
    public void approveMethod(@RequestParam Long id){
        ReferenceInfoEntity referenceInfoEntity = referenceInfoService.findReferenceInfoEntityById(id);
        referenceInfoEntity.setStatus(ReferenceInfoStatus.CONFIRM);
        ReferenceInfoEntity referenceInfoEntity1 = referenceInfoService.save(referenceInfoEntity);
        System.out.println(referenceInfoEntity1.getStatus());
    }


    @GetMapping("reject")
    @ResponseBody
    public void rejectMethod(@RequestParam Long id){
        ReferenceInfoEntity referenceInfoEntity = referenceInfoService.findReferenceInfoEntityById(id);
        referenceInfoEntity.setStatus(ReferenceInfoStatus.REJECT);
        ReferenceInfoEntity referenceInfoEntity1 = referenceInfoService.save(referenceInfoEntity);
        System.out.println(referenceInfoEntity1.getStatus());
    }


    @GetMapping("view")
    public ResponseEntity<Map<String, Object>> view(@RequestParam(value = "id", required = true) String loanAccountNo) throws IOException {
        List<ReferenceInfoEntity> list=referenceInfoService.referenceInfoEntityList(loanAccountNo);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("referenceInfo", list);
        resultMap.put("successMsg", "Reference Info viewed!");
        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }

}
