package com.csinfotechbd.collection.samd.setup.agencySamd;


import com.csinfotechbd.cryptography.CryptoException;
import com.csinfotechbd.dms.DmsFileSaver;
import org.apache.chemistry.opencmis.client.api.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class AgencySamdFileService {


    @Autowired
    private DmsFileSaver dmsFileSaver;
    @Autowired
    private AgencySamdFileRepository agencySamdFileRepository;


    public void save(AgencySamdEntity entity) {

        if (entity.getFile() != null) {

            MultipartFile file = entity.getFile();
            String originalFileName = file.getOriginalFilename();
            String fileType = file.getContentType();

            try {
                String filePath = "agencySamd-file/";
                Session session = dmsFileSaver.cmisSession();
                String dmsFileId = dmsFileSaver.saveFileToDmsFilePath(file, filePath, originalFileName, session);
                AgencySamdFile agencySamdFile = new AgencySamdFile();
                agencySamdFile.setFileName(originalFileName);
                agencySamdFile.setDmsFileId(dmsFileId);
                agencySamdFile.setDmsFileType(fileType);
                agencySamdFile.setAgencySamdEntity(entity);
                agencySamdFileRepository.save(agencySamdFile);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (CryptoException e) {
                e.printStackTrace();
            }
        }
    }

}
