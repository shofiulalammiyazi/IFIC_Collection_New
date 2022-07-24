package com.unisoft.collection.settings.monthlyworkingday;

import com.unisoft.audittrail.AuditTrailService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/collection/settings/monthlyworking/")
public class MonthlyWorkingDayController {
    @Autowired
    private MonthlyWorkingDayRepository monthlyWorkingDayRepository;
    @Autowired
    private AuditTrailService auditTrailService;

    @GetMapping(value = "list")
    public String getList(){
        return "collection/settings/monthlyworkingday/monthlyworkingday";
    }

    @GetMapping(value = "daylist")
    @ResponseBody
    public List<MonthlyWorkingDay> getDayList() {
        return monthlyWorkingDayRepository.findAll();
    }

    @PostMapping(value = "save")
    @ResponseBody
    public List<MonthlyWorkingDay> saveWorkingDay(@RequestBody MonthlyWorkingDayPayload monthlyWorkingDayPayload){
        if(monthlyWorkingDayPayload.getId() != null){
            Optional<MonthlyWorkingDay> byId = monthlyWorkingDayRepository.findById(new Long(monthlyWorkingDayPayload.getId()));
            if(byId.isPresent()){
                MonthlyWorkingDay monthlyWorkingDay = byId.get();

                List<MonthlyWorkingDayDetails> dayDetailsList = new ArrayList<>();
                for (MonthlyWorkingDayDetailsPayload payload : monthlyWorkingDayPayload.getPayloads()) {
                    MonthlyWorkingDayDetails monthlyWorkingDayDetails = new MonthlyWorkingDayDetails();
                    monthlyWorkingDayDetails.setId(new Long(payload.getId()));
                    monthlyWorkingDayDetails.setDayName(payload.getDayname());
                    monthlyWorkingDayDetails.setDayNumber(payload.getDaynumber());
                    monthlyWorkingDayDetails.setWorkingDay(payload.isWorkingdays());
                    dayDetailsList.add(monthlyWorkingDayDetails);
                }
                monthlyWorkingDay.setDetailsList(dayDetailsList);
                monthlyWorkingDayRepository.save(monthlyWorkingDay);
                auditTrailService.saveCreatedData("Monthly Working day",monthlyWorkingDayPayload);
            }
        }else{
            MonthlyWorkingDay oldEntity = monthlyWorkingDayRepository.getOne(Long.valueOf(monthlyWorkingDayPayload.getId()));
            MonthlyWorkingDay previouseEntity = new MonthlyWorkingDay();
            BeanUtils.copyProperties(oldEntity,previouseEntity);

            MonthlyWorkingDay monthlyWorkingDay = new MonthlyWorkingDay();
            monthlyWorkingDay.setName(monthlyWorkingDayPayload.getName());

            List<MonthlyWorkingDayDetails> dayDetailsList = new ArrayList<>();
            for (MonthlyWorkingDayDetailsPayload payload : monthlyWorkingDayPayload.getPayloads()) {
                MonthlyWorkingDayDetails monthlyWorkingDayDetails = new MonthlyWorkingDayDetails();
                monthlyWorkingDayDetails.setDayName(payload.getDayname());
                monthlyWorkingDayDetails.setDayNumber(payload.getDaynumber());
                monthlyWorkingDayDetails.setWorkingDay(payload.isWorkingdays());
                dayDetailsList.add(monthlyWorkingDayDetails);
            }
            monthlyWorkingDay.setDetailsList(dayDetailsList);
            monthlyWorkingDayRepository.save(monthlyWorkingDay);
            auditTrailService.saveUpdatedData("Monthly Working day",previouseEntity,monthlyWorkingDayPayload);
        }


        return monthlyWorkingDayRepository.findAll();
    }
}
