package com.csinfotechbd.customerloanprofile.vehicleRepossession;


import com.csinfotechbd.collection.settings.assetClassificationLoan.AssetClassificationLoanEntity;
import com.csinfotechbd.collection.settings.assetClassificationLoan.AssetClassificationLoanService;
import com.csinfotechbd.collection.settings.clstatus.CLStatus;
import com.csinfotechbd.collection.settings.clstatus.CLStatusService;
import com.csinfotechbd.customerbasicinfo.CustomerBasicInfoEntity;
import com.csinfotechbd.customerbasicinfo.CustomerBasicInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/customer-loan-profile/vehicle-repossession")
public class VehicleRepossessionController{


    @Autowired
    private VehicleRepossessionService repossessionService;

    @Autowired
    private CustomerBasicInfoService customerBasicInfoService;

//    @Autowired
//    private AssetClassificationLoanService assetClassificationLoanService;

    @Autowired
    private CLStatusService clStatusService;


    @GetMapping("/list")
    @ResponseBody
    public List<VehicleRepossessionDto> getVehicleList(@RequestParam String customerId){
        List<VehicleRepossessionDto> vehicleRepossessions = repossessionService.findVehicleRepossessionByCustomerId(customerId);
        System.out.println("test");
        return vehicleRepossessions;
    }


    @PostMapping("/save")
    @ResponseBody
    public VehicleRepossession createVehicleRepossession(VehicleRepossession vehicleRepossession){
         VehicleRepossession vehicleRepossession1=repossessionService.save(vehicleRepossession);
         System.out.println("test");
         return vehicleRepossession1;
    }


    @GetMapping("/find")
    @ResponseBody
    public VehicleRepossession findVehicleRepossessionById(@RequestParam Long id){
        VehicleRepossession vehicleRepossession = repossessionService.findVehicleRepossessionById(id);
        return vehicleRepossession;
    }

    @GetMapping("/cl-status")
    @ResponseBody
    public List<CLStatus> getClStatus(){
        List<CLStatus> clStatuses = clStatusService.getClStatusList();
        return clStatuses;
    }
}
