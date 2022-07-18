package com.csinfotechbd.retail.loan.setup.nplReleaseLoan;
/*
 * Created by Yasir Araphat on 25 April, 2021
 */

import com.csinfotechbd.common.CommonRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NplReleaseLoanRepository extends CommonRepository<NplReleaseLoan> {

    NplReleaseLoan findNplReleaseLoanById(Long id);

}
