package com.csinfotechbd.common;

import java.util.List;
import java.util.Map;

public interface CommonService<T> {

    Map<String, Object> storeData(T data);

    Map<String, Object> updateData(T data);

    Map<String, Object> deleteData(T data);

    T findDataById(Long id);

    List<T> findAllData();

    List<String> validate(T data);
}
