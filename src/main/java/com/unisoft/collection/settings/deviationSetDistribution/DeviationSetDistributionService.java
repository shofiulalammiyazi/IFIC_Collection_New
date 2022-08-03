package com.unisoft.collection.settings.deviationSetDistribution;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviationSetDistributionService {
    @Autowired
    private DeviationSetDistributionDao deviationSetDistributionDao;

    public List<DeviationSetDistributionInfo> deviationSetDistributionInfoList()
    {
        return deviationSetDistributionDao.getList();
    }

    public  boolean saveDeviationSetDistribution(DeviationSetDistributionInfo deviationSetDistributionInfo)
    {
        return  deviationSetDistributionDao.save(deviationSetDistributionInfo);
    }

    public DeviationSetDistributionInfo getById(Long id)
    {
        return deviationSetDistributionDao.getById(id);
    }

    public boolean updateDeviationSetDistribution(DeviationSetDistributionInfo deviationSetDistributionInfo)
    {
        return deviationSetDistributionDao.update(deviationSetDistributionInfo);
    }

    public List<DeviationSetDistributionInfo> getActiveList()
    {
        return  deviationSetDistributionDao.getActiveOnly();
    }
}
