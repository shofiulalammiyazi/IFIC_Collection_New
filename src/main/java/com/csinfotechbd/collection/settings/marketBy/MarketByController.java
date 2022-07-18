package com.csinfotechbd.collection.settings.marketBy;

import com.csinfotechbd.collection.emi.EmiEntity;
import com.csinfotechbd.user.UserPrincipal;
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
@RequestMapping("/collection/marketby/excel")
public class MarketByController {

    private final MarketByRepository repository;

    @GetMapping(value = "/list")
    public String viewAll(Model model) {
        List<MarketByEntity> marketByEntities = repository.findAll();
        model.addAttribute("marketByAll", marketByEntities);

        return "collection/settings/marketBy/list";
    }

    @GetMapping("/upload-excel")
    public String bulkUpload() {
        return "collection/settings/marketBy/upload";
    }

    @PostMapping("/upload-excel")
    public String mapReapExcelDatatoDB(@RequestParam("file") MultipartFile reapExcelDataFile) throws IOException {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {
            List<MarketByEntity> marketByEntities = new ArrayList<MarketByEntity>();
            XSSFWorkbook workbook = new XSSFWorkbook(reapExcelDataFile.getInputStream());
            XSSFSheet worksheet = workbook.getSheetAt(0);

            for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
                MarketByEntity market = new MarketByEntity();

                XSSFRow row = worksheet.getRow(i);


                market.setContractId(row.getCell(0) != null ? row.getCell(0).toString() : null);
                market.setMarketBy(row.getCell(1) != null ? row.getCell(1).toString() : null);
                market.setCreatedBy(user.getUsername());
                market.setCreatedDate(new Date());

                marketByEntities.add(market);
            }

            repository.saveAll(marketByEntities);

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return "redirect:/collection/marketby/excel/list";
    }

}
