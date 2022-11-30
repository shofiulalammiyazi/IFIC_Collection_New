package com.unisoft.collection.settings.ivrSetupAndConfig;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IVRRepository extends JpaRepository<IvrEntity, Long> {
}
