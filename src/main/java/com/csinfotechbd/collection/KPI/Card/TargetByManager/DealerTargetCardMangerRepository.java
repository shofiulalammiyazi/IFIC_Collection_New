package com.csinfotechbd.collection.KPI.Card.TargetByManager;
/*
Created by Monirul Islam at 9/23/2019 
*/

import com.csinfotechbd.collection.settings.employee.EmployeeInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DealerTargetCardMangerRepository extends JpaRepository<DealerTargetCardManager, Long> {

    DealerTargetCardManager findFirstByEmployeeInfoEntityList(List<EmployeeInfoEntity> emplList);
}
