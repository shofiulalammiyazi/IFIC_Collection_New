package com.csinfotechbd.retail.card.dataEntry.distribution.accountBasicInfo;
/*
Created by Monirul Islam at 7/21/2019
*/

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import java.util.List;

@Service
public class CardAccountBasicService {
    @Autowired
    private CardAccountBasicDao cardAccountBasicDao ;

    @Autowired
    private CardAccountBasicRepository cardAccountBasicRepository;

    public List<CardAccountBasicInfo> getAll()
    {
        return cardAccountBasicDao.getList();
    }

    public boolean saveNew(CardAccountBasicInfo agency)
    {
        return cardAccountBasicDao.saveNew(agency);
    }

    public boolean updateAgency(CardAccountBasicInfo agency)
    {
        return cardAccountBasicDao.updateObj(agency);
    }

    public CardAccountBasicInfo getById(Long Id)
    {
        return cardAccountBasicDao.getById(Id);
    }

    public CardAccountBasicInfo getByAccountNo(String Id)
    {
        return cardAccountBasicDao.findByAccountNo(Id);
    }

    public List<CardAccountBasicInfo> getActiveList()
    {
        return cardAccountBasicDao.getActiveOnly();
    }

    public CardAccountBasicInfo getByContractId(String contractId)
    {
        return cardAccountBasicDao.getByContractId(contractId);
    }

    public CardAccountBasicInfo getByClientId(String clientId)
    {
        return cardAccountBasicDao.getByClientId(clientId);
    }

    public List<CardAccountBasicInfo> findAllByClientId(String clientId) {
        return cardAccountBasicRepository.findAllByClientId(clientId);
    }



//    public CardInfoDto getCardInfoForLegal(String clientId) {
//        Tuple tuple = cardAccountBasicRepository.getCardDataForLegal(clientId);
//        CardInfoDto cardInfoDto = new CardInfoDto();
//        cardInfoDto.setCustomerAccName(tuple.get("customerAccName"));
//        cardInfoDto.setCardNumber(tuple.get("cardNumber"));
//        cardInfoDto.setBranchName(tuple.get("branchName"));
//        return cardInfoDto;
//    }

    public List<CardAccountBasicInfo> findAllByContractId(String accountNo) {
        return cardAccountBasicRepository.findAllByContractId(accountNo);
    }

    public CardAccountBasicInfo getByContractIdAndClientId(String contractNo, String clientId) {
        return cardAccountBasicRepository.findByContractIdAndClientId(contractNo,clientId);
    }


    public List<String> getAllContractId(){
        return cardAccountBasicRepository.findAllContractId();
    }
}
