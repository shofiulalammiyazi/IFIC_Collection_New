package com.unisoft.reports.activity;

import com.unisoft.collection.settings.employee.EmployeeDao;
import com.unisoft.collection.settings.employee.EmployeeInfoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@RequestMapping("/activity/report/")
public class ActivityReportController {

    @Autowired
    private EmployeeDao employeeDao;


    @Autowired
    private activityDao1 activityDao1;

    @GetMapping("/create")
    public String create(Model model) {
        List<EmployeeInfoEntity> supervisorList = employeeDao.getSuprvisorList();
        model.addAttribute("supervisorList", supervisorList);
        return "collection/reports/activity/create";
    }


    @PostMapping("activityreport")
    @ResponseBody
    public List<ActivityReport> list(
            @RequestParam(value = "startDate", required = true) String startDate,
            @RequestParam(value = "endDate", required = true) String endDate) {


        //String sql = "SELECT LOAN_PTP_STATUS,ID FROM LOAN_PTP";


//      String sql="SELECT a.CID, b.ACCOUNT_NAME, b.ACCOUNT_NO," +
//              "       SUM (a.PTP)              PTP," +
//              "       SUM (a.DAIRY_NOTES)      DAIRY_NOTES," +
//              "       SUM (a.DAILY_NOTES)      DAILY_NOTES," +
//              "       SUM (a.HOT_NOTES)        HOT_NOTES," +
//              "       SUM (a.FOLLOW_UP)        FOLLOW_UP" +
//              "  FROM (SELECT a.ID CID, COUNT (b.ID)     PTP," +
//              "               0                DAIRY_NOTES," +
//              "               0                DAILY_NOTES," +
//              "               0                HOT_NOTES," +
//              "               0                FOLLOW_UP" +
//              "          FROM CUSTOMER_BASIC_INFO_ENTITY a, LOAN_PTP b" +
//              "         WHERE a.ID = b.LOANPTP_ID " +
//              "         " +
//              "         AND b.CREATED_DATE BETWEEN TO_DATE ('"+startDate+"', 'dd/mm/yyyy')" +
//              "                              AND TO_DATE ('"+endDate+"', 'dd/mm/yyyy') " +
//              "                              " +
//              "                              GROUP BY a.ID" +
//              "        UNION" +
//              "        SELECT a.ID CID, 0                PTP," +
//              "               COUNT (b.ID)     DAIRY_NOTES," +
//              "               0                DAILY_NOTES," +
//              "               0                HOT_NOTES," +
//              "               0                FOLLOW_UP" +
//              "          FROM CUSTOMER_BASIC_INFO_ENTITY a, DAIRY_NOTES_LOAN b" +
//              "         WHERE a.ID = b.DAITYNOTE_ID AND b.CREATED_DATE BETWEEN TO_DATE ('"+startDate+"', 'dd/mm/yyyy')" +
//              "                              AND TO_DATE ('"+endDate+"', 'dd/mm/yyyy') GROUP BY a.ID" +
//              "        UNION" +
//              "        SELECT a.ID CID, 0                PTP," +
//              "               0                DAIRY_NOTES," +
//              "               COUNT (b.ID)     DAILY_NOTES," +
//              "               0                HOT_NOTES," +
//              "               0                FOLLOW_UP" +
//              "          FROM CUSTOMER_BASIC_INFO_ENTITY a, DAILY_NOTE_ENTITY b" +
//              "         WHERE a.ID = b.DAILYNOTE_ID AND b.CREATED_DATE BETWEEN TO_DATE ('"+startDate+"', 'dd/mm/yyyy')" +
//              "                              AND TO_DATE ('"+endDate+"', 'dd/mm/yyyy') GROUP BY a.ID" +
//              "        UNION" +
//              "        SELECT a.ID CID, 0                PTP," +
//              "               0                DAIRY_NOTES," +
//              "               0                DAILY_NOTES," +
//              "               COUNT (b.ID)     HOT_NOTES," +
//              "               0                FOLLOW_UP" +
//              "          FROM CUSTOMER_BASIC_INFO_ENTITY a, HOT_NOTE_ENTITY b" +
//              "         WHERE a.ID = b.HOTNOTE_ID AND b.CREATED_DATE BETWEEN TO_DATE ('"+startDate+"', 'dd/mm/yyyy')" +
//              "                              AND TO_DATE ('"+endDate+"', 'dd/mm/yyyy') GROUP BY a.ID" +
//              "        UNION" +
//              "        SELECT a.ID CID, 0                PTP," +
//              "               0                DAIRY_NOTES," +
//              "               0                DAILY_NOTES," +
//              "               0                HOT_NOTES," +
//              "               COUNT (b.ID)     FOLLOW_UP" +
//              "          FROM CUSTOMER_BASIC_INFO_ENTITY a, FOLLOW_UP_ENTITY b" +
//              "         WHERE a.ID = b.FOLLOWUP_ID AND b.CREATED_DATE BETWEEN TO_DATE ('"+startDate+"', 'dd/mm/yyyy')" +
//              "                              AND TO_DATE ('"+endDate+"', 'dd/mm/yyyy') GROUP BY a.ID" +
//              "        ) a,  LOAN_ACCOUNT_BASIC_INFO b " +
//              "        WHERE a.CID=b.CUSTOMER_ID" +
//              "        GROUP BY a.CID, b.ACCOUNT_NAME, b.ACCOUNT_NO";
//
//
//
//
//        String cardsql="SELECT a.CID, b.ACCOUNT_NAME, b.ACCOUNT_NO, " +
//                "       SUM (a.PTP)              PTP," +
//                "       SUM (a.DAIRY_NOTES)      DAIRY_NOTES," +
//                "       SUM (a.DAILY_NOTES)      DAILY_NOTES," +
//                "       SUM (a.HOT_NOTES)        HOT_NOTES," +
//                "       SUM (a.FOLLOW_UP)        FOLLOW_UP" +
//                "  FROM (SELECT a.ID CID, COUNT (b.ID)     PTP," +
//                "               0                DAIRY_NOTES," +
//                "               0                DAILY_NOTES," +
//                "               0                HOT_NOTES," +
//                "               0                FOLLOW_UP" +
//                "          FROM CUSTOMER_BASIC_INFO_ENTITY a, CARD_PTP b" +
//                "         WHERE a.ID = b.CARDPTP_ID " +
//                "         " +
//                "         AND b.CREATED_DATE BETWEEN TO_DATE ('"+startDate+"', 'dd/mm/yyyy')" +
//                "                              AND TO_DATE ('"+endDate+"', 'dd/mm/yyyy') " +
//                "                              " +
//                "                              GROUP BY a.ID" +
//                "        UNION" +
//                "        SELECT a.ID CID, 0                PTP," +
//                "               COUNT (b.ID)     DAIRY_NOTES," +
//                "               0                DAILY_NOTES," +
//                "               0                HOT_NOTES," +
//                "               0                FOLLOW_UP" +
//                "          FROM CUSTOMER_BASIC_INFO_ENTITY a, DAIRY_NOTES b" +
//                "         WHERE a.ID = b.DAIRY_ID AND b.CREATED_DATE BETWEEN TO_DATE ('"+startDate+"', 'dd/mm/yyyy')" +
//                "                              AND TO_DATE ('"+endDate+"', 'dd/mm/yyyy') GROUP BY a.ID" +
//                "        UNION" +
//                "        SELECT a.ID CID, 0                PTP," +
//                "               0                DAIRY_NOTES," +
//                "               COUNT (b.ID)     DAILY_NOTES," +
//                "               0                HOT_NOTES," +
//                "               0                FOLLOW_UP" +
//                "          FROM CUSTOMER_BASIC_INFO_ENTITY a, DAILY_NOTES_CARD b" +
//                "         WHERE a.ID = b.CARD_DAILYNOTE_ID AND b.CREATED_DATE BETWEEN TO_DATE ('"+startDate+"', 'dd/mm/yyyy')" +
//                "                              AND TO_DATE ('"+endDate+"', 'dd/mm/yyyy') GROUP BY a.ID" +
//                "        UNION" +
//                "        SELECT a.ID CID, 0                PTP," +
//                "               0                DAIRY_NOTES," +
//                "               0                DAILY_NOTES," +
//                "               COUNT (b.ID)     HOT_NOTES," +
//                "               0                FOLLOW_UP" +
//                "          FROM CUSTOMER_BASIC_INFO_ENTITY a, CARD_HOT_NOTES b" +
//                "         WHERE a.ID = b.CARDHOTNOTE_ID AND b.CREATED_DATE BETWEEN TO_DATE ('"+startDate+"', 'dd/mm/yyyy')" +
//                "                              AND TO_DATE ('"+endDate+"', 'dd/mm/yyyy') GROUP BY a.ID" +
//                "        UNION" +
//                "        SELECT a.ID CID, 0                PTP," +
//                "               0                DAIRY_NOTES," +
//                "               0                DAILY_NOTES," +
//                "               0                HOT_NOTES," +
//                "               COUNT (b.ID)     FOLLOW_UP" +
//                "          FROM CUSTOMER_BASIC_INFO_ENTITY a, CARD_FOLLOW_UP b" +
//                "         WHERE a.ID = b.CARDFOLLOWUP_ID AND b.CREATED_DATE BETWEEN TO_DATE ('"+startDate+"', 'dd/mm/yyyy')" +
//                "                              AND TO_DATE ('"+endDate+"', 'dd/mm/yyyy') GROUP BY a.ID" +
//                "        ) a,  LOAN_ACCOUNT_BASIC_INFO b " +
//                "        WHERE a.CID=b.CUSTOMER_ID" +
//                "        GROUP BY a.CID, b.ACCOUNT_NAME, b.ACCOUNT_NO";
//
        Map<String, String> stringMap = new HashMap<>();
        stringMap.put("startDate", startDate);
        stringMap.put("endDate", endDate);

        String sql = "SELECT a.CID, b.ACCOUNT_NAME, b.ACCOUNT_NO," +
                "       SUM (a.PTP)              PTP," +
                "       SUM (a.DAIRY_NOTES)      DAIRY_NOTES," +
                "       SUM (a.DAILY_NOTES)      DAILY_NOTES," +
                "       SUM (a.HOT_NOTES)        HOT_NOTES," +
                "       SUM (a.FOLLOW_UP)        FOLLOW_UP" +
                "  FROM (SELECT a.ID CID, COUNT (b.ID)     PTP," +
                "               0                DAIRY_NOTES," +
                "               0                DAILY_NOTES," +
                "               0                HOT_NOTES," +
                "               0                FOLLOW_UP" +
                "          FROM CUSTOMER_BASIC_INFO_ENTITY a, LOAN_PTP b" +
                "         WHERE a.ID = b.LOANPTP_ID " +
                "         " +
                "         AND b.CREATED_DATE BETWEEN TO_DATE (:startDate, 'dd/mm/yyyy')" +
                "                              AND TO_DATE (:endDate, 'dd/mm/yyyy') " +
                "                              " +
                "                              GROUP BY a.ID" +
                "        UNION" +
                "        SELECT a.ID CID, 0                PTP," +
                "               COUNT (b.ID)     DAIRY_NOTES," +
                "               0                DAILY_NOTES," +
                "               0                HOT_NOTES," +
                "               0                FOLLOW_UP" +
                "          FROM CUSTOMER_BASIC_INFO_ENTITY a, DAIRY_NOTES_LOAN b" +
                "         WHERE a.ID = b.DAITYNOTE_ID AND b.CREATED_DATE BETWEEN TO_DATE (:startDate, 'dd/mm/yyyy')" +
                "                              AND TO_DATE (:endDate, 'dd/mm/yyyy') GROUP BY a.ID" +
                "        UNION" +
                "        SELECT a.ID CID, 0                PTP," +
                "               0                DAIRY_NOTES," +
                "               COUNT (b.ID)     DAILY_NOTES," +
                "               0                HOT_NOTES," +
                "               0                FOLLOW_UP" +
                "          FROM CUSTOMER_BASIC_INFO_ENTITY a, DAILY_NOTE_ENTITY b" +
                "         WHERE a.ID = b.DAILYNOTE_ID AND b.CREATED_DATE BETWEEN TO_DATE (:startDate, 'dd/mm/yyyy')" +
                "                              AND TO_DATE (:endDate, 'dd/mm/yyyy') GROUP BY a.ID" +
                "        UNION" +
                "        SELECT a.ID CID, 0                PTP," +
                "               0                DAIRY_NOTES," +
                "               0                DAILY_NOTES," +
                "               COUNT (b.ID)     HOT_NOTES," +
                "               0                FOLLOW_UP" +
                "          FROM CUSTOMER_BASIC_INFO_ENTITY a, HOT_NOTE_ENTITY b" +
                "         WHERE a.ID = b.HOTNOTE_ID AND b.CREATED_DATE BETWEEN TO_DATE (:startDate, 'dd/mm/yyyy')" +
                "                              AND TO_DATE (:endDate, 'dd/mm/yyyy') GROUP BY a.ID" +
                "        UNION" +
                "        SELECT a.ID CID, 0                PTP," +
                "               0                DAIRY_NOTES," +
                "               0                DAILY_NOTES," +
                "               0                HOT_NOTES," +
                "               COUNT (b.ID)     FOLLOW_UP" +
                "          FROM CUSTOMER_BASIC_INFO_ENTITY a, FOLLOW_UP_ENTITY b" +
                "         WHERE a.ID = b.FOLLOWUP_ID AND b.CREATED_DATE BETWEEN TO_DATE (:startDate, 'dd/mm/yyyy')" +
                "                              AND TO_DATE (:endDate, 'dd/mm/yyyy') GROUP BY a.ID" +
                "        ) a,  LOAN_ACCOUNT_BASIC_INFO b " +
                "        WHERE a.CID=b.CUSTOMER_ID" +
                "        GROUP BY a.CID, b.ACCOUNT_NAME, b.ACCOUNT_NO";


        String cardsql = "SELECT a.CID, b.ACCOUNT_NAME, b.ACCOUNT_NO, " +
                "       SUM (a.PTP)              PTP," +
                "       SUM (a.DAIRY_NOTES)      DAIRY_NOTES," +
                "       SUM (a.DAILY_NOTES)      DAILY_NOTES," +
                "       SUM (a.HOT_NOTES)        HOT_NOTES," +
                "       SUM (a.FOLLOW_UP)        FOLLOW_UP" +
                "  FROM (SELECT a.ID CID, COUNT (b.ID)     PTP," +
                "               0                DAIRY_NOTES," +
                "               0                DAILY_NOTES," +
                "               0                HOT_NOTES," +
                "               0                FOLLOW_UP" +
                "          FROM CUSTOMER_BASIC_INFO_ENTITY a, CARD_PTP b" +
                "         WHERE a.ID = b.CARDPTP_ID " +
                "         " +
                "         AND b.CREATED_DATE BETWEEN TO_DATE (:startDate, 'dd/mm/yyyy')" +
                "                              AND TO_DATE (:endDate, 'dd/mm/yyyy') " +
                "                              " +
                "                              GROUP BY a.ID" +
                "        UNION" +
                "        SELECT a.ID CID, 0                PTP," +
                "               COUNT (b.ID)     DAIRY_NOTES," +
                "               0                DAILY_NOTES," +
                "               0                HOT_NOTES," +
                "               0                FOLLOW_UP" +
                "          FROM CUSTOMER_BASIC_INFO_ENTITY a, DAIRY_NOTES b" +
                "         WHERE a.ID = b.DAIRY_ID AND b.CREATED_DATE BETWEEN TO_DATE (:startDate, 'dd/mm/yyyy')" +
                "                              AND TO_DATE (:endDate, 'dd/mm/yyyy') GROUP BY a.ID" +
                "        UNION" +
                "        SELECT a.ID CID, 0                PTP," +
                "               0                DAIRY_NOTES," +
                "               COUNT (b.ID)     DAILY_NOTES," +
                "               0                HOT_NOTES," +
                "               0                FOLLOW_UP" +
                "          FROM CUSTOMER_BASIC_INFO_ENTITY a, DAILY_NOTES_CARD b" +
                "         WHERE a.ID = b.CARD_DAILYNOTE_ID AND b.CREATED_DATE BETWEEN TO_DATE (:startDate, 'dd/mm/yyyy')" +
                "                              AND TO_DATE (:endDate, 'dd/mm/yyyy') GROUP BY a.ID" +
                "        UNION" +
                "        SELECT a.ID CID, 0                PTP," +
                "               0                DAIRY_NOTES," +
                "               0                DAILY_NOTES," +
                "               COUNT (b.ID)     HOT_NOTES," +
                "               0                FOLLOW_UP" +
                "          FROM CUSTOMER_BASIC_INFO_ENTITY a, CARD_HOT_NOTES b" +
                "         WHERE a.ID = b.CARDHOTNOTE_ID AND b.CREATED_DATE BETWEEN TO_DATE (:startDate, 'dd/mm/yyyy')" +
                "                              AND TO_DATE (:endDate, 'dd/mm/yyyy') GROUP BY a.ID" +
                "        UNION" +
                "        SELECT a.ID CID, 0                PTP," +
                "               0                DAIRY_NOTES," +
                "               0                DAILY_NOTES," +
                "               0                HOT_NOTES," +
                "               COUNT (b.ID)     FOLLOW_UP" +
                "          FROM CUSTOMER_BASIC_INFO_ENTITY a, CARD_FOLLOW_UP b" +
                "         WHERE a.ID = b.CARDFOLLOWUP_ID AND b.CREATED_DATE BETWEEN TO_DATE (:startDate, 'dd/mm/yyyy')" +
                "                              AND TO_DATE (:endDate, 'dd/mm/yyyy') GROUP BY a.ID" +
                "        ) a,  LOAN_ACCOUNT_BASIC_INFO b " +
                "        WHERE a.CID=b.CUSTOMER_ID" +
                "        GROUP BY a.CID, b.ACCOUNT_NAME, b.ACCOUNT_NO";

        System.err.println("LOAN SQL : " + sql);
        System.err.println("CARD SQL : " + cardsql);


        //String cardsql = "";
        List<ActivityReport> loanactivylist = activityDao1.getData(sql, stringMap);
        List<ActivityReport> cardactivylist = activityDao1.getcarddata(cardsql, stringMap);
        List<ActivityReport> combinedList = Stream.of(loanactivylist, cardactivylist)
                .flatMap(x -> x.stream())
                .collect(Collectors.toList());

        return loanactivylist;
    }
}
