package com.csinfotechbd.legal.setup.lawyers;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LawyerRepository extends JpaRepository<Lawyer, Long> {

    List<Lawyer> findByEnabled(boolean b);

    List<Lawyer> findByIdIn(List<Long> idList);

    Lawyer findByPhoneNo(String phoneNo);

    @Query("SELECT l FROM Lawyer l WHERE l.enabled = true AND (l.phoneNo LIKE CONCAT('%',:contact,'%')  OR l.mobileNo LIKE CONCAT('%',:contact,'%'))")
    List<Lawyer> findByContactNo(@Param("contact") String contact);

    @Query("SELECT l FROM Lawyer l " +
            "WHERE l.enabled = true AND (l.phoneNo LIKE CONCAT('%',:contact,'%')  OR l.mobileNo LIKE CONCAT('%',:contact,'%') OR LOWER(l.name) LIKE LOWER(CONCAT('%', :name, '%') ) )")
    List<Lawyer> findByContactOrNameLike(@Param("contact") String contact, @Param("name") String name);

    /*@Query(value = "SELECT l.* FROM Lawyer l " +
            " WHERE l.enabled = 1 AND (l.PHONE_NO = :contact " +
            "                               OR l.MOBILE_NO = :contact " +
            "                               OR LOWER(l.NAME) = LOWER(:name) )" ,nativeQuery = true)
    List<Lawyer> findByContactOrNameLike(@Param("contact") String*/

    @Query("SELECT MAX(l.lawyerId) FROM Lawyer l")
    Long findMaxLawyerId();

    boolean existsByPhoneNo(String phoneNo);

    boolean existsByLawyerId(String lawyerId);

    boolean existsByEmail(String email);

    boolean existsByMobileNo(String mobileNo);
}
