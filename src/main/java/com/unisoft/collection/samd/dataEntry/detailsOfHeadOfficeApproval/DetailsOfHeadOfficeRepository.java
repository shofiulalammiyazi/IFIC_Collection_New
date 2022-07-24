package com.unisoft.collection.samd.dataEntry.detailsOfHeadOfficeApproval;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetailsOfHeadOfficeRepository extends JpaRepository<DetailsOfHeadOfficeApproval,Long > {

    List<DetailsOfHeadOfficeApproval> findAllByLoanAccountNo(String accountNo);

}
