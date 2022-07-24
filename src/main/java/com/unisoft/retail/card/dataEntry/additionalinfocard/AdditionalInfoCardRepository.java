package com.unisoft.retail.card.dataEntry.additionalinfocard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdditionalInfoCardRepository extends JpaRepository<AdditionalInfoCard,Long> {

//    @Query(value = "SELECT * FROM ADDITIONAL_INFO_CARD where CONTRACT_ID=? and CLIENT_ID=? ORDER  BY ID DESC", nativeQuery = true)
//    List<AdditionalInfoCard> findAdditionalInfoByAccountNo(String accountNo);

    List<AdditionalInfoCard> findByClientIdAndContractIdOrderByIdDesc(String clientId,String contractId);
//
//    @Query(value = "SELECT * FROM ADDITIONAL_INFO_CARD WHERE (CONTRACT_ID=? AND STATUS=?)",nativeQuery = true)
//    List<AdditionalInfoCard> findAdditionalInfoByCustomerIdAndStatus(String customerId, String pending);
//
//    @Query(value = "SELECT * FROM ADDITIONAL_INFO_CARD WHERE (DEALER_PIN=? AND STATUS=?)", nativeQuery = true)
//    List<AdditionalInfoCard> findAdditionalInfoByDealerPinAndStatus(String pin, String pending);
//
//    AdditionalInfoCard findTopByAccountNoOrderByIdDesc(String accountNo);

}
