package com.unisoft.collection.letterInformation;

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
public class LetterInformationsService implements CommonService<LetterInformations> {

    @Autowired
    private LetterInformationsRepository letterInformationsRepository;

    @Autowired
    private AuditTrailService auditTrailService;

    @Autowired
    private DmsFileSaver dmsFileSaver;

    @Autowired
    private LetterInformationsFileRepository letterInformationsFileRepository;

    @Override
    public Map<String, Object> storeData(LetterInformations data) {
        Map<String, Object> response = new HashMap<>();
        List<String> errorMessages = this.validate(data);

        if (errorMessages.size() == 0) {
            boolean isNewEntity = true;
            LetterInformations oldEntity = new LetterInformations();
            if (data.getId() != null) {
                LetterInformations entity = letterInformationsRepository.getOne(data.getId());
                BeanUtils.copyProperties(entity, oldEntity);

                isNewEntity = false;
            }

            letterInformationsRepository.save(data);
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
    public Map<String, Object> updateData(LetterInformations data) {
        return null;
    }

    @Override
    public Map<String, Object> deleteData(LetterInformations data) {
        return null;
    }

    @Override
    public LetterInformations findDataById(Long id) {
        return null;
    }

    @Override
    public List<LetterInformations> findAllData() {
        return null;
    }

    public List<LetterInformationsDto> getAllDataByCustomerId(String customerId) {
        List<Tuple>tupleList =  letterInformationsRepository.findAllByCustomerId(customerId);
        return tupleList.stream().map(s -> new LetterInformationsDto(s)).collect(Collectors.toList());
    }

    @Override
    public List<String> validate(LetterInformations data) {
        return new ArrayList<>();
    }


    public LetterInformations saveData(LetterInformations letterInformations, MultipartFile file){
        LetterInformations letterInformations1 =letterInformationsRepository.save(letterInformations);
        if (file != null && file.getSize() > 0){

            String fileType = file.getContentType();

            try {
                String filePath = "letterInformations/" + letterInformations1.getId();
                String fileName = file.getOriginalFilename();
                Session session = dmsFileSaver.cmisSession();
                String dmsFileId = dmsFileSaver.saveFileToDmsFilePath(file, filePath, fileName, session);
                LetterInformationsFile letterInformationsFile = new LetterInformationsFile();
                letterInformationsFile.setFileName(fileName);
                letterInformationsFile.setDmsFileId(dmsFileId);
                letterInformationsFile.setDmsFileType(fileType);
                letterInformationsFile.setLetterInformations(letterInformations1);
                letterInformationsFileRepository.save(letterInformationsFile);

            } catch (IOException | CryptoException e) {
                System.err.println(e.getMessage());
            }
        }

        return letterInformations1;
    }
}
