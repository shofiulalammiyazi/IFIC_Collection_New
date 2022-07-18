package com.csinfotechbd.reports.retail.loan.vipListReport;

import com.csinfotechbd.collection.settings.vipStatus.VipStatusService;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/collection/report/retail/loan/vip-list")
public class VipListReportController {

    private final VipListReportService vipListReportService;
    private final VipStatusService vipStatusService;

    @GetMapping("/view")
    public String view(Model model) {
        List<String> vipStatusList = vipStatusService.getVipStatusNameList();
        model.addAttribute("reportTitle", "VIP List");
        model.addAttribute("vipStatusList", vipStatusList);
        return "collection/reports/retail/loan/vipListReport/view";
    }

    @GetMapping("/report")
    @ResponseBody
    public JSONObject getVipList(String vipStatus) {
        JSONObject data = new JSONObject();
        List<VipListReportDto> vipList = vipListReportService.getVipListByVipStatus(vipStatus);
        data.put("data", vipList);
        data.put("recordsTotal", vipList.size());
        data.put("recordsFiltered", vipList.size());

        return data;
    }
}
