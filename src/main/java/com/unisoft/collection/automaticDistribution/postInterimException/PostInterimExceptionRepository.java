package com.unisoft.collection.automaticDistribution.postInterimException;
/*
Created by   Islam at 8/21/2019
*/

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostInterimExceptionRepository extends JpaRepository<PostInterimException, Long> {
    PostInterimException findFirstByOrderByIdDesc();
}
