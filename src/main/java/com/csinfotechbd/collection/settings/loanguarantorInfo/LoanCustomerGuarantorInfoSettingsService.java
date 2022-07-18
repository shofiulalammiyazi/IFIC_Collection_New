package com.csinfotechbd.collection.settings.loanguarantorInfo;

import com.csinfotechbd.customerloanprofile.guarantorinfo.GuarantorInfoEntity;
import com.csinfotechbd.customerloanprofile.guarantorinfo.GuarantorInfoStatus;
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
public class LoanCustomerGuarantorInfoSettingsService {

@Autowired
private  LoanCustomerGuarantorInfoSettingsRepository repository;

    @Value("${spring.jpa.properties.hibernate.jdbc.batch_size}")
    private int batchSize;


    public Map<String, String> saveLoanCustomerGuarantor(MultipartFile multipartFile) {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Map<String, String> errors = new LinkedHashMap<>();
        XSSFWorkbook xssfWorkbook = null;
        ArrayList<GuarantorInfoEntity> guarantorInfoArrayList=new ArrayList<>();
        String loanAccountNumber=null;
        try {
            xssfWorkbook = new XSSFWorkbook(multipartFile.getInputStream());
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);

            for (int i = 1; i < xssfSheet.getPhysicalNumberOfRows(); i++) {
                try {
                    XSSFRow row = xssfSheet.getRow(i);
//                    loanAccountNumber = Objects.toString(row.getCell(0), null);
                    GuarantorInfoEntity guarantorInfo=new GuarantorInfoEntity();
                    if((row.getCell(0).toString().trim()!=null && row.getCell(0).toString().trim().length()>1)&&(row.getCell(1).toString().trim()!=null && row.getCell(1).toString().trim().length()>1)){
                        String loanAccountNo=row.getCell(0).toString().trim();
                        String accountNo=row.getCell(1).toString().trim();
                        GuarantorInfoEntity findedGuarantorInfo=repository.findByAccountNoAndLoanAccountNo(accountNo,loanAccountNo);
                        if(findedGuarantorInfo!=null){
                            guarantorInfo.setId(findedGuarantorInfo.getId());
                        }
                    }
                    guarantorInfo.setLoanAccountNo(row.getCell(0) != null ? row.getCell(0).toString().trim() : null);
                    guarantorInfo.setAccountNo(row.getCell(1) != null ? row.getCell(1).toString().trim() : null);
                    guarantorInfo.setName(row.getCell(2) != null ? row.getCell(2).toString().trim() : null);
                    guarantorInfo.setOccupation(row.getCell(3) != null ? row.getCell(3).toString().trim() : null);
                    guarantorInfo.setCompanyName(row.getCell(4) != null ? row.getCell(4).toString().trim() : null);
                    guarantorInfo.setDesignation(row.getCell(5) != null ? row.getCell(5).toString().trim() : null);
                    guarantorInfo.setPhoneNo(row.getCell(6) != null ? row.getCell(6).toString().trim() : null);
                    guarantorInfo.setHomeAddress(row.getCell(7) != null ? row.getCell(7).toString().trim() : null);
                    guarantorInfo.setOfficeAddress(row.getCell(8) != null ? row.getCell(8).toString().trim() : null);
                    guarantorInfo.setPermanentAddress(row.getCell(9) != null ? row.getCell(9).toString().trim() : null);
                    guarantorInfo.setDob(row.getCell(10) != null ? row.getCell(10).toString().trim() : null);
                    guarantorInfo.setNidOrPassport(row.getCell(11) != null ? row.getCell(11).toString().trim() : null);
                    guarantorInfo.setRelationGuarantor(row.getCell(12) != null ? row.getCell(12).toString().trim() : null);
                    guarantorInfo.setFacilityWithUcbl(row.getCell(13) != null ? row.getCell(13).toString().trim() : null);

                    guarantorInfo.setGuarantorFatherName(row.getCell(14) != null ? row.getCell(14).toString().trim() : null);
                    guarantorInfo.setGuarantorMotherName(row.getCell(15) != null ? row.getCell(15).toString().trim() : null);
                    guarantorInfo.setOptimaId(row.getCell(16) != null ? row.getCell(16).toString().trim() : null);
                    guarantorInfo.setStatus(GuarantorInfoStatus.PENDING);
                    guarantorInfo.setCreatedDate(new Date());
                    guarantorInfo.setCreatedBy(user.getEmpId());
                    guarantorInfo.setDealerPin(user.getEmpId());
                    guarantorInfoArrayList.add(guarantorInfo);

                } catch (Exception e) {
                    System.err.println(e.getMessage());
                    errors.put(loanAccountNumber, "Something went wrong");
                }

                if (i % batchSize == 0 && i > 0) {
                    repository.saveAll(guarantorInfoArrayList);
                    guarantorInfoArrayList.clear();
                }

            }
            if (guarantorInfoArrayList.size() > 0) {
                repository.saveAll(guarantorInfoArrayList);
                guarantorInfoArrayList.clear();
            }


        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return errors;
    }

