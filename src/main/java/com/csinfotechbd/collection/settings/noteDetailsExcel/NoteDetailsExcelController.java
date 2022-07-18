package com.csinfotechbd.collection.settings.noteDetailsExcel;

import com.csinfotechbd.customerloanprofile.dailynote.DailyNoteEntity;
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
@RequestMapping("/collection/settings/noteDetailsExcel")
public class NoteDetailsExcelController {

    @Autowired
    private NoteDetailsExcelService noteDetailsExcelService;

    @GetMapping(value = "/create")
    public String create(){
        return "collection/distribution/loan/notedetailsExcel/create";
    }


    @PostMapping(value = "/create")
    public String saveNoteDetailsExcel(@RequestParam("file") MultipartFile file, HttpSession session, Model model) {
        if(file.isEmpty()){
            model.addAttribute("validationError","Attach a excel file");
            return "collection/distribution/loan/notedetailsExcel/create";
        }

        if(!file.getContentType().equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet") && !file.getContentType().equals("application/vnd.ms-excel")){
            model.addAttribute("validationError","Only excel file format is supported");
            return "collection/distribution/loan/notedetailsExcel/create";
        }

        Map errors = noteDetailsExcelService.saveNoteDetailsExcel(file);
        model.addAttribute("distributionErrors", errors);
        return "redirect:/collection/settings/noteDetailsExcel/list";
    }

    @GetMapping(value = "/list")
    public String list(Model model){
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("noteExcelList", noteDetailsExcelService.findAll());
        return "collection/distribution/loan/notedetailsExcel/list";
    }

    @GetMapping(value = "/find")
    @ResponseBody
    public DailyNoteEntity findNoteDetailsByAccountNo(@RequestParam String accountNo){
        DailyNoteEntity dailyNoteEntity = noteDetailsExcelService.findByAccountNo(accountNo);
        return dailyNoteEntity;
    }
}
