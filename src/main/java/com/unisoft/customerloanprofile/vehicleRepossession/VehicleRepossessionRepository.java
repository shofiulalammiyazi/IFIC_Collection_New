package com.unisoft.customerloanprofile.vehicleRepossession;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.List;

@Repository
public interface VehicleRepossessionRepository extends JpaRepository<VehicleRepossession, Long> {

    @Query(value = "SELECT VR.ID                      as id, " +
            "       VR.VEHICLE_TYPE            as vehicleType, " +
            "       VR.VEHICLE_MODEL           as vehicleModel, " +
            "       VR.VEHICLE_REGISTRATION_NO as vehicleRegistrationNo, " +
            "       VR.PRESENT_STATUS          as presentStatus, " +
            "       VRF.FILE_NAME              as fileName, " +
            "       VRF.DMS_FILE_ID            as dmsFileId, " +
            "       VRF.DMS_FILE_TYPE          as dmsFileType " +
            "FROM VEHICLE_REPOSSESSION VR " +
            "            LEFT JOIN VEHICLE_REPOSSESSION_FILE VRF on VR.ID = VRF.VEHICLE_REPOSSESSION_ID " +
            "WHERE VR.CUSTOMER_ID = ? ", nativeQuery = true)
    List<Tuple> findVehicleRepossessionByCustomerId(String customerId);

    VehicleRepossession findVehicleRepossessionById(Long id);
    
    VehicleRepossession findByCustomerId(String customerId);
}
