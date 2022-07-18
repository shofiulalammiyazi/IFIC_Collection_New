package com.csinfotechbd.collection.settings.vipStatus;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VipStatusRepository extends JpaRepository<VipStatus, Long> {

    List<VipStatus> findByEnabled(boolean enabled);

    @Query("select distinct name from VipStatus where enabled = true")
    List<String> findVipStatusNames();

    boolean existsByName(String name);

    VipStatus findByName(String name);

}
