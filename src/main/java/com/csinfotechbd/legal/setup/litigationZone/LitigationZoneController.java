package com.csinfotechbd.legal.setup.litigationZone;

import com.csinfotechbd.collection.settings.branch.BranchService;
import com.csinfotechbd.collection.settings.district.DistrictService;
import com.csinfotechbd.collection.settings.location.LocationService;
import com.csinfotechbd.common.CommonController;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/legal/setup/zone")
public class LitigationZoneController implements CommonController<LitigationZoneEntity> {

    private final LocationService locationService;
    private final LitigationZoneService litigationZoneService;
    private final BranchService branchService;
    private final DistrictService districtService;

    @Override
    @GetMapping("/create")
    public String create(Model model) {

        model.addAttribute("zone", new LitigationZoneEntity());
        model.addAttribute("locList", locationService.getActiveList());
        model.addAttribute("branches", branchService.getActiveList());
        model.addAttribute("districts", districtService.getActiveOnly());

        return "legal/setup/litigationZone/create";
    }

    @Override
    @PostMapping("/save")
    public String create(Model model, LitigationZoneEntity entity) {
        litigationZoneService.updateData(entity);
        return "redirect:/legal/setup/zone/list";
    }

    @Override
    @GetMapping("/view")
    public String view(Model model, Long id) {
        return null;
    }

    @Override
    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("zoneEntities", litigationZoneService.findAllData());
        return "legal/setup/litigationZone/list";
    }

    @GetMapping("/edit")
    public String edit(@RequestParam(value = "id") Long id, Model model) {

        model.addAttribute("zone", litigationZoneService.findDataById(id));
        model.addAttribute("locList", locationService.getActiveList());
        model.addAttribute("branches", branchService.getActiveList());
        model.addAttribute("districts", districtService.getActiveOnly());

        return "legal/setup/litigationZone/create";
    }

    @Override
    public String delete() {
        return null;
    }

}
