package com.csinfotechbd.loanApi.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Created by Yasir Araphat
 * Created at 23 February 2021
 */
public class ResultSetExtractor {

    /**
     * Extract value associated with given column label from ResultSet
     *
     * @param rs
     * @param columnLabel
     * @param defaultValue
     * @return associated value or  default value if column is not present in ResultSet
     */
    @SuppressWarnings("unchecked")
    public static <T> T getValueFromResultSet(final ResultSet rs, String columnLabel, T defaultValue) {
        try {
            if (defaultValue instanceof String) {
                String defValue = Objects.toString(defaultValue, "");
                String value = Objects.toString(rs.getObject(columnLabel), defValue);
                return (T) value;
            }
            return (T) rs.getObject(columnLabel);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return defaultValue;
    }

    /**
     * Extract value associated with given column label from ResultSet
     *
     * @param rs
     * @param columnLabels
     * @param columnLabel
     * @param defaultValue
     * @param <T>
     * @return Associated value or default value if column is not present in ResultSet
     */
    @SuppressWarnings("unchecked")
    public static <T> T getValueFromResultSet(final ResultSet rs, List<String> columnLabels, String columnLabel, T defaultValue) {
        try {
            if (columnLabels.contains(columnLabel))
                return (T) rs.getObject(columnLabel);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return defaultValue;
    }

    /**
     * Extract double value associated with given column label from ResultSet
     *
     * @param rs
     * @param columnLabel
     * @param defaultValue
     * @return Double value from ResultSet column or default value
     */
    public static double getDoubleFromResultSet(final ResultSet rs, String columnLabel, double defaultValue) {
        try {
//            Number number = Optional.ofNullable((Number) rs.getObject(columnLabel)).orElse(0);
            String value = rs.getString(columnLabel);


            Double value2 =  value != null ? Double.parseDouble(value) : defaultValue;
            BigDecimal decimal = new BigDecimal(value2);
            return decimal.setScale(2, RoundingMode.HALF_UP).doubleValue();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return defaultValue;
    }

    /**
     * Extract long value associated with given column label from ResultSet
     *
     * @param rs
     * @param columnLabel
     * @param defaultValue
     * @return long value from ResultSet column or default value
     */
    public static long getLongFromResultSet(final ResultSet rs, String columnLabel, long defaultValue) {
        try {
            Number number = Optional.ofNullable((Number) rs.getObject(columnLabel)).orElse(0);
            return number.longValue();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return defaultValue;
    }

    /**
     * Extract Date value associated with given column label from ResultSet
     *
     * @param rs
     * @param columnLabel
     * @param defaultValue
     * @return Date value from ResultSet column or default value
     */
    public static Date getDateFromResultSet(final ResultSet rs, String columnLabel, Date defaultValue) {
        try {
            return rs.getDate(columnLabel);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return defaultValue;
    }

}
