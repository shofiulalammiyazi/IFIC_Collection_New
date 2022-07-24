package com.unisoft.collection.samd.setup.borrowerstayingat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface BorrowerStayingRepository extends JpaRepository<BorrowerStaying,Long> {


}
