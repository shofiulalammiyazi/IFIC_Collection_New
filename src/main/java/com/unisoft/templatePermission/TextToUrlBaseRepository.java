package com.unisoft.templatePermission;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TextToUrlBaseRepository extends JpaRepository<TextToUrlBase, Long> {
}
