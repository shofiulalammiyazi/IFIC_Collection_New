package com.unisoft.collection.samd.setup.writtenOffManagement;

public interface WrittenOffManagementService {
    WrittenOffManagement save(WrittenOffManagement writtenOffManagement);

    WrittenOffManagement findWrittenOffManagementByCustomerId(String customerId);
}
