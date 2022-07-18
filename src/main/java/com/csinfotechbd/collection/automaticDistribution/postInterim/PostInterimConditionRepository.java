package com.csinfotechbd.collection.automaticDistribution.postInterim;
/*
Created by Monirul Islam at 9/14/2019 
*/

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostInterimConditionRepository extends JpaRepository<PostInterimCondition, Long> {
    PostInterimCondition findFirstByOrderByIdDesc();
}
