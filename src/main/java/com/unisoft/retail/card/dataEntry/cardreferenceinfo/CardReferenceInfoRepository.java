package com.unisoft.retail.card.dataEntry.cardreferenceinfo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
@Repository
@Transactional
public interface CardReferenceInfoRepository extends JpaRepository<CardReferenceInfo,Long> {
    List<CardReferenceInfo> findByEnabled(boolean a);
    List<CardReferenceInfo> findByEnabledIs(boolean a);


}
