package com.unisoft.collection.settings.esau.card;
/*
  Created by Md.   Islam on 9/11/2019
*/

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ESAUCardService {

    @Autowired
    private ESAUCardDao esauCardDao;

    public List<ESAUCardEntity> getAll()
    {
        return esauCardDao.getList();
    }

    public ESAUCardEntity getById(Long id)
    {
        return esauCardDao.getById(id);
    }

    public boolean saveNew(ESAUCardEntity  esauLoanEntity)
    {
        return esauCardDao.saveNew(esauLoanEntity);
    }

    public boolean updateData(ESAUCardEntity esauLoanEntity)
    {
        return esauCardDao.updateObj(esauLoanEntity);
    }
}
