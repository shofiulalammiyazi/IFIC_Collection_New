package com.unisoft.collection.distribution.writeOff;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/*

 */
@Service
public class WriteOffAccountService {

    @Autowired
    private WriteOffAccountDao writeOffAccountDao;

    public List<WriteOffAccountInfo> getAll() {
        return writeOffAccountDao.getList();
    }

    public boolean saveNew(WriteOffAccountInfo agency) {
        return writeOffAccountDao.saveNew(agency);
    }

    public boolean updateAgency(WriteOffAccountInfo agency) {
        return writeOffAccountDao.updateObj(agency);
    }

    public WriteOffAccountInfo getById(Long Id) {
        return writeOffAccountDao.getById(Id);
    }

    public List<WriteOffAccountInfo> getActiveList() {
        return writeOffAccountDao.getActiveOnly();
    }
}
