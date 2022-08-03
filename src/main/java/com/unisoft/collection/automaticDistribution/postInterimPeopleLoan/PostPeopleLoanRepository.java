package com.unisoft.collection.automaticDistribution.postInterimPeopleLoan;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostPeopleLoanRepository extends JpaRepository<PostPeopleLoan, Long> {
    PostPeopleLoan findFirstByOrderByIdDesc();
}
