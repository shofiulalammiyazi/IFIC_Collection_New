package com.unisoft.collection.samd.setup.dpdSamd;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface DpdSamdEntityRepo extends JpaRepository<DpdSamdEntity,Long> {
    
    List<DpdSamdEntity> findByEnabledOrderByDpdNameAsc(boolean enabled);
    DpdSamdEntity findByDpdName(String dpdName);
    boolean existsByDpdName(String dpdName);
}
