package com.unisoft.retail.card.dataEntry.dailyNotes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DailyNotesRepository extends JpaRepository<DailyNotesCard, Long> {
    List<DailyNotesCard> findByCustomerBasicInfoIdOrderByCreatedDateDesc(Long customerBasicInfoId);
}
