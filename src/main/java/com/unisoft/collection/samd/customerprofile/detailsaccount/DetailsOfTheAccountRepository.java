package com.unisoft.collection.samd.customerprofile.detailsaccount;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetailsOfTheAccountRepository extends JpaRepository<DetailsOfTheAccount,Long> {

    DetailsOfTheAccount findTopByLoanAccountNoOrderByIdDesc(String accountNo);
}
