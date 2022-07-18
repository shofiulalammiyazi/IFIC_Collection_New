package com.csinfotechbd.collection.settings.additionalInfoExcel;

import com.csinfotechbd.customerloanprofile.AdditionalInfo.AdditionalInfo;
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
@RequestMapping("/collection/setting/additionalInfoExcel")
public class AddiInfoExcelController {

    @Autowired
    private AddiInfoExcelService addiInfoExcelService;

    @GetMapping(value = "/create")
    public String create(){
        return "collection/distribution/loan/additionalExcel/create";
    }

    @PostMapping(value = "/create")
    public String saveAddiInfoExcel(@RequestParam("file") MultipartFile file, HttpSession session, Model model) {
        if(file.isEmpty()){
            model.addAttribute("validationError","Attach a excel file");
            return "collection/distribution/loan/additionalExcel/create";
        }

        if(!file.getContentType().equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet") && !file.getContentType().equals("application/vnd.ms-excel")){
            model.addAttribute("validationError","Only excel file format is supported");
            return "collection/distribution/loan/additionalExcel/create";
        }

        Map errors = addiInfoExcelService.saveAddiInfoExcel(file);
        model.addAttribute("distributionErrors", errors);
        return "redirect:/collection/setting/additionalInfoExcel/list";
    }

    @GetMapping(value = "/list")
    public String list(Model model){
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("addiInfoExcelList", addiInfoExcelService.findAll());
        return "collection/distribution/loan/additionalExcel/list";
    }

    @GetMapping(value = "/find")
    @ResponseBody
    public AdditionalInfo findAddiInfoByAccountNo(@RequestParam String accountNo){
        AdditionalInfo additionalInfo = addiInfoExcelService.findByAccountNo(accountNo);
        return additionalInfo;
    }

}
