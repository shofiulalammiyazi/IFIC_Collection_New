package com.csinfotechbd.legal.setup.legalDivisionInfo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LegalDivisionInfoRepository extends JpaRepository<LegalDivisionInfo, Long> {
}
