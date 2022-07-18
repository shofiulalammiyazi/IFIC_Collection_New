package com.csinfotechbd.collection.samd.setup.capableInfluenceCustomerBankDue;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CapableInfluenceCustomerBankDueServiceImpl implements CapableInfluenceCustomerBankDueService{


    @Autowired
    private CapableInfluenceCustomerBankDueRepository capableInfluenceCustomerBankDueRepository;


    @Override
    public List<CapableInfluenceCustomerBankDue> findAll() {
        return capableInfluenceCustomerBankDueRepository.findAll();
    }

    @Override
    public void save(CapableInfluenceCustomerBankDue capableInfluenceCustomerBankDue) {
            capableInfluenceCustomerBankDueRepository.save(capableInfluenceCustomerBankDue);
    }

    @Override
    public CapableInfluenceCustomerBankDue findCapableInfluenceCustomerBankDueById(Long id) {
        return capableInfluenceCustomerBankDueRepository.findCapableInfluenceCustomerBankDueById(id);
    }
}
