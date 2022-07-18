package com.csinfotechbd.collection.settings.esau.loan;
/*
  Created by Md. Monirul Islam on 9/9/2019
*/

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ESAULoanService {

    @Autowired
    private ESAULoanDao esauLoanDao;

    public List<ESAULoanEntity> getAll()
    {
        return esauLoanDao.getList();
    }

    public ESAULoanEntity getById(Long id)
    {
        return esauLoanDao.getById(id);
    }

    public boolean saveNew(ESAULoanEntity  esauLoanEntity)
    {
        return esauLoanDao.saveNew(esauLoanEntity);
    }

    public boolean updateData(ESAULoanEntity esauLoanEntity)
    {
        return esauLoanDao.updateObj(esauLoanEntity);
    }


}
