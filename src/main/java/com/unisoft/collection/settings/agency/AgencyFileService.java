package com.unisoft.collection.settings.agency;

import com.unisoft.cryptography.CryptoException;
import com.unisoft.dms.DmsFileSaver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.apache.chemistry.opencmis.client.api.Session;

import java.io.IOException;

@Service
public class AgencyFileService {


    @Autowired
    private DmsFileSaver dmsFileSaver;

    @Autowired
    private AgencyFileRepository agencyFileRepository;


    public void save(AgencyEntity entity) {

        if (entity.getFile() != null) {

            MultipartFile file = entity.getFile();
            String originalFileName = file.getOriginalFilename();
            String fileType = file.getContentType();

            try {
                String filePath = "agency-file/";
                Session session = dmsFileSaver.cmisSession();
                String dmsFileId = dmsFileSaver.saveFileToDmsFilePath(file, filePath, originalFileName, session);
                AgencyFile agencyFile = new AgencyFile();
                agencyFile.setFileName(originalFileName);
                agencyFile.setDmsFileId(dmsFileId);
                agencyFile.setDmsFileType(fileType);
                agencyFile.setAgencyEntity(entity);
                agencyFileRepository.save(agencyFile);

            } catch (IOException | CryptoException e) {
                System.err.println(e.getMessage());
            }
        }
    }
}
