package com.csinfotechbd.collection.settings.securedCardCriteria;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SecuredCardCriteriaService {

    @Autowired
    private SecuredCardCriteriaDao securedCardCriteriaDao;

    public List<SecuredCardCriteriaEntity> getAll() {
        return securedCardCriteriaDao.getAll();
    }

    public SecuredCardCriteriaEntity getById(Long Id) {
        return securedCardCriteriaDao.getById(Id);
    }

    public boolean saveNew(SecuredCardCriteriaEntity securedCardCriteriaEntity) {
        return securedCardCriteriaDao.saveSecuredCard(securedCardCriteriaEntity);
    }

    public boolean updateSecured(SecuredCardCriteriaEntity securedCardCriteriaEntity) {
        return securedCardCriteriaDao.updateSecuredCard(securedCardCriteriaEntity);
    }

    public List<SecuredCardCriteriaEntity> getActiveList() {
        return securedCardCriteriaDao.getActiveList();
    }

    public SecuredCardCriteriaEntity getSecuredCardLatest() {
        return securedCardCriteriaDao.getSecuredCardLatest();
    }
}
