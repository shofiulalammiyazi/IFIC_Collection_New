package com.unisoft.collection.settings.noteDetailsExcel;

import com.unisoft.customerloanprofile.dailynote.DailyNoteEntity;
import com.unisoft.user.User;
import com.unisoft.user.UserPrincipal;
import com.unisoft.user.UserService;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class NoteDetailsExcelService {

    @Autowired
    private NoteDetailsExcelRepository noteDetailsExcelRepository;

    @Autowired
    private UserService userService;

    public List<DailyNoteEntity> findAll(){
        return noteDetailsExcelRepository.findAll();
    }

    public DailyNoteEntity findByAccountNo(String accountNo){
        return noteDetailsExcelRepository.findDailyNoteEntitiesByAccountNo(accountNo);
    }

    @Value("1000")
    private int batchSize;

    public Map<String, String> saveNoteDetailsExcel(MultipartFile multipartFile) {

        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Map<String, String> errors = new LinkedHashMap<>();
        XSSFWorkbook xssfWorkbook = null;
        ArrayList<DailyNoteEntity> dailyNoteEntityArrayList = new ArrayList<>();
        String loanAccountNumber = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            xssfWorkbook = new XSSFWorkbook(multipartFile.getInputStream());
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);

            for (int i = 1; i < xssfSheet.getPhysicalNumberOfRows(); i++) {
                try {
                    XSSFRow row = xssfSheet.getRow(i);
                    DailyNoteEntity dailyNoteEntity = new DailyNoteEntity();
                    if ((row.getCell(0).toString().trim() != null && row.getCell(0).toString().trim().length() > 1)
                            && (row.getCell(1).toString().trim() != null && row.getCell(1).toString().trim().length() > 1)) {
                        String accountNo=row.getCell(0).toString().trim();
                        DailyNoteEntity dailyNoteEntity1 = noteDetailsExcelRepository.findDailyNoteEntitiesByAccountNo(accountNo);
                        if (dailyNoteEntity1 != null) {
                            dailyNoteEntity.setId(dailyNoteEntity1.getId());
                        }

                        dailyNoteEntity.setAccountNo(row.getCell(0) != null ? row.getCell(0).toString().trim() : null);
                        dailyNoteEntity.setNote(row.getCell(1) != null ? row.getCell(1).toString().trim() : null);
                        dailyNoteEntity.setCreatedDate(row.getCell(2) != null ? new Date(row.getCell(2).toString()) : null);

                        String pin = row.getCell(3) != null ? row.getCell(3).toString().trim() : null;

                        User user1;
                        if(pin != null) {
                            user1 = userService.getUserByUsername(pin);

                            dailyNoteEntity.setPin(user1.getUsername());
                            dailyNoteEntity.setCreatedBy(user.getFirstName());
                        }

//                        dailyNoteEntity.setCreatedDate(new Date());
                        dailyNoteEntity.setCreatedBy(user.getFirstName());
                        dailyNoteEntity.setPin(user.getEmpId());
                        dailyNoteEntityArrayList.add(dailyNoteEntity);
                    } else {
                        continue;
                    }

                } catch (Exception e) {
                    System.err.println(e.getMessage());
                    errors.put(loanAccountNumber, "Something went wrong");
                }

                if (i % batchSize == 0 && i > 0) {
                    noteDetailsExcelRepository.saveAll(dailyNoteEntityArrayList);
                    dailyNoteEntityArrayList.clear();
                }

            }
            if (dailyNoteEntityArrayList.size() > 0) {
                noteDetailsExcelRepository.saveAll(dailyNoteEntityArrayList);
                dailyNoteEntityArrayList.clear();
            }


        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return errors;
    }

}
