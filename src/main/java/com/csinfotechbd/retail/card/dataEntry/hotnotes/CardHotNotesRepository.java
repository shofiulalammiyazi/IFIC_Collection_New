package com.csinfotechbd.retail.card.dataEntry.hotnotes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardHotNotesRepository extends JpaRepository<CardHotNotes, Long> {
    List<CardHotNotes> findByCustomerBasicInfoIdOrderByCreatedDateDesc(Long customerBasicInfoId);
}
