package com.csinfotechbd.collection.settings.previousaccount;

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
public class PreviousAccountService {

@Autowired
private PreviousAccountRepository repository;

    @Value("${spring.jpa.properties.hibernate.jdbc.batch_size}")
    private int batchSize;


    public Map<String, String> savePrevious(MultipartFile multipartFile) {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Map<String, String> errors = new LinkedHashMap<>();
        XSSFWorkbook xssfWorkbook = null;
        ArrayList<PreviousAccountEntity> previousAccountList=new ArrayList<>();
        try {
            xssfWorkbook = new XSSFWorkbook(multipartFile.getInputStream());
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);

            for (int i = 1; i < xssfSheet.getPhysicalNumberOfRows(); i++) {
                try {
                    XSSFRow row = xssfSheet.getRow(i);
                    PreviousAccountEntity previousAccount=new PreviousAccountEntity();
                    if ((row.getCell(0).toString().trim() != null && row.getCell(0).toString().trim().length() > 1)
                            && (row.getCell(1).toString().trim() != null && row.getCell(1).toString().trim().length() > 1)) {
                        previousAccount.setLoanAccountNo(row.getCell(0).toString().trim());
                        previousAccount.setPreviousAccountNo(row.getCell(1) != null ? row.getCell(1).toString().trim() : null);
                        previousAccount.setCreatedDate(new Date());
                        previousAccount.setCreatedBy(user.getEmpId());
                        previousAccountList.add(previousAccount);
                    }else{
                        continue;
                    }


                } catch (Exception e) {
                    System.err.println(e.getMessage());
//                    errors.put(errors, "Something went wrong");
                }

                if (i % batchSize == 0 && i > 0) {
                    repository.saveAll(previousAccountList);
                    previousAccountList.clear();
                }

            }
            if (previousAccountList.size() > 0) {
                repository.saveAll(previousAccountList);
                previousAccountList.clear();
            }


        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return errors;
    }





    public  List<PreviousAccountEntity>findAllPreviousAccount(){

        return repository.findAll();
    }

    public PreviousAccountEntity findPreviousAcccountByLoanAccountNo(String accountNo) {
        return repository.findPreviousAccountByLoanAccountNo(accountNo);
    }
}
