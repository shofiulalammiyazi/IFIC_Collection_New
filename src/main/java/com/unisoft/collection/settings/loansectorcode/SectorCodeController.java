package com.unisoft.collection.settings.loansectorcode;

import com.unisoft.user.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping("/collection/loan/settings/sector-code")
public class SectorCodeController {

    @Autowired
    private SectorCodeService service;

    @GetMapping("/create")
    public String create(){
        return "collection/distribution/loan/sectorcode/create";
    }

    @GetMapping("/list")
    public String list(Model model){
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("sectorCodeList", service.findAllSectorCode());
        return "collection/distribution/loan/sectorcode/sectorcodelist";
    }


    @PostMapping("/save")
    public String saveLoanGuarantor(@RequestParam("file") MultipartFile file, HttpSession session, Model model) {
        if(file.isEmpty()){
            model.addAttribute("validationError","Attach a excel file");
            return "collection/distribution/loan/sectorcode/create";
        }

        if(!file.getContentType().equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet") && !file.getContentType().equals("application/vnd.ms-excel")){
            model.addAttribute("validationError","Only excel file format is supported");
            return "collection/distribution/loan/sectorcode/create";
        }

        Map errors = service.saveSectorCode(file);
        model.addAttribute("distributionErrors", errors);
        return "redirect:/collection/loan/settings/sector-code/list";
    }



    @GetMapping("/find")
    @ResponseBody
    public SectorCodeEntity findSectorCodeEntityByAccountNo(@RequestParam String accountNo){
        SectorCodeEntity sectorCodeEntity = service.findSectorCodeEntityByAccountNo(accountNo);
        return sectorCodeEntity;
    }
}
