package com.unisoft.collection.settings.unit;

import java.util.List;

public interface UnitService {
    List<UnitEntity> getList();

    String save(UnitEntity unit);

    UnitEntity getById(Long id);

    UnitEntity getByName(String name);

    List<UnitEntity> getByName(String... name);

    List<UnitEntity> getActiveList();
}
