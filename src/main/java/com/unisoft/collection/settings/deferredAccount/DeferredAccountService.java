package com.unisoft.collection.settings.deferredAccount;

import com.unisoft.user.UserPrincipal;
import com.unisoft.utillity.ExcelFileUtils;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
@RequiredArgsConstructor
public class DeferredAccountService {

    private final ExcelFileUtils excelFileUtils;
    private final DeferredAccountRepository deferredAccountRepository;


    public List<String> saveFromExcel(MultipartFile file) {
        Map<String, Object> cache = new HashMap<>();
        try {
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(file.getInputStream());
            int totalSheets = xssfWorkbook.getNumberOfSheets();

            for (int i = 0; i < totalSheets; i++)
                extractXssfSheet(xssfWorkbook.getSheetAt(i), cache);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return (List<String>) cache.get("errors");
    }


    @SuppressWarnings("unchecked")
    private void extractXssfSheet(XSSFSheet table, Map<String, Object> cache) {

        List<String> errors = (List<String>) cache.getOrDefault("errors", new LinkedList<>());

        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        DeferredAccount deferredAccount;
        Iterator<Row> rows = table.iterator();

        // Skip rows untill the header row found and collect cell headers and indices
        Map<Integer, String> headerColumns = new HashMap<Integer, String>() {{
            put(0, "Account No");
        }};
        Map<String, Integer> headers = excelFileUtils.getColumnHeadersAndIndices(rows, headerColumns);

        while(rows.hasNext()){
            try{
                Row row = rows.next();
                deferredAccount = new DeferredAccount();

                String accNo = excelFileUtils.getTextCellValue(row, headers, "Account No", errors);
                String status = excelFileUtils.getTextCellValue(row, headers, "Status", errors);

                deferredAccount.setAccNo(accNo);
                deferredAccount.setStatus(status);
                deferredAccount.setCreatedBy(userPrincipal.getFirstName());
                deferredAccount.setCreatedDate(new Date());
                deferredAccount.setEnabled(true);
                deferredAccount.setDeleted(false);

                deferredAccountRepository.save(deferredAccount);

            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    public DeferredAccount findDeferredAccount(String accountNo) {
        return deferredAccountRepository.findDeferredAccount(accountNo);
    }
}
