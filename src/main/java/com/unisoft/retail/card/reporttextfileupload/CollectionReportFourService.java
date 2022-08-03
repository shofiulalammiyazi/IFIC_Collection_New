package com.unisoft.retail.card.reporttextfileupload;

import com.unisoft.utillity.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CollectionReportFourService {
   @Autowired
   DateUtils dateUtils;
    @Autowired
    private CollectionReportFourRepository collectionReportFourRepository;

    public CollectionReportFour getfdrInfoCollectionReportFour(String contractId ){
        Date startDate=dateUtils.getMonthStartDate();
        Date endDate=dateUtils.getMonthEndDate();
        String stringStartDate=dateUtils.dateStringFormat(startDate);
        String stringEndDate=dateUtils.dateStringFormat(endDate);
        return collectionReportFourRepository.findByContractNoAndCreatedDate(contractId,stringStartDate,stringEndDate);
    }


}
