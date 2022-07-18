package com.csinfotechbd.logging;

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
public class LoggerRepository {

	@Autowired
	private EntityManager entityManager;

	public void save(Log log) {
		entityManager.persist(log);
	}

	public List<Log> findAllLogByRefId(long refId) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Log> criteria = builder.createQuery(Log.class);
		Root<Log> root = criteria.from(Log.class);
		criteria.where(builder.equal(root.get("refNo"), refId));
		return entityManager.createQuery(criteria).getResultList();
	}

}
