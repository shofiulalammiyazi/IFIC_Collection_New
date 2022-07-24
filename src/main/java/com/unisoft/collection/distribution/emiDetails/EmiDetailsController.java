package com.unisoft.collection.distribution.emiDetails;

import com.unisoft.user.UserPrincipal;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping(value = "/collection/distribution/emiDetails/")
public class EmiDetailsController {

    @Autowired
    EmiDetailsService emiDetailsService;

    @GetMapping("create")
    public String addNewExcel(Model model) {
        return "collection/distribution/emiDetails/create";
    }

    @GetMapping("list")
    public String viewDetails(Model model) {
        List<EmiDetails> list = emiDetailsService.getShownEmiDetails();
        model.addAttribute("emiDetailsList", list);
        return "collection/distribution/emiDetails/emiDetails";
    }

    @GetMapping("search")
    public Map searchByAcc(@RequestParam("accountNo") String accountNo) {
        List<EmiDetails> emiDetailsList = new ArrayList<>();
        emiDetailsList.addAll(emiDetailsService.findByAcc(accountNo));
        Map<String, Object> map = new HashMap<>();
        map.put("emiDetails", emiDetailsList);
        return map;
    }

    @PostMapping("save")
    public String save(@RequestParam("file") MultipartFile multipartFile) {
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try {
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(multipartFile.getInputStream());
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
            List<EmiDetails> emiDetailsList = new ArrayList<>();
            int numOfRows = Math.max(xssfSheet.getPhysicalNumberOfRows(), 0);
            for (int i = 1; i < numOfRows; i++) {
                XSSFRow row = xssfSheet.getRow(i);
                if (row.getCell(0) != null && row.getCell(0).toString().length() > 1) {
                    EmiDetails emiDetails = new EmiDetails();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
                    try {
                        emiDetails.setStartDate(simpleDateFormat.parse(row.getCell(3) != null ? row.getCell(3).toString() : "1-Jan-2020"));
                        emiDetails.setEndDate(simpleDateFormat.parse(row.getCell(4) != null ? row.getCell(4).toString() : "1-Jan-2020"));
                    } catch (ParseException e) {
                        System.out.println(e.getMessage());
                    }
                    emiDetails.setCardNo(row.getCell(0) != null ? row.getCell(0).toString() : "default");
                    emiDetails.setNumOfInst(row.getCell(1) != null ? row.getCell(1).toString() : "default");
                    emiDetails.setEmiSize(row.getCell(2) != null ? row.getCell(2).toString() : "default");
                    emiDetails.setInitiator(row.getCell(5) != null ? row.getCell(5).toString() : "default");
                    emiDetails.setApproval(row.getCell(6) != null ? row.getCell(6).toString() : "default");
                    emiDetails.setCompleted(row.getCell(7) != null ? row.getCell(7).toString() : "default");
                    emiDetails.setRemark(row.getCell(8) != null ? row.getCell(8).toString() : "default");

                    emiDetails.setCreatedBy(userPrincipal.getUsername());
                    emiDetails.setCreatedDate(new Date());

                    emiDetailsList.add(emiDetails);
                }
                for (EmiDetails emiDetails : emiDetailsList) {
                    emiDetailsService.save(emiDetails);
                }
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return "redirect:/distribution/emiDetails/list";
    }
}
