package br.com.fiap.challenge.diner.core.application.utils;

public class Strings {

    public static boolean isEmpty(String value) {
        return value == null || value.isEmpty();
    }

    public static boolean isNonEmpty(String value) {
        return value != null && !value.isEmpty();
    }

    public static boolean equals(String a, String b) {
        if (a != null && b != null) {
            return a.equalsIgnoreCase(b);
        } else {
            return a == b;
        }
    }

    public static boolean diff(String a, String b) {
        if (a != null && b != null) {
            return !a.equalsIgnoreCase(b);
        } else {
            return a != b;
        }
    }

    public static boolean containsAny(String text, String... parts) {
        if (text != null) {
            String value = text.toLowerCase();
            for (String part : parts) {
                if (part != null) {
                    if (value.contains(part.toLowerCase())) return true;
                }
            }
        }
        return false;
    }

    public static boolean in(String value, String... items) {
        return isAnyEquals(value, items);
    }

    public static boolean isAnyEquals(String value, String... items) {
        for (String item : items) {
            if (equals(value, item)) return true;
        }
        return false;
    }

    public static boolean notIn(String value, String... items) {
        return isAllDiff(value, items);
    }

    public static boolean isAllDiff(String value, String... items) {
        for (String item : items) {
            if (equals(value, item)) return false;
        }
        return true;
    }
}
