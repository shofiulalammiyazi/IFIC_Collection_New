package com.unisoft.customerloanprofile.vehicleRepossession;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepossessionFileRepository extends JpaRepository<VehicleRepossessionFile, Long> {
}
