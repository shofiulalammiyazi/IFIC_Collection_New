package com.unisoft.dms;

import com.unisoft.cryptography.CryptoException;
import com.unisoft.customerbasicinfo.CustomerBasicInfoEntity;
import com.unisoft.customerbasicinfo.CustomerBasicInfoEntityRepository;
import com.unisoft.user.User;
import com.unisoft.user.UserPrincipal;
import com.unisoft.user.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.chemistry.opencmis.client.api.Session;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/collection/dms/document")
public class DmsDocumentController {

    private final DmsFileSaver dmsFileSaver;

    private final DmsDocumentRepository dmsDocumentRepository;

    private final CustomerBasicInfoEntityRepository customerBasicInfoEntityRepository;

    private final UserService userService;

    /**
     * Modified By    on 29 April 2021
     *
     * @param file
     * @param comments
     * @param glat
     * @param glong
     * @param customerId
     * @return
     */
    @PostMapping("/upload")
    @ResponseBody
    public List<DmsDocumentUpload> saveFileToDms(
            @RequestParam(value = "file") MultipartFile file,
            @RequestParam(value = "comments", required = false) String comments,
            @RequestParam(value = "glat", required = false) String glat,
            @RequestParam(value = "glong", required = false) String glong,
            @RequestParam(value = "id") String customerId) {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<CustomerBasicInfoEntity> customerBasicInfoEntity = customerBasicInfoEntityRepository.findById(new Long(customerId));
        try {
            Session cmisSession = dmsFileSaver.cmisSession();
            String type = file.getContentType();
            String filePath = "documents/" + customerId;
            String id = dmsFileSaver.saveFileToDmsFilePath(file, filePath, file.getOriginalFilename(), cmisSession);
            if (id != null) {
                DmsDocumentUpload dmsDocumentUpload = new DmsDocumentUpload(comments, file.getOriginalFilename(), id, type, customerBasicInfoEntity.get());
                dmsDocumentUpload.setCreatedDate(new Date());
                dmsDocumentUpload.setCreatedBy(user.getUsername());
                dmsDocumentRepository.save(dmsDocumentUpload);
            }
        } catch (IOException | CryptoException e) {
            System.out.println(e.getMessage());
        }
        List<DmsDocumentUpload> dmsDocumentUploadList = dmsDocumentRepository.findByCustomerBasicInfoEntity(customerBasicInfoEntity.get());
        for (DmsDocumentUpload d : dmsDocumentUploadList) {
            User user1 = userService.getUserByUsername(d.getCreatedBy());
            d.setCreatorName(user1.getFirstName() + " " + user1.getLastName());
            d.setPin(user1.getUsername());
        }
        return dmsDocumentUploadList;
    }

    @GetMapping("/list")
    @ResponseBody
    public List<DmsDocumentUpload> getFileFromDms(@RequestParam(value = "id") String id) {
        Optional<CustomerBasicInfoEntity> customerBasicInfoEntity = customerBasicInfoEntityRepository.findById(new Long(id));
        if (customerBasicInfoEntity.isPresent()) {
            List<DmsDocumentUpload> dmsDocumentUploads = dmsDocumentRepository.findByCustomerBasicInfoEntity(customerBasicInfoEntity.get());
            for (DmsDocumentUpload d : dmsDocumentUploads) {
                User user = userService.getUserByUsername(d.getCreatedBy());
                d.setCreatorName(user.getFirstName() + " " + user.getLastName());
                d.setPin(user.getUsername());
            }
            return dmsDocumentUploads;
        } else return new ArrayList<>();
    }

    @GetMapping("/view")
    @ResponseBody
    public DmsDocumentProperty getPropertyValue(
            @RequestParam(value = "id") String id,
            @RequestParam(value = "name") String documentName,
            @RequestParam(value = "type") String documentType) {

        String documentURL = dmsFileSaver.getDocumentURL(id);
        return new DmsDocumentProperty(id, documentURL, documentType, documentName);
    }

    /**
     * Modified by    on 28 April 2021
     *
     * @param webRequest
     * @param httpServletResponse
     * @return
     */
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
