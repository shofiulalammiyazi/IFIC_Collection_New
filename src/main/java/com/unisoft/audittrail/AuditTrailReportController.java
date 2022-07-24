package com.unisoft.audittrail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/report/auditTrail/")
public class AuditTrailReportController {

    @Autowired
    private AuditTrailService auditTrailService;


    @PostMapping(value = "list")
    public String auditraildata(@RequestParam(value = "start_date") String startDate, @RequestParam(value = "end_date") String endDate, Model model) {

        try {
            Date start = new SimpleDateFormat("MM/dd/yyyy").parse(startDate);
            Date endD = new SimpleDateFormat("MM/dd/yyyy").parse(endDate);

            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss.SSSSSS");

            String startDateString = formatter.format(start);
            String endDateString = formatter.format(endD);

            Date startDate_date = formatter.parse(startDateString);
            Date endDate_date = formatter.parse(endDateString);

            if (startDateString.equals(endDateString)) {
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.HOUR_OF_DAY, 23);
                cal.set(Calendar.MINUTE, 59);
                cal.set(Calendar.SECOND, 0);
                cal.set(Calendar.MILLISECOND, 0);
                endDate_date = cal.getTime();
            }

            model.addAttribute("audittrail", auditTrailService.auditTrailList(startDate_date, endDate_date));


        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
        return "collection/report/audittrailreport";
    }

    @GetMapping(value = "list")
    public String viewPage(Model model) {
        List<AuditTrail> list = new ArrayList<>();
        model.addAttribute("audittrail", list);
        return "collection/report/audittrailreport";
    }
}
