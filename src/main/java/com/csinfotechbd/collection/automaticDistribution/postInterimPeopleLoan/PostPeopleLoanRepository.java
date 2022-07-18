package com.csinfotechbd.collection.automaticDistribution.postInterimPeopleLoan;
/*
Created by Monirul Islam at 8/21/2019 
*/

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostPeopleLoanRepository extends JpaRepository<PostPeopleLoan, Long> {
    PostPeopleLoan findFirstByOrderByIdDesc();
}
