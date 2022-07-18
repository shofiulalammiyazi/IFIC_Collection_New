package com.csinfotechbd.reports.ptp;

import com.csinfotechbd.collection.settings.employee.EmployeeDao;
import com.csinfotechbd.collection.settings.employee.EmployeeInfoEntity;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@RequestMapping("/collection/report")
public class PtpReportReportController {

    @Autowired
    PtpReportService ptpReportService;

    @Autowired
    PtpReportsDao dao;

    @Autowired
    EmployeeDao employeeDao;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    @Qualifier("jdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    @GetMapping(value = "/create")
    public String createptpreprt(Model model) {
        List<EmployeeInfoEntity> managerList = employeeDao.getManagerList();
        List<EmployeeInfoEntity> teamLeaderList = employeeDao.getTeamLeaderList();
        List<EmployeeInfoEntity> dealerList = employeeDao.getDealerList();
        List<EmployeeInfoEntity> supervisorList = employeeDao.getSuprvisorList();
        model.addAttribute("managerlist", managerList);
        model.addAttribute("teamLeaderList", teamLeaderList);
        model.addAttribute("dealerList", dealerList);
        model.addAttribute("supervisorList", supervisorList);
        return "collection/reports/ptp/create";
    }


    @PostMapping(value = "/ptpreports")
    public void exportptp(ModelAndView model, @RequestParam(value = "startDate", required = true) String startDate, @RequestParam(value = "endDate", required = true) String endDate, HttpServletResponse response) throws IOException, JRException, SQLException {
        JasperPrint jasperPrint = null;

        response.setContentType("application/x-download");
        response.setHeader("Content-Disposition", String.format("attachment; filename=\"PTP.pdf\""));
        OutputStream out = response.getOutputStream();

        jasperPrint = ptpReportService.exportPtpPdfFile();
        JasperExportManager.exportReportToPdfStream(jasperPrint, out);
    }

    /*
        @PostMapping (value = "/search")
        @ResponseBody
        public List<LoanPtpreport> search(Model model,

                                                @RequestParam(value = "startDate", required = true) String startDate ,
                                                @RequestParam(value = "endDate", required = true) String endDate ,
                                                @RequestParam(value = "dealer", defaultValue = "") String dealer,
                                                @RequestParam(value = "teamleader", defaultValue = "") String teamleader ,
                                                @RequestParam(value = "supervisor" , defaultValue = "") String supervisor,
                                                @RequestParam(value = "manager" , defaultValue = "") String manager,
                                                @RequestParam(value = "account" , defaultValue = "") String account

        ) throws IOException, ParseException, SQLException {

//            String sql = "SELECT c.ACCOUNT_NO, c.ACCOUNT_NAME, a.CREATED_DATE, a.LOAN_PTP_DATE, a.LOAN_AMOUNT, a.LOAN_PTP_STATUS " +
//                    "  FROM LOAN_PTP                        a," +
//                    "       CUSTOMER_BASIC_INFO_ENTITY      b," +
//                    "       LOAN_ACCOUNT_BASIC_INFO         c," +
//                    "       LOAN_ACCOUNT_DISTRIBUTION_INFO  d" +
//                    " WHERE     a.LOANPTP_ID = b.ID" +
//                    "       AND b.ID = c.CUSTOMER_ID" +
//                    "       AND c.ID = d.LOAN_ACCOUNT_BASIC_INFO_ID" +
//                    "       AND a.CREATED_DATE BETWEEN TO_DATE ('"+startDate+"', 'dd/mm/yyyy')" +
//                    "                              AND TO_DATE ('"+endDate+"', 'dd/mm/yyyy')";
            Map<String, String> stringMapLoan = new HashMap<>();
            Map<String, String> stringMapCard = new HashMap<>();
            stringMapLoan.put("startDate", startDate);
            stringMapLoan.put("endDate", endDate);
            stringMapCard.put("startDate", startDate);
            stringMapCard.put("endDate", endDate);

            Connection conn = jdbcTemplate.getDataSource().getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement("");

            String sql = "SELECT c.ACCOUNT_NO, c.ACCOUNT_NAME, a.CREATED_DATE, a.LOAN_PTP_DATE, a.LOAN_AMOUNT, a.LOAN_PTP_STATUS " +
                    "  FROM LOAN_PTP                        a," +
                    "       CUSTOMER_BASIC_INFO_ENTITY      b," +
                    "       LOAN_ACCOUNT_BASIC_INFO         c," +
                    "       LOAN_ACCOUNT_DISTRIBUTION_INFO  d" +
                    " WHERE     a.LOANPTP_ID = b.ID" +
                    "       AND b.ID = c.CUSTOMER_ID" +
                    "       AND c.ID = d.LOAN_ACCOUNT_BASIC_INFO_ID" +
                    "       AND a.CREATED_DATE BETWEEN TO_DATE (:startDate, 'dd/mm/yyyy')" +
                    "                              AND TO_DATE (:endDate, 'dd/mm/yyyy')";



            if( !dealer.equals("") ) {
                sql += " AND d.DEALER_PIN=:dealer";
                stringMapLoan.put("dealer", dealer);
            }

            if( !supervisor.equals("")  ) {
                sql += " AND d.SUPERVISOR_PIN=:supervisor";
                stringMapLoan.put("supervisor", supervisor);
            }
            if( !account.equals("")  ) {
                sql += " AND c.ACCOUNT_NO=:account";
                stringMapLoan.put("account", account);
            }




            String cardsql ="SELECT" +
                    "        c.CARD_NO AS ACCOUNT_NO," +
                    "        c.CARD_NAME AS ACCOUNT_NAME," +
                    "        a.CREATED_DATE," +
                    "        a.CARD_PTP_DATE AS LOAN_PTP_DATE," +
                    "        a.CARD_AMOUNT AS LOAN_AMOUNT," +
                    "        a.CARD_PTP_STATUS AS LOAN_PTP_STATUS" +
                    "         " +
                    "    FROM" +
                    "        CARD_PTP                        a," +
                    "        CUSTOMER_BASIC_INFO_ENTITY      b," +
                    "        CARD_ACCOUNT_BASIC_INFO         c," +
                    "        CARD_ACCOUNT_DISTRIBUTION_INFO  d " +
                    "    WHERE" +
                    "        a.CARDPTP_ID = b.ID" +
                    "        AND b.ID = c.CUSTOMER_ID " +
                    "        AND c.ID = d.CARD_ACCOUNT_BASIC_INFO_ID" +
                    "        AND a.CREATED_DATE BETWEEN TO_DATE (:startDate, 'dd/mm/yyyy')  " +
                    "        AND TO_DATE (:endDate, 'dd/mm/yyyy')";

            if( !dealer.equals("") ) {
                cardsql += " AND d.DEALER_PIN=:dealer";
                stringMapCard.put("dealer", dealer);
            }

            if( !supervisor.equals("")  ) {
                cardsql += " AND d.SUPERVISOR_PIN=:supervisor";
                stringMapCard.put("supervisor", supervisor);
            }
            if( !account.equals("")  ) {
                cardsql += " AND c.ACCOUNT_NO=:account";
                stringMapCard.put("account", account);
            }

            List<LoanPtpreport> laonptp=dao.getlonptp(sql, stringMapLoan);
            List<LoanPtpreport> cardptp=dao.getcardptp(cardsql, stringMapCard);
            List<LoanPtpreport> combinedList = Stream.of(laonptp, cardptp)
                    .flatMap(x -> x.stream())
                    .collect(Collectors.toList());



//            System.err.println("ptp list"+ptp);
            return combinedList;


        }
*/


    @PostMapping(value = "/search")
    @ResponseBody
    public List<LoanPtpreport> search(Model model,

                                      @RequestParam(value = "startDate", required = true) String startDate,
                                      @RequestParam(value = "endDate", required = true) String endDate,
                                      @RequestParam(value = "dealer", defaultValue = "") String dealer,
                                      @RequestParam(value = "teamleader", defaultValue = "") String teamleader,
                                      @RequestParam(value = "supervisor", defaultValue = "") String supervisor,
                                      @RequestParam(value = "manager", defaultValue = "") String manager,
                                      @RequestParam(value = "account", defaultValue = "") String account

    ) throws IOException, ParseException {

        Map<String, String> stringMapLoan = new HashMap<>();
        Map<String, String> stringMapCard = new HashMap<>();
        stringMapLoan.put("startDate", startDate);
        stringMapLoan.put("endDate", endDate);
        stringMapCard.put("startDate", startDate);
        stringMapCard.put("endDate", endDate);


        String sql = "SELECT c.ACCOUNT_NO, c.ACCOUNT_NAME, a.CREATED_DATE, a.LOAN_PTP_DATE, a.LOAN_AMOUNT, a.LOAN_PTP_STATUS " +
                "  FROM LOAN_PTP                        a," +
                "       CUSTOMER_BASIC_INFO_ENTITY      b," +
                "       LOAN_ACCOUNT_BASIC_INFO         c," +
                "       LOAN_ACCOUNT_DISTRIBUTION_INFO  d" +
                " WHERE     a.LOANPTP_ID = b.ID" +
                "       AND b.ID = c.CUSTOMER_ID" +
                "       AND c.ID = d.LOAN_ACCOUNT_BASIC_INFO_ID" +
                "       AND a.CREATED_DATE BETWEEN TO_DATE (?, 'dd/mm/yyyy')" +
                "                              AND TO_DATE (?, 'dd/mm/yyyy')";


        if (!dealer.equals("")) {
            sql += " AND d.DEALER_PIN=?";
            stringMapLoan.put("dealer", dealer);
        }

        if (!supervisor.equals("")) {
            sql += " AND d.SUPERVISOR_PIN=?";
            stringMapLoan.put("supervisor", supervisor);
        }
        if (!account.equals("")) {
            sql += " AND c.ACCOUNT_NO=?";
            stringMapLoan.put("account", account);
        }


        String cardsql = "SELECT" +
                "        c.CARD_NO AS ACCOUNT_NO," +
                "        c.CARD_NAME AS ACCOUNT_NAME," +
                "        a.CREATED_DATE," +
                "        a.CARD_PTP_DATE AS LOAN_PTP_DATE," +
                "        a.CARD_AMOUNT AS LOAN_AMOUNT," +
                "        a.CARD_PTP_STATUS AS LOAN_PTP_STATUS" +
                "         " +
                "    FROM" +
                "        CARD_PTP                        a," +
                "        CUSTOMER_BASIC_INFO_ENTITY      b," +
                "        CARD_ACCOUNT_BASIC_INFO         c," +
                "        CARD_ACCOUNT_DISTRIBUTION_INFO  d " +
                "    WHERE" +
                "        a.CARDPTP_ID = b.ID" +
                "        AND b.ID = c.CUSTOMER_ID " +
                "        AND c.ID = d.CARD_ACCOUNT_BASIC_INFO_ID" +
                "        AND a.CREATED_DATE BETWEEN TO_DATE (:startDate, 'dd/mm/yyyy')  " +
                "        AND TO_DATE (:endDate, 'dd/mm/yyyy')";

        if (!dealer.equals("")) {
            cardsql += " AND d.DEALER_PIN=:dealer";
            stringMapCard.put("dealer", dealer);
        }

        if (!supervisor.equals("")) {
            cardsql += " AND d.SUPERVISOR_PIN=:supervisor";
            stringMapCard.put("supervisor", supervisor);
        }
        if (!account.equals("")) {
            cardsql += " AND c.ACCOUNT_NO=:account";
            stringMapCard.put("account", account);
        }

        List<LoanPtpreport> laonptp = dao.getlonptp(sql, stringMapLoan);
        List<LoanPtpreport> cardptp = dao.getcardptp(cardsql, stringMapCard);
        List<LoanPtpreport> combinedList = Stream.of(laonptp, cardptp)
                .flatMap(x -> x.stream())
                .collect(Collectors.toList());


//            System.err.println("ptp list"+ptp);
        return combinedList;


    }


}
