package com.unisoft.collection.settings.district;
/*
Created by   Islam at 7/3/2019
*/


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DistrictService {

    @Autowired
    private DistrictDao districtDao;

    @Autowired
    private DistrictRepository districtRepository;

    public DistrictEntity geByDistrickId(Long id){
        return districtRepository.getOne(id);
    }

    public List<DistrictEntity> getAll() {
        return districtDao.getList();
    }

    public List<DistrictEntity> getActiveOnly() {
        return districtDao.getActiveOnly();
    }

    public boolean saveNew(DistrictEntity district) {
        return districtDao.saveNew(district);
    }

    public boolean updateDis(DistrictEntity district) {
        return districtDao.updateObj(district);
    }

    public DistrictEntity getById(Long id) {
        return districtDao.getById(id);
    }

    public DistrictEntity getByName(String name) {
        return districtDao.getByName(name);
    }

    public List getByDivision(Long divisionId) {
        return districtDao.getByDivision(divisionId);
    }
}
