package com.unisoft.collection.settings.atm;



import java.util.List;

public interface AtmService {
    
    List<AtmEntity> getList();

    String save(AtmEntity unit);

    AtmEntity getById(Long id);

    List<AtmEntity> getActiveList();
}
