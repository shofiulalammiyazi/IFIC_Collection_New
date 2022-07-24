package com.unisoft.customerloanprofile.followup;


import com.unisoft.customerbasicinfo.CustomerBasicInfoEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/customerloanprofile/followup/")
public class FollowUpController {

    private final FollowUpService followUpService;

    @Autowired
    private FollowUpRepository followUpRepository;

    @Autowired
    private CustomerBasicInfoEntityRepository customerBasicInfoEntityRepository;

    @GetMapping("list")
    @ResponseBody
    public List<FollowUpEntity> viewFollowUpList(Long id){
        List<FollowUpEntity> byCustomerId = followUpService.getByCustomerId(id);
        return byCustomerId;
    }

    @PostMapping(value = "save")
    @ResponseBody
    public FollowUpEntity save(FollowUpEntity followUpEntity){
        FollowUpEntity followUpEntity1 = followUpService.save(followUpEntity);
        return followUpEntity1;
    }

    @GetMapping(value="/ById")
    public ResponseEntity<Map<String, Object>> view(@RequestParam(value = "id", required = true) String id ) throws IOException {
        Map<String,Object> resultMap=new HashMap<String,Object>();
        Optional<FollowUpEntity> followUpEntity =followUpRepository.findById(Long.parseLong(id));
        resultMap.put("followUpEntity", followUpEntity);
        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);

    }
}
