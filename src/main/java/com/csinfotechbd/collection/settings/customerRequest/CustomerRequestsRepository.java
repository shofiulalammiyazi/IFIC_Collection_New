package com.csinfotechbd.collection.settings.customerRequest;

import com.csinfotechbd.customerbasicinfo.CustomerBasicInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.List;

@Repository
public interface CustomerRequestsRepository extends JpaRepository<CustomerRequestsEntity,Long> {

    List<CustomerRequestsEntity> findByCustomerBasicInfo(CustomerBasicInfoEntity customerBasicInfoEntity);
//    List<CustomerRequestsEntity> findBy(LoanAccountDistributionInfo loanAccountDistributionInfo);
    List<CustomerRequestsEntity> findByCustomerBasicInfoId(Long customerBasicInfoId);

    @Query(value = "SELECT * FROM customer_requests_entity  WHERE ID = ? ", nativeQuery = true)
    CustomerRequestsEntity findCustomerRequestsEntitiesById(Long id);

    @Query(value = "SELECT * FROM customer_requests_entity WHERE (DEALER_PIN = ? AND STATUS = ?) ", nativeQuery = true)
    List<CustomerRequestsEntity> findCustomerRequestsEntitiesByDealerPinAndStatus(String pin, String pending);

    @Query(value = "Select custreq.ID            as id, " +
            "       custreq.CUST_DATE     as custDate, " +
            "       custreq.CUST_TIME     as custtime, " +
            "       custreq.MOBILE_NO     as mobileNo, " +
            "       custreq.REQ_DETAILS   as reqdetails, " +
            "       custreq.REQ_THOUGH    as thought, " +
            "       custreq.REQ_TIME      as reqtime, " +
            "       custreq.STATUS        as status, " +
            "       custreq.DEALER_PIN    as dealerpin, " +
            "       reqfile.FILE_NAME     as filename, " +
            "       reqfile.DMS_FILE_ID   as dmsfileId, " +
            "       reqfile.DMS_FILE_TYPE as dmsFileType " +
            "from CUSTOMER_REQUESTS_ENTITY custreq " +
            "       left join CUSTOMER_REQUEST_FILE reqfile on reqfile.CUSTOMER_REQUESTS_ENTITY_ID = custreq.ID " +
            "where custreq.CUSTOMER_ID = ? ", nativeQuery = true)
    List<Tuple> findCustomerRequestByCustomerId(String customerId);




}
