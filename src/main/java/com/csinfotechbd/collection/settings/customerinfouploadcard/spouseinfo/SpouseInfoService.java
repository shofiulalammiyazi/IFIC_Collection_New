package com.csinfotechbd.collection.settings.customerinfouploadcard.spouseinfo;

import com.csinfotechbd.customerloanprofile.referenceinfo.ReferenceInfoEntity;
import com.csinfotechbd.customerloanprofile.referenceinfo.ReferenceInfoStatus;
import com.csinfotechbd.user.UserPrincipal;
import com.csinfotechbd.utillity.DateUtils;
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
public class SpouseInfoService {




    @Autowired
    private SpouseInfoRepository repository;
    @Value("${spring.jpa.properties.hibernate.jdbc.batch_size}")
    private int batchSize;


    public Map<String, String> saveSpouseInfo(MultipartFile multipartFile) {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Map<String, String> errors = new LinkedHashMap<>();
        XSSFWorkbook xssfWorkbook = null;
        ArrayList<SpouseInfo> spouseInfoArrayList=new ArrayList<>();
        String contractid=null;
        try {
            xssfWorkbook = new XSSFWorkbook(multipartFile.getInputStream());
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);

            for (int i = 1; i < xssfSheet.getPhysicalNumberOfRows(); i++) {
                try {
                    XSSFRow row = xssfSheet.getRow(i);
                    SpouseInfo spouseInfo=new SpouseInfo();
                    spouseInfo.setContractId(row.getCell(0) != null ? row.getCell(0).toString().trim() : null);
                    spouseInfo.setSpouseMobileNo(row.getCell(1) != null ? row.getCell(1).toString().trim() : null);
                    spouseInfo.setSpouseOccupation(row.getCell(2) != null ? row.getCell(2).toString().trim() : null);
                    spouseInfo.setCreatedDate(new Date());
                    spouseInfo.setCreatedBy(user.getEmpId());
                    spouseInfoArrayList.add(spouseInfo);

                } catch (Exception e) {
                    System.err.println(e.getMessage());
                    errors.put(contractid, "Something went wrong");
                }

                if (i % batchSize == 0 && i > 0) {
                    repository.saveAll(spouseInfoArrayList);
                    spouseInfoArrayList.clear();
                }

            }
            if (spouseInfoArrayList.size() > 0) {
                repository.saveAll(spouseInfoArrayList);
                spouseInfoArrayList.clear();
            }


        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return errors;
    }

    public List<SpouseInfo>allSpouseInfo(){
        return repository.findAll();
    }

}
