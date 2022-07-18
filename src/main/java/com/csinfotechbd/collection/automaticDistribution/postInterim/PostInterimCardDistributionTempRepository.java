package com.csinfotechbd.collection.automaticDistribution.postInterim;
/*
Created by Monirul Islam at 9/15/2019 
*/

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostInterimCardDistributionTempRepository extends JpaRepository<PostInterimCardDistributionTemp, Long> {
    List<PostInterimCardDistributionTemp> findAllByOrderByIdDesc();
}
