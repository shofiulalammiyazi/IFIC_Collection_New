package com.unisoft.reports.retail.loan.clReportMonthBasis;

import lombok.AllArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@Controller
@AllArgsConstructor
@RequestMapping("/collection/report/retail/loan/month-basis-cl")
public class MonthBasisCLReportController {

    private MonthBasisCLReportService monthBasisCLReportService;

    @GetMapping("/view")
    public String view(Model model) {
        model.addAttribute("reportTitle", "Month on Month Basis CL Report");
        return "collection/reports/retail/loan/clReportMonthBasis/view";
    }

    @ResponseBody
    @GetMapping("/search")
    public MonthBasisCLReportViewModel search(@RequestParam String month) {
        try {
            MonthBasisCLReportViewModel data = monthBasisCLReportService.searchData(month);
            return data;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new MonthBasisCLReportViewModel();
    }

    @ResponseBody
    @GetMapping("/download")
    public ResponseEntity<InputStreamResource> download() {
        Resource resource = new ClassPathResource("/static/assets/css/404.css");
        File file = null;
        try {
            file = resource.getFile().getAbsoluteFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        InputStreamResource downloadedData = null;
        try {
            downloadedData = new InputStreamResource(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                .body(downloadedData);
    }
}
