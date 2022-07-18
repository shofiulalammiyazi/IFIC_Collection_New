package com.csinfotechbd.collection.samd.dataEntry.cardDistribution;

import com.csinfotechbd.common.CommonController;
import com.csinfotechbd.common.EnumDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/collection/samd/card-distribution")
public class SamCardDistributionController implements CommonController<SamCardDistribution> {

    @Autowired
    private EnumDataService enumDataService;

    @Autowired
    private SamCardDistributionService samCardDistributionService;

    @GetMapping("/manual")
    public String manual(Model model) {
        Map<String, Object> response = new HashMap<>();
        response.put("outcome", "success");
        model.addAttribute("response", response);
        return "samd/dataEntry/cardDistribution/manual";
    }

    @PostMapping("/manual")
    public String manual(Model model, @RequestParam("file") MultipartFile file) {
        Map<String, Object> response = samCardDistributionService.storeData(file);

        if (response.get("outcome").equals("failure")) {
            model.addAttribute("response", response);
            return "samd/dataEntry/cardDistribution/manual";
        }
        else
            return "redirect:/collection/samd/card-distribution/list";
    }

    @Override
    @GetMapping("/list")
    public String list(Model model) {
        List<SamCardDistribution> allDistributions = samCardDistributionService.findAllData();

        model.addAttribute("dealers", enumDataService.getDealers());
        model.addAttribute("distributions", allDistributions);

        return "samd/dataEntry/cardDistribution/list";
    }

    @ResponseBody
    @PostMapping("/manual-dealer-allocation")
    public Map<String, Object> manualDealerAllocation(@RequestBody SamCardDistributionDTO distributionDTO){
        Map<String, Object> response = samCardDistributionService.updateManualDealerAllocationData(distributionDTO);
        return response;
    }

    @Override
    public String create(Model model) {
        return null;
    }

    @Override
    public String create(Model model, SamCardDistribution entity) {
        return null;
    }

    @Override
    public String view(Model model, Long id) {
        return null;
    }

    @Override
    public String delete() {
        return null;
    }
}
