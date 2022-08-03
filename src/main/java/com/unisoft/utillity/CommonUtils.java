package com.unisoft.utillity;
/*
 * Created by    on 01 May, 2021
 */

import com.google.gson.Gson;
import org.springframework.stereotype.Component;

import java.lang.reflect.ParameterizedType;
import java.sql.Clob;
import java.util.Arrays;

@Component
public class CommonUtils<T> {

    public boolean doesFieldExistInClass(Class<T> type, String fieldName) {
        return Arrays.stream(type.getDeclaredFields())
                .anyMatch(f -> f.getName().equals(fieldName));
    }

    // Failed
    public Class<T> getParameterizedType() {
        ParameterizedType superClass = (ParameterizedType) getClass().getGenericSuperclass();
        return (Class<T>) superClass.getActualTypeArguments()[0];
    }

    // Failed
    public T getInstanceOfParameterizedType() {
        Class<T> type = getParameterizedType();
        try {
            return type.newInstance();
        } catch (Exception e) {
            // No default constructor
            throw new RuntimeException(e);
        }

    }

    public static <E> E extractClob(Object data, Class<E> type) {
        try {
            Clob clob = (Clob) data;
            String dataListString = StringUtils.clobToString(clob);
            return new Gson().fromJson(dataListString, type);
        } catch (Exception e) {
            e.printStackTrace();
            try {
                return type.newInstance();
            } catch (Exception ignored) {
            }
        }
        return null;
    }
}
