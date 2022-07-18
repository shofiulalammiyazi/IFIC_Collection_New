package com.csinfotechbd.collection.settings.ptpContactDetails;
/*
Created by Monirul Islam at 7/2/2019 
*/

import com.csinfotechbd.audittrail.AuditTrailService;
import com.csinfotechbd.user.UserPrincipal;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/collection/ptp_contact_details/")
public class PTPContactDetailsController {

    @Autowired
    private PTPContactDetailsService ptpContactDetailsService;
    @Autowired
    private AuditTrailService auditTrailService;

    @GetMapping(value = "list")
    public String viewAll(Model model)
    {
        model.addAttribute("CDList",ptpContactDetailsService.getAll());
        return "collection/settings/ptpContactDetails/ptp_contact_details";
    }

    @GetMapping(value = "view")
    public String viewPage(@RequestParam(value = "id") Long Id,Model model)
    {
        model.addAttribute("contactDetail",ptpContactDetailsService.getById(Id));
        return "collection/settings/ptpContactDetails/view";
    }

    @GetMapping(value = "create")
    public String addPage(Model model)
    {
        model.addAttribute("contactDetail",new PTPContactDetailsEntity());
        return "collection/settings/ptpContactDetails/create";
    }

    @PostMapping(value = "create")
    public String create(PTPContactDetailsEntity contactDetails)
    {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(contactDetails.getId() == null)
        {
            contactDetails.setCreatedBy(user.getUsername());
            contactDetails.setCreatedDate(new Date());
            boolean save=ptpContactDetailsService.savePtpCD(contactDetails);
            auditTrailService.saveCreatedData("PTP Contact Details", contactDetails);

        }else {
            PTPContactDetailsEntity oldEntity = ptpContactDetailsService.getById(contactDetails.getId());
            PTPContactDetailsEntity previousEntity = new PTPContactDetailsEntity();
            BeanUtils.copyProperties(oldEntity,previousEntity);

            contactDetails.setModifiedBy(user.getUsername());
            contactDetails.setModifiedDate(new Date());
            boolean update=ptpContactDetailsService.updatePTPCD(contactDetails);
            auditTrailService.saveUpdatedData("PTP Contact Details",previousEntity,contactDetails);
        }

        return "redirect:/collection/ptp_contact_details/list";
    }

    @GetMapping(value = "edit")
    public String editPage(@RequestParam(value = "id")Long Id,Model model)
    {
        model.addAttribute("contactDetail",ptpContactDetailsService.getById(Id));
        return "collection/settings/ptpContactDetails/create";
    }

    @GetMapping(value="listall")
    public ResponseEntity<Map<String, Object>> list() throws IOException {
        Map<String,Object> resultMap=new HashMap<String,Object>();
        List<PTPContactDetailsEntity> contact =ptpContactDetailsService.getActiveList();
        resultMap.put("contact", contact);
        resultMap.put("successMsg", "contact details Successfully Received!");
        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);

    }
}
