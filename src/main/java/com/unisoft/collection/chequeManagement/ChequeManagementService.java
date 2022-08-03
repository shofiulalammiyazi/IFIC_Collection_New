package com.unisoft.collection.chequeManagement;

import com.unisoft.audittrail.AuditTrailService;
import com.unisoft.common.CommonService;
import com.unisoft.cryptography.CryptoException;
import com.unisoft.dms.DmsFileSaver;
import org.apache.chemistry.opencmis.client.api.Session;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Tuple;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ChequeManagementService implements CommonService<ChequeManagement> {

    @Autowired
    private ChequeManagementRepository chequeManagementRepository;

    @Autowired
    private AuditTrailService auditTrailService;

    @Autowired
    private DmsFileSaver dmsFileSaver;

    @Autowired
    private ChequeManagementFileRepository chequeManagementFileRepository;

    @Override
    public Map<String, Object> storeData(ChequeManagement data) {
        Map<String, Object> response = new HashMap<>();
        List<String> errorMessages = this.validate(data);

        if (errorMessages.size() == 0) {
            boolean isNewEntity = true;
            ChequeManagement oldEntity = new ChequeManagement();
            if (data.getId() != null) {
                ChequeManagement entity = chequeManagementRepository.getOne(data.getId());
                BeanUtils.copyProperties(entity, oldEntity);

                isNewEntity = false;
            }

            chequeManagementRepository.save(data);
            if (isNewEntity)
                auditTrailService.saveCreatedData("Cheque Management", data);
            else
                auditTrailService.saveUpdatedData("Cheque Management", oldEntity, data);
            response.put("outcome", "success");
        } else {
            response.put("outcome", "failure");
            response.put("errors", errorMessages);
        }
        return response;
    }

    @Override
    public Map<String, Object> updateData(ChequeManagement data) {
        return null;
    }

    @Override
    public Map<String, Object> deleteData(ChequeManagement data) {
        return null;
    }

    @Override
    public ChequeManagement findDataById(Long id) {
        return null;
    }

    @Override
    public List<ChequeManagement> findAllData() {
        return null;
    }

    public List<ChequeManagementDto> getAllDataByCustomerId(String customerId) {
        List<Tuple>tupleList =  chequeManagementRepository.findAllByCustomerId(customerId);
        return tupleList.stream().map(s -> new ChequeManagementDto(s)).collect(Collectors.toList());
    }

    @Override
    public List<String> validate(ChequeManagement data) {
        return new ArrayList<>();
    }


    public ChequeManagement saveData(ChequeManagement chequeManagement, MultipartFile file){
        ChequeManagement chequeManagement1 = chequeManagementRepository.save(chequeManagement);
        if (file != null && file.getSize() > 0){

            String fileType = file.getContentType();

            try {
                String filePath = "instrumentInformation/" + chequeManagement1.getId();
                String fileName = file.getOriginalFilename();
                Session session = dmsFileSaver.cmisSession();
                String dmsFileId = dmsFileSaver.saveFileToDmsFilePath(file, filePath, fileName, session);
                ChequeManagementFile chequeManagementFile = new ChequeManagementFile();
                chequeManagementFile.setFileName(fileName);
                chequeManagementFile.setDmsFileId(dmsFileId);
                chequeManagementFile.setDmsFileType(fileType);
                chequeManagementFile.setChequeManagement(chequeManagement1);
                chequeManagementFileRepository.save(chequeManagementFile);

            } catch (IOException | CryptoException e) {
                System.err.println(e.getMessage());
            }
        }

        return chequeManagement1;
    }
}
