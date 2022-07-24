package com.unisoft.collection.samd.dataEntry.timeExtensionTdConversion;

import com.unisoft.common.CommonController;
import com.unisoft.common.EnumDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/collection/samd/data-entry/time-extension-td-conversion")
public class TimeExtensionTDConversionController implements CommonController<TimeExtensionTdConversion> {

    private final EnumDataService enumDataService;
    private final TimeExtensionTDConversionService timeExtensionTDConversionService;

    @Override
    public String create(Model model) {
        return null;
    }

    @Override
    public String create(Model model, TimeExtensionTdConversion entity) {
        return null;
    }

    @ResponseBody
    @PostMapping("/create")
    public Map<String, Object> create(TimeExtensionTdConversion entity,
                                      @RequestParam("attachment") MultipartFile file){
        entity.setFile(file);
        Map<String, Object> response = timeExtensionTDConversionService.storeData(entity);
        return response;
    }

    @Override
    public String view(Model model, Long id) {
        return null;
    }

    @Override
    public String list(Model model) {
        return null;
    }

    @Override
    public String delete() {
        return null;
    }

//    @ResponseBody
//    @GetMapping("/get-by-customer-id")
//    public TimeExtensionTdConversion getByCustomerId(@RequestParam(value = "customerId", required = true) Long customerId){
//        TimeExtensionTdConversion timeExtensionTdConversion = timeExtensionTDConversionService.findDataByCustomerId(customerId);
//        return timeExtensionTdConversion;
//    }


    @ResponseBody
    @GetMapping("/get-by-customer-id")
    public TimeExtensionTdConversionDto getTimeExtensionByCustomerId(@RequestParam(value = "customerId") Long customerId) {
        TimeExtensionTdConversionDto timeExtensionTdConversionDto = timeExtensionTDConversionService.getByCustomerId(customerId);
        return timeExtensionTdConversionDto;
    }

    @ResponseBody
    @GetMapping("/get-cl-status")
    public Map<Long, String> getClStatus(){
        Map<Long, String> clsStatus = enumDataService.getClStatuses();
        return clsStatus;
    }

    @ResponseBody
    @GetMapping("/get-proposal-placed-to")
    public Map<Long, String> getProposalPlacedTo(){
        Map<Long, String> positions = enumDataService.getPositions();
        return positions;
    }

    @ResponseBody
    @GetMapping("/get-approval-levels")
    public Map<Long, String> getApprovalLevels(){
        Map<Long, String> levels = enumDataService.getApprovalLevel();
        return levels;
    }

    @ResponseBody
    @GetMapping("/get-proposal-prepared-reasons")
    public Map<Long, String> getProposalPreparedReasons(){
        Map<Long, String> reasons = enumDataService.getProposalPreparedReasons();
        return reasons;
    }

    @ResponseBody
    @GetMapping("/get-proposal-purposes")
    public Map<Long, String> getProposalPurposes(){
        Map<Long, String> purposes = enumDataService.getProposalPurposes();
        return purposes;
    }
}
