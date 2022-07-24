package com.unisoft.collection.samd.setup.hrPosition;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HrPositionRepository extends JpaRepository<HrPosition, Long> {
}
