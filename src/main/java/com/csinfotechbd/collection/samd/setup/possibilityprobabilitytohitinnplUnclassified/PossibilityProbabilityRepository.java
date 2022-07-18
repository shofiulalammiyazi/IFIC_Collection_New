package com.csinfotechbd.collection.samd.setup.possibilityprobabilitytohitinnplUnclassified;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface PossibilityProbabilityRepository extends JpaRepository<PossibilityProbability,Long> {


}
