package com.csinfotechbd.collection.dblink;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DbLinkDataRepository extends JpaRepository<DbLinkData, String> {
}
