package com.unisoft.collection.customerComplain;

import com.unisoft.cryptography.CryptoException;
import com.unisoft.dms.DmsFileSaver;
import com.unisoft.user.UserPrincipal;
import org.apache.chemistry.opencmis.client.api.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Tuple;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerComplainServiceImpl implements CustomerComplainService {

    @Autowired
    private CustomerComplainRepository customerComplainRepository;

    @Autowired
    private DmsFileSaver dmsFileSaver;

    @Autowired
    private CustomerComplainFileRepository customerComplainFileRepository;

    @Override
    public CustomerComplainEntity saveComplain(CustomerComplainEntity customerComplainEntity) {
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        customerComplainEntity.setStatus(CustomerComplainStatus.PENDING);
        customerComplainEntity.setDealerPin(userPrincipal.getUsername());
        CustomerComplainEntity customerComplainEntity1 = customerComplainRepository.save(customerComplainEntity);

        if (customerComplainEntity.getFile() != null) {
            MultipartFile file = customerComplainEntity.getFile();
            String fileType = file.getContentType();

            try {
                String filePath = "customer_complains/" + customerComplainEntity.getCustomerId();
                String fileName = file.getOriginalFilename();
                Session session = dmsFileSaver.cmisSession();
                String dmsFileId = dmsFileSaver.saveFileToDmsFilePath(file, filePath, fileName, session);
                CustomerComplainFile complainFile = new CustomerComplainFile();
                complainFile.setFileName(fileName);
                complainFile.setDmsFileId(dmsFileId);
                complainFile.setDmsFileType(fileType);
                complainFile.setCustomerComplainEntityId(customerComplainEntity1.getId());
                customerComplainFileRepository.save(complainFile);

            } catch (IOException | CryptoException e) {
                System.err.println(e.getMessage());
            }
        }
        return customerComplainEntity1;
    }

    @Override
    public List<CustomerComplainEntity> getAllComplain() {
        return customerComplainRepository.findAll();
    }

    @Override
    public List<CustomerComplainDto> getComplainByCustomerId(Long customerId) {
        List<Tuple> customerComplainDtos = customerComplainRepository.getComplainByCustomerId(customerId);
        return getComplainDtos(customerComplainDtos);
    }

    private List<CustomerComplainDto> getComplainDtos(List<Tuple> customerComplainDtos) {
        List<CustomerComplainDto> complains = new ArrayList<>();
        for (Tuple tuple : customerComplainDtos) {
            CustomerComplainDto dto = new CustomerComplainDto();
            try {

                dto.setId(tuple.get("id"));
                dto.setCustDate(tuple.get("custDate"));
                dto.setCustTime(tuple.get("custtime"));
                dto.setMobileNo(tuple.get("mobileNo"));
                dto.setReqDetails(tuple.get("reqdetails"));
                dto.setReqThough(tuple.get("thought"));
                dto.setReqTime(tuple.get("reqtime"));
                dto.setStatus(tuple.get("status"));
                dto.setDealerPin(tuple.get("dealerpin"));
                dto.setFileName(tuple.get("filename"));
                dto.setDmsfileId(tuple.get("dmsfileId"));
                dto.setDmsFileType(tuple.get("dmsFileType"));
//                dto.setId(tuple.get("id"));
//                dto.setDealerName(tuple.get("dealerName"));
//                dto.setMobileNumber(tuple.get("mobileNumber"));
//                dto.setComplainDetails(tuple.get("complainDetails"));
//
//                dto.setCustomerId(tuple.get("customerId"));
//                dto.setFileName(tuple.get("fileName"));
//                dto.setDmsFileId(tuple.get("dmsFileId"));
//                dto.setDmsFileType(tuple.get("dmsFileType"));
//                dto.setCreatedBy(tuple.get("createdBy"));
//                dto.setStatus(tuple.get("status"));
//
//                dto.setAccountNo(tuple.get("accountNo"));
//                dto.setAccountName(tuple.get("accountName"));
//                dto.setClStatus(tuple.get("clStatus"));
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
            complains.add(dto);
        }
        return complains;
    }

    public CustomerComplainEntity findByCustomerId(Long customerId) {
        return customerComplainRepository.findByCustomerId(customerId);
    }

    public CustomerComplainEntity findByid(Long id) {
        return customerComplainRepository.findCustomerComplainEntityById(id);
    }

    public List<CustomerComplainDto> getCustomerCompainFileById(Long id){

        List<Tuple> customerComplainEntityFileById = customerComplainRepository.findCustomerComplainEntityFileById(id);

        return getComplainDtos(customerComplainEntityFileById);
    }

    public CustomerComplainEntity save(CustomerComplainEntity customerComplainEntity) {
        customerComplainRepository.save(customerComplainEntity);
        return customerComplainEntity;
    }


    public List<CustomerComplainDto> findCustomerComplainEntityByDealerPinBndStatus(String pin, String pending) {
        List<Tuple> customerComplainDtos = customerComplainRepository.findCustomerComplainEntityByAndDealerPinAndStatus(pin, pending);
        return getComplainDtos(customerComplainDtos);
    }

    @Override
    public List<CustomerComplainDto> getCustomerComplainEntityByDealerPinList(List<String> pin) {
        List<Tuple> customerComplainEntityByAndDealerPinList = customerComplainRepository.findCustomerComplainEntityByAndDealerPinList(pin);
        return getComplainDtos(customerComplainEntityByAndDealerPinList);
    }

    @Override
    public List<CustomerComplainViewModel> getCustomerComplainByDealerPinList(List<String> pin) {

        if (!pin.isEmpty()){
            List<CustomerComplainViewModel> customerComplainViewModels = new ArrayList<>();
            List<Tuple> customerRequestsEntitiesByDealerPinAndStatus = customerComplainRepository.findCustomerRequestsEntitiesByDealerPin(pin);
            for(Tuple t : customerRequestsEntitiesByDealerPinAndStatus){
                CustomerComplainViewModel customerComplainViewModel = new CustomerComplainViewModel(t);
                customerComplainViewModels.add(customerComplainViewModel);
            }
            return customerComplainViewModels;

        }

        return  new ArrayList<>();

    }

    @Override
    public List<CustomerComplainEntity> getCustomerComplainList(Long custId) {
        return customerComplainRepository.findCustomerComplainEntitiesByCustomerId(custId);
    }

    @Override
    public String getDmsFileUrl(String dmsFileId) {
        return dmsFileSaver.getDocumentURL(dmsFileId);
    }

    @Override
    public CustomerComplainFile findCustomerComplainFileByDmsFileId(String dmsFileId) {
        return customerComplainFileRepository.findCustomerComplainFileByDmsFileId(dmsFileId);
    }
}