    public List<GuarantorInfoEntity> AllPendingGuarantorInfo(){
        return repository.findByStatus(GuarantorInfoStatus.PENDING);
    }
    public List<GuarantorInfoEntity>AllApprovedGuarantorInfo(){
        return repository.findByStatus(GuarantorInfoStatus.CONFIRM);
    }
    public List<GuarantorInfoEntity>AllRejecteGuarantorInfo(){
        return repository.findByStatus(GuarantorInfoStatus.REJECT);
    }

    public List<GuarantorInfoEntity> approvedGuarantorService(List<GuarantorInfoEntity> guarantorInfoEntityList) {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<GuarantorInfoEntity> guarantorInfoEntityArrayList = new ArrayList<>();
        try {

            for (GuarantorInfoEntity guar: guarantorInfoEntityList) {
                guar.setStatus(GuarantorInfoStatus.CONFIRM);
                guar.setModifiedBy(user.getEmpId());
                guar.setModifiedDate(new Date());
                guarantorInfoEntityArrayList.add(guar);

                if (guarantorInfoEntityArrayList.size() % batchSize == 0 && guarantorInfoEntityArrayList.size() > 0) {
                    repository.saveAll(guarantorInfoEntityArrayList);
                    guarantorInfoEntityArrayList.clear();
                }
            }

            if (guarantorInfoEntityArrayList.size() > 0) {
                repository.saveAll(guarantorInfoEntityArrayList);
                guarantorInfoEntityArrayList.clear();
            }

        }catch ( Exception e){
            System.err.println(e);
        }

        return guarantorInfoEntityArrayList;
    }
    public List<GuarantorInfoEntity> rejectguarantorService(List<GuarantorInfoEntity> guarantorInfoEntityList) {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<GuarantorInfoEntity> guarantorInfoEntityArrayList = new ArrayList<>();
        try {

            for (GuarantorInfoEntity guar: guarantorInfoEntityList) {
                guar.setStatus(GuarantorInfoStatus.REJECT);
                guar.setModifiedBy(user.getEmpId());
                guar.setModifiedDate(new Date());
                guarantorInfoEntityArrayList.add(guar);

                if (guarantorInfoEntityArrayList.size() % batchSize == 0 && guarantorInfoEntityArrayList.size() > 0) {
                    repository.saveAll(guarantorInfoEntityArrayList);
                    guarantorInfoEntityArrayList.clear();
                }
            }

            if (guarantorInfoEntityArrayList.size() > 0) {
                repository.saveAll(guarantorInfoEntityArrayList);
                guarantorInfoEntityArrayList.clear();
            }

        }catch ( Exception e){
            System.err.println(e);
        }

        return guarantorInfoEntityArrayList;
    }

}
