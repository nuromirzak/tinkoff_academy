package ru.tinkoff.edu.java.link_parser.validators;

public interface LinkValidator {
    /**
     * @param link ссылка для проверки
     * @return true - если ссылка валидна, кидает исключение - если ссылка не валидна
     */
    public boolean validate(String link);
}
