package com.unisoft.collection.samd.setup.borrowerPresentStatus;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BorrowerPresentStatusServiceImpl implements BorrowerPresentStatusService{


    @Autowired
    private BorrowerPresentStatusRepository borrowerPresentStatusRepository;


    @Override
    public List<BorrowerPresentStatus> findAll() {
        return borrowerPresentStatusRepository.findAll();
    }

    @Override
    public void save(BorrowerPresentStatus borrowerPresentStatus) {
        borrowerPresentStatusRepository.save(borrowerPresentStatus);
    }

    @Override
    public BorrowerPresentStatus findBorrowerPresentStatusById(Long id) {
        return borrowerPresentStatusRepository.findBorrowerPresentStatusById(id);
    }
}
