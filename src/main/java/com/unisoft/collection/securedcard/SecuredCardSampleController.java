package com.unisoft.collection.securedcard;

import com.google.gson.Gson;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/collection/secured/sample/")
public class SecuredCardSampleController {

    @Autowired
    private SecuredCardSampleRepository securedCardSampleRepository;

    @GetMapping("create")
    public String addLoan(Model model) {
        return "collection/securedcard/create";
    }

    @PostMapping("save")
    public String saveLoan(@RequestParam("file") MultipartFile multipartFile) {
        try {
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(multipartFile.getInputStream());
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
            int numOfRows = Math.max(xssfSheet.getPhysicalNumberOfRows(), 0);

            for(int i=1; i<numOfRows; i++){
                XSSFRow row = xssfSheet.getRow(i);

                if(row.getCell(0) != null && row.getCell(0).toString().length() > 1){
                    String securedCard = row.getCell(0) != null ? row.getCell(0).toString() : "";
                    String accountNo = row.getCell(1) != null ? row.getCell(1).toString() : "";
                    SecuredCardSample securedCardSample = new SecuredCardSample(securedCard, accountNo, "YES");

                    SecuredCardSample firstBySecuredCard = securedCardSampleRepository.findFirstBySecuredCard(securedCard);
                    if(firstBySecuredCard == null){
                        securedCardSampleRepository.save(securedCardSample);
                    }
                }

            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return "redirect:/collection/secured/sample/list";
    }

    @GetMapping("list")
    public String getSecuredCardList(Model model) {
        Gson gson = new Gson();
        List<SecuredCardSample> securedCardSamples = securedCardSampleRepository.findAll();
        model.addAttribute("securedCardSamples", gson.toJson(securedCardSamples));
        return "collection/securedcard/loan";
    }

    @PostMapping("update")
    @ResponseBody
    public String updateSecuredCard(@Valid @RequestBody SecuredCardSamplePayload securedCardSamplePayload){
        Gson gson = new Gson();
        for(String s: securedCardSamplePayload.getList()){
            SecuredCardSample securedCardSample = securedCardSampleRepository.findFirstBySecuredCard(s);
            if(securedCardSample != null){
                securedCardSample.setSecureCheck("NO");
                securedCardSampleRepository.save(securedCardSample);
            }

        }
        List<SecuredCardSample> securedCardSamples = securedCardSampleRepository.findAll();
        return gson.toJson(securedCardSamples);
    }

    @PostMapping("update/add")
    @ResponseBody
    public String updateSecuredCardAdd(@Valid @RequestBody SecuredCardSamplePayload securedCardSamplePayload){
        Gson gson = new Gson();
        for(String s: securedCardSamplePayload.getList()){
            SecuredCardSample securedCardSample = securedCardSampleRepository.findFirstBySecuredCard(s);
            if(securedCardSample != null){
                securedCardSample.setSecureCheck("YES");
                securedCardSampleRepository.save(securedCardSample);
            }

        }
        List<SecuredCardSample> securedCardSamples = securedCardSampleRepository.findAll();
        return gson.toJson(securedCardSamples);
    }

}
