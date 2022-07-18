package com.csinfotechbd.collection.KPI.esau;
/*
Created by Monirul Islam at 9/23/2019 
*/

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EsauDefaultRatingSetupRepository extends JpaRepository<EsauDefaultRatingSetup, Long> {
    EsauDefaultRatingSetup findFirstByOrderByIdDesc();
}
