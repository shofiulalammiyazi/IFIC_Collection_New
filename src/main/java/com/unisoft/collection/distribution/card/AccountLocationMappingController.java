package com.unisoft.collection.distribution.card;
/*
Created by   Islam at 9/25/2019
*/

import com.unisoft.collection.settings.location.LocationEntity;
import com.unisoft.collection.settings.location.LocationRepository;
import com.unisoft.customerbasicinfo.CustomerBasicInfoEntity;
import com.unisoft.customerbasicinfo.CustomerBasicInfoEntityRepository;
import lombok.AllArgsConstructor;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping(value = "/collection/account/mapping/card")
public class AccountLocationMappingController {

    private AccountLocationTempMappingRepository accountLocationTempMappingRepository;

    private AccountLocationMappingRepository accountLocationMappingRepository;

    private CustomerBasicInfoEntityRepository customerBasicInfoEntityRepository;

    private LocationRepository locationRepository;

    @GetMapping(value = "/create")
    public String getAddPage() {
        return "/collection/distribution/card/addAccountMapping";
    }

    @PostMapping(value = "/create")
    public String saveAccountMapping(@RequestParam("file") MultipartFile multipartFile) {

        List<AccountLocationMapping> accountLocationMappings = new ArrayList<>();
        List<AccountLocationMapping> saveToDatabase = new ArrayList<>();
        List<AccountLocationTempMapping> notToSaveDatabase = new ArrayList<>();

        try {
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(multipartFile.getInputStream());
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
            int numOfRows = Math.max(xssfSheet.getPhysicalNumberOfRows(), 0);
            for (int i = 1; i < numOfRows; i++) {
                XSSFRow row = xssfSheet.getRow(i);

                if (row.getCell(0) != null && row.getCell(0).toString().length() > 1) {
                    AccountLocationMapping accountLocationMapping = new AccountLocationMapping();
                    accountLocationMapping.setCustomerId(row.getCell(0) != null ? row.getCell(0).toString() : "");
                    accountLocationMapping.setLocationName(row.getCell(1) != null ? row.getCell(1).toString() : "");
                    accountLocationMapping.setCustComAddress(row.getCell(2) != null ? row.getCell(2).toString() : "");
                    accountLocationMappings.add(accountLocationMapping);
                }

            }

            accountLocationMappings.forEach(accountLocationMapping -> {
                CustomerBasicInfoEntity customerBasicInfoEntity = customerBasicInfoEntityRepository.findByCustomerId(accountLocationMapping.getCustomerId());
                LocationEntity locationEntity = locationRepository.findByCity(accountLocationMapping.getLocationName());

                if (customerBasicInfoEntity != null && locationEntity != null) {
                    accountLocationMapping.setCustomer(customerBasicInfoEntity);
                    accountLocationMapping.setLocation(locationEntity);
                    saveToDatabase.add(accountLocationMapping);
                } else {
                    AccountLocationTempMapping accountLocationTempMapping = new AccountLocationTempMapping();
                    accountLocationTempMapping.setCustomerId(accountLocationMapping.getCustomerId());
                    accountLocationTempMapping.setLocationName(accountLocationMapping.getLocationName());
                    accountLocationTempMapping.setCustComAddress(accountLocationMapping.getCustComAddress());
                    notToSaveDatabase.add(accountLocationTempMapping);
                }
            });
            accountLocationMappingRepository.saveAll(saveToDatabase);
            accountLocationTempMappingRepository.saveAll(notToSaveDatabase);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return "redirect:/collection/account/mapping/card/list";
    }

    @GetMapping(value = "/list")
    public String getList(Model model) {
        model.addAttribute("accountList", accountLocationMappingRepository.findAll());
        return "/collection/distribution/card/accountMappingList";

    }

    @GetMapping(value = "/templist")
    public String getTempList(Model model) {
        model.addAttribute("accountList", accountLocationTempMappingRepository.findAll());
        return "/collection/distribution/card/tempaccountMappingList";

    }
}
