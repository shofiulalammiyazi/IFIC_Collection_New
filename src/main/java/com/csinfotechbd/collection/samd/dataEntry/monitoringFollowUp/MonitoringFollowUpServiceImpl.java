package com.csinfotechbd.collection.samd.dataEntry.monitoringFollowUp;

import com.csinfotechbd.cryptography.CryptoException;
import com.csinfotechbd.customerbasicinfo.CustomerBasicInfoEntityRepository;
import com.csinfotechbd.dms.DmsFileSaver;
import org.apache.chemistry.opencmis.client.api.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Tuple;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class MonitoringFollowUpServiceImpl implements MonitoringFollowUpService{


    @Autowired
    private MonitoringFollowUpRepository monitoringFollowUpRepository;
    @Autowired
    private CustomerBasicInfoEntityRepository customerBasicInfoEntityRepository;
    @Autowired
    private DmsFileSaver dmsFileSaver;
    @Autowired
    private MonitoringFollowUpFileRepository monitoringFollowUpFileRepository;


    @Override
    public MonitoringAndFollowUp saveMonitoringFollowUp(MonitoringAndFollowUp monitoringFollowUp, MultipartFile file) {
        MonitoringAndFollowUp monitoringFollowUp1 = monitoringFollowUpRepository.save(monitoringFollowUp);

        if (file != null && file.getSize() >0){

            try {
                String filePath = "monitoringAndFollowUp/"+monitoringFollowUp1.getId();
                String fileName = file.getOriginalFilename();
                String fileType = file.getContentType();
                Session session = dmsFileSaver.cmisSession();
                String dmsFileId = dmsFileSaver.saveFileToDmsFilePath(file, filePath, fileName, session);

                MonitoringFollowUpFile monitoringFollowUpFile = new MonitoringFollowUpFile();
                monitoringFollowUpFile.setDmsFileId(dmsFileId);
                monitoringFollowUpFile.setDmsFileType(fileType);
                monitoringFollowUpFile.setFileName(fileName);
                monitoringFollowUpFile.setMonitoringFollowUp(monitoringFollowUp1);

                monitoringFollowUpFileRepository.save(monitoringFollowUpFile);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (CryptoException e) {
                e.printStackTrace();
            }
        }
        return monitoringFollowUp1;
    }

    @Override
    public MonitoringAndFollowUp findMonitoringFollowUpById(Long id) {
        return monitoringFollowUpRepository.findMonitoringAndFollowUpById(id);
    }

    @Override
    public List<MonitoringAndFollowUpDto> findMonitoringFollowUpByCustomerId(String customerId) {
        List<Tuple>  tupleList= monitoringFollowUpRepository.findMonitoringAndFollowUpByCustomerId(customerId);
        List<MonitoringAndFollowUpDto> monitoringFollowUpList = new ArrayList<>();
        for (Tuple tuple: tupleList){
            MonitoringAndFollowUpDto monitoringAndFollowUpDto = new MonitoringAndFollowUpDto();
            monitoringAndFollowUpDto.setMonitoringId(tuple.get("monitoringId"));
            monitoringAndFollowUpDto.setBorrowerNature(tuple.get("borrowerNature"));
            monitoringAndFollowUpDto.setNoTeTdRCRs(tuple.get("noTeTdRCRs"));
            monitoringAndFollowUpDto.setProReCurrentYear(tuple.get("proReCurrentYear"));
            monitoringAndFollowUpDto.setDmsFileId(tuple.get("dmsFileId"));
            monitoringAndFollowUpDto.setDmsFileType(tuple.get("dmsFileType"));
            monitoringAndFollowUpDto.setFileName(tuple.get("fileName"));
            monitoringFollowUpList.add(monitoringAndFollowUpDto);
        }

        return monitoringFollowUpList;
    }
}
