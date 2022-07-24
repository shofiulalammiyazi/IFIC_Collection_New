package com.unisoft.collection.samd.setup.borrowerPresentStatus;

import java.util.List;

public interface BorrowerPresentStatusService {
    List<BorrowerPresentStatus> findAll();

    void save(BorrowerPresentStatus borrowerPresentStatus);

    BorrowerPresentStatus findBorrowerPresentStatusById(Long id);
}
