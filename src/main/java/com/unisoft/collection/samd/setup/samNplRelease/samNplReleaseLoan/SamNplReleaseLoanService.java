package com.unisoft.collection.samd.setup.samNplRelease.samNplReleaseLoan;

import java.util.List;

public interface SamNplReleaseLoanService {
    List<SamNplReleaseLoan> findAll();
    SamNplReleaseLoan save(SamNplReleaseLoan samNplReleaseLoan);

    SamNplReleaseLoan findSamNplReleaseLoanById(Long id);
}
