package com.csinfotechbd.retail.card.reporttextfileupload;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AgingHistoryRepository extends JpaRepository<AgingHistory, Long> {

    List<AgingHistory> findAllByContractNo(String contractNo);
}
