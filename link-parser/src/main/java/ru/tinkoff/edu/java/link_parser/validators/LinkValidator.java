package ru.tinkoff.edu.java.link_parser.validators;

public interface LinkValidator {
    public default boolean validate(String link) {
        try {
            return throwExceptionIfInvalid(link);
        } catch (RuntimeException e) {
            return false;
        }
    }

    public boolean throwExceptionIfInvalid(String link);
}
