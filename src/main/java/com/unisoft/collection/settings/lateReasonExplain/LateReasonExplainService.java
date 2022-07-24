package com.unisoft.collection.settings.lateReasonExplain;
/*
Created by   Islam at 7/22/2019
*/

import com.unisoft.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class LateReasonExplainService {
    @Autowired
    private LateReasonExplainDao lateReasonExplainDao;

    @Autowired
    private LateReasonExplainRepository lateReasonExplainRepository;

    public List<LateReasonExplainInfo> getAll()
    {
        return lateReasonExplainDao.getList();
    }

    public boolean saveNew(LateReasonExplainInfo agency)
    {
        return lateReasonExplainDao.saveNew(agency);
    }

    public boolean updateAgency(LateReasonExplainInfo agency)
    {
        return lateReasonExplainDao.updateObj(agency);
    }

    public LateReasonExplainInfo getById(Long Id)
    {
        return lateReasonExplainDao.getById(Id);
    }

    public List<LateReasonExplainInfo> getActiveList()
    {
        return lateReasonExplainDao.getActiveOnly();
    }

    public List<LateReasonExplainInfo> findByUserId(Long userId) {
        return lateReasonExplainRepository.findByUserIdAndDate(userId);
    }

    public List<LateReasonExplainInfo>findByUserIdInCurrentMonth(Date startDate , Date endDate, User user){

        return lateReasonExplainRepository.findByCreatedDateIsBetweenAndUserOrderByCreatedDateDesc(startDate,endDate,user);
    }
}
