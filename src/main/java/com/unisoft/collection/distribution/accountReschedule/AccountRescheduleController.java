package com.unisoft.collection.distribution.accountReschedule;
/*
Created by   Islam at 8/26/2019
*/

import com.unisoft.dms.DmsFileSaver;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Controller
@RequestMapping(value = "/collection/account/reschedule/")
public class AccountRescheduleController {

    @Autowired
    private DmsFileSaver dmsFileSaver;

    @Autowired
    private AccountRescheduleRepository accountRescheduleRepository;


    @GetMapping(value = "list")
    public String geList(Model model) {
        LocalDate today = LocalDate.now();

        LocalDate startDateOfMonth = today.withDayOfMonth(1);
        Date startDate = Date.from(startDateOfMonth.atStartOfDay(ZoneId.systemDefault()).toInstant());

        LocalDate endDateOfMonth = today.withDayOfMonth(today.lengthOfMonth());
        String endDateOfMonthString = endDateOfMonth.toString();
        endDateOfMonthString = endDateOfMonthString + " 11:59 PM";

        Date endDate = null;
        try {
            endDate = new SimpleDateFormat("yyyy-MM-dd hh:mm a").parse(endDateOfMonthString);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }

        List<AccountReschedule> accountRescheduleList = accountRescheduleRepository.findByCreatedDateIsBetween(startDate, endDate);
        List<AccountReschedule> accountReschedules = new ArrayList<>();

        Collections.sort(accountRescheduleList, new Comparator<AccountReschedule>() {
            @Override
            public int compare(AccountReschedule o1, AccountReschedule o2) {
                if (o1.getCreatedDate() == null || o2.getCreatedDate() == null)
                    return 0;
                return o1.getCreatedDate().compareTo(o2.getCreatedDate());
            }
        });

        for (int i = accountRescheduleList.size() - 1; i >= 0; i--) {
            AccountReschedule accountReschedule = accountRescheduleList.get(i);
            boolean check = accountReschedules.stream().anyMatch(s -> s.getAccountNo().equals(accountReschedule.getAccountNo()));
            if (!check)
                accountReschedules.add(accountReschedule);
        }
        model.addAttribute("accounts", accountReschedules);
        return "/collection/distribution/accountReschedule/account";
    }

    @GetMapping(value = "create")
    public String getPage() {
        return "/collection/distribution/accountReschedule/create";
    }

    @PostMapping(value = "create")
    public String saveExcel(@RequestParam("file") MultipartFile multipartFile) {
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try {
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(multipartFile.getInputStream());
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
            List<AccountReschedule> accountReschedules = new ArrayList<>();

            Date executionDate = null;
            Date existingEmiDate = null;
            Date newEmiDate = null;
            Date approvalDate = null;
            int numOfRows = Math.max(xssfSheet.getPhysicalNumberOfRows(), 0);

            for (int i = 1; i < numOfRows; i++) {
                XSSFRow row = xssfSheet.getRow(i);

                if (row.getCell(0) != null && row.getCell(0).toString().length() > 1) {

                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
                    executionDate = simpleDateFormat.parse(row.getCell(2).toString());
                    existingEmiDate = simpleDateFormat.parse(row.getCell(10).toString());
                    newEmiDate = simpleDateFormat.parse(row.getCell(14).toString());
                    approvalDate = simpleDateFormat.parse(row.getCell(17).toString());

                    String[] executor = row.getCell(15).toString().split("-");
                    String[] approver = row.getCell(16).toString().split("-");

                    AccountReschedule accountReschedule = new AccountReschedule();
                    accountReschedule.setAccountNo(row.getCell(0) != null ? row.getCell(0).toString() : "");
                    accountReschedule.setStatus(row.getCell(1) != null ? row.getCell(1).toString() : "");
                    accountReschedule.setExecutionDate(executionDate);
                    accountReschedule.setOutStandingAmount(row.getCell(3) != null ? row.getCell(3).toString() : "");
                    accountReschedule.setOverdueAmount(row.getCell(4) != null ? row.getCell(4).toString() : "");
                    accountReschedule.setAssetStatus(row.getCell(5) != null ? row.getCell(5).toString() : "");
                    accountReschedule.setDownPaymentAmount(row.getCell(6) != null ? row.getCell(6).toString() : "");
                    accountReschedule.setExsistingEmiAmount(row.getCell(7) != null ? row.getCell(7).toString() : "");
                    accountReschedule.setExistingTenor(row.getCell(8) != null ? row.getCell(8).toString() : "");
                    accountReschedule.setExsistingInterestRate(row.getCell(9) != null ? row.getCell(9).toString() : "");
                    accountReschedule.setExsistingEmiDate(existingEmiDate);
                    accountReschedule.setNewEmiAmount(row.getCell(10) != null ? row.getCell(10).toString() : "");
                    accountReschedule.setNewTenor(row.getCell(11) != null ? row.getCell(11).toString() : "");
                    accountReschedule.setNewInterestRate(row.getCell(12) != null ? row.getCell(12).toString() : "");
                    accountReschedule.setNewEmiDate(newEmiDate);
                    accountReschedule.setExecutorName(executor[0]);
                    accountReschedule.setExecutorPin(executor[1]);
                    accountReschedule.setExecutorDepartMent(executor[2]);
                    accountReschedule.setApproverName(approver[0]);
                    accountReschedule.setApproverPin(approver[1]);
                    accountReschedule.setApproverDepartMent(approver[2]);
                    accountReschedule.setApprovalDate(approvalDate);
                    accountReschedule.setCreatedBy(userPrincipal.getUsername());
                    accountReschedule.setEnabled(true);
                    accountReschedule.setCreatedDate(new Date());
                    accountReschedules.add(accountReschedule);
                }
            }
            accountRescheduleRepository.saveAll(accountReschedules);
        } catch (IOException | ParseException e) {
            System.out.println(e.getMessage());
        }
        return "redirect:/collection/account/reschedule/list";
    }

}
