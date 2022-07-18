package com.csinfotechbd.collection.samd.setup.whethertheloanistetd;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface WhetherTheLoanTeTdRcRsRepository extends JpaRepository<WhetherTheLoanTeTdRcRs,Long> {


}
