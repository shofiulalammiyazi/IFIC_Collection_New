package com.csinfotechbd.beans;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {

    public static boolean isStringEmpty(String data) {
        return data == null || data.trim().isEmpty();
    }

    public static boolean isContainsSpaces(String data) {
        return data != null && data.contains(" ");
    }

    public static boolean isContainsRepetitiveCharacters(String data) {
        Pattern pattern = Pattern.compile("(\\w)\\1+");
        Matcher matcher = pattern.matcher(data);
        return matcher.find();
    }
}
