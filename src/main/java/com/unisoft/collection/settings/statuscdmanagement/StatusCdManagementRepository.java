package com.unisoft.collection.settings.statuscdmanagement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface StatusCdManagementRepository extends JpaRepository<StatusCd,Long> {
    List<StatusCd> findByEnabled(boolean a);
//    List<StatusCd> findByCreatedByOrderByCreatedDateDesc();

}
