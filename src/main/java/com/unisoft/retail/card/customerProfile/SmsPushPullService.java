package com.unisoft.retail.card.customerProfile;
/*
  Created by MR on 9/28/2021
*/

import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SmsPushPullService {

    private SmsPushPullRepository pushPullRepo;

    SmsPushPullService(SmsPushPullRepository pushPullRepo) {
        this.pushPullRepo = pushPullRepo;
    }

    List<SmsPushPull> findByContractNoOrMobileNo(String contractNo, String mobileNo) {
        return pushPullRepo.findByContractNoAndMobileNumber(contractNo, mobileNo);
    }

    List<SmsPushPull> findAll() {
        return pushPullRepo.findAll();
    }

    List<SmsPushPull> findByDateRange(Date fromDate, Date toDate, String contractNo) {
        return pushPullRepo.findByFromDateGreaterThanEqualAndToDateLessThanEqualAndContractNo(fromDate, toDate, contractNo);
    }

    List<SmsPushPull> findByAlertType(String alertType, String contractNo) {
        return pushPullRepo.findByAlertTypeAndContractNo(alertType, contractNo);
    }

    List<SmsPushPull> findByDeliveryStatus(String deliveryStatus, String contractNo) {
        return pushPullRepo.findByDeliveryStatusAndContractNo(deliveryStatus, contractNo);
    }

    List<SmsPushPull> findByFilters(Date fromDate, Date toDate, String alertType, String deliveryStatus, String mobileNo, String contractNo) {
        return pushPullRepo.findByFromDateGreaterThanEqualAndToDateLessThanEqualAndAlertTypeAndDeliveryStatusAndMobileNumberAndContractNo
                (fromDate, toDate, alertType, deliveryStatus, mobileNo, contractNo);
    }

    List<SmsPushPull> findByContractNo(String contractNo) {
        return pushPullRepo.findByContractNo(contractNo);
    }

}
