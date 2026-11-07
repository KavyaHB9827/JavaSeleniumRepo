package com.automation.bigbasket.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Parsing helpers for prices, pack sizes and product names. */
public final class TextUtils {

    private static final Pattern NUMBER = Pattern.compile("(\\d[\\d,]*(?:\\.\\d+)?)");
    private static final Pattern PACK = Pattern.compile(
            "(\\d+(?:\\.\\d+)?\\s?(?:kg|g|gm|gms|ml|l|ltr|litre|pcs|pc|units?|pack))\\b",
            Pattern.CASE_INSENSITIVE);

    private TextUtils() { }

    /** "Rs 1,234.50" -> 1234.5 ; returns 0 when no number is present. */
    public static double parsePrice(String text) {
        if (text == null) return 0;
        Matcher m = NUMBER.matcher(text);
        return m.find() ? Double.parseDouble(m.group(1).replace(",", "")) : 0;
    }

    public static int parseInt(String text) {
        return (int) parsePrice(text);
    }

    /** Pulls "500 ml" / "1 kg" etc. out of a product name; "N/A" if absent. */
    public static String extractPackSize(String productName) {
        if (productName == null) return "N/A";
        Matcher m = PACK.matcher(productName);
        return m.find() ? m.group(1) : "N/A";
    }

    public static String normalize(String s) {
        return s == null ? "" : s.toLowerCase().replaceAll("[^a-z0-9]", "");
    }

    /** Tolerant name comparison - the basket may truncate or re-format long names. */
    public static boolean namesMatch(String a, String b) {
        String x = normalize(a);
        String y = normalize(b);
        if (x.isEmpty() || y.isEmpty()) return false;
        return x.contains(y) || y.contains(x);
    }

    public static double round2(double v) {
        return Math.round(v * 100.0) / 100.0;
    }
}
