package com.csinfotechbd.collection.samd.setup.businessSegment;

import com.csinfotechbd.collection.settings.location.LocationEntity;
import com.csinfotechbd.collection.settings.location.LocationService;
import com.csinfotechbd.common.CommonController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/collection/samd/businessSegment")
public class BusinessSegmentSamdController implements CommonController<BusinessSegmentSamd> {

    @Autowired
    private LocationService locationService;
    @Autowired
    private BusinessSegmentSamdService businessSegmentSamdService;

    @Override
    @GetMapping("/create")
    public String create(Model model){
        List<LocationEntity> locationList = locationService.getLocList();

        model.addAttribute("locationList",locationList);

        return "samd/setup/businessSegment/create";
    }

    @Override
    @PostMapping("/create")
    public String create (Model model, BusinessSegmentSamd businessSegmentSamd){
        businessSegmentSamdService.storeData(businessSegmentSamd);
        return "redirect:/collection/samd/businessSegment/list";
    }

    @Override
    public String view(Model model, Long id) {
        return null;
    }

    @GetMapping("/edit")
    public String edit(Model model, @RequestParam(value = "id", required = true) Long id) {
        BusinessSegmentSamd businessSegmentSamd = businessSegmentSamdService.findDataById(id);

        List<LocationEntity> locationList = locationService.getLocList();

        model.addAttribute("businessSegment", businessSegmentSamd);
        model.addAttribute("locationList", locationList);
        return "samd/setup/businessSegment/edit";
    }

    @PostMapping("/edit")
    public String edit(Model model, BusinessSegmentSamd entity) {
        businessSegmentSamdService.updateData(entity);
        return "redirect:/collection/samd/businessSegment/list";
    }

    @Override
    @GetMapping("/list")
    public String list(Model model) {
        List<BusinessSegmentSamd> allData = businessSegmentSamdService.findAllData();
        model.addAttribute("allData", allData);
        return "samd/setup/businessSegment/list";
    }

    @Override
    public String delete() {
        return null;
    }
}
