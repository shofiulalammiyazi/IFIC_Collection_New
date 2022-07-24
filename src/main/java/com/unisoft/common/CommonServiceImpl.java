package com.unisoft.common;

import com.unisoft.audittrail.AuditTrailService;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Provides implementation for commonly required persistent operations
 * (e.g. Persist, Data audit and Fetch)
 * <p>
 * Implementation by Yasir Araphat
 * on 25 April, 2021
 *
 * @param <T>
 */
@Service
public class CommonServiceImpl<T extends CommonEntity> implements CommonService<T> {

    protected CommonRepository<T> repository;

    @Autowired
    private AuditTrailService auditTrailService;

    @Override
    public Map<String, Object> storeData(T data) {
        if (data.getId() != null)
            return updateData(data);

        Map<String, Object> messages = new HashMap<>();

        try {
            repository.save(data);
            auditTrailService.saveCreatedData(getModuleName(data), data);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        messages.put("success", true);
        return messages;
    }

    @Override
    public Map<String, Object> updateData(T data) {
        Map<String, Object> messages = new HashMap<>();
        try {
            T previousData = getPreviousData(data);
            if (previousData == null)
                return storeData(data);
            repository.save(data);
            auditTrailService.saveUpdatedData(getModuleName(data), previousData, data);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        messages.put("success", true);
        return messages;
    }

    @Override
    public Map<String, Object> deleteData(T data) {
        return null;
    }

    @Override
    public T findDataById(Long id) {
        return repository.getOne(id);
    }

    @Override
    public List<T> findAllData() {
        return repository.findAll();
    }

    @Override
    public List<String> validate(T data) {
        return null;
    }

    public List<T> findByEnabled(boolean enabled) {
        return repository.findByEnabled(enabled);
    }

    private T getPreviousData(T data) {
        T previousData = repository.getOne(data.getId());
        if (previousData == null) {
            data.setId(null); // Prevents non-persistent ids received from front-end from being persisted
            return null;
        } else {
            data.setCreatedBy(previousData.getCreatedBy());
            data.setModifiedBy(previousData.getModifiedBy());
            return (T) Hibernate.unproxy(previousData); // unproxy prevents serialization error
        }
    }

    private String getModuleName(T data) {
        String className = data.getClass().getSimpleName();
        StringBuilder classNameBuilder = new StringBuilder();
        int length = className.length();
        for (int i = 0; i < length; i++) {
            boolean isWordBreak = i > 0 && Character.isUpperCase(className.charAt(i)) &&
                    !Character.isUpperCase(className.charAt(i - 1));
            if (isWordBreak)
                classNameBuilder.append(' ');
            classNameBuilder.append(className.charAt(i));
        }
        return classNameBuilder.toString();
    }
}
