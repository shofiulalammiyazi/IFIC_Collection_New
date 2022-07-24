package com.unisoft.collection.customerComplain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.List;


@Repository
public interface CustomerComplainRepository extends JpaRepository<CustomerComplainEntity, Long> {

    //    @Query(value = "SELECT CCE.ID      as id, " +
//            "       CCE.DEALER_NAME      as dealerName, " +
//            "       CCE.CUSTOMER_ID      as customerId, " +
//            "       CCE.MOBILE_NUMBER    as mobileNumber, " +
//            "       CCE.COMPLAIN_DETAILS as complainDetails, " +
//            "       CCF.FILE_NAME        as fileName, " +
//            "       CCF.DMS_FILE_ID      as dmsFileId, " +
//            "       CCE.CREATED_BY       as createdBy, " +
//            "       CCF.DMS_FILE_TYPE    as   dmsFileType, " +
//            "       CCE.STATUS           as status," +
//            "       CBE.ACCOUNT_NO       as accountNo, " +
//            "       CBE.CUSTOMER_NAME    as accountName, " +
//            "       CBE.CL_STATUS       as clStatus " +
//            "FROM CUSTOMER_COMPLAIN_ENTITY CCE " +
//            "       LEFT JOIN CUSTOMER_COMPLAIN_FILE CCF ON CCF.CUSTOMER_COMPLAIN_ENTITY_ID = CCE.ID " +
//            "       LEFT JOIN CUSTOMER_BASIC_INFO_ENTITY CBE ON CBE.ID = CCE.CUSTOMER_ID " +
//            "WHERE CCE.CUSTOMER_ID = ?", nativeQuery = true)
    @Query(value = "SELECT CCE.ID      as id,  " +
            "                   CCE.CUST_DATE     as custDate, " +
            "                   CCE.CUST_TIME     as custtime,  " +
            "                   CCE.MOBILE_NO     as mobileNo,  " +
            "                   CCE.REQ_DETAILS   as reqdetails,  " +
            "                   CCE.REQ_THOUGH    as thought,  " +
            "                   CCE.REQ_TIME      as reqtime,  " +
            "                   CCE.STATUS        as status,  " +
            "                   CCE.DEALER_PIN    as dealerpin, " +
            "                   CCF.FILE_NAME     as filename, " +
            "                   CCF.DMS_FILE_ID   as dmsfileId,  " +
            "                   CCF.DMS_FILE_TYPE as dmsFileType " +
            "            FROM CUSTOMER_COMPLAIN_ENTITY CCE  " +
            "                   LEFT JOIN CUSTOMER_COMPLAIN_FILE CCF ON CCF.CUSTOMER_COMPLAIN_ENTITY_ID = CCE.ID  " +
            "            WHERE CCE.CUSTOMER_ID = ?", nativeQuery = true)
    List<Tuple> getComplainByCustomerId(Long customerId);

    //List<CustomerComplainEntity> findByCustomerBasicInfo(CustomerBasicInfoEntity customerBasicInfoEntity);

    CustomerComplainEntity findByCustomerId(Long customerId);


    @Query(value = "SELECT * FROM CUSTOMER_COMPLAIN_ENTITY WHERE (CUSTOMER_ID=? AND STATUS = ?) ", nativeQuery = true)
    List<CustomerComplainEntity> findCustomerComplainEntityByCustomerIdAndStatus(String customerId, String pending);

    @Query(value = "SELECT * FROM CUSTOMER_COMPLAIN_ENTITY  WHERE ID = ? ", nativeQuery = true)
    CustomerComplainEntity findCustomerComplainEntityById(Long id);

    @Query(value = "SELECT CCE.ID      as id,  " +
            "                   CCE.CUST_DATE     as custDate, " +
            "                   CCE.CUST_TIME     as custtime,  " +
            "                   CCE.MOBILE_NO     as mobileNo,  " +
            "                   CCE.REQ_DETAILS   as reqdetails,  " +
            "                   CCE.REQ_THOUGH    as thought,  " +
            "                   CCE.REQ_TIME      as reqtime,  " +
            "                   CCE.STATUS        as status,  " +
            "                   CCE.DEALER_PIN    as dealerpin, " +
            "                   CCF.FILE_NAME     as filename, " +
            "                   CCF.DMS_FILE_ID   as dmsfileId,  " +
            "                   CCF.DMS_FILE_TYPE as dmsFileType " +
            "            FROM CUSTOMER_COMPLAIN_ENTITY CCE  " +
            "                   LEFT JOIN CUSTOMER_COMPLAIN_FILE CCF ON CCF.CUSTOMER_COMPLAIN_ENTITY_ID = CCE.ID  " +
            "           WHERE (DEALER_PIN=? AND STATUS = ?)", nativeQuery = true)
    List<Tuple> findCustomerComplainEntityByAndDealerPinAndStatus(String pin, String pending);

