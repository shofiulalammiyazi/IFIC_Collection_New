package com.csinfotechbd.collection.samd.dataEntry.updateAddress;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdatedAddressServiceImpl implements UpdatedAddressService{


    @Autowired
    private UpdatedAddressRepository updatedAddressRepository;


    @Override
    public UpdateAddress findUpdateAddressByCustomerId(String customerId) {
        return updatedAddressRepository.findUpdateAddressByCustomerId(customerId);
    }

    @Override
    public UpdateAddress save(UpdateAddress updateAddress) {
        UpdateAddress updateAddress1 = updatedAddressRepository.save(updateAddress);
        return updateAddress1;
    }

    @Override
    public UpdateAddress findUpdateAddressById(Long id) {
        return updatedAddressRepository.findUpdateAddressById(id);
    }
}
