package com.unisoft.collection.reasonDelinquency;

import com.unisoft.audittrail.AuditTrailService;
import com.unisoft.user.UserPrincipal;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/collection/reason-delinquency")
public class ReasonDelinquencyController {

    @Autowired
    private ReasonDelinquencyService reasonDelinquencyService;

    @Autowired
    private AuditTrailService auditTrailService;

    @Autowired
    private ReasonDelinquencyRepository reasonDelinquencyRepository;


    @PostMapping("/save")
    @ResponseBody
    public ReasonDelinquency save(ReasonDelinquency reasonDelinquency){

        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(reasonDelinquency.getId()==null){
            reasonDelinquency.setCreatedBy(user.getUsername());
            reasonDelinquency.setCreatedDate(new Date());
            ReasonDelinquency reasonDelinquency1 = reasonDelinquencyService.save(reasonDelinquency);
            auditTrailService.saveCreatedData("Reason for delinquency",reasonDelinquency1);
        }
        else{
            ReasonDelinquency oldEntity = reasonDelinquencyRepository.getOne(reasonDelinquency.getId());
            ReasonDelinquency previousEntity = new ReasonDelinquency();
            BeanUtils.copyProperties(oldEntity,previousEntity);

            reasonDelinquency.setCreatedBy(user.getUsername());
            reasonDelinquency.setCreatedDate(new Date());
            reasonDelinquencyRepository.save(reasonDelinquency);
            auditTrailService.saveUpdatedData("Reason for delinquency",previousEntity,reasonDelinquency);
        }
        ReasonDelinquency reasonDelinquency1 = reasonDelinquencyService.save(reasonDelinquency);
        return reasonDelinquency1;
    }


    @GetMapping("/find")
    @ResponseBody
    public List<ReasonDelinquency> getAccountWiseData(@RequestParam Long id){
        List<ReasonDelinquency> reasonDelinquencies = reasonDelinquencyService.findReasonDelinquencyById(id);
        return reasonDelinquencies;
    }

    @GetMapping("/findById")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getById(@RequestParam Long id){
        Optional<ReasonDelinquency> reasonDelinquencies = reasonDelinquencyRepository.findById(id);
        Map<String,Object> resultMap = new HashMap<String,Object>();

        resultMap.put("reasonDelinquencies",reasonDelinquencies);
        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
    }

    @GetMapping(value = "/findByAc")
    @ResponseBody
    public List<ReasonDelinquency> getAccountNo(@RequestParam(value = "id", required = true) String id){
        List<ReasonDelinquency> reasonDelinquencyList = reasonDelinquencyService.findByAccountNo(id);
        return reasonDelinquencyList;
    }

    @GetMapping("/teamlead/list")
    @ResponseBody
    public List<ReasonDelinquencyDto> getTeamleaderRfd(String designation) {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<ReasonDelinquencyDto> reasonDelinquencyDtoList = new ArrayList<>();

        if (designation.replaceAll(" ", "").toLowerCase().equals("supervisor") ){
            reasonDelinquencyDtoList = reasonDelinquencyService.findCurrentMonthReasonDelinquncyBySupervisorPin(user.getUsername());
        }
        if (designation.replaceAll(" ", "").toLowerCase().equals("manager") ){
            reasonDelinquencyDtoList = reasonDelinquencyService.findCurrentMonthReasonDelinquncyByManagerPin(user.getUsername());
        }



        return reasonDelinquencyDtoList;
    }

    @GetMapping("/delinquencywise/list")
    @ResponseBody
    public List<ReasonDelinquencyWiseDto> getTeamleaderRfdWise(@RequestParam("delinquency") String delinquency) {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return reasonDelinquencyService.findCurrentMonthReasonDelinquncyWiseByDealerPin(user.getUsername(), delinquency);
    }

    @GetMapping("/dealerwisereason/list")
    @ResponseBody
    public List<ReasonDelinquencyWiseDto> getDealerWiseRfdBySupervisor(@RequestParam("dealerPins") Long[] dealerPins, String designation) {

        List<ReasonDelinquencyWiseDto> reasonDelinquencyDtoList = new ArrayList<>();

        if (designation.replaceAll(" ", "").toLowerCase().equals("supervisor") ){
            reasonDelinquencyDtoList = reasonDelinquencyService.getDealerWiseRfdBySupervisor(Arrays.asList(dealerPins));
        }
        if (designation.replaceAll(" ", "").toLowerCase().equals("manager") ){
            reasonDelinquencyDtoList = reasonDelinquencyService.getDealerWiseRfdByManager(Arrays.asList(dealerPins));
        }

        return reasonDelinquencyDtoList;
    }
}
