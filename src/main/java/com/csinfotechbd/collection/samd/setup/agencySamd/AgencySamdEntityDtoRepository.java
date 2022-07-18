package com.csinfotechbd.collection.samd.setup.agencySamd;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgencySamdEntityDtoRepository extends JpaRepository<AgencySamdEntity,Long> {
}
