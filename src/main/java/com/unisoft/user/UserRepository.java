package com.unisoft.user;
/*
Created by   Islam at 9/30/2019
*/

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "UPDATE LOS_TB_M_USERS SET PASSWORD=?1 where USERNAME=?2 ", nativeQuery = true)
    User saveUserPassword(String password, String username);

    List<User> findByEnabled(boolean enabled);

    User findUserByUsername(String username);

}
