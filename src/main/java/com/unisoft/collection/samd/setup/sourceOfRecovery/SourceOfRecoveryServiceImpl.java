package com.unisoft.collection.samd.setup.sourceOfRecovery;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SourceOfRecoveryServiceImpl implements SourceOfRecoveryService{

    @Autowired
    private SourceOfRecoveryRepository sourceOfRecoveryRepository;



    @Override
    public List<SourceOfRecovery> findAll() {
        return sourceOfRecoveryRepository.findAll();
    }

    @Override
    public void save(SourceOfRecovery sourceOfRecovery) {
        sourceOfRecoveryRepository.save(sourceOfRecovery);
    }

    @Override
    public SourceOfRecovery findSourceOfRecoveryById(Long id) {
        return sourceOfRecoveryRepository.findSourceOfRecoveryById(id);
    }
}
