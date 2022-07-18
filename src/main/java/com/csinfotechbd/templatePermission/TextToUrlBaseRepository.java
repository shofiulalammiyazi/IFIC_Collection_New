package com.csinfotechbd.templatePermission;
/*
Created by Monirul Islam at 9/29/2019 
*/

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TextToUrlBaseRepository extends JpaRepository<TextToUrlBase, Long> {
}
