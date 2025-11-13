package org.reflections;

public enum NUriScheme {

    HTTP("http"),

    HTTPS("https");

    private String value;

    NUriScheme(String value) {
        this.value = value;
    }

    public static NUriScheme fromString(String s) {
        for (NUriScheme b : NUriScheme.values()) {
            if (java.util.Objects.toString(b.value).equals(s)) {
                return b;
            }
        }
        throw new IllegalArgumentException("Unexpected string value '" + s + "'");
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    public static NUriScheme fromValue(String value) {
        for (NUriScheme b : NUriScheme.values()) {
            if (b.value.equals(value)) {
                return b;
            }
        }
        throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
}
