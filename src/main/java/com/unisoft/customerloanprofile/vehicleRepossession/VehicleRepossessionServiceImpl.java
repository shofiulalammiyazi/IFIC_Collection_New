package com.unisoft.customerloanprofile.vehicleRepossession;

import com.unisoft.cryptography.CryptoException;
import com.unisoft.dms.DmsFileSaver;
import org.apache.chemistry.opencmis.client.api.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Tuple;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class VehicleRepossessionServiceImpl implements VehicleRepossessionService{

    @Autowired
    private VehicleRepossessionRepository vehicleRepossessionRepository;

    @Autowired
    private DmsFileSaver dmsFileSaver;

    @Autowired
    private VehicleRepossessionFileRepository vehicleRepossessionFileRepository;

    @Override
    public List<VehicleRepossessionDto> findVehicleRepossessionByCustomerId(String customerId) {
        List<Tuple>tupleList =  vehicleRepossessionRepository.findVehicleRepossessionByCustomerId(customerId);
        List<VehicleRepossessionDto> vehicleRepossessionDtoList = new ArrayList<>();
        for (Tuple tuple: tupleList){
            VehicleRepossessionDto vehicleRepossessionDto = new VehicleRepossessionDto();
            vehicleRepossessionDto.setId(tuple.get("id"));
            vehicleRepossessionDto.setVehicleType(tuple.get("vehicleType"));
            vehicleRepossessionDto.setVehicleModel(tuple.get("vehicleModel"));
            vehicleRepossessionDto.setVehicleRegistrationNo(tuple.get("vehicleRegistrationNo"));
            vehicleRepossessionDto.setPresentStatus(tuple.get("presentStatus"));
            vehicleRepossessionDto.setFileName(tuple.get("fileName"));
            vehicleRepossessionDto.setDmsFileId(tuple.get("dmsFileId"));
            vehicleRepossessionDto.setDmsFileType(tuple.get("dmsFileType"));
            vehicleRepossessionDtoList.add(vehicleRepossessionDto);
        }
        return vehicleRepossessionDtoList;
    }

    @Override
    public VehicleRepossession save(VehicleRepossession vehicleRepossession) {
        VehicleRepossession vehicleRepossession1 = vehicleRepossessionRepository.save(vehicleRepossession);


        if (vehicleRepossession.getFile() != null) {

            MultipartFile file = vehicleRepossession.getFile();
            String fileType = file.getContentType();

            try {
                String filePath = "vehicle-repossession/" + vehicleRepossession.getCustomerId();
                String fileName = file.getOriginalFilename();
                Session session = dmsFileSaver.cmisSession();
                String dmsFileId = dmsFileSaver.saveFileToDmsFilePath(file, filePath, fileName, session);
                VehicleRepossessionFile vehicleRepossessionFile = new VehicleRepossessionFile();
                vehicleRepossessionFile.setFileName(fileName);
                vehicleRepossessionFile.setDmsFileId(dmsFileId);
                vehicleRepossessionFile.setDmsFileType(fileType);
                vehicleRepossessionFile.setVehicleRepossession(vehicleRepossession1);
                vehicleRepossessionFileRepository.save(vehicleRepossessionFile);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (CryptoException e) {
                e.printStackTrace();
            }
        }
        
        return vehicleRepossession1;
    }

    @Override
    public VehicleRepossession findVehicleRepossessionById(Long id) {
        return vehicleRepossessionRepository.findVehicleRepossessionById(id);
    }
}
