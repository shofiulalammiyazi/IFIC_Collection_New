package com.csinfotechbd.collection.samd.setup.agencySamd;

import com.csinfotechbd.cryptography.CryptoException;
import com.csinfotechbd.dms.DmsDocumentProperty;
import com.csinfotechbd.dms.DmsFileSaver;
import lombok.RequiredArgsConstructor;
import org.apache.chemistry.opencmis.client.api.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/collection/samd/agency/")
public class AgencySamdController {

    private final AgencySamdService agencySamdService;
    private final AgencySamdFileService agencySamdFileService;
    private final DmsFileSaver dmsFileSaver;

    @GetMapping(value = "list")
    public String viewAll(Model model) {
        model.addAttribute("agencyList", agencySamdService.getAll());
        return "samd/setup/agencySamd/agencySamd";
    }

    @GetMapping("create")
    public String addpage(Model model) {
        model.addAttribute("entity", new AgencySamdEntity());
        return "samd/setup/agencySamd/create";
    }

    @PostMapping(value = "create")
    public String addnew(Model model, AgencySamdEntity entity) {
        String output = agencySamdService.save(entity);
        agencySamdFileService.save(entity);
        switch (output) {
            case "1":
                return "redirect:/collection/samd/agency/list";
            default:
                model.addAttribute("error", output);
        }
        model.addAttribute("entity", entity);
        return "redirect:/collection/samd/agency/list";
    }

    @GetMapping(value = "edit")
    public String editPage(@RequestParam(value = "id") Long id, Model model) {
        model.addAttribute("entity", agencySamdService.getById(id));
        return "samd/setup/agencySamd/create";
    }

    @GetMapping(value = "view")
    public String viewPage(@RequestParam(value = "id") Long id, Model model) {
        AgencySamdEntityDto agencySamdEntityDto = agencySamdService.findAgencySamdEntityById(id);
        model.addAttribute("agency", agencySamdEntityDto);
        return "samd/setup/agencySamd/view";
    }

    @GetMapping("/view-file")
    @ResponseBody
    public DmsDocumentProperty getPropertyValue(
            @RequestParam(value = "id") String id,
            @RequestParam(value = "name") String documentName,
            @RequestParam(value = "type") String documentType){
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
