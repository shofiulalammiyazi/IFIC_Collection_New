package com.unisoft.collection.settings.customerinfouploadcard.professionsegment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping("/collection/card/profession-segment")
public class ProfessionSegmentController {
@Autowired
private ProfessionSegmentService service;


    @GetMapping("/create")
    public String create(){
        return professionSegmentHtml();
    }

    @PostMapping("save")
    public String saveProfessionSeg(@RequestParam("file") MultipartFile file, HttpSession session, Model model) {
        if(file.isEmpty()){
            model.addAttribute("validationError","Attach a excel file");
            return professionSegmentHtml();
        }

        if(!file.getContentType().equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet") && !file.getContentType().equals("application/vnd.ms-excel")){
            model.addAttribute("validationError","Only excel file format is supported");
            return professionSegmentHtml();
        }

        Map errors = service.saveProfessionSegment(file);
        model.addAttribute("distributionErrors", errors);
        return "redirect:/collection/card/profession-segment/list";
    }

    private String professionSegmentHtml(){
        return "customerinfouploadcard/professionsegment/create";
    }


    @GetMapping("/list")
    public String list(Model model){
        model.addAttribute("professionSegment",service.allProfessionSegment());
        return "customerinfouploadcard/professionsegment/profession-sigment-list";
    }




}
