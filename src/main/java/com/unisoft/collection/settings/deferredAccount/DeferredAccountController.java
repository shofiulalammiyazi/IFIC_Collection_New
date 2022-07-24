package com.unisoft.collection.settings.deferredAccount;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/collection/deferred/")
public class DeferredAccountController {

    private final DeferredAccountService deferredAccountService;
    private final DeferredAccountRepository deferredAccountRepository;

    @GetMapping(value = "list")
    public String viewAll(Model model) {
        model.addAttribute("deferredAccList", deferredAccountRepository.findAll());
        return "collection/settings/deferredAccount/list";
    }

    @GetMapping("/upload-excel")
    public String employeeUpload() {
        return "collection/settings/deferredAccount/uploadexcel";
    }

    @PostMapping("/upload-excel")
    public String employeeUpload(@RequestParam("file") MultipartFile multipartFile, HttpSession session) {
        List errors = deferredAccountService.saveFromExcel(multipartFile);
        session.setAttribute("errors", errors);
        return "redirect:list";
    }

    @GetMapping("/find")
    @ResponseBody
    public DeferredAccount findDeferredByAccountNo(@RequestParam String accountNo){
        DeferredAccount deferredAccount = deferredAccountService.findDeferredAccount(accountNo);
        return deferredAccount;
    }
}
