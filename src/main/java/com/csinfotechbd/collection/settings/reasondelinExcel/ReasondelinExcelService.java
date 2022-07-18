package com.csinfotechbd.collection.settings.reasondelinExcel;

import com.csinfotechbd.collection.reasonDelinquency.ReasonDelinquency;
import com.csinfotechbd.user.UserPrincipal;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
public class ReasondelinExcelService {

    @Autowired
    private ReasondelinExcelRepository reasondelinExcelRepository;

    public List<ReasonDelinquency> findAll(){
        return reasondelinExcelRepository.findAll();
    }

    public ReasonDelinquency findByAccountNo(String accountNo){
        return reasondelinExcelRepository.findReasonDelinquencyByAccountNo(accountNo);
    }

    @Value("1000")
    private int batchSize;

    public Map<String, String> saveReasonDelinExcel(MultipartFile multipartFile) {

        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Map<String, String> errors = new LinkedHashMap<>();
        XSSFWorkbook xssfWorkbook = null;
        ArrayList<ReasonDelinquency> reasonDelinquencyArrayList = new ArrayList<>();
        String loanAccountNumber = null;
        try {
            xssfWorkbook = new XSSFWorkbook(multipartFile.getInputStream());
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);

            for (int i = 1; i < xssfSheet.getPhysicalNumberOfRows(); i++) {
                try {
                    XSSFRow row = xssfSheet.getRow(i);
                    ReasonDelinquency reasonDelinquency = new ReasonDelinquency();
                    if ((row.getCell(0).toString().trim() != null && row.getCell(0).toString().trim().length() > 1)
                            && (row.getCell(1).toString().trim() != null && row.getCell(1).toString().trim().length() > 1)) {
                        String accountNo=row.getCell(0).toString().trim();
                        ReasonDelinquency reasonDelinquency1 = reasondelinExcelRepository.findReasonDelinquencyByAccountNo(accountNo);
                        if (reasonDelinquency1 != null) {
                            reasonDelinquency.setId(reasonDelinquency1.getId());
                        }

                        reasonDelinquency.setAccountNo(row.getCell(0) != null ? row.getCell(0).toString().trim() : null);
                        reasonDelinquency.setReaDelinName(row.getCell(1) != null ? row.getCell(1).toString().trim() : null);

                        reasonDelinquency.setCreatedDate(new Date());
                        reasonDelinquency.setCreatedBy(user.getEmpId());
                        reasonDelinquency.setDealerName(user.getFirstName().concat(user.getLastName()));
                        reasonDelinquencyArrayList.add(reasonDelinquency);
                    } else {
                        continue;
                    }

                } catch (Exception e) {
                    System.err.println(e.getMessage());
                    errors.put(loanAccountNumber, "Something went wrong");
                }

                if (i % batchSize == 0 && i > 0) {
                    reasondelinExcelRepository.saveAll(reasonDelinquencyArrayList);
                    reasonDelinquencyArrayList.clear();
                }

            }
            if (reasonDelinquencyArrayList.size() > 0) {
                reasondelinExcelRepository.saveAll(reasonDelinquencyArrayList);
                reasonDelinquencyArrayList.clear();
            }


        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return errors;
    }

}

