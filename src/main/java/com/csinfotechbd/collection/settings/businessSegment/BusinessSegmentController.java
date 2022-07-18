package com.csinfotechbd.collection.settings.businessSegment;

import com.csinfotechbd.collection.settings.businessRegion.BusinessRegionManagement;
import com.csinfotechbd.collection.settings.businessRegion.BusinessRegionService;
import com.csinfotechbd.collection.settings.location.LocationEntity;
import com.csinfotechbd.collection.settings.location.LocationService;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RequestMapping("/businessSegment")
@Controller
public class BusinessSegmentController {


    LocationService locationService;
    BusinessSegmentService businessSegmentService;

    @GetMapping("/create")
    public String createBusinessSegment(Model model){
        List<LocationEntity> locationEntityList = locationService.getLocList();
        model.addAttribute("locationList", locationEntityList);
        model.addAttribute("businessSegment", new BusinessSegment());
        return "collection/settings/businessSegment/create";
    }

    @PostMapping("/create")
    public String saveBusinessSegment(@Valid BusinessSegment businessSegment, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes){

        if(bindingResult.hasErrors()) {
            return returnToCreatePage(businessSegment, model, "Segment Name is required");
        }
        try{
            if(businessSegment.getId()!=null)
                businessSegmentService.update(businessSegment);
            else
                businessSegmentService.save(businessSegment);

            redirectAttributes.addFlashAttribute("saved","Succesfully Saved");
        }
        catch (DataIntegrityViolationException ex){
            businessSegment.setId(null);
            return returnToCreatePage(businessSegment, model, "Segment Name is already exist");
        }
        catch(Exception ex){
            ex.printStackTrace();
        }

        return "redirect:/businessSegment/list";
    }

    private String returnToCreatePage(@Valid BusinessSegment businessSegment, Model model, String msg) {
        List<LocationEntity> locationEntityList = locationService.getLocList();
        model.addAttribute("locationList", locationEntityList);
        model.addAttribute("businessSegment", businessSegment);
        model.addAttribute("validationError", msg);
        return "collection/settings/businessSegment/create";
    }

    @GetMapping("/edit")
    public String editBusinessSegment(@RequestParam("id") Long id, Model model){
        BusinessSegment businessSegment = businessSegmentService.getById(id);
        model.addAttribute("businessSegment",businessSegment);
        List<LocationEntity> locationEntityList = locationService.getLocList();
        model.addAttribute("locationList", locationEntityList);
        return "collection/settings/businessSegment/create";
    }

    @GetMapping("/list")
    public String getBusinessSegmentList(Model model){
        List<BusinessSegment> businessSegmentList = businessSegmentService.getList();
        model.addAttribute("businessSegmentList",businessSegmentList);
        return "collection/settings/businessSegment/business-segment";
    }


}
