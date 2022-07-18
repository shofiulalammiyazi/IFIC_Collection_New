package com.csinfotechbd.retail.card.dataEntry.dairynotes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DairyNotesService {
    @Autowired
    DairyNotesRepository repository;

    public List<DairyNotes> list() {
        return repository.findByEnabledIs(true);
    }

    public List<DairyNotes> findByCustomerId(Long customerId) {
        return repository.findByCustomerBasicInfoId(customerId);
    }

    public DairyNotes findbyId(Long id) {
        return repository.getOne(id);
    }

    public DairyNotes save(DairyNotes dairyNotes) {
        repository.save(dairyNotes);
        return dairyNotes;
    }

    public boolean delete(Long id) {
        repository.deleteById(id);
        return true;
    }


}
