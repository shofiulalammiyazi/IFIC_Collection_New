package com.unisoft.customerloanprofile.vehicleRepossession;


import java.util.List;

public interface VehicleRepossessionService {
    List<VehicleRepossessionDto> findVehicleRepossessionByCustomerId(String customerId);

    VehicleRepossession save(VehicleRepossession vehicleRepossession);

    VehicleRepossession findVehicleRepossessionById(Long id);
}
