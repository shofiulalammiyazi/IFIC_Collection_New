package com.csinfotechbd.collection.distribution.emiDetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmiDetailsService {

    @Autowired
    EmiDetailsRepository emiDetailsRepository;

    public List<EmiDetails> getShownEmiDetails() {
        return emiDetailsRepository.findMyDistinct();
    }

    public EmiDetails save(EmiDetails emiDetails) {
        emiDetailsRepository.save(emiDetails);
        return emiDetails;
    }

    public List<EmiDetails> findByAcc(String accountNo) {
        List<EmiDetails> emiDetailsList = new ArrayList<>();
        EmiDetails byCardNo = emiDetailsRepository.findByCardNo(accountNo);
        emiDetailsList.add(byCardNo);
        //TODO: add sub card emi details also
        return emiDetailsList;
    }
}
