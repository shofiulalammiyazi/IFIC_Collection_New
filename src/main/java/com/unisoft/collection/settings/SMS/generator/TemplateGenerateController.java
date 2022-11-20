package com.unisoft.collection.settings.SMS.generator;

import com.unisoft.collection.settings.SMS.SMSEntity;
import com.unisoft.collection.settings.SMS.SMSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping(value = "/collection/generator")
public class TemplateGenerateController {

    @Autowired
    private TemplateGenerateService templateGenerateService;

    @Autowired
    private SMSService smsService;

    @GetMapping("/list")
    public String showGenerator(Model model){
//        TemplateGenerate templateGenerate = templateGenerateService.findTemGenById(id);
//        model.addAttribute("templateGenerate", templateGenerate);

        List<TemplateGenerate> generates = templateGenerateService.findAll();
        model.addAttribute("generates", generates);

        SMSEntity smsEntity = new SMSEntity();
        model.addAttribute("smsEntity", smsEntity);


        return "collection/settings/sms/smsgenerator/view";
    }
    @GetMapping(value = "/create")
    public String viewAddPage(Model model) {

        TemplateGenerate templateGenerate = new TemplateGenerate();
        model.addAttribute("templateGenerate", templateGenerate);

        List<SMSEntity> smsEntityList = smsService.findAll();
        model.addAttribute("smsEntityList", smsEntityList);


        return "collection/settings/sms/smsgenerator/create";
    }

    @PostMapping(value = "/save-generator")
    public String saveSMSGenerator(Model model, TemplateGenerate templateGenerate){

        templateGenerateService.saveGenerate(templateGenerate);
        return "redirect:/collection/generator/find-all";

    }

    @GetMapping(value="/find-all")
    public String findAllSms(Model model){

        List<TemplateGenerate>  generate = templateGenerateService.findAll();
        model.addAttribute("generate", generate);

        return "collection/settings/sms/smsgenerator/generator";
    }

    @GetMapping("/edit")
    public String editGeneration(@RequestParam Long id, Model model){


        TemplateGenerate templateGenerate = templateGenerateService.findTemGenById(id);
        model.addAttribute("templateGenerate", templateGenerate);

        List<SMSEntity> smsEntityList = smsService.findAll();
        model.addAttribute("smsEntityList", smsEntityList);

//        SMSEntity smsEntity = new SMSEntity();
//        model.addAttribute("smsEntity", smsEntity);

        return "collection/settings/sms/smsgenerator/create";
    }

    @GetMapping("/view")
    public String viewGenerator(@RequestParam Long id, Model model){
        TemplateGenerate templateGenerate = templateGenerateService.findTemGenById(id);
        model.addAttribute("templateGenerate", templateGenerate);

        List<TemplateGenerate>  generate = templateGenerateService.findAll();
        model.addAttribute("generate", generate);

//        List<SMSEntity> smsEntityList = smsService.findAll();
//
//        model.addAttribute("smsEntityList", smsEntityList);
        SMSEntity smsEntity = new SMSEntity();
        model.addAttribute("smsEntity", smsEntity);

        return "collection/settings/sms/smsgenerator/show";

    }




}
