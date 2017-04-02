package net.samplelogin.util;

public class Assert {

    private Assert() {}

    public static <E>void notNull(E obj, Class<?> referenceType) {
        if (obj == null) {
            throw new IllegalArgumentException(String.format("%s must not be null", referenceType.getSimpleName()));
        }
    }

    public static <E>void notNull(E obj, String message) {
        if (obj == null) {
            throw new IllegalArgumentException(message);
        }
    }
}
