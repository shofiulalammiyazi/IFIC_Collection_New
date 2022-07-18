package com.csinfotechbd.collection.samd.setup.agencySamd;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.List;

@Repository
public interface AgencySamdRepository extends JpaRepository<AgencySamdEntity,Long> {

    List<AgencySamdEntity> findByEnabled(boolean enabled);


    @Query(value = "SELECT AE.ID AS ID, AE.NAME AS name, AE.CONTACT_PERSON AS contactPerson, AE.CONTACT_NO AS contactNo, AE.REMARKS AS remarks, " +
            " AE.ENABLED AS enabled, AF.DMS_FILE_ID AS dmsFileId, AF.DMS_FILE_TYPE AS dmsFileType, AF.FILE_NAME AS fileName " +
            "FROM AGENCY_SAMD_ENTITY AE " +
            "LEFT JOIN AGENCY_SAMD_FILE AF ON AF.AGENCY_SAMD_ENTITY_ID = AE.ID " +
            "WHERE AE.ID = ? ", nativeQuery = true)

    Tuple findAgencySamdEntityById(Long id);



    AgencySamdEntity findAgencySamdEntityByName(String name);
}
