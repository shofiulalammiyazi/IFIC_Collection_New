package com.csinfotechbd.collection.samd.setup.capableInfluenceCustomerBankDue;



import java.util.List;

public interface CapableInfluenceCustomerBankDueService {

    List<CapableInfluenceCustomerBankDue> findAll();

    void save(CapableInfluenceCustomerBankDue capableInfluenceCustomerBankDue);

    CapableInfluenceCustomerBankDue findCapableInfluenceCustomerBankDueById(Long id);
}
