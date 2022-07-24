package com.unisoft.utillity;

import java.sql.Clob;
import java.sql.SQLException;
import java.util.*;

import com.google.gson.Gson;
import org.springframework.stereotype.Component;

/**
 * @author Yasir Araphat
 * @since May 27, 2021
 */
@Component
public class StringUtils {

    public List<String> split(String value, String regex) {
        return Arrays.asList(value.split(regex));
    }

    public String join(List<String> values) {
        String listString = values.toString();
        return listString.substring(1, listString.length() - 1);
    }

    public String join(List<String> values, String delimiter) {
        return join(values).replace(",", delimiter);
    }

    public static String concat(String... values) {
        if (values == null || values.length == 0) return "";
        StringBuilder builder = new StringBuilder();
        for (String value : values) {
            builder.append(value);
        }
        return builder.toString();
    }

    public static boolean hasText(String str) {
        return str != null && !str.isEmpty();
    }

    public static String clobToString(final Clob clob) {
        try {
            return clob.getSubString(1, (int) clob.length());
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return "";
    }

    public static <T> T[] jsonStringToArray(String data, Class<T[]> type) {
        Gson gson = new Gson();
        try {
            return gson.fromJson(data, type);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return gson.fromJson("[]", type);
    }

    /**
     * Calculates the similarity score of Strings using Levenshtein distance strategy,
     * where 0.0 implies absolutely no similarity and 1.0 implies absolute similarity.
     * Comparison is case insensitive.
     *
     * @param first  The first string to compare.
     * @param second The second string to compare.
     * @return A number between 0.0 and 1.0.
     */
    public double findLevenshteinSimilarity(String first, String second) {
        if (first == null || second == null)
            return (first == null && second == null) ? 1.0 : 0.0;
        int maxLength = Math.max(first.length(), second.length());
        //Can't divide by 0
        if (maxLength == 0) return 1.0;
        return ((double) (maxLength - computeEditDistance(first, second))) / (double) maxLength;
    }

    private int computeEditDistance(String first, String second) {
        first = first.toLowerCase();
        second = second.toLowerCase();

        int[] costs = new int[second.length() + 1];
        for (int i = 0; i <= first.length(); i++) {
            int previousValue = i;
            for (int j = 0; j <= second.length(); j++) {
                if (i == 0) {
                    costs[j] = j;
                } else if (j > 0) {
                    int useValue = costs[j - 1];
                    if (first.charAt(i - 1) != second.charAt(j - 1)) {
                        useValue = Math.min(Math.min(useValue, previousValue), costs[j]) + 1;
                    }
                    costs[j - 1] = previousValue;
                    previousValue = useValue;

                }
            }
            if (i > 0) {
                costs[second.length()] = previousValue;
            }
        }
        return costs[second.length()];
    }

}
