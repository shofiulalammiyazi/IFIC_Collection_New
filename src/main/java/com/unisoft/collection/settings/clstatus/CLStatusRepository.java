package com.unisoft.collection.settings.clstatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface CLStatusRepository extends JpaRepository<CLStatus, Long> {

    List<CLStatus> findByEnabled(boolean enabled);
}
