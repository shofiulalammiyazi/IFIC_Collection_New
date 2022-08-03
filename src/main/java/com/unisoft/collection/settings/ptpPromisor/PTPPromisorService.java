package com.unisoft.collection.settings.ptpPromisor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PTPPromisorService {

    @Autowired
    private PTPPromisorDao ptpPromisorDao;

    public List<PTPPromisorEntity> getAll()
    {
        return ptpPromisorDao.getAll();
    }

    public PTPPromisorEntity getById(Long Id)
    {
        return ptpPromisorDao.getById(Id);
    }

    public boolean savePtpPro(PTPPromisorEntity promisorEntity)
    {
        return ptpPromisorDao.savePTPPro(promisorEntity);
    }

    public boolean updatePromisor(PTPPromisorEntity promisorEntity)
    {
        return ptpPromisorDao.updatePtpPro(promisorEntity);
    }


    public List<PTPPromisorEntity> getActiveList()
    {
        return ptpPromisorDao.getActiveList();
    }
}
