package com.unisoft.retail.card.dataEntry.cardreferenceinfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardReferenceInfoService {
    @Autowired
    CardReferenceInfoRepository repository;

    public List<CardReferenceInfo> list(){
        return repository.findByEnabledIs(true);
    }
    public CardReferenceInfo findbyId( Long id){
        return repository.findById(id).get();
    }

    public CardReferenceInfo save(CardReferenceInfo cardReferenceInfo){
        repository.save(cardReferenceInfo);
        return cardReferenceInfo;
    };

    public boolean delete(Long id){
        repository.deleteById(id);
        return true;
    }




}
