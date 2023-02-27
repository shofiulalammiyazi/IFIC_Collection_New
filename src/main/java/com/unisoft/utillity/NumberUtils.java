package com.unisoft.utillity;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;

//import com.ibm.icu.text.NumberFormat;

/**
 * @author
 * @since 15 April, 2021
 */

@Component
public class NumberUtils {

    private final String[] BANGLA_DIGITS = {"০", "১", "২", "৩", "৪", "৫", "৬", "৭", "৮", "৯"};

    private final NumberFormat BD_CURRENCY_FORMATTER;
    private final NumberFormat BANGLA_TAKA_FORMATTER;
    private final NumberFormat US_CURRENCY_FORMATTER;

    public NumberUtils() {
        BD_CURRENCY_FORMATTER = NumberFormat.getCurrencyInstance(new Locale("en", "IN"));
        BANGLA_TAKA_FORMATTER = NumberFormat.getCurrencyInstance(new Locale("bn", "BD"));
        US_CURRENCY_FORMATTER = NumberFormat.getCurrencyInstance(new Locale("en", "US"));

        BD_CURRENCY_FORMATTER.setMaximumFractionDigits(2);
        BANGLA_TAKA_FORMATTER.setMaximumFractionDigits(2);
        US_CURRENCY_FORMATTER.setMaximumFractionDigits(2);
    }

    public String formatBdtInEnglish(Number value) {
        if (value.equals(0)) return "0";
        else if (value.equals(0.0)) return "0.00";

        StringBuilder result = new StringBuilder();
        long number;
        if (value instanceof Double) {
            BigDecimal decimalValue = new BigDecimal(value.doubleValue()).setScale(2, RoundingMode.HALF_UP);
            formatNumberToBdCurrency(decimalValue.longValue(), result);

            String decimalString = decimalValue.toString();
            result.append(decimalString.substring(decimalString.indexOf(".")));
        } else {
            number = value.longValue();
            formatNumberToBdCurrency(number, result);
        }
        return result.toString();
//        return BD_CURRENCY_FORMATTER.format(value).replaceAll("RS|Rs\\.|₹|(\\.00)", "");
    }

    private void formatNumberToBdCurrency(long number, StringBuilder result) {
        int count = 0;
        while (number > 0) {
            result.insert(0, (int) (number % 10));
            number /= 10;
            count++;
            if (count > 1 && count % 2 != 0 && number > 0) {
                result.insert(0, ",");
            }
        }
    }

    public String convertToBanglaNumber(Number value) {
        StringBuilder result = new StringBuilder();
        long number;
        int decimal;
        if (value instanceof Double) {
            BigDecimal decimalValue = new BigDecimal(value.doubleValue()).setScale(2, RoundingMode.HALF_UP);
            convertIntoBanglaNumberByBdCurrencyFormat(decimalValue.longValue(), result, true);

            decimal = ((int) Math.round(decimalValue.doubleValue() * 100)) % 100;
            if (decimal > 0) {
                result.append(".");
                StringBuilder precisionResult = new StringBuilder();
                convertIntoBanglaNumberByBdCurrencyFormat(decimal, precisionResult, false);
                result.append(precisionResult.toString());
            }
        } else {
            number = value.longValue();
            convertIntoBanglaNumberByBdCurrencyFormat(number, result, true);
        }
        return value.equals(0) ? "০" : value.equals(0.0) ? "০.০০" : result.toString();
//        return BANGLA_TAKA_FORMATTER.format(value).replaceAll("BDT|BDT |৳|(\\.০০)", "");
    }

    private void convertIntoBanglaNumberByBdCurrencyFormat(long number, StringBuilder result, boolean includeComma) {
        int count = 0;
        while (number > 0) {
            result.insert(0, BANGLA_DIGITS[(int) (number % 10)]);
            number /= 10;
            count++;
            if (includeComma && count > 1 && count % 2 != 0 && number > 0) {
                result.insert(0, ",");
            }
        }
    }

    public String formatGlobalCurrency(Number value) {
        return US_CURRENCY_FORMATTER.format(value).replace("$|(\\.00)", "");
    }

    public String formatToTwoPrecision(String value) {
        return String.format("%.2f", value);
    }

    public static boolean isNumberString(String value) {
        return value != null && value.matches("^\\d+(\\.\\d+)?$");
    }

    public static boolean isNumber(String value) {
        return isNumberString(value);
    }

    public <T extends java.lang.Number> T parseNumber(String value, T defaultValue) {
        try {
            if (isNumberString(value)) {
                return (T) java.text.NumberFormat.getInstance().parse(value);
            }
        } catch (Exception e) {
            System.err.println("Failed to parse Number " + value);
        }
        return defaultValue;
    }

    public static void swap(Number a, Number b) {
        Number c = a;
        a = b;
        b = c;
    }
//
//    public static void swap(int a, int b) {
//        a += b;
//        b = a - b;
//        a = a - b;
//    }
//
//    public static void swap(long a, long b) {
//        a += b;
//        b = a - b;
//        a = a - b;
//    }
//
//    public static void swap(float a, float b) {
//        a += b;
//        b = a - b;
//        a = a - b;
//    }
//
//    public static void swap(double a, double b) {
//        a += b;
//        b = a - b;
//        a = a - b;
//    }

}
