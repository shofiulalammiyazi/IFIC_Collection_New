package com.csinfotechbd.customerloanprofile.vehicleRepossession;


import org.springframework.stereotype.Service;

import java.util.List;

public interface VehicleRepossessionService {
    List<VehicleRepossessionDto> findVehicleRepossessionByCustomerId(String customerId);

    VehicleRepossession save(VehicleRepossession vehicleRepossession);

    VehicleRepossession findVehicleRepossessionById(Long id);
}
