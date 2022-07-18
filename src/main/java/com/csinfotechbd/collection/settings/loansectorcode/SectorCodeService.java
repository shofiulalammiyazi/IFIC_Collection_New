package com.csinfotechbd.collection.settings.loansectorcode;

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
public class SectorCodeService {

@Autowired
private SectorCodeRepository repository;

    @Value("${spring.jpa.properties.hibernate.jdbc.batch_size}")
    private int batchSize;


    public Map<String, String> saveSectorCode(MultipartFile multipartFile) {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Map<String, String> errors = new LinkedHashMap<>();
        XSSFWorkbook xssfWorkbook = null;
        ArrayList<SectorCodeEntity> sectorCodeArrayList=new ArrayList<>();
        String loanAccountNumber=null;
        try {
            xssfWorkbook = new XSSFWorkbook(multipartFile.getInputStream());
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);

            for (int i = 1; i < xssfSheet.getPhysicalNumberOfRows(); i++) {
                try {
                    XSSFRow row = xssfSheet.getRow(i);
                    SectorCodeEntity sectorCodeEntity =new SectorCodeEntity();
                    if ((row.getCell(0).toString().trim() != null && row.getCell(0).toString().trim().length() > 1)
                            && (row.getCell(2).toString().trim() != null && row.getCell(2).toString().trim().length() > 1)
                            && (row.getCell(3).toString().trim() != null && row.getCell(3).toString().trim().length() > 1)) {
                        sectorCodeEntity.setAccountNo(row.getCell(0).toString().trim());
                        sectorCodeEntity.setGroupName(row.getCell(1) != null ? row.getCell(1).toString().trim() : null);
                        sectorCodeEntity.setSectorCode(row.getCell(2).toString().trim());
                        sectorCodeEntity.setSectorNo(row.getCell(3).toString().trim());
                        sectorCodeEntity.setOptimaId(row.getCell(4) != null ? row.getCell(4).toString().trim() : null);
                        sectorCodeEntity.setCreatedDate(new Date());
                        sectorCodeEntity.setCreatedBy(user.getEmpId());
                        sectorCodeArrayList.add(sectorCodeEntity);
                    }else{
                        continue;
                    }


                } catch (Exception e) {
                    System.err.println(e.getMessage());
                    errors.put(loanAccountNumber, "Something went wrong");
                }

                if (i % batchSize == 0 && i > 0) {
                    repository.saveAll(sectorCodeArrayList);
                    sectorCodeArrayList.clear();
                }

            }
            if (sectorCodeArrayList.size() > 0) {
                repository.saveAll(sectorCodeArrayList);
                sectorCodeArrayList.clear();
            }


        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return errors;
    }





    public  List<SectorCodeEntity>findAllSectorCode(){

        return repository.findAll();
    }

    public SectorCodeEntity findSectorCodeEntityByAccountNo(String accountNo) {
        return repository.findSectorCodeEntityByAccountNo(accountNo);
    }
}
