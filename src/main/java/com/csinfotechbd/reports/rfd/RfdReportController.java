package com.csinfotechbd.reports.rfd;

import com.csinfotechbd.collection.settings.diaryNoteMenu.DiaryNoteMenuEntity;
import com.csinfotechbd.collection.settings.diaryNoteMenu.DnoteMenuService;
import com.csinfotechbd.collection.settings.diaryNoteSubMenu1.DNSubMenu1Dao;
import com.csinfotechbd.collection.settings.diaryNoteSubMenu1.DiaryNoteSubMenu1Entity;
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
@RequestMapping("/rfd/report/")
public class RfdReportController {
    @Autowired
    private DnoteMenuService dnoteMenuService;
    @Autowired
    private DNSubMenu1Dao dnSubMenu1Dao;
    @Autowired
    public RfdReportDao dao;
    @GetMapping("create")
    public String create( Model model){
        List<DiaryNoteMenuEntity> dairynotelist=dnoteMenuService.getAllActive();
        List<DiaryNoteSubMenu1Entity> dairynotesubmenulist=dnSubMenu1Dao.getActiveOnly();

        model.addAttribute("rfd",dairynotelist);
        model.addAttribute("subrfd",dairynotesubmenulist);
        return "collection/reports/rfd/create";
    }


    @PostMapping("rfdsearch")
    @ResponseBody
    public List<RfdReport>list(Model model,
                                         @RequestParam(value = "rfd",required = false ,defaultValue = "") String rfd,
                                         @RequestParam(value = "subrfd",required = false ,defaultValue = "") String subrfd,
                                         @RequestParam(value = "startDate", required = true )String startDate,
                                         @RequestParam(value = "endDate",required = true)String endDate
                                         ){
        String sql="SELECT " +
                " a.CREATED_DATE," +
                " b.ACCOUNT_NO ACCOUNT_NO," +
                " b.ACCOUNT_NAME ACCOUNT_NAME," +
                " a.LOAN_MENU ," +
                " a.LOAN_SUBMENU_ONE," +
                " a.LOAN_SUBMENU_TWO," +
                " a.LOAN_SUBMENU_THREE," +
                " a.LOAN_SHORT_NOTE" +
                "  FROM " +
                " DAIRY_NOTES_LOAN a, LOAN_ACCOUNT_BASIC_INFO b" +
                " WHERE " +
                "    a.DAITYNOTE_ID = b.CUSTOMER_ID" +
                "       AND a.CREATED_DATE BETWEEN TO_DATE (:startDate, 'dd/mm/yyyy')" +
                "                              AND TO_DATE (:endDate, 'dd/mm/yyyy')";

        Map<String, String> stringMapLoan = new HashMap<>();
        Map<String, String> stringMapCard = new HashMap<>();
        stringMapLoan.put("startDate", startDate);
        stringMapLoan.put("endDate", endDate);
        stringMapCard.put("startDate", startDate);
        stringMapCard.put("endDate", endDate);

        if(!rfd.equals("")){
          sql+=  " AND a. LOAN_MENU_ID=:rfd";
          stringMapLoan.put("rfd", rfd);
        }
        if(!subrfd.equals("")){
            sql+=" AND a.LOAN_SUBMENU_ONE_ID=:subrfd";
            stringMapLoan.put("subrfd", subrfd);
        }

        String cardsql="SELECT a.CREATED_DATE, " +
                " b.CARD_NO ACCOUNT_NO," +
                "  b.CARD_NAME ACCOUNT_NAME," +
                " a.CARD_MENU ," +
                " a.CARD_SUBMENU_ONE," +
                " a.CARD_SUBMENU_TWO," +
                " a.CARD_SUBMENU_THREE," +
                " a.CARD_SHORT_NOTE" +
                "  FROM " +
                " DAIRY_NOTES a, CARD_ACCOUNT_BASIC_INFO b" +
                " WHERE  " +
                "a.DAIRY_ID = b.CUSTOMER_ENTITY_ID" +
                "       AND a.CREATED_DATE BETWEEN TO_DATE (:startDate, 'dd/mm/yyyy')" +
                "                              AND TO_DATE (:endDate, 'dd/mm/yyyy')";

        if(!rfd.equals("")){
            cardsql+= " AND a. CARD_MENU_ID=:rfd";
            stringMapCard.put("rfd", rfd);
        }
        if(!subrfd.equals("")){
            cardsql+="  AND a.CARD_SUBMENU_ONE_ID=:subrfd";
            stringMapCard.put("subrfd", subrfd);
        }

      List<RfdReport>loanlist=dao.rfdReportloanList(sql, stringMapLoan);
      List<RfdReport>cardlist=dao.rfdReportcardList(cardsql, stringMapCard);
      List<RfdReport> combinedList = Stream.of(loanlist, cardlist)
                .flatMap(x -> x.stream())
                .collect(Collectors.toList());


      return combinedList;
    }

}
