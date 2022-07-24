package com.unisoft.collection.distribution.loan;

import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@RequestMapping(value = "/distribution/migration/")
public class DataMigrationController {

    @GetMapping("create")
    public String addLoan(Model model) {
        return "collection/distribution/bblmigration/create";
    }

    @PostMapping("save")
    public void saveLoan(@RequestParam("file") MultipartFile multipartFile) {
        List<String> pinList = new ArrayList<>();

        try {
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(multipartFile.getInputStream());
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
            int numOfRows = Math.max(xssfSheet.getPhysicalNumberOfRows(), 0);
            for (int i = 1; i < numOfRows; i++) {
                XSSFRow row = xssfSheet.getRow(i);

                if (row.getCell(0) != null) {
                    String pin = row.getCell(1) != null ?
                            row.getCell(1).toString().contains(".")
                                    ? row.getCell(1).toString().substring(0, row.getCell(1).toString().indexOf('.'))
                                    : row.getCell(1).toString() : "";

                    pinList.add(pin);
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
