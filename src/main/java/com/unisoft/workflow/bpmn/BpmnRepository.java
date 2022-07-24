package com.unisoft.workflow.bpmn;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class BpmnRepository {

    @Autowired
    private EntityManager manager;

    public void save(BpmnStatus bs) {
        manager.persist(bs);
    }

    public void update(BpmnStatus bs) {
        manager.merge(bs);
    }

    public BpmnStatus findOne(int id) {
        return manager.find(BpmnStatus.class, id);
    }

    public List<BpmnStatus> findAll() {
        CriteriaBuilder cb = manager.getCriteriaBuilder();
        CriteriaQuery<BpmnStatus> cq = cb.createQuery(BpmnStatus.class);
        Root<BpmnStatus> root = cq.from(BpmnStatus.class);
        List<BpmnStatus> bs = manager.createQuery(cq).getResultList();
        return bs;
    }

}
