package com.unisoft.collection.automaticDistribution.postInterim;
/*
Created by   Islam at 9/14/2019
*/

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostInterimConditionRepository extends JpaRepository<PostInterimCondition, Long> {
    PostInterimCondition findFirstByOrderByIdDesc();
}
