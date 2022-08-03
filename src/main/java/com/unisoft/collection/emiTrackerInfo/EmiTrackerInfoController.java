package com.unisoft.collection.emiTrackerInfo;


import com.unisoft.user.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/collection/emi-tracker-info")
public class EmiTrackerInfoController {

    private final EmiTrackerInfoRepository emiTrackerInfoRepository;

    @GetMapping(value = "/list")
    public String viewAll(Model model) {
        List<EmiTrackerInfoEntity> emiEntities = emiTrackerInfoRepository.findAll();
        model.addAttribute("emiAll", emiEntities);
        return "collection/emiTrackerInfo/list";
    }

    @GetMapping("/upload-excel")
    public String bulkUpload() {
        return "collection/emiTrackerInfo/upload";
    }

    @PostMapping("/upload-excel")
    public String mapReapExcelDatatoDB(@RequestParam("file") MultipartFile reapExcelDataFile) throws IOException {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {
            List<EmiTrackerInfoEntity> emiTrackerInfoEntities = new ArrayList<EmiTrackerInfoEntity>();
            XSSFWorkbook workbook = new XSSFWorkbook(reapExcelDataFile.getInputStream());
            XSSFSheet worksheet = workbook.getSheetAt(0);

            for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
                EmiTrackerInfoEntity emi = new EmiTrackerInfoEntity();

                XSSFRow row = worksheet.getRow(i);
                emi.setContractId(row.getCell(0) != null ? row.getCell(0).toString() : null);

                emi.setTotalSettlementAmount(row.getCell(1) != null ? row.getCell(1).getNumericCellValue() : null);
                emi.setTenor(row.getCell(2) != null ? row.getCell(2).getNumericCellValue() : null);
                emi.setCreatedBy(user.getUsername());
                emi.setCreatedDate(new Date());

                emiTrackerInfoEntities.add(emi);
            }

            emiTrackerInfoRepository.saveAll(emiTrackerInfoEntities);

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return "redirect:/collection/emi-tracker-info/list";
    }
}
