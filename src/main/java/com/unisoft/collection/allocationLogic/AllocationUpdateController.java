package com.unisoft.collection.allocationLogic;

import com.unisoft.collection.settings.employee.EmployeeInfoEntity;
import com.unisoft.user.UserPrincipal;
import com.unisoft.utillity.JsonHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/allocation-logic")
public class AllocationUpdateController {

    @Autowired
    private  PeopleAllocationLogicRepository peopleAllocationLogicRepository;
    @Autowired
    private  PeopleAllocationLogicService peopleAllocationLogicService;
    @Autowired
    private JsonHandler jsonHandler;

    @PostMapping(value = "/update", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Map<String, Object>> saveCadEndInfo(Model model, @RequestBody AllocationLogicWrapper allocationLogicWrapper) throws IOException {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        HttpHeaders header = jsonHandler.setHeader(Collections.singletonMap("user-id", String.valueOf(user.getId())));
//        HttpEntity<String> req = new HttpEntity<>(jsonHandler.convertToJSON(allocationLogicWrapper.getAllocationLogicDtos()), header);

        List<PeopleAllocationLogicInfo> allocationLogicDtoList = new ArrayList<>();
      for (int i=0;i<allocationLogicWrapper.getAllocationLogicDtos().size();i++){
          PeopleAllocationLogicInfo info=peopleAllocationLogicService.getById(Long.parseLong(allocationLogicWrapper.getAllocationLogicDtos().get(i).getId()));
          info.setTeamlead(new EmployeeInfoEntity(Long.parseLong(allocationLogicWrapper.getAllocationLogicDtos().get(i).getSupervisorId())));
          info.setSupervisor(new EmployeeInfoEntity(Long.parseLong(allocationLogicWrapper.getAllocationLogicDtos().get(i).getManagerId())));
          info.setManager(new EmployeeInfoEntity(Long.parseLong(allocationLogicWrapper.getAllocationLogicDtos().get(i).getSrManagerId())));
          allocationLogicDtoList.add(info);
      }

        resultMap.put("allocationLogicDtoList", peopleAllocationLogicRepository.saveAll(allocationLogicDtoList));

        resultMap.put("message", "Data   Successfully Update.");
        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
    }
}
