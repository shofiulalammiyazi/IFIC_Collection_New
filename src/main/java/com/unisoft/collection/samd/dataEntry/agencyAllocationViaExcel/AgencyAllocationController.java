package com.unisoft.collection.samd.dataEntry.agencyAllocationViaExcel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/collection/samd/data-entry/agency-allocation-via-excel")
public class AgencyAllocationController {


    @Autowired
    private AgencyAllocationService agencyAllocationService;




    @GetMapping("/list")
    public String getAgencyAllocationList(Model model){
        List<AgencyAllocation> agencyAllocationList = agencyAllocationService.findAllLatest();
        model.addAttribute("agencyAllocationList", agencyAllocationList);
        return "samd/dataEntry/agencyAllocationViaExcel/list";
    }

    @GetMapping("/create")
    public String create(Model model){
        Map<String, Object> response = new HashMap<>();
        response.put("outcome", "success");
        model.addAttribute("response", response);
        return "samd/dataEntry/agencyAllocationViaExcel/create";
    }

    @PostMapping("/create")
    public String create(Model model, @RequestPart MultipartFile file){
        Map<String, Object> response = agencyAllocationService.saveData(file);

        if (response.get("outcome").equals("failure")){
            model.addAttribute("response", response);
            return "samd/dataEntry/agencyAllocationViaExcel/create";
        }else {
            return "redirect:/collection/samd/data-entry/agency-allocation-via-excel/list";
        }
    }
}
