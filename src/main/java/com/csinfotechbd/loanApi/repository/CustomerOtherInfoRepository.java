package com.csinfotechbd.loanApi.repository;

import com.csinfotechbd.common.CommonRepository;
import com.csinfotechbd.loanApi.model.CustomerAndBorrowerInfo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerOtherInfoRepository extends CommonRepository<CustomerAndBorrowerInfo> {

    CustomerAndBorrowerInfo findByCustomerId(Long customerId);


    @Query(value = "SELECT * FROM CUSTOMER_AND_BORROWER_INFO WHERE (CUSTOMER_ID=? AND STATUS = ?) ", nativeQuery = true)
    List<CustomerAndBorrowerInfo> findCustomerAndBorrowerInfoByCustomerIdAndStatus(String customerId, String pending);


    @Query(value = "SELECT * FROM CUSTOMER_AND_BORROWER_INFO WHERE (DEALER_PIN=? AND STATUS = ?) ", nativeQuery = true)
    List<CustomerAndBorrowerInfo> findCustomerAndBorrowerInfoByDealerPinBndStatus(String pin, String pending);
}
