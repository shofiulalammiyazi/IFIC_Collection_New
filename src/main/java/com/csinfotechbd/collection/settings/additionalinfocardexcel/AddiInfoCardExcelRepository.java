package com.csinfotechbd.collection.settings.additionalinfocardexcel;


import com.csinfotechbd.retail.card.dataEntry.additionalinfocard.AdditionalInfoCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface AddiInfoCardExcelRepository extends JpaRepository<AdditionalInfoCard, Long> {


List<AdditionalInfoCard> findByClientIdAndContractIdOrderByIdDesc(String clientId, String contractId);
}
