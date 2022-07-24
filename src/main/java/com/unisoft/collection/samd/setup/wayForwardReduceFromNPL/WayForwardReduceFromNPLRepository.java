package com.unisoft.collection.samd.setup.wayForwardReduceFromNPL;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WayForwardReduceFromNPLRepository extends JpaRepository<WayForwardReduceFromNPL, Long> {


    WayForwardReduceFromNPL findWayForwardReduceFromNPLById(Long id);
}
