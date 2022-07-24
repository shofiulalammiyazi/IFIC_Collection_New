package com.unisoft.collection.settings.customerRequest;

import com.unisoft.collection.distribution.loan.LoanAccountDistributionRepository;
import com.unisoft.cryptography.CryptoException;
import com.unisoft.customerbasicinfo.CustomerBasicInfoEntity;
import com.unisoft.dms.DmsFileSaver;
import com.unisoft.loanApi.service.RetailLoanUcbApiService;
import com.unisoft.user.UserPrincipal;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class CustomerRequestsService {

    private final CustomerRequestsRepository repository;

    @Autowired
    private DmsFileSaver dmsFileSaver;

    @Autowired
    private CustomerRequestFileRepository customerRequestFileRepository;
    @Autowired
    private RetailLoanUcbApiService apiService;
    @Autowired
    private LoanAccountDistributionRepository loanAccountDistributionRepository;


    public List<CustomerRequestsEntity> getCustomerReqList(){
        return repository.findAll();
    }

    public List<CustomerRequestsEntity> findByCustomer(CustomerBasicInfoEntity customerBasicInfoEntity){
        return repository.findByCustomerBasicInfo(customerBasicInfoEntity);
    }

    public List<CustomerRequestsEntity> findByCustomerId(Long customerId){
        return repository.findByCustomerBasicInfoId(customerId);
    }

    public boolean saveCustomerReqInfo(CustomerRequestsEntity customerRequestsEntity){
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        CustomerRequestsEntity customerRequestsEntity1= repository.save(customerRequestsEntity);

//
//        String accountNo = customerRequestsEntity.getCustomerBasicInfo().getAccountNo();
//
//        LoanAccDetails loanAccDetails = apiService.getLoanAccountDetails(accountNo);
//
//        customerRequestsEntity.setOutstanding(loanAccDetails.getOutStandingLocalCurrency());
//        customerRequestsEntity.setOverdue(loanAccDetails.getOverdue());
//        customerRequestsEntity.setBucket(loanAccDetails.getDpdBucket());

        customerRequestsEntity.setStatus(CustomerRequestsStatus.PENDING);
        customerRequestsEntity.setDealerPin(userPrincipal.getUsername());



        if(customerRequestsEntity.getFile() !=null){
            MultipartFile file = customerRequestsEntity.getFile();
            String fileType = file.getContentType();

            try {
                String filePath = "customer_request/" + customerRequestsEntity.getCustomerBasicInfo();
                String fileName = file.getOriginalFilename();
                Session session = dmsFileSaver.cmisSession();
                String dmsFileId = dmsFileSaver.saveFileToDmsFilePath(file, filePath, fileName, session);
                CustomerRequestFile requestFile = new CustomerRequestFile();
                requestFile.setFileName(fileName);
                requestFile.setDmsFileId(dmsFileId);
                requestFile.setDmsFileType(fileType);
                requestFile.setCustomerRequestEntityId(customerRequestsEntity.getId());
                customerRequestFileRepository.save(requestFile);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (CryptoException e) {
                e.printStackTrace();
            }
        }
//        return repository.save(customerRequestsEntity).getId() != null;
        return false;
    }

    public CustomerRequestsEntity findByid(Long id) {
        return repository.findCustomerRequestsEntitiesById(id);
    }

    public List<CustomerRequestsEntity> findCustomerRequestsEntityByDealerPinBndStatus(String pin, String pending) {
        return repository.findCustomerRequestsEntitiesByDealerPinAndStatus(pin, pending);
    }


    public void save(CustomerRequestsEntity customerRequestsEntity) {
        repository.save(customerRequestsEntity);
    }

    public String getDmsFileUrl(String dmsFileId) {
        return dmsFileSaver.getDocumentURL(dmsFileId);
    }

    public CustomerRequestFile findCustomerRequestFileByDmsFileId(String dmsFileId) {
        CustomerRequestFile customerComplainFile = customerRequestFileRepository.findCustomerRequestFileByDmsFileId(dmsFileId);
        return customerComplainFile;
    }

    public List<CustomerRequestDto> findCustomerRequestByCustomerId(String customerId) {
        List<Tuple> tupleList = repository.findCustomerRequestByCustomerId(customerId);
        List<CustomerRequestDto> customerRequestDtoList = new ArrayList<>();
        for (Tuple tuple:tupleList ){

            CustomerRequestDto dto = new CustomerRequestDto();
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
            customerRequestDtoList.add(dto);
        }
        return customerRequestDtoList;
    }
}
