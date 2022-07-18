package com.csinfotechbd.legal.report.datasheets.fileFollowUpBeforeFillingSuitReport;


import com.csinfotechbd.utillity.PageUtils;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/legal/report/file-follow-up-before-filling-suit")
public class FileFollowUpBeforeFillingSuitReportController {

    private final FileFollowUpBeforeFillingSuitReportService service;
    private final PageUtils pageUtils;

    @GetMapping("/view")
    public String FileFollowUpBeforeFillingSuitReportView(Model model){
        model.addAttribute("reportTitle", "File Follow up Before Filling Suit");
        return "legal/report/fileFollowUpBeforeFillingSuitReport/view";
    }

    @GetMapping("/report")
    @ResponseBody
    public JSONObject getReport(){
        JSONObject jsonObject = new JSONObject();

//        TODO: Implement pagination whenever possible
        Pageable pageable = PageRequest.of(0, 4000, Sort.by(Sort.Direction.DESC, "id"));

        List<FileFollowUpBeforeFillingSuitReportDto> reportData = service.getReport(pageable);

        jsonObject.put("data", reportData);

        return jsonObject;

    }
}
