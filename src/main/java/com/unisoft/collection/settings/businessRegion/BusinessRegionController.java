package com.unisoft.collection.settings.businessRegion;

import com.unisoft.collection.settings.location.LocationEntity;
import com.unisoft.collection.settings.location.LocationService;
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
@RequestMapping("/businessRegion")
@Controller
public class BusinessRegionController {


    LocationService locationService;
    BusinessRegionService businessRegionService;

    @GetMapping("/create")
    public String createBusinessRegion(Model model){
        List<LocationEntity> locationEntityList = locationService.getLocList();
        model.addAttribute("locationList", locationEntityList);
        model.addAttribute("businessRegion", new BusinessRegionManagement());
        return "collection/businessRegion/create";
    }

    @PostMapping("/create")
    public String saveBusinessRegion(@Valid BusinessRegionManagement businessRegionManagement, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes){

        if(bindingResult.hasErrors()) {
            return returnToCreatePage(businessRegionManagement, model, "Region Name is required");
        }
        try{
            if(businessRegionManagement.getId()!=null)
                businessRegionService.update(businessRegionManagement);
            else
                businessRegionService.save(businessRegionManagement);

            redirectAttributes.addFlashAttribute("saved","Succesfully Saved");
        }
        catch (DataIntegrityViolationException ex){
            businessRegionManagement.setId(null);
            return returnToCreatePage(businessRegionManagement, model, "Region Name is already exist");
        }
        catch(Exception ex){
            ex.printStackTrace();
        }

        return "redirect:/businessRegion/list";
    }

    private String returnToCreatePage(@Valid BusinessRegionManagement businessRegionManagement, Model model, String msg) {
        List<LocationEntity> locationEntityList = locationService.getLocList();
        model.addAttribute("locationList", locationEntityList);
        model.addAttribute("businessRegion", businessRegionManagement);
        model.addAttribute("validationError", msg);
        return "collection/businessRegion/create";
    }

    @GetMapping("/edit")
    public String editBusinessRegion(@RequestParam("id") Long id, Model model){
        BusinessRegionManagement businessRegionManagement = businessRegionService.getById(id);
        model.addAttribute("businessRegion",businessRegionManagement);
        List<LocationEntity> locationEntityList = locationService.getLocList();
        model.addAttribute("locationList", locationEntityList);
        return "collection/businessRegion/create";
    }

    @GetMapping("/list")
    public String getBusinessRegionList(Model model){
        List<BusinessRegionManagement> businessRegionManagementList = businessRegionService.getList();
        model.addAttribute("businessRegionList",businessRegionManagementList);
        return "collection/businessRegion/business-region";
    }


}
