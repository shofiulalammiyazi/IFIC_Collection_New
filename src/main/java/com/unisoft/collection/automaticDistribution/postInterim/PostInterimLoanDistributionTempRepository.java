package com.unisoft.collection.automaticDistribution.postInterim;
/*
Created by   Islam at 9/17/2019
*/

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostInterimLoanDistributionTempRepository extends JpaRepository<PostInterimLoanDistributionTemp, Long> {
    List<PostInterimLoanDistributionTemp> findAllByOrderByIdDesc();

}
