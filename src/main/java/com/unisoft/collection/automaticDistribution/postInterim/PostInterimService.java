package com.unisoft.collection.automaticDistribution.postInterim;
/*
Created by   Islam at 8/20/2019
*/

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostInterimService {

    @Autowired
    private PostInterimDao postInterimDao;

    public List<PostInterimCondition> getAll() {
        return postInterimDao.getList();
    }

    public boolean saveNew(PostInterimCondition agency) {
        return postInterimDao.saveNew(agency);
    }

    public boolean updateAgency(PostInterimCondition agency) {
        return postInterimDao.updateObj(agency);
    }

    public PostInterimCondition getById(Long Id) {
        return postInterimDao.getById(Id);
    }

    public List<PostInterimCondition> getActiveList() {
        return postInterimDao.getActiveOnly();
    }
}
