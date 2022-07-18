package com.csinfotechbd.collection.settings.customerinfouploadcard.professionsegment;


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
public class ProfessionSegmentService {


    @Autowired
    private ProfessionSegmentRepository repository;
    @Value("${spring.jpa.properties.hibernate.jdbc.batch_size}")
    private int batchSize;


    public Map<String, String> saveProfessionSegment(MultipartFile multipartFile) {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Map<String, String> errors = new LinkedHashMap<>();
        XSSFWorkbook xssfWorkbook = null;
       ArrayList<ProfessionSegment> professionSegmentArrayList=new ArrayList<>();
       String contractid=null;
        try {
            xssfWorkbook = new XSSFWorkbook(multipartFile.getInputStream());
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);

            for (int i = 1; i < xssfSheet.getPhysicalNumberOfRows(); i++) {
                try {
                    XSSFRow row = xssfSheet.getRow(i);
                    ProfessionSegment professionSegment=new ProfessionSegment();
//                    if((row.getCell(0).toString().trim()!=null && row.getCell(0).toString().trim().length()>1)){
//                       String contractId=row.getCell(0).toString().trim();
//                        ProfessionSegment professionSegmentData=repository.findByContractId(contractId);
//                        if(professionSegmentData!=null){
//                            professionSegment.setId(professionSegment.getId());
//                        }
//                    }
                    professionSegment.setContractId(row.getCell(0) != null ? row.getCell(0).toString().trim() : null);
                    professionSegment.setProfessionSegment(row.getCell(1) != null ? row.getCell(1).toString().trim() : null);
                    professionSegment.setCreatedDate(new Date());
                    professionSegment.setCreatedBy(user.getEmpId());
                    professionSegmentArrayList.add(professionSegment);

                } catch (Exception e) {
                    System.err.println(e.getMessage());
                    errors.put(contractid, "Something went wrong");
                }

                if (i % batchSize == 0 && i > 0) {
                    repository.saveAll(professionSegmentArrayList);
                    professionSegmentArrayList.clear();
                }

            }
            if (professionSegmentArrayList.size() > 0) {
                repository.saveAll(professionSegmentArrayList);
                professionSegmentArrayList.clear();
            }


        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return errors;
    }

    public List<ProfessionSegment>allProfessionSegment(){
        return repository.findAll();
    }
}
