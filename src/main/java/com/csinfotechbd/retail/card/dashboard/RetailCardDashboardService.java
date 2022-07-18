package com.csinfotechbd.retail.card.dashboard;


import com.csinfotechbd.collection.dashboard.AccountWiseSummaryModel;
import com.csinfotechbd.collection.dashboard.AdvanceSearchPayload;
import com.csinfotechbd.retail.card.dashboard.model.AdvanceSearchDataModelCard;
import com.csinfotechbd.retail.card.dataEntry.followup.CardFollowUp;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Yasir Araphat
 * Created at 01 April, 2021
 */

@Service
@RequiredArgsConstructor
public class RetailCardDashboardService {

    private final RetailCardDashboardDao dashboardDao;
    private final RetailCardDashBoardRepository retailCardDashBoardRepository;

    public List<CardFollowUp> getCardFollowUpByCusBasicInfo(Long cusId, String userid) {
        return dashboardDao.getCardFollowUpByCusBasicInfo(cusId, userid);
    }

    public List<AdvanceSearchDataModelCard> getAdvanceSearchData(AdvanceSearchPayload payload){
        List<Tuple> advanceSearchDataList = retailCardDashBoardRepository.getAdvancedSearchData(
                payload.getAccountNo(),
                payload.getCustomerName(),
                payload.getMotherName(),
                payload.getMobile(),
                payload.getNationalId(),
                payload.getDateOfBirth().toUpperCase(),
                payload.getEmail(),
                payload.getPassport(),
                payload.getTin(),
                payload.getClientId(),
                payload.getContractId()
                
        );
        
        List<AdvanceSearchDataModelCard> searchDataList =advanceSearchDataList.stream().map(AdvanceSearchDataModelCard::new).collect(Collectors.toList());
        return searchDataList;
    }
}
