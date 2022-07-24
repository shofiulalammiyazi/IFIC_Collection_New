package com.unisoft.collection.samd.setup.businessRegion;

import com.unisoft.collection.settings.location.LocationEntity;
import com.unisoft.collection.settings.location.LocationService;
import com.unisoft.common.CommonController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
@RequestMapping("/collection/samd/businessRegion")
public class BusinessRegionSamdController implements CommonController<BusinessRegionSamd> {


    @Autowired
    private LocationService locationService;

    @Autowired
    private BusinessRegionService businessRegionService;

    @Override
    @GetMapping("/create")
    public String create(Model model){
        List<LocationEntity> locationList = locationService.getLocList();

        model.addAttribute("locationList",locationList);

        return "samd/setup/businessRegion/create";
    }

    @Override
    @PostMapping("/create")
    public String create (Model model, BusinessRegionSamd businessRegionSamd){
        businessRegionService.storeData(businessRegionSamd);
        return "redirect:/collection/samd/businessRegion/list";
    }

    @Override
    public String view(Model model, Long id) {
        return null;
    }

    @GetMapping("/edit")
    public String edit(Model model, @RequestParam(value = "id", required = true) Long id) {
        BusinessRegionSamd businessRegionSamd = businessRegionService.findDataById(id);

        List<LocationEntity> locationList = locationService.getLocList();

        model.addAttribute("businessRegion", businessRegionSamd);
        model.addAttribute("locationList", locationList);
        return "samd/setup/businessRegion/edit";
    }

    @PostMapping("/edit")
    public String edit(Model model, BusinessRegionSamd entity) {
        businessRegionService.updateData(entity);
        return "redirect:/collection/samd/businessRegion/list";
    }

    @Override
    @GetMapping("/list")
    public String list(Model model) {
        List<BusinessRegionSamd> allData = businessRegionService.findAllData();
        model.addAttribute("allData", allData);
        return "samd/setup/businessRegion/list";
    }

    @Override
    public String delete() {
        return null;
    }

}
