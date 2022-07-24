package com.unisoft.collection.samd.setup.bBAuditType;


import java.util.List;

public interface BBAuditTypeService {

    List<BBAuditType> findAll();

    void save(BBAuditType bbAuditType);

    BBAuditType findBBAuditTypeById(Long id);
}
