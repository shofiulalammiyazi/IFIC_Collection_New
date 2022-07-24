package com.unisoft.loanApi.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerAndBorrowerRepo extends JpaRepository<CustomerAndBorrowerInfo,Long> {
    
    CustomerAndBorrowerInfo findByCustomerId(Long customerId);
}
