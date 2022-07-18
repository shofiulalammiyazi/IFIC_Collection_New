package com.csinfotechbd.collection.settings.customerinfouploadcard.sourcechannel;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping("/collection/card/source-channel")
public class SourceChannelController {
@Autowired
private SourceChannelService service;


    @GetMapping("/create")
    public String create(){
        return sourceChannelHtml();
    }

    @PostMapping("save")
    public String saveProfessionSeg(@RequestParam("file") MultipartFile file, HttpSession session, Model model) {
        if(file.isEmpty()){
            model.addAttribute("validationError","Attach a excel file");
            return sourceChannelHtml();
        }

        if(!file.getContentType().equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet") && !file.getContentType().equals("application/vnd.ms-excel")){
            model.addAttribute("validationError","Only excel file format is supported");
            return sourceChannelHtml();
        }

        Map errors = service.saveSourceChannel(file);
        model.addAttribute("distributionErrors", errors);
        return "redirect:/collection/card/source-channel/list";
    }

    private String sourceChannelHtml(){
        return "customerinfouploadcard/sourcechannel/create";
    }


    @GetMapping("/list")
    public String list(Model model){
        model.addAttribute("sourceChannel",service.allSourceChannel());
        return "customerinfouploadcard/sourcechannel/source-channel-list";
    }



}
