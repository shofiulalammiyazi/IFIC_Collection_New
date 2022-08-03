package com.unisoft.collection.settings.agency;
/*
Created by   Islam on 7/7/2019
Modified by Abu Salek Shaon on 16/8/2021
*/


import com.unisoft.cryptography.CryptoException;
import com.unisoft.dms.DmsDocumentProperty;
import com.unisoft.dms.DmsFileSaver;
import lombok.RequiredArgsConstructor;
import org.apache.chemistry.opencmis.client.api.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/collection/agency/")
public class AgencyController {

    private final AgencyService agencyService;

    private final AgencyFileService agencyFileService;

    private final DmsFileSaver dmsFileSaver;

    @GetMapping(value = "list")
    public String viewAll(Model model) {
        model.addAttribute("agencyList", agencyService.getAll());
        return "collection/settings/agency/agency";
    }

    @GetMapping("create")
    public String addpage(Model model) {
        model.addAttribute("entity", new AgencyEntity());
        return "collection/settings/agency/create";
    }

//    @PostMapping(value = "create")
//    public String addnew(Model model, AgencyEntity entity) {
//        String output = agencyService.save(entity);
//        agencyFileService.save(entity);
//        switch (output) {
//            case "1":
//                return "redirect:/collection/agency/list";
//            default:
//                model.addAttribute("error", output);
//        }
//        model.addAttribute("entity", entity);
//        return "redirect:/collection/agency/list";
//    }

    @PostMapping("create")
    public String createAgency(Model model, AgencyDto agencyDto, BindingResult result){
        if (!result.hasErrors()){
            AgencyDto agencyDto1 = agencyService.create(agencyDto);
            model.addAttribute("success", true);
            return "redirect:/collection/agency/list";
        }
        model.addAttribute("error", true);
        return "collection/settings/agency/create";
    }

    @GetMapping(value = "edit")
    public String editPage(@RequestParam(value = "id") Long id, Model model) {
        model.addAttribute("entity", agencyService.getById(id));
        return "collection/settings/agency/create";
    }

    @GetMapping(value = "view")
    public String viewPage(@RequestParam(value = "id") Long id, Model model) {
        AgencyEntityDto agencyEntityDto = agencyService.findAgencyEntityById(id);
        model.addAttribute("agency", agencyEntityDto);
        return "collection/settings/agency/view";
    }

    @GetMapping("/view-file")
    @ResponseBody
    public DmsDocumentProperty getPropertyValue(
            @RequestParam(value = "id") String id,
            @RequestParam(value = "name") String documentName,
            @RequestParam(value = "type") String documentType) {
        String documentURL = dmsFileSaver.getDocumentURL(id);
        return new DmsDocumentProperty(id, documentURL, documentType, documentName);
    }


    @GetMapping("/preview")
    public void getPropertyPreview(WebRequest webRequest, HttpServletResponse httpServletResponse) {

        String mimeType = webRequest.getParameter("type");
        String contentId = webRequest.getParameter("id");

        httpServletResponse.setContentType(mimeType);
        httpServletResponse.setHeader("Cache-Control", "private, max-age=5");
        httpServletResponse.setHeader("Pragma", "");
        ServletOutputStream outputStream = null;
        InputStream is = null;
        try {
            outputStream = httpServletResponse.getOutputStream();
            Session cmisSession = dmsFileSaver.cmisSession();
            if (cmisSession != null) {
                is = dmsFileSaver.getContentStream(contentId, cmisSession);
                int b;
                while ((b = is.read()) != -1) {
                    outputStream.write(b);
                }
            }
        } catch (IOException | CryptoException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.flush();
                    outputStream.close();
                }
                if (is != null)
                    is.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
