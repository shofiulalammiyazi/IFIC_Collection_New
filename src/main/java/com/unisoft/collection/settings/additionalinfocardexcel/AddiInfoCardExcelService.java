package com.unisoft.collection.settings.additionalinfocardexcel;

import com.unisoft.customerloanprofile.AdditionalInfo.AdditionalInfoStatus;
import com.unisoft.retail.card.dataEntry.additionalinfocard.AdditionalInfoCard;
import com.unisoft.user.UserPrincipal;
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
public class AddiInfoCardExcelService {

    @Autowired
    private AddiInfoCardExcelRepository addiInfoExcelRepository;


    public List<AdditionalInfoCard> findAll() {
        return addiInfoExcelRepository.findAll();
    }



    @Value("${spring.jpa.properties.hibernate.jdbc.batch_size}")
    private int batchSize;

    public Map<String, String> saveAddiInfoExcel(MultipartFile multipartFile) {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Map<String, String> errors = new LinkedHashMap<>();
        XSSFWorkbook xssfWorkbook = null;
        ArrayList<AdditionalInfoCard> additionalInfoArrayList = new ArrayList<>();
//        String loanAccountNumber = null;
        try {
            xssfWorkbook = new XSSFWorkbook(multipartFile.getInputStream());
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);

            for (int i = 1; i < xssfSheet.getPhysicalNumberOfRows(); i++) {
                try {
                    XSSFRow row = xssfSheet.getRow(i);
                    AdditionalInfoCard additionalInfo = new AdditionalInfoCard();
                    if ((row.getCell(0).toString().trim() != null && row.getCell(0).toString().trim().length() > 1)
                            && (row.getCell(1).toString().trim() != null && row.getCell(1).toString().trim().length() > 1)) {
                        additionalInfo.setCardType("basic");
                        additionalInfo.setClientId(row.getCell(0) != null ? row.getCell(0).toString().trim() : null);
                        additionalInfo.setContractId(row.getCell(1) != null ? row.getCell(1).toString().trim() : null);
                        additionalInfo.setOccupation(row.getCell(2) != null ? row.getCell(2).toString().trim() : null);
                        additionalInfo.setOrgName(row.getCell(3) != null ? row.getCell(3).toString().trim() : null);
                        additionalInfo.setContactNo(row.getCell(4) != null ? row.getCell(4).toString().trim() : null);
                        additionalInfo.setEmail(row.getCell(5) != null ? row.getCell(5).toString().trim() : null);
                        additionalInfo.setSpouseMblNo(row.getCell(6) != null ? row.getCell(6).toString().trim() : null);
                        additionalInfo.setSpouseOccupation(row.getCell(7) != null ? row.getCell(7).toString().trim() : null);
                        additionalInfo.setSpouseAcNo(row.getCell(8) != null ? row.getCell(8).toString().trim() : null);
                        additionalInfo.setSpouseFatherName(row.getCell(9) != null ? row.getCell(9).toString().trim() : null);
                        additionalInfo.setSpouseMotherName(row.getCell(10) != null ? row.getCell(10).toString().trim() : null);
                        additionalInfo.setHomeAddress(row.getCell(11) != null ? row.getCell(11).toString().trim() : null);
                        additionalInfo.setOfficeAddress(row.getCell(12) != null ? row.getCell(12).toString().trim() : null);
                        additionalInfo.setPermanentAddress(row.getCell(13) != null ? row.getCell(13).toString().trim() : null);
                        additionalInfo.setRemarks(row.getCell(14) != null ? row.getCell(14).toString().trim() : null);
                        additionalInfo.setStatus(AdditionalInfoStatus.CONFIRM);
                        additionalInfo.setCreatedDate(new Date());
                        additionalInfo.setCreatedBy(user.getEmpId());
                        additionalInfo.setDealerPin(user.getEmpId());
                        additionalInfoArrayList.add(additionalInfo);
                    } else {
                        continue;
                    }

                } catch (Exception e) {
                    System.err.println(e.getMessage());
//                    errors.put(loanAccountNumber, "Something went wrong");
                }

                if (i % batchSize == 0 && i > 0) {
                    addiInfoExcelRepository.saveAll(additionalInfoArrayList);
                    additionalInfoArrayList.clear();
                }

            }
            if (additionalInfoArrayList.size() > 0) {
                addiInfoExcelRepository.saveAll(additionalInfoArrayList);
                additionalInfoArrayList.clear();
            }


        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return errors;
    }
}

