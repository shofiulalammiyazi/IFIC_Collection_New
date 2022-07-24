package com.unisoft.collection.samd.dataEntry.timeExtensionTdConversion;

import com.unisoft.common.CommonService;
import com.unisoft.cryptography.CryptoException;
import com.unisoft.dms.DmsFileSaver;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Tuple;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TimeExtensionTDConversionService implements CommonService<TimeExtensionTdConversion> {

    @Autowired
    private DmsFileSaver dmsFileSaver;

    @Autowired
    private TimeExtensionTdConversionRepository timeExtensionTdConversionRepository;

    @Autowired
    private TimeExtensionTdConversionFilesRepository timeExtensionTdConversionFilesRepository;

    @Override
    public Map<String, Object> storeData(TimeExtensionTdConversion data) {
        Map<String, Object> response = new HashMap<>();
        List<String> errorMessages = this.validate(data);
        data = timeExtensionTdConversionRepository.save(data);


        if (errorMessages.size() == 0){
            MultipartFile file = data.getFile();
            if (file != null && file.getOriginalFilename() != null && !file.getOriginalFilename().isEmpty()){
                Session cmisSession = null;
                try{
                    cmisSession = dmsFileSaver.cmisSession();
                } catch (Exception e){
                    e.printStackTrace();
                }

                try {
                    if (cmisSession != null) {
                        String filePath = "time_extension_td_conversion/" + data.getCustomerId();
                        String fileName = file.getOriginalFilename();
                        String fileType = FilenameUtils.getExtension(fileName);
                        Session session = dmsFileSaver.cmisSession();
                        String dmsFileId = dmsFileSaver.saveFileToDmsFilePath(file, filePath, fileName, cmisSession);

                        TimeExtensionTdConversionFiles timeExtensionTdConversionFiles = new TimeExtensionTdConversionFiles();
                        timeExtensionTdConversionFiles.setFileName(fileName);
                        timeExtensionTdConversionFiles.setDmsFileId(dmsFileId);
                        timeExtensionTdConversionFiles.setDmsFileType(fileType);
                        timeExtensionTdConversionFiles.settimeExtensionTdConversion(data);
                        timeExtensionTdConversionFiles.setTimeExtensionTdConversionId(data.getId());
                        timeExtensionTdConversionFilesRepository.save(timeExtensionTdConversionFiles);
                        data.setFileName(fileName);
                        data.setDmsFileType(fileType);
                        data.setDmsFileId(dmsFileId);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (CryptoException e) {
                    e.printStackTrace();
                }

            }

            timeExtensionTdConversionRepository.save(data);
            response.put("outcome", "success");
            response.put("timeExtensionTdConversion", data);
        }
        else {
            response.put("outcome", "failure");
            response.put("errors", errorMessages);
        }

        return response;
    }

    @Override
    public Map<String, Object> updateData(TimeExtensionTdConversion data) {
        return null;
    }

    @Override
    public Map<String, Object> deleteData(TimeExtensionTdConversion data) {
        return null;
    }

    @Override
    public TimeExtensionTdConversion findDataById(Long id) {
        return null;
    }

    public TimeExtensionTdConversion findDataByCustomerId(Long customerId){
        TimeExtensionTdConversion data = timeExtensionTdConversionRepository.findByCustomerId(customerId);
        return data;
    }

    public TimeExtensionTdConversionDto getByCustomerId(Long customerId) {
        Tuple timeExtensiontdConversionDto = timeExtensionTdConversionRepository.findByTimeExtensionCustomerId(customerId);

        TimeExtensionTdConversionDto dto = new TimeExtensionTdConversionDto();

        dto.setId(timeExtensiontdConversionDto.get("id"));
        dto.setProposalType(timeExtensiontdConversionDto.get("proposalType"));
        dto.setBranchName(timeExtensiontdConversionDto.get("branchName"));
        dto.setLoanAccountNo(timeExtensiontdConversionDto.get("loanAccountNo"));
        dto.setLoanAccountName(timeExtensiontdConversionDto.get("loanAccountName"));
        dto.setFacilityNature(timeExtensiontdConversionDto.get("facilityNature"));
        dto.setDisbursementDate(timeExtensiontdConversionDto.get("disbursementDate"));
        dto.setLegalDetailStatus(timeExtensiontdConversionDto.get("legalDetailStatus"));
        dto.setFillingAmount(timeExtensiontdConversionDto.get("fillingAmount"));
        dto.setTypeOfCase(timeExtensiontdConversionDto.get("typeOfCase"));
        dto.setExpiryDate(timeExtensiontdConversionDto.get("expiryDate"));
        dto.setProposalSendBy(timeExtensiontdConversionDto.get("proposalSendBy"));
        dto.setMemoNotePreparedBy(timeExtensiontdConversionDto.get("memoNotePreparedBy"));

        dto.setCLStatus(timeExtensiontdConversionDto.get("cLStatus"));
        dto.setCLStatusId(timeExtensiontdConversionDto.get("cLStatusId"));
        dto.setProposalPlacedToId(timeExtensiontdConversionDto.get("proposalPlacedToId"));
        dto.setPropPlaceName(timeExtensiontdConversionDto.get("propPlaceName"));
        dto.setApprovalLevelName(timeExtensiontdConversionDto.get("approvalLevelName"));
        dto.setApprovalLevelId(timeExtensiontdConversionDto.get("approvalLevelId"));
        dto.setProposalPreparedFor(timeExtensiontdConversionDto.get("proposalPreparedFor"));
        dto.setProposalPreparedForId(timeExtensiontdConversionDto.get("proposalPreparedForId"));
        dto.setPurposeOfTheProposal(timeExtensiontdConversionDto.get("purposeOfTheProposalId"));
        dto.setPurposeOfTheProposalName(timeExtensiontdConversionDto.get("purposeOfTheProposal"));

        dto.setExistingCaseStatus(timeExtensiontdConversionDto.get("existingCaseStatus"));
        dto.setDynamicCalculatorForTETDConversion(timeExtensiontdConversionDto.get("dynamicCalculatorForTETDConversion"));
        dto.setTotalNoOfTETDC(timeExtensiontdConversionDto.get("totalNoOfTETDC"));
        dto.setProposedExtendedDate(timeExtensiontdConversionDto.get("proposedExtendedDate"));
        dto.setSanctionAcceptedByBorrower(timeExtensiontdConversionDto.get("sanctionAcceptedByBorrower"));
        dto.setSanctionAcceptedDateByBorrower(timeExtensiontdConversionDto.get("sanctionAcceptedDateByBorrower"));
        dto.setCapitalRelease(timeExtensiontdConversionDto.get("capitalRelease"));
        dto.setNplReduced(timeExtensiontdConversionDto.get("nplReduced"));
        dto.setRemarksOrComments(timeExtensiontdConversionDto.get("remarksOrComments"));
        dto.setTotalLegalAndOtherExpense(timeExtensiontdConversionDto.get("totalLegalAndOtherExpense"));

        dto.setFileName(timeExtensiontdConversionDto.get("fileName"));
        dto.setDmsFileId(timeExtensiontdConversionDto.get("dmsFileId"));
        dto.setDmsFileType(timeExtensiontdConversionDto.get("dmsFileType"));

        return dto;

    }


    @Override
    public List<TimeExtensionTdConversion> findAllData() {
        return null;
    }

    @Override
    public List<String> validate(TimeExtensionTdConversion data) {
        ArrayList<String> errors = new ArrayList<>();
        return errors;
    }

}
