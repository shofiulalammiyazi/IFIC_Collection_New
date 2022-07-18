package com.csinfotechbd.retail.loan.dataEntry.writeOffAccountDistribution;


import com.csinfotechbd.collection.settings.employee.EmployeeInfoEntity;
import com.csinfotechbd.collection.settings.employee.EmployeeService;
import com.csinfotechbd.user.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/retail/loan/data-entry/write-off-account-distribution")
public class WriteOffAccountDistributionController {


    @Autowired
    private WriteOffAccountDistributionService writeOffAccountDistributionService;
    @Autowired
    private EmployeeService employeeService;


    @GetMapping("/list")
    @ResponseBody
    public List<WriteOffAccountDistribution> writeOffDistributionList(){
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        EmployeeInfoEntity employeeInfoEntity = employeeService.getByPin(userPrincipal.getUsername());
        List<WriteOffAccountDistribution>writeOffAccountDistributionList = writeOffAccountDistributionService.findByDealerPinAndLatest(employeeInfoEntity.getPin());
        return writeOffAccountDistributionList;
    }


    @GetMapping("/distribution/list")
    public String manualWriteOffDistributionList(Model model){
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        EmployeeInfoEntity employeeInfoEntity = employeeService.getByPin(userPrincipal.getUsername());
        List<WriteOffAccountDistribution>writeOffAccountDistributionList = writeOffAccountDistributionService.findByDealerPinAndLatest(employeeInfoEntity.getPin());
        model.addAttribute("writeOfDistribution", writeOffAccountDistributionList);
        return "retail/loan/writeOffAccountDistribution/list";
    }

    @GetMapping("/create")
    public String createWriteOffDistributionPage(Model model){
        model.addAttribute("writeOffDistribution", new WriteOffAccountDistribution());
        return "retail/loan/writeOffAccountDistribution/create";
    }


    @PostMapping("/create")
    public String createWriteOffDistribution(Model model, @RequestParam("file") MultipartFile file){

        if(file.isEmpty()){
            model.addAttribute("validationError","Attach a excel file");
            return "collection/distribution/writeOffAccount/create";
        }

        if(!file.getContentType().equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet") && !file.getContentType().equals("application/vnd.ms-excel")){
            model.addAttribute("validationError","Only excel file format is supported");
            return "collection/distribution/writeOffAccount/create";
        }

        writeOffAccountDistributionService.storeData(file);

        return "redirect:/retail/loan/data-entry/write-off-account-distribution/distribution/list";
    }

    @GetMapping("/write-off-account/distribution/summary")
    @ResponseBody
    public List<WriteOffAccountdistributionSummary> findCurrentMonthDealerWriteOffAccountDistribution(){
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<WriteOffAccountdistributionSummary> writeOffAccountdistributionSummaryList = writeOffAccountDistributionService.findCurrentMonthDealerWriteOffAccountDistribution(userPrincipal.getUsername());
        return writeOffAccountdistributionSummaryList;
    }
}
