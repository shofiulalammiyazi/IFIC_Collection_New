package com.unisoft.collection.settings.SMS.smsType;

import com.unisoft.collection.settings.SMS.generate.GeneratedSMSRepository;
import com.unisoft.collection.settings.SMS.smslog.SMSLogRepository;
import com.unisoft.collection.settings.SMS.smslog.SmsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping(value = "/collection/smstemplate")
public class SMSController {

    @Autowired
    private SMSService smsService;

    @Autowired
    private SMSLogRepository smsLogRepository;


    @GetMapping(value = "/list")
    public String viewAll(Model model)
    {
//        model.addAttribute("smsList", smsService.getAll());

        List<SMSEntity>  smsEntities = smsService.findAll();
        model.addAttribute("smsEntities", smsEntities);

        return "collection/settings/sms/smstemplate";
    }

    @GetMapping(value = "/create")
    public String viewAddPage(Model model) {
        SMSEntity smsEntity = new SMSEntity();

        model.addAttribute("smsEntity", smsEntity);

        model.addAttribute("smstemplate",new SMSEntity());
        return "collection/settings/sms/create";
    }

    @PostMapping(value = "/save-smstemplate")
    public String saveSMS(Model model, SMSEntity smsEntity){

        smsService.saveTemplate(smsEntity);
        return "redirect:/collection/smstemplate/find-all";

    }

    @GetMapping(value="/find-all")
    public String findAllSms(Model model){

        List<SMSEntity>  smsEntities = smsService.findAll();
        model.addAttribute("smsEntities", smsEntities);

        return "collection/settings/sms/smstemplate";
    }

    @GetMapping("/edit")
    public String editSMS(@RequestParam Long id, Model model){

        SMSEntity smsEntity = smsService.findSmsById(id);
        model.addAttribute("smsEntity", smsEntity);

        return "collection/settings/sms/create";
    }


    @GetMapping("/view")
    public String viewSMS(@RequestParam Long id, Model model){

        SMSEntity smsEntity = smsService.findSmsById(id);
        model.addAttribute("smsEntity", smsEntity);

        return "collection/settings/sms/view";
    }


    @GetMapping(value = "/smslog")
    public String smslog(Model model) {

        List<SmsLog> smsLogs = smsService.findAllDesc();
        model.addAttribute("smsList",smsLogs);

        return "collection/settings/SMS/smslog";
    }



}