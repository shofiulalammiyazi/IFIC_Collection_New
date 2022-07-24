package com.unisoft.retail.card.dataEntry.followup;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface CardFollowUpRepository extends JpaRepository<CardFollowUp,Long> {
    List<CardFollowUp> findByEnabled(boolean a);
    List<CardFollowUp> findByEnabledIs(boolean a);
    List<CardFollowUp> findByCustomerBasicInfoIdOrderByCreatedDateDesc(Long customerBasicInfoId);


}
