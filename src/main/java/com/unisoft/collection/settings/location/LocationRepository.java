package com.unisoft.collection.settings.location;
/*
Created by   Islam at 9/25/2019
*/

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<LocationEntity, Long> {

    LocationEntity findByCity(String cityName);

    List<LocationEntity> findByEnabled(boolean enabled);
}
