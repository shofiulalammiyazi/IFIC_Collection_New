package com.unisoft.collection.settings.productGroup;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductGroupService {

    @Autowired
    private ProductGroupDao productGroupDao;


    public List<ProductGroupEntity> getList() {
        return productGroupDao.getList();
    }

    public ProductGroupEntity getById(Long Id) {
        return productGroupDao.getById(Id);
    }

    public boolean saveGrp(ProductGroupEntity productGroup) {
        return productGroupDao.saveGroup(productGroup);
    }

    public boolean updateGrp(ProductGroupEntity productGroup) {
        return productGroupDao.updateGrp(productGroup);
    }

    public List<ProductGroupEntity> getAllActive() {
        return productGroupDao.getActiveOnly();
    }
}
