package com.csinfotechbd.utillity;
/*
  Created by MR on 9/28/2021
*/

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class InputSanitizer {

//    String alphanumericRegex = "^[a-zA-Z0-9 ]+$";
//    Pattern alphanumericPattern = Pattern.compile(alphanumericRegex);

    private boolean validAlphanumeric(String name) {
//        Matcher matcher = alphanumericPattern.matcher(name);
//        return matcher.matches();

        boolean isLower;
        boolean isUpper;
        boolean isDigit;
        boolean isPermitted;

        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);
            isLower = (c >= 'a' && c <= 'z');
            isUpper = (c >= 'A' && c <= 'Z');
            isDigit = (c >= '0' && c <= '9');
            isPermitted = validChar(c);
            if (!isLower && !isUpper && !isDigit && !isPermitted)
                return false;
        }
        return true;
    }

    private boolean validChar(char c) {
        String validChars = new String(":\\ /-");
        for (int i = 0; i < validChars.length(); i++) {
            if (validChars.charAt(i) == c)
                return true;
        }
        return false;
    }

    public boolean isSafe(String str) {
        String s = str.toLowerCase();
        if (s.contains("select") || s.contains("where") || s.contains("delete")
                || s.contains("update") || s.contains("alter")
                || s.contains("script") || !validAlphanumeric(s))
            return false;
        return true;
    }

    public boolean isSafe(List<String> strList) {
        boolean safe = true;
        for (String s : strList) {
            if (s != null)
                safe &= isSafe(s);
        }
        return safe;
    }
}
