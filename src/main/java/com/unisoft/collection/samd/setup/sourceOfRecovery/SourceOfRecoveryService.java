package com.unisoft.collection.samd.setup.sourceOfRecovery;

import java.util.List;

public interface SourceOfRecoveryService {
    List<SourceOfRecovery> findAll();

    void save(SourceOfRecovery sourceOfRecovery);

    SourceOfRecovery findSourceOfRecoveryById(Long id);
}
