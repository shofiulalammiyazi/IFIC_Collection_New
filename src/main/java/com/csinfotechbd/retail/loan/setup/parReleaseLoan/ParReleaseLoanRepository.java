package com.csinfotechbd.retail.loan.setup.parReleaseLoan;
/*
 * Created by Yasir Araphat on 25 April, 2021
 */

import com.csinfotechbd.common.CommonRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParReleaseLoanRepository extends CommonRepository<ParReleaseLoan> {
    List<ParReleaseLoan> findByEnabled(boolean enabled);

    ParReleaseLoan findParReleaseLoanById(Long id);

}
