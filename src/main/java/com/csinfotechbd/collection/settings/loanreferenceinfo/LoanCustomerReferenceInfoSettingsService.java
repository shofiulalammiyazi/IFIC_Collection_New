package com.csinfotechbd.collection.settings.loanreferenceinfo;

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
public class LoanCustomerReferenceInfoSettingsService {


    @Autowired
    private  DateUtils dateUtils;

    @Autowired
    private  LoanCustomerReferenceInfoSettingsRepository repository;
    @Value("${spring.jpa.properties.hibernate.jdbc.batch_size}")
    private int batchSize;


    public Map<String, String> saveLoanCustomerReferenceInfo(MultipartFile multipartFile) {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Map<String, String> errors = new LinkedHashMap<>();
        XSSFWorkbook xssfWorkbook = null;
       ArrayList<ReferenceInfoEntity> referenceInfoEntityArrayList=new ArrayList<>();
       String loanAccountNumber=null;
        try {
            xssfWorkbook = new XSSFWorkbook(multipartFile.getInputStream());
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);

            for (int i = 1; i < xssfSheet.getPhysicalNumberOfRows(); i++) {
                try {
                    XSSFRow row = xssfSheet.getRow(i);
                    loanAccountNumber = Objects.toString(row.getCell(0), null);
                    ReferenceInfoEntity referenceInfoEntity=new ReferenceInfoEntity();
                    if((row.getCell(0).toString().trim()!=null && row.getCell(0).toString().trim().length()>1)&&(row.getCell(1).toString().trim()!=null && row.getCell(1).toString().trim().length()>1)){
                       String loanAccountNo=row.getCell(0).toString().trim();
                       String accountNo=row.getCell(1).toString().trim();
                        ReferenceInfoEntity findedReferenceInfo=repository.findByAccountNoAndLoanAccountNo(accountNo,loanAccountNo);
                        if(findedReferenceInfo!=null){
                            referenceInfoEntity.setId(findedReferenceInfo.getId());
                        }
                    }
                    referenceInfoEntity.setLoanAccountNo(row.getCell(0) != null ? row.getCell(0).toString().trim() : null);
                    referenceInfoEntity.setAccountNo(row.getCell(1) != null ? row.getCell(1).toString().trim() : null);
                    referenceInfoEntity.setName(row.getCell(2) != null ? row.getCell(2).toString().trim() : null);
                    referenceInfoEntity.setOccupation(row.getCell(3) != null ? row.getCell(3).toString().trim() : null);
                    referenceInfoEntity.setCompanyName(row.getCell(4) != null ? row.getCell(4).toString().trim() : null);
                    referenceInfoEntity.setDesignation(row.getCell(5) != null ? row.getCell(5).toString().trim() : null);
                    referenceInfoEntity.setPhoneNo(row.getCell(6) != null ? row.getCell(6).toString().trim() : null);
                    referenceInfoEntity.setHomeAddress(row.getCell(7) != null ? row.getCell(7).toString().trim() : null);
                    referenceInfoEntity.setOfficeAddress(row.getCell(8) != null ? row.getCell(8).toString().trim() : null);
                    referenceInfoEntity.setPermanentAddress(row.getCell(9) != null ? row.getCell(9).toString().trim() : null);
                    referenceInfoEntity.setDob(row.getCell(10) != null ? row.getCell(10).toString().trim() : null);
                    referenceInfoEntity.setNidOrPassport(row.getCell(11) != null ? row.getCell(11).toString().trim() : null);
                    referenceInfoEntity.setRelationReference(row.getCell(12) != null ? row.getCell(12).toString().trim() : null);
                    referenceInfoEntity.setFacilityWithUcbl(row.getCell(13) != null ? row.getCell(13).toString().trim() : null);
                    referenceInfoEntity.setFatherName(row.getCell(14) != null ? row.getCell(14).toString().trim() : null);
                    referenceInfoEntity.setMotherName(row.getCell(15) != null ? row.getCell(14).toString().trim() : null);
                    referenceInfoEntity.setOptimaId(row.getCell(16) != null ? row.getCell(14).toString().trim() : null);
                    referenceInfoEntity.setStatus(ReferenceInfoStatus.PENDING);
                    referenceInfoEntity.setCreatedDate(new Date());
                    referenceInfoEntity.setCreatedBy(user.getEmpId());
                    referenceInfoEntity.setDealerPin(user.getEmpId());
                    referenceInfoEntityArrayList.add(referenceInfoEntity);

                } catch (Exception e) {
                    System.err.println(e.getMessage());
                    errors.put(loanAccountNumber, "Something went wrong");
                }

                if (i % batchSize == 0) {
                    repository.saveAll(referenceInfoEntityArrayList);
                    referenceInfoEntityArrayList.clear();
                }

            }
            if (referenceInfoEntityArrayList.size() > 0) {
                repository.saveAll(referenceInfoEntityArrayList);
                referenceInfoEntityArrayList.clear();
            }


        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return errors;
    }

public List<ReferenceInfoEntity>AllPendingReferenceInfo(){
        return repository.findByStatus(ReferenceInfoStatus.PENDING);
}
public List<ReferenceInfoEntity>AllApprovedReferenceInfo(){
        return repository.findByStatus(ReferenceInfoStatus.CONFIRM);
}
public List<ReferenceInfoEntity>AllRejecteReferenceInfo(){
        return repository.findByStatus(ReferenceInfoStatus.REJECT);
}

    public List<ReferenceInfoEntity> approvedReferenceService(List<ReferenceInfoEntity> referenceInfoEntityList) {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<ReferenceInfoEntity> referenceInfoEntityArrayList = new ArrayList<>();
        try {

            for (ReferenceInfoEntity ref : referenceInfoEntityList) {
                ref.setStatus(ReferenceInfoStatus.CONFIRM);
                ref.setModifiedBy(user.getEmpId());
                ref.setModifiedDate(new Date());
                referenceInfoEntityArrayList.add(ref);

                if (referenceInfoEntityArrayList.size() % batchSize == 0 && referenceInfoEntityArrayList.size() > 0) {
                    repository.saveAll(referenceInfoEntityArrayList);
                    referenceInfoEntityArrayList.clear();
                }
            }

            if (referenceInfoEntityArrayList.size() > 0) {
                repository.saveAll(referenceInfoEntityArrayList);
                referenceInfoEntityArrayList.clear();
            }

        }catch ( Exception e){
            System.err.println(e);
        }

        return referenceInfoEntityArrayList;
    }
    public List<ReferenceInfoEntity> rejectReferenceService(List<ReferenceInfoEntity> referenceInfoEntityList) {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<ReferenceInfoEntity> referenceInfoEntityArrayList = new ArrayList<>();
        try {

            for (ReferenceInfoEntity ref : referenceInfoEntityList) {
                ref.setStatus(ReferenceInfoStatus.REJECT);
                ref.setModifiedBy(user.getEmpId());
                ref.setModifiedDate(new Date());
                referenceInfoEntityArrayList.add(ref);

                if (referenceInfoEntityArrayList.size() % batchSize == 0 && referenceInfoEntityArrayList.size() > 0) {
                    repository.saveAll(referenceInfoEntityArrayList);
                    referenceInfoEntityArrayList.clear();
                }
            }

            if (referenceInfoEntityArrayList.size() > 0) {
                repository.saveAll(referenceInfoEntityArrayList);
                referenceInfoEntityArrayList.clear();
            }

        }catch ( Exception e){
            System.err.println(e);
        }

        return referenceInfoEntityArrayList;
    }

}
