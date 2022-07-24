package com.unisoft.collection.samd.dataEntry.loanAccountDistribution;

import com.unisoft.common.CommonController;
import com.unisoft.common.EnumDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/collection/samd/loan-account-distribution")
public class SamLoanAccountDistributionController implements CommonController<SamLoanAccountDistribution> {

    @Autowired
    private EnumDataService enumDataService;

    @Autowired
    private SamLoanAccountDistributionService samLoanAccountDistributionService;

    @GetMapping("/manual")
    public String manual(Model model) {
        Map<String, Object> response = new HashMap<>();
        response.put("outcome", "success");
        model.addAttribute("response", response);
        return "samd/dataEntry/loanAccountDistribution/manual";
    }

    @PostMapping("/manual")
    public String manual(Model model, @RequestParam("file") MultipartFile file) {
        Map<String, Object> response = samLoanAccountDistributionService.storeData(file);

        if (response.get("outcome").equals("failure")) {
            model.addAttribute("response", response);
            return "samd/dataEntry/loanAccountDistribution/manual";
        }
        else
            return "redirect:/collection/samd/loan-account-distribution/list";
    }

    @Override
    @GetMapping("/list")
    public String list(Model model) {
        List<SamLoanAccountDistribution> allDistributions = samLoanAccountDistributionService.findAllData();

        model.addAttribute("dealers", enumDataService.getUnitWiseDealers());
        model.addAttribute("agencies", enumDataService.getAgencies());
        model.addAttribute("distributions", allDistributions);

        return "samd/dataEntry/loanAccountDistribution/list";
    }

    @ResponseBody
    @PostMapping("/manual-dealer-allocation")
    public Map<String, Object> manualDealerAllocation(@RequestBody SamLoanAccountDistributionDTO distributionDTO){
        Map<String, Object> response = samLoanAccountDistributionService.updateManualDealerAllocationData(distributionDTO);
        return response;
    }

    @ResponseBody
    @PostMapping("/manual-agency-allocation")
    public Map<String, Object> manualAgencyAllocation(@RequestBody SamLoanAccountDistributionDTO distributionDTO){
        Map<String, Object> response = samLoanAccountDistributionService.updateManualAgencyAllocationData(distributionDTO);
        return response;
    }

    @Override
    public String create(Model model) {
        return null;
    }

    @Override
    public String create(Model model, SamLoanAccountDistribution entity) {
        return null;
    }

    @Override
    public String view(Model model, Long id){
        return null;
    }

    @Override
    public String delete() {
        return null;
    }
}
