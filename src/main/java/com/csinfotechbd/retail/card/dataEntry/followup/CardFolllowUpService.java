package com.csinfotechbd.retail.card.dataEntry.followup;

import com.csinfotechbd.retail.card.dataEntry.dairynotes.DairyNotes;
import com.csinfotechbd.utillity.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CardFolllowUpService {
    @Autowired
    CardFollowUpRepository repository;

    private final DateUtils dateUtils;

    public List<CardFollowUp> list() {
        return repository.findByEnabledIs(true);
    }

    public List<CardFollowUp> findByCustomerId(Long customerId) {
        return repository.findByCustomerBasicInfoIdOrderByCreatedDateDesc(customerId);
    }

    public CardFollowUp findbyId(Long id) {
        return repository.findById(id).get();
    }

    public CardFollowUp save(CardFollowUp cardFollowUp) {
        String dateString = cardFollowUp.getTemdates().concat("")
                .concat(cardFollowUp.getFollowUpTimes().equals("&quot;") ? "" : " "+cardFollowUp.getFollowUpTimes());
        Date followUpDate = dateUtils.getFormattedDate(dateString, "dd-MM-yyyy");
        cardFollowUp.setFollowUpDate(followUpDate);

        repository.save(cardFollowUp);
        return cardFollowUp;
    }

    ;

    public boolean delete(Long id) {
        repository.deleteById(id);
        return true;
    }


}