    @Query(value = "SELECT CCE.ID      as id,  " +
            "                   CCE.CUST_DATE     as custDate, " +
            "                   CCE.CUST_TIME     as custtime,  " +
            "                   CCE.MOBILE_NO     as mobileNo,  " +
            "                   CCE.REQ_DETAILS   as reqdetails,  " +
            "                   CCE.REQ_THOUGH    as thought,  " +
            "                   CCE.REQ_TIME      as reqtime,  " +
            "                   CCE.STATUS        as status,  " +
            "                   CCE.DEALER_PIN    as dealerpin, " +
            "                   CCF.FILE_NAME     as filename, " +
            "                   CCF.DMS_FILE_ID   as dmsfileId,  " +
            "                   CCF.DMS_FILE_TYPE as dmsFileType " +
            "            FROM CUSTOMER_COMPLAIN_ENTITY CCE  " +
            "                   LEFT JOIN CUSTOMER_COMPLAIN_FILE CCF ON CCF.CUSTOMER_COMPLAIN_ENTITY_ID = CCE.ID  " +
            "           WHERE DEALER_PIN IN (?1)", nativeQuery = true)
    List<Tuple> findCustomerComplainEntityByAndDealerPinList(List<String> pin);


    @Query(value = "SELECT CCE.*,CBIE.ACCOUNT_NO, CBIE.CUSTOMER_NAME,CBIE.CL_STATUS " +
            "         FROM CUSTOMER_COMPLAIN_ENTITY CCE " +
            "         LEFT JOIN CUSTOMER_BASIC_INFO_ENTITY CBIE ON CBIE.ID = CCE.CUSTOMER_ID " +
            "        WHERE CCE.DEALER_PIN IN :pinList ORDER BY CCE.CREATED_DATE DESC ", nativeQuery = true)
    List<Tuple> findCustomerRequestsEntitiesByDealerPin(@Param("pinList") List<String> pinList);

//    @Query(value = "SELECT CCE.ID      as id, " +
//            "       CCE.DEALER_NAME      as dealerName, " +
//            "       CCE.CUSTOMER_ID      as customerId, " +
//            "       CCE.MOBILE_NUMBER    as mobileNumber, " +
//            "       CCE.COMPLAIN_DETAILS as complainDetails, " +
//            "       CCF.FILE_NAME        as fileName, " +
//            "       CCF.DMS_FILE_ID      as dmsFileId, " +
//            "       CCE.CREATED_BY       as createdBy, " +
//            "       CCF.DMS_FILE_TYPE    as   dmsFileType, " +
//            "       CCE.STATUS           as status," +
//            "       CBE.ACCOUNT_NO       as accountNo, " +
//            "       CBE.CUSTOMER_NAME    as accountName, " +
//            "       CBE.CL_STATUS       as clStatus " +
//            "FROM CUSTOMER_COMPLAIN_ENTITY CCE " +
//            "       LEFT JOIN CUSTOMER_COMPLAIN_FILE CCF ON CCF.CUSTOMER_COMPLAIN_ENTITY_ID = CCE.ID " +
//            "       LEFT JOIN CUSTOMER_BASIC_INFO_ENTITY CBE ON CBE.ID = CCE.CUSTOMER_ID " +
//            "WHERE (DEALER_PIN=? AND STATUS = ?) ", nativeQuery = true)
//    List<Tuple> findCustomerComplainEntityByAndDealerPinAndStatus(String pin, String pending);


    List<CustomerComplainEntity> findCustomerComplainEntitiesByCustomerId(Long customerId);

    @Query(value = "SELECT CCE.ID      as id, " +
            "   CCE.CUST_DATE     as custDate," +
            "   CCE.CUST_TIME     as custtime, " +
            "   CCE.MOBILE_NO     as mobileNo, " +
            "   CCE.REQ_DETAILS   as reqdetails, " +
            "   CCE.REQ_THOUGH    as thought, " +
            "   CCE.REQ_TIME      as reqtime, " +
            "   CCE.STATUS        as status, " +
            "   CCE.DEALER_PIN    as dealerpin," +
            "   CCF.FILE_NAME     as filename," +
            "   CCF.DMS_FILE_ID   as dmsfileId, " +
            "   CCF.DMS_FILE_TYPE as dmsFileType" +
            "   FROM CUSTOMER_COMPLAIN_ENTITY CCE   " +
            "  LEFT JOIN CUSTOMER_COMPLAIN_FILE CCF ON CCE.ID = CCF.CUSTOMER_COMPLAIN_ENTITY_ID " +
            " WHERE CCE.ID = ? ", nativeQuery = true)
    List<Tuple> findCustomerComplainEntityFileById(Long id);
}