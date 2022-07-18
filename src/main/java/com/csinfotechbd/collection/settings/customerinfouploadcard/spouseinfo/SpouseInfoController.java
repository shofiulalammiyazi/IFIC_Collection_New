package com.csinfotechbd.collection.settings.customerinfouploadcard.spouseinfo;

import com.csinfotechbd.user.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping("/collection/card/spouse-info")
public class SpouseInfoController {
@Autowired
private SpouseInfoService service;

    @GetMapping("/create")
    public String create(){
        return spouseInfoHtmlPage();
    }

    @PostMapping("save")
    public String saveSpInfo(@RequestParam("file") MultipartFile file, HttpSession session, Model model) {
        if(file.isEmpty()){
            model.addAttribute("validationError","Attach a excel file");
            return spouseInfoHtmlPage();
        }

        if(!file.getContentType().equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet") && !file.getContentType().equals("application/vnd.ms-excel")){
            model.addAttribute("validationError","Only excel file format is supported");
            return spouseInfoHtmlPage();
        }

        Map errors = service.saveSpouseInfo(file);
       model.addAttribute("distributionErrors", errors);
        return "redirect:/collection/card/spouse-info/list";
    }



    @GetMapping("/list")
    public String list(Model model){
      model.addAttribute("spouseInfo",service.allSpouseInfo());
        return "customerinfouploadcard/spouseinfo/spouse-info-list";
    }


    private String spouseInfoHtmlPage(){
        return "customerinfouploadcard/spouseinfo/create";
    }

}
