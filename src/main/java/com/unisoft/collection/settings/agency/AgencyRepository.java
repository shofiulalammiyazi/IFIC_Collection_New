package com.unisoft.collection.settings.agency;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.List;

@Repository
public interface AgencyRepository extends JpaRepository<AgencyEntity, Long> {

    List<AgencyEntity> findByEnabled(boolean enabled);

    @Query(value = "SELECT AE.ID AS ID, AE.NAME AS name, AE.CONTACT_PERSON AS contactPerson, AE.CONTACT_NO AS contactNo, AE.REGISTERED_ADDRESS AS registeredAddress, " +
            "AE.AGREEMENT_WITH_AGENCY AS agreementWithAgency, AE.ENABLED AS enabled, AF.DMS_FILE_ID AS dmsFileId, AF.DMS_FILE_TYPE AS dmsFileType, AF.FILE_NAME AS fileName " +
            "FROM AGENCY_ENTITY AE " +
            "LEFT JOIN AGENCY_FILE AF ON AF.AGENCY_ENTITY_ID = AE.ID " +
            "WHERE AE.ID = ? ", nativeQuery = true)
    Tuple findAgencyEntityById(Long id);


    AgencyEntity findAgencyEntityByPin(String pin);
}
