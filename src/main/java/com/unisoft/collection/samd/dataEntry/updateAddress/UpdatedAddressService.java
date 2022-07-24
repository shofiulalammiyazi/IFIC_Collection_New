package com.unisoft.collection.samd.dataEntry.updateAddress;

public interface UpdatedAddressService {
    UpdateAddress findUpdateAddressByCustomerId(String customerId);

    UpdateAddress save(UpdateAddress updateAddress);

    UpdateAddress findUpdateAddressById(Long id);
}
