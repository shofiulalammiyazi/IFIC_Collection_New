package com.csinfotechbd.retail.card.customerProfile;
/*
  Created by MR on 9/28/2021
*/

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface SmsPushPullRepository extends JpaRepository<SmsPushPull, Long> {

    List<SmsPushPull> findByContractNoAndMobileNumber(String contractNo, String mobileNo);

    List<SmsPushPull> findByAlertTypeAndContractNo(String alertType, String contractNo);

    List<SmsPushPull> findByDeliveryStatusAndContractNo(String deliveryStatus, String contractNo);

    List<SmsPushPull> findByFromDateGreaterThanEqualAndToDateLessThanEqualAndContractNo(Date fromDate, Date toDate, String contractNo);

    List<SmsPushPull> findByFromDateGreaterThanEqualAndToDateLessThanEqualAndAlertTypeAndDeliveryStatusAndMobileNumberAndContractNo(
            Date fromDate, Date toDate, String alertType, String deliveryStatus, String mobileNo, String contractNo);

    List<SmsPushPull> findByContractNo(String contractNo);
}
