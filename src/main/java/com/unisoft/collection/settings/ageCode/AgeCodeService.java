package com.unisoft.collection.settings.ageCode;
/*
Created by   Islam on 7/9/2019
*/

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgeCodeService {

    @Autowired
    private AgeCodeDao ageCodeDao;

    public List<AgeCodeEntity> getAll() {
        return ageCodeDao.getList();
    }

    public List<AgeCodeEntity> getActiveList() {
        return ageCodeDao.getActiveOnly();
    }

    public boolean saveAgeCode(AgeCodeEntity ageCode) {
        return ageCodeDao.saveNew(ageCode);
    }

    public boolean updateAgeCode(AgeCodeEntity ageCode) {
        return ageCodeDao.updateObj(ageCode);
    }

    public AgeCodeEntity getById(Long id) {
        return ageCodeDao.getById(id);
    }

}
