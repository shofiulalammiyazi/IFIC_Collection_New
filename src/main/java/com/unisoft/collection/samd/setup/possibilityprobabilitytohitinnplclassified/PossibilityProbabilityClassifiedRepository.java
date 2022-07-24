package com.unisoft.collection.samd.setup.possibilityprobabilitytohitinnplclassified;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface PossibilityProbabilityClassifiedRepository extends JpaRepository<PossibilityProbabilityClassified,Long> {


}
